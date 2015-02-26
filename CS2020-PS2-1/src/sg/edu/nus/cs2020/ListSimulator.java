package sg.edu.nus.cs2020;

/**
 * ListSimulator
 * Description: A simple simulator for testing list management heuristics
 * CS2020 2012
 */


// Uses the Random library
import java.util.Random;

/**
 * Class: ListSimulator
 * Description: Simple simulator for testing list management heuristics.
 * The simulator inserts a specified number of items into a list, and then
 * queries the items according to a randomly chosen (exponential) distribution.
 */
public class ListSimulator {

	// Random number generator
	static Random s_generator = new Random();
	
	// Constant: size of the list to test
	final static int LISTSIZE = 1000;
	// Constant: number of searches to run on the list in each test
	final static long NUMQUERIES = 1000000;

	// Three different types of test, depending on the initial order of the list
	// INCREASING: the list is initially organized in order of increasing probability
	// DECREASING: the list is initially organized in order of decreasing probability
	// RANDOM: the list is initially organized in a random order
	public static enum TestType {INCREASING, DECREASING, RANDOM};

	// Array for storing the probability distribution.
	// Item k is chosen with probability m_ListProb[k]
	private double[] m_ListProb = new double[LISTSIZE];
	
	// A list for running the test on
	private FixedLengthList m_list = null;
	
	// A stopwatch for measuring how long the tests take
	private StopWatch m_watch = new StopWatch();
	
	
	/**
	 * Constructor
	 * @param type specifies the type of experiment to run
	 * @param list specifies the list on which to run the experiment
	 * Description: generates the probability distribution and inserts the keys into the list in the specified order
	 */
	ListSimulator(TestType type, FixedLengthList list){
		
		// Save list
		m_list = list;
		
		// Reset clock
		m_watch.reset();
		
		// Generate access probabilities, where the largest probability is 10/LISTSIZE
		generateListProbs(0.1);
		
		// Insert all the items into the list in the appropriate order for this experiment.
		if (type == TestType.INCREASING){
			for (int i=0; i<LISTSIZE; i++){
				m_list.add(LISTSIZE-i-1);
			}
		}
		else if (type == TestType.DECREASING){
			for (int i=0; i<LISTSIZE; i++){
				m_list.add(i);
			}
		}
		else if (type == TestType.RANDOM){
			// Add all the items to an arraylist
			java.util.ArrayList<Integer> itemsToAdd = new java.util.ArrayList<Integer>(LISTSIZE);
			for (int i=0; i<LISTSIZE; i++){
				itemsToAdd.add(i);
			}
			// For each item in the arraylist:
			for (int i=0; i<LISTSIZE; i++){
				// Choose a random item in the arraylist and add it to our list
				int toAdd = s_generator.nextInt(itemsToAdd.size());
				m_list.add(itemsToAdd.get(toAdd));
				// Remove the chosen item from the arraylist
				itemsToAdd.remove(toAdd);
			}
		}
	}
	
	/**
	 * Method: generateListProbs generates a distribution over all keys
	 * @param maxProb specifies the maximum probability with which any one item is chosen
	 * Description: This method generates a distribution and stores it in m_ListProb.
	 * The distribution is stored in descending order, i.e., m_ListProb[0] is the largest probability, 
	 * while m_ListProb[LISTSIZE-1] is the smallest probability.
	 * The distribution being generated is roughly exponential.
	 */
	private void generateListProbs(double maxProb){
		// Begin with all the probability mass here
		double total = 1.0;
		
		// Assign a probability to each item in the list:
		for (int i=0; i<LISTSIZE-1; i++){
			// Get a random double between 0 and 1
			double amt = s_generator.nextDouble();
			// Scale the randomly chosen double so that it is no bigger than maxProb
			amt = amt * maxProb;
			// amt now specifies the percentage of the remaining total probability that should be assigned
			// to element i
			m_ListProb[i] = amt * total;
			// Subtract the newly allocated probability from total
			total = total - m_ListProb[i];
		}
		// Give any remaining probability to the last element in the list
		m_ListProb[LISTSIZE-1] = total;
		
		// Sort the probabilities from smallest to largest
		java.util.Arrays.sort(m_ListProb);

		// Reverse the list so it is sorted from largest to smallest
		int i=0;
		int j = LISTSIZE-1;
		double temp = 0;
		while (i < j){
			temp = m_ListProb[i];
			m_ListProb[i] = m_ListProb[j];
			m_ListProb[j] = temp;
			i++;
			j--;
		}
	}
	
