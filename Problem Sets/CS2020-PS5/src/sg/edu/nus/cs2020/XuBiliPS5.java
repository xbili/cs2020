package sg.edu.nus.cs2020;

import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Creates a HashMap that reads a long file to search for pairs.
 * 
 * Run this Java file with all the test files as arguments, it
 * automatically reads every file. Saves some time!
 * 
 * @author xbili
 *
 */
public class XuBiliPS5 {
	// Stopwatch for testnig purposes
	// public static StopWatch sw = new StopWatch();

	/**
	 * Sorts a string by the char's ascii value
	 * 
	 * @param s - string to sort
	 * @return sorted string by ascii value
	 */
	private static String sortString(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		String sorted = new String(chars);

		return sorted;
	}

	/**
	 * Formula for nC2. (n choose 2)
	 * 
	 * @param n
	 * @return nC2
	 */
	private static int chooseTwo(int n) {
		return (n * n) / 2 - n / 2;
	}

	/**
	 * Finds the number of pairs in the HashMap, simply by choose-2 formula
	 * defined for each value in the HashMap.
	 * 
	 * We only do this if the value is more than or equals to 2. 
	 * It doesn't make sense to calculate the number of pairs in a set of one element.
	 * 
	 * @param hm - HashMap to get key/values from
	 * @param result - 0 by default
	 * @return number of pairs in the entire HashMap
	 */
	private static int computeNumPairs(HashMap<String, Integer> hm, int result) {
		for (int value : hm.values()) {
			if (value >= 2)
				result += chooseTwo(value);
		}
		return result;
	}

	/**
	 * If HashMap contains the sorted key, then we increment the value of that
	 * key by 1 Else we put that key into the HashMap and set the value as 1.
	 * 
	 * @param hm - HashMap to place key/value in
	 * @param sorted - sorted string as key
	 */
	private static void storeInHashMap(HashMap<String, Integer> hm,
			String sorted) {
		if (hm.containsKey(sorted)) {
			hm.put(sorted, hm.get(sorted) + 1);
		} else {
			hm.put(sorted, 1);
		}
	}

	/**
	 * Reads the next line in a BufferedReader
	 * 
	 * @param line
	 * @param bufferedDataFile
	 * @return
	 */
	private static String readNextLine(String line,
			BufferedReader bufferedDataFile) {
		try {
			line = bufferedDataFile.readLine();
		} catch (IOException e) {
			System.err.println("File IO exception.");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * Reads the first entry in a BufferedReader, so that the first value can be
	 * ignored.
	 * 
	 * @param line
	 * @param bufferedDataFile
	 * @return
	 */
	private static String readFirstEntry(String line,
			BufferedReader bufferedDataFile) {
		try {
			bufferedDataFile.readLine(); // To skip the first integer value
			line = bufferedDataFile.readLine();
		} catch (IOException e1) {
			System.err.println("IOException caught.");
			e1.printStackTrace();
		}
		return line;
	}

	/**
	 * Creates a new FileReader based on the fileName taken from args.
	 * 
	 * @param fileName - file to open
	 * @param dataFile - outfile
	 * @return dataFile - a new FileReader
	 */
	private static FileReader createNewReader(String fileName,
			FileReader dataFile) {
		try {
			dataFile = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			e.printStackTrace();
		}
		return dataFile;
	}

	/**
	 * Main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			throw new Error("Invalid arguments.");
		}
		
		for (int i = 0; i < args.length; i++) {

			// sw.start();

			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			
			int result = 0; // Number of pairs
			String fileName = args[i];
			String line = null, sortedString = null;
			FileReader dataFile = null;

			// Create new file reader and buffer
			dataFile = createNewReader(fileName, dataFile);
			BufferedReader bufferedDataFile = new BufferedReader(dataFile);

			// Read first line of the input file (after skipping the integer value in the first line)
			line = readFirstEntry(line, bufferedDataFile);

			// For every line in the file
			while (line != null) {
				sortedString = sortString(line);
				storeInHashMap(hm, sortedString);
				line = readNextLine(line, bufferedDataFile);
			}
			
			// Saves the number of pairs in the entire HashMap
			result = computeNumPairs(hm, result);
			
			// System.out.println("File: " + fileName);
			System.out.println(result);
			// sw.stop();
			// System.out.println("Time taken: " + sw.getTime());
			// System.out.println("-----------------------------");
			// sw.reset();
		}
	}
}
