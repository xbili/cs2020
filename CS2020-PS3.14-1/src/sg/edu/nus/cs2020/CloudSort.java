package sg.edu.nus.cs2020;

import java.util.ArrayList;

import sg.edu.nus.cs2020.ICloudManager.CloudProvider;

/**
 * Distributed sorting algorithm that runs in O(k) time, where k is the number of servers currently used.
 * 
 * @author xbili
 *
 */
public class CloudSort {
	/**
	 * Algorithm is explained under scheduleSort method comments
	 * 
	 * To handle insufficient number of servers provided, the algorithm will tweak the maximum page size according to the
	 * number of servers provided, and the size of data provided. This is under the assumption that each server can have
	 * unlimited amount of resources to sort (which is very unrealistic, but it makes the algorithm more flexible).
	 * 
	 * @param inFile - input file
	 * @param outFile - output file
	 * @param numServers - max number of servers that can be used
	 */
	public void cloudSort(String inFile, String outFile, int numServers) {
		// Initialize a new cloud manager with a arbitrary page size limit of 5 first
		// The page size limit will vary depending on data size.
		CloudManager cloud = new CloudManager(CloudProvider.AmazonEC2, 5);

		// To handle cloud not initializing properly (the incorrect spelling of the method really annoys me)
		if (!cloud.initiliazeCloud(inFile, numServers)) {
			System.out.println("Error in initializing cloud.");
		}
		
		
		if (cloud.numPages() > 2 * numServers) {
			// If there isn't enough servers to make this algorithm work, we increase the page size of each server to make it work. 
			System.out.println("Not enough servers to handle such data, increasing the page size of each server.");
			
			// Increase the page size limit of each server to suit the needs of the algorithm
			cloud = updateCloud(cloud, numServers);
			
			// Re-initialize the cloud
			cloud.initiliazeCloud(inFile, numServers);
			
			System.out.println("Page size limit increased to: " + cloud.numPages());
		} else if (cloud.numPages() < 2 * numServers) {
			// If there are too many servers for a very small data set, we reduce the number of servers used.
			System.out.println("Too many servers for a small data set, decreasing the number of servers utilized");
			
			// The algorithm cannot handle data of only one page long.
			if (cloud.numPages() == 1) {
				System.out.println("Servers cannot handle a page size of 1.");
			}
			
			// The current number of pages
			int currentPages = cloud.numPages();
			
			// New server size to update
			int updatedServerSize = currentPages * 2;
			
			// Re-initialize cloud with the new server size
			cloud.initiliazeCloud(inFile, updatedServerSize);
			
			// Update the numServer parameter in order to schedule the right amount of phases, as shown in code below
			numServers = updatedServerSize;
		}
		
		// The main sorting algorithm
		for (int i = 0; i < numServers; i++) {
			// Schedules each server on the cloud to perform tasks
			scheduleServers(cloud, numServers);
		}
		
		// Prints out statistics and saves the sorted file
		cloud.getStatus();
		cloud.shutDown(outFile);
		
		// For testing purposes
		if (cloud.isSorted()) {
			System.out.println("Data is sorted!");
		} else {
			System.out.println("Oops, something went wrong.");
		}
	}
	
	/**
	 * For problem 1b.
	 * 
	 * Updates the page size limit of the cloud in order to best fit the number of 
	 * elements in the dataset.
	 * 
	 * @param currentCloud - cloud to update
	 * @param numServers - number of servers to use
	 * @return a new CloudManager that has updatedPageSize
	 */
	private CloudManager updateCloud(CloudManager currentCloud, int numServers) {
		int currentElementSize = currentCloud.numElements();
		int updatedPageSize = (currentElementSize / 2) / numServers;
		CloudManager newCloud = new CloudManager(CloudProvider.AmazonEC2, updatedPageSize);
		
		return newCloud;
	}
	
