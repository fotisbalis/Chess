package utils;

import java.util.ArrayList;

import board.*;
import pawn.*;

public class AIUtils {

	public static ArrayList<int[]> legalMoves(Board board, PawnColor aiColor) {
		
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
	    
	    return legalMoves;
	}
	
	public static int boardScore(Board board, PawnColor aiColor) {
		
		int score = 0;
		
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				Pawn pawn = board.getPawn(r, c);

	            if(pawn == null) {
	                continue;
	            }

	            int value = pawn.getPawnValue();

	            if(pawn.getColor() == aiColor)
	                score += value;
	            else 
	                score -= value;
			}
		}
		
		return score;
	}

}
