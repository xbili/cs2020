package sg.edu.nus.cs2020;

public class Tower implements Comparable<Tower> {
	// Position of Tower
	private int position;
	// Range of Tower
	private int range;
	// Left and Right bound of Tower
	private int left_bound;
	private int right_bound;
	
	/**
	 * @Constructor
	 * Initalizes a new Tower, and calculates left and right bound.
	 * 
	 * - IF left bound < 0, we take it as it is zero
	 * 
	 * NOTE: We cannot deal with right_bound upon instantiation because we have yet to know
	 * 		 the maximum highway length yet.
	 * 
	 */
	public Tower(int position, int range) {
		this.position = position;
		this.range = range;
		this.right_bound = position + range;
		
		if (position - range < 0) {
			this.left_bound = 0;
		} else {
			this.left_bound = position - range;
		}
	}
	
	/**
	 * Getter methods for properties:
	 * - position
	 * - range
	 * - left_bound
	 * - right_bound
	 * 
	 * Encapsulation of the private properties!
	 */
	
	public int getPosition() {
		return position;
	}
	
	public int getRange() {
		return range;
	}

	public int getLeftBound() {
		return left_bound;
	}

	public int getRightBound() {
		return right_bound;
	}
	
	/**
	 * @Override
	 * We compare the towers by their left bounds, so that the tower list will be sorted
	 * by the location of their left bounds.
	 * 
	 * @param o is the other tower to compare to
	 * @return
	 */
	public int compareTo(Tower o) {
		// Comparing between the left bounds of the Towers in order to be sorted.
		if (this.getLeftBound() == o.getLeftBound()) {
			return 0;
		} else if (this.getLeftBound() < o.getLeftBound()) {
			return -1;
		} else if (this.getLeftBound() > o.getLeftBound()) {
			return 1;	
		}
		
		return 0;
	}
	
}
