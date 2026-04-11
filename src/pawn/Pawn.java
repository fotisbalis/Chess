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
	
	public boolean[][] possibleMoves(Board board) {
		boolean[][] validMoves = new boolean[8][8];
		int r, c;		
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				validMoves[r][c] = false;
				
				if(row == r && col == c)
					continue;
				
				if(isValidMove(board, r, c)) {
					validMoves[r][c] = true;
				}
			}
		}
		
		return validMoves;
	}
	
	public boolean canMove(Board board) {
		int r, c;
		boolean[][] moves = possibleMoves(board);
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				if(moves[r][c]) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}

