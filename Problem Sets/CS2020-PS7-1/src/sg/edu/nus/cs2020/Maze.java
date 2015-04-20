package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
	private int rows, columns;			// size of the maze

	Room [][] room;
		
	final static char WALL = '#';
	final static char START = 'S';
	final static char END = 'T';
	
	Maze() {
		rows = columns = 0;		
		room = null;
	}

	/**
	 * Get a room given the row and column (0-index)
	 * 
	 * @param row
	 * @param column
	 * @return room
	 */
	public Room getRoom(int row, int column) {
		if (row >= rows || column >= columns || row < 0 || column < 0) {
			throw new IllegalArgumentException();
		}
		
		return room[row][column];
	}
	
	/**
	 * @return number of rows in the maze
	 */
	public int getRows() {
		return rows/2;
	}
	/**
	 * @return number of columns in the maze
	 */
	public int getColumns() {
		return columns/2;
	}

	
	/**
	 * Reads in a ASCII description of a maze and returns the
	 * created maze object
	 * 
	 * @param fileName
	 * @return maze
	 * @throws IOException
	 */
	public static Maze readMaze(String fileName) throws IOException{
		FileReader fin = new FileReader(fileName);
		BufferedReader bin = new BufferedReader(fin);

		Maze maze = new Maze();

		List<String> input = new ArrayList<String>(); 
		String line;
		while( (line = bin.readLine()) != null) {
			if (maze.columns == 0 && line.length() > 0) {
				maze.columns = line.length();
			} else if (line.length() == 0) {
				// reach a new line => end of input
				break;
			} else if (line.length() != maze.columns) {
				throw new IOException("Invalid input format");
			}
			maze.columns = Math.max(maze.columns, line.length());
			maze.rows++;
			input.add(line);
		}

		if (maze.rows == 0 || maze.rows % 2 == 0 || maze.columns % 2 == 0) {
			throw new IOException("Invalid input format");
		}
		
		maze.room = new Room[maze.rows/2][maze.columns/2];
		for (int i=1;i<maze.rows-1;i+=2) {
			for (int j=1;j<maze.columns-1;j+=2) {
				maze.room[i/2][j/2] = new Room(
						 input.get(i-1).charAt(j)==WALL  // north: i-1
						,input.get(i+1).charAt(j)==WALL  // south: i+1
						,input.get(i).charAt(j+1)==WALL  // east: j+1
						,input.get(i).charAt(j-1)==WALL  // west: j-1
					);
			}
		}
				
		assert(!bin.ready());			
		bin.close();
		
		return maze;			
	}
}
