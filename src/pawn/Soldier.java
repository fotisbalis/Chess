package pawn;

import board.*;

public class Soldier extends Pawn {
	protected boolean isFirstMove;	
	
	public Soldier(int row, int col, PawnColor color) {
		super(row, col, color);
		setFirstMove(true);
	}
	
	public void setFirstMove(boolean firstMove) {
		this.isFirstMove = firstMove;
	}
	
	public boolean isFirstMove() {
		return isFirstMove;
    }
	
    @Override
    public PawnType getPawnType() {
    	return PawnType.SOLDIER;
    }
    
    public boolean isValidMove(Board board, int newRow, int newCol) {
    	
    	if(newRow < 0 || newRow > 7 || newCol < 0 || newCol >7)
    		return false;
    	
    	Pawn target = board.getPawn(newRow, newCol);
    	
    	if(color == PawnColor.WHITE) {
    		if(isFirstMove) {
    			if(newCol == col && (newRow == (row - 1) || newRow == (row - 2)) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row - 1) && target != null) {
    				if(target.getColor() == PawnColor.BLACK) {
    					return true;
    				}    				
    			}
    		}
    		else {
    			if(newCol == col && newRow == (row - 1) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row - 1) && target != null) {
    				if(target.getColor() == PawnColor.BLACK) {
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
    				if(target.getColor() == PawnColor.WHITE) {
    					return true;
    				}    				
    			}
    		}
    		else {
    			if(newCol == col && newRow == (row + 1) && target == null) {
    				return true;
    			}
    			else if((newCol == (col + 1) || newCol == (col - 1)) && newRow == (row + 1) && target != null) {
    				if(target.getColor() == PawnColor.WHITE) {
    					return true;
    				}    				
    			}
    		}
    	}
    	
    	return false;
    }
    
}

