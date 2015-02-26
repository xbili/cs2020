package sg.edu.nus.cs2020;

import java.util.Iterator;

/**
 * Query class
 * @author gilbert
 * This class encapsulats a query.
 * A query consists of two types of properties: those that are required to be present, and 
 * those that are required to be absent.  For example, if object 1 has properties A and C,
 * then it will satisfy queries "A", "A,C", and "A, -B" (where -B indicates that B is absent).
 * The properties will always be stored and returned in sorted order.
 */
public class Query {

		// Properties that are required 
		private java.util.TreeSet<String> m_properties = new java.util.TreeSet<String>();
		
		// Properties that must be absent
		private java.util.TreeSet<String> m_notProperties = new java.util.TreeSet<String>();

		/**
		 * Constructor
		 * Builds an empty query.
		 */
		public Query(){
		}
		
		/**
		 * Constructor
		 * @param props
		 * @param notProps
		 */
		public Query(java.util.TreeSet<String> props, java.util.TreeSet<String> notProps){
			m_properties = props;
			m_notProperties = notProps;
		}
		
		/**
		 * addProperty
		 * Adds a property to the query
		 * @param prop
		 */
		public void addProperty(String prop){
			// Error checking
			if ((prop == null) || (prop == "")){
				throw new IllegalArgumentException("Bad property name");
			}
			if (m_notProperties.contains(prop)){
				throw new IllegalArgumentException("Cannot add property: already exists as negative property.");
			}
			m_properties.add(prop);
		}
		
		/**
		 * containsProperty
		 * Returns whether the query contains a specific property
		 * @param s
		 * @return
		 */
		public boolean containsProperty(String s){
			return m_properties.contains(s);
		}
		
		/**
		 * propertyIterator
		 * Returns an iterator for the properties
		 */
		public Iterator<String> propertyIterator(){
			return m_properties.iterator();
		}
		
		/**
		 * numProperties
		 * The number of properties that are in the query
		 * @return
		 */
		public int numProperties(){
			return m_properties.size();
		}
		
		/**
		 * addNotProperty
		 * Adds a negative property to the query
		 * @param notProp
		 */
		public void addNotProperty(String notProp){
			// Error checking
			if ((notProp == null) || (notProp == "")){
				throw new IllegalArgumentException("Bad property name");
			}
			if (m_properties.contains(notProp)){
				throw new IllegalArgumentException("Cannot add property: already exists as negative property.");
			}
			m_notProperties.add(notProp);
		}
		
		/**
		 * containsNotProperty
		 * Returns whether the query contains a specific negative property
		 * @param i
		 * @return
		 */
		public boolean containsNotProperty(String s){
			return m_notProperties.contains(s);
		}
		
		/**
		 * notPropertyIterator
		 * Returns an iterator for the negative properties
		 */
		public Iterator<String> notPropertyIterator(){
			return m_notProperties.iterator();
		}

		/** 
		 * numNotProperties
		 * Returns the number of negative properties
		 * @return
		 */
		public int numNotProperties(){
			return m_notProperties.size();
		}
		
		/**
		 * toString
		 * Returns the query as a string
		 * Merges the property and negative property lists together, 
		 * maintaining a sorted order and negating the properties that
		 * must be absent.  
		 * Most of the code here is for merging the property lists.
		 */
		@Override
		public String toString(){
			String output = "Query: ";
			
			int count = m_properties.size() + m_notProperties.size();
			
			Iterator<String> props = m_properties.iterator();
			Iterator<String> notProps = m_notProperties.iterator();
			
			String p = null;
			String np = null;
			String next = null;
			
			if (props.hasNext()){
				p = props.next();
			}
			if (notProps.hasNext()){
				np = notProps.next();
			}
			
			for (int i=0; i<count; i++){
				if ((p == null) && (np == null)){
					// This shouldn't happen.
					continue;
				}
				else if (p == null){
					next = '-' + np;
					if (notProps.hasNext()){
						np = notProps.next();
					}
					else {
						np = null;
					}
				}
				else if (np == null){
					next = p;
					if (props.hasNext()){
						p = props.next();
					}
					else {
						p = null;
					}
				}
				else { // Both are available
					int compare = (p.compareTo(np));
					if (compare < 0){ // p comes first
						next = p;
						if (props.hasNext()){
							p = props.next();
						}
						else {
							p = null;
						}
					}
					else if (compare > 0) { // np comes first
						next = '-' + np;
						if (notProps.hasNext()){
							np = notProps.next();
						}
						else {
							np = null;
						}
					}
					else { // should never get here
						throw new IllegalStateException("Bad query: some property has been added as both negative and positive.");
					}
				}
				output += next;
				if (i < count-1) output += ",";
			}
			
			return output;
		}
		
		
		
	
}
