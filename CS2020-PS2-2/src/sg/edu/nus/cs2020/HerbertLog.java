package sg.edu.nus.cs2020;

//  Import file handling classes
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class HerbertLog
 * The HerbertLog class records the jobs worked by Herbert, and
 * the wages paid to Herbert, over the last period of employment.
 * The constructor opens the specified log-file, and the get(.) method
 * returns records from the file.
 * 
 */
public class HerbertLog {
		
	/**
	 *  Public static final constants
	 */
	
	// Separator character used in the database file
	public static final String SEP = ":";
	// Length of each record in the database
	public static final int rLength = 18;
	// Padding character for database file
	public static final char PADDING = '.';

	/**
	 * Private state for the HerbertLog
	 */
	// Filename where the database can be found
	private String m_name = null;
	// Variable that points to the database, once opened
	private File m_file = null;
	// Variable for reading from the database file
	private RandomAccessFile m_inRAM = null;
	// Size of the database: number of available records
	private long m_numMinutes = 0;
	
	/**
	 * Debugging information
	 */
	// Number of "get" operations performed on the database
	// Note this is primarily for debugging.
	protected long m_numGets = 0;
	
	/**
	 * Constructor 
	 * @param filename File where the database can be found.
	 * The specified file must exist, and must contain records
	 * in the proper format. 
	 **/
	HerbertLog(String fileName){
		// Save the filename
		m_name = fileName;
		// Next, we open the file
		try {
			// Open the file
			m_file = new File(m_name);
			m_inRAM = new RandomAccessFile(m_file, "r");
			
			// Calculate the number of records in the database by
			// dividing the number of characters by the length of each record
			long numChars = m_inRAM.length();
			m_numMinutes = numChars/rLength;
		} catch (IOException e) {
			System.out.println("Error opening file: " + e);
		}
	}
	
	/**
	 * size
	 * @return the number of records in the database
	 */
	public long numMinutes(){
		return m_numMinutes;
	}

        /**
	 * numGets : primarily for debugging
         * @return number of times get has been called
         */
         public long numGets(){
	         return m_numGets;
         }

	/**
	 * get
	 * @param i specified the record number to retrieve, starting from 0
	 * @return the specified record, if it exists, or null otherwise
	 */
	public Record get(long i){
		
		// Increment the number of "get" operations
		m_numGets++;
		
		// Check for errors: if i is too large or too small, fail
		if (i > numMinutes()) return null;
		if (i < 0) return null;
		
		// Retrieve the proper record
		try {
			// First, calculate the offset into the file, and seek to that location
			long numChars = i*rLength;			
			m_inRAM.seek(numChars);
			
			// Next, read in rLength bytes
			// Recall that rLength is the length of one record
			byte[] entry = new byte[rLength];
			m_inRAM.read(entry);
			
			// Now, convert the string to a record.
			// Convert it to a string...
			String line = new String(entry);
			// .. parse the string using the record separator
			String[] tokens = line.split(SEP);
			// Every record should have 2 or 3 components
			assert(tokens.length==2 || tokens.length==3);
			// The first token is the name
			String name = tokens[0];
			// The second token is the height
			int height = Integer.parseInt(tokens[1]);
			return new Record(name, height);
			
		} catch (IOException e) {
			System.out.println("Error getting data from file: " + e);
		}
		// If the record wasn't found, for any reason, return null
		return null;
	}

	
	/*
	 * A Divide-and-Conquer algorithm to find the 'peaks'.
	 * An ArrayList is created to store the names of previously added people.
	 * 
	 * The algorithm starts with finding half the number of people and comparing the middle person to the
	 * person next to him. 
	 * 
	 */
	public int calculateSalary() {
		/*
		 *  A better solution, making use of Divide and Conquer,
		 *  and memoization.
		 */
		int salary = 0;
		
		long begin = 0;
		long end = m_numMinutes - 1;
		long max = m_numMinutes - 1;
		
		// An ArrayList of peaks that have already been found
		ArrayList<String> peaks = new ArrayList<String>();
		
		/*
		 * The time complexity for ArrayList methods used is as follows:
		 * get: O(1)
		 * add: O(n)
		 * contains: O(n)
		 * 
		 * n in this case is NOT the total entries in log, but the number of peaks found so far
		 * 
		 */
		
		// An HashMap of all the records for memoization, in order to prevent additional gets
		HashMap<Long, Record> records = new HashMap<Long, Record>();
		
		/*
		 * The time complexity for HashMap methods used is as follows: 
		 * get: O(1)
		 * put: O(1)
		 * containsKey: O(1) 
		 * 
		 */
		
		// To contain easy references of begin, middle, next and end
		Record beginEl;
		Record middleEl;
		Record nextEl;
		Record endEl;
		
		while (end - begin > 0) {
			// Middle is defined as the floor of the sum, so it's always the one on the right
			long middle = (begin + end) / 2;
			
			/*
			 * Checks if the element has been memoized
			 */
			// Begin element
			if (records.containsKey(begin)) {
				beginEl = records.get(begin);
			} else {
				beginEl = get(begin);
				records.put(begin, beginEl);
			}
			
			// Middle element
			if (records.containsKey(middle)) {
				middleEl = records.get(middle);
			} else {
				middleEl = get(middle);
				records.put(middle, middleEl);
			}
			
			// Middle + 1-th element --- 'next' element
			if (records.containsKey(middle + 1)) {
				nextEl = records.get(middle + 1);
			} else {
				nextEl = get(middle + 1);
				records.put(middle + 1, nextEl);
			}
			
			// Last element
			if (records.containsKey(end)) {
				endEl = records.get(end);
			} else {
				endEl = get(end);
				records.put(end, endEl);
			}

			
			if (beginEl.m_name.equals(endEl.m_name)) {
				// If begin and end name are the same, means there is no peak in between begin and end
				
				// Since we start from 0, it means that we have already found all other peaks on the left
				// Thus we can safely put aside the portion we are currently searching
				begin = end + 1;
				end = max;
				
			} else if (end - begin == 1) {
				// If begin and end are right next to each other, and they don't have the same name,
				// means begin is a peak
				
				// Checks if peak has already been found before
				if (!peaks.contains(beginEl.m_name)) {
					// If not, add in the name into the ArrayList and increment the salary
					peaks.add(beginEl.m_name);
					salary += beginEl.m_wages;
				}
				
				// Proceed on to check the right side of the pair
				begin = end;
				end = max;
			} else if (!middleEl.m_name.equals(nextEl.m_name)) {
				// If the middle name is different from the entry right next to it
				// means it is a peak
				end = middle;
				
				// If the ArrayList does not contain the peak yet
				if (!peaks.contains(middleEl.m_name)) {
					// Add the name of middleEl to the peak,
					// and increment salary
					peaks.add(middleEl.m_name);
					salary += middleEl.m_wages;
				}
			} else {
				// If nothing else is found, put away right half of the data first
				end = middle;
			}
		}
		
		// The last element of the log is ALWAYS a peak. Algorithm doesn't take care of that,
		// but we could add it in ourselves.
		salary += get(max).m_wages;
		
		// To illustrate number of gets
		System.out.println("Number of gets to find total salary: " + numGets());
		
		return salary;
	}
	
