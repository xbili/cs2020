package sg.edu.nus.cs2020;

public class CountInversion extends MergeSort {
	public static void main(String[] args) {
		// Test set s1
		int[] s1 = {1, 2, 10, 5, 3};
		
		MergeSort sorter = new MergeSort();
		sorter.sort(s1);
		
		System.out.println(sorter.getInversions());
	}
}
