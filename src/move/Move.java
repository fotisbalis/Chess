package move;

public class Move {
	
	protected int fromRow, fromCol, toRow, toCol;
	
	public Move(int fromRow, int fromCol, int toRow, int toCol) {
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}
	
	public int getStartingRow() {
		return fromRow;
	}
	
	public int getStartingCol() {
		return fromCol;
	}
	
	public int getTargetRow() {
		return toRow;
	}

	public int getTargetCol() {
		return toCol;
	}

}
