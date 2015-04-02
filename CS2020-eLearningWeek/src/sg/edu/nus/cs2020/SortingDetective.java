package sg.edu.nus.cs2020;

import java.util.Arrays;

public class SortingDetective {
	public static boolean isStable(ISort sorter, int size) {
		// Generate Sample
		IntegerWithKey[] sample = new IntegerWithKey[size];

		for (int i = 0; i < size; i++) {
			sample[i] = new IntegerWithKey((int) Math.random() * size);
		}

		// Clone the array
		IntegerWithKey[] clone = sample.clone();

		// Attempt to sort SAMPLE array using sorter
		sorter.sort(sample);

		// Sort CLONE using a known and stable sorter
		Arrays.sort(clone);

		// If both Arrays are equivalent, then the sorter is stable.
		for (int i = 0; i < size; i++) {
			if (sample[i].getKey() != clone[i].getKey()) {
				return false;
			}
		}
	
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isStable(new SorterA(), 100));
		System.out.println(isStable(new SorterB(), 100));
		System.out.println(isStable(new SorterC(), 100));
		System.out.println(isStable(new SorterD(), 100));
		System.out.println(isStable(new SorterE(), 100));
		System.out.println(isStable(new SorterF(), 100));
	}
}
