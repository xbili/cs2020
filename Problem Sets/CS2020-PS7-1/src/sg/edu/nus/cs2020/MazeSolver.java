package sg.edu.nus.cs2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private Maze maze = null;
	private Pair initNode;
	
	// For BFS
	private boolean[][] m_visited;
	private HashMap<Pair, Pair> m_parents;
	private Queue<Pair> m_frontier;
	private int pathLength;
	
	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Finds the shortest path from the (startRow, startCol) -> (endRow, endCol)
	 * 
	 * This method first runs a BFS for the room, and backtracks once it reaches the room.
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol)
			throws Exception {
		if (startRow < 0 ||
				startCol < 0 ||
				endRow >= maze.getRows() ||
				endCol >= maze.getColumns()) {
			throw new Exception("MazeSolver: Invalid coordinates for pathSearch.");
		}
		
		if (maze == null) {
			throw new Exception("MazeSolver: Maze is not initialized.");
		}
		
		// Initialize required data structures for BFS
		m_visited = new boolean[maze.getRows()][maze.getColumns()];
		m_parents = new HashMap<Pair, Pair>();
		m_frontier = new ArrayDeque<Pair>();
		pathLength = 0;
		
		// Initialize all onPath values to false
		for (int i=0; i < maze.getRows(); i++) {
			for (int j=0; j < maze.getColumns(); j++) {
				maze.getRoom(i, j).onPath = false;
			}
		}
		
		// Adds the start node into queue
		initNode = new Pair(startRow, startCol);
		m_frontier.add(initNode);
		m_visited[initNode.row][initNode.col] = true;
		
		// Location of destination found
		Pair dest = null;
		
		// Main BFS code
		Pair curr;
		while (!m_frontier.isEmpty()) {
			curr = m_frontier.remove();
			
			// Find all neighbour rooms to go, and add to frontier
			ArrayList<Pair> neighbours = getNeighbours(curr);
			
			for (int i=0; i < neighbours.size(); i++) {
				Pair currNeighbour = neighbours.get(i);
				
				if (m_visited[currNeighbour.row][currNeighbour.col] == false) {
					m_frontier.add(currNeighbour);
					m_parents.put(currNeighbour, curr);
					m_visited[currNeighbour.row][currNeighbour.col] = true;
				}
			}
			
			// If curr is the destination, clear the queue to end while loop
			if (curr.row == endRow && curr.col == endCol) {
				m_frontier.clear();
				dest = curr;
			}
		}
		
		if (dest == null) {
			return null;
		}
			
		// Backtrack
		maze.getRoom(dest.row, dest.col).onPath = true;
		while (!dest.equals(initNode)) {
			Pair next = m_parents.get(dest);
			
			if (next != null) {
				maze.getRoom(next.row, next.col).onPath = true;
			}
			
			dest = next;
			pathLength++;
		}
		
		return pathLength;
	}
	
	/**
	 * Finds and returns all possible neighbours of the room
	 * 
	 * @param room - current room
	 * @return 
	 */
	private ArrayList<Pair> getNeighbours(Pair room) {
		ArrayList<Pair> neighbours = new ArrayList<Pair>();
		Room curr = maze.getRoom(room.row, room.col);
		
		if (!curr.hasNorthWall()) {
			neighbours.add(new Pair(room.row-1, room.col));
		} 
		if (!curr.hasSouthWall()) {
			neighbours.add(new Pair(room.row+1, room.col));
		}
		if (!curr.hasEastWall()) {
			neighbours.add(new Pair(room.row, room.col+1));
		}
		if (!curr.hasWestWall()) {
			neighbours.add(new Pair(room.row, room.col-1));
		}
		
		return neighbours;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (k < 0) {
			throw new IllegalArgumentException("MazeSolver: numReachable invalid argument (less than zero).");
		}
		
		int initRow = initNode.row;
		int initCol = initNode.col;
		
		System.out.println(initRow + ", " + initCol);
		
		int count = 0;
		
		// Run pathSearch to every room
		for (int i=0; i < maze.getRows(); i++) {
			for (int j=0; j < maze.getColumns(); j++) {
				if (pathSearch(initRow, initCol, i, j) != null &&
						pathSearch(initRow, initCol, i, j) == k) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	public static void main(String [] args) {
		try {
			Maze maze = Maze.readMaze("maze.txt");
			IMazeSolver solver = new MazeSolver();
			
			solver.initialize(maze);
			System.out.println( solver.pathSearch(0, 0, 3, 3) );
			MazePrinter.printMaze(maze);
			
			System.out.println( solver.numReachable(3) );
			//MazePrinter.printMaze(maze);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
