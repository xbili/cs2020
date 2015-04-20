package sg.edu.nus.cs2020;

public class Room {
	private boolean westWall, eastWall, northWall, southWall;
	public boolean onPath;
	
	Room(boolean north, boolean south, boolean east, boolean west) {
		northWall = north;
		southWall = south;
		eastWall = east;
		westWall = west;
		
		onPath = false;
	}
	
	public boolean hasWestWall() {
		return westWall;
	}
	public boolean hasEastWall() {
		return eastWall;
	}
	public boolean hasNorthWall() {
		return northWall;
	}
	public boolean hasSouthWall() {
		return southWall;
	}
}
