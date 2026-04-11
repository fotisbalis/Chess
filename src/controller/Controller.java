package controller;

import pawn.*;

import java.util.ArrayList;

import board.*;
import utils.*;
import view.*;

public class Controller {
	
	private static void movePawn(Board board, ArrayList<Pawn> captured, int fromRow, int fromCol, int toRow, int toCol){
		
		Pawn origin = board.getPawn(fromRow, fromCol);
		Pawn target = board.getPawn(toRow, toCol);
		
		if(target != null) {			
			captured.add(target);
		}
		
		board.setPawn(toRow, toCol, origin);
		board.setPawn(fromRow, fromCol, null);
		
		origin.setPosition(toRow, toCol);
	}
	
	public static void makeMove(Board board, Pawn pawn, ArrayList<Pawn> captured, int toRow, int toCol) {
		
		if (pawn == null)
	        return;
	    
		boolean valid = pawn.isValidMove(board, toRow, toCol);
		
		if(!valid)
	        return;
		
		if(pawn instanceof Soldier) {
			Soldier soldier = (Soldier) pawn;
			
			if(soldier.isFirstMove())
				soldier.setFirstMove(false);
		}
		
		Controller.movePawn(board, captured, pawn.getRow(), pawn.getCol(), toRow, toCol);
	}
}
