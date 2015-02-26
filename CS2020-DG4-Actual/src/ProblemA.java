import java.io.*;

class ProblemA {
	public static final int NUM_QUERIES = 100000;
	public static final double PRECISION_ERROR = 0.0000000001; // 10^-9

	public static double solveEquation(int a, int b, int c, int d, int e, int f) {
		// =======================
		// INSERT YOUR CODE HERE
		// =======================
		
		// Initialize variables
		double begin = 0;
		double end = 1;
		
		// Typecast a, b, c, d, e to double 
		double dA = (double) a;
		double dB = (double) b;
		double dC = (double) c;
		double dD = (double) d;
		double dE = (double) e;
		double dF = (double) f;
		
		// Loop invariant
		while (end - begin > PRECISION_ERROR) {
			// Get middle element
			double middle = begin + (end - begin) / 2.0;
			
			// x^5
			double middlePow5 = Math.pow(middle, 5);
			// x^4
			double middlePow4 = Math.pow(middle, 4);
			// x^3
			double middlePow3 = Math.pow(middle, 3);
			// x^2
			double middlePow2 = Math.pow(middle, 2);
			
			double curr_result = middlePow5 * dA +
								middlePow4 * dB + 
								middlePow3 * dC +
								middlePow2 * dD + 
								middle * dE;
			
			// Reset begin and end
			if (curr_result < dF) {
				// If the current result is lesser than expected
				// Don't have to add + 0.0001 because floating point will not go into infinite loop
				begin = middle;
			} else if (curr_result > dF) {
				// If the current result is larger than expected
				end = middle;
			} else {
				// Answer is found
				return curr_result;
			}
		}
		
		// =======================
		// END OF CODE
		// =======================
		return begin;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));
		for (int i = 0; i < NUM_QUERIES; i++) {
			int a = Integer.parseInt(br.readLine());
			int b = Integer.parseInt(br.readLine());
			int c = Integer.parseInt(br.readLine());
			int d = Integer.parseInt(br.readLine());
			int e = Integer.parseInt(br.readLine());
			int f = Integer.parseInt(br.readLine());
			double solution = solveEquation(a, b, c, d, e, f);
			pr.printf("%.4f\n", solution);
		}
		pr.close();
		System.exit(0);
	}
}
