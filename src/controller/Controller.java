package controller;

import pawn.*;
import utils.GameCheckUtils;

import java.util.ArrayList;

import board.*;

public class Controller {
	
	public static void makeMove(Board board, ArrayList<Pawn> captured, int halfMoveCounter, Pawn pawn, int toRow, int toCol) {
		
		if(pawn instanceof Soldier) {
			Soldier soldier = (Soldier) pawn;
			
			if(soldier.isFirstMove())
				soldier.setFirstMove(false);
		}
		
		int fromRow = pawn.getRow();
		int fromCol = pawn.getCol();
		
		Pawn origin = board.getPawn(fromRow, fromCol);
		Pawn target = board.getPawn(toRow, toCol);
		
		if(target != null) {	
			captured.add(target);
			halfMoveCounter = 0;
		}
		
		else if(origin instanceof Soldier)
			halfMoveCounter = 0;
		
		else
			halfMoveCounter++;
		
		board.setPawn(toRow, toCol, origin);
		board.setPawn(fromRow, fromCol, null);
		
		origin.setPosition(toRow, toCol);
	}

	public static boolean isGameOver(Board board, boolean whiteTurn, ArrayList<BoardState> BoardStates, int halfMoveCounter) {
		
		if(GameCheckUtils.kingCapturedWinner(board) != null || GameCheckUtils.checkMateWinner(board, !whiteTurn) != null ||
				GameCheckUtils.isThreefoldRepetition(BoardStates) || GameCheckUtils.is50MoveRule(halfMoveCounter) ||
				GameCheckUtils.isStaleMate(board, whiteTurn) || GameCheckUtils.isInsufficientMaterial(board))
			return true;
		
		return false;
	}
	
}
