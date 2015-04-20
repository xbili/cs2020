package sg.edu.nus.cs2020;

/**
 * LeafNode
 * The leafnode is designed to be the leaf in a QuestionTree.
 * It extends TreeNode.
 * It has one additional field m_object that stores the object.
 * It overrides all methods to add/modify children links: a leafnode may
 * not have any children.
 * @author gilbert
 *
 * @param <KType>
 */
public class LeafNode<KType> extends TreeNode<KType>{
	
	// The question object being stored in this node
	QuestionObject m_object = null;

	/**
	 * Constructor
	 * @param value name of object
	 * @param object the QuestionObject itself
	 */
	public LeafNode(KType value, QuestionObject object){
		super();
		m_value = value;
		m_object = object;
	}
	
	/**
	 * setLeft
	 * Cannot add a child to a leafnode
	 */
	@Override
	public void setLeft(TreeNode<KType> child){
		throw new UnsupportedOperationException("Cannot add a child to a leaf.");
	}

	/**
	 * setRight
	 * Cannot add a child to a leafnode
	 */
	@Override
	public void setRight(TreeNode<KType> child){
		throw new UnsupportedOperationException("Cannot add a child to a leaf.");
	}
	
	/**
	 * isLeaf
	 * Should always return true.
	 * We perform some additional error checking, just in case.
	 */
	@Override
	public boolean isLeaf(){
		if (getLeft() != null){
			throw new IllegalStateException("Leaf is not a leaf.");
		}
		if (getRight() != null){
			throw new IllegalStateException("Leaf is not a leaf.");
		}
		return true;
	}

	/**
	 * getObject
	 * @return QuestionObject associated with this leaf.
	 */
	public QuestionObject getObject(){
		return m_object;
	}
	
}
