package sg.edu.nus.cs2020;

public class MyFasterList extends FixedLengthList {
	// Constructor
	public MyFasterList(int length) {
		super(length);
	}
	
	// Override add method
	public boolean add(int key, boolean found) {		
		m_max++;
		if (m_max < m_length) {
			if (!found) {
				// If found is false
				int[] buffer = new int[m_length + 1];
				buffer[0] = -key;
				for (int i = 0; i < m_length; i++) {
					buffer[i + 1] = m_list[i];
				}
			} else {
				m_list[m_max] = key;
			}
			return true;
		} else {
			System.out.println("Error: list length exceeded.");
			return false;
		}
	}
	
	// Override search method
	public boolean search(int key) {
		for (int i=0; i<=m_max; i++){
			if (m_list[i] == key){
				return true;
			} else if (m_list[i] == -key) {
				return false;
			}
		}
		add(key, false);
		return false;
	}
}
