package pawn;

import board.*;

public class Knight extends Pawn {
	
	public Knight(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.KNIGHT;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if((Math.abs(newRow - row) == 1 && Math.abs(newCol - col) == 2) || 
    	   (Math.abs(newRow - row) == 2 && Math.abs(newCol - col) == 1)) {
    		if(target != null) {
        		if(target.getColor() != color) {
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

