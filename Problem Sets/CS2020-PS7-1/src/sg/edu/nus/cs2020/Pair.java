package sg.edu.nus.cs2020;

public class Pair {
	public int row;
	public int col;
	
	public Pair(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean equals(Pair o) {
		if (this.row == o.row && this.col == o.col) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return row ^ col;
	}
	
	@Override
	public String toString() {
		String result;
		result = Integer.toString(row) + "," + Integer.toString(col);
		
		return result;
	}
}
