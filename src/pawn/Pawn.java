package pawn;

import board.*;

public abstract class Pawn {
	protected int row, col;
	protected boolean isWhite;
	
	public Pawn(int row, int col, boolean isWhite) {
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public abstract String getPawnType();
	
	public abstract boolean isValidMove(Board board, int newRow, int newCol);
	
	
	
}

