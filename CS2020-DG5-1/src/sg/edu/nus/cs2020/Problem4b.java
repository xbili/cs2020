package sg.edu.nus.cs2020;

public class Problem4b {
	
	public static void main(String[] args) {
		try {
			// Risky code
			int[] array = new int[5];
			for (int i = 0; i < 6; i++) {
				array[i] = i;
			}
		} catch (Exception e) {
			System.out.println("Array out of bounds!");
		} catch (NullPointerException e) {
			System.out.println("Null pointer!");
		}
	}
}
