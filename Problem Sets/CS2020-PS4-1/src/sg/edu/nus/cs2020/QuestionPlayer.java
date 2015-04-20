package sg.edu.nus.cs2020;

/**
 * QuestionPlayer
 * @author gilbert
 * 
 * This is the main class for the player of Twenty Questions.
 *
 */
public class QuestionPlayer {
	
	// The player maintains a tree, which keeps track
	// of the current state of the game
	QuestionTreeBase m_tree = null;
	
	/**
	 * Constructor
	 * @param objects
	 * @param tree
	 * Builds a new question tree, based on the objects
	 * with which it is initialized.
	 */
	public QuestionPlayer(java.util.ArrayList<QuestionObject> objects,
			QuestionTreeBase tree){
		m_tree = tree;
		m_tree.buildTree(objects);
	}
	
	/**
	 * readyToGuess
	 * @return true/false
	 * The player is ready ot guess when there is only one possible
	 * object left in the tree.
	 */
	public boolean readyToGuess(){
		return (m_tree.countObjects() == 1);
	}
	
	/**
	 * guessObject
	 * @return name of object
	 * When we are ready to guess, there is only one object left
	 * in the tree.  Find that object and return it as your guess.
	 */
	public String guessObject(){
		if (!readyToGuess()){
			return null;
		}
		return m_tree.getOneObject();
	}
	
	/**
	 * nextGuess
	 * @return a query
	 * Delegate the query to the tree.
	 * The tree is responsible for finding a good query to return.
	 */
	public Query nextGuess(){
		return m_tree.findQuery();
	}
	
	/**
	 * update
	 * @param query that was performed
	 * @param answer whether it was satisfied or not by the chosen object
	 * This is delegated to the tree
	 */
	public void update(Query query, boolean answer){
		m_tree.updateTree(query, answer);
	}

}
