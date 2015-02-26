package sg.edu.nus.cs2020;

/**
 * FixedLengthList
 * Description: Simple array implementation of a fixed-length list of integers
 * CS2020 2012
 */


/**
 * Class: FixedLengthList
 * Description: Simple array-based implementation of a fixed-lengthlist.
 * All the elements in the list are integers >= 0.
 */
public class FixedLengthList {

	// Maximum length of the list
	protected int m_length = 0;
	
	// Array for storing elements in the list
	protected int[] m_list = null;
	
	// Maximum full slot in the array
	// m_list[m_max] contains the last element added to the list
	protected int m_max = -1;
	
	/**
	 * Constructor
	 * @param length the length of the list
	 * Creates a new list of specified length.
	 * Initializes array m_list, setting every slot to -1.
	 */
	public FixedLengthList(int length){
		m_length = length;
		m_list = new int[length];
		for (int i=0; i<length; i++){
			m_list[i] = -1;
		}
		
	}
	
	/**
	 * Method: add appends a new integer to the end of the list
	 * @param key the integer to add to the list
	 * @return true if the add succeeds, and false otherwise
	 * Outputs an error if the key is < 0, or of the length of the list is exceeded.
	 */
	public boolean add(int key){		
		m_max++;
		if (m_max < m_length){
			m_list[m_max] = key;
			return true;
		}
		else{
			System.out.println("Error: list length exceeded.");
			return false;
		}
	}
	
	
	/**
	 * Method: search checks whether the key is in the list
	 * @param key the integer to search for in the list
	 * @return true if key is in the last and false otherwise
	 * Description: performs a linear search in the list
	 */
	public boolean search(int key){
		for (int i=0; i<=m_max; i++){
			if (m_list[i] == key){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * toString
	 * 
	 * Description
	 * + Converts the list into a printable string
	 */
	@Override
	public String toString(){
		String output = "";
		
		for (int i = 0; i <= m_max; i++){
			output += m_list[i] + " ";
		}
		
		return output.trim();
	}
	
}
