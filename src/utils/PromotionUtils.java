package utils;

import board.*;
import pawn.*;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class PromotionUtils {

	public static void handlePromotion(Component parent, Board board, ArrayList<Pawn> captured, boolean whiteTurn) {
		
		int row, col;
		
		row = whiteTurn ? 0 : 7;
		
		for(col = 0; col < 8; col++) {
			
			Pawn currentPawn = board.getPawn(row, col);
			
			if(currentPawn instanceof Soldier && currentPawn.isWhite() == whiteTurn) {
				
				ArrayList<Pawn> currentPlayerCaptured = CapturedUtils.getCurrentPlayerCaptured(captured, whiteTurn);
				
				if(currentPlayerCaptured.isEmpty())
					return;
				
				Pawn chosenPawn = GUIUtils.choosePawnForPromotion(parent, currentPlayerCaptured);
				
				if(chosenPawn != null)
					PromotionUtils.makePromotion(board, (Soldier) currentPawn, chosenPawn, captured);
			}

		}
	}
	
	private static void makePromotion(Board board, Soldier soldier, Pawn returningPawn, ArrayList<Pawn> captured) {
		
		int row = soldier.getRow(), col = soldier.getCol();
		boolean color = soldier.isWhite();
		
		if(returningPawn instanceof Queen)
			board.setPawn(row, col, new Queen(row, col, color));
		
		else if(returningPawn instanceof Bishop)
			board.setPawn(row, col, new Bishop(row, col, color));
		
		else if(returningPawn instanceof Knight)
			board.setPawn(row, col, new Knight(row, col, color));
		
		else if(returningPawn instanceof Rook)
			board.setPawn(row, col, new Rook(row, col, color));
		
		captured.remove(returningPawn);
		captured.add(soldier);
	}
	
	
}
