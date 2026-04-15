package board;

import pawn.PawnColor;

public class BoardState {

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
