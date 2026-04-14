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
		
		if (!KingCheckUtils.isKingInDanger(board, whiteTurn))
            return null;
		
		if (MovesUtils.hasLegalMoves(board, whiteTurn))
			return null;
		
		return whiteTurn ? "Black" : "White";
	}
	
	private static boolean isThreefoldRepetition(ArrayList<BoardState> BoardStates) {
		
		for(BoardState boardState1 : BoardStates) {
			int count = 0;
			
			for(BoardState boardState2 : BoardStates) {
				if(ThreefoldRepetitionUtils.equalBoardStates(boardState1, boardState2))
					count++;
			}
			
			if(count >= 3)
				return true;
		}
		
		return false;
	}
	
	private static boolean is50MoveRule(int halfMoveCounter) {
		
		return (halfMoveCounter >= 100) ? true : false;
	}
	
	private static boolean isStaleMate(Board board, boolean whiteTurn) {

	    if(KingCheckUtils.isKingInDanger(board, whiteTurn))
	        return false;

	    if(MovesUtils.hasLegalMoves(board, whiteTurn))
	        return false;

	    return true;
	}
	
	public static boolean isGameOver(Board board, boolean whiteTurn, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		if(GameCheckUtils.kingCapturedWinner(board) != null || GameCheckUtils.checkMateWinner(board, !whiteTurn) != null ||
				GameCheckUtils.isThreefoldRepetition(BoardStates) || GameCheckUtils.is50MoveRule(halfMoveCounter) ||
				GameCheckUtils.isStaleMate(board, whiteTurn))
			return true;
		
		return false;
	}
	
	public static String gameOverMessage(Board board, boolean whiteTurn, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		String winner;
		
		if(GameCheckUtils.isGameOver(board, whiteTurn, BoardStates, halfMoveCounter)) {
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
		}
		
		return null;
	}
}
