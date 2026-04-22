package utils;

import java.util.ArrayList;

import board.*;
import controller.Controller;
import pawn.*;

public class MovesUtils {

	public static boolean[][] possibleMoves(Board board, Pawn pawn) {
		return possibleMoves(board, pawn, true);
	}

	public static boolean[][] possibleMoves(Board board, Pawn pawn, boolean includeCastling) {
		boolean[][] validMoves = new boolean[8][8];
		int r, c;		
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				validMoves[r][c] = false;
				
				if(pawn.getRow() == r && pawn.getCol() == c)
					continue;
				
				if(!includeCastling && pawn instanceof King) {
					boolean normalKingMove = Math.abs(c - pawn.getCol()) <= 1 && Math.abs(r - pawn.getRow()) <= 1;
					Pawn target = board.getPawn(r, c);

					if(normalKingMove && (target == null || target.getColor() != pawn.getColor()))
						validMoves[r][c] = true;
				}
				else if(pawn.isValidMove(board, r, c)) {
					validMoves[r][c] = true;
				}
			}
		}
		
		return validMoves;
	}
	
	public static boolean[][] possibleLegalMoves(Board board, Pawn pawn) {
		boolean[][] legalMoves = new boolean[8][8];
		int r, c;

		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				legalMoves[r][c] = false;

				if(pawn.getRow() == r && pawn.getCol() == c)
					continue;

				if(MovesUtils.isLegalMove(board, pawn, r, c))
					legalMoves[r][c] = true;
			}
		}

		return legalMoves;
	}
	
	public static boolean hasLegalMoves(Board board, PawnColor color) {
		
		int r, c, row, col;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn currentPawn = board.getPawn(r, c);
				
				if(currentPawn != null && currentPawn.getColor() == color) {
					
					for(row = 0; row < 8; row ++) {
						for(col = 0; col < 8; col++) {
							
							if(MovesUtils.isLegalMove(board, currentPawn, row, col)) {
								
								Board tmpBoard = MovesUtils.simulateMove(board, currentPawn, row, col);
																
								if(!KingCheckUtils.isKingInDanger(tmpBoard, currentPawn.getColor()))
										return true;
							}
						}
					}
					
				}
			}
		}
			
		return false;
	}
	
	public static boolean isLegalMove(Board board, Pawn pawn, int toRow, int toCol) {

		if(!pawn.isValidMove(board, toRow, toCol))
			return false;
		
		if(pawn instanceof King) {
			King king = (King) pawn;
			
			if(CastlingUtils.isMoveCastling(king, toRow, toCol)) {
				return CastlingUtils.canCastlingHappen(board, king, toCol);
			}
		}

		Board tmpBoard = MovesUtils.simulateMove(board, pawn, toRow, toCol);

		return !KingCheckUtils.isKingInDanger(tmpBoard, pawn.getColor());
	}
	
	public static Board simulateMove(Board board, Pawn pawn, int toRow, int toCol) {
		
		Board tmpBoard = board.copyBoard();
		Pawn tmpPawn = tmpBoard.getPawn(pawn.getRow(), pawn.getCol());

		Controller.makeMove(tmpBoard, tmpPawn, toRow, toCol);
		
		return tmpBoard;
	}
	
	public static boolean isSameColumnValidMove(Board board, int fromRow, int toRow, int toCol) {
		
		int stepRow = Integer.compare(toRow, fromRow);
		int i;
    	
		for(i = (fromRow + stepRow); i != toRow; i += stepRow) {
    		if(board.getPawn(i, toCol) != null) {
    			return false;
    		}
    	}
		
		return true;
	}
	
	public static boolean isSameRowValidMove(Board board, int fromCol, int toRow, int toCol) {
		
		int stepCol = Integer.compare(toCol, fromCol);
		int i;
    	
		for(i = (fromCol + stepCol); i != toCol; i += stepCol) {
    		if(board.getPawn(toRow, i) != null) {
    			return false;
    		}
    	}
		
		return true;
	}
	
	public static boolean isDiagonalValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
		
		int stepRow = Integer.compare(toRow, fromRow);
	    int stepCol = Integer.compare(toCol, fromCol);

	    int currentRow = fromRow + stepRow;
	    int currentCol = fromCol + stepCol;

	    while (currentRow != toRow && currentCol != toCol) {
	        if (board.getPawn(currentRow, currentCol) != null) {
	            return false;
	        }

	        currentRow += stepRow;
	        currentCol += stepCol;
	    }

	    return true;
	}
	
	public static boolean wasDoubleSquareSoldierMove(Board board, Soldier soldier) {
		
		if(soldier == null)
			return false;
		
		if(soldier.getMoveCount() != 1)
			return false;
		
		if(Math.abs(soldier.getStartingRow() - soldier.getRow()) != 2)
			return false;
		
		if(soldier.getStartingCol() != soldier.getCol())
			return false;
		
		return true;
	}
	
}
