package sg.edu.nus.cs2020;

import java.util.*;

public class ChurchDatabase {
	private final static long REQ_CHAKRA = 1000000000;
	public ArrayList<Devotee> devotees;

	public ChurchDatabase() {
		devotees = new ArrayList<Devotee>();
	}

	public void addDevotee(Devotee devotee) {
		devotees.add(devotee);
	}

	public boolean hasCompatiblePair() {
		Collections.sort(devotees);
		int runner = 1;
		for (int i = 0; i < devotees.size(); i++) {
			if (devotees.get(i) == devotees.get(runner))
				return true;
			if (runner == devotees.size() - 1)
				return false;
			runner++;
		}
		return false;
	}

	public boolean hasComplementaryPair() {
		ArrayList<Long> chakraList = new ArrayList<Long>();
		for (int i = 0; i < devotees.size(); i++) {
			chakraList.add(devotees.get(i).getChakra());
		}
		Collections.sort(chakraList);

		int ptr1 = 0, ptr2 = chakraList.size() - 1;
		
		while (ptr1 != ptr2) {
			long result = chakraList.get(ptr1) + chakraList.get(ptr2);
			
			if (result > REQ_CHAKRA) {
				ptr2--;
			} else if (result == REQ_CHAKRA) {
				return true;
			} else {
				ptr1++;
			}
		}
		return false;
	}

	public boolean hasComplementarySet() {
		// Finds every subset in the devotees list and check
		
		
		
		return false;
	}
}
