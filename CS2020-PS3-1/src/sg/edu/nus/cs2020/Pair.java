package sg.edu.nus.cs2020;

/**
 * A class for pairs similiar to the one in Scheme and JediScript of CS1101S.
 * 
 * Contains a head and a tail, and is immutable, unless usage of setHead or setTail methods.
 * 
 * Used to test for stability in the sorting algorithms
 * 
 * @author xbili
 *
 */
public class Pair implements Comparable<Pair> {
	/**
	 * Head and tail properties of a pair
	 */
	private int head;
	private int tail;
	
	/**
	 * Sets the head of the pair as value
	 * 
	 * @param value
	 */
	public void setHead(int value) {
		this.head = value;
	}
	
	/**
	 * Sets the tail of the pair as value
	 * 
	 * @param value
	 */
	public void setTail(int value) {
		this.tail = value;
	}
	
	/**
	 * Returns the head of the pair
	 * 
	 * @return
	 */
	public int getHead() {
		return this.head;
	}
	
	/**
	 * Returns the tail of the pair
	 * 
	 * @return
	 */
	public int getTail() {
		return this.tail;
	}
	
	/**
	 * Overrides Comparable's method to make comparing between the heads of the pair
	 * ignoring the tail. Thus when it is being sorted, it will be sorted based on the head value.
	 * 
	 */
	@Override
	public int compareTo(Pair o) {
		Integer wrap_head = (Integer) this.head;
		return wrap_head.compareTo(o.head);
	}
	
	/**
	 * @Constructor
	 * Takes in a head and a tail to declare a new pair
	 * 
	 * @param head
	 * @param tail
	 */
	public Pair(int head, int tail) {
		this.head = head;
		this.tail = tail;
	}
}
