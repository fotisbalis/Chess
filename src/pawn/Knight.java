package pawn;

import java.util.ArrayList;

import board.*;
import move.Move;
import utils.MovesUtils;

public class Knight extends Pawn {
	
	public Knight(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.KNIGHT;
    }
    
    public int getPawnValue() {
    	return 300;
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
    
    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	
    	if(row < 6 && col < 7) {
    		move = new Move(row, col, row + 2, col + 1);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row < 6 && col > 0) {
    		move = new Move(row, col, row + 2, col - 1);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row < 7 && col < 6) {
    		move = new Move(row, col, row + 1, col + 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row < 7 && col > 1) {
    		move = new Move(row, col, row + 1, col - 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row > 1 && col < 7) {
    		move = new Move(row, col, row - 2, col + 1);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row > 1 && col > 0) {
    		move = new Move(row, col, row - 2, col - 1);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row > 0 && col < 6) {
    		move = new Move(row, col, row - 1, col + 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	if(row > 0 && col > 1) {
    		move = new Move(row, col, row - 1, col - 2);
	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
	    		legalMoves.add(move);
    	}
    	
    	return legalMoves;
    }
    
}

