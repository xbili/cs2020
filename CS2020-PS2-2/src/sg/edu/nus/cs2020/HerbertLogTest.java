package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * HerbertLogTest
 * 
 * Description: A set of tests to ensure that the correct salary is calculated
 * and the minimum number of minutes for selected goalIncomes are correct
 *
 * @author Goh Chun Teck
 */
public class HerbertLogTest {

	@Test
	/**
	 * Verify the correct salary calculated in veryShortNames
	 */
	public void testVeryShortNamesTotalSalary() {
		HerbertLog log = new HerbertLog("veryShortNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("veryShortNamesTotalSalary", 52, salary);
	}
	
	@Test
	/**
	 * Verify the correct salary calculated in shortNames
	 */
	public void testShortNamesTotalSalary() {
		HerbertLog log = new HerbertLog("shortNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("shortNamesTotalSalary", 401, salary);
	}
	
	@Test
	/**
	 * Verify the correct salary calculated in vmanyNames
	 */
	public void testManyNamesTotalSalary() {
		HerbertLog log = new HerbertLog("manyNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("manyNamesTotalSalary", 47803, salary);
	}
	
	@Test
	/**
	 * Verify the correct salary calculated in longNames
	 */
	public void testLongNamesTotalSalary() {
		HerbertLog log = new HerbertLog("longNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("longNamesTotalSalary", 2796254, salary);
	}

}
