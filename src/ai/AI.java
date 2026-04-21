package ai;

import java.util.ArrayList;

import pawn.*;
import utils.MovesUtils;
import board.*;

public class AI {

	public static int[] chooseMove(Board board, PawnColor aiColor){
		
		ArrayList<int[]> legalMoves = new ArrayList<int[]>();

	    for(Pawn pawn : board.getPlayerPawns(aiColor)) {
	        boolean[][] possibleLegalMoves = MovesUtils.possibleLegalMoves(board, pawn);

	        for(int row = 0; row < 8; row++) {
	            for(int col = 0; col < 8; col++) {
	                if(possibleLegalMoves[row][col]) {
	                    legalMoves.add(new int[] {pawn.getRow(), pawn.getCol(), row, col});
	                }
	            }
	        }
	    }
	    
	    if(legalMoves.isEmpty()) {
	        return null;
	    }
	    
	    for(int[] move : legalMoves) {
	    	Pawn target = board.getPawn(move[2], move[3]);
	    	
	    	if(target != null)
	    		return move;
	    }
	    
	    int randomIndex = (int)(Math.random() * legalMoves.size());
	    return legalMoves.get(randomIndex);
	}
	
}
