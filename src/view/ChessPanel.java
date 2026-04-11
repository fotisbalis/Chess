package view;

import pawn.*;
import board.*;
import utils.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;


public class ChessPanel extends JPanel {
	
	private Board board = new Board();
	private JButton[][] squares = new JButton[8][8];
	private JPanel boardPanel = new JPanel(new GridLayout(8,8));
	private JPanel leftCaptured = new JPanel();
	private JPanel rightCaptured = new JPanel();
	private JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
	
	private boolean whiteTurn = true;
	private int selectedRow = -1, selectedCol = -1;
	
	private ImageIcon whiteKingIcon = new ImageIcon("pictures/white_king.jpg");
	private ImageIcon blackKingIcon = new ImageIcon("pictures/black_king.jpg");
	private ImageIcon whiteQueenIcon = new ImageIcon("pictures/white_queen.jpg");
	private ImageIcon blackQueenIcon = new ImageIcon("pictures/black_queen.jpg");
	private ImageIcon whiteBishopIcon = new ImageIcon("pictures/white_bishop.jpg");
	private ImageIcon blackBishopIcon = new ImageIcon("pictures/black_bishop.jpg");
	private ImageIcon whiteKnightIcon = new ImageIcon("pictures/white_knight.jpg");
	private ImageIcon blackKnightIcon = new ImageIcon("pictures/black_knight.jpg");
	private ImageIcon whiteRookIcon = new ImageIcon("pictures/white_rook.jpg");
	private ImageIcon blackRookIcon = new ImageIcon("pictures/black_rook.jpg");
	private ImageIcon whiteSoldierIcon = new ImageIcon("pictures/white_soldier.jpg");
	private ImageIcon blackSoldierIcon = new ImageIcon("pictures/black_soldier.jpg");
	
	ArrayList<Pawn> captured = new ArrayList<Pawn>();
	
	public ChessPanel() {
		
		this.board.initializeBoard();
		
		setLayout(new BorderLayout());

		add(leftCaptured, BorderLayout.WEST);
		add(boardPanel, BorderLayout.CENTER);
		add(rightCaptured, BorderLayout.EAST);
		add(turnLabel, BorderLayout.NORTH);
		
		leftCaptured.setLayout(new BoxLayout(leftCaptured, BoxLayout.Y_AXIS));
		rightCaptured.setLayout(new BoxLayout(rightCaptured, BoxLayout.Y_AXIS));
		turnLabel.setLayout(new BoxLayout(turnLabel, BoxLayout.Y_AXIS));
		
		leftCaptured.setPreferredSize(new Dimension(100, 0));
		rightCaptured.setPreferredSize(new Dimension(100, 0));
		turnLabel.setPreferredSize(new Dimension(100, 50));
		
		turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		intitializeGUIBoard();
		refreshBoard();
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
		
		resetBoardColors();
	}
	
	private void playTurn(int row, int col) {
		
		Pawn clickedPawn = board.getPawn(row, col);
		
		// Case 1: Player chooses a pawn to play
		if(selectedRow == -1) {
	
			if(clickedPawn == null) {
				JOptionPane.showMessageDialog(this, "Select a pawn first!");
				return;
			}
				
			if(clickedPawn.isWhite() != whiteTurn) {
				JOptionPane.showMessageDialog(this, "You have to choose a " + (whiteTurn ? "White" : "Black") + " pawn!");
				return;
			}
			
			if(!MovesUtils.pawnCanMove(board, clickedPawn)) {
				JOptionPane.showMessageDialog(this, "This Pawn cannot move!");
				return;
			}
			
			selectedRow = row;
			selectedCol = col;
			
			highlightPossibleMoves(clickedPawn);
			
			return;
		}
		
		// Case 2: Player changed his mind and chose a new pawn
		if(clickedPawn != null && clickedPawn.isWhite() == whiteTurn) {
			
			resetBoardColors();
			
			selectedRow = row;
			selectedCol = col;
			
			highlightPossibleMoves(clickedPawn);
			
			return;
		}
		
		//Case 3: Player has chosen pawn and now chooses move
		Pawn selectedPawn = board.getPawn(selectedRow, selectedCol);
		
		if (selectedPawn == null) {
	        selectedRow = -1;
	        selectedCol = -1;
	        
	        resetBoardColors();
	        
	        return;
	    }	
		
		boolean valid = selectedPawn.isValidMove(board, row, col);
		
		if(!valid) {
	        JOptionPane.showMessageDialog(this, "Invalid move!");
	        selectedRow = -1;
	        selectedCol = -1;
	        
	        resetBoardColors();
	        
	        return;
	    }
		
		if(selectedPawn instanceof Soldier) {
			Soldier soldier = (Soldier) selectedPawn;
			
			if(soldier.isFirstMove())
				soldier.setFirstMove(false);
		}
		
		movePawn(selectedRow, selectedCol, row, col);
		refreshBoard();
		updateCapturedPawns();
		
		whiteTurn = !whiteTurn;
		selectedRow = -1;
		selectedCol = -1;
		
		updateTurnLabel();
		resetBoardColors();
	}
	
