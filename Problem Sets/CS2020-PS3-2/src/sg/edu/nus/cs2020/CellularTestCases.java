package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellularTestCases {
	
	@Test
	public void testCase1(){
		CoverageCalculator calc = new CoverageCalculator(100);
	    calc.addTower(20, 5);
	    calc.addTower(10, 5);
	    assertEquals(20, calc.getCoverage());
	}
	
	@Test
	public void testCase2(){
		CoverageCalculator calc = new CoverageCalculator(100);
	    calc.addTower(20, 5);
	    calc.addTower(10, 5);
	    calc.addTower(30, 2);
	    calc.addTower(16, 10);
	    assertEquals(25, calc.getCoverage());
	}
	
	@Test
	public void testCase3(){
		CoverageCalculator calc = new CoverageCalculator(100);
	    calc.addTower(20, 5);
	    calc.addTower(10, 5);
	    calc.addTower(30, 2);
	    calc.addTower(16, 10);
	    calc.addTower(5, 10);
	    calc.addTower(18, 2);
	    assertEquals(30, calc.getCoverage());
	}
	
	@Test
	public void testCase4(){
		CoverageCalculator calc = new CoverageCalculator(100);
	    calc.addTower(20, 0);
	    calc.addTower(40, 0);
	    calc.addTower(60, 0);
	    assertEquals(0, calc.getCoverage());
	}
	
	@Test
	public void testCase5(){
		CoverageCalculator calc = new CoverageCalculator(100);
		try{
			calc.addTower(110, 30);
		}
		catch(Exception e){
			// Ok, something went wrong.
		}
	    assertEquals(0, calc.getCoverage());
	}
	
	
}
