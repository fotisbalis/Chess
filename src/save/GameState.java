package save;

import java.util.ArrayList;

import board.*;
import pawn.*;

public class GameState {

	protected int halfMoveCounter;
	protected ArrayList<BoardState> boardStates;
	protected BoardState boardState;
	protected ArrayList<Pawn> captured;
	protected boolean isEnPassantSituation;
	
	public GameState(Board board, PawnColor turnColor, int halfMoveCounter, ArrayList<BoardState> boardStates, ArrayList<Pawn> captured, boolean isEnPassantSituation) {
		this.boardState = new BoardState(board, turnColor);
		this.boardStates = boardStates;
		this.halfMoveCounter = halfMoveCounter;
		this.captured = captured;
		this.isEnPassantSituation = isEnPassantSituation;
	}
	
	public Board getBoard() {
		return boardState.getBoard();
	}
	
	public PawnColor getTurnColor() {
		return boardState.getTurnColor();
	}
	
	public ArrayList<BoardState> getBoardStates() {
		return boardStates;
	}
	
	public int getHalfMoveCounter() {
		return halfMoveCounter;
	}
	
	public ArrayList<Pawn> getCaptured() {
		return captured;
	}
	
	public boolean getEnPassantSituation() {
		return isEnPassantSituation;
	}
	
}
