import static org.junit.Assert.*;

import org.junit.Test;


public class ProblemBTest {

	@Test
	public void test1() {
		int[] testArray = {1, 2, 3, 4, 4, 4, 5, 6, 7};
		int[] solution = ProblemB.binarySearchRange(testArray, 4);
		int[] correctAnswer = {3, 5};
		assertArrayEquals(correctAnswer, solution);
	}
	
	@Test
	public void test2() {
		int[] testArray = {1, 2, 3, 4, 4, 4, 5, 6, 7};
		int[] solution = ProblemB.binarySearchRange(testArray, 8);
		int[] correctAnswer = {-1, -1};
		assertArrayEquals(correctAnswer, solution);
	}

	@Test
	public void test3() {
		int[] testArray = {1, 2, 3, 4, 4, 4, 5, 6, 7};
		int[] solution = ProblemB.binarySearchRange(testArray, 5);
		int[] correctAnswer = {6, 6};
		assertArrayEquals(correctAnswer, solution);
	}
}
