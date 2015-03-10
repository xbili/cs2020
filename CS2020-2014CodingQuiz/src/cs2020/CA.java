package cs2020;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Framework code for the CA (Cellular Automaton) class for
 * the CS2020 Coding Quiz, 2014
 * @author gilbert
 *
 */
public class CA implements ICA {

	// Stores the state of the CA.  Each entry should be a 0 or a 1.
	int[] m_state;
	
	// The rule for updating the CA.  This should be an array of 
	// size 8, where each entry is a 0 or a 1.
	int[] m_rule;
	
	// The size of the CA, initialized in the constructor.
	int m_size = 0;
	
	// Boolean flag indicating whether the CA is initialized.
	boolean m_initialized = false;
	
	/**
	 * Constructor
	 * @param size
	 */
	public CA(int size){
		// Create a new array of the specified size.
		// Notice that we create an array with (size+2) entries.
		// m_state[0] and m_state[n-1] are dummy values marking
		// the boundary of the CA.
		m_state = new int[size+2];
		// Update the size.
		m_size = size;
		// Note that the CA has not yet been initialized
		m_initialized = false;
	}
	
	@Override
	public void initialize(int[] rule){
		if (rule == null) System.err.println("Null rule error.");
		
		m_rule = rule;
		
		// Set dummy values
		m_state[0] = 0;
		m_state[m_state.length-1] = 0;
		
		for (int i=1; i < m_state.length-1; i++) {
			if (i == m_state.length/2) m_state[i] = 1;
			else m_state[i] = 0;
		}
		
		m_initialized = true;
	}
		
	@Override
	public void step(){
		int temp[] = Arrays.copyOf(m_state, m_state.length);
		updateState(temp);
		m_state = Arrays.copyOf(temp, temp.length);
	}

	private void updateState(int[] temp) {
		int left;
		int mid;
		int right;
		int binNum;
		String binString;
		for (int i=1; i < m_state.length-1; i++) {
			mid = i;
			left = i-1;
			right = i+1;
			
			binString = Integer.toString(m_state[left]) + Integer.toString(m_state[mid]) + Integer.toString(m_state[right]);
			binNum = Integer.parseInt(binString, 2);
			
			temp[mid] = m_rule[binNum];
		}
	}
	
	@Override
	public Iterator<String> iterator() {
		return new CAIterator(this);
	}
	
	class CAIterator implements Iterator<String> {
		int count;
		CA automaton;
		
		public CAIterator(CA automaton) {
			count = 0;
			this.automaton = automaton;
		}
		
		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public String next() {
			if (count == 0) {
				count++;
				return automaton.toString();
			} else {
				automaton.step();
				count++;
				return automaton.toString();
			}
		}
		
		public void remove() {}
	}

	/**
	 * toString
	 * Transforms the state of the CA into a string.
	 */
	@Override
	public String toString(){
		String answer = "";
		for (int i=1; i<m_size+1; i++){
			if (m_state[i] > 0){
				answer += '1';
			}
			else {
				answer += ' ';
			}
		}
		
		return answer;
	}
	
	public static void main(String[] args){
		
		// Create a new cellular automaton of size 31
		CA example = new CA(31);
		
		// Create a new rule
		int[] rule = {0, 1, 0, 0, 1, 0, 0, 0};
		
		// Initialize the CA with the rule
		example.initialize(rule);
		
		// Get an iterator
		Iterator<String> iterator = example.iterator();
		
		// Run the cellular automaton for 17 steps
		for (int i=0; i<17; i++){
			if (iterator.hasNext()){
				String s = iterator.next();
				System.out.println(s);
			}
		}
	}
}
