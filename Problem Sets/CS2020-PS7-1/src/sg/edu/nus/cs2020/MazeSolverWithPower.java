package sg.edu.nus.cs2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private Maze maze = null;
	private Pair initNode;
	
	/** 
	 * Initializes the maze solver
	 */
	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}
	
	/**
	 * Original implementation of pathSearch without superpowers. 
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
		
		// BFS Data structures
		boolean[][] m_visited;
		HashMap<Pair, Pair> m_parents;
		Queue<Pair> m_frontier;
		
		// Initialize required data structures for BFS
		m_visited = new boolean[maze.getRows()][maze.getColumns()];
		m_parents = new HashMap<Pair, Pair>();
		m_frontier = new ArrayDeque<Pair>();
		int pathLength = 0;
		
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
			return Integer.MAX_VALUE;
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
	 * @return ArrayList<Pair> of rooms
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

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
			int endCol, int superpowers) throws Exception {
		// Handles invalid parameters
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns() ||
				superpowers < 0) {
			System.out.println("startRow: " + startRow);
			System.out.println("startCol: " + startCol);
			System.out.println("endRow: " + endRow);
			System.out.println("endCol: " + endCol);
			System.out.println("superpowers: " + superpowers);
			throw new Exception("MazeSolverWithPower: invalid pathSearch parameters");
		}
		
		if (startRow == endRow && startCol == endCol) {
			maze.getRoom(startRow, startCol).onPath = true;
			return 0;
		}
		
		// Calls normal pathSearch
		if (superpowers == 0) {
			return pathSearch(startRow, startCol, endRow, endCol);
		} else {
			/**
			 * Initialize BFS
			 */
			
			// BFS Data structures
			boolean[][] m_visited;
			HashMap<Pair, Pair> m_parents;
			Queue<Pair> m_frontier;
			
			// Initialize required data structures for BFS
			m_visited = new boolean[maze.getRows()][maze.getColumns()];
			m_parents = new HashMap<Pair, Pair>();
			m_frontier = new ArrayDeque<Pair>();
			int pathLength = 0;
			Pair dest = null;
			Pair initNode = new Pair(startRow, startCol);
			
			// Actual BFS
			m_frontier.add(initNode);
			m_visited[startRow][startCol] = true;
			
			while (!m_frontier.isEmpty()) {
				Pair curr = m_frontier.remove();
				
				// Only look at walls if we have superpowers remaining
				if (superpowers > 0) {
					// Get walls around current room
					ArrayList<Pair> walls = getWalls(curr);
					
					// Evaluate if superpowers should be used on walls
					superpowers = evaluateWalls(endRow, endCol, superpowers,
							m_visited, m_parents, m_frontier, curr, walls);	
				}
				
				// Get neighbours around current room
				ArrayList<Pair> neighbours = getNeighbours(curr);
				
				for (int j=0; j < neighbours.size(); j++) {
					Pair currNeighbour = neighbours.get(j);
					if (!m_visited[currNeighbour.row][currNeighbour.col]) {
						m_visited[currNeighbour.row][currNeighbour.col] = true;
						m_frontier.add(currNeighbour);
						m_parents.put(currNeighbour, curr);
					}
				}
				
				if (curr.row == endRow && curr.col == endCol) {
					dest = curr;
					m_frontier.clear();
				}
			}
			
			// Backtrack
			if (dest == null) {
				return Integer.MAX_VALUE;
			}
			
			maze.getRoom(dest.row, dest.col).onPath = true;
			while (!dest.equals(initNode)) {
				Pair next = m_parents.get(dest);
				
				if (next != null) {
					maze.getRoom(next.row, next.col).onPath = true;
				}
				
				dest = next;
				pathLength++;
			}
			
			return pathLength+1;
		}
	}

	private int evaluateWalls(int endRow, int endCol, int superpowers,
			boolean[][] m_visited, HashMap<Pair, Pair> m_parents,
			Queue<Pair> m_frontier, Pair curr, ArrayList<Pair> walls)
			throws Exception {
		for (int i=0; i < walls.size(); i++) {
			Pair currWall = walls.get(i);
			
			// Check if the wall is at the boundary of the maze
			if (currWall.row < 0 || currWall.row >= maze.getRows() ||
					currWall.col < 0 || currWall.col >= maze.getColumns()) {
				continue; 
			}
			
			// Path taken with the wall knocked down and
			// path taken with the wall intact
			int knockItDown = pathSearch(currWall.row, currWall.col, endRow, endCol, superpowers-1);
			int cont = pathSearch(curr.row, curr.col, endRow, endCol);
			
			// Reset maze because the previous pathSearch screws it up.
			for (int k=0; k < maze.getRows(); k++) {
				for (int j=0; j < maze.getColumns(); j++) {
					maze.getRoom(k, j).onPath = false;
				}
			}
			
			// If path taken after knocking it down is faster, we use superpower.
			if (knockItDown < cont) {
				if (!m_visited[currWall.row][currWall.col]) {
					m_visited[currWall.row][currWall.col] = true;
					m_frontier.add(currWall);
					m_parents.put(currWall, curr);
					superpowers--;
				}
			}
		}
		return superpowers;
	}
	
	private ArrayList<Pair> getWalls(Pair room) {
		ArrayList<Pair> walls = new ArrayList<Pair>();
		Room curr = maze.getRoom(room.row, room.col);
		
		if (curr.hasNorthWall()) {
			walls.add(new Pair(room.row-1, room.col));
		} 
		if (curr.hasSouthWall()) {
			walls.add(new Pair(room.row+1, room.col));
		}
		if (curr.hasEastWall()) {
			walls.add(new Pair(room.row, room.col+1));
		}
		if (curr.hasWestWall()) {
			walls.add(new Pair(room.row, room.col-1));
		}
		
		return walls;
	}
	
	public static void main(String [] args) {
		try {
			Maze maze = Maze.readMaze("maze.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			
			solver.initialize(maze);
			// System.out.println( solver.pathSearch(0, 0, 3, 0) );
			System.out.println( solver.pathSearch(0, 0, 2, 2, 3) );
			MazePrinter.printMaze(maze);
			
			// System.out.println( solver.numReachable(0) );
			// MazePrinter.printMaze(maze);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
