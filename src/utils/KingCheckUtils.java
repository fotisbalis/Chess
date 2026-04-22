package utils;

import pawn.*;
import board.*;

public class KingCheckUtils {

	public static King findKing(Board board, PawnColor color) {
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn pawn = board.getPawn(r, c);
				
				if(pawn != null && pawn instanceof King && pawn.getColor() == color) {
					King king = (King) pawn;
					
					return king;
				}
			}
		}
		
		return null;
	}
	
	public static boolean isKingInDanger(Board board, PawnColor color) {
		int r, c;
		
		King king = KingCheckUtils.findKing(board, color);
		
		if(king == null) return false;
		
		int kingRow = king.getRow();
		int kingCol = king.getCol();
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn pawn = board.getPawn(r, c);
				
				if(pawn != null && pawn.getColor() != color) {
					boolean validMoves[][] = MovesUtils.possibleMoves(board, pawn, false);
					
					if(validMoves[kingRow][kingCol])
						return true;
				}
			}
		}
		
		return false;
	}
	
}
