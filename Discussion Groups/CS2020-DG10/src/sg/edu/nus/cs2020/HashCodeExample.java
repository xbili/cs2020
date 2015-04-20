package sg.edu.nus.cs2020;

public class HashCodeExample {
	public static void main(String[] args) {
		
		int hashed1 = -17 % 2076428;
		int hashed = Math.abs(Integer.MIN_VALUE) % 457096;
		
		System.out.println(hashed1);
		System.out.println(hashed);
	}
	
}
