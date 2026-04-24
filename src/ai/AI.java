package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import pawn.*;
import utils.*;
import board.*;
import move.*;

public class AI {

	private static final int MIN_PARALLEL_DEPTH = 4;
	private static final int MIN_PARALLEL_MOVES = 8;
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(
			Math.max(1, Runtime.getRuntime().availableProcessors()));

	public static Move chooseMove(Board board, PawnColor aiColor, int depth, int moveCounter){
		
		ArrayList<Move> legalMoves = AIUtils.legalMoves(board, aiColor);
	    int maxScore = Integer.MIN_VALUE;
	    Move bestMove = null;
		
	    if(legalMoves.isEmpty()) {
	        return null;
	    }
	    
	    if(moveCounter < 2) {
	    	int randomIndex = (int)(Math.random() * legalMoves.size());
		    
	    	while(board.getPawn(legalMoves.get(randomIndex).getStartingRow(), legalMoves.get(randomIndex).getStartingCol()) instanceof King)
	    		randomIndex = (int)(Math.random() * legalMoves.size());
	    	
	    	return legalMoves.get(randomIndex);
	    }

	    if(depth < MIN_PARALLEL_DEPTH || legalMoves.size() < MIN_PARALLEL_MOVES) {
	    	for(Move move : legalMoves) {
	    		Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());

	    		if(pawn == null)
	    			continue;

	    		Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());

	    		int score = AIUtils.scoreAfterMove(board, tmpBoard, move, aiColor);
	    		score += simulateMoveScoreInDepth(tmpBoard, aiColor, aiColor.opposite(), depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

	    		if(score > maxScore) {
	    			maxScore = score;
	    			bestMove = move;
	    		}
	    	}

	    	return bestMove;
	    }

	    List<Future<MoveScore>> futures = new ArrayList<Future<MoveScore>>();

	    try {
	    	for(Move move : legalMoves) {
	    		futures.add(EXECUTOR.submit(new Callable<MoveScore>() {
	    			@Override
	    			public MoveScore call() {
	    				Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());

	    				if(pawn == null)
	    					return null;

	    				Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());
	    				PromotionUtils.handlePromotion(null, tmpBoard, aiColor, true, true, aiColor);

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
	        	int score = scoreMoveInDepth(board, move, aiColor, turnColor, depth, alpha, beta);
	            
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
	        	int score = scoreMoveInDepth(board, move, aiColor, turnColor, depth, alpha, beta);
	            
	            bestScore = Math.min(bestScore, score);
	            beta = Math.min(beta, bestScore);
	            
	            if(beta <= alpha)
	            	break;
	        }
	        return bestScore;
		}
	}

	private static int scoreMoveInDepth(Board board, Move move, PawnColor aiColor, PawnColor turnColor, int depth, int alpha, int beta) {
		Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());

		if(pawn == null) {
			return turnColor == aiColor ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}

		boolean pawnMoveState = pawn.hasMoved();
		Pawn target = board.getPawn(move.getTargetRow(), move.getTargetCol());

		if(pawn instanceof Soldier && EnPassantUtils.isMoveEnPassant(board, (Soldier) pawn, move.getTargetRow(), move.getTargetCol()) ||
				pawn instanceof King && CastlingUtils.isMoveCastling((King) pawn, move.getTargetRow(), move.getTargetCol())) {
			Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());
			return simulateMoveScoreInDepth(tmpBoard, aiColor, turnColor.opposite(), depth - 1, alpha, beta);
		}

		if(pawn instanceof Soldier && move.getTargetRow() == (pawn.getColor() == PawnColor.WHITE ? 0 : 7)) {
			MovesUtils.makeTmpMove(board, move);
			PromotionUtils.handlePromotion(null, board, aiColor, true, true, aiColor);
			int score = simulateMoveScoreInDepth(board, aiColor, turnColor.opposite(), depth - 1, alpha, beta);
			MovesUtils.undoTmpMove(board, move, target, pawnMoveState);
			return score;
		}

		MovesUtils.makeTmpMove(board, move);
		int score = simulateMoveScoreInDepth(board, aiColor, turnColor.opposite(), depth - 1, alpha, beta);
		MovesUtils.undoTmpMove(board, move, target, pawnMoveState);
		return score;
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
