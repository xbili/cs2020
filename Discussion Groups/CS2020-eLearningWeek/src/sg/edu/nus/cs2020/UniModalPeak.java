package sg.edu.nus.cs2020;

import java.util.Arrays;

public class UniModalPeak {
	private int[] arr;
	
	public UniModalPeak(int[] arr) {
		this.arr = Arrays.copyOf(arr, arr.length);
	}
	
	public int findPeakIndex() {
		int start, end, middle;
		start = 0;
		end = arr.length - 1;
		
		while (start < end - 1) {
			middle = (start + end) / 2;
			if (arr[start] > arr[middle]) {
				end = middle;
			} else if (arr[start] < arr[middle]) {
				start = middle + 1;
			} 
		}
		
		return start;
	}
	
	public static void main(String[] args) {
		int[] test = {1, 2, 4, 6, 5, 3};
		
		UniModalPeak peak = new UniModalPeak(test);
		System.out.println(peak.findPeakIndex());
	}
}
