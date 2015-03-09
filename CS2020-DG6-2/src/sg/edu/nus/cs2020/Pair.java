package sg.edu.nus.cs2020;

public class Pair {
	private int head, tail;
	
	public Pair(int head, int tail) {
		this.head = head;
		this.tail = tail;
	}

	public boolean isBlack() {
		if ((head + tail) % 2 != 0) return true;
		else return false;
	}
	
	public boolean isWhite() {
		if ((head + tail) % 2 == 0) return true;
		else return false;
	}
	
	public int getHead() {
		return head;
	}

	public int getTail() {
		return tail;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}
}
