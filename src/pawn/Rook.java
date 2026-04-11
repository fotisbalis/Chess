package pawn;

import board.Board;

public class Rook extends Pawn {
	
	public Rook(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}
	
    @Override
    public String getPawnType() {
    	return "Rook";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	int i;
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(row != newRow && col != newCol) return false;
    	
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
    	
    	if(target != null) {
    		if(target.isWhite == isWhite)
    			return false;
    	}
    	
    	return true;
    }
    
}

