package sg.edu.nus.cs2020;

public class TextToBinary {
	
	// Takes a string of text and turn it into a string of binary
	public static String toBinary(String text) {
		
		String result = "";
		
		for (int i = 0; i < text.length(); i++) {
			// Saves current character
			char current = text.charAt(i);
			
			// Convert character into ASCII
			int currentAscii = (int) current;
			
			// Convert into Binary String
			String binary = Integer.toBinaryString(currentAscii);
			
			// Concatenate the string together
			result += binary;
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		// Test
		System.out.println(toBinary("Bili is awesome."));
	}
}
