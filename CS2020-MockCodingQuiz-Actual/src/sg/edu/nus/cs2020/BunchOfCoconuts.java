package sg.edu.nus.cs2020;

import java.util.*;

public class BunchOfCoconuts {
	private int numBalance, numCoconuts, lightIndex;

	/**
	 * Constructor for BunchOfCoconuts
	 * 
	 * @param n
	 *            number of coconuts
	 * @param index
	 *            index of the lighter coconut. Uses 0-based index
	 */
	public BunchOfCoconuts(int n, int index) {
		// Error checks
		if (n <= 0)
			throw new IllegalArgumentException(
					"You can't have 0 or less coconuts you nutcase!");
		if (index < 0 || index >= n)
			throw new IllegalArgumentException(
					"Stop hacking the coconut constructor you nutcase!");
		if (n > 100000)
			throw new IllegalArgumentException(
					"Sorry I can't find that many coconuts in Singapore...");
		// Error checks end here
		
		numCoconuts = n;
		lightIndex = index;
		numBalance = 0;
	}

	/**
	 * balance(List<Integer> left, List<Integer> right)
	 * Takes in two lists that contain the indices of the coconuts placed on the
	 * left side and the right side of the balance respectively. Note that both
	 * lists need to be of the same size.
	 * 
	 * @param left
	 *            list of indices of coconuts on the left side of the balance
	 * @param right
	 *            list of indices of coconuts on the right side of the balance
	 * @return -1 if the left side of the balance is lighter, 1 if the right
	 *         side of the balance is lighter, 0 if both sides are of equal
	 *         weight.
	 */
	public int balance(List<Integer> left, List<Integer> right) {
		numBalance++;
		
		// Error checks
		if (left.size() != right.size())
			throw new IllegalArgumentException(
					"Number of coconuts are not equal at both sides you nutcase!");
		if (left.size() == 0)
			throw new IllegalArgumentException(
					"You have not placed anything on the coconut yet you nutcase!!");

		Collections.sort(left);
		Collections.sort(right);
		int prev = -999;
		int[] leftOccupy = new int[100010];
		for (Integer i : left) {
			if (i >= numCoconuts || i < 0)
				throw new IllegalArgumentException(
						"Coconut out of bounds you nutcase!");
			if (i == prev)
				throw new IllegalArgumentException(
						"You can't put the same coconut twice at the same side you nutcase!");
			prev = i;
			leftOccupy[i]++;
		}
		prev = -999;
		for (Integer i : right) {
			if (i >= numCoconuts || i < 0)
				throw new IllegalArgumentException(
						"Coconut out of bounds you nutcase!");
			if (i == prev)
				throw new IllegalArgumentException(
						"You can't put the same coconut twice at the same side you nutcase!");
			if (leftOccupy[i] > 0)
				throw new IllegalArgumentException(
						"You can't put the same coconut on both sides you nutcase!");
			prev = i;
		}
		// Error checks end here

		if (left.contains(lightIndex))
			return -1;
		else if (right.contains(lightIndex))
			return 1;
		else
			return 0;
	}
	
	/**
	 * getNumBalance() 
	 * @return returns number of times balance is called
	 */
	public int getNumBalance() {
		return numBalance;
	}

	/**
	 * getNumCoconuts() 
	 * @return returns number of coconuts
	 */
	public int getNumCoconuts() {
		return numCoconuts;
	}

}
