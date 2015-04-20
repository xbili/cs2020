package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

public class TSPGraph implements IApproximateTSP {
	private TSPMap m_map;
	private HashMap<Integer, ArrayList<Integer>> mstAdjList;
	private ArrayList<Integer> walk;
	private HashMap<Integer, Integer> m_parent;
	private ArrayList<Integer> tspTour;
	
	/**
	 * Inititialization function for graph.
	 * 
	 * Saves map as a private variable.
	 * Intitalize required data structures.
	 * Initialize MST Adjacency List for use later.
	 * 
	 * @param map
	 */
	@Override
	public void initialize(TSPMap map) {
		m_map = map;
		mstAdjList = new HashMap<Integer, ArrayList<Integer>>();
		walk = new ArrayList<Integer>();
		m_parent = new HashMap<Integer, Integer>();
		for (int i=0; i < m_map.getCount(); i++) {
			mstAdjList.put(i, new ArrayList<Integer>());
		}
	}

	/**
	 * Generates a MST with Prim's algorithm.
	 * 
	 * Priority queue implemented with a Hash Table.
	 * 
	 * We save the MST as an adjacency list and update the graph from there
	 * 
	 */
	@Override
	public void MST() {
		// Initialize Priority Queue
		PriorityQueue pq = new PriorityQueue();
		for (int i=0; i < m_map.getCount(); i++) {
			pq.insert(i, Double.MAX_VALUE);
		}
		
		// Arbitrary start node
		int start = 0;
		pq.decreaseKey(start, 0.0);
		
		// Initialize MST set
		HashSet<Integer> mst = new HashSet<Integer>();
		mst.add(start);
		
		// Initialize parent table
		HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
		parent.put(start, null);
		
		/**
		 * Prim's algorithm main code
		 */
		while (!pq.isEmpty()) {
			int v = pq.deleteMin();
			mst.add(v);
			for (int w=0; w < m_map.getCount(); w++) {
				if (w != v) {
					updatePriority(pq, mst, parent, v, w);
				}
			}
		}
		parent.remove(start);
		
		for (Entry<Integer, Integer> e : parent.entrySet()) {
			// Draw the links
			m_map.setLink(e.getKey(), e.getValue());
			
			saveMstInAdjList(e);
		}
		
		// To make erasing links for TSP later easier, save the parent HashMap
		m_parent = parent;
	}

	private void saveMstInAdjList(Entry<Integer, Integer> e) {
		ArrayList<Integer> updatedList = mstAdjList.get(e.getKey());
		updatedList.add(e.getValue());
		mstAdjList.put(e.getKey(), updatedList);
	}

	private void updatePriority(PriorityQueue pq, HashSet<Integer> mst,
			HashMap<Integer, Integer> parent, int v, int w) {
		if (!mst.contains(w)) {
			if(pq.getPriority(w) > m_map.pointDistance(v, w)){
				pq.decreaseKey(w, m_map.pointDistance(v, w));
				parent.put(w, v);
			}
		}
	}
	
	/**
	 * Perform a DFS walk of the tree T. Remember every time we visit a node.
	 * Every node appears at least twice in the DFS walk
	 */
	@Override
	public void TSP() {
		dfsWalkMST();
		LinkedHashSet<Integer> tspWalk = removeDuplicates();
		resetGraphLinks();
		drawTSPWalk(tspWalk);
	}
	
	/**
	 * Using an iterator for the TSP Walk ArrayList, we connect every edge together with
	 * graph.setLink method.
	 * 
	 * @param tspWalk
	 */
	private void drawTSPWalk(LinkedHashSet<Integer> tspWalk) {
		Iterator<Integer> tspIter = tspWalk.iterator();
		int curr = tspIter.next();
		while (tspIter.hasNext()) {
			int next = tspIter.next();
			m_map.setLink(curr, next);
			curr = next;
		}
		m_map.setLink(curr, 0);
	}
	
	/**
	 * Erases graph links drawn by MST() method, in order to make way for TSP graph links.
	 */
	private void resetGraphLinks() {
		for (int i=0; i < m_map.getCount(); i++) {
			m_map.eraseLink(i, false);
		}
	}

	/**
	 * Stores the ArrayList of nodes in DFS into an ordered set.
	 * This ensures that no duplicates will be present.
	 * 
	 * @return
	 */
	private LinkedHashSet<Integer> removeDuplicates() {
		LinkedHashSet<Integer> tspWalk = new LinkedHashSet<Integer>();
		tspWalk.addAll(walk);
		return tspWalk;
	}

	/**
	 * DFS walks the MST. 
	 * 
	 * Taken from lecture's pseduocode.
	 */
	private void dfsWalkMST() {
		boolean visited[] = new boolean[m_map.getCount()];
		Arrays.fill(visited, false);
		
		for (int i = 0; i < mstAdjList.size(); i++) {
			if (!visited[i]) {
				visited[i] = true;
				dfsVisit(mstAdjList, visited, i);
			}
		}
	}
	
	/**
	 * Helper function for DFS, taken from lecture's pseduocode.
	 * 
	 * @param adjList
	 * @param visited
	 * @param index
	 */
	private void dfsVisit(HashMap<Integer, ArrayList<Integer>> adjList, boolean[] visited, int index) {
		walk.add(index);
		for (int v : adjList.get(index)) {
			if (!visited[v]) {
				visited[v] = true;
				dfsVisit(adjList, visited, v);
				walk.add(index);
			}
		}
	}

	/**
	 * To check if the current tspTour is a valid tour, we simply check if number of nodes in the tour
	 * is equal to the total number of nodes + 1.
	 */
	@Override
	public boolean isValidTour() {
		int curr = m_map.getLink(0);
		ArrayList<Integer> tour = new ArrayList<Integer>();
		tour.add(0);
		
		while(curr != 0){
			tour.add(curr);
			curr = m_map.getLink(curr);
		}
		
		// Add in the last edge as well.
		tour.add(curr);
		
		tspTour = tour;
		return (tour.size() == (m_map.getCount() + 1));
	}

	/**
	 * We simply calculate the total distance between all of the nodes in the TSP tour.
	 */
	@Override
	public double tourDistance() {
		if (isValidTour()) {
			double result = 0;
			for (int i=1; i < tspTour.size(); i++) {
				result += m_map.pointDistance(tspTour.get(i-1), tspTour.get(i));
			}
			result += m_map.pointDistance(tspTour.get(0), tspTour.get(tspTour.size()-1));
			return result;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		// Open a new map
        TSPMap map = new TSPMap("hundredpoints.txt");
        TSPGraph graph = new TSPGraph();
        graph.initialize(map);
        graph.MST();
        graph.TSP();
        System.out.println(graph.isValidTour());
        System.out.println(graph.tourDistance());
	}
}
