package utils;

import pawn.*;
import board.*;

public class GameCheckUtils {
	
	public static boolean gameOver(Board board) {
		
		King whiteKing = KingCheckUtils.findKing(board, true);		
		King blackKing = KingCheckUtils.findKing(board, false);
		
		if(whiteKing == null || blackKing == null)
			return true;
		
		return false;
	}
}