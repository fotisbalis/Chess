package board;

public class BoardState {

	Board board;
	boolean whiteTurn;
	
	public BoardState(Board board, boolean whiteTurn) {
		this.board = board.copyBoard();
		this.whiteTurn = whiteTurn;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public boolean getWhiteTurn() {
		return whiteTurn;
	}
	
}