	/**
	 * For problem 1b: main algorithm
	 * 
	 * The algorithm works under two different cases:
	 * 1. Even number of pages
	 * 		- The servers first schedule two pages by two pages. e.g.
	 * 			Server 0 takes pg0 & pg1
	 * 			Server 1 takes pg2 & pg3
	 * 			Server 2 takes pg4 & pg5
	 * 			... so on ...
	 * 		
	 * 		- Execute the sorting
	 * 
	 * 		- Next the first server will schedule the first and last page. e.g
	 * 			Server 0 takes pg0 & pgK, where K is the index of the last page
	 * 			Server 1 takes pg1 & pg2
	 * 			Server 2 takes pg3 & pg4
	 * 			... so on ...
	 * 
	 * 		- Execute the sorting
	 * 
	 * 		- Repeat this for as many times as the number of servers (explanation in Coursemology answer)
	 * 
	 * 2. Odd number of pages
	 * 		- The servers first schedule two pages by two pages from the FRONT. e.g.
	 * 			Server 0 takes pg0 & pg1
	 * 			Server 1 takes pg2 & pg3
	 * 			... so on (same as the even pages case) ...
	 * 
	 * 		- Execute the sorting
	 * 
	 * 		- The servers then schedule two pages by two pages from the BACK. e.g.
	 * 			Server 0 takes pg(k-1) & pg(k)
	 * 			Server 1 takes pg(k-3) & pg(k-2)
	 * 			... so on, until it reaches 0 ...
	 * 
	 * 		- Execute the sorting
	 * 
	 * 		- Repeat this for as many times as the number of servers (same as the even pages case too)
	 * 
	 * @param provider - Cloud provider
	 * @param servers - Number of servers utilized
	 */
	private void scheduleServers(CloudManager provider, int servers) {
		// Stores the current server being scheduled
		int serverId = 0;
		
		// Index of the last page in the dataset
		int maxPageIndex = provider.numPages() - 1;
		
		/**************************** PHASE 1 STARTS **********************************/
		// PHASE 1 - Schedules each server with two consecuvtive pages 

		for (int i = 0; i < maxPageIndex; i+=2) {
			provider.scheduleSort(serverId, i, i+1);
			serverId++;
		}
		
		// SORT
		provider.executePhase();
		
		/***************************** PHASE 1 ENDS ***********************************/
		
		/***************************** PHASE 2 STARTS ***********************************/
		// PHASE 2 - depends if the array length is even or odd
		
		// Reset serverId counter
		serverId = 0;
		
		if (provider.numPages() % 2 == 0) {
			// EVEN NUMBER OF PAGES
			
			// Schedule the first server with the first and last page of the dataset
			provider.scheduleSort(0, 0, maxPageIndex);
			serverId++;
			
			// For every other server, we schedule it with two consecutive pages
			for (int i = 1; i < maxPageIndex - 1; i+=2) {
				provider.scheduleSort(serverId, i, i+1);
				serverId++;
			}
			
		} else { 
			// ODD NUMBER OF PAGES
			
			// Start scheduling from the back, each server taking two pages
			for (int i = maxPageIndex; i > 0; i-=2) {
				provider.scheduleSort(serverId, i-1, i);
				serverId++;
			}
		}
		
		// SORT
		provider.executePhase();
		
		/********************* PHASE 2 ENDS ***************************/
	}
	