	private void movePawn(int fromRow, int fromCol, int toRow, int toCol){
		
		Pawn origin = board.getPawn(fromRow, fromCol);
		Pawn target = board.getPawn(toRow, toCol);
		
		if(target != null) {
			if(target.getPawnType().equals("King")) {
				JOptionPane.showMessageDialog(this, (target.isWhite() ? "Black" : "White") + " has won the game!");
				System.exit(0);
			}
			
			captured.add(target);
		}
		
		board.setPawn(toRow, toCol, origin);
		board.setPawn(fromRow, fromCol, null);
		
		origin.setPosition(toRow, toCol);
	}
	
	private void refreshBoard() {
		int row, col;
		
		for(row = 0; row < 8; row++) {
			for(col = 0; col < 8; col++) {
				
				Pawn pawn = board.getPawn(row, col);
				
				if(pawn == null) {
					squares[row][col].setIcon(null);
				}
				else {
					ImageIcon img;
					
					if(pawn.getPawnType().equals("Soldier"))
						img = resizeIcon(getImage(pawn), 35, 60);
					else
						img = resizeIcon(getImage(pawn), 50, 80);
					
					squares[row][col].setIcon(img);
				}
			}
		}
	}
	
	private void updateCapturedPawns() {
		
		leftCaptured.removeAll();
	    rightCaptured.removeAll();
	    
	    for(Pawn p : captured) {
	    	
	    	ImageIcon img;
	    	if(p instanceof Soldier)
				img = resizeIcon(getImage(p), 30, 50);
			else
				img = resizeIcon(getImage(p), 40, 70);
	    	
	        JLabel label = new JLabel(img);

	        if(p.isWhite()) {
	            rightCaptured.add(label);   
	        }
	        else {
	            leftCaptured.add(label);
	        }
	    }
	    
	    leftCaptured.revalidate();
	    leftCaptured.repaint();
	    rightCaptured.revalidate();
	    rightCaptured.repaint();
	}
	
	public ImageIcon getImage(Pawn pawn) {

        switch(pawn.getPawnType()) {
            case "Soldier": return pawn.isWhite() ? whiteSoldierIcon : blackSoldierIcon;
            case "Rook": return pawn.isWhite() ? whiteRookIcon : blackRookIcon;
            case "Knight": return pawn.isWhite() ? whiteKnightIcon : blackKnightIcon;
            case "Bishop": return pawn.isWhite() ? whiteBishopIcon : blackBishopIcon;
            case "Queen": return pawn.isWhite() ? whiteQueenIcon : blackQueenIcon;
            case "King": return pawn.isWhite() ? whiteKingIcon : blackKingIcon;
        }

        return null;
    }
	
	public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {

		Image img = icon.getImage();
	    Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

	    return new ImageIcon(scaled);
	}
	
	private void highlightPossibleMoves(Pawn pawn) {
		int r, c;
		boolean[][] moves = MovesUtils.possibleMoves(board, pawn);
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				if(moves[r][c]) {
					squares[r][c].setBackground(Color.GREEN);
				}
			}
		}
	}
	
	private void resetBoardColors() {
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				if((r + c) % 2 == 0)
                    squares[r][c].setBackground(Color.WHITE);
                else
                    squares[r][c].setBackground(Color.GRAY);
			}
		}
	}
	
	private void updateTurnLabel() {
		
		if (whiteTurn)
	        turnLabel.setText("White's Turn"); 
		else
	        turnLabel.setText("Black's Turn");
	}
}

