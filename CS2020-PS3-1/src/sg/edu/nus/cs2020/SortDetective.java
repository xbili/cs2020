package sg.edu.nus.cs2020;

import java.util.Arrays;

/**
 * SortDetective to determine the identities of each sorter provided.
 * 
 * The main workhorse of this detective is in the method `checkDifferentSizes`.
 * To put the sorters to a rigorous test to decide who is DrEvil, and the identities of each
 * sort algorithm, we pass in arrays of different lengths.
 * 
 * Explanation and analysis of results is in the text accompanied with the submission.
 * 
 * @author xbili
 *
 */
public class SortDetective {
	private static StopWatch timer = new StopWatch();
	private static float current_time = 0;
	
	/**
	 * This method makes use of the Pair class defined.
	 * 
	 * The algorithm should sort the array of pairs in such a way that the relative order of the values will be
	 * preserved.
	 * 
	 * This method compares the BEFORE and AFTER state of the array of Pairs with their head set as the same value. 
	 * If they are in different order after sorting, means that the algorithm is not stable.
	 * 
	 * @param sorter used to sort
	 * @param size of input
	 * @return true if sorting is stable
	 */
	public boolean isStable(ISort sorter, int size) {
		Pair[] testPairArray = new Pair[size];
		
		// All entries will have 1 as the head and the index of their position as i
		for (int i = 0; i < size; i++) {
			testPairArray[i] = new Pair(1, i);
		}
		
		// Change the head value of the five elements so that sorting will be executed, and swaps
		// will occur if there is any.
		for (int i = 0; i < 5; i++) {
			testPairArray[(int) (Math.random() * size)].setHead(2);
		}
		
		// Array of all the tails of elements with head == 2 before and after the sort
		int[] tailsBeforeSorted = new int[5];
		int[] tailsAfterSorted = new int[5];
		
		// Saves the order of the indexes of element with head == 2
		for (int i = 0; i < size; i++) {
			int count = 0;
			if (testPairArray[i].getHead() == 2) {
				tailsBeforeSorted[count] = testPairArray[i].getTail();
				count++;
			}
		}
		
		// Carry out sorting
		sorter.sort(testPairArray);
		
		// Save the all the tails into an array for comparison
		for (int i = 0; i < size; i++) {
			int count = 0;
			if (testPairArray[i].getHead() == 2) {
				tailsAfterSorted[count] = testPairArray[i].getTail();
				count++;
			}
		}
		
		// Checks if the array of tails before sorted is in the same sequence as arrays of tails after sorted
		// If algorithm is stable, they should be in the same sequence.
		for (int i = 0; i < 5; i++) {
			if (tailsAfterSorted[i] != tailsBeforeSorted[i]) return false;
		}
		
		return true;
	}
	
