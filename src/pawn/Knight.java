package pawn;

import board.*;

public class Knight extends Pawn {
	
	public Knight(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}
	
    @Override
    public String getPawnType() {
    	return "Knight";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if((Math.abs(newRow - row) == 1 && Math.abs(newCol - col) == 2) || 
    	   (Math.abs(newRow - row) == 2 && Math.abs(newCol - col) == 1)) {
    		if(target != null) {
        		if(target.isWhite != isWhite) {
        			return true;
        		}
        		else {
        			return false;
        		}
        	}
    		else {
    			return true;
    		}
    	}
    
        return false;
    }
    
    
}

