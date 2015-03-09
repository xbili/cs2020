package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

public class QuestionTree extends QuestionTreeBase {

	/**
	 * Builds a new tree with properties as nodes and objects as leaves. We define a branch as a tree with nodes that has only right childs,
	 * similar to a list.
	 * 
	 * The algorithm for building the tree is as follows:
	 * 
	 * 1. Sort the list of objects so that the first object will have the most properties. This is to ensure that
	 * 	  when adding objects into the tree, we do not need to remove and add intermediate nodes.
	 * 
	 * 2. Keep a pointer to the current node we are at.
	 * 
	 * 3. On the first iteration, build the first branch of the tree with the first object's properties.
	 * 
	 * 4. For each remaining object in the list:
	 * 
	 * 		1) Create an ArrayList of all the properties of the current object
	 * 		2) Check if the value of the current node is contained in the ArrayList
	 * 			There are a total of 4 cases:
	 *			- If it is contained and the right node is NOT EMPTY:
	 *		  		Set the pointer as the right child, then
	 *		  		Remove the current property from the ArrayList
	 *		 
	 *			- If it is contained and the right node is EMPTY:
	 *		 		Create a new branch and set it as the right child
	 *		  
	 *			- If it is NOT contained and the left node is NOT EMPTY:
	 *		  		Set the pointer to the left child
	 *		  
	 *			- If it is NOT contained and the left node is EMPTY:
	 *		  		Create a new branch and set it as the left child
	 * 		3) To handle the corner case: 
	 * 			If the object has no more properties, set the last left child of the tree as the object.
	 * 
	 * 5. A binary search tree should be built by the end of the iterations of all objects.
	 * 
	 * @param objects - ArrayList of objects we want to build the tree with
	 */
	@Override
	public void buildTree(ArrayList<QuestionObject> objects) {
		// Error handling
		if (objects.isEmpty() || objects == null) System.out.println("Building an empty tree...");
		
		// Sort the list of objects first
		Collections.sort(objects);
		
		// Pointer to the current node
		TreeNode<String> currNode;
		
		// Iterate through the entire list of objects
		for (QuestionObject object : objects) {
			
			// Saves properties in an ArrayList
			ArrayList<String> properties = new ArrayList<String>(object.getPropCount());
			
			Iterator<String> property_iterator = object.propertyIterator();
			
			// Add all of the object's properties into the ArrayList
			while(property_iterator.hasNext()) {
				properties.add(property_iterator.next());
			}
			
			// Reset the current node pointer to the root of the tree
			currNode = m_root;

			// On the first iteration of the program
			if (currNode == null) {
				// Generates the first branch of the tree
				currNode = generateBranch(object, properties);
				
				// Updates the root as the first node of the branch generated
				m_root = currNode;
				
				// Iterate current node pointer to the last node in the branch
				currNode = lastBranchNode(currNode);
				
				// Set the object as the right leaf
				setRightLeaf(currNode, object);
				
				// Move on to the next object, ignoring the while loop below
				continue;
			}
				
			// While curr is not a leaf, so that each node can be iterated through.
			// The first branch is already created, so there should be no instances where curr is a leaf unless
			// an object is added to the branch, which will break the loop.
			while (!currNode.isLeaf()) {
				// If the object has no more properties left, means the object should be added all the way
				// at the left from the current node.
				if (properties.isEmpty()) {
					
					// Adds the leaf to the left bottom of the tree
					addToLeftBottom(currNode, object);
					
					// Break the loop
					break;
				}
				
				// If value of current node is equals to the current property
				if (object.containsProperty(currNode.getValue())) {
					
					// If the right child of the current node is empty
					if (currNode.getRight() == null) {
						
						// Add a branch to the right child of the node
						addRightBranch(currNode, object, properties);
						
					} else { // If the right child of the current node is NOT empty 
						
						// Set the current node as the right child
						currNode = currNode.getRight();
						
						// Remove the checked property
						removeCheckedProperty(currNode, properties);
					}
					
				} else { // If value of current node is NOT equals to the current property
					
					// If the left child of the current node is empty
					if (currNode.getLeft() == null) {	
						addLeftBranch(currNode, object, properties);
					} else { // If the left child of the current node is NOT empty
						
						// Set the current node as the left child
						currNode = currNode.getLeft();
					}
				}
			}
			
			// For debugging purposes
			// printTree();
			// System.out.println("--------");
		}
	}
	
