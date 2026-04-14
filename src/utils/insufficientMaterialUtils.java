package utils;

import java.util.ArrayList;

import pawn.*;

public class insufficientMaterialUtils {
	
	public static Pawn getNonKingPiece(ArrayList<Pawn> pawns) {
	    
		if(pawns.size() != 2)
			return null;
		
		for(Pawn p : pawns)
	        if(!(p instanceof King))
	            return p;

	    return null;
	}
	
	public static boolean sameColorSquare(Pawn p1, Pawn p2) {
	    
		return (p1.getRow() + p1.getCol()) % 2 == (p2.getRow() + p2.getCol()) % 2;
	}
}
