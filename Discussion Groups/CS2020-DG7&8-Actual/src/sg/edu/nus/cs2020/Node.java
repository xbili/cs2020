package sg.edu.nus.cs2020;

class Node<T> {

	public T key;
	public Node<T> left, right, parent;
	public Node<T> next, prev;

	public Node(T key) {
		this.key = key;
	}

	public String toString(){
		return key.toString();
	}

}