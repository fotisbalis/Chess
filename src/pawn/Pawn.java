package pawn;

import board.*;

public abstract class Pawn {
	protected int row, col;
	protected PawnColor color;
	
	public Pawn(int row, int col, PawnColor color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public PawnColor getColor() {
		return color;
	}

	public boolean isColor(PawnColor color) {
		return this.color == color;
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

