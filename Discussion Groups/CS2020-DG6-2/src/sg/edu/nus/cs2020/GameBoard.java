package sg.edu.nus.cs2020;

import java.util.ArrayList;

public class GameBoard {
	private int[][] board;
	
	private int lower_x, upper_x,
				lower_y, upper_y;
	private int dim;
	
	ArrayList<Pair> broken;
	
	public GameBoard(int n, int x, int y) {
		dim = n;
		board = new int[n][n];
		
		lower_x = x-1;
		upper_x = lower_x+1;
		lower_y = y-1;
		upper_y = lower_y+1;
		
		broken.add(new Pair(x-1, y-1));
	}
	
	public void solveBoard() {
		int x_length = upper_x - lower_x;
		int y_length = upper_y - lower_y;
		
		while (x_length != dim && y_length != dim) {
			
			
		}
		
	}
	
	private boolean isEdge(Pair current) {
		int x = current.getHead();
		int y = current.getTail();
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		neighbours.add(board[x+1][y-1]);
		neighbours.add(board[x+1][y]);
		neighbours.add(board[x+1][y+1]);
		neighbours.add(board[x][y-1]);
		neighbours.add(board[x][y+1]);
		neighbours.add(board[x-1][y-1]);
		neighbours.add(board[x-1][y]);
		neighbours.add(board[x-1][y+1]);
		
		int emptyCount = 0;
		for (int neighbour : neighbours) {
			if (neighbour == 0) emptyCount++;
		}
		
		if (emptyCount == 2) return true;
		else return false;
	}
	
	public boolean isSolved() {
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) return false;
			}
		}
		
		return true;
	}
	
	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = board.length-1; j >= 0; j--) {
				System.out.print(board[i][j]);
			}
			System.out.print('\n');
		}
	}
	
	public static void main(String[] args) {
		GameBoard newGame = new GameBoard(16, 4, 13);
		newGame.solveBoard();
		newGame.printBoard();
		System.out.println(newGame.isSolved());
		
	}
}
