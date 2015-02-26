package sg.edu.nus.cs2020;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Twenty Questions
 * @author gilbert
 *
 */
public abstract class QuestionGameBase {

	/*
	 * State:
	 * The game consists of a player who is trying to guess a selected object
	 */
	
	// The player class
	QuestionPlayer m_player;

	// The chosen object
	private QuestionObject m_chosenObject;
	
	// All the objects
	ArrayList<QuestionObject> m_objects;
	
	// Random number generator
	private Random m_gen;
	
	/*
	 * Constructor
	 */
	
	/**
	 * Constructor: QuestionGame
	 * @param objectFileName
	 * The constructor reads in the objects from a specified file, and choose
	 * a random object.
	 * It also initializes the player, sending it the list of possible objects.
	 */
	protected QuestionGameBase(String objectFileName){
		
		// Get the objects
		m_objects = readObjectFile(objectFileName);
		
		// Initialize random number generator.
		m_gen = new java.util.Random();
		
		// Initialize the game state
		RestartGame();
	}
	
	/**
	 * RestartGame
	 * Reinitializes the game variables to play again.
	 * It first chooses a new object at random, and then
	 * creates a new player to play the game.
	 */
	public void RestartGame(){
		// Choose a new object
		int numObject = m_objects.size();
		m_chosenObject = m_objects.get(m_gen.nextInt(numObject));
		
		// Initialize the player
		m_player = new QuestionPlayer(m_objects, CreateTree());
	}
	
	/**
	 * CreateTree
	 * Creates a new empty tree for running one game. This must be implemented in
	 * subclasses.  The tree should be empty.  It is populated with objects later.
	 */
	protected abstract QuestionTreeBase CreateTree();
	
	/**
	 * readObjectFile
	 * @param objectFileName
	 * @return An array of QuestionObject
	 * Reads in a specially formatted file
	 */
	ArrayList<QuestionObject> readObjectFile(String objectFileName){
		try{
			// Open the file
			ArrayList<QuestionObject> objArray = null;
			FileReader f = new FileReader(objectFileName);
			BufferedReader buff = new BufferedReader(f);
			
			// Read the first line of the file and
			// parse the number of objects
			String line = buff.readLine().trim();
			int objCount = Integer.parseInt(line);
			objArray = new ArrayList<QuestionObject>(objCount);
			
			// Read in each object
			for (int i=0; i<objCount; i++){
				// The first line of the object contains its name
				String name = buff.readLine().trim();
				// The second line of the object contains the number of properties
				String propCountLine = buff.readLine().trim();
				int propCount = Integer.parseInt(propCountLine);
				
				// Now we loop and read in each property
				TreeSet<String> props = new TreeSet<String>();
				for (int j=0; j<propCount; j++){
					String propName = buff.readLine().trim();
					if ((propName != null) && (propName != "")) {
						props.add(propName);
					}
				}
				
				// Once we have all the properties, create a new QuestionObject
				if ((name != null) && (name != "") && (props.size() > 0)){
					objArray.add(new QuestionObject(name, props));
				}
			}
			f.close();
			buff.close();
			return objArray;
		}
		catch(Exception e)
		{
			// If there is an error reading in the file, there isn't much we can do.
			// Print out an error, and later the program will exit.
			System.out.println(e);
			System.out.println("Unable to read in object database.  Please check the filename, the path, and that the file is formatted correctly.");
		}	
		return null;
	}
	
	/**
	 * isSatisfied
	 * @param query
	 * @return true/false
	 * Checks whether the chosen object satisfies (or does not satisfy) the query.
	 * Assumes that the properties are in sorted order (both in the query and in the object).
	 * Since both lists are sorted, it just loops through and verifies that all the specifies properties
	 * and non-properties are present.
	 */
	private boolean isSatisfied(Query query){
		// Error checking
		if (query == null){
			throw new IllegalArgumentException("Null query cannot be satisfied.");
		}
		
		// Get iterators for the query
		Iterator<String> props = query.propertyIterator();
		Iterator<String> notProps = query.notPropertyIterator();
				
		// First, we check the properties specified in the query
		while (props.hasNext()){
			String property = props.next();
			if (!m_chosenObject.containsProperty(property)){
				return false;
			}
		}
		
		// Second, we check the negative properties specified in the query
		while (notProps.hasNext()){
			String notProperty = notProps.next();
			if (m_chosenObject.containsProperty(notProperty)){
				return false;
			}
		}
		
		// If we got this far, the query is satisfied
		return true;		
	}
	
	/**
	 * playGame
	 * This is the main loop for playing the game.
	 * It repeatedly asks the player for a query,
	 * checks whether it is satisfied, and updates
	 * the user.  When the user is ready to guess,
	 * we ask for a guess, and see if they are right.
	 */
	int playGame() throws Exception {
		System.out.println(m_chosenObject);
		int countQueries = 0;
		
		// Main loop here
		while (!m_player.readyToGuess()){
			countQueries++;
			// Get the player's query
			Query query = m_player.nextGuess();
			System.out.println(query);
			// See if the query is true or false
			boolean answer = isSatisfied(query);
			System.out.println("Answer: " + answer);
			// Now update the player
			m_player.update(query, answer);
		}
		
		// Ok, the player is ready to guess
		String guess = m_player.guessObject();
		System.out.println("You guess: " + guess);
		// See if they are right!
		if (guess.compareTo(m_chosenObject.getName())== 0){
			System.out.println("You win!");
		}
		else {
			System.out.println("Loser!");
			throw new Exception("You lost!");
		}
		System.out.println("You made " + countQueries + " guesses.");
		return countQueries;
	}
	
}
