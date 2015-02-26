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
public class HerbertLogBonusTest {

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

	
	@Test
	public void testVeryShortNamesGoalIncome() {
		HerbertLog log = new HerbertLog("veryShortNamesHerbert.txt");
		
		int numMinutes = log.calculateMinimumWork(52);
		assertEquals("veryShortNamesGoalIncome - $52", 2, numMinutes);
		
		numMinutes = log.calculateMinimumWork(30);
		assertEquals("veryShortNamesGoalIncome - $30", 1, numMinutes);
		
		numMinutes = log.calculateMinimumWork(53);
		assertEquals("veryShortNamesGoalIncome - $53", -1, numMinutes);
	}
	
	@Test
	public void testShortNamesGoalIncome() {
		HerbertLog log = new HerbertLog("shortNamesHerbert.txt");
		
		int numMinutes = log.calculateMinimumWork(401);
		assertEquals("shortNamesGoalIncome - $401", 5, numMinutes);
		
		numMinutes = log.calculateMinimumWork(279);
		assertEquals("shortNamesGoalIncome - $279", 2, numMinutes);
		
		numMinutes = log.calculateMinimumWork(280);
		assertEquals("shortNamesGoalIncome - $280", 3, numMinutes);
		
		numMinutes = log.calculateMinimumWork(402);
		assertEquals("shortNamesGoalIncome - $402", -1, numMinutes);
	}
	
	@Test
	public void testManyNamesGoalIncome() {
		HerbertLog log = new HerbertLog("manyNamesHerbert.txt");
		
		int numMinutes = log.calculateMinimumWork(47803);
		assertEquals("manyNamesGoalIncome - $47803", 20, numMinutes);
		
		numMinutes = log.calculateMinimumWork(13320);
		assertEquals("manyNamesGoalIncome - $13320", 3, numMinutes);
		
		numMinutes = log.calculateMinimumWork(25567);
		assertEquals("manyNamesGoalIncome - $25567", 6, numMinutes);
		
		numMinutes = log.calculateMinimumWork(47804);
		assertEquals("manyNamesGoalIncome - $47804", -1, numMinutes);
	}
	
	@Test
	public void testLongNamesGoalIncome() {
		HerbertLog log = new HerbertLog("longNamesHerbert.txt");
		
		int numMinutes = log.calculateMinimumWork(2796254);
		assertEquals("longNamesGoalIncome - $2796254", 42399, numMinutes);
		
		numMinutes = log.calculateMinimumWork(1);
		assertEquals("longNamesGoalIncome - $1", 1, numMinutes);
		
		numMinutes = log.calculateMinimumWork(1937821);
		assertEquals("longNamesGoalIncome - $2937821", 17698, numMinutes);
		
		numMinutes = log.calculateMinimumWork(2796255);
		assertEquals("longNamesGoalIncome - $2796255", -1, numMinutes);
	}
}
