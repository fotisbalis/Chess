package pawn;

import java.util.ArrayList;

import board.*;
import move.Move;
import utils.*;

public class Soldier extends Pawn {
	
	protected int moveCount;
	protected int startingRow;
	protected int startingCol;
	
	public Soldier(int row, int col, PawnColor color) {
		super(row, col, color);
		this.moveCount = 0;
		this.startingRow = row;
		this.startingCol = col;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void incrementMoveCount() {
		moveCount++;
	}
	
	public void setMoveCount(int newMoveCount) {
		moveCount = newMoveCount;
	}
	
	public int getStartingRow() {
		return startingRow;
	}
	
	public int getStartingCol() {
		return startingCol;
	}
	
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.SOLDIER;
    }
    
    public int getPawnValue() {
    	return 100;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(newCol == col && target == null) {
    		
    		if(color == PawnColor.WHITE) {
    	        
    			if(newRow == row - 1)
    	            return true;

    	        if(!hasMoved() && newRow == row - 2 && MovesUtils.isSameColumnValidMove(board, row, newRow, col))
    	            return true;
    	    }
    	    
    		else {
    	        
    			if(newRow == row + 1)
    	            return true;

    	        if(!hasMoved() && newRow == row + 2 && MovesUtils.isSameColumnValidMove(board, row, newRow, col))
    	            return true;
    	    }    		
    	}
    	
    	else if(Math.abs(newCol - col) == 1) {
    		
    		if(target != null) {
    			
    			if(color == PawnColor.WHITE && newRow == (row - 1) && target.getColor() == PawnColor.BLACK)
    				return true;
    			
    			if(color == PawnColor.BLACK && newRow == (row + 1) && target.getColor() == PawnColor.WHITE)
    				return true;
    		}
    		
    		else {
    			
    			boolean isRightSide = EnPassantUtils.isRightSideEnPassant(col, newCol);
    			Soldier defender = EnPassantUtils.getDefender(board, this, isRightSide);
    			
    			if(EnPassantUtils.isMoveEnPassant(board, this, newRow, newCol) && EnPassantUtils.canEnPassantHappen(board, defender))
    		        return true;
    		}
    	}
    	
    	return false;
    }
    
    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	
    	int direction = 1;
    	
    	if(this.getColor() == PawnColor.WHITE)
    		direction = -1;
    	
    	move = new Move(row, col, row + direction, col);
    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    		legalMoves.add(move);
    		
    	move = new Move(row, col, row + 2 * direction, col);
    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    		legalMoves.add(move);
    	
    	move = new Move(row, col, row + direction, col + 1);
    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    		legalMoves.add(move);
    	
    	move = new Move(row, col, row + direction, col - 1);
    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
    		legalMoves.add(move);
    	
    	return legalMoves;
    }
    
}

