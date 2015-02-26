package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Collections;

public class CoverageCalculator {
	// Length of the highway
	private int highwayLength;
	// ArrayList of Tower present along the highway
	private ArrayList<Tower> towers;

	/**
	 * @Constructor
	 * Initializes private properties and creates a new ArrayList for towers.
	 * 
	 * @param highwayLength
	 */
	public CoverageCalculator(int highwayLength) {
		this.highwayLength = highwayLength;
		this.towers = new ArrayList<Tower>();
	}
	
	/**
	 * Adds a tower at a specific location. 
	 * 
	 * @param location
	 * @param range
	 */
	public void addTower(int location, int range) {
		// If location is out of bounds of highway
		if (location > highwayLength || location < 0) {
			System.out.println("Error! Location out of highway length.");
		} else {
			Tower newTower = new Tower(location, range);
			towers.add(newTower);
		}
	}
	
	/**
	 * Calculates the coverage by all of the current present towers.
	 * 
	 * The algorithm is as follows:
	 * 1. The tower is sorted based on their left bound. Sorting takes O(n logn).
	 * 2. We iterate through each tower and at each tower:
	 * 
	 * 		- IF right bound > maxRightBound, means the total coverage of that block increased
	 * 			* Therefore we update maxRightBound to the value of the right bound
	 * 
	 * 		- IF left bound > maxRightBound, means there is a gap in coverage.
	 * 		  	* Therefore we add the current coverage block to the result, and reset the left and right bounds of the current coverage block
	 * 
	 * 		- IF left bound < minLeftBound (this shouldn't happen, but just in case sorting is screwed up), it means that
	 * 		  the range is growing larger to the left. 
	 * 			* Therefore we change the value of minLeftBound to the smaller left bound. 		
	 * 
	 * 3. We add the currentRange into the results from the previous fragments, and that is the total coverage by the
	 * 	  cellular towers.
	 * 
	 * This algorithm runs in O(n logn) because the sorting implementation of Java runs in O(n logn), and the updating
	 * of the coverage value runs in O(n) time, where n is the number of towers.
	 * 
	 * Thus the overall time complexity is O(n logn).
	 * 
	 * @return result: the total coverage of the cellular towers
	 */
	public int getCoverage() {
		// If there are no towers along the highway, simply return 0.
		if (towers.size() == 0) return 0;
		
		// Sort the Towers according to their left bound (check Tower's compareTo implementation)
		// O(n logn)
		Collections.sort(towers);
		
		// Left and right bound of the coverage blocks
		int maxRightBound = towers.get(0).getRightBound();
		int minLeftBound = towers.get(0).getLeftBound();
		
		// Result of fragments of coverage blocks
		int result = 0;
		// Coverage of the current block
		int currentBlock = 0;
		
		// For each tower in the ArrayList
		for (int i = 0; i < towers.size(); i++) {
			// Left and right bound of the current tower
			int left_bound = towers.get(i).getLeftBound();
			int right_bound = towers.get(i).getRightBound();
			
			// If right_bound exceeds the max value, set it back to highwayLength
			if (right_bound > highwayLength) {
				right_bound = highwayLength;
			}
			
			// If the maxRightBound exceeds the max value, set it back to highwayLength
			if (maxRightBound > highwayLength) {
				maxRightBound = highwayLength;
			}
			
			// If there is a gap in the coverage, we add the block of coverage to our result, and set the current left_bound
			// as the left bound of the next coverage block, and the current right_bound as the right bound of the next coverage block.
			if (left_bound > maxRightBound) {
				result += currentBlock;
				minLeftBound = left_bound;
				maxRightBound = right_bound;
			}
			
			// If the left bound is smaller than the minLeftBound, it means that the total coverage of the current
			// block is increased as well.
			//
			// NOTE: This should not happen if the sort algorithm works. Because the Towers should be sorted by their
			// 		 left bounds.
			if (left_bound < minLeftBound) {
				minLeftBound = left_bound;
			}
			
			// If the right bound is bigger or equal to the maxRightBound, it means that the total coverage of the current block
			// is increased.
			if (right_bound > maxRightBound) {
				maxRightBound = right_bound;
			}
			
			// Updates the current coverage block
			currentBlock = maxRightBound - minLeftBound;
		}
		
		// We have to add the currentRange into the results that we previously obtained from fragments of coverage.
		result += currentBlock;
		
		return result;
	}
}
