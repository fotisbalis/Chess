package ai;

import java.util.ArrayList;

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
	    
	    for(Move move : legalMoves) {
	    	Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());
	    	
	    	if(pawn == null)
	    		continue;
	    	
	    	Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());
	    	
	    	int score = AIUtils.currentScore(board, tmpBoard, move, aiColor);
	    
	    	score += simulateMoveScoreInDepth(tmpBoard, aiColor, aiColor.opposite(), depth - 1);
	    	
	    	if(score > maxScore) {
	    		maxScore = score;
	    		bestMove = move;
	    	}
	    }
	    
	    return bestMove;
	}
	
	private static int simulateMoveScoreInDepth(Board board, PawnColor aiColor, PawnColor turnColor, int depth) {
		
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

	            int score = simulateMoveScoreInDepth(tmpBoard, aiColor, turnColor.opposite(), depth - 1);
	            bestScore = Math.max(bestScore, score);
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

	            int score = simulateMoveScoreInDepth(tmpBoard, aiColor, turnColor.opposite(), depth - 1);
	            bestScore = Math.min(bestScore, score);
	        }
	        return bestScore;
		}
	}
	
}
