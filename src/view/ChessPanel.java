package view;

import pawn.*;
import board.*;
import controller.*;
import utils.*;
import save.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class ChessPanel extends JPanel {
	
	private JButton[][] squares = new JButton[8][8];
	private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	private JPanel leftCaptured = new JPanel();
	private JPanel rightCaptured = new JPanel();
	private JPanel topPanel = new JPanel(new BorderLayout());
	private JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
	private JButton exitButton = new JButton("Exit Game");
	private JButton saveButton = new JButton("Save Game");
	private GUI gui;
	
	private Board board = new Board();
	private PawnColor turnColor;
	private int selectedRow = -1, selectedCol = -1;
	private ArrayList<Pawn> captured;
	private ArrayList<BoardState> boardStates;
	private int halfMoveCounter;
	private boolean isEnPassantSituation;
	
	GameState savedGameState;
	
	private Color backgroundColor = new Color(200, 200, 100);
	private boolean highlightMoves;
	
	public ChessPanel(GUI gui, boolean highlightMoves) {
		
		this.gui = gui;
		this.highlightMoves = highlightMoves;
		
		this.board.initializeBoard();
		this.turnColor = PawnColor.WHITE;
		this.halfMoveCounter = 0;
		this.isEnPassantSituation = this.board.hasEnPassantVulnerableSquare();
		this.captured = new ArrayList<Pawn>();
		this.boardStates = new ArrayList<BoardState>();
		
		buildPanel();
	}
	
	public ChessPanel(GUI gui, boolean highlightMoves, GameState savedGame) {
		
		this.gui = gui;
		this.highlightMoves = highlightMoves;
		
		this.board = savedGame.getBoard();
		this.turnColor = savedGame.getTurnColor();
		this.halfMoveCounter = savedGame.getHalfMoveCounter();
		this.isEnPassantSituation = this.board.hasEnPassantVulnerableSquare();
		this.captured = savedGame.getCaptured();
		this.boardStates = savedGame.getBoardStates();
		
		buildPanel();
	}
	
	private void saveGame() {
		
		savedGameState = new GameState(board, turnColor, halfMoveCounter, boardStates, captured, isEnPassantSituation);
		
		gui.setSavedGame(savedGameState);
		
	    JOptionPane.showMessageDialog(this, "Game saved.");
	}
	
	public void buildPanel() {
		
		setLayout(new BorderLayout());

		add(leftCaptured, BorderLayout.WEST);
		add(boardPanel, BorderLayout.CENTER);
		add(rightCaptured, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
		
		boardPanel.setBackground(backgroundColor);
		
		leftCaptured.setLayout(new BoxLayout(leftCaptured, BoxLayout.Y_AXIS));
		rightCaptured.setLayout(new BoxLayout(rightCaptured, BoxLayout.Y_AXIS));
		topPanel.setLayout(new BorderLayout());
		
		leftCaptured.setPreferredSize(new Dimension(100, 0));
		rightCaptured.setPreferredSize(new Dimension(100, 0));
		
		leftCaptured.setOpaque(true);
		rightCaptured.setOpaque(true);

		leftCaptured.setBackground(backgroundColor);
		rightCaptured.setBackground(backgroundColor);
		
		turnLabel.setPreferredSize(new Dimension(100, 50));			
		turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		turnLabel.setOpaque(true);
		turnLabel.setBackground(backgroundColor);
		turnLabel.setForeground(Color.WHITE);

		exitButton.setFont(new Font("Arial", Font.BOLD, 16));
		exitButton.setFocusPainted(false);
		exitButton.addActionListener(e -> GUIUtils.returnToMenu(this, gui));
		
		saveButton.setFont(new Font("Arial", Font.BOLD, 16));
		saveButton.setFocusPainted(false);
		saveButton.addActionListener(e -> saveGame());
		
		topPanel.setBackground(backgroundColor);
		topPanel.add(turnLabel, BorderLayout.CENTER);
		topPanel.add(exitButton, BorderLayout.WEST);
		topPanel.add(saveButton, BorderLayout.EAST);
		
		intitializeGUIBoard();
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.updateTurnLabel(turnLabel, backgroundColor, turnColor);
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
		
		GUIUtils.resetBoardColors(board, squares);
	}
	
	private void playTurn(int row, int col) {
		
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
				GUIUtils.highlightPossibleMoves(board, clickedPawn, squares);
			else
				GUIUtils.highlightSelectedPawn(clickedPawn, squares);
			
			return;
		}
		
		// Case 2: Player has chosen pawn but changed his mind and chooses a new pawn
		if(clickedPawn != null && clickedPawn.getColor() == turnColor) {
			
			GUIUtils.resetBoardColors(board, squares);
			
			selectedRow = row;
			selectedCol = col;
			
			if(highlightMoves)
				GUIUtils.highlightPossibleMoves(board, clickedPawn, squares);
			else
				GUIUtils.highlightSelectedPawn(clickedPawn, squares);
			
			return;
		}
		
		//Case 3: Player has chosen pawn but makes invalid move
		Pawn selectedPawn = board.getPawn(selectedRow, selectedCol);
		
		boolean valid = MovesUtils.isLegalMove(board, selectedPawn, row, col);
		
		if (selectedPawn == null || !valid) {
			selectedRow = -1;
			selectedCol = -1;
			
			GUIUtils.resetBoardColors(board, squares);
			
			return;
		}
		
		//Case 4: Player has chosen pawn and makes valid move
		Pawn target = board.getPawn(row, col);
		
		if(target != null) {	
			captured.add(target);
			halfMoveCounter = 0;
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
		
		
		isEnPassantSituation = board.hasEnPassantVulnerableSquare();
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.updateCapturedPawns(captured, leftCaptured, rightCaptured);
		GUIUtils.resetBoardColors(board, squares);
		
		PromotionUtils.handlePromotion(this, board, turnColor);

		boardStates.add(new BoardState(board, turnColor.opposite()));
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.resetBoardColors(board, squares);
		
		if(Controller.isGameOver(board, turnColor, boardStates, halfMoveCounter)) {
			JOptionPane.showMessageDialog(this, GUIUtils.gameOverMessage(board, turnColor, boardStates, halfMoveCounter));
			gui.showStartScreen();
		}
		
		turnColor = turnColor.opposite();
		selectedRow = -1;
		selectedCol = -1;
		
		GUIUtils.updateTurnLabel(turnLabel, backgroundColor, turnColor);
	}
	
	
	
	
	
}
