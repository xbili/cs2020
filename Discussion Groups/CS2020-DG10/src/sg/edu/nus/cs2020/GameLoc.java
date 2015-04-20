package sg.edu.nus.cs2020;

public class GameLoc {
	private float x, y;
	private boolean state;
	
	public int hashCode() {
		if (state) {
			return (int) Math.floor(this.x);
		} else {
			return (int) Math.floor(this.y);
		}
	}
	
	public static void addGlork(GameLoc location, GameEntity glork) {
		
	}
	
	public static boolean nearGlork(GameLoc location) {
		
		
		return false;
	}
	
	public static void main(String[] args) {
		
	}
}
