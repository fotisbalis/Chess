package pawn;

import board.Board;
import utils.MovesUtils;

public class Rook extends Pawn {
	
	public Rook(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.ROOK;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(row != newRow && col != newCol) return false;
    	
    	if(row == newRow) {
    		if(!MovesUtils.isSameRowValidMove(board, col, newRow, newCol))
    			return false;
    	}
    	else {
    		if(!MovesUtils.isSameColumnValidMove(board, row, newRow, newCol))
    			return false;
    	}
    	
    	if(target != null)
    		if(target.getColor() == color)
    			return false;
    	
    	return true;
    }
    
}

