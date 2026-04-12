package utils;

import pawn.*;

import java.util.ArrayList;

import board.*;
import controller.Controller;

public class GameCheckUtils {
	
	private static boolean isKingCaptured(Board board) {
		
		King whiteKing = KingCheckUtils.findKing(board, true);		
		King blackKing = KingCheckUtils.findKing(board, false);
		
		if(whiteKing == null || blackKing == null)
			return true;
		
		return false;
	}
	
	private static boolean isCheckMate(Board board) {
		
		boolean safePosition = false;
		int r, c, row, col;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn currentPawn = board.getPawn(r, c);
				
				if(currentPawn != null) {
					for(row = 0; row < 8; row ++) {
						for(col = 0; col < 8; col++) {
							if(currentPawn.isValidMove(board, row, col)) {
								Board tmpBoard = board.copyBoard();
								
								Pawn tmpPawn = tmpBoard.getPawn(r, c);
								
								Controller.makeMove(tmpBoard, tmpPawn, null, row, col);
																
								safePosition = !KingCheckUtils.isKingInDanger(tmpBoard, currentPawn.isWhite());
							}
						}
					}
				}
			}
		}
		
		return safePosition;
	}
	
	public static boolean isGameOver(Board board) {
		
		if(GameCheckUtils.isKingCaptured(board))
			return true;
		
		if(GameCheckUtils.isCheckMate(board))
			return true;
		
		return false;
	}
	
	public static String gameOverMessage(Board board) {
		
		if(GameCheckUtils.isKingCaptured(board)) {
			King whiteKing = KingCheckUtils.findKing(board, true);		
			King blackKing = KingCheckUtils.findKing(board, false);
			
			if(whiteKing == null) return "Black has won the game!!!";
			if(blackKing == null) return "White has won the game!!!";
		}
		
		if (isCheckMate(board)) return "Checkmate!!!";
		
		return null;
	}
}
