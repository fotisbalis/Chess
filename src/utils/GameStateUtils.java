package utils;

import java.util.ArrayList;

import board.*;
import pawn.*;

public class GameStateUtils {
	
	public static ArrayList<BoardState> copyBoardStates(ArrayList<BoardState> boardStates) {
		
		ArrayList<BoardState> copiedBoardStates = new ArrayList<BoardState>();
		
		for(BoardState boardState : boardStates) {
			copiedBoardStates.add(new BoardState(boardState.getBoard(), boardState.getTurnColor()));
		}
		
		return copiedBoardStates;
	}

	public static ArrayList<Pawn> copyCaptured(ArrayList<Pawn> captured) {
		
		ArrayList<Pawn> copiedCaptured = new ArrayList<Pawn>();
		
		for(Pawn pawn : captured) {
			copiedCaptured.add(copyPawn(pawn));
		}
		
		return copiedCaptured;
	}

	public static Pawn copyPawn(Pawn pawn) {
		
		if(pawn == null)
			return null;
		
		if(pawn instanceof King) {
			King king = new King(pawn.getRow(), pawn.getCol(), pawn.getColor());
			king.setHasMoved(pawn.hasMoved());
			return king;
		}
		
		if(pawn instanceof Queen)
			return new Queen(pawn.getRow(), pawn.getCol(), pawn.getColor());
		
		if(pawn instanceof Rook) {
			Rook rook = new Rook(pawn.getRow(), pawn.getCol(), pawn.getColor());
			rook.setHasMoved(pawn.hasMoved());
			return rook;
		}
		
		if(pawn instanceof Bishop)
			return new Bishop(pawn.getRow(), pawn.getCol(), pawn.getColor());
		
		if(pawn instanceof Knight)
			return new Knight(pawn.getRow(), pawn.getCol(), pawn.getColor());
		
		if(pawn instanceof Soldier) {
			Soldier oldSoldier = (Soldier) pawn;
			Soldier soldier = new Soldier(pawn.getRow(), pawn.getCol(), pawn.getColor());
			soldier.setHasMoved(oldSoldier.hasMoved());
			soldier.setMoveCount(oldSoldier.getMoveCount());
			return soldier;
		}
		
		return null;
	}
}
