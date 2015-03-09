package sg.edu.nus.cs2020;

import java.util.ArrayList;

public class CoconutHunter {
	public static int findLightCoconut(BunchOfCoconuts coconuts) {
		// Pointers
		int begin, end, mid;
		
		// Set begin and end
		begin = 0;
		end = coconuts.getNumCoconuts() - 1;
		
		// Loop invariant
		while (begin < end) {
			// Lists to compare coconuts with
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			ArrayList<Integer> list2 = new ArrayList<Integer>();
			
			if ((end-begin+1) % 2 == 0) {
				// even case
				mid = (end+begin) / 2;
			} else {
				// odd case, remove the last element
				end -= 1;
				mid = (end+begin) / 2;
			}
			
			// Reset listIndex to begin
			int listIndex = begin;
			while (listIndex <= end) {
				if (listIndex <= mid) {
					// First half of the coconuts go into list1
					list1.add(listIndex);
					listIndex++;
				} else {
					// Second half of coconuts to list 2
					list2.add(listIndex);
					listIndex++;
				}
			}

			// Balance the two list
			int result = coconuts.balance(list1, list2);
			

			if (result == -1) {
				// If left side is lighter, search the left side now
				end = mid;
			} else if (result == 1) {
				// If right side is lighter, search the left side now
				begin = mid+1;
			} else {
				// If 0 is returned, means the lightest coconut is the last coconut that we left
				// out for the odd case
				return end+1;
			}
		}
		
		return begin;
	}

	public static void main(String[] args) {
		BunchOfCoconuts coconuts = new BunchOfCoconuts(99999, 58684);
		System.out.println(findLightCoconut(coconuts));
	}
}
