package utils;

import pawn.*;
import board.*;

public class EnPassantUtils {
	
	public static boolean canEnPassantHappen(Board board, Soldier defender) {
		
		if(!MovesUtils.wasDoubleSquareSoldierMove(board, defender))
			return false;
		
		Pawn rightAttacker, leftAttacker;
		
		if(defender.getCol() == 0) {
			rightAttacker = board.getPawn(defender.getRow(), defender.getCol() + 1);
			
			return EnPassantUtils.isAttackerValid(board, defender, rightAttacker);
		}
		
		else if(defender.getCol() == 7) {
			leftAttacker = board.getPawn(defender.getRow(), defender.getCol() - 1);
			
			return EnPassantUtils.isAttackerValid(board, defender, leftAttacker);
		}
		
		leftAttacker = board.getPawn(defender.getRow(), defender.getCol() - 1);
		rightAttacker = board.getPawn(defender.getRow(), defender.getCol() + 1);
		
		return EnPassantUtils.isAttackerValid(board, defender, leftAttacker) || EnPassantUtils.isAttackerValid(board, defender, rightAttacker);
	}
	
	private static boolean isAttackerValid(Board board, Soldier defender, Pawn attacker) {
		
		if(attacker == null)
			return false;
		
		if(defender.getColor() == attacker.getColor())
	        return false;
		
		if(!(attacker instanceof Soldier))
			return false;    		
	    
		return true;
	}

	public static boolean isRightSideEnPassant(int fromCol, int toCol) {
		
		return toCol > fromCol;
	}
	
	public static Soldier getDefender(Board board, Soldier attacker, boolean isRightSide) {
		
		int row = attacker.getRow(); 
		int col = isRightSide ? attacker.getCol() + 1 : attacker.getCol() - 1;
		
		if(col < 0 || col > 7)
			return null;
		
		Pawn defender = board.getPawn(row, col);
		
		if(!(defender instanceof Soldier))
			return null;
		
		return (Soldier) defender;
	}
	
	public static boolean isMoveEnPassant(Board board, Soldier attacker, int toRow, int toCol) {

		if(Math.abs(toCol - attacker.getCol()) != 1)
	        return false;
		
		int expectedRow = attacker.getColor() == PawnColor.WHITE ? attacker.getRow() - 1 : attacker.getRow() + 1;

	    if(toRow != expectedRow)
	        return false;
	    
	    if(board.getPawn(toRow, toCol) != null)
			return false;
		
		boolean isRightSide = EnPassantUtils.isRightSideEnPassant(attacker.getCol(), toCol);
		Soldier defender = EnPassantUtils.getDefender(board, attacker, isRightSide);
		
		if(defender == null)
			return false;
		
		if(defender.getColor() == attacker.getColor())
			return false;
		
		return true;
	}
	
	
	
}
