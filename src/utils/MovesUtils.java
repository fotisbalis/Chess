package utils;

import java.util.ArrayList;

import board.*;
import controller.Controller;
import pawn.*;

public class MovesUtils {

	public static boolean[][] possibleMoves(Board board, Pawn pawn) {
		boolean[][] validMoves = new boolean[8][8];
		int r, c;		
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				validMoves[r][c] = false;
				
				if(pawn.getRow() == r && pawn.getCol() == c)
					continue;
				
				if(pawn.isValidMove(board, r, c)) {
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

				if(isLegalMove(board, pawn, r, c))
					legalMoves[r][c] = true;
			}
		}

		return legalMoves;
	}

	
	public static boolean hasLegalMoves(Board board, PawnColor color) {
		
		int r, c, row, col, ignore = 0;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn currentPawn = board.getPawn(r, c);
				
				if(currentPawn != null && currentPawn.getColor() == color) {
					
					for(row = 0; row < 8; row ++) {
						for(col = 0; col < 8; col++) {
							
							if(MovesUtils.isLegalMove(board, currentPawn, row, col)) {
								
								Board tmpBoard = board.copyBoard();
								
								Pawn tmpPawn = tmpBoard.getPawn(r, c);
								
								Controller.makeMove(tmpBoard, new ArrayList<Pawn>(), ignore, tmpPawn, row, col);
																
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
	
	public static boolean isLegalMove(Board board, Pawn pawn, int newRow, int newCol) {

		if(!pawn.isValidMove(board, newRow, newCol))
			return false;

		Board tmpBoard = board.copyBoard();
		Pawn tmpPawn = tmpBoard.getPawn(pawn.getRow(), pawn.getCol());

		Controller.makeMove(tmpBoard, new ArrayList<Pawn>(), 0, tmpPawn, newRow, newCol);

		return !KingCheckUtils.isKingInDanger(tmpBoard, pawn.getColor());
	}
	
}
