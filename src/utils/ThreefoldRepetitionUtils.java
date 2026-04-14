package utils;

import board.*;
import pawn.*;

public class ThreefoldRepetitionUtils {
	
	public static boolean equalBoardStates(BoardState boardState1, BoardState boardState2) {
		
		Board board1 = boardState1.getBoard();
		Board board2 = boardState2.getBoard();
		
		boolean whiteTurn1 = boardState1.getWhiteTurn();
		boolean whiteTurn2 = boardState2.getWhiteTurn();
		
		if(whiteTurn1 != whiteTurn2)
			return false;
		
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn p1 = board1.getPawn(r, c);
				Pawn p2 = board2.getPawn(r, c);
				
				if(p1 == null && p2 == null)
					continue;
				
				if((p1 == null && p2 != null) || (p1 != null && p2 == null))
					return false;
				
				if(!p1.getPawnType().equals(p2.getPawnType()))
					return false;
				
				if(p1.isWhite() != p2.isWhite())
					return false;
				
				if(p1 instanceof Soldier && p2 instanceof Soldier) {
					Soldier s1 = (Soldier) p1;
					Soldier s2 = (Soldier) p2;

					if(s1.isFirstMove() != s2.isFirstMove())
						return false;
				}
			}
		}
		
		return true;
	}

}
