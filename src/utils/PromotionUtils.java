package utils;

import board.*;
import pawn.*;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class PromotionUtils {
	
	public static ArrayList<Pawn> getPawnsForPromotion(boolean whiteTurn){
	
		ArrayList<Pawn> pawnsForPromotion = new ArrayList<Pawn>();
		
		pawnsForPromotion.add(new Bishop(-1, -1, whiteTurn));
		pawnsForPromotion.add(new Rook(-1, -1, whiteTurn));
		pawnsForPromotion.add(new Knight(-1, -1, whiteTurn));
		pawnsForPromotion.add(new Queen(-1, -1, whiteTurn));
		
		return pawnsForPromotion;
	}

	public static void handlePromotion(Component parent, Board board, boolean whiteTurn) {
		
		ArrayList<Pawn> pawnsForPromotion = PromotionUtils.getPawnsForPromotion(whiteTurn);
		
		int row, col;
		
		row = whiteTurn ? 0 : 7;
		
		for(col = 0; col < 8; col++) {
			
			Pawn currentPawn = board.getPawn(row, col);
			
			if(currentPawn instanceof Soldier && currentPawn.isWhite() == whiteTurn) {
				
				Pawn chosenPawn = GUIUtils.choosePawnForPromotion(parent, pawnsForPromotion);
				
				while(chosenPawn == null) {
					JOptionPane.showMessageDialog(parent, "Promotion is mandatory. You have to choose a pawn.");
					chosenPawn = GUIUtils.choosePawnForPromotion(parent, pawnsForPromotion);
				}
				
				PromotionUtils.makePromotion(board, (Soldier) currentPawn, chosenPawn);					
			}

		}
	}
	
	private static void makePromotion(Board board, Soldier soldier, Pawn returningPawn) {
		
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
	}
	
	
}
