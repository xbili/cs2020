package sg.edu.nus.cs2020;

import java.util.*;

class Tree<T extends Comparable<T>> implements Iterable<T> {

	Node<T> root;

	public Tree() {

	}

	public void insert(Node<T> node){
		if(root == null) root = node;
		else insertRecursion(root, node);
	}

	public void insertRecursion(Node<T> currentNode, Node<T> newNode){
		T currentKey = currentNode.key;
		T newKey = newNode.key;
		if(newKey.compareTo(currentKey) == -1){
			if(currentNode.left == null){
				currentNode.left = newNode;
				newNode.parent = currentNode;
				newNode.prev = currentNode.prev;
				newNode.next = currentNode;
				if(currentNode.prev != null) currentNode.prev.next = newNode;
				currentNode.prev = newNode;

			} else {
				insertRecursion(currentNode.left, newNode);
			}
		} else {
			if(currentNode.right == null){
				currentNode.right = newNode;
				newNode.parent = currentNode;
			} else {
				insertRecursion(currentNode.right, newNode);
			}
		}
	}

	public Iterator<T> iterator(){
		ArrayList<T> preOrderedKeys = new ArrayList<>();
		preOrderRecursion(preOrderedKeys, root);
		return preOrderedKeys.iterator();
	}

	public void preOrderRecursion(ArrayList<T> preOrderedKeys, Node<T> currentNode){
		if(currentNode == null) return;
		preOrderRecursion(preOrderedKeys, currentNode.left);
		preOrderRecursion(preOrderedKeys, currentNode.right);
		preOrderedKeys.add(currentNode.key);
	}

	public static void main(String[] args) {
		Tree<Integer> tree = new Tree<>();
		tree.insert(new Node<Integer>(3));
		tree.insert(new Node<Integer>(1));
		tree.insert(new Node<Integer>(5));
		tree.insert(new Node<Integer>(0));
		tree.insert(new Node<Integer>(2));
		tree.insert(new Node<Integer>(4));
		tree.insert(new Node<Integer>(6));

		for(int node : tree){
			System.out.println(node);
		}

	}
}