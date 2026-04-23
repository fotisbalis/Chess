package pawn;

import board.*;
import utils.CastlingUtils;

public class King extends Pawn {
	
	public King(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.KING;
    }
    
    public int getPawnValue() {
    	return 100;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	boolean normalKingMove = Math.abs(newCol - col) <= 1 && Math.abs(newRow - row) <= 1;
    	boolean castlingKingMove = newRow == row && Math.abs(newCol - col) == 2;
    	
    	if(!normalKingMove && !(castlingKingMove && CastlingUtils.canCastlingHappen(board, this, newCol)))
    		return false;
    	
    	if(target != null) {
    		if(target.getColor() == color)
    			return false;
    	}
    	
    	return true;
    }
    
    
}
