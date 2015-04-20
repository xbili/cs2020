package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeNode<T extends Comparable<T>> implements Iterable<TreeNode<T>> {
	private T m_value;
	private TreeNode<T> m_left, m_right, m_parent;
	
	/**
	 * Constructor
	 * 
	 * Initializes a new TreeNode with an integer value
	 * 
	 * @param value - integer value that node contains
	 */
	public TreeNode(T value) {
		m_value = value;
		m_left = null;
		m_right = null;
		m_parent = null;
	}
	
	/**
	 * Iterator class
	 * 
	 * Implemented with a stack. 
	 * 
	 * While the node stack is not empty: 
	 * 	1. pop the stack and visit the node,
	 * 	2. push it's two children into the stack
	 * 
	 */
	class TreeNodeIterator implements Iterator<T> {
		Stack nodeStack;
		TreeNode<T> curr;
		
		TreeNodeIterator(TreeNode<T> root) {
			nodeStack = new Stack(root);
			curr = root;
		}
		
		@Override
		public boolean hasNext() {
			return nodeStack.isEmpty();
		}

		@Override
		public T next() {
			curr = nodeStack.pop();
			nodeStack.push(curr.getLeft());
			nodeStack.push(curr.getRight());
			return curr;
		}
		
		public void remove() {} 
	}
	
	/**
	 * Returns a new iterator. Starts from the node itself
	 */
	@Override
	public Iterator<T> iterator() {
		return new TreeNodeIterator(this);
	}

	/**
	 * Getters and setters
	 */
	public T getValue() {
		return m_value;
	}

	public TreeNode<T> getLeft() {
		return m_left;
	}

	public TreeNode<T> getRight() {
		return m_right;
	}

	public TreeNode<T> getParent() {
		return m_parent;
	}

	public void setValue(T m_value) {
		this.m_value = m_value;
	}

	public void setLeft(TreeNode<T> m_left) {
		this.m_left = m_left;
	}

	public void setRight(TreeNode<T> m_right) {
		this.m_right = m_right;
	}

	public void setParent(TreeNode<T> m_parent) {
		this.m_parent = m_parent;
	}
	
	public static void main(String[] args) {
		// Test case not really implemented
		ArrayList<Integer> test_1 = new ArrayList<Integer>();
		test_1.add(6);
		test_1.add(5);
		test_1.add(4);
		test_1.add(9);
		test_1.add(7);
		test_1.add(15);
	}
	
}
