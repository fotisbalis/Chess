package pawn;

public enum PawnColor {
	WHITE,
	BLACK;

	public PawnColor opposite() {
		return this == WHITE ? BLACK : WHITE;
	}
}
