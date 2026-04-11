package pawn;

import board.*;

public class King extends Pawn {
	
	public King(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}
	
    @Override
    public String getPawnType() {
    	return "King";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(Math.abs(newCol - col) > 1 || Math.abs(newRow - row) > 1)
    		return false;
    	
    	if(target != null) {
    		if(target.isWhite == isWhite)
    			return false;
    	}
    	
    	return true;
    }
    
    
}
