package pawn;

import board.*;

public class Soldier extends Pawn {
	protected boolean isFirstMove;	
	
	public Soldier(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		setFirstMove(true);
	}
	
	public void setFirstMove(boolean firstMove) {
		this.isFirstMove = firstMove;
	}
	
	public boolean isFirstMove() {
		return isFirstMove;
	}
	
    @Override
    public String getPawnType() {
    	return "Soldier";
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(isWhite) {
    		if(isFirstMove) {
    			if(newCol == col && (newRow == (row - 1) || newRow == (row - 2)) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row - 1) && target != null) {
    				if(!target.isWhite) {
    					return true;
    				}    				
    			}
    		}
    		else {
    			if(newCol == col && newRow == (row - 1) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row - 1) && target != null) {
    				if(!target.isWhite) {
    					return true;
    				}    				
    			}
    		}
    	}
    	else {
    		if(isFirstMove) {
    			if(newCol == col && (newRow == (row + 1) || newRow == (row + 2)) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row + 1) && target != null) {
    				if(target.isWhite) {
    					return true;
    				}    				
    			}
    		}
    		else {
    			if(newCol == col && newRow == (row + 1) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row + 1) && target != null) {
    				if(target.isWhite) {
    					return true;
    				}    				
    			}
    		}
    	}
    	
    	return false;
    }
    
}

