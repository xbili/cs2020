package sg.edu.nus.cs2020;

public class BinarySearch {
	
	/**
	 * DG 4 actual
	 * 
	 * Returns key in array.
	 * 
	 * Systematic way: 
	 * 	1. Initialize variables.
	 * 	2. Loop invariant - make into a loop condition (while begin < end)
	 * 	3. Get middle element - make use of the better solution in DG4
	 * 	4. Reset begin and end
	 * 
	 * @param a - array to search in 
	 * @param key - key to search for
	 * @return
	 */
	public static int binarySearch(int [] a, int key) {
		// Starting index
		int begin = 0;
		// Ending index
		int end = a.length - 1;
		
		// Loop invariant comes in here
		while (begin < end) {
			// Get middle element
			int mid = (end + begin) / 2; // or use the newer one learnt in DG to prevent overflow
			int midVal = a[mid];
			
			// Resets the begin and end
			if (midVal < key) {
				begin = mid + 1; // This prevents infinite loop, example of begin == 0 && end == 1
			} else if (midVal > key) {
				end = mid - 1;
			} else {
				return mid; // key is found
			}
			
		}
		
		return -1;
	}
	
	
	/**
	 * Problem 3.
	 * 
	 * @param key
	 * @param array
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean unsortedSearch(int key, int[] array, int begin, int end) {
		// Base case:
		if (begin >= end) return (key == array[begin]);
		
		// Check mid-point of the array
		int mid = (begin + (end - begin) / 2);
		if (key == mid) return true;
		
		// Do recursive searching
		boolean leftSearch = unsortedSearch(key, array, begin, mid - 1);
		boolean rightSearch = unsortedSearch(key,  array,  mid + 1, end);
		
		// Check return value
		if (leftSearch || rightSearch) return true;
		else return false;
	}
	
	public static void main(String[] args) {
		/**
		 * Problem 2
		 */
		int high = 100;
		int low = 500;
		
		// Method 1 
		int middle = (high + low) / 2;
		
		// Method 2
		int middle2 = low + (high - low) / 2;
		
		System.out.println("Method 1: " + middle);
		System.out.println("Method 2: " + middle2);
		
	}
}
