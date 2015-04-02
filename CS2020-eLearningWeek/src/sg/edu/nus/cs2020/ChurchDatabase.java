package sg.edu.nus.cs2020;

import java.util.*;

public class ChurchDatabase {
	final static long REQ_CHAKRA = 1000000000;
	public ArrayList<Devotee> devotees;

	/**
	 * @Constructor
	 * Creates a new database that holds an empty ArrayList of Devotees
	 * 
	 */
	public ChurchDatabase() {
		devotees = new ArrayList<Devotee>();
	}
	
	/**
	 * Adds a devotee into the ArrayList
	 * 
	 * @param devotee - devotee to be added
	 */
	public void addDevotee(Devotee devotee) {
		if (devotee == null) System.err.println("Null devotee");
		
		devotees.add(devotee);
	}

	/**
	 * Returns if there are two devotees in the ArrayList with the same karma.
	 * 
	 * The algorithm works as follows: 
	 * 1. We sort the devotees based on karma
	 * 2. We keep a runner pointer that is one element ahead of the current pointer
	 * 3. If the value runner is pointing to is the same as the current value, we have a compatible pair
	 * 
	 * Runs in O(nlogn) time because of the sorting.
	 * 
	 * @return true - has compatible pair
	 * @return false - has no compatible pair
	 */
	public boolean hasCompatiblePair() {
		if (devotees.isEmpty()) System.err.println("Empty devotees list!");
		
		// Sort devotees based on karma
		Collections.sort(devotees);

		// Runner pointer
		int runner = 1;
		
		for (int i = 0; i < devotees.size(); i++) {
			// If runner exceeds ArrayList size
			if (runner == devotees.size()) {
				return false;
			}
			
			// If the runner pointer is equal to the current element
			if (devotees.get(i).getKarma() == devotees.get(runner).getKarma()) {
				return true;
			}
			
			runner++;
		}
		return false;
	}
	
	/**
	 * Returns true if there exist a pair that has a sum of 1000000000 chakra.
	 * 
	 * Algorithm works as follows:
	 * 1. We store the values of chakra into a seperate array
	 * 2. Sort the array
	 * 3. We do a binary search for answer, checking if the sum of element is equal to the required
	 * 	  chakra to be complementary.
	 * 4. We return true once we encounter a pair.
	 * 
	 * @return true - has complementary pair
	 * @return false - has no complementary pair
	 */
	public boolean hasComplementaryPair() {
		if (devotees.isEmpty()) System.err.println("Empty devotees list!");

		long[] arr = sortChakra();
		
		int begin, middle, end;
		
		begin = 0;
		end = arr.length-1;
		
		while (begin < end) {
			middle = (begin + end) / 2;
			if (arr[middle] + arr[begin] < REQ_CHAKRA) {
				begin = middle;
			} else if (arr[middle] + arr[begin] > REQ_CHAKRA) {
				end = middle;
			} else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if there exist a set of people whose chakra sums up to 1000000000 (REQ_CHAKRA)
	 * 
	 * Algorithm is as follow:
	 * 1. Store all chakra values in a seperate array and sort it
	 * 2. Keep a variable for the current sum of chakra
	 * 3. Keep another pointer called turtle, initially at the start of the array
	 * 4. Starting from the front, add the current value to the global sum
	 * 5. If the global sum exceeds REQ_CHAKRA, minus off the value from arr[turtle] and increment turtle
	 * 6. Return true if the sum is equals to REQ_CHAKRA
	 * 
	 * @return true - has complementary set
	 * @return false - has no complementary set
	 */
	public boolean hasComplementarySet() {
		if (devotees.isEmpty()) System.err.println("Empty devotees list!");
		
		long[] arr = sortChakra();
		
		long sum = arr[0];
		int turtle = 0;
		int hare = 1;
		
		while (turtle < hare) {
			if (sum < REQ_CHAKRA) {
				sum += arr[hare];
				hare++;
			} else if (sum > REQ_CHAKRA) {
				sum -= arr[turtle];
				turtle++;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Saves the chakra of each devotee and stores them in an array before sorting them.
	 * 
	 * @return array of sorted long integers
	 */
	private long[] sortChakra() {
		long[] arr = new long[devotees.size()];
		
		for (int i = 0; i < devotees.size(); i++) {
			arr[i] = devotees.get(i).getChakra();
		}
		Arrays.sort(arr);
		return arr;
	}
	
	// Basic test cases
	public static void main(String[] args) {
		// Check for compatible pair
		ChurchDatabase compatible = new ChurchDatabase();
		
		compatible.addDevotee(new Devotee("Cacho", 20, 1));
		compatible.addDevotee(new Devotee("Muchos", 20, 1));
		
		for (int i = 0; i < 1000000; i++) {
			compatible.addDevotee(new Devotee("Clone", 21 + i, 1)); // No other devotees will have the same karma
		}
		
		System.out.println(compatible.hasCompatiblePair());
		
		// Check for complementary pair
		ChurchDatabase complementary = new ChurchDatabase();
		
		complementary.addDevotee(new Devotee("Hola", 20, 500000000)); 
		complementary.addDevotee(new Devotee("Kobo", 21, 500000000));
		
		for (int i=0; i < 1000000; i++) {
			complementary.addDevotee(new Devotee("Clones", 
					(long) (Math.random() * 40), // Any karma will do. 
					(long) (Math.random() * 5) // Small amount of chakra, will not add up to REQ_CHAKRA
					)
			);  
		}
		
		System.out.println(complementary.hasComplementaryPair());
		
		// Check for complementary set
		ChurchDatabase complementarySet = new ChurchDatabase();
		
		complementarySet.addDevotee(new Devotee("Pika", 1, 125020));
		complementarySet.addDevotee(new Devotee("Chu", 1, 29582923));
		complementarySet.addDevotee(new Devotee("Chew", 1, 970292057));
		
		for (int i=0; i < 35; i++) {
			complementarySet.addDevotee(new Devotee("Clone", 1, 1));
		}

		System.out.println(complementary.hasComplementarySet());
	}
}
