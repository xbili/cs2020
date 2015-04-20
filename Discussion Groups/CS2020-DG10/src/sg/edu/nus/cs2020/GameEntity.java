package sg.edu.nus.cs2020;

public class GameEntity {
	private boolean glork;
	private boolean player;
	
	public GameEntity() {
		this.glork = true;
		this.player = false;
	}
	
	public GameEntity(String player) {
		this.glork = false;
		this.player = true;
	}

	public boolean isGlork() {
		return glork;
	}

	public boolean isPlayer() {
		return player;
	}
}
