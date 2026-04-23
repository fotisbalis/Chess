package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import pawn.*;
import utils.*;
import board.*;
import move.*;

public class AI {

	public static Move chooseMove(Board board, PawnColor aiColor, int depth){
		
		ArrayList<Move> legalMoves = AIUtils.legalMoves(board, aiColor);
	    int maxScore = Integer.MIN_VALUE;
	    Move bestMove = null;
		
	    if(legalMoves.isEmpty()) {
	        return null;
	    }

	    int threadCount = Math.min(legalMoves.size(), Runtime.getRuntime().availableProcessors());
	    ExecutorService executor = Executors.newFixedThreadPool(Math.max(1, threadCount));
	    List<Future<MoveScore>> futures = new ArrayList<Future<MoveScore>>();

	    try {
	    	for(Move move : legalMoves) {
	    		futures.add(executor.submit(new Callable<MoveScore>() {
	    			@Override
	    			public MoveScore call() {
	    				Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());

	    				if(pawn == null)
	    					return null;

	    				Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());

	    				int score = AIUtils.scoreAfterMove(board, tmpBoard, move, aiColor);
	    				score += simulateMoveScoreInDepth(tmpBoard, aiColor, aiColor.opposite(), depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

	    				return new MoveScore(move, score);
	    			}
	    		}));
	    	}

	    	for(Future<MoveScore> future : futures) {
	    		MoveScore moveScore = future.get();

	    		if(moveScore == null)
	    			continue;

	    		if(moveScore.score > maxScore) {
	    			maxScore = moveScore.score;
	    			bestMove = moveScore.move;
	    		}
	    	}
	    }
	    catch(InterruptedException e) {
	    	Thread.currentThread().interrupt();
	    }
	    catch(ExecutionException e) {
	    	throw new RuntimeException("Failed to evaluate AI moves in parallel", e);
	    }
	    finally {
	    	executor.shutdown();
	    }
	    
	    return bestMove;
	}
	
	private static int simulateMoveScoreInDepth(Board board, PawnColor aiColor, PawnColor turnColor, int depth, int alpha, int beta) {
		
		if(depth <= 0)
			return AIUtils.boardScore(board, aiColor);
		
		ArrayList<Move> legalMoves = AIUtils.legalMoves(board, turnColor);
		
		if(legalMoves.isEmpty())
			return AIUtils.boardScore(board, aiColor);
		
		if(turnColor == aiColor) {
	        int bestScore = Integer.MIN_VALUE;
 
	        for(Move move : legalMoves) {
	            Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());
	            
	            if(pawn == null)
	            	continue;
	            
	            Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());

	            int score = simulateMoveScoreInDepth(tmpBoard, aiColor, turnColor.opposite(), depth - 1, alpha, beta);
	            bestScore = Math.max(bestScore, score);
	            alpha = Math.max(alpha, bestScore);
	            
	            if(beta <= alpha)
	            	break;
	        }

	        return bestScore;
	    } 
		else {
	        int bestScore = Integer.MAX_VALUE;

	        for (Move move : legalMoves) {
	            Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());
	            
	            if(pawn == null)
	            	continue;
	            
	            Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());

	            int score = simulateMoveScoreInDepth(tmpBoard, aiColor, turnColor.opposite(), depth - 1, alpha, beta);
	            bestScore = Math.min(bestScore, score);
	            beta = Math.min(beta, bestScore);
	            
	            if(beta <= alpha)
	            	break;
	        }
	        return bestScore;
		}
	}

	private static class MoveScore {
		private final Move move;
		private final int score;

		private MoveScore(Move move, int score) {
			this.move = move;
			this.score = score;
		}
	}
	
}