	public void printProbs(){
		for (int i=0; i<m_ListProb.length; i++){
			System.out.println(i + ":" + m_ListProb[i]);
		}
	}

	/**
	 * Method: chooseRandom
	 * Description: Chooses a random item according to the distribution in m_ListProb.
	 */
	private int chooseRandom(){
		// Choose a random double between 0 and 1
		double randomChoice = s_generator.nextDouble();
		
		// Find the first index such that the sum of the prefixes in m_ListProb >= randomChoice
		// Begin by initializing sum to zero
		double sum = 0;
		// Iterate through the distribution until we find the right element
		for (int i=0; i<LISTSIZE; i++){
			// Add the next element to sum
			sum += m_ListProb[i];
			// Check if the sum is big enough.  If so, return i
			if (randomChoice <= sum){
				return i;
			}
		}
		// If there is an error (likely due to floating point rounding) just return element 0.
		return 0;
	}
	
	/**
	 * Method: search
	 * @param key is the item to search for
	 * @return true if the element is found, false otherwise
	 * Description: searches for the key in the list.
	 * It also measures how long the search takes.
	 */
	private boolean search(int key){
		// Check for null list
		if (m_list == null){
			System.out.println("Error: null list.");
			return false;
		}
		// Start the stopwatch
		m_watch.start();
		// Perform the search
		boolean found = m_list.search(key);
		// Stop the stopwatch
		m_watch.stop();
		// If the element is not found, report an error.
		if (found == false) System.out.println("Error!");
		return found;
	}
	
	/**
	 * Method: getTotal
	 * Description: Returns the cumulative time for all the search operations since the test was begun.
	 */
	float getTotal(){
		return m_watch.getTime();
	}
	
	/**
	 * Method: main
	 * Description: Runs three tests, one for INCREASING, DECREASING, and RANDOM
	 */
	static public void main(String[] args){
		// FixedLengthList experiment
		
		// Initialize the three experiments
		MyFastList lRandom = new MyFastList(LISTSIZE);		
		ListSimulator tRandom = new ListSimulator(TestType.RANDOM, lRandom);
		
		MyFastList lUp = new MyFastList(LISTSIZE);
		ListSimulator tUp = new ListSimulator(TestType.INCREASING, lUp);
		
		MyFastList lDown = new MyFastList(LISTSIZE);
		ListSimulator tDown = new ListSimulator(TestType.DECREASING, lDown);

		// For each of the three experiments, run NUMQERIES searches.
		// For each search, choose a random key according to the distribution
		// and search for that key.
		int key = 0;
		int i=0;
		for (i=0; i<ListSimulator.NUMQUERIES; i++){
			key = tRandom.chooseRandom();
			tRandom.search(key);
		}
		
		key = 0;
		for (i=0; i<ListSimulator.NUMQUERIES; i++){
			key = tUp.chooseRandom();
			tUp.search(key);
		}
		
		key = 0;
		for (i=0; i<ListSimulator.NUMQUERIES; i++){
			key = tDown.chooseRandom();
			tDown.search(key);
		}
	
		
		
		// Print out the results.
		System.out.println("Total cost random: " + tRandom.getTotal());
		System.out.println("Total cost increasing: " + tUp.getTotal());
		System.out.println("Total cost decreasing: " + tDown.getTotal());		
	}
	
}