	/**
	 * Adds a new branch to the RIGHT node of the child.
	 * The branch will include all of the properties that the object has.
	 * 
	 * @param node - which node to add the branch to
	 * @param object - which object to add as the leaf
	 * @param properties - ArrayList of properties of the object
	 */
	private void addRightBranch(TreeNode<String> node, QuestionObject object, ArrayList<String> properties) {
		// If the right child of the current node is empty
		
		// Create a new branch with all of the unchecked properties
		TreeNode<String> newBranch = generateBranch(object, properties);
		
		// Set the parent of branch as current node
		newBranch.setParent(node);
		
		// Set the right of current node as the branch
		node.setRight(newBranch);
		
		// Update the current node to the start of new branch
		node = node.getRight();
		
		// Iterate current node to the bottom of the tree
		node = lastBranchNode(node);
		
		// Sets the leaf at the bottom of the tree
		setRightLeaf(node, object);
		
		// To terminate the loop
		node = node.getRight();
	}
	
	/**
	 * Adds a new branch to the LEFT node of the child.
	 * The branch will include all of the properties that the object has
	 * 
	 * @param node - which node to add the branch to
	 * @param object - which object to add as the leaf
	 * @param properties - ArrayList of properties of the object
	 */
	private void addLeftBranch(TreeNode<String> node, QuestionObject object, ArrayList<String> properties) {
		// Create a branch with properties of that object
		TreeNode<String> newBranch = generateBranch(object, properties);
		
		// Set parent of branch to be current node
		newBranch.setParent(node);
		
		// Set the left of current node to be the new branch
		node.setLeft(newBranch);
		
		// Update current node to the start of the new branch
		node = node.getLeft();
		
		// Iterate the pointer to the last node in the branch
		node = lastBranchNode(node);
		
		// Sets the leaf at the bottom of the tree
		setRightLeaf(node, object);
		
		// To terminate the loop
		node = node.getRight();
	}
	
	/**
	 * Generates a branch.
	 * 
	 * A branch consist of nodes that are all linked with right edges in one long list.
	 * 
	 * @param object - object to generate the branch with
	 * @param properties - ArrayList of properties of the object
	 * @return a branch with the object as the leaf
	 */
	private TreeNode<String> generateBranch(QuestionObject object, ArrayList<String> properties) {
		// Create new iterator for object
		Iterator<String> current_iterator = properties.iterator();
		
		// Root of the branch aka first node in the branch
		TreeNode<String> b_root = new TreeNode<String>(current_iterator.next());
		
		// Current node being built
		TreeNode<String> curr = b_root;
		
		while (current_iterator.hasNext()) {
			TreeNode<String> newNode = new TreeNode<String>(current_iterator.next());
			newNode.setParent(curr);
			curr.setRight(newNode);
			curr = curr.getRight();
		}
		
		return b_root;
	}
	
	
	/**
	 * Sets the right child of the node as leaf
	 * 
	 * @param node - node to set leaf
	 * @param object - object to set as leaf
	 */
	private void setRightLeaf(TreeNode<String> node, QuestionObject object) {
		// Sets the leaf at the bottom of the tree
		LeafNode<String> newLeaf = new LeafNode<String>(object.getName(), object);
		newLeaf.setParent(node);
		node.setRight(newLeaf);
	}
	
	
	/**
	 * Sets the left child of the node as leaf
	 * 
	 * @param node - node to set leaf
	 * @param object - object to set as leaf
	 */
	private void setLeftLeaf(TreeNode<String> node, QuestionObject object) {
		// Sets the leaf at the bottom of the tree
		LeafNode<String> newLeaf = new LeafNode<String>(object.getName(), object);
		newLeaf.setParent(node);
		node.setLeft(newLeaf);
	}
	
	/**
	 * Removes the checked property from the ArrayList of properties.
	 * 
	 * @param node - current node
	 * @param properties - ArrayList of properties of object
	 */
	private void removeCheckedProperty(TreeNode<String> node, ArrayList<String> properties) {
		// Remove the checked property
		for (int i = 0; i < properties.size(); i++) {
			if (node.getValue() == properties.get(i)) {
				properties.remove(i);
			}
		}
	}
	
	/**
	 * Returns the last node of the branch
	 * 
	 * @param node - first node of the branch to iterate
	 * @return last node of the branch
	 */
	private TreeNode<String> lastBranchNode(TreeNode<String> node) {
		// Iterate current node to the bottom of the tree
		while (node.getRight() != null) {
			node = node.getRight();
		}
		
		return node;
	}
	
