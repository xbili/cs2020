package sg.edu.nus.cs2020;

public class QuestionGame extends QuestionGameBase {
	
	/**
	 * Constructor
	 * 
	 * @param objectFileName - Filename of Database to include
	 */
	protected QuestionGame(String objectFileName) {
		super(objectFileName);
	}

	/**
	 * Creates a new tree with the database loaded in the QuestionGame object.
	 * 
	 */
	@Override
	protected QuestionTreeBase CreateTree() {
		QuestionTree newTree = new QuestionTree();
		
		newTree.buildTree(m_objects);
		
		return newTree;
	}
	
	/**
	 * Test cases from the 5 databases provided. 
	 * 
	 * Possible to wrap the 5 games in a for-loop to test multiple times if the game fails. (It doesn't)
	 * 
	 * @param args
	 * @throws Exception - Usually Lose/Null pointer exceptions
	 */
	public static void main(String[] args) throws Exception {
		// Uncomment the for loop to test the game for 100 times - it works
		// for (int i = 0; i < 100; i++) {
			System.out.println("TestDB_1");
			QuestionGame obj_1 = new QuestionGame("TestDB_1.txt");
			obj_1.playGame();
			System.out.println("--------------------------------");
			
			System.out.println("TestDB_2");
			QuestionGame obj_2 = new QuestionGame("TestDB_2.txt");
			obj_2.playGame();
			System.out.println("--------------------------------");
			
			System.out.println("TestDB_3");
			QuestionGame obj_3 = new QuestionGame("TestDB_3.txt");
			obj_3.playGame();
			System.out.println("--------------------------------");
			
			System.out.println("TestDB_4");
			QuestionGame obj_4 = new QuestionGame("TestDB_4.txt");
			obj_4.playGame();
			System.out.println("--------------------------------");
			
			System.out.println("TestDB_5");
			QuestionGame obj_5 = new QuestionGame("TestDB_5.txt");
			obj_5.playGame();
			System.out.println("--------------------------------");
			
			System.out.println("Actors");
			QuestionGame obj_6 = new QuestionGame("Actors.txt");
			obj_6.playGame();
			System.out.println("--------------------------------");
		}
//	}
}
