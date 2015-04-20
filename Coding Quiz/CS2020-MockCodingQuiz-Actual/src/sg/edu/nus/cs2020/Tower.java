package sg.edu.nus.cs2020;


import java.util.*;

public class Tower implements IBody {
	public static final double MASS = 1;
	public static final double LENGTH = 1;

	public IBody top;
	public IBody bottom;
	public double distance;

	public Tower(IBody a, IBody b, double d) {
		if(a == null || b == null || d < 0 || d > a.getLength()) System.out.println("Tower not valid!");
		else{
			top = b;
			bottom = a;
			distance = d;
		}
	}

	public Iterator<Block> iterator() {
		return new TowerIterator(top.iterator(), bottom.iterator());
	}

	public double getCenterOfMass() {
		return ((bottom.getCenterOfMass() * bottom.getMass()) + (top
				.getCenterOfMass() + distance) * top.getMass())
				/ this.getMass();
	}

	public double getLength() {
		return distance + top.getLength();
	}

	public double getMass() {
		return top.getMass() + bottom.getMass();
	}

	static public IBody buildTower(int numBlocks) {
		IBody[] towers = new IBody[numBlocks];
		towers[0] = new Block(1,1);
		for(int i = 1; i < numBlocks; i++){
			towers[i] = new Tower(new Block(1,1), towers[i-1], 1 - towers[i-1].getCenterOfMass());
			//System.out.println(towers[i].getLength());
		}
		return towers[numBlocks-1];
	}

	public static void main(String[] args) {
		IBody tower = buildTower(1000);
		System.out.println("Length: " + tower.getLength());
		System.out.println("COM: " + tower.getCenterOfMass());

		for (Block b : tower) {
			System.out.println("Another block. Length = " + b.getLength()
					+ ", COM = " + b.getCenterOfMass());
		}
	}
	
	public class TowerIterator implements Iterator<Block>{
		
		Iterator<Block> bottomIterator;
		Iterator<Block> topIterator;
		
		TowerIterator(Iterator<Block> top, Iterator<Block> bottom){
			bottomIterator = bottom;
			topIterator = top;
		}
		
		public boolean hasNext() {
			return bottomIterator.hasNext() || topIterator.hasNext();
		}

		@Override
		public Block next() {
			if(bottomIterator.hasNext()) return bottomIterator.next();
			else return topIterator.next();
		}

		public void remove() {
		}
	}
}
