package board;

import java.io.Serializable;

import pawn.PawnColor;

public class BoardState implements Serializable {
	private static final long serialVersionUID = 1L;

	Board board;
	PawnColor turnColor;
	
	public BoardState(Board board, PawnColor turnColor) {
		this.board = board.copyBoard();
		this.turnColor = turnColor;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public PawnColor getTurnColor() {
		return turnColor;
	}
	
}