	/**
	 * Problem 2d.
	 * 
	 * Binary search is defined as a private method below.
	 * 
	 * We check if the array is sorted using binary search for k number of times. Index and element
	 * used for the check is selected at random.
	 * 
	 * @param array - array to check if it is sorted
	 * @param k - as found in 2a.
	 * @return true if array is sorted
	 */
	public boolean isSorted(int[] array, int k) {
		for (int i = 0; i < k; i++) {
			int randomIndex = (int) (Math.random() * array.length);
			int found = binarySearch(array, array[randomIndex], 0, array.length - 1);
			if (found != randomIndex) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * For problem 2d.
	 * 
	 * A standard binary search algorithm.
	 * 
	 * @param array - to search
	 * @param key - value to find in the array
	 * @param left - current left bound
	 * @param right - current right bound
	 * @return the index of the key found in the array
	 */
	private int binarySearch(int[] array, int key, int left, int right) {
		int middle;
		
		// Loop invariant
		while (right > left) {
			// Get middle element
			middle = (int) Math.ceil((left + right) / 2);
			if (key > array[middle]) {
				left = middle + 1;
			} else if (key < array[middle]) {
				right = middle - 1;
			} else {
				return middle;
			}
		}
		
		return left;
	}
	
	/**
	 * TEST CASES
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * Problem 1 Tests
		 * 
		 * Tried out the sort algorithm on data of different length, all works fine!
		 */
		System.out.println("****** PROBLEM 1 TESTS ******");
		CloudSort sorter = new CloudSort();
		
		System.out.println("------1 million------");
		sorter.cloudSort("1000000Ints.txt", "sorted.txt", 100);
		System.out.println("");
		
		System.out.println("------ 100 thousand ------");
		sorter.cloudSort("100000Ints.txt", "sorted.txt", 100);
		System.out.println("");
		
		System.out.println("------ 10 thousand ------");
		sorter.cloudSort("10000Ints.txt", "sorted.txt", 100);
		System.out.println("");
		
		System.out.println("------ 1 thousand ------");
		sorter.cloudSort("1000Ints.txt", "sorted.txt", 100);
		System.out.println("");
		
		System.out.println("------ 1 hundred ------");
		sorter.cloudSort("100Ints.txt", "sorted.txt", 100);
		System.out.println("");
		
		System.out.println("------ ten ------");
		sorter.cloudSort("10Ints.txt", "sorted.txt", 100);
		
		System.out.println("****** END PROBLEM 1 TESTS ******");
		System.out.println("");
		System.out.println("");
		
		/**
		 * Problem 2 Tests
		 * 
		 * Tried out the tests on every single test case provided. 
		 * 
		 */
		
		// Saves all the test files in an ArrayList to make my life easier
		ArrayList<String> testFiles = new ArrayList<String>(); 
		
		// One million
		testFiles.add("1000000Ints.txt");
		testFiles.add("1000000IntsSorted.txt");
		testFiles.add("1000000IntsAlmostSorted500.txt");
		testFiles.add("1000000IntsAlmostSorted100.txt");
		testFiles.add("1000000IntsAlmostSorted50.txt");
		
		// Hundred Thousand
		testFiles.add("100000Ints.txt");
		testFiles.add("100000IntsSorted.txt");
		testFiles.add("100000IntsAlmostSorted50.txt");
		testFiles.add("100000IntsAlmostSorted10.txt");
		
		// Ten thousand
		testFiles.add("10000Ints.txt");
		testFiles.add("10000IntsSorted.txt");
		testFiles.add("10000IntsAlmostSorted100.txt");
		testFiles.add("10000IntsAlmostSorted10.txt");
		testFiles.add("10000IntsAlmostSorted5.txt");
		
		// One thousand
		testFiles.add("1000Ints.txt");
		testFiles.add("1000IntsSorted.txt");
		testFiles.add("1000IntsAlmostSorted10.txt");
		testFiles.add("1000IntsAlmostSorted5.txt");
		testFiles.add("1000IntsAlmostSorted1.txt");
		
		// One hundred
		testFiles.add("100Ints.txt");
		testFiles.add("100IntsSorted.txt");
		testFiles.add("100IntsAlmostSorted100.txt");
		testFiles.add("100IntsAlmostSorted50.txt");
		testFiles.add("100IntsAlmostSorted10.txt");
		
		// One hundred and three
		testFiles.add("103Ints.txt");
		
		// Ten
		testFiles.add("10Ints.txt");
		testFiles.add("10IntsSorted.txt");
		testFiles.add("10IntsAlmostSorted20.txt");
		testFiles.add("10IntsAlmostSorted5.txt");
		testFiles.add("10IntsAlmostSorted1.txt");
		
		System.out.println("****** PROBLEM 2 TESTS ******");
		
		// CloudManager to getElements from the txt files and store them in an array for testing purposes
		CloudManager cloud = new CloudManager(CloudProvider.AmazonEC2, 100);
		
		// Tests for one million items
		System.out.println("-------One Million Integers-------");
		for (int i = 0; i < 5; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[1000000];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		// Tests for hundred thousand items
		System.out.println("-------100 Thousand Integers-------");
		for (int i = 5; i < 9; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[100000];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		// Tests for ten thousand items
		System.out.println("-------10 Thousand Integers-------");
		for (int i = 9; i < 14; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[10000];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		// Tests for thousand items
		System.out.println("-------Thousand Integers-------");
		for (int i = 14; i < 19; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[1000];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		// Tests for hundred items
		System.out.println("-------Hundred Integers-------");
		for (int i = 19; i < 24; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[100];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		// Test for hundred and three items
		System.out.println("-------103 Integers-------");
		cloud.initiliazeCloud(testFiles.get(24), 2);
		int[] testArray_103 = new int[103];
		for(int j = 0; j < cloud.numElements(); j++) {
			testArray_103[j] = cloud.getElement(j);
		}
		
		System.out.println("Currently testing: " + testFiles.get(24));
		
		// For testing purposes
		if (sorter.isSorted(testArray_103, 100)) {
			System.out.println("Array is sorted!");
		} else {
			System.out.println("Array is not sorted.");
		}
		System.out.println("");

		// Test for ten items
		System.out.println("-------Ten Integers-------");
		for (int i = 25; i < 30; i++) {
			cloud.initiliazeCloud(testFiles.get(i), 2);
			int[] testArray = new int[10];
			for(int j = 0; j < cloud.numElements(); j++) {
				testArray[j] = cloud.getElement(j);
			}
			
			System.out.println("Currently testing: " + testFiles.get(i));
			
			// For testing purposes
			if (sorter.isSorted(testArray, 100)) {
				System.out.println("Array is sorted!");
			} else {
				System.out.println("Array is not sorted.");
			}
			System.out.println("");
		}
		
		System.out.println("****** END PROBLEM 2 TESTS ******");
	}
}
