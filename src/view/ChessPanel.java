package view;

import pawn.*;
import board.*;
import controller.Controller;
import utils.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class ChessPanel extends JPanel {
	
	private JButton[][] squares = new JButton[8][8];
	private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	private JPanel leftCaptured = new JPanel();
	private JPanel rightCaptured = new JPanel();
	private JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
	
	private Board board = new Board();
	private PawnColor turnColor = PawnColor.WHITE;
	private int selectedRow = -1, selectedCol = -1;
	private ArrayList<Pawn> captured = new ArrayList<Pawn>();
	private ArrayList<BoardState> BoardStates = new ArrayList<BoardState>();
	private int halfMoveCounter = 0;
	
	private Color backgroundColor = new Color(200, 200, 100);
	
	public ChessPanel() {
		
		this.board.initializeBoard();
		
		setLayout(new BorderLayout());

		add(leftCaptured, BorderLayout.WEST);
		add(boardPanel, BorderLayout.CENTER);
		add(rightCaptured, BorderLayout.EAST);
		add(turnLabel, BorderLayout.NORTH);
		
		boardPanel.setBackground(backgroundColor);
		
		leftCaptured.setLayout(new BoxLayout(leftCaptured, BoxLayout.Y_AXIS));
		rightCaptured.setLayout(new BoxLayout(rightCaptured, BoxLayout.Y_AXIS));
		turnLabel.setLayout(new BoxLayout(turnLabel, BoxLayout.Y_AXIS));
		
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
		
		intitializeGUIBoard();
		GUIUtils.refreshGUIBoard(board, squares);
	}
	
	private void intitializeGUIBoard(){
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
			
			GUIUtils.highlightPossibleMoves(board, clickedPawn, squares);
			
			return;
		}
		
		// Case 2: Player has chosen pawn but changed his mind and chooses a new pawn
		if(clickedPawn != null && clickedPawn.getColor() == turnColor) {
			
			GUIUtils.resetBoardColors(board, squares);
			
			selectedRow = row;
			selectedCol = col;
			
			GUIUtils.highlightPossibleMoves(board, clickedPawn, squares);
			
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
		Controller.makeMove(board, captured, halfMoveCounter, selectedPawn,  row, col);
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.updateCapturedPawns(captured, leftCaptured, rightCaptured);
		GUIUtils.resetBoardColors(board, squares);
		
		PromotionUtils.handlePromotion(this, board, turnColor);

		BoardStates.add(new BoardState(board, turnColor.opposite()));
		
		GUIUtils.refreshGUIBoard(board, squares);
		GUIUtils.resetBoardColors(board, squares);
		
		if(Controller.isGameOver(board, turnColor, BoardStates, halfMoveCounter)) {
			JOptionPane.showMessageDialog(this, GUIUtils.gameOverMessage(board, turnColor, BoardStates, halfMoveCounter));
			System.exit(0);
		}
					
		turnColor = turnColor.opposite();
		selectedRow = -1;
		selectedCol = -1;
		
		GUIUtils.updateTurnLabel(turnLabel, backgroundColor, turnColor);
	}
	
	
	
	
	
}
