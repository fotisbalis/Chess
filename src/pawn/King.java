package pawn;

import board.*;
import move.Move;
import java.util.ArrayList;
import utils.CastlingUtils;
import utils.MovesUtils;

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

    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	int newRow, newCol;
    	int dRow, dCol;
    	
    	for(dRow = -1; dRow <= 1; dRow++) {
    		for(dCol = -1; dCol <= 1; dCol++) {
    			if(dRow == 0 && dCol == 0)
    				continue;
    			
    			newRow = row + dRow;
    			newCol = col + dCol;
    			
    			if(newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
    				move = new Move(row, col, newRow, newCol);
        	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
        	    		legalMoves.add(move);
    			}
    				
    		}
    	}
    	
    	if(col + 2 < 8) {
    		move = new Move(row, col, row, col + 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(col - 2 >= 0) {
    		move = new Move(row, col, row, col - 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	
    	return legalMoves;
    }
    
    
}
