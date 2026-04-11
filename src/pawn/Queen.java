package pawn;

import board.Board;

public class Queen extends Pawn {
	
	public Queen(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}
	
    @Override
    public String getPawnType() {
    	return "Queen";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	int i;
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(row == newRow && col == newCol) 
    		return false;
    	
    	int dRow = newRow - row;
        int dCol = newCol - col;
    	
        boolean isStraight = (dRow == 0 || dCol == 0);
        boolean isDiagonal = Math.abs(dRow) == Math.abs(dCol);
        
        if (!isStraight && !isDiagonal)
            return false;
        
        if(isStraight) {
        	int stepRow = Integer.compare(newRow, row);   	
        	for(i = (row + stepRow); i != newRow; i += stepRow) {
        		if(board.getPawn(i, newCol) != null) {
        			return false;
        		}
        	}
        	
        	int stepCol = Integer.compare(newCol, col);
        	for(i = (col + stepCol); i != newCol; i += stepCol) {
        		if(board.getPawn(newRow, i) != null) {
        			return false;
        		}
        	}
        }
        
        if(isDiagonal) {
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
        }
        
        
    	if(target != null) {
    		if(target.isWhite == isWhite)
    			return false;
    	}
    	
    	return true;
    }
    
    
}

