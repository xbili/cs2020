import static org.junit.Assert.*;

import org.junit.Test;

public class ProblemATest {

	@Test
	public void test1() {
		int a = 1, b = 2, c = 3, d = 4, e = 5, f = 4;
		double ans = ProblemA.solveEquation(a, b, c, d, e, f);
		String ansString = String.format("%.4f", ans);
		for (int i=0; i<10; i++){
			assertEquals("ProblemATest", "0.4975", ansString);
		}
	}
	
	@Test
	public void test2() {
		int a = 1, b = 1, c = 1, d = 1, e = 1, f = 0;
		double ans = ProblemA.solveEquation(a, b, c, d, e, f);
		String ansString = String.format("%.4f", ans);
		for (int i=0; i<10; i++){
			assertEquals("ProblemATest", "0.0000", ansString);
		}
	}

}
