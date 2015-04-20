package sg.edu.nus.cs2020;

import java.util.ArrayList;

public class Stack {
	private ArrayList<TreeNode> m_stack;
	private int count;
	
	public Stack(TreeNode firstItem) {
		m_stack = new ArrayList<TreeNode>();
		m_stack.add(firstItem);
		count++;
	}
	
	public void push(TreeNode item) {
		m_stack.add(item);
		count++;
	}
	
	public TreeNode pop() {
		if (m_stack.isEmpty()) {
			System.out.println("Error: Empty stack.");
			return null;
		}
		else {
			TreeNode result = m_stack.get(count);
			m_stack.remove(count);
			count--;
			return result;
		}
	}
	
	public boolean isEmpty() {
		return m_stack.isEmpty();
	}
	
	public void printStack() {
		for (int i=0; i < m_stack.size(); i++) {
			System.out.println(i + ":" + m_stack.get(i));
		}
	}
}
