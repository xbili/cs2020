package sg.edu.nus.cs2020;

public class CountInversion extends MergeSort {
	public static void main(String[] args) {
		// Test set s1
		int[] s1 = {7, 6, 5, 4, 3, 2, 1};
		
		MergeSort sorter = new MergeSort();
		sorter.sort(s1);
		
		System.out.println(sorter.getInversions());
	}
}
