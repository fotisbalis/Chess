package ai;

import java.util.ArrayList;

import pawn.*;
import utils.*;
import board.*;

public class AI {

	public static int[] chooseMove(Board board, PawnColor aiColor){
		
		ArrayList<int[]> legalMoves = AIUtils.legalMoves(board, aiColor);
	    int max_score = Integer.MIN_VALUE;
	    int[] best_move = null;
		
	    if(legalMoves.isEmpty()) {
	        return null;
	    }
	    
	    for(int[] move : legalMoves) {
	    	Pawn pawn = board.getPawn(move[0], move[1]);
	    	Board tmpBoard = MovesUtils.simulateMove(board, pawn, move[2], move[3]);
	    	
	    	int tmpBoardScore = AIUtils.boardScore(tmpBoard, aiColor);
	    	if(tmpBoardScore > max_score) {
	    		max_score = tmpBoardScore;
	    		best_move = move;
	    	}
	    }
	    
	    return best_move;
	}
	
}
