package utils;

import pawn.*;

import java.util.ArrayList;

import board.*;
import controller.Controller;

public class GameCheckUtils {
	
	private static String kingCapturedWinner(Board board) {
		
		King whiteKing = KingCheckUtils.findKing(board, true);		
		King blackKing = KingCheckUtils.findKing(board, false);
		
		if(whiteKing == null)
			return "Black";
		
		if(blackKing == null)
			return "White";
		
		return null;
	}
	
	private static String checkMateWinner(Board board, boolean whiteTurn) {
		
		int r, c, row, col;
		
		if (!KingCheckUtils.isKingInDanger(board, whiteTurn))
            return null;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				
				Pawn currentPawn = board.getPawn(r, c);
				
				if(currentPawn != null && currentPawn.isWhite() == whiteTurn) {
					
					for(row = 0; row < 8; row ++) {
						for(col = 0; col < 8; col++) {
							
							if(currentPawn.isValidMove(board, row, col)) {
								
								Board tmpBoard = board.copyBoard();
								
								Pawn tmpPawn = tmpBoard.getPawn(r, c);
								
								Controller.makeMove(tmpBoard, tmpPawn, new ArrayList<Pawn>(), row, col);
																
								if(!KingCheckUtils.isKingInDanger(tmpBoard, currentPawn.isWhite()))
										return null;
							}
						}
					}
					
				}
			}
		}
		
		return whiteTurn ? "Black" : "White";
	}
	
	public static boolean isGameOver(Board board, boolean whiteTurn) {
		
		if(GameCheckUtils.kingCapturedWinner(board) != null || GameCheckUtils.checkMateWinner(board, !whiteTurn) != null)
			return true;
		
		return false;
	}
	
	public static String gameOverMessage(Board board, boolean whiteTurn) {
		
		String winner;
		
		if(GameCheckUtils.isGameOver(board, whiteTurn)) {
			winner = GameCheckUtils.kingCapturedWinner(board);
			if(winner != null)
				return "King captured! " + winner + " wins!";
			
			winner = GameCheckUtils.checkMateWinner(board, !whiteTurn);
			if(winner != null)
				return "Checkmate! " + winner + " wins!";
		}
		
		return null;
	}
}
