package utils;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import board.*;
import controller.*;
import pawn.*;
import view.*;
import move.*;

public class GUIUtils {
	
	public static void refreshGUIBoard(Board board, JButton[][] squares) {
		int row, col;
		
		for(row = 0; row < 8; row++) {
			for(col = 0; col < 8; col++) {
				
				Pawn pawn = board.getPawn(row, col);
				
				if(pawn == null) {
					squares[row][col].setIcon(null);
				}
				else {
					ImageIcon img;
					
					if(pawn instanceof Soldier)
						img = ImagesUtils.resizeIcon(ImagesUtils.getImage(pawn), 20, 40);
					if(pawn instanceof Queen || pawn instanceof King)
						img = ImagesUtils.resizeIcon(ImagesUtils.getImage(pawn), 50, 90);
					else
						img = ImagesUtils.resizeIcon(ImagesUtils.getImage(pawn), 50, 80);
					
					squares[row][col].setIcon(img);
				}
			}
		}
	}
	
	public static void updateCapturedPawns(ArrayList<Pawn> captured, JPanel leftCaptured, JPanel rightCaptured) {
		
		leftCaptured.removeAll();
	    rightCaptured.removeAll();
	    
	    leftCaptured.add(Box.createVerticalGlue());
	    rightCaptured.add(Box.createVerticalGlue());
	    
	    ArrayList<Pawn> whiteCaptured = CapturedUtils.getCurrentPlayerCaptured(captured, PawnColor.WHITE);
	    ArrayList<Pawn> blackCaptured = CapturedUtils.getCurrentPlayerCaptured(captured, PawnColor.BLACK);

	    CapturedUtils.addCapturedGroup(leftCaptured, blackCaptured);
	    CapturedUtils.addCapturedGroup(rightCaptured, whiteCaptured);
	    
	    leftCaptured.add(Box.createVerticalGlue());
	    rightCaptured.add(Box.createVerticalGlue());
	    
	}
	
	public static void highlightPossibleMoves(Board board, Pawn pawn, JButton[][] squares, boolean brightColors) {
		
		ArrayList<Move> moves = pawn.getLegalMoves(board);
		
		Color orange = brightColors ? Color.ORANGE : new Color(205, 170, 90);
		Color green = brightColors ? Color.GREEN : new Color(60, 80, 60);
		
		GUIUtils.highlightSquare(pawn.getRow(), pawn.getCol(), squares, orange);
		
		for(Move move : moves) {
			squares[move.getTargetRow()][move.getTargetCol()].setBackground(green);
		}
	}
	
	public static void highlightSquare(int row, int col, JButton[][] squares, Color color) {
		
		squares[row][col].setBackground(color);
	}
	
	public static void resetBoardColors(Board board, JButton[][] squares, boolean brightColors) {
		int r, c;
		
		Color light = new Color(200, 200, 190);
		Color dark  = new Color(119, 148, 85);
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				if((r + c) % 2 == 0)
                    squares[r][c].setBackground(light);
                else
                    squares[r][c].setBackground(dark);
			}
		}
		
		Color red = brightColors ? Color.RED : new Color(190, 60, 60);
		
		GUIUtils.markKingInDanger(board, squares, red);
	}
	
	private static void markKingInDanger(Board board, JButton[][] squares, Color color) {
		
		King whiteKing = KingCheckUtils.findKing(board, PawnColor.WHITE);		
		King blackKing = KingCheckUtils.findKing(board, PawnColor.BLACK);
		
		if(KingCheckUtils.isKingInDanger(board, PawnColor.WHITE))
			squares[whiteKing.getRow()][whiteKing.getCol()].setBackground(color);
		
		if(KingCheckUtils.isKingInDanger(board, PawnColor.BLACK))
			squares[blackKing.getRow()][blackKing.getCol()].setBackground(color);
		
	}

	public static void updateTurnLabel(JLabel turnLabel, Color backgroundColor, PawnColor turnColor) {
		
		turnLabel.setOpaque(true);
		turnLabel.setBackground(backgroundColor);
		
		if (turnColor == PawnColor.WHITE) {
			turnLabel.setText("White's Turn");
			turnLabel.setForeground(Color.WHITE);
		}
	        
		else {
	        turnLabel.setText("Black's Turn");
	        turnLabel.setForeground(Color.BLACK);
		}
	
	}
	
	public static Pawn choosePawnForPromotion(Component parent, ArrayList<Pawn> pawnsForPromotion) {
		
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Choose Promotion", true);
		dialog.setLayout(new BorderLayout());

		JPanel piecesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		piecesPanel.setBackground(new Color(200, 200, 100));

		JLabel titleLabel = new JLabel("Choose a pawn to promote", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(200, 200, 100));
		titleLabel.setForeground(Color.WHITE);

		final Pawn[] selectedPawn = new Pawn[1];

		for(Pawn pawn : pawnsForPromotion) {
			JButton button = new JButton();
			
			ImageIcon img = ImagesUtils.resizeIcon(ImagesUtils.getImage(pawn), 40, 80);
			
			button.setIcon(img);
			button.setFocusPainted(false);
			button.setBackground(new Color(200, 200, 190));
			
			button.addActionListener(e -> {
				selectedPawn[0] = pawn;
				dialog.dispose();
			});
			
			piecesPanel.add(button);
		}

		dialog.add(titleLabel, BorderLayout.NORTH);
		dialog.add(piecesPanel, BorderLayout.CENTER);

		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);

		return selectedPawn[0];
		
	}
	
	public static String gameOverMessage(Board board, PawnColor turnColor, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		if(Controller.isGameOver(board, turnColor, BoardStates, halfMoveCounter)) {			
			if(GameCheckUtils.isCheckMate(board, turnColor.opposite())) {
				String winner = turnColor == PawnColor.WHITE ? "White" : "Black";
				return "Checkmate! " + winner + " wins!";
			}
			
			if(GameCheckUtils.isThreefoldRepetition(BoardStates))
				return "Threefold Repetition. Tie game!";
			
			if(GameCheckUtils.is50MoveRule(halfMoveCounter))
				return "50 Move rule occured. Tie game!";
			
			if(GameCheckUtils.isStaleMate(board, turnColor))
				return "Stalemate occured. Tie game!";
			
			if(GameCheckUtils.isInsufficientMaterial(board))
				return "Insufficient material. Tie game!";
		}
		
		return null;
	}
	
	public static void returnToMenu(Component parent, GUI gui) {
		if(gui == null) {
			return;
		}
		
		int choice = JOptionPane.showConfirmDialog(parent, "Exit current game? Any unsaved progress will be lost.", "Exit to Menu", JOptionPane.YES_NO_OPTION);
		
		if(choice == JOptionPane.YES_OPTION) {
			gui.showStartScreen();
		}
	}
	
	public static void exitToDesktop(Component parent) {
		
		int choice = JOptionPane.showConfirmDialog(parent, "Exit current game? Any unsaved progress will be lost.", "Exit to Desktop", JOptionPane.YES_NO_OPTION);
		
		if(choice == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public static void initializeCheckBox(JCheckBox checkBox) {
		
		checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setOpaque(false);
        checkBox.setForeground(new Color(240, 240, 240));
        checkBox.setFont(new Font("SansSerif", Font.BOLD, 16));
	}
	
	public static void initializeButton(JButton button) {
		
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.setFocusPainted(false);
		button.setMargin(new Insets(2, 10, 2, 10));
		
	}
	
}
