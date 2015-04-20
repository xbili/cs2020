package sg.edu.nus.cs2020;
import java.util.Arrays;

import sg.edu.nus.cs2020.FixedLengthList;

/**
 * MoveToFrontList
 * Description: 
 * CS2020 2012
 * 
 * You need to create a constructor and implement search.
 */


/**
 * 
 */
public class MoveToFrontList extends FixedLengthList {
	
	// Constructor
	public MoveToFrontList(int length) {
		// Calls FixedLengthList constructor with `length` parameter
		super(length);
	}
	
	public boolean search(int key) {
		int foundAt;
		int[] buffer = new int[m_length];
		
		for (int i = 0; i < m_length; i++) {
			if (m_list[i] == key) {
				foundAt = i;
				buffer[0] = m_list[i];
				
				// Shift the portion before the key
				for (int j = 0; j < foundAt; j++) {
					buffer[j + 1] = m_list[j]; 
				}
				
				// Shift the portion after the key
				for (int j = foundAt + 1; j < m_length; j++) {
					buffer[j] = m_list[j];
				}
				
				m_list = Arrays.copyOf(buffer, buffer.length);
				return true;
			}
		}
		
		return false;
	}
}
