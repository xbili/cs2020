package sg.edu.nus.cs2020;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Priority queue to be used with Prim's algorithm.
 * 
 * Hash table with:
 * 	Key - Node
 * 	Value - Estimate
 * 
 * @author xbili
 *
 */
public class PriorityQueue {
	private HashMap<Integer, Double> pq;
	
	public PriorityQueue() {
		pq = new HashMap<Integer, Double>();
	}
	
	public void insert(int v, double priority) {
		pq.put(v, priority);
	}
	
	public void decreaseKey(int v, double priority) {
		if (pq.containsKey(v) && pq.get(v) > priority) {
			pq.put(v, priority);
		}
	}
	
	public boolean isEmpty() {
		return pq.isEmpty();
	}
	
	public double getPriority(int v) {
		return pq.get(v);
	}
	
	public int deleteMin() {
		// Index of item with lowest priority
		Integer lowestIndex = null;
		
		// Least priority so far
		Double leastPriority = Double.POSITIVE_INFINITY;
		
		for (Entry<Integer, Double> e : pq.entrySet()) {
			if (e.getValue() < leastPriority) {
				lowestIndex = e.getKey();
				leastPriority = e.getValue();
			}
		}
		
		pq.remove(lowestIndex);
		return lowestIndex;
	}
}
