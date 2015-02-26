package sg.edu.nus.cs2020;

import java.util.ArrayList;

/**
 * QuestionTree
 * @author gilbert
 * The main class for maintaining the player's state in the 
 * 20 Questions Game.  The QuestionTree stores the possible
 * objects in a tree indexed by their properties.  
 * 
 * Every internal node in the tree is a TreeNode and has a
 * property as its value.  Every leaf node is a LeafNode and
 * has an object name as its value.  Be sure to use the LeafNode 
 * class for objects to ensure that properties and objects do not
 * get confused.
 * 
 * As the game proceeds, parts of the tree are eliminated, allowing the
 * player to eventually discover which is the chosen item.
 */
public abstract class QuestionTreeBase {
	
	/***********************
	 * State stored by the Question tree
	 */
	
	// The root of the tree
	protected TreeNode<String> m_root = null;
		
	/************************
	 * Constructor
	 */
	
	/**
	 * Constructor
	 * Builds an empty tree.
	 */
	protected QuestionTreeBase(){
		m_root = null;
	}
	
	/**************************
	 * Functionality for building the tree
	 * Implement this method for Problem 1.a
	 */
	
	/**
	 * buildTree
	 * @param objects an array of QuestionObjects
	 * Builds the tree of objects.
	 */
	public abstract void buildTree(ArrayList<QuestionObject> objects);
	
	
	
	/********************************
	 * Choosing which query to ask next
	 * Implement this functionality in part 1.c.
	 */
	
	/**
	 * findQuery
	 * @return the next query
	 * This method calculates the next question to ask in the game.
	 * It finds a node in the tree that has at least n/3 objects as descendants, and
	 * at most 2n/3 objects as descendants, where n is the total number of objects in
	 * the tree.
	 */
	public abstract Query findQuery();
	
	
	/*********************************
	 * Updating the tree after a query
	 */	
			
	/**
	 * updateTree
	 * @param query
	 * @param answer
	 */
	public void updateTree(Query query, boolean answer){
		
		// Error checking
		if (query == null){
			throw new IllegalArgumentException("No query to update");
		}
		
		// Translate the query into a node in the tree
		TreeNode<String> node = search(query);
		
		if (node == null){
			// Oops, this was not a good query.
			throw new IllegalArgumentException("Bad query.  Cannot update tree.");
		}
		
		// Get the parent of the node in question
		TreeNode<String> parent = node.getParent();
		
		// Was the query answered yes or no?
		if (answer==true){
			// Update the tree.
			// All future queries start here.  The rest of the tree is abandoned.
			m_root = node;			
		}
		else { // answer==false, hence prune the tree
			if (parent.getLeft() == node){
				parent.setLeft(null);
			}
			else if (parent.getRight() == node){
				parent.setRight(null);
			}
			else {
				// Should never get here
				throw new IllegalStateException("Bad tree state.");
			}
			
			// Now walk up the tree from the leaf to the root.
			// If this branch of the tree is now empty, then
			// delete it.
			node = parent;
			parent = node.getParent();
			
			// Walk up the tree
			while (parent != null){
				// If node has no children, delete it
				if ((node.getLeft() == null) && (node.getRight() == null)){
					if (parent.getLeft() == node){
						parent.setLeft(null);
					}
					else if (parent.getRight() == node){
						parent.setRight(null);
					}
					else{
						// Should never get here
						assert(false);
					}
				}
				else {
					// In this case, we are done
					break;
				}
				node = parent;
				parent = node.getParent();
			
			}
		}
	}

	/**************************
	 * Functionality for translating between nodes in the
	 * tree and queries
	 */
	
	/**
	 * constructQuery
	 * @param node
	 * @return query associated with the node in the tree
	 * This method walks up the tree, adding each property to
	 * the query as either a positive or negative property (depending
	 * on whether it is a left or right child).
	 * The resulting query exactly captures the properties needed to
	 * walk from the (current) root of the tree to the node in question.
	 * 
	 * The returned query does not include the property of the specified node.
	 */
	protected Query constructQuery(TreeNode<String> node){
		// Error checking
		if (node == null){
			throw new IllegalArgumentException("Cannot construct a query based on a null node.");
		}
		
		// Initialize the nodes for walking up the tree
		TreeNode<String> leaf = node;
		TreeNode<String> parent = leaf.getParent();
		
		// Create a new query
		Query query = new Query();
		
		// Now walk up the tree...
		while (parent != null){
			// Get the property of the parent node
			String property = parent.getValue();
			
			// Add it to the query as a positive/negative property,
			// depending on whether it is a left or right child.
			if (leaf == parent.getLeft()){
				query.addNotProperty(property);
			}
			else if (leaf == parent.getRight()){
				query.addProperty(property);
			}
			else{
				throw new IllegalStateException("Invalid tree state.");
			}
			
			// Progress up the tree
			leaf = parent;
			parent = leaf.getParent();

		}
		
		return query;
	}
	
	/**
	 * search
	 * @param query
	 * @return node associated with the query
	 * 
	 * This method return the node in the tree that satisfies the query.
	 * If the query is true, then the chosen item is in the subtree of the
	 * returned node.  If the query is false, then the chosen item is not in the
	 * subtree of the returned node.
	 * 
	 * The search stops as soon as it finds a node that is not represented in the query.
	 * It does not guarantee that every property or non-property in the query is traversed.
	 * The search performs a treewalk only as far as it can, as specified by the query.
	 */
	private TreeNode<String> search(Query query){
		return search(query, m_root);
	}
	
