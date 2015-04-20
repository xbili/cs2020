package sg.edu.nus.cs2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Markov Model implementation for Problem Set 6.
 * 
 * This class takes a String of content and turns it into a Markov Model, which can
 * then be used to generate text based on the Markov Model.
 * 
 * @author xbili
 *
 */
public class MarkovModel {
	private HashMap<String, Integer[]> freqTable;
	private int order;
	public final static char NOCHARACTER = (char)(255);
	private Random randnum;
	
	/**
	 * Constructor: 
	 * 
	 * Initialize order value and a random number generator.
	 * Then we take the input text and store it in the HashMap based on the provided
	 * order value. 
	 * 
	 * @param text - input text to store in HashMap
	 * @param order - order of markov model
	 */
	public MarkovModel(String text, int order) {
		this.order = order;
		this.randnum = new Random();
		
		// HashMap that stores the frequency of characters that comes after the key
		freqTable = new HashMap<String, Integer[]>();
		
		// Put all consecutive k chars into the HashMap, where k is the order
		placeInHashMap(text, order);
	}

	/**
	 * Places the text in a HashMap according to order of Markov Model.
	 * 
	 * Makes use of a 'window' that looks at `order` number of characters, storing the items in
	 * the window as the key in HashMap.
	 * 
	 * For each key, we take the immediate next value, `next` as index for the freqArray, which
	 * is the value inside the HashMap, and increment the corresponding frequency value by 1.
	 * 
	 * @param text - content to store in HashMap
	 * @param order - Markov model order
	 */
	private void placeInHashMap(String text, int order) {
		char[] buffer = new char[order];
		char next = 0;
		for (int i=0; i < text.length()-order; i++) {
			
			// 'Window' that looks at k number of text at a time.
			for (int j=0; j<order; j++) {
				buffer[j] = text.charAt(i+j);
				
				// Single char immediately after the 'window'
				next = text.charAt(i+j+1); 
			}

			String entry = String.valueOf(buffer);
			
			// Storing the value in HashMap
			if (freqTable.containsKey(entry)) {
				// If already present, we increment the value of frequency of next char by one
				Integer[] freqArray = freqTable.get(entry);
				freqArray[(int) next] += 1;
			} else {
				// If not, we initalize a new Integer array and set the frequency of next char
				// by one
				Integer[] freqArray = new Integer[128];
				Arrays.fill(freqArray, 0);
				freqArray[(int) next] += 1;
				freqTable.put(entry, freqArray);
			}
		}
	}
	
	/**
	 * Returns the order of Markov Model
	 * 
	 * @return order of Markov Model
	 */
	public int order() {
		return order;
	}
	
	/**
	 * Gets the frequency of the input string, kgram, in the Markov Model.
	 * 
	 * This is taken by the sum of frequency of all characters in the frequency array
	 * 
	 * @param kgram
	 * @return frequency of kgram in the entire text
	 * @throws IOException - from invalid length of kgram
	 */
	public int getFrequency(String kgram) throws Exception {
		if (kgram.length() != order) {
			throw new Exception("String input not consistent with order.");
		}
		
		Integer[] freqArray = freqTable.get(kgram);
		int total = 0;
		
		// Sum of all frequency in frequency array
		for (int i=0; i < freqArray.length; i++) {
			total += freqArray[i];
		}
		
		return total;
	}
	
	/**
	 * Get the frequency of a character, c, after kgram.
	 * 
	 * This is taken by quering the frequency array of kgram with the index as
	 * ASCII value of c.
	 * 
	 * @param kgram
	 * @param c
	 * @return frequency of c after kgram
	 * @throws IOException - from invalid length of kgram
	 */
	public int getFrequency(String kgram, char c) throws Exception {
		if (kgram.length() != order) {
			throw new Exception("String input not consistent with order.");
		}
		
		Integer[] freqArray = freqTable.get(kgram);
		int cAscii = (int) c;
		
		if (freqArray == null) {
			System.out.println(kgram);
			throw new Exception("String input not found in freqTable.");
		}
		
		return freqArray[cAscii];
	}
	
	/**
	 * Generates the next random character following kgram
	 * 
	 * @param kgram
	 * @return
	 * @throws IOException - from invalid length of kgram
	 */
	public char nextCharacter(String kgram) throws Exception {
		if (kgram.length() != order) {
			throw new Exception("String input not consistent with order.");
		}
		
		// Gets a random double
		double rand = randnum.nextDouble();
		
		// For each character in the 128 ascii, we check if the random double generated is lesser
		// than the probability of getting that character
		char c;
		for (int i=0; i < 128; i++) {
			c = (char) i;
			if (rand <= (double) getFrequency(kgram, c) / (double) getFrequency(kgram)) {
				return c;
			}
		}
		
		// If not, return ASCII 255, NOCHARACTER
		return NOCHARACTER;
	}
	
	/**
	 * Sets the random seed for random number generator used in nextCharacter(kgram)
	 *
	 * @param s - seed
	 */
	public void setRandomSeed(long s) {
		randnum.setSeed(s);
	}
}
