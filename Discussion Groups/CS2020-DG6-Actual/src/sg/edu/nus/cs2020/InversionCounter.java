package sg.edu.nus.cs2020;

import java.util.Arrays;

public class InversionCounter {
	public static int countInversions(int[] a) {
		int[] newArray = Arrays.copyOf(a, a.length);
		return countInversionsRecursion(newArray, 0, a.length - 1);
	}

	/**
	 * Conduct merge sort and count the number of inversions at the same time
	 * 
	 * @param a
	 *            the entire array
	 * @param leftB
	 *            left most index of the subarray that we are currently
	 *            iterating
	 * @param rightB
	 *            right most index of the subarray that we are curerntly
	 *            iterating
	 * @return inversion count
	 */
	public static int countInversionsRecursion(int[] a, int leftB, int rightB) {
		if (leftB == rightB) {
			return 0;
		}
		int mid = (leftB + rightB) / 2;
		int invCount = 0;
		invCount += countInversionsRecursion(a, leftB, mid);
		invCount += countInversionsRecursion(a, mid + 1, rightB);
		invCount = mergeArrays(a, leftB, rightB, mid, invCount);
		return invCount;
	}

	public static int mergeArrays(int[] a, int leftB, int rightB, int mid,
			int invCount) {
		int[] copy = new int[rightB - leftB + 1];

		// p1 and p2 are the pointers at each subarray
		int p1 = leftB, p2 = mid + 1;
		for (int i = 0; i < copy.length; i++) {
			// Case 1: Copy element from the second subarray
			if (p1 == mid + 1 || (p2 != rightB + 1 && a[p2] < a[p1])) {
				copy[i] = a[p2];
				p2++;
				invCount += mid + 1 - p1; // Add the number of inversions
											// attributed by this element
			} else {
				// Case 2: Copy element from the first subarray
				copy[i] = a[p1];
				p1++;
			}
		}
		for (int i = 0; i < copy.length; i++) {
			a[leftB + i] = copy[i];
		}
		return invCount;
	}

	public static void main(String[] args) {
		int[] test = { 7, 6, 4, 5, 3, 2, 1 };
		System.out.println(countInversions(test));
	}
}
