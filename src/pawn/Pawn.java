package pawn;

import board.*;
import java.io.Serializable;

public abstract class Pawn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int row, col;
	protected PawnColor color;
	protected boolean hasMoved;
	
	public Pawn(int row, int col, PawnColor color) {
		this.row = row;
		this.col = col;
		this.color = color;
		this.hasMoved = false;
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
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public abstract PawnType getPawnType();
	
	public abstract boolean isValidMove(Board board, int newRow, int newCol);
	
}

