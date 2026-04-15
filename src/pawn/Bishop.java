package pawn;

import board.*;
import utils.MovesUtils;

public class Bishop extends Pawn {
	
	public Bishop(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.BISHOP;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
       	
    	if(Math.abs(newRow - row) != Math.abs(newCol - col)) return false;
   	 
    	if(!MovesUtils.isDiagonalValidMove(board, row, col, newRow, newCol))
    		return false;
    	
    	if(target != null) {
    		if(target.getColor() == color) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    
}

