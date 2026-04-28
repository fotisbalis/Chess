package pawn;

import java.util.ArrayList;

import board.Board;
import move.Move;
import utils.MovesUtils;

public class Rook extends Pawn {
	
	public Rook(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.ROOK;
    }
    
    public int getPawnValue() {
    	return 400;
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
    
    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	
    	int i;
    	for(i = 0; i < 8; i++) {
    		if(i != col) {
    			move = new Move(row, col, row, i);
    	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    	    		legalMoves.add(move);
    		}
    		if(i != row) {
    			move = new Move(row, col, i, col);
    	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    	    		legalMoves.add(move);
    		}
    	}
    	
    	return legalMoves;
    }
}

