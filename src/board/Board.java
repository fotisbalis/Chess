package board;

import pawn.*;

public class Board {
	protected Pawn[][] board;
	
	public Board() {
		this.board = new Pawn[8][8];		
	}
	
	public void initializeBoard() {
		int i;
		
		//Soldiers
		for(i = 0; i < 8; i++) {
			board[1][i] = new Soldier(1, i, false);
			board[6][i] = new Soldier(6, i, true);
		}
		
		//Rooks
		board[0][0] = new Rook(0, 0, false);
		board[0][7] = new Rook(0, 7, false);
		board[7][0] = new Rook(7, 0, true);
		board[7][7] = new Rook(7, 7, true);
		
		//Knights
		board[0][1] = new Knight(0, 1, false);
		board[0][6] = new Knight(0, 6, false);
		board[7][1] = new Knight(7, 1, true);
		board[7][6] = new Knight(7, 6, true);
		
		//Bishops
		board[0][2] = new Bishop(0, 2, false);
		board[0][5] = new Bishop(0, 5, false);
		board[7][2] = new Bishop(7, 2, true);
		board[7][5] = new Bishop(7, 5, true);
		
		//Queens
		board[0][3] = new Queen(0, 3, false);
        board[7][3] = new Queen(7, 3, true);
        
        //Kings
        board[0][4] = new King(0, 4, false);
        board[7][4] = new King(7, 4, true);
	}
	
	public Pawn getPawn(int row, int col) {
		return board[row][col];
	}
	
	public void setPawn(int row, int col, Pawn pawn) {
		board[row][col] = pawn;
	}
	
	public Pawn[][] getBoard() {
		return board;
	}
	
	public King findOpponentKing(boolean whiteTurn) {
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 0; c++) {
				
				Pawn pawn = getPawn(r, c);
				
				if(pawn != null && pawn instanceof King && pawn.isWhite() != whiteTurn) {
					King king = (King) pawn;
					
					return king;
				}
			}
		}
		
		return null;
	}
	
	/*public boolean isKingInDanger(boolean whiteTurn) {
		int r, c;
		
		King king = findOpponentKing(whiteTurn);
		int kingRow = king.getRow();
		int kingCol = king.getCol();
		
		if(king == null)
			return false;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 0; c++) {
				
				Pawn pawn = getPawn(r, c);
				
				if(pawn != null && pawn.isWhite() == whiteTurn) {
					boolean validMoves = pawn.possibleMoves(getBoard());
					
					if(validMoves[king.getRow()][king.getCol()]) {
						return true;
					}
				}
			}
		}
	}*/
}