	/**
	 * Tests if the sorter is working properly by generating a random array and sorting it.
	 * To ensure reliability of results, the same sorter is required to sort the array for 
	 * 20 times correctly before it can be deemed as sorted.
	 * 
	 * NOTE: THIS IS DIFFERENT FROM checkOrder()!
	 * 
	 * @param sorter
	 * @param size
	 * @return
	 */
	public boolean checkSorted(ISort sorter, int size) {
		for (int i = 0; i < 20; i++) {
			// Randomly generated test array
			Integer[] testArray = Arrays.copyOf(generateArrayTestCase(size), size);
			
			// Runs the sort
			runSort(sorter, testArray);
			
			// Checks the order of the sorted array
			if (!checkOrder(testArray, size)) return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if the sorting order of the array is correct. 
	 * i.e. Sorted from the smallest to the largest.
	 * 
	 * NOTE: THIS IS DIFFERENT FROM checkSorted()!
	 * 
	 * This checks for the order of the array ONLY. 
	 * 
	 * ****checkSorted() depends on this method.*****
	 * 
	 * @param testArray: the sorted array to be tested
	 * @param size: size of the sorted array
	 * @return
	 */
	private boolean checkOrder(Integer[] testArray, int size) {
		// Throws an error if the size is not equal to the length of the test array
		if (size != testArray.length) throw new Error("Size and length mismatch.");
		
		// For each item in the list, if it is the last item, array is sorted properly
		// If the next element is larger than the current element, it means that there is a mistake in sorting.
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				return true; 
			} else if (testArray[i + 1] < testArray[i]) {
				System.out.println("Error: " + testArray[i+1] + " is not bigger than " + testArray[i]);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Runs the sorting algorithm and saves the time taken. 
	 * StopWatch is a static variable, since it is used in all of the methods, and isn't object specific. 
	 * 
	 * @param sorter
	 * @param testArray
	 */
	private void runSort(ISort sorter, Integer[] testArray) {
		timer.start();
		
		// Sort algorithm
		sorter.sort(testArray);
		
		timer.stop();
		current_time = timer.getTime();
		timer.reset();
	}
	
	/**
	 * *********************************************************************
	 * **THE MAIN METHOD THAT YOU SHOULD BE RUNNING TO DEDUCE THE SORTERS **
	 * *********************************************************************
	 * 
	 * The main tester to check for speed and efficiency of the sorting algorithms.
	 * 
	 * The sorters will be put through 3 different types of arrays: Random, Sorted, Almost Sorted and Repeats
	 * For each type of array, the sorter will sort arrays of length 10 100 1,000 10,000 100,000 & 1,000,000.
	 * 
	 * It is then observed how much does time take increase over size of test case,
	 * Results are logged out into the console.
	 *  
	 * This method takes VERY LONG to execute due to the length of the test cases. One could remove the last
	 * test case (1 million) to make life easier.
	 * 
	 * @param sorter to be tested
	 */
	private void checkDifferentSizes(ISort sorter) {
		// Array of sizes to be tested
		// NOTE: Remove/add as you wish. Reduces the time taken to test if results are obvious at 100,000.
		int[] sizes = { 10, 100, 1000, 10000, 100000, 1000000 };
		
		// Checks on an already sorted array
		for (int i = 0; i < sizes.length; i++) {
			int currentSize = sizes[i];
			Integer[] testCase = generateSortedTestCase(currentSize);
			
			System.out.println("Testing on a sorted array of length " + currentSize);
			
			System.out.print("Time taken: ");
			
			// Runs the sort algorithm
			runSort(sorter, testCase);
			
			// Only print out time taken for sort if it is correctly sorted
			if (checkOrder(testCase, currentSize)) {
				System.out.println(current_time);
			} else {
				System.out.println("Not sorted properly!");
			}
		}
		
		// Checks on almost sorted array
		for (int i = 0; i < sizes.length; i++) {
			int currentSize = sizes[i];
			Integer[] testCase = generateAlmostSortedTestCase(currentSize);
			
			System.out.println("Testing on an almost sorted array of length " + currentSize);
			
			System.out.print("Time taken: ");
			
			// Runs the sort algorithm
			runSort(sorter, testCase);
			
			// Only print out time taken for sort if it is correctly sorted
			if (checkOrder(testCase, currentSize)) {
				System.out.println(current_time);
			} else {
				System.out.println("Not sorted properly!");
			}
		}
		
		// Checks on random array
		for (int i = 0; i < sizes.length; i++) {
			int currentSize = sizes[i];
			Integer[] testCase = generateArrayTestCase(currentSize);
			
			System.out.println("Testing on array of length " + currentSize);
			
			System.out.print("Time taken: ");
			
			// Runs the sort algorithm
			runSort(sorter, testCase);
			
			// Only print out time taken for sort if it is correctly sorted
			if (checkOrder(testCase, currentSize)) {
				System.out.println(current_time);
			} else {
				System.out.println("Not sorted properly!");
			}
		}
		
		// Checks on worst case array
		for (int i = 0; i < sizes.length; i++) {
			int currentSize = sizes[i];
			Integer[] testCase = generateWorstCaseTestCase(currentSize);
			
			System.out.println("Testing on worst case array of length " + currentSize);
			
			System.out.print("Time taken: ");
			
			// Runs the sort algorithm
			runSort(sorter, testCase);
			
			// Only print out time taken for sort if it is correctly sorted
			if (checkOrder(testCase, currentSize)) {
				System.out.println(current_time);
			} else {
				System.out.println("Not sorted properly!");
			}
		}
		
		// Checks on an array of repeated elements
		for (int i = 0; i < sizes.length; i++) {
			int currentSize = sizes[i];
			Integer[] testCase = generateRepeatTestCase(currentSize);
			
			System.out.println("Testing on array of repeated elements of length " + currentSize);
			
			System.out.print("Time taken: ");
			
			// Runs the sort algorithm
			runSort(sorter, testCase);
			
			// Only print out time taken for sort if it is correctly sorted
			if (checkOrder(testCase, currentSize)) {
				System.out.println(current_time);
			} else {
				System.out.println("Not sorted properly!");
			}
		}
	}
	
	/**
	 * The following methods generates test cases for the arrays.
	 * 
	 * We have (in order):
	 * 1. Random
	 * 2. Almost Sorted
	 * 3. Worst Case
	 * 4. Sorted
	 * 5. Repeats
	 * 
	 * @param size
	 * @return
	 */
	private static Integer[] generateArrayTestCase(int size) {
		Integer[] result = new Integer[size];
		
		for (int i = 0; i < size; i++) {
			// Assigns random numbers into the array
			result[i] = (int) (Math.random() * size);
		}
		
		return result;
	}

	private static Integer[] generateAlmostSortedTestCase(int size) {
		Integer[] result = new Integer[size];
		
		for (int i = 0; i < size; i++) {
			// Assigns in incrementing order
			result[i] = i;
		}
		
		// Swaps two of the entries to make it 'almost sorted'
		int index_1 = (int) (Math.random() * size / 2); // From the first half
		int index_2 = (int) (Math.random() * size / 2 + size / 2); // From the second half
		
		int temp = result[index_1];
		result[index_1] = result[index_2];
		result[index_2] = temp;
		
		return result;
	}
	
	private static Integer[] generateWorstCaseTestCase(int size) {
		Integer[] result = new Integer[size];
		
		// Assigns Integers in the array in reverse order
		for (int i = 0, j = size; i < size; i++) {
			result[i] = j;
			j--;
		}
		
		return result;
	}

	private static Integer[] generateSortedTestCase(int size) {
		Integer[] result = new Integer[size];
		
		// Assigns integers in incrementing order
		for (int i = 0; i < size; i++) {
			result[i] = i;
		}
		
		return result;
	}
	
	private static Integer[] generateRepeatTestCase(int size) {
		Integer[] result = new Integer[size];
		
		// Creates an array of 1s
		for (int i = 0; i < size; i++) {
			result[i] = 1;
		}
		
		return result;
	}
	
	/**
	 * ALL of the sorters being put to test here.
	 * 
	 * NOTE: This will run for quite a while. Like really quite a while.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SortDetective detective = new SortDetective();
		
		// Sorter A
		System.out.println("------------SorterA DATA--------------");
		ISort sorterA = new SorterA();

		detective.checkDifferentSizes(sorterA);
		
		if (detective.isStable(sorterA, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterA END---------");
		
		// Sorter B
		System.out.println("------------SorterB DATA--------------");
		ISort sorterB = new SorterB();

		detective.checkDifferentSizes(sorterB);
		
		if (detective.isStable(sorterB, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterB END---------");
		
		// Sorter C
		System.out.println("------------SorterC DATA--------------");
		ISort sorterC = new SorterC();

		detective.checkDifferentSizes(sorterC);
		
		if (detective.isStable(sorterC, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterC END---------");
		
		// Sorter D
		System.out.println("------------SorterD DATA--------------");
		ISort sorterD = new SorterD();

		detective.checkDifferentSizes(sorterD);
		
		if (detective.isStable(sorterD, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterD END---------");
		
		// Sorter E
		System.out.println("------------SorterE DATA--------------");
		ISort sorterE = new SorterE();

		detective.checkDifferentSizes(sorterE);
		
		if (detective.isStable(sorterE, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterE END---------");
		
		// Sorter F
		System.out.println("------------SorterF DATA--------------");
		ISort sorterF = new SorterF();

		detective.checkDifferentSizes(sorterF);
		
		if (detective.isStable(sorterF, 10000)) {
			System.out.println("STABLE");
		} else {
			System.out.println("NOT STABLE");
		}
		System.out.println("----------SorterF END---------");
	}

}
