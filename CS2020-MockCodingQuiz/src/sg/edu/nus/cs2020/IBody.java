package sg.edu.nus.cs2020;
/**
 * Interface IBody
 * Describes a physical body.  A body has a mass, a length, and a
 * center of mass.
 */
public interface IBody extends Iterable<Block>{

	// Returns the center of mass of a body
	double getCenterOfMass();
	
	// Returns the length of a boday
	double getLength();
	
	// Returns the total mass of a body
	double getMass();

	
}
