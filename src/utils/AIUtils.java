package utils;

import java.util.ArrayList;

import board.*;
import pawn.*;
import move.*;

public class AIUtils {

	public static ArrayList<Move> legalMoves(Board board, PawnColor aiColor) {
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();

	    for(Pawn pawn : board.getPlayerPawns(aiColor)) {
	        boolean[][] possibleLegalMoves = MovesUtils.possibleLegalMoves(board, pawn);

	        for(int row = 0; row < 8; row++) {
	            for(int col = 0; col < 8; col++) {
	                if(possibleLegalMoves[row][col]) {
	                    legalMoves.add(new Move(pawn.getRow(), pawn.getCol(), row, col));
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
	            int position_score = 0;
	            
	            if((pawn.getCol() == 3 || pawn.getCol() == 4) && !(pawn instanceof King))
	            	position_score = value / 10;

	            if(pawn.getColor() == aiColor)
	                score += value + position_score;
	            else 
	                score -= value + position_score;
			}
		}
		
		return score;
	}

	public static boolean isPawnInDanger(Board board, Pawn pawn) {
		int r, c;
		
		if(pawn == null) return false;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn attackingPawn = board.getPawn(r, c);
				
				if(attackingPawn != null && attackingPawn.getColor() != pawn.getColor()) {
					boolean validMoves[][] = MovesUtils.possibleMoves(board, attackingPawn);
					
					if(validMoves[pawn.getRow()][pawn.getCol()])
						return true;
				}
			}
		}
		
		return false;
	}
}
