package sg.edu.nus.cs2020;

public class Devotee implements Comparable<Devotee> {
	private String name;
	private long karma;
	private long chakra;
	
	public Devotee (String name, long karma, long chakra){
		this.name = name;
		this.karma = karma;
		this.chakra = chakra;
	}

	public String getName() {
		return name;
	}

	public long getKarma() {
		return karma;
	}

	public long getChakra() {
		return chakra;
	}

	@Override
	public int compareTo(Devotee d) {
		if (this.getKarma() > d.getKarma()) {
			return 1;
		} else if (this.getKarma() == d.getKarma()) {
			return 0;
		} else {
			return -1;
		}
	}

}
