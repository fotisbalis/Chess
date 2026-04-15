package utils;

import pawn.*;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CapturedUtils {

	public static ArrayList<Pawn> getCurrentPlayerCaptured(ArrayList<Pawn> captured, PawnColor color){
		
		ArrayList<Pawn> currentPlayerCaptured = new ArrayList<Pawn>();
		
		for(Pawn p : captured)
			if(p.getColor() == color)
				currentPlayerCaptured.add(p);
				
		return currentPlayerCaptured;
	}
	
	public static ArrayList<Pawn> getSpecificTypeCaptured(ArrayList<Pawn> captured, PawnType pawnType){
		
		ArrayList<Pawn> capturedType = new ArrayList<Pawn>();
		
		for(Pawn p : captured) {
			if(p.getPawnType() == pawnType)
				capturedType.add(p);
		}
		
		return capturedType;
	}
	
	public static void addCapturedGroup(JPanel panel, ArrayList<Pawn> capturedPawns) {
		PawnType[] pieceTypes = {
			PawnType.QUEEN,
			PawnType.ROOK,
			PawnType.BISHOP,
			PawnType.KNIGHT,
			PawnType.SOLDIER,
			PawnType.KING
		};

		for(PawnType pieceType : pieceTypes) {
			ArrayList<Pawn> capturedByType = CapturedUtils.getSpecificTypeCaptured(capturedPawns, pieceType);

			if(capturedByType.isEmpty())
				continue;

			Pawn samplePawn = capturedByType.get(0);
			ImageIcon img;
			if(samplePawn instanceof Soldier)
				img = ImagesUtils.resizeIcon(ImagesUtils.getImage(samplePawn), 30, 60);
			else if(samplePawn instanceof Queen || samplePawn instanceof King)
				img = ImagesUtils.resizeIcon(ImagesUtils.getImage(samplePawn), 40, 70);
			else
				img = ImagesUtils.resizeIcon(ImagesUtils.getImage(samplePawn), 35, 65);
			
			JLabel label = new JLabel("x" + capturedByType.size(), img, SwingConstants.LEFT);
			label.setHorizontalTextPosition(SwingConstants.RIGHT);
			label.setIconTextGap(8);
			label.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(label);
		}
	}
	
}
