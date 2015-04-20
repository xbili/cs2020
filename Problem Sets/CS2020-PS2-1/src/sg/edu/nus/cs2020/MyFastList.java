package sg.edu.nus.cs2020;
import sg.edu.nus.cs2020.FixedLengthList;

/**
 * Problem 1b.
 * 
 * This algorithm takes two lists, one to store the original list, the other acts as a priority
 * list that will reset itself everytime it reaches 5 values. 
 * 
 * The idea why this works faster than the MoveToFrontList and FixedLengthList is because shifting
 * is an expensive operation, and linear searching every single time is also repetitive work, especially
 * if there are recurring keys.
 * 
 * A priority list will reduce the number of time that the program has to perform a linear search on the
 * entire m_list, and only perform it when it has not been found before.
 * 
 * There should be an optimal number for maxPriorityLength that will maximize the search algorithm speed.
 * However for this example, 5 seems to perform better than the normal FixedLengthList's search method.
 * 
 * 
 * Collaborated with Ryan Lou, we came up with the idea of a priority list together.
 * 
 */

public class MyFastList extends FixedLengthList {
	// Constructor
	public MyFastList(int length) {
		super(length);
	}
	
	// Override search method
	public boolean search(int key) {
		// Priority list count
		int pCount = 0;
		
		// Max length of buffer
		int maxPriorityLength = 5;
		
		// Buffer array - a priority list
		int[] priorityList = new int[maxPriorityLength];
		
		if (m_list[0] == key || priorityList[0] == key || priorityList[pCount] == key) {
			
			// If first item of priority list is key OR
			// If the first item of the priority list is the key OR
			// If the last item of the priority list is the key
			return true;
			
		} else if (m_list[m_max] == key) {
			// If the key is not found in priority list:
			// AND
			// If the last item of m_list is the key
			
			// Add the key into priority list
			priorityList[pCount] = m_list[m_max];
			pCount++;
			
			// Resets priority list count when it reaches max priority list length
			if (pCount >= maxPriorityLength) {
				pCount = 0;
			}
			
			return true;
			
		} else {
			// If the key is not found in priority list:
			// AND
			// If the item is NOT the last item of m_list
			
			// Linear search the priority list
			for (int i = 1; i < pCount - 1; i++) {
				if (priorityList[i] == key) return true;
			}
			
			
			// If the item is not inside the priority list:
			
			// Linear search from second to second last index
			for (int i = 1; i < m_length - 1; i++) {
				if (m_list[i] == key) {
					// Adds the found key into priority list
					priorityList[pCount] = m_list[i];
					pCount++;
					
					// Resets buffer count when it reaches max buffer length
					if (pCount >= maxPriorityLength) { 
						pCount = 0; 
					}
					
					return true;
				}
			}
			
			return false;
		}
	}
}
