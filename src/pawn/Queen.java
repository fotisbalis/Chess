package pawn;

import board.Board;
import utils.MovesUtils;

public class Queen extends Pawn {
	
	public Queen(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.QUEEN;
    }
    
    public int getPawnValue() {
    	return 1000;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(row == newRow && col == newCol) 
    		return false;
    	
    	int dRow = newRow - row;
        int dCol = newCol - col;
    	
        boolean isStraight = (dRow == 0 || dCol == 0);
        boolean isDiagonal = Math.abs(dRow) == Math.abs(dCol);
        
        if (!isStraight && !isDiagonal)
            return false;
        
        if (isStraight) {
        	if(row == newRow) {
        		if(!MovesUtils.isSameRowValidMove(board, col, newRow, newCol))
        			return false;
        	}
        	else {
        		if(!MovesUtils.isSameColumnValidMove(board, row, newRow, newCol))
        			return false;
        	}
        }
        
        if (isDiagonal) {
        	if(!MovesUtils.isDiagonalValidMove(board, row, col, newRow, newCol))
        		return false;
        }
    	
    	if (target != null) {
    		if(target.getColor() == color)
    			return false;
    	}
    	
    	return true;
    }
    
    
}

