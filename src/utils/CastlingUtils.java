package utils;

import board.*;
import pawn.*;

public class CastlingUtils {

	public static boolean isMoveCastling(Pawn pawn, int newRow, int newCol) {
		
		if(!(pawn instanceof King))
			return false;
		
		if(pawn.getRow() != newRow)
			return false;
		
		if(Math.abs(newCol - pawn.getCol()) != 2)
			return false;
		
		return true;
	}

	public static boolean isKingsideCastling(int fromCol, int toCol) {
		
		return toCol > fromCol;
	}

	public static int getRookStartCol(int kingTargetCol) {
		
		if(kingTargetCol == 6)
			return 7; 
		
		if(kingTargetCol == 2)
			return 0;
		
		//Ignore
		return -1;
	}

	public static int getRookTargetCol(int kingTargetCol) {
		
		if(kingTargetCol == 6)
			return 5; 
		
		if(kingTargetCol == 2)
			return 3;
		
		//Ignore
		return -1;
	}

	public static int getKingMiddleCol(int fromCol, int toCol) {
		
		return fromCol + Integer.compare(toCol, fromCol);
	}

	public static boolean hasCastlingRook(Board board, King king, int targetCol) {
		
		Pawn pawn = board.getPawn(king.getRow(), CastlingUtils.getRookStartCol(targetCol));

		if(!(pawn instanceof Rook))
			return false;
		
		Rook rook = (Rook) pawn;
		
		if(rook.getColor() != king.getColor())
			return false;
		
		if(rook.hasMoved())
			return false;
			
		return true;
	}

}
