package sg.edu.nus.cs2020;

public interface IMazeSolverWithPower extends IMazeSolver {
	/**
	 * Finds the shortest path from a given starting coordinate to an
	 * ending coordinate with a fixed number of Powers given
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @param superpowers
	 * @return minimum moves from start to end
	 * @return null if there is no path from start to end
	 * @throws Exception 
	 */
	Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception;
}
