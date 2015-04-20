package sg.edu.nus.cs2020;

public class Trimino {

	static int numTile = 1;

	public static int[][] triminoTiling(int n, int x, int y) {
		int side = (1 << n);
		int[][] solution = new int[side][side];
		numTile = 1;
		triminoTilingRecursion(solution, 0, 0, x, y, side);
		return solution;
	}

	/**
	 * Recursive method to till in the triminoes into the solution array.
	 * General idea is to split the array into 4 quadrants, identify the
	 * quadrant with a hole, fit a trimino into the remaining three quadrants
	 * without the hole, and then recurse on each quadrant. Something like this:
	 * 
	 * ----------------------------------------
	 * |                  |                   |
	 * |      X           |                   |
	 * |                  |                   |
	 * |                  |                   |
	 * |                  |                   |
	 * |                  |T                  | 
	 * ----------------------------------------
	 * |                 T|T                  |
	 * |                  |                   |
	 * |                  |                   |
	 * |                  |                   |
	 * |                  |                   |
	 * |                  |                   |
	 * ----------------------------------------
	 * 
	 * @param solution
	 *            the solution array to be returned
	 * @param posX
	 *            X coordinate of the topleft corner of the current subarray
	 * @param posY
	 *            Y coordinate of the topleft corner of the current subarray
	 * @param holeX
	 *            X coordinate of the hole
	 * @param holeY
	 *            Y coordinate of the hole
	 * @param side
	 *            side length of the subarray
	 */
	public static void triminoTilingRecursion(int[][] solution, int posX,
			int posY, int holeX, int holeY, int side) {
		if (side == 1){
			return;
		}
		side /= 2;
		int quadrant = 0;

		// Check which quadrant the hole lies in
		// -----
		// |1|2|
		// -----
		// |3|4|
		// -----
		
		/**
		 * A more detailed schematic:
		 * 
		 *         side
		 * ----------------------------------------
		 * |X,Y               |                   |
		 * |      holeX       |                   |
		 * |      holeY       |                   |
		 * |                  |                   |
		 * |          X+side-1|X+side-1           |
		 * |          Y+side-1|Y+side             |		 
		 * ----------------------------------------
		 * |          	X+side|X+side             |
		 * |          Y+side-1|Y+side             |
		 * |                  |                   |
		 * |                  |                   |
		 * |                  |                   |
		 * |                  |                   |
		 * ----------------------------------------
		 */

		if (holeX < posX + side && holeY < posY + side){
			quadrant = 1;
		} else if (holeX < posX + side && holeY >= posY + side){
			quadrant = 2;
		} else if (holeX >= posX + side && holeY < posY + side){
			quadrant = 3;
		} else {
			quadrant = 4;
		}

		if (quadrant == 1) {
			// Place the trimino
			solution[posX + side - 1][posY + side] = numTile;
			solution[posX + side][posY + side - 1] = numTile;
			solution[posX + side][posY + side] = numTile;
			numTile++;
			
			// Recurse on each quadrant
			triminoTilingRecursion(solution, posX, posY, holeX, holeY, side);
			triminoTilingRecursion(solution, posX, posY + side,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY + side, posX
					+ side - 1, posY + side - 1, side);
		} else if (quadrant == 2) {
			solution[posX + side - 1][posY + side - 1] = numTile;
			solution[posX + side][posY + side - 1] = numTile;
			solution[posX + side][posY + side] = numTile;
			numTile++;
			triminoTilingRecursion(solution, posX, posY, posX + side - 1, posY
					+ side - 1, side);
			triminoTilingRecursion(solution, posX, posY + side, holeX, holeY,
					side);
			triminoTilingRecursion(solution, posX + side, posY,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY + side, posX
					+ side - 1, posY + side - 1, side);
		} else if (quadrant == 3) {
			solution[posX + side - 1][posY + side - 1] = numTile;
			solution[posX + side - 1][posY + side] = numTile;
			solution[posX + side][posY + side] = numTile;
			numTile++;
			triminoTilingRecursion(solution, posX, posY, posX + side - 1, posY
					+ side - 1, side);
			triminoTilingRecursion(solution, posX, posY + side,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY, holeX, holeY,
					side);
			triminoTilingRecursion(solution, posX + side, posY + side, posX
					+ side - 1, posY + side - 1, side);
		} else {
			solution[posX + side - 1][posY + side - 1] = numTile;
			solution[posX + side - 1][posY + side] = numTile;
			solution[posX + side][posY + side - 1] = numTile;
			numTile++;
			triminoTilingRecursion(solution, posX, posY, posX + side - 1, posY
					+ side - 1, side);
			triminoTilingRecursion(solution, posX, posY + side,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY,
					posX + side - 1, posY + side - 1, side);
			triminoTilingRecursion(solution, posX + side, posY + side, holeX,
					holeY, side);
		}
	}

	public static void main(String[] args) {
		int n = 3;
		int[][] solution = triminoTiling(n, 2, 2);
		for (int i = 0; i < (1 << n); i++) {
			for (int j = 0; j < (1 << n); j++) {
				if (solution[i][j] < 10)
					System.out.printf(" ");
				System.out.printf("%d ", solution[i][j]);
			}
			System.out.println();
		}
	}
}
