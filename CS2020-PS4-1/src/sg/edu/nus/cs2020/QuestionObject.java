package sg.edu.nus.cs2020;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * QuestionObject
 * @author gilbert
 * Encapsulates an object in the 20 Questions Game.
 * Each object has a name and a list of properties.
 * Throughout we assume that properties are stored 
 * in sorted order.
 */
public class QuestionObject implements Comparable<QuestionObject> {

	// name of object
	private String m_name;
	
	// properties of object, stored in a Tree
	private TreeSet<String> m_properties;
	
	/**
	 * Constructor
	 * @param n name of object
	 * @param p properties of object
	 */
	public QuestionObject(String n, java.util.TreeSet<String> p){
		m_name = n;
		m_properties = p;
	}
	
	/**
	 * getName
	 * @return the name
	 */
	public String getName(){
		return m_name;
	}
	
	/**
	 * getPropCount
	 * @return the number of properties
	 */
	public int getPropCount(){
		return m_properties.size();
	}

	/**
	 * propertyIterator
	 * @return an iterator for the properties of this object
	 */
	public Iterator<String> propertyIterator(){
		return m_properties.iterator();
	}
	
	/**
	 * containsProperty
	 * @param s 
	 * @return Does the object contain this property s?
	 */
	boolean containsProperty(String s){
		return m_properties.contains(s);
	}
	
	/**
	 * toString
	 * Outputs object as a string: name: property1, property2, property3, ...
	 */
	public String toString(){
		String output = "Object: " + m_name;
		output += '\n';
		output += "Properties: ";
		Iterator<String> iter = m_properties.iterator();
		while (iter.hasNext()){
			String s = iter.next();
			output += s;
			if (iter.hasNext()) output += ',';
		}
		return output;
	}

	/**
	 * compareTo
	 * @param you QuestionObject to compare to
	 * We say that (x < y) if x.numProperties > y.numProperties.
	 * In this way, we sort objects with more properties first.
	 */
	@Override
	public int compareTo(QuestionObject you) {
		if (this == you) return 0;
		int myLength = getPropCount();
		int yourLength = you.getPropCount();
		if (myLength < yourLength){
			return 1;
		}
		else if (myLength > yourLength){
			return -1;
		}
		else{
			return 0;
		}
	}
}
