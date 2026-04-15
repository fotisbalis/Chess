package pawn;

import board.*;

public class King extends Pawn {
	
	public King(int row, int col, PawnColor color) {
		super(row, col, color);
	}
	
    @Override
    public String getPawnType() {
    	return "King";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(Math.abs(newCol - col) > 1 || Math.abs(newRow - row) > 1)
    		return false;
    	
    	if(target != null) {
    		if(target.getColor() == color)
    			return false;
    	}
    	
    	return true;
    }
    
    
}
