package save;

import java.io.Serializable;
import java.util.ArrayList;

import board.*;
import pawn.*;
import utils.*;

public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int halfMoveCounter;
	private final ArrayList<BoardState> boardStates;
	private final BoardState boardState;
	private final ArrayList<Pawn> captured;
	private final boolean highlightMoves;
	private final boolean autoQueenPromotion;
	private final PawnColor playerColor;
	private final int aiDepth;
	
	public GameState(Board board, PawnColor turnColor, int halfMoveCounter, ArrayList<BoardState> boardStates, ArrayList<Pawn> captured, boolean highlightMoves, boolean autoQueenPromotion) {
		this(board, turnColor, halfMoveCounter, boardStates, captured, highlightMoves, autoQueenPromotion, null, 5);
	}

	public GameState(Board board, PawnColor turnColor, int halfMoveCounter, ArrayList<BoardState> boardStates, ArrayList<Pawn> captured, boolean highlightMoves, boolean autoQueenPromotion, PawnColor playerColor, int aiDepth) {
		this.boardState = new BoardState(board, turnColor);
		this.boardStates = GameStateUtils.copyBoardStates(boardStates);
		this.halfMoveCounter = halfMoveCounter;
		this.captured = GameStateUtils.copyCaptured(captured);
		this.highlightMoves = highlightMoves;
		this.autoQueenPromotion = autoQueenPromotion;
		this.playerColor = playerColor;
		this.aiDepth = aiDepth;
	}
	
	public Board getBoard() {
		return boardState.getBoard().copyBoard();
	}
	
	public PawnColor getTurnColor() {
		return boardState.getTurnColor();
	}
	
	public ArrayList<BoardState> getBoardStates() {
		return GameStateUtils.copyBoardStates(boardStates);
	}
	
	public int getHalfMoveCounter() {
		return halfMoveCounter;
	}
	
	public ArrayList<Pawn> getCaptured() {
		return GameStateUtils.copyCaptured(captured);
	}
	
	public boolean isHighlightMovesEnabled() {
		return highlightMoves;
	}

	public boolean isAutoQueenPromotionEnabled() {
		return autoQueenPromotion;
	}

	public PawnColor getPlayerColor() {
		return playerColor;
	}

	public int getAIDepth() {
		return aiDepth;
	}
	
	
}
