package ai;

import java.util.ArrayList;

import pawn.*;
import utils.*;
import board.*;
import move.*;

public class AI {

	public static Move chooseMove(Board board, PawnColor aiColor){
		
		ArrayList<Move> legalMoves = AIUtils.legalMoves(board, aiColor);
	    int max_score = Integer.MIN_VALUE;
	    Move best_move = null;
		
	    if(legalMoves.isEmpty()) {
	        return null;
	    }
	    
	    for(Move move : legalMoves) {
	    	Pawn pawn = board.getPawn(move.getStartingRow(), move.getStartingCol());
	    	
	    	Board tmpBoard = MovesUtils.simulateMove(board, pawn, move.getTargetRow(), move.getTargetCol());
	    	Pawn movedPawn = tmpBoard.getPawn(move.getTargetRow(), move.getTargetCol());
	    	
	    	int tmpScore = AIUtils.boardScore(tmpBoard, aiColor);
	    	
	    	if(AIUtils.isPawnInDanger(tmpBoard, movedPawn))
	    		tmpScore -= movedPawn.getPawnValue() / 2;
	    	
	    	if(pawn instanceof King && CastlingUtils.isMoveCastling((King) pawn, move.getTargetRow(), move.getTargetCol()))
	    		tmpScore += 500;
	    	
	    	if(pawn instanceof Soldier && EnPassantUtils.isMoveEnPassant(board, (Soldier) pawn, move.getTargetRow(), move.getTargetCol()))
	    		tmpScore += 100;
	    	
	    	int promotionRow = pawn.getColor() == PawnColor.WHITE ? 0 : 7;
	    	if(pawn instanceof Soldier && move.getTargetRow() == promotionRow)
	    		tmpScore += 10000;
	    		
	    	if(tmpScore > max_score) {
	    		max_score = tmpScore;
	    		best_move = move;
	    	}
	    }
	    
	    return best_move;
	}
	
}