	/**
	 * search
	 * @param query
	 * @param node
	 * @return
	 * Searches for a node associated with a given query.
	 * 
	 * As soon as it finds a node that is not represented in the
	 * query, it stops.  It does not guarantee that every property or
	 * non-property in the query is traversed.
	 * The search performs a treewalk only as far as it can, as specified by the query.
	 */
	private TreeNode<String> search(Query query, TreeNode<String> node){
		// Error checking
		if ((query == null) || (node == null)){
			throw new IllegalArgumentException("Query or node is null.  Cannot search for a null query, or from a null node.");
		}
		
		// If we are at a leaf, then we are done
		if (node.isLeaf()) {
			return node;
		}
		
		// Get the property of the current node
		String s = node.getValue();
		
		// Check if the property is part of the query as either a positive or negative property.
		boolean satisfied = query.containsProperty(s);
		boolean unsatisfied = query.containsNotProperty(s);
		// If it is positive, then recurse right.
		if (satisfied){
			if (node.getRight() != null){
				return search(query, node.getRight());
			}
			else{
				// If we can't go right, then stop here and return this node.
				return node;
			}
		}
		// If it is negative, then recurse left.
		else if (unsatisfied){
			if (node.getLeft() != null){
				return search(query, node.getLeft());
			}
			else{
				// If we can't go left, then stop here and return this node.
				return node;
			}
		}
		// The query no longer specifies what to do.
		// This is as far as we can walk with this query.
		return node;
	}
	
	
	/**********************************
	 * Helper functionality
	 */

	/**
	 * countObjects
	 * @return
	 * Counts the number of objects in the tree
	 */
	public int countObjects(){
		return countObjects(m_root);
	}
	
	/**
	 * countObjects
	 * @param node
	 * @return
	 * Counts the number of objects in a subtree
	 */
	protected int countObjects(TreeNode<String> node){
		// A null node has no objects.
		if (node == null){
			return 0;
		}
		// A leaf has exactly one object.
		if (node.isLeaf()){
			return 1;
		}
		// Recursively count the two children.  
		return (countObjects(node.getLeft()) + countObjects(node.getRight()));
	}
	
	/**
	 * getOneObject
	 * @return any one object in the tree
	 * This method returns some object from the tree.  
	 */
	public String getOneObject(){
		return getOneObject(m_root).getValue();
	}
	
	/**
	 * getOneObject
	 * @param node
	 * @return any one object from the tree
	 * This performs a recursive treewalk until we find some object to return.
	 */
	protected TreeNode<String> getOneObject(TreeNode<String> node){
		if (node == null){
			throw new IllegalArgumentException("Cannot get an object from a null node.");
		}
		
		// If we are at a leaf, then we have found an object.  Return it.
		if (node.isLeaf()){
			return node; 
		}
		// Otherwise, if we have a right child, then recurse right.
		else if (node.getRight() != null){
			return getOneObject(node.getRight());
		}
		// Otherwise, if we have a left child, then recurse left.
		else if (node.getLeft() != null){
			return getOneObject(node.getLeft());
		}
		// Otherwise, we have no children.  That's not allowed!
		// Every leaf in the tree has to be an object.
		else throw new IllegalStateException("Illegal tree state: no children.");
	}
	
	/**
	 * printTree
	 * Prints all the objects in the tree, specifying the root-to-leaf path of each object.
	 */
	public void printTree(){
		printTree(m_root);
	}

	/**
	 * printTree
	 * @param node
	 * Prints all the objects in the subtree, specifying the root-to-leaf path of each object.
	 */
	private void printTree(TreeNode<String> node){
		if (node == null){
			return;
		}
		if (node.isLeaf()){
			printLeaf(node);
		}
		else{
			printTree(node.getLeft());
			printTree(node.getRight());
		}
	}
	
	/**
	 * printLeaf
	 * @param leaf
	 * Prints an object.  
	 * Iterates up the tree, printing each node on the root-to-leaf path.
	 */
	private void printLeaf(TreeNode<String> leaf){
		if (leaf == null){
			throw new IllegalArgumentException("Invalid leaf.");
		}
		
		// Get the parent.
		TreeNode<String> parent = leaf.getParent();
		
		// Print out the object name
		System.out.print(leaf.getValue() + ": ");
		
		// Print out the root-to-leaf path to the parent
		if (parent != null){
			if (parent.getLeft() == leaf){
				printNode(parent, false);
			}
			else{
				printNode(parent, true);
			}
		}
		System.out.print('\n');
	}
	
	/**
	 * printNode
	 * @param node
	 * @param direction 
	 * Prints the root-to-leaf path up to this node.
	 * The direction specifies whether the property is a positive or negative property
	 * for the path in question.
	 */
	private void printNode(TreeNode<String> node, boolean direction)
	{
		// Error checking
		if (node == null){
			throw new IllegalArgumentException("Invalid leaf.");
		}
		
		// Get the parent
		TreeNode<String> parent = node.getParent();
		if (parent != null){
			// Recursively print out the root-to-leaf path to the parent.
			// Set direction true/false based on whether the parent is a left
			// or right child.
			if (parent.getLeft() == node){
				printNode(parent, false);
			}
			else{
				printNode(parent, true);
			}
		}
		// Now print out this node.
		// If the direction is false, then add a negative sign.
		if (direction == true){
			System.out.print(" " + node.getValue());
		}
		else{
			System.out.print(" " + '-' + node.getValue());
		}
	}
	
}
