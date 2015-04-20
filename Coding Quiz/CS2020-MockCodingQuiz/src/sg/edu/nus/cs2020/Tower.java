package sg.edu.nus.cs2020;

import java.util.*;

public class Tower implements IBody {
	public static final double MASS = 1;
	public static final double LENGTH = 1;

	private IBody bodyA, bodyB;
	private double dist, totalMass;
	
	public Tower(IBody a, IBody b, double d) {
		bodyA = a;
		bodyB = b;
		dist = d;
		totalMass = bodyA.getMass() + bodyB.getMass();
	}

	public double getCenterOfMass() {
		double result = (bodyA.getCenterOfMass() * bodyA.getMass() + (dist + bodyB.getCenterOfMass()) * bodyB.getMass()) / totalMass; 
		
		return result;
	}
	
	public double getLength() {
		double result = bodyA.getLength() + bodyB.getLength();
		
		return result;
	}
	
	/*
	public static IBody buildTower(int numBlocks) {
		IBody currentTower;
		
		if (numBlocks == 1) {
			return new Block(1, 1);
		}
		
		while (numBlocks > 0) {
			if (numBlocks == 1) {
				
			}
		}
	}
	*/
	
	@Override
	public Iterator<Block> iterator() {
		return new TowerIterator<Block>();
	}
	
	class TowerIterator<Block> implements Iterator<Block> {
		
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Block next() {
			return null;
		}
		
	}

	@Override
	public double getMass() {
		return totalMass;
	}
	
	public static void main(String[] args) {
		/*
		IBody tower = buildTower(1000);
		System.out.println("Length: " + tower.getLength());
		System.out.println("COM: " + tower.getCenterOfMass());

		for (Block b : tower) {
			System.out.println("Another block. Length = " + b.getLength()
					+ ", COM = " + b.getCenterOfMass());
		}
		*/
		
		Block a = new Block(1, 1);
		Block b = new Block(1, 1);
		Block c = new Block(1, 1);
		Tower base = new Tower(a, b, 0.25);
		Tower entire = new Tower(base, c, 0.75);
		System.out.println(entire.getLength()); // Prints 1.75
		System.out.println(entire.getCenterOfMass()); // Prints 0.8333333333333334

	}


}
