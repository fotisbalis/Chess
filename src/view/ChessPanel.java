package view;

import pawn.*;
import board.*;
import controller.Controller;
import utils.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class ChessPanel extends JPanel {
	
	private Board board = new Board();
	private JButton[][] squares = new JButton[8][8];
	private JPanel boardPanel = new JPanel(new GridLayout(8, 8));
	private JPanel leftCaptured = new JPanel();
	private JPanel rightCaptured = new JPanel();
	private JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
	
	private boolean whiteTurn = true;
	private int selectedRow = -1, selectedCol = -1;
	private ArrayList<Pawn> captured = new ArrayList<Pawn>();
	
	private ImageIcon whiteKingIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_king.png");
	private ImageIcon blackKingIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_king.png");
	private ImageIcon whiteQueenIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_queen.png");
	private ImageIcon blackQueenIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_queen.png");
	private ImageIcon whiteBishopIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_bishop.png");
	private ImageIcon blackBishopIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_bishop.png");
	private ImageIcon whiteKnightIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_knight.png");
	private ImageIcon blackKnightIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_knight.png");
	private ImageIcon whiteRookIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_rook.png");
	private ImageIcon blackRookIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_rook.png");
	private ImageIcon whiteSoldierIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\white_soldier.png");
	private ImageIcon blackSoldierIcon = new ImageIcon("C:\\Users\\fotis\\OneDrive\\Έγγραφα\\Chess\\Chess\\Pictures\\black_soldier.png");
	
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
		refreshGUIBoard();
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
				return;
			}
				
			if(clickedPawn.isWhite() != whiteTurn) {
				return;
			}
			
			selectedRow = row;
			selectedCol = col;
			
			highlightPossibleMoves(clickedPawn);
			
			return;
		}
		
		// Case 2: Player has chosen pawn but changed his mind and chooses a new pawn
		if(clickedPawn != null && clickedPawn.isWhite() == whiteTurn) {
			
			resetBoardColors();
			
			selectedRow = row;
			selectedCol = col;
			
			highlightPossibleMoves(clickedPawn);
			
			return;
		}
		
		//Case 3: Player has chosen pawn but makes invalid move
		Pawn selectedPawn = board.getPawn(selectedRow, selectedCol);
		
		boolean valid = selectedPawn.isValidMove(board, row, col);
		
		if (selectedPawn == null || !valid) {
			selectedRow = -1;
			selectedCol = -1;
			
			resetBoardColors();
			
			return;
		}
		
		//Case 4: Player has chosen pawn and makes valid move
		Controller.makeMove(board, selectedPawn, captured, row, col);
		
		refreshGUIBoard();
		updateCapturedPawns();
		resetBoardColors();
		
		if(GameCheckUtils.isGameOver(board, whiteTurn)) {
			JOptionPane.showMessageDialog(this, GameCheckUtils.gameOverMessage(board, whiteTurn));
			System.exit(0);
		}
					
		whiteTurn = !whiteTurn;
		selectedRow = -1;
		selectedCol = -1;
		
		updateTurnLabel();
	}
	
	private void refreshGUIBoard() {
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
						img = resizeIcon(getImage(pawn), 20, 40);
					if(pawn instanceof Queen || pawn instanceof King)
						img = resizeIcon(getImage(pawn), 50, 90);
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
	    
	    leftCaptured.add(Box.createVerticalGlue());
	    rightCaptured.add(Box.createVerticalGlue());
	    
	    for(Pawn p : captured) {
	    	
	    	ImageIcon img;
	    	if(p instanceof Soldier)
				img = resizeIcon(getImage(p), 30, 60);
			else
				img = resizeIcon(getImage(p), 40, 80);
	    	
	        JLabel label = new JLabel(img);
	        label.setAlignmentX(Component.CENTER_ALIGNMENT);

	        if(p.isWhite()) {
	            rightCaptured.add(label);   
	        }
	        else {
	            leftCaptured.add(label);
	        }
	    }
	    
	    leftCaptured.add(Box.createVerticalGlue());
	    rightCaptured.add(Box.createVerticalGlue());
	    
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
		
		squares[pawn.getRow()][pawn.getCol()].setBackground(Color.ORANGE);
		
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
		
		markKingInDanger();
		
	}
	
	private void updateTurnLabel() {
		
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
	
	private void markKingInDanger() {
		
		King whiteKing = KingCheckUtils.findKing(board, true);		
		King blackKing = KingCheckUtils.findKing(board, false);
		
		if(KingCheckUtils.isKingInDanger(board, true))
			squares[whiteKing.getRow()][whiteKing.getCol()].setBackground(Color.RED);
		
		if(KingCheckUtils.isKingInDanger(board, false))
			squares[blackKing.getRow()][blackKing.getCol()].setBackground(Color.RED);
		
	}
}
