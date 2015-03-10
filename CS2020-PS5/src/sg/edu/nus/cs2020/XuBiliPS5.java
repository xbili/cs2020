package sg.edu.nus.cs2020;

import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class XuBiliPS5 {
	public static StopWatch sw = new StopWatch();
	
	private static String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        
        return sorted;
	}
	
	private static int chooseTwo(int n) {
		return (n*n)/2 - n/2;
	}
	
	private static int computeNumPairs(HashMap<String, Integer> toReturn,
			int result) {
		for (int value : toReturn.values()) {
			if (value > 2) result += chooseTwo(value);
		}
		return result;
	}

	private static void storeInHashMap(HashMap<String, Integer> hm,
			String sorted) {
		if (hm.containsKey(sorted)) {
			hm.put(sorted, hm.get(sorted)+1);
		} else {
			hm.put(sorted, 1);
		}
	}

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
	
	public static void main(String[] args) {
		sw.start();
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		int result 			= 0;
		
		String fileName 	= args[0],
				line 		= null,
				sorted 		= null;
		
		FileReader dataFile = null;
		
		// Create new file reader
		dataFile = createNewReader(fileName, dataFile);
		BufferedReader bufferedDataFile = new BufferedReader(dataFile);
		
		// Read every line of the input file
		line = readFirstEntry(line, bufferedDataFile);
		
		while (line != null) {			
			sorted = sortString(line);
			
			storeInHashMap(hm, sorted);
			
			line = readNextLine(line, bufferedDataFile);
		}

		result = computeNumPairs(hm, result);
		
		System.out.println(result);
		sw.stop();
		System.out.println(sw.getTime());
	}
}
