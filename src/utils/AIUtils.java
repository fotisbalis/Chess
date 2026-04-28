package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import board.*;
import pawn.*;
import move.*;

public class AIUtils {
	
	public static ArrayList<Move> legalMoves(Board board, PawnColor aiColor) {
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();

	    for(Pawn pawn : board.getPlayerPawns(aiColor)) {
	    	legalMoves.addAll(pawn.getLegalMoves(board));
	    }
	    
	    AIUtils.sortMoves(board, legalMoves);
	    
	    return legalMoves;
	}

	public static void sortMoves(Board board, ArrayList<Move> moves) {
		Collections.sort(moves, new Comparator<Move>() {
			@Override
			public int compare(Move firstMove, Move secondMove) {
				int firstScore = AIUtils.movePriority(board, firstMove);
				int secondScore = AIUtils.movePriority(board, secondMove);
				
				return Integer.compare(secondScore, firstScore);
			}
		});
	}
	
	public static int boardScore(Board board, PawnColor aiColor) {
		
		int score = 0;
		
		int r, c;
		
		for(r = 0; r < 8; r++) {
			for(c = 0; c < 8; c++) {
				Pawn pawn = board.getPawn(r, c);

	            if(pawn == null) {
	                continue;
	            }

	            int value = pawn.getPawnValue();

	            if(pawn.getColor() == aiColor)
	                score += value;
	            else 
	                score -= value;
			}
		}
		
		return score;
	}

	public static int scoreAfterMove(Board originalBoard, Board board, Move move, PawnColor aiColor) {
		
		int score = AIUtils.boardScore(board, aiColor);
		
		Pawn originalPawn = originalBoard.getPawn(move.getStartingRow(), move.getStartingCol());
		Pawn movedPawn = board.getPawn(move.getTargetRow(), move.getTargetCol());
		
		if(originalPawn == null || movedPawn == null)
			return score;
		
		if(GameCheckUtils.isCheckMate(board, aiColor.opposite()))
			score += 1000000;
     	
		if(!(originalPawn instanceof Soldier) && !(originalPawn instanceof King))
			return score;
		
		int promotionRow = originalPawn.getColor() == PawnColor.WHITE ? 0 : 7;
     	if(originalPawn instanceof Soldier && move.getTargetRow() == promotionRow)
     		score += 10000;
		
     	else if(originalPawn instanceof King && CastlingUtils.isMoveCastling((King) originalPawn, move.getTargetRow(), move.getTargetCol()))
     		score += 1000;
     	
		else if(originalPawn instanceof Soldier && EnPassantUtils.isMoveEnPassant(originalBoard, (Soldier) originalPawn, move.getTargetRow(), move.getTargetCol()))
     		score += 500;
		
     	return score;
	}

	private static int movePriority(Board board, Move move) {
		Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());
		
		if(pawn == null)
			return Integer.MIN_VALUE;
		
		int toRow = move.getTargetRow();
		int toCol = move.getTargetCol();
		int score = 0;
		
		if(!(pawn instanceof Soldier) && !(pawn instanceof King))
			return score;
		
		int promotionRow = pawn.getColor() == PawnColor.WHITE ? 0 : 7;
		if(pawn instanceof Soldier && toRow == promotionRow) {
			score += 10000;
		}
		
		else if(pawn instanceof Soldier && EnPassantUtils.isMoveEnPassant(board, (Soldier) pawn, toRow, toCol))
			score += 500;
		
		else if(pawn instanceof King && CastlingUtils.isMoveCastling((King) pawn, toRow, toCol))
			score += 1000;
		
		return score;
	}
	
}
