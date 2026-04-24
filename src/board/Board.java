package board;

import java.io.Serializable;
import java.util.ArrayList;

import controller.*;
import pawn.*;
import utils.*;
import move.*;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Pawn[][] board;
	protected int enPassantVulnerableRow;
	protected int enPassantVulnerableCol;
	
	public Board() {
		this.board = new Pawn[8][8];
		this.enPassantVulnerableRow = -1;
		this.enPassantVulnerableCol = -1;
	}
	
	public void initializeBoard() {
		int i;
		
		//Soldiers
		for(i = 0; i < 8; i++) {
			board[1][i] = new Soldier(1, i, PawnColor.BLACK);
			board[6][i] = new Soldier(6, i, PawnColor.WHITE);
		}
		
		//Rooks
		board[0][0] = new Rook(0, 0, PawnColor.BLACK);
		board[0][7] = new Rook(0, 7, PawnColor.BLACK);
		board[7][0] = new Rook(7, 0, PawnColor.WHITE);
		board[7][7] = new Rook(7, 7, PawnColor.WHITE);
		
		//Knights
		board[0][1] = new Knight(0, 1, PawnColor.BLACK);
		board[0][6] = new Knight(0, 6, PawnColor.BLACK);
		board[7][1] = new Knight(7, 1, PawnColor.WHITE);
		board[7][6] = new Knight(7, 6, PawnColor.WHITE);
		
		//Bishops
		board[0][2] = new Bishop(0, 2, PawnColor.BLACK);
		board[0][5] = new Bishop(0, 5, PawnColor.BLACK);
		board[7][2] = new Bishop(7, 2, PawnColor.WHITE);
		board[7][5] = new Bishop(7, 5, PawnColor.WHITE);
		
		//Queens
		board[0][3] = new Queen(0, 3, PawnColor.BLACK);
        board[7][3] = new Queen(7, 3, PawnColor.WHITE);
        
        //Kings
        board[0][4] = new King(0, 4, PawnColor.BLACK);
        board[7][4] = new King(7, 4, PawnColor.WHITE);
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

	public void setEnPassantVulnerableSquare(int row, int col) {
		this.enPassantVulnerableRow = row;
		this.enPassantVulnerableCol = col;
	}

	public void clearEnPassantVulnerableSquare() {
		this.enPassantVulnerableRow = -1;
		this.enPassantVulnerableCol = -1;
	}

	public boolean isEnPassantVulnerableSquare(int row, int col) {
		return enPassantVulnerableRow == row && enPassantVulnerableCol == col;
	}

	public boolean hasEnPassantVulnerableSquare() {
		return enPassantVulnerableRow != -1 && enPassantVulnerableCol != -1;
	}
	
	public ArrayList<Pawn> getPlayerPawns(PawnColor color) {
		
		ArrayList<Pawn> currentPlayerPawns = new ArrayList<Pawn>();
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn pawn = getPawn(r, c);
				
				if(pawn != null && pawn.getColor() == color)
					currentPlayerPawns.add(pawn);
			}
		}
		
		return currentPlayerPawns;
	}
	
	public Board copyBoard() {
		
		Board newBoard = new Board();
		newBoard.setEnPassantVulnerableSquare(enPassantVulnerableRow, enPassantVulnerableCol);
		
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				newBoard.setPawn(r, c, GameStateUtils.copyPawn(getPawn(r, c)));
			}
		}
		
		return newBoard;
	}
	
	
	
}