	/**
	 * Handles the case when we run out of properties for an object. 
	 * We will add the object as a leaf to the left bottom of the tree.
	 * 
	 * @param curr
	 * @param object
	 */
	private	void addToLeftBottom(TreeNode<String> curr, QuestionObject object) {
		while (!curr.isLeaf()) {
			if (curr.getLeft() == null) {
				// If left child of current node is empty
				
				// Set the left child of current node as object 
				setLeftLeaf(curr, object);
				curr = curr.getLeft(); // To break the loop
			} else {
				// If left child of curent node is occupied
				
				// Iterate down that node and repeat check
				curr = curr.getLeft();
			}
		}
	}
	
	/**
	 * Implementation for problem 1c.
	 * 
	 * We recursively look for the appropriate node that satisfies the condition.
	 * 
	 */
	@Override
	public Query findQuery() {
		// Pointer to the current node
		TreeNode<String> curr = m_root;
		
		// Total number of objects in the tree
		int totalCount = countObjects();
		
		// Total number of objects under the node
		int nodeCount = countObjects(curr);
		
		// Result to return
		Query result = null;
		
		// Upper and lower bound of number of objects to satisfy in order to be selected for query
		int lowerBound = totalCount / 3;
		int upperBound = (2 * totalCount) / 3;
		
		// To break the loop
		boolean found = false;

		while (!found) {
			// Counts the current number of objects under the node
			nodeCount = countObjects(curr);
			
			if (nodeCount == 1) {
				// If there is only one object under that node
				
				// We can cut the number of queries, since no matter which query we give, we'll eventually reach that object
				result = constructQuery(getOneObject(curr));
				found = true;
			}
			
			// Number of objects in right and left child
			int leftObjects = countObjects(curr.getLeft());
			int rightObjects = countObjects(curr.getRight());
			
			if (nodeCount > lowerBound && nodeCount <= upperBound) {
				// If the current node satisfies the condition
				result = constructQuery(curr);
				found = true;
			} else if (leftObjects >= rightObjects) {
				// If left child has more objects than right child
				curr = curr.getLeft();
			} else {
				// If right child has more objects than left child
				curr = curr.getRight();
			}
		}
		
		// Debug purposes
		// System.out.println("----Current Tree Status----");
		// printTree();
		// System.out.println("---------------------------");
		
		return result;
	}
	
	/**
	 * Test cases: Similiar to the example provided in the pset itself, but the tree is built differently.
	 * Binary search tree property still holds.
	 * 
	 * To check the tree build process, uncomment the code under "Debug purposes" in buildTree method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a test case of different objects
		ArrayList<QuestionObject> test_1 = new ArrayList<QuestionObject>();
		
		// Hamster
		TreeSet<String> hammy = new TreeSet<String>();
		
		
		QuestionObject hamster = new QuestionObject("Hamster", hammy);
		
		// Zorilla
		TreeSet<String> zorly = new TreeSet<String>();
		
		zorly.add("Carnivore");
		
		QuestionObject zorilla = new QuestionObject("Zorilla", zorly);
		
		// Caracal
		TreeSet<String> carcy = new TreeSet<String>();
		
		carcy.add("Feline");
		
		QuestionObject caracal = new QuestionObject("Caracal", carcy);
		
		// Elephant 
		TreeSet<String> elephy = new TreeSet<String>();
		
		elephy.add("Big");
		
		QuestionObject elephant = new QuestionObject("Elephant", elephy);
		
		// Giraffe
		TreeSet<String> girfy = new TreeSet<String>();
		
		girfy.add("Big");
		girfy.add("Yellow");
		
		QuestionObject giraffe = new QuestionObject("Giraffe", girfy);
		
		// T-Rex
		TreeSet<String> rexy = new TreeSet<String>();
		
		rexy.add("Big");
		rexy.add("Carnivore");
		
		QuestionObject trex = new QuestionObject("T-Rex", rexy);
		
		// Add them into the test list
		test_1.add(hamster);
		test_1.add(zorilla);
		test_1.add(caracal);
		test_1.add(elephant);
		test_1.add(giraffe);
		test_1.add(trex);
		
		// Finally, the test begins.
		QuestionTree test_tree = new QuestionTree();
		test_tree.buildTree(test_1);
	}
}
