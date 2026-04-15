package utils;

import pawn.*;

import java.util.ArrayList;

import board.*;
import controller.Controller;

public class GameCheckUtils {
	
	public static String kingCapturedWinner(Board board) {
		
		King whiteKing = KingCheckUtils.findKing(board, PawnColor.WHITE);		
		King blackKing = KingCheckUtils.findKing(board, PawnColor.BLACK);
		
		if(whiteKing == null)
			return "Black";
		
		if(blackKing == null)
			return "White";
		
		return null;
	}
	
	public static String checkMateWinner(Board board, PawnColor turnColor) {
		
		if (KingCheckUtils.isKingInDanger(board, turnColor) && !(MovesUtils.hasLegalMoves(board, turnColor)))
            return turnColor == PawnColor.WHITE ? "Black" : "White";
		
		return null;
	}
	
	public static boolean isThreefoldRepetition(ArrayList<BoardState> BoardStates) {
		
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
	
	public static boolean is50MoveRule(int halfMoveCounter) {
		
		return (halfMoveCounter >= 100) ? true : false;
	}
	
	public static boolean isStaleMate(Board board, PawnColor turnColor) {

	    if(!KingCheckUtils.isKingInDanger(board, turnColor) && !MovesUtils.hasLegalMoves(board, turnColor))
	        return true;

	    return false;
	}
	
	public static boolean isInsufficientMaterial(Board board) {
		
		ArrayList<Pawn> whitePawns = board.getPlayerPawns(PawnColor.WHITE);
		ArrayList<Pawn> blackPawns = board.getPlayerPawns(PawnColor.BLACK);
		
		// King vs King
		if(whitePawns.size() == 1 && blackPawns.size() == 1)
			return true;
		
		// King + Bishop vs King OR King + Knight vs King
		if(whitePawns.size() == 2 && blackPawns.size() == 1) {
			Pawn extraWhitePiece = insufficientMaterialUtils.getNonKingPiece(whitePawns);

	        if(extraWhitePiece instanceof Bishop || extraWhitePiece instanceof Knight)
	            return true;
		}
		
		// King vs King + Bishop OR King vs King + Knight
		if(whitePawns.size() == 1 && blackPawns.size() == 2) {
			Pawn extraBlackPiece = insufficientMaterialUtils.getNonKingPiece(blackPawns);

			if(extraBlackPiece instanceof Bishop || extraBlackPiece instanceof Knight)
	            return true;
		}
		
		// King + Bishop vs King + Bishop (Bishops on same color square)
		if(whitePawns.size() == 2 && blackPawns.size() == 2) {
	        Pawn extraWhitePiece = insufficientMaterialUtils.getNonKingPiece(whitePawns);
	        Pawn extraBlackPiece = insufficientMaterialUtils.getNonKingPiece(blackPawns);

	        if(extraWhitePiece instanceof Bishop && extraBlackPiece instanceof Bishop) {
	            Bishop whiteBishop = (Bishop) extraWhitePiece;
	            Bishop blackBishop = (Bishop) extraBlackPiece;

	            if(insufficientMaterialUtils.sameColorSquare(whiteBishop, blackBishop))
	                return true;
	        }
	    }
		
		return false;
	}
	
	
}
