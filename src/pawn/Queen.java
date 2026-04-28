package pawn;

import java.util.ArrayList;

import board.Board;
import move.Move;
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

    public ArrayList<Move> getLegalMoves(Board board){
    	
    	ArrayList<Move> legalMoves = new ArrayList<Move>();
    	Move move;
    	int currentRow, currentCol;
    	int[] directions = {-1, 0, 1};
    	
    	for(int directionRow : directions) {
    		for(int directionCol : directions) {
    			if(directionRow == 0 && directionCol == 0)
    				continue;
    			
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

