package sg.edu.nus.cs2020;

public class BinarySearch {
	public static boolean contains(int[] a, int b) {
		if (a.length == 0) {
			return false;
		}
		
		int low = 0;
		int high = a.length - 1;

		while (low < high) {
			int middle = (low + high) / 2;
			if (b > a[middle]) {
				low = middle + 1;
			} else if (b < a[middle]) {
				high = middle;
			} else { // The element has been found
				return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 3, 4, 5 };

		System.out.println(contains(test, 3));
	}
}
