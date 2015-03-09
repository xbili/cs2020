package sg.edu.nus.cs2020;

public class Trimino {
	private Pair[] tiles = new Pair[3];
	
	public void setTiles(Pair tile1, Pair tile2, Pair tile3) {
		tiles[0] = tile1;
		tiles[1] = tile2;
		tiles[2] = tile3;
	}
}
