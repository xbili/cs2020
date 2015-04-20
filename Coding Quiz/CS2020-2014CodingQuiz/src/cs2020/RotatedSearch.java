package cs2020;

/**
 * Class containing the static searchMax method for finding the 
 * maximum in a sorted, but rotated, array.
 * @author Xu Bili
 *
 */
public class RotatedSearch {
	
	/**
	 * searchMax
	 * @param list an array of items
	 * Takes a list of items and returns the maximum item in the list.
	 * This is a generic method, and works for an array of any type of items.
	 * This is a static method.
	 * 
	 * The list of items is sorted from smallest to largest, but it has been
	 * rotated, i.e., the smallest item is not in array slot 0 and the sequence
	 * wraps around the end of the array.
	 */
	public static <T extends Comparable<T>> T searchMax(T[] arr) {
		if (arr == null) System.err.println("Null array.");
		
		int start = 0,
			end = arr.length - 1;
		
		while (start < end) {
			int middle = (start + end) / 2;
			if (end - start == 1) {
				// Return the larger element
				if (arr[start].compareTo(arr[end]) > 0) {
					end = start;
				} else {
					start = end;
				}
			} else if (arr[start].compareTo(arr[middle]) > 0) {
				end = middle;
			} else if (arr[start].compareTo(arr[middle]) < 0) {
				start = middle;
			} else  {
				start = middle;
			}
		}
		
		return arr[start];
	}

	
	public static void main(String[] args){
		
		Integer[] integers = {39, 47, 53, 3, 13, 14, 16, 18, 25, 31};
		System.out.println(searchMax(integers));
		// Prints: 53
		
		Double[] doubles = {16.69, 23.89, 27.61, 33.05, 34.48, 36.63, 46.62, 5.96, 8.3, 11.44};
		System.out.println(searchMax(doubles));
		// Prints: 46.62
		
		String[] names = {"Franny", "Glen", "Harry", "Isabelle", "Julia", "Alice", "Bob", "Collin", "David", "Elissa"};
		System.out.println(searchMax(names));
		// Prints: Julia
		
	}

}
