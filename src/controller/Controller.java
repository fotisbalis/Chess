package controller;

import pawn.*;
import utils.CastlingUtils;
import utils.GameCheckUtils;

import java.util.ArrayList;

import board.*;

public class Controller {
	
	public static void makeMove(Board board, Pawn pawn, int toRow, int toCol) {
	
		int fromRow = pawn.getRow();
		int fromCol = pawn.getCol();
		
		board.setPawn(toRow, toCol, pawn);
		board.setPawn(fromRow, fromCol, null);
		
		pawn.setPosition(toRow, toCol);
		pawn.setHasMoved(true);
		
		if(pawn instanceof Soldier) {
			Soldier soldier = (Soldier) pawn;
			soldier.incrementMoveCount();
		}
	}
	
	public static void makeCastlingMove(Board board, King king, boolean isKingSide) {
		
		Rook rook = CastlingUtils.getRook(board, king, isKingSide);
		
		int row = king.getRow();
		int newKingCol = isKingSide ? (king.getCol() + 2) : (king.getCol() - 2);
		int newRookCol = isKingSide ? (newKingCol - 1) : (newKingCol + 1);	
		
		Controller.makeMove(board, king, row, newKingCol);
		
		Controller.makeMove(board, rook, row, newRookCol);			
	}

	public static void makeEnPassantMove(Board board, Soldier attacker, int toRow, int toCol) {
		
		int capturedRow = attacker.getRow();
		int capturedCol = toCol;
		
		Controller.makeMove(board, attacker, toRow, toCol);
		board.setPawn(capturedRow, capturedCol, null);
	}
	
	public static boolean isGameOver(Board board, PawnColor turnColor, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		if(GameCheckUtils.checkMateWinner(board, turnColor.opposite()) != null ||
				GameCheckUtils.isThreefoldRepetition(BoardStates) || GameCheckUtils.is50MoveRule(halfMoveCounter) ||
				GameCheckUtils.isStaleMate(board, turnColor) || GameCheckUtils.isInsufficientMaterial(board))
			return true;
		
		return false;
	}
	
}
