package sg.edu.nus.cs2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordGenerator {
	public static void main(String[] args) throws IOException {
		int order = Integer.parseInt(args[0]);
		int numWord = Integer.parseInt(args[1]);
		String filename = args[2];
		String content;
		
		try {
			content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
			// Replace all spaces with ''
			// content = content.replaceAll("\\s+", "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		MarkovModelWord mm = new MarkovModelWord(content, order);
		mm.setRandomSeed(new Random().nextInt());
		
		// Splits the string into an ArrayList of words
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(content.split("\\s")));
		
		// Initialize the first kgram
		String[] initString = new String[order];
		for (int i=0; i < order; i++) {
			initString[i] = words.get(i);
		}
		
		String[] temp = Arrays.copyOf(initString, order);
		String next;
		// Start generating
		for (int i=0; i < numWord; i++) {
			next = mm.nextWord(temp);
			if (next == Character.toString(MarkovModelWord.NOCHARACTER)) {
				temp = Arrays.copyOf(initString, order);
				next = mm.nextWord(temp);
			} else {
				// Shift the string array
				for (int j=0; j < order-1; j++) {
					temp[i] = temp[i+1];
				}
				
				// Add in new generated word
				temp[order] = next;
			}
			
			System.out.print(next + " ");
		}
	}
}
