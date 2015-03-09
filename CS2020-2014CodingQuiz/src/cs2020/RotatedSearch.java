package cs2020;

/**
 * Class containing the static searchMax method for finding the 
 * maximum in a sorted, but rotated, array.
 * @author gilbert
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
	public static T searchMax() {
		
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
