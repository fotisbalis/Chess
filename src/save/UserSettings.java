package save;

import java.io.Serializable;

public class UserSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	private final boolean highlightMoves;
	private final boolean autoQueenPromotion;

	public UserSettings(boolean highlightMoves, boolean autoQueenPromotion) {
		this.highlightMoves = highlightMoves;
		this.autoQueenPromotion = autoQueenPromotion;
	}

	public boolean isHighlightMovesEnabled() {
		return highlightMoves;
	}

	public boolean isAutoQueenPromotionEnabled() {
		return autoQueenPromotion;
	}
}
