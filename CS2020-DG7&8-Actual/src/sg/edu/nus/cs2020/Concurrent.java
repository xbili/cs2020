package sg.edu.nus.cs2020;

import java.util.*;

class Concurrent {
	public static void main(String[] args) {
		ArrayList<String> test = new ArrayList<>();
		test.add("test");
		test.add("test");
		for(String x : test){
			test.add(x);
		}	
	}
}