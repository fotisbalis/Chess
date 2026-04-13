package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

import pawn.*;

public class ImagesUtils {
	
	public static ImageIcon getImage(Pawn pawn) {
		
		ImageIcon whiteKingIcon = new ImageIcon("resources/white_king.png");
		ImageIcon blackKingIcon = new ImageIcon("resources/black_king.png");
		ImageIcon whiteQueenIcon = new ImageIcon("resources/white_queen.png");
		ImageIcon blackQueenIcon = new ImageIcon("resources/black_queen.png");
		ImageIcon whiteBishopIcon = new ImageIcon("resources/white_bishop.png");
		ImageIcon blackBishopIcon = new ImageIcon("resources/black_bishop.png");
		ImageIcon whiteKnightIcon = new ImageIcon("resources/white_knight.png");
		ImageIcon blackKnightIcon = new ImageIcon("resources/black_knight.png");
		ImageIcon whiteRookIcon = new ImageIcon("resources/white_rook.png");
		ImageIcon blackRookIcon = new ImageIcon("resources/black_rook.png");
		ImageIcon whiteSoldierIcon = new ImageIcon("resources/white_soldier.png");
		ImageIcon blackSoldierIcon = new ImageIcon("resources/black_soldier.png");
		
        switch(pawn.getPawnType()) {
        	case "Soldier": return pawn.isWhite() ? whiteSoldierIcon : blackSoldierIcon;
            case "Rook": return pawn.isWhite() ? whiteRookIcon : blackRookIcon;
            case "Knight": return pawn.isWhite() ? whiteKnightIcon : blackKnightIcon;
            case "Bishop": return pawn.isWhite() ? whiteBishopIcon : blackBishopIcon;
            case "Queen": return pawn.isWhite() ? whiteQueenIcon : blackQueenIcon;
            case "King": return pawn.isWhite() ? whiteKingIcon : blackKingIcon;
        }

        return null;
    }
	
	public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {

		Image img = icon.getImage();
	    Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

	    return new ImageIcon(scaled);
	}
	
}
