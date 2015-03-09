package sg.edu.nus.cs2020;

import java.util.Iterator;

/**
 * class Block
 * Represents a simple block with size and mass
 */
public class Block implements IBody{
	
	private double mass;
	private double size;
	
	/**
	 * Constructor for a block
	 * @param m
	 * @param s
	 */
	Block(double m, double s){
		mass = m;
		size = s;
	}

	/**
	 * getCenterOfMass
	 * Returns the center of mass of a block.
	 */
	@Override
	public double getCenterOfMass() {
		return (size/2);
	}

	/**
	 * getLength
	 * Returns the length of the block.
	 */
	@Override
	public double getLength() {
		return size;
	}

	/**
	 * getMass
	 * Returns the mass of the block.
	 */
	@Override
	public double getMass() {
		return mass;
	}

	/**
	 * Iterator
	 * returns an iterator for this block.
	 */
	@Override
	public Iterator<Block> iterator() {
		return new BlockIterator(this);
	}
	
	/**
	 * class BlockIterator
	 * Iterates over the single block in this tower.
	 */
	class BlockIterator implements Iterator<Block>{
		
		// Has this iterator been queried yet?
		boolean queried = false;
		
		// The one and only block in my tower
		Block myBlock;

		/**
		 * Constructor
		 * @param t
		 */
		BlockIterator(Block t){
			myBlock = t;
		}
		
		/**
		 * Returns true if the iterator has not yet been queried.
		 */
		@Override
		public boolean hasNext() {
			return (!queried);
		}

		/**
		 * Returns the block, if this is the first time the iterator is queried.
		 */
		@Override
		public Block next() {
			if (!queried) {
				queried = true;
				return myBlock;
			}
			else return null;
		}

		/**
		 * Remove not supported.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not implemented.");
		}
		
	}

}
