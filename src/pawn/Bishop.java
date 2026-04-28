package pawn;

import java.util.ArrayList;

import board.*;
import move.Move;
import utils.MovesUtils;

public class Bishop extends Pawn {
	
	public Bishop(int row, int col, PawnColor color) {
		super(row, col, color);
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.BISHOP;
    }
    
    public int getPawnValue() {
    	return 300;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
       	
    	if(Math.abs(newRow - row) != Math.abs(newCol - col)) return false;
   	 
    	if(!MovesUtils.isDiagonalValidMove(board, row, col, newRow, newCol))
    		return false;
    	
    	if(target != null) {
    		if(target.getColor() == color) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	int currentRow, currentCol;
    	int[] directions = {-1, 1};
    	
    	for(int directionRow : directions) {
    		for(int directionCol : directions) {
    			currentRow = row + directionRow;
    			currentCol = col + directionCol;
    			
    			while(currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
    				
    				move = new Move(row, col, currentRow, currentCol);
        	    	if(MovesUtils.isLegalMove(board, board.getPawn(row, col), move.getTargetRow(), move.getTargetCol()))
        	    		legalMoves.add(move);
    				
    				if(board.getPawn(currentRow, currentCol) != null)
    					break;
    				
    				currentRow += directionRow;
    				currentCol += directionCol;
    			}
    		}
    	}
    	
    	return legalMoves;
    }
    
}

