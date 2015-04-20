package sg.edu.nus.cs2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MarkovModelWord {
	private HashMap<String[], HashMap<String, Integer>> freqTable;
	private int order;
	public final static char NOCHARACTER = (char)(255);
	private Random randnum;
	
	public MarkovModelWord(String text, int order) {
		this.order = order;
		this.randnum = new Random();
		this.freqTable = new HashMap<String[], HashMap<String, Integer>>(); 
		
		// Splits the string into an ArrayList of words
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(text.split("\\s")));
		
		String[] buffer = new String[order];
		String next = "";
		
		// Stores words into HashMap according to order
		for (int i=0; i < words.size() - order; i++) {
			for (int j=0; j<order; j++) {
				buffer[j] = words.get(i+j);
				next = words.get(i+j+1);
			}
			
			// Storing the value in HashMap
			if (freqTable.containsKey(buffer)) {
				HashMap<String, Integer> freqMap = freqTable.get(buffer);
				if (freqMap.containsKey(next)) {
					freqMap.put(next, freqMap.get(next) + 1);
					freqTable.put(buffer, freqMap);
				} else {
					freqMap.put(next, 1);
					freqTable.put(buffer, freqMap);
				}
			} else {
				HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
				freqMap.put(next, 1);
				freqTable.put(buffer, freqMap);
			}
		}
	}
	
	public int order() {
		return this.order;
	}
	
	public int getFrequency(String[] kgram) throws IOException {
		if (kgram.length != order) {
			throw new IOException("ArrayList size different from order.");
		} 
		
		HashMap<String, Integer> freqMap = freqTable.get(kgram);
		
		int total = 0;
		
		for (int value : freqMap.values()) {
			total += value;
		}
		
		return total;
	}
	
	public int getFrequency(String[] kgram, String word) throws IOException {
		if (kgram.length != order) {
			throw new IOException("ArrayList size different from order.");
		}
		
		HashMap<String, Integer> freqMap = freqTable.get(kgram);
		
		return freqMap.get(word);
	}
	
	public String nextWord(String[] kgram) throws IOException {
		if (kgram.length != order) {
			throw new IOException("ArrayList size different from order.");
		}
		
		double rand = randnum.nextDouble();
		Collection<String> wordList = freqTable.get(kgram).keySet();
		Iterator<String> wordListIter = wordList.iterator();
		
		while (wordListIter.hasNext()) {
			String curr = wordListIter.next();
			if (rand <= (double) getFrequency(kgram, curr) / (double) getFrequency(kgram)) {
				return curr;
			}
		}
		
		return Character.toString(NOCHARACTER);
	}
	
	public void setRandomSeed(long s) {
		randnum.setSeed(s);
	}
}
