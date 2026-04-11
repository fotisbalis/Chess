package utils;

import pawn.*;
import board.*;

public class KingCheckUtils {

	public static King findOpponentKing(Board board, boolean whiteTurn) {
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 0; c++) {
				
				Pawn pawn = board.getPawn(r, c);
				
				if(pawn != null && pawn instanceof King && pawn.isWhite() != whiteTurn) {
					King king = (King) pawn;
					
					return king;
				}
			}
		}
		
		return null;
	}
	
	public static boolean isKingInDanger(Board board, boolean whiteTurn) {
		int r, c;
		
		King king = KingCheckUtils.findOpponentKing(board, whiteTurn);
		int kingRow = king.getRow();
		int kingCol = king.getCol();
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 0; c++) {
				
				Pawn pawn = board.getPawn(r, c);
				
				if(pawn != null && pawn.isWhite() == whiteTurn) {
					boolean validMoves[][] = MovesUtils.possibleMoves(board, pawn);
					
					if(validMoves[kingRow][kingCol])
						return true;
				}
			}
		}
		
		return false;
	}
	
	
}
