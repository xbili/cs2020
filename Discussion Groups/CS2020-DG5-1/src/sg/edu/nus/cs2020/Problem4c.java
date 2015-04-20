package sg.edu.nus.cs2020;

public class Problem4c {
	public void riskyFunction() throws TestException {
		throw new TestException("Testing...");
		// System.out.println("This is risky! Don't do it!");
	}
	
	public void testRiskyFunction() throws TestException {
		riskyFunction();
		System.out.println("Let's test the risky function.");
	}
	
	public void runTheTest() {
		try {
			testRiskyFunction();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		Problem4c problem = new Problem4c();
		problem.runTheTest();
	}
}
