package cs2020;

import java.util.ArrayList;
import java.util.Collections;

public class Highway implements IHighway {
	private int m_end;
	private int m_begin;
	private int m_length;

	// Each entry represents the location of the customers
	private ArrayList<Integer> m_customers;

	public Highway(int begin, int end) {
		m_end = end;
		m_begin = begin;
		m_length = end - begin;
		m_customers = new ArrayList<Integer>();
	}

	@Override
	public void addCustomer(int location) throws IllegalArgumentException {
		if (location > m_end || location < m_begin) {
			throw new IllegalArgumentException(
					"Your location has exceeded the highway.");
		}

		m_customers.add(location);
	}

	@Override
	public int numberOfStations(int range) throws IllegalArgumentException {
		if (m_customers.isEmpty()) {
			return 0;
		} else if (m_length == 0) {
			return m_begin;
		} else if (range == 0) {
			return m_customers.size();
		}

		int numStation = 0;
		int currentStationLocation = 0;

		// We first sort the ArrayList - O(nlogn)
		Collections.sort(m_customers);

		int start = 0;
		int end = m_customers.size() - 1;

		while (start < end) {
			// The best place to place the first station is the location of
			// first person + range
			// Place first tower at first location + range
			currentStationLocation = m_customers.get(start) + range;

			// If there are only two locations in the ArrayList and they cannot
			// be contained in the same range, we need two stations
			if (end - start == 1
					&& (m_customers.get(end) > currentStationLocation + range)) {
				numStation += 2;
				break;
			}

			int originalStart = start;

			// Move start to the first location that is out of range
			for (int i = 0; i < m_customers.size(); i++) {
				if (m_customers.get(i) > currentStationLocation + range) {
					start = i;
					break;
				}
			}

			// If start did not change, means we have went through all of the
			// locations
			// This will end the loop
			if (start == originalStart) {
				start = end;
			}

			numStation++;
		}

		return numStation;
	}

	@Override
	public int findSmallestRange(int numStations)
			throws IllegalArgumentException {

		if (numStations < 0) {
			throw new IllegalArgumentException(
					"Number of stations should be positive");
		}

		if (m_customers.isEmpty()) {
			return 0;
		}

		Collections.sort(m_customers);

		// If there is only one station, the smallest range needed is the
		// distance from the first customer to the last customer
		if (numStations == 1) {
			return (m_customers.get(m_customers.size() - 1) - m_customers
					.get(0)) / 2;
		}

		// Binary search for solution
		int low = 0;
		int high = m_customers.get(m_customers.size() - 1) - m_customers.get(0)
				/ 2;
		int middle;

		int result = 0;

		while (low < high) {
			middle = (low + high) / 2;

			int currStations = numberOfStations(middle);
			System.out.println(currStations);

			if (numberOfStations(middle) < numStations) {
				low = middle + 1;
			} else if (numberOfStations(middle) > numStations) {
				high = middle;
			} else {
				result = middle;
			}
		}

		return result;
	}


}
