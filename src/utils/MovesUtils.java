package utils;

import board.*;
import pawn.*;

public class MovesUtils {

	public static boolean[][] possibleMoves(Board board, Pawn pawn) {
		boolean[][] validMoves = new boolean[8][8];
		int r, c;		
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				validMoves[r][c] = false;
				
				if(pawn.getRow() == r && pawn.getCol() == c)
					continue;
				
				if(pawn.isValidMove(board, r, c)) {
					validMoves[r][c] = true;
				}
			}
		}
		
		return validMoves;
	}
	
	public static boolean pawnCanMove(Board board, Pawn pawn) {
		int r, c;
		boolean[][] validMoves = MovesUtils.possibleMoves(board, pawn);
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				if(validMoves[r][c]) {
					return true;
				}
			}
		}
		
		return false;
	}
}