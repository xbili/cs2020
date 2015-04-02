package sg.edu.nus.cs2020;

public class QuickSort {
	int[] numbers;
	int number;

	public void sort(int[] values) {
		if (values == null || values.length == 0)
			System.err.println("Something's wrong with your array to sort.");

		this.numbers = values;
		number = values.length;
		quicksort(0, number - 1);
	}

	private void quicksort(int low, int high) {
		int i = low, j = high, pivot = numbers[(low + high) / 2];

		while (i <= j) {
			while (numbers[i] < pivot)
				i++;
			while (numbers[j] > pivot)
				j--;

			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private void exchange(int i, int j) {
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}
}
