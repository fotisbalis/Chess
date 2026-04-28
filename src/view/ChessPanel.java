package view;

import pawn.*;
import board.*;
import controller.*;
import utils.*;
import save.*;
import ai.*;
import move.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class ChessPanel extends JPanel {
	
	private JButton[][] squares = new JButton[8][8];
	private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	private JPanel leftCaptured = new JPanel();
	private JPanel rightCaptured = new JPanel();
	private JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
	private JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
	private JButton exitMenuButton = new JButton("Exit to Menu");
	private JButton exitDesktopButton = new JButton("Exit to Desktop");
	private JButton saveButton = new JButton("Save Game");
	private JButton restartButton = new JButton("Restart Game");
	private GUI gui;
	
	private Board board = new Board();
	private PawnColor turnColor;
	private int selectedRow = -1, selectedCol = -1;
	private ArrayList<Pawn> captured;
	private ArrayList<BoardState> boardStates;
	private int halfMoveCounter;
	
	private Color backgroundColor = new Color(200, 200, 100);
	private boolean highlightMoves;
	private boolean singlePlayer;
	private PawnColor aiColor = PawnColor.BLACK;
	private PawnColor playerColor = PawnColor.WHITE;
	private boolean aiThinking = false;
	private int aiMoveCounter;
	private int aiDepth = 5;
	private Move lastAIMove = null;
	private Move secondLastAIMove = null;
	
	public ChessPanel(GUI gui, boolean highlightMoves) {
		this(gui, highlightMoves, false);
	}

	public ChessPanel(GUI gui, boolean highlightMoves, boolean singlePlayer) {
		this(gui, highlightMoves, singlePlayer, 5, PawnColor.WHITE);
	}

	public ChessPanel(GUI gui, boolean highlightMoves, boolean singlePlayer, int aiDepth) {
		this(gui, highlightMoves, singlePlayer, aiDepth, PawnColor.WHITE);
	}

	public ChessPanel(GUI gui, boolean highlightMoves, boolean singlePlayer, int aiDepth, PawnColor playerColor) {
		
		this.gui = gui;
		this.highlightMoves = highlightMoves;
		this.singlePlayer = singlePlayer;
		this.aiDepth = aiDepth;
		this.playerColor = playerColor;
		if(singlePlayer)
			this.aiColor = playerColor.opposite();
		
		this.board.initializeBoard();
		this.turnColor = PawnColor.WHITE;
		this.halfMoveCounter = 0;
		this.captured = new ArrayList<Pawn>();
		this.boardStates = new ArrayList<BoardState>();
		this.boardStates.add(new BoardState(board, turnColor));
		
		buildPanel();
		startAITurnIfNeeded();
	}
	
	public ChessPanel(GUI gui, boolean highlightMoves, GameState savedGame) {
		this(gui, highlightMoves, savedGame, false);
	}

	public ChessPanel(GUI gui, boolean highlightMoves, GameState savedGame, boolean singlePlayer) {
		
		this.gui = gui;
		this.highlightMoves = savedGame.isHighlightMovesEnabled();
		this.singlePlayer = singlePlayer;
		this.gui.setHighlightMoves(savedGame.isHighlightMovesEnabled());
		this.gui.setAutoQueenPromotion(savedGame.isAutoQueenPromotionEnabled());
		
		this.board = savedGame.getBoard();
		this.turnColor = savedGame.getTurnColor();
		this.halfMoveCounter = savedGame.getHalfMoveCounter();
		this.captured = savedGame.getCaptured();
		this.boardStates = savedGame.getBoardStates();
		this.aiDepth = savedGame.getAIDepth();
		
		if(singlePlayer) {
			PawnColor savedPlayerColor = savedGame.getPlayerColor();
			this.playerColor = savedPlayerColor != null ? savedPlayerColor : PawnColor.WHITE;
			this.aiColor = this.playerColor.opposite();
		}
		
		buildPanel();
		startAITurnIfNeeded();
	}
	
	public void buildPanel() {
		
		setLayout(new BorderLayout());

		add(leftCaptured, BorderLayout.WEST);
		add(boardPanel, BorderLayout.CENTER);
		add(rightCaptured, BorderLayout.EAST);
		add(turnLabel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		boardPanel.setBackground(backgroundColor);
		
		leftCaptured.setLayout(new BoxLayout(leftCaptured, BoxLayout.Y_AXIS));
		rightCaptured.setLayout(new BoxLayout(rightCaptured, BoxLayout.Y_AXIS));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 4));
		
		leftCaptured.setPreferredSize(new Dimension(100, 0));
		rightCaptured.setPreferredSize(new Dimension(100, 0));
		
		leftCaptured.setOpaque(true);
		rightCaptured.setOpaque(true);
		bottomPanel.setOpaque(true);

		leftCaptured.setBackground(backgroundColor);
		rightCaptured.setBackground(backgroundColor);
		bottomPanel.setBackground(backgroundColor);
		
		turnLabel.setPreferredSize(new Dimension(100, 50));			
		turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		turnLabel.setOpaque(true);
		turnLabel.setBackground(backgroundColor);
		turnLabel.setForeground(Color.WHITE);

		GUIUtils.initializeButton(exitMenuButton);
		exitMenuButton.addActionListener(e -> GUIUtils.returnToMenu(this, gui));
		
		GUIUtils.initializeButton(exitDesktopButton);
		exitDesktopButton.addActionListener(e -> GUIUtils.exitToDesktop(this));
		
		GUIUtils.initializeButton(saveButton);
		saveButton.addActionListener(e -> saveGame());

		GUIUtils.initializeButton(restartButton);
		restartButton.addActionListener(e -> restartGame());
		
		bottomPanel.add(exitMenuButton);
		bottomPanel.add(exitDesktopButton);
		bottomPanel.add(saveButton);
		bottomPanel.add(restartButton);
		
		intitializeGUIBoard();
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.updateCapturedPawns(captured, leftCaptured, rightCaptured);
		GUIUtils.updateTurnLabel(turnLabel, backgroundColor, turnColor);
	}
	
	private void saveGame() {
		GameState savedGameState = new GameState(board, turnColor, halfMoveCounter, boardStates, captured, highlightMoves, gui.isAutoQueenPromotion(), singlePlayer ? playerColor : null, aiDepth);
		
		int choice;
		
		if(gui.hasSavedGame(singlePlayer))
			choice = JOptionPane.showConfirmDialog(this, "Save current game? Previous save will be overwritten.", "Save Game", JOptionPane.YES_NO_OPTION);
		else
			choice = JOptionPane.showConfirmDialog(this, "Save current game?", "Save Game", JOptionPane.YES_NO_OPTION);
		
		if(choice == JOptionPane.YES_OPTION) {
			try {
				SaveLoadUtils.saveGame(savedGameState, singlePlayer);
				gui.setSavedGame(savedGameState, singlePlayer);
				JOptionPane.showMessageDialog(this, "Game saved.");
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(this, "Game could not be saved.");
			}
		}	
	}

	private void restartGame() {
		int choice = JOptionPane.showConfirmDialog(this, "Restart the game with the same settings? Any unsaved progress will be lost.", "Restart Game", JOptionPane.YES_NO_OPTION);

		if(choice != JOptionPane.YES_OPTION) {
			return;
		}

		if(singlePlayer) {
			gui.startSinglePlayerGame(aiDepth, playerColor);
			return;
		}

		gui.showGameScreen(highlightMoves);
	}

	private void handleGameOver() {
		String[] options = {"Restart Game", "Return to Menu"};
		int choice = JOptionPane.showOptionDialog(
				this,
				GUIUtils.gameOverMessage(board, turnColor, boardStates, halfMoveCounter),
				"Game Over",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]
		);

		if(choice == 0) {
			if(singlePlayer) {
				gui.startSinglePlayerGame(aiDepth, playerColor);
				return;
			}

			gui.showGameScreen(highlightMoves);
			return;
		}

		gui.showStartScreen();
	}
	
	private void intitializeGUIBoard() {
		int row, col;
		
		for(row = 0; row < 8; row++) {
			for(col = 0; col < 8; col++) {
				
				JButton button = new JButton();
				
				int r = row, c = col;
				
				button.addActionListener(e -> playTurn(r, c));
				
				squares[row][col] = button;
				boardPanel.add(button);
			}
		}
		
		GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
	}
	
	private void playTurn(int row, int col) {

		GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
		
		if(singlePlayer && turnColor == aiColor && !aiThinking) {
			return;
		}
		
		Pawn clickedPawn = board.getPawn(row, col);
			
		// Case 1: Player chooses a pawn to play
		if(selectedRow == -1) {
	
			if(clickedPawn == null) {
				return;
			}
				
			if(clickedPawn.getColor() != turnColor) {
				return;
			}
			
			selectedRow = row;
			selectedCol = col;
			
			if(highlightMoves)
				GUIUtils.highlightPossibleMoves(board, clickedPawn, squares, gui.isBrightColorsEnabled());
			else
				GUIUtils.highlightSquare(clickedPawn.getRow(), clickedPawn.getCol(), squares, Color.ORANGE);
			
			return;
		}
		
		// Case 2: Player has chosen pawn but changed his mind and chooses a new pawn
		if(clickedPawn != null && clickedPawn.getColor() == turnColor) {
			
			GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
			
			selectedRow = row;
			selectedCol = col;
			
			if(highlightMoves)
				GUIUtils.highlightPossibleMoves(board, clickedPawn, squares, gui.isBrightColorsEnabled());
			else
				GUIUtils.highlightSquare(row, col, squares, Color.ORANGE);
			
			return;
		}
		
		//Case 3: Player has chosen pawn but makes invalid move
		Pawn selectedPawn = board.getPawn(selectedRow, selectedCol);
		
		boolean valid = MovesUtils.isLegalMove(board, selectedPawn, row, col);
		
		if (selectedPawn == null || !valid) {
			selectedRow = -1;
			selectedCol = -1;
			
			GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
			
			return;
		}
		
		//Case 4: Player has chosen pawn and makes valid move
		Pawn target = board.getPawn(row, col);
		
		if(target != null) {	
			captured.add(target);
			halfMoveCounter = 0;
			boardStates.clear();
		}
		else if(selectedPawn instanceof Soldier)
			halfMoveCounter = 0;
		else
			halfMoveCounter++;
		
		
		if(selectedPawn instanceof Soldier && EnPassantUtils.isMoveEnPassant(board, (Soldier) selectedPawn, row, col)) {
			Pawn capturedPawn = board.getPawn(selectedPawn.getRow(), col);
			
			captured.add(capturedPawn);
			
			Controller.makeEnPassantMove(board, (Soldier) selectedPawn, row, col);
		}
		else if(selectedPawn instanceof King && CastlingUtils.isMoveCastling((King) selectedPawn, row, col))
			Controller.makeCastlingMove(board, (King) selectedPawn, CastlingUtils.isKingsideCastling(selectedPawn.getCol(), col));
		else
			Controller.makeMove(board, selectedPawn,  row, col);
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.updateCapturedPawns(captured, leftCaptured, rightCaptured);
		GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
		
		int promotionRow = selectedPawn.getColor() == PawnColor.WHITE ? 0 : 7;
		if(selectedPawn instanceof Soldier && row == promotionRow)
			PromotionUtils.handlePromotion(this, board, turnColor, gui.isAutoQueenPromotion(), singlePlayer, aiColor);

		boardStates.add(new BoardState(board, turnColor.opposite()));
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.resetBoardColors(board, squares, gui.isBrightColorsEnabled());
		
		if(Controller.isGameOver(board, turnColor, boardStates, halfMoveCounter)) {
			handleGameOver();
			return;
		}
		
		turnColor = turnColor.opposite();
		selectedRow = -1;
		selectedCol = -1;
		
		GUIUtils.updateTurnLabel(turnLabel, backgroundColor, turnColor);

		startAITurnIfNeeded();
	}
	
	public void playAITurn() {
		
		Move move = AI.chooseMove(board, aiColor, aiDepth, aiMoveCounter, secondLastAIMove);

		if(move == null) {
			return;
		}
		
	    playTurn(move.getStartingRow(), move.getStartingCol());
	    playTurn(move.getTargetRow(), move.getTargetCol());
	    
	    aiMoveCounter++;
	    secondLastAIMove = lastAIMove;
	    lastAIMove = move;
	    
	    Color softBlue = new Color(100, 149, 237);
	    GUIUtils.highlightSquare(move.getTargetRow(), move.getTargetCol(), squares, softBlue);
	}

	private void startAITurnIfNeeded() {
		if(singlePlayer && turnColor == aiColor && !aiThinking) {
			aiThinking = true;

			javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
				playAITurn();
				aiThinking = false;
			});

			timer.setRepeats(false);
			timer.start();
		}
	}
	
}
