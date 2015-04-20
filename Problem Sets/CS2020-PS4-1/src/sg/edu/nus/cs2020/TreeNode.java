package sg.edu.nus.cs2020;

/**
 * TreeNode class for 20 Questions Game
 * @author gilbert
 *
 * @param <KType>
 * @param <VType>
 */
public class TreeNode<KType> {
	
	// Left child
	private TreeNode<KType> m_left = null;
	// Right child
	private TreeNode<KType> m_right = null;
	// Parent
	private TreeNode<KType> m_parent = null;
	
	// Each node represents either a property or an object (name).
	// Thus, one of these two should be set, and the other should be null.
	
	// Property or object name
	protected KType m_value = null;
	
	/**
	 * Constructor
	 * Builds an empty node.
	 */
	public TreeNode(){
		m_left = null;
		m_right = null;
		m_parent = null;
		m_value=null;
	}
	
	/**
	 * Constructor
	 * @param key
	 * @param value
	 * Builds a node with a key/value
	 */
	public TreeNode(KType v){
		m_value = v;
	}
	
	/**
	 * getLeft
	 * @return left child
	 */
	public TreeNode<KType> getLeft(){
		return m_left;
	}
	
	/**
	 * getRight
	 * @return right child
	 */
	public TreeNode<KType> getRight(){
		return m_right;
	}
	
	/**
	 * getParent
	 * @return parent
	 */
	public TreeNode<KType> getParent(){
		return m_parent;
	}
	
	/**
	 * getProperty
	 * @return property
	 */
	public KType getValue(){
		return m_value;
	}
	
	/**
	 * setLeft
	 * @param newNode
	 * Updates left child
	 */
	public void setLeft(TreeNode<KType> newNode){
		m_left = newNode;
	}
	
	/**
	 * setRight
	 * @param newNode
	 * Updates right child
	 */
	public void setRight(TreeNode<KType> newNode){
		m_right = newNode;
	}
	
	/**
	 * setParent
	 * @param newNode
	 * Updates parent
	 */
	public void setParent(TreeNode<KType> newNode){
		m_parent = newNode;
	}
	
	public boolean isLeaf(){
		if ((m_right == null) && (m_left == null)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Returns a string version of the node.
	 * @Override
	 */
	public String toString(){
		return new String("(" + m_value + ")");
	}
	
	
}
