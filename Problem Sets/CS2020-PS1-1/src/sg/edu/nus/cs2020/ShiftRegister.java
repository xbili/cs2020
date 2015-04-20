package sg.edu.nus.cs2020;

public class ShiftRegister implements ILFShiftRegister {
	
	private int size; // Length of seed
	private int[] seed; // Seed 
	private int tap; // Tap
	
	// Constructor for ShiftRegister
	// In addition to assigning values of properties, it initializes the seed array
	public ShiftRegister(int size, int tap) {
		this.size = size;
		this.tap = tap;
		this.seed = new int[size];
	}
	
	public void setSeed(int[] seed) {
		// Checks the `seed` array for any invalid characters. If there is, throw an error
		for (int i = 0; i < this.size; i++) {
			if (seed[i] > 1 || seed[i] < 0) {
				throw new Error("Seed contains non-valid integers. Has to be either 0 or 1.");
			}
		}
		
		// Assigns `seed` as new value of seed if everything is valid
		this.seed = seed;
	}

	public int shift() {
		// Creates a new array of the same length
		int[] newArray = new int[this.size];
		
		// Calculate value of rightmost bit by taking the XOR of tap and leftmost bit
		newArray[0] = this.seed[this.tap] ^ this.seed[this.size - 1];
		
		// Shifts the entire array to the left
 		for (int i = 0; i < this.size - 1; i++) {
			newArray[i + 1] = this.seed[i];
		}
 		
 		// Assign the newArray as the seed
		this.seed = newArray;
		
		return newArray[0];
	}

	public int generate(int k) {
		// Creates a new array which contains the results of all shifts done by register
		int[] resultArray = new int[k];
		
		// Used to compute decimal representation of binary
		int result = 0;
		
		// Shifts k times
		for (int i = 0; i < k; i++) {
			 resultArray[i] = this.shift();
		}
		
		// Converts binary bit array into a single integer
		for (int i = 0; i < resultArray.length; i++) {
			result += resultArray[i] * Math.pow(2, resultArray.length - 1 - i);
		}
		
		return result;
	}
	
	// Method to check internal state of object
	public String toString() {
		String result = "";
		for (int i = 0; i < this.seed.length; i++) {
			result += this.seed[i];
		}
		
		return result;
	}
	
	// Main method for tests
	public static void main(String args[]) {
		
		// Test 1
		System.out.println("Test 1");
		int[] testArray = {0, 1, 0, 1, 1, 1, 1, 0, 1};
		
		ShiftRegister shifter = new ShiftRegister(9, 7);
		shifter.setSeed(testArray);

		for (int i = 0; i < 10; i++) {
			int j = shifter.shift();
			System.out.print(j);
		}
		
		// Test 2
		System.out.println("\nTest 2");
		int[] testArray2 = {0, 1, 0, 1, 1, 1, 1, 0, 1};
		ShiftRegister shifter2 = new ShiftRegister(9, 7);
		shifter2.setSeed(testArray2);
		
		for (int i = 0; i < 10; i++) {
			int j = shifter2.generate(3);
			System.out.println(j);
		}
		

		// Personal Test 1
		// This should return an error because of invalid integers in seed
		System.out.println("Personal Test 1");
		int[] testArray3 = {2, 1, 0, 3, 5, 1, 2, 5}; // Invalid seed values
		ShiftRegister shifter3 = new ShiftRegister(8, 7);
		shifter3.setSeed(testArray3);
		
		for (int i = 0; i < 10; i++) {
			int j = shifter3.generate(3);
			System.out.println(j);
		}

		// Personal Test 2
		// This should return an error because of invalid seed size
		System.out.println("Personal Test 2");
		int[] testArray4 = {1, 0, 1, 1, 0, 1, 1, 0}; // array of size 8
		ShiftRegister shifter4 = new ShiftRegister(4, 2);
		shifter4.setSeed(testArray4);
		System.out.println("Shifted: " + shifter4.shift());
		System.out.println("Generated: " + shifter4.generate(3));

	}
}
