package sg.edu.nus.cs2020;

public class SubsetSum {
	static int n = 4;
	static int c = 10;
	static int[] v = {1, 2, 3, 4};
	static int[] w = {5, 2, 3, 6};
	static int[][] memo;
	
	public static int subsetSum(int k, int remain) {
		if (k == -1) return 0;
		if (memo[k][remain] == 0) return 0;
		else {
			if (w[k] > remain) {
				return memo[k][remain] = subsetSum(k-1, remain);
			} else {
				return memo[k][remain] = Math.max(subsetSum(k-1,  remain),
						subsetSum(k-1, remain-w[k]) + v[k]);
			}
		}
	}
	
	public static void main(String[] args) {
		memo = new int[4][11];
		for (int i=0; i < 4; i++) {
			for (int j=0; j < 11; j++) {
				memo[i][j] = -1;
				// For bottom-up, make changes to table instead
			}
		}
		
		System.out.println(subsetSum(3, 10));
	}
}
