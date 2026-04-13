package utils;

import pawn.*;

import java.util.ArrayList;

public class CapturedUtils {

	public static ArrayList<Pawn> getCurrentPlayerCaptured(ArrayList<Pawn> captured, boolean whiteTurn){
		
		ArrayList<Pawn> currentPlayerCaptured = new ArrayList<Pawn>();
		
		for(Pawn p : captured)
			if(p.isWhite() == whiteTurn && !(p instanceof Soldier))
				currentPlayerCaptured.add(p);
				
		return currentPlayerCaptured;
	}
}
