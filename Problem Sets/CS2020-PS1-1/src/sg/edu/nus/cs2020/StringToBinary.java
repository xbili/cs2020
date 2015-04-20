package sg.edu.nus.cs2020;

public class StringToBinary {
	public static int[] convert(String input) {
		int[] asciiArray = new int[input.length()];
		int[][] binaryArray = new int[input.length()][];
		int[] result;
		
		for (int i = 0; i < input.length(); i++) {
			asciiArray[i] = (int) input.charAt(i);
		}
		
		for (int i = 0; i < asciiArray.length; i++) {
			binaryArray[i] = toBinary(asciiArray[i]);
		}
		
		// Check the size of result needed
		int resultSize = 0;
		for (int i = 0; i < binaryArray.length; i++) {
			for (int j = 0; j < binaryArray[i].length; j++) {
				resultSize += 1;
			}
		}
		
		result = new int[resultSize];
		
		int count = 0;
		for (int i = 0; i < binaryArray.length; i++) {
			for(int j = 0; j < binaryArray[i].length; j++) {
				result[count] = binaryArray[i][j];
				count++;
			}
		}
		
		return result;
	}
	
	private static int[] toBinary(int number) {
		int[] result;
		
		String binaryString = Integer.toBinaryString(number);
		result = new int[binaryString.length()];
		
		for (int i = 0; i < binaryString.length(); i++) {
			result[i] = Character.getNumericValue(binaryString.charAt(i));
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		String password = "tigermonkey101";
		int[] binary = convert(password);
		
		for(int i = 0; i < binary.length; i++) {
			System.out.print(binary[i]);
		}
	}
}