	public int calculateMinimumWork(int goalIncome) {
		// Maximum amout of salary possibly earned
		int maxSalary = calculateSalary();
		
		// If the goalIncome can never be reached, return -1
		if (goalIncome > maxSalary) {
			System.out.println("Number of gets to find minimum time: " + numGets());
			return -1;
		}
		
		// HashMap that contains the name of person as Key, and
		// ArrayList of every minute and wage
		HashMap<String, ArrayList<Integer>> records = new HashMap<String, ArrayList<Integer>>();
		
		// Store every entry into the HashMap records
		for (long i = 0; i < m_numMinutes; i++) {
			// Current record
			Record curr = get(i);
			
			if (records.containsKey(curr.m_name)) {
				// If the HashMap already contains the key:
				
				// Store the new ArrayList in a buffer first
				ArrayList<Integer> temp = new ArrayList<Integer>();
				
				// Adds the newest wage value into the buffer
				temp = records.get(curr.m_name);
				temp.add(curr.m_wages);
				
				// Updates the new value in HashMap as buffer
				records.put(curr.m_name, temp);
			} else {
				// If the HashMap doesn't contain the key yet:
				
				// Store the new ArrayList in a buffer first
				ArrayList<Integer> temp = new ArrayList<Integer>();
				
				// Adds the wage value into the buffer
				temp.add(curr.m_wages);
				
				// Updates the HashMap
				records.put(curr.m_name, temp);
			}
		}
		
		// Minimum number of minutes working
		int minutes = 0;
		// Total income earned in minutes time
		int income = 0;
		
		// While we have not reached the goal income yet
		while(income < goalIncome) {
			// Resets the income counter at every start of iteration, since
			// the salary earned is not cumalative.
			income = 0;
			
			// Iterate through HashMap values
			for (ArrayList<Integer> wages : records.values()) {
				if (minutes < wages.size()) {
					// If the current number of minutes does not exceed the maximum time that the person worked,
					// Increment the income by the amount of salary earned so far in that minute
					income += wages.get(minutes);
				} else {
					// If the current number of minutes exceed the maximum time that the person worked for,
					// Increment the income by the max salary earned by that person
					income += wages.get(wages.size() - 1);
				}
			}
			
			// Increment minutes
			minutes++;
		}
		
		System.out.println("Number of gets to find minimum time: " + numGets());
		return minutes;
	}
}
