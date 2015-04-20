import java.io.*;

class ProblemB {
	public static final int MAX_ARRAY_SIZE = 100000;
	public static final int NUM_QUERIES = 100000;

	public static int[] binarySearchRange(int[] array, int x) {
		int[] answer = new int[2];
		// =======================
		// INSERT YOUR CODE HERE
		// =======================
		
		
		
		
		
		// =======================
		// END OF CODE
		// =======================

		return answer;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));
		int[] array = new int[MAX_ARRAY_SIZE];
		for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
			array[i] = Integer.parseInt(br.readLine());
		}
		for (int i = 0; i < NUM_QUERIES; i++) {
			int query = Integer.parseInt(br.readLine());
			int[] solution = binarySearchRange(array, query);
			pr.printf("%d %d\n", solution[0], solution[1]);
		}
		pr.close();
		System.exit(0);
	}
}
