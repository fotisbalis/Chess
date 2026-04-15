package utils;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import board.*;
import controller.Controller;
import pawn.*;

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
	    
	    ArrayList<Pawn> whiteCaptured = CapturedUtils.getCurrentPlayerCaptured(captured, true);
	    ArrayList<Pawn> blackCaptured = CapturedUtils.getCurrentPlayerCaptured(captured, false);

	    CapturedUtils.addCapturedGroup(leftCaptured, blackCaptured);
	    CapturedUtils.addCapturedGroup(rightCaptured, whiteCaptured);
	    
	    leftCaptured.add(Box.createVerticalGlue());
	    rightCaptured.add(Box.createVerticalGlue());
	    
	}
	
	public static void highlightPossibleMoves(Board board, Pawn pawn, JButton[][] squares) {
		int r, c;
		boolean[][] moves = MovesUtils.possibleLegalMoves(board, pawn);
		
		squares[pawn.getRow()][pawn.getCol()].setBackground(Color.ORANGE);
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				if(moves[r][c])
					squares[r][c].setBackground(Color.GREEN);
				
			}
		}
	}
	
	public static void resetBoardColors(Board board, JButton[][] squares) {
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
		
		GUIUtils.markKingInDanger(board, squares);
	}
	
	private static void markKingInDanger(Board board, JButton[][] squares) {
		
		King whiteKing = KingCheckUtils.findKing(board, true);		
		King blackKing = KingCheckUtils.findKing(board, false);
		
		if(KingCheckUtils.isKingInDanger(board, true))
			squares[whiteKing.getRow()][whiteKing.getCol()].setBackground(Color.RED);
		
		if(KingCheckUtils.isKingInDanger(board, false))
			squares[blackKing.getRow()][blackKing.getCol()].setBackground(Color.RED);
		
	}

	public static void updateTurnLabel(JLabel turnLabel, Color backgroundColor, boolean whiteTurn) {
		
		turnLabel.setOpaque(true);
		turnLabel.setBackground(backgroundColor);
		
		if (whiteTurn) {
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
	
	public static String gameOverMessage(Board board, boolean whiteTurn, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		String winner;
		
		if(Controller.isGameOver(board, whiteTurn, BoardStates, halfMoveCounter)) {
			winner = GameCheckUtils.kingCapturedWinner(board);
			if(winner != null)
				return "King captured! " + winner + " wins!";
			
			winner = GameCheckUtils.checkMateWinner(board, !whiteTurn);
			if(winner != null)
				return "Checkmate! " + winner + " wins!";
			
			if(GameCheckUtils.isThreefoldRepetition(BoardStates))
				return "Threefold Repetition. Tie game!";
			
			if(GameCheckUtils.is50MoveRule(halfMoveCounter))
				return "50 Move rule occured. Tie game!";
			
			if(GameCheckUtils.isStaleMate(board, whiteTurn))
				return "Stalemate occured. Tie game!";
			
			if(GameCheckUtils.isInsufficientMaterial(board))
				return "Insufficient material. Tie game!";
		}
		
		return null;
	}
	
}
