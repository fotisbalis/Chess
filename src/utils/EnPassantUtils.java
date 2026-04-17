package utils;

import pawn.*;
import board.*;

public class EnPassantUtils {
	
	public static boolean canEnPassantHappen(Board board, Soldier defender, Soldier attacker, boolean lastMove) {
		
		PawnColor defenderColor = defender.getColor();
		PawnColor attackerColor = attacker.getColor();
		
		int row = defenderColor.equals(PawnColor.WHITE) ? 4 : 5;
		int targetRow = defenderColor.equals(PawnColor.WHITE) ? 3 : 6;
		int targetCol = defender.getCol();
		
		// 1st rule: different colors
		if(defenderColor.equals(attackerColor))
			return false;
		
		// 2nd rule: defender must have made only one move and it should have been on the previous turn
		if(defender.getMoveCount() != 1 || !lastMove)
			return false;
		
		// 3rd rule: defender must have just made a 2 square move
		if(defender.getRow() != row)
			return false;
		
		// 4th rule: attacker must be on same row and one column away
		if(attacker.getRow() != row || (attacker.getCol() != defender.getCol() - 1 && attacker.getCol() != defender.getCol() + 1))
			return false;
		
		// 5th rule: the square that the attacker is about to move to must be empty
		if(board.getPawn(targetRow, targetCol) != null)
			return false;
		
		return true;
	}

	public static boolean isRightSideEnPassant(int fromCol, int toCol) {
		
		return toCol > fromCol;
	}
	
	public static Soldier getDefender(Board board, Soldier attacker, boolean isRightSide) {
		
		int row = attacker.getRow(); 
		int col = isRightSide ? attacker.getCol() + 1 : attacker.getCol() - 1;
		
		Pawn defender = board.getPawn(row, col);
		
		if(!(defender instanceof Soldier))
			return null;
		
		return (Soldier) defender;
	}
	
	public static boolean isMoveEnPassant(Board board, Soldier attacker, int toRow, int toCol) {
		
		boolean isRightSide = EnPassantUtils.isRightSideEnPassant(attacker.getCol(), toCol);
		
		Soldier defender = EnPassantUtils.getDefender(board, attacker, isRightSide);
		Pawn target = board.getPawn(toRow, toCol);
		
		if(target != null)
			return false;
		
		if(defender == null)
			return false;
		
		if(defender.getColor() == attacker.getColor())
			return false;
		
		if(Math.abs(toCol - attacker.getCol()) != 1)
	        return false;
		
		int expectedRow = attacker.getColor() == PawnColor.WHITE ? attacker.getRow() - 1 : attacker.getRow() + 1;

	    if(toRow != expectedRow)
	        return false;
		
		return true;
	}
	
}
