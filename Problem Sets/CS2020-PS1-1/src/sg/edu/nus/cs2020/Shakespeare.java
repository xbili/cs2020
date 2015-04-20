package sg.edu.nus.cs2020;

import sg.edu.nus.cs2020.VectorTextFile;

public class Shakespeare {
	public static void main(String args[]) {
		// Load in all of the text files
		VectorTextFile hamlet = new VectorTextFile("hamlet.txt");
		VectorTextFile henryv = new VectorTextFile("henryv.txt");
		VectorTextFile macbeth = new VectorTextFile("macbeth.txt");
		VectorTextFile mystery = new VectorTextFile("mystery.txt");
		VectorTextFile cromwell = new VectorTextFile("cromwell.txt");
		VectorTextFile vortigern = new VectorTextFile("vortigern.txt");

		// hamlet and henryv
		System.out.println("Hamlet vs Henry V: " + VectorTextFile.Angle(hamlet, henryv));
		// hamlet and macbeth
		System.out.println("Hamlet vs Macbeth: " + VectorTextFile.Angle(hamlet, macbeth));
		// hamlet and mystery
		System.out.println("Hamlet vs Mystery: " + VectorTextFile.Angle(hamlet, mystery));
		// hamlet and cromwell
		System.out.println("Hamlet vs Cromwell: " + VectorTextFile.Angle(hamlet, cromwell));
		// hamlet and vortigern
		System.out.println("Hamlet vs Vortigern: " + VectorTextFile.Angle(hamlet, vortigern));
		
		// henryv and macbeth
		System.out.println("Henry V vs Macbeth: " + VectorTextFile.Angle(henryv, macbeth));
		// henryv and mystery
		System.out.println("Henry V vs Mystery: " + VectorTextFile.Angle(henryv, mystery));
		// henryv and cromwell
		System.out.println("Henry V vs Cromwell: " + VectorTextFile.Angle(henryv, cromwell));
		// henryv and vortigern
		System.out.println("Herny V vs Vortigern: " + VectorTextFile.Angle(henryv, vortigern));
		
		// macbeth and mystery
		System.out.println("Macbeth vs Mystery: " + VectorTextFile.Angle(macbeth, mystery));
		// macbeth and cromwell
		System.out.println("Macbeth vs Cromwell: " + VectorTextFile.Angle(macbeth, cromwell));
		// macbeth and vortigern
		System.out.println("Macbeth vs Vortigern: " + VectorTextFile.Angle(macbeth, vortigern));
		
		// mystery and cromwell
		System.out.println("Mystery vs Cromwell: " + VectorTextFile.Angle(mystery, cromwell));
		// mystery and vortigen
		System.out.println("Mystery vs Vortigern: " + VectorTextFile.Angle(mystery, vortigern));
		
		// cromwell and vortigern
		System.out.println("Cromwell vs Vortigern: " + VectorTextFile.Angle(cromwell, vortigern));
		
		// Judging from the outputs, I would say that 0.41 is a rough estimate of the threshold.
		/*
		 * This means that
		 * Cromwell and Vortigern were not written by Shakespeare, 
		 * while Hamlet, Henry V, Mystery and Macbeth were written by Shakespeare.
		 * */
	}
}
