package utils;

import board.*;
import pawn.*;

public class CastlingUtils {
	
	public static boolean canCastlingHappen(Board board, King king, int kingTargetCol) {
		
		Boolean isKingSide = CastlingUtils.isKingsideCastling(king.getCol(), kingTargetCol);
		
		PawnColor turnColor = king.getColor();
		
		Rook rook = CastlingUtils.getRook(board, king, isKingSide);
		if(rook == null) return false;
		
		int stepCastlingDirection = isKingSide ? 1 : -1;
		int row = king.getRow();
		
		// 1st rule: King and Rook should not have moved
		if(king.hasMoved() || rook.hasMoved())
			return false;
		
		// 2nd rule: Squares between King and Rook must be empty
		if(!MovesUtils.isSameRowValidMove(board, rook.getCol(), row, king.getCol() + stepCastlingDirection))
			return false;
		
		// 3rd rule: King must not currently be in check
		if(KingCheckUtils.isKingInDanger(board, turnColor))
			return false;
		
		// 4th rule: The square that the King will pass through and the destination square must not be in check
		if(CastlingUtils.isSquareInDanger(board, turnColor, row, king.getCol() + stepCastlingDirection) ||
				CastlingUtils.isSquareInDanger(board, turnColor, row, kingTargetCol))
			return false;
		
		return true;
	}
	
	public static boolean isMoveCastling(King king, int newRow, int newCol) {
		
		if(king.getRow() != newRow)
			return false;
		
		if(Math.abs(newCol - king.getCol()) != 2)
			return false;
		
		return true;
	}

	public static boolean isKingsideCastling(int fromCol, int toCol) {
		
		return toCol > fromCol;
	}
	
	public static Rook getRook(Board board, King king, boolean isKingSide) {
		
		int row = king.getRow();
		int col = isKingSide ? 7 : 0;
		
		Pawn pawn = board.getPawn(row, col);
		
		if(pawn instanceof Rook)
			return (Rook) pawn;
		
		return null;	
	}
	
	public static boolean isSquareInDanger(Board board, PawnColor color, int row, int col) {
		int r, c;
			
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn pawn = board.getPawn(r, c);
				
				if(pawn != null && pawn.getColor() != color) {
					boolean validMoves[][] = MovesUtils.possibleMoves(board, pawn);
					
					if(validMoves[row][col])
						return true;
				}
			}
		}
		
		return false;
	}
}
