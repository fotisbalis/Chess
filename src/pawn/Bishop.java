package pawn;

import board.*;

public class Bishop extends Pawn {
	
	public Bishop(int row, int col, PawnColor color) {
		super(row, col, color);
	}
	
    @Override
    public String getPawnType() {
    	return "Bishop";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	int i;
    	Pawn target = board.getPawn(newRow, newCol);
       	
    	if(Math.abs(newRow - row) != Math.abs(newCol - col)) return false;
   	 
    	if(newCol > col && newRow > row) {
	    	for(i = 1; i < (newRow - row); i++) {
	    		if(board.getPawn(row + i, col + i) != null) {
	    			return false;
	    		}
	    	}
    	}
    	else if(newCol < col && newRow > row) {
	    	for(i = 1; i < (newRow - row); i++) {
	    		if(board.getPawn(row + i, col - i) != null) {
	    			return false;
	    		}
	    	}
    	}
    	else if(newCol > col && newRow < row) {
	    	for(i = 1; i < (row - newRow); i++) {
	    		if(board.getPawn(row - i, col + i) != null) {
	    			return false;
	    		}
	    	}
    	}
    	else if(newCol < col && newRow < row) {
	    	for(i = 1; i < (row - newRow); i++) {
	    		if(board.getPawn(row - i, col - i) != null) {
	    			return false;
	    		}
	    	}
    	}
    	
    	if(target != null) {
    		if(target.getColor() == color) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    
}

