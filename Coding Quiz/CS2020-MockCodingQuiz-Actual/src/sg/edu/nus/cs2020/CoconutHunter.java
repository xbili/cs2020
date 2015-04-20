package sg.edu.nus.cs2020;


import java.util.ArrayList;

public class CoconutHunter {

	public static int findLightCoconut(BunchOfCoconuts coconuts) {
		int n = coconuts.getNumCoconuts();
		return findLightCoconutRecursion(coconuts, 0, n);
	}

	/**
	 * Recursive method that is called on the partition of the coconuts that
	 * contain the light coconut
	 * 
	 * @param coconuts
	 *            The bunch of coconuts
	 * @param pos
	 *            The leftmost position of the partition of coconut
	 * @param nCoconuts
	 *            The number of coconuts in the partition
	 * @return the index of the light coconut in the partition
	 */
	public static int findLightCoconutRecursion(BunchOfCoconuts coconuts,
			int pos, int nCoconuts) {
		if (nCoconuts == 1) {
			return pos; // Base case: only one coconut is left
		} else {
			// Find the size of each partition, note that the value is rounded
			// up to prevent forming a partition with size 0
			int partitionSize = (nCoconuts + 2) / 3;

			// Tripartitie the bunch of coconuts, and put the first two
			// partitions on the balance. Note that we must make sure that both
			// lists have the same number of elements
			ArrayList<Integer> leftList = new ArrayList<>();
			ArrayList<Integer> rightList = new ArrayList<>();
			for (int i = pos; i < pos + partitionSize; i++) {
				leftList.add(i);
			}
			for (int i = pos + partitionSize; i < pos + 2 * partitionSize; i++) {
				rightList.add(i);
			}
			int result = coconuts.balance(leftList, rightList);

			// Iterate on the appropriate partition upon balancing
			if (result == -1) {
				return findLightCoconutRecursion(coconuts, pos, partitionSize);
			} else if (result == 1) {
				return findLightCoconutRecursion(coconuts, pos + partitionSize,
						partitionSize);
			} else {
				return findLightCoconutRecursion(coconuts, pos + 2
						* partitionSize, nCoconuts - 2 * partitionSize);
			}
		}
	}

	public static void main(String[] args) {
		BunchOfCoconuts coconuts = new BunchOfCoconuts(100000, 10);
		System.out.println(findLightCoconut(coconuts));
		System.out.println(coconuts.getNumBalance());
		
	}
}
