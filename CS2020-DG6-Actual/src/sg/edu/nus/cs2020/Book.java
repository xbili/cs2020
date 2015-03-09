package sg.edu.nus.cs2020;

import java.io.*;
import java.math.*;
import java.util.*;

class Book {
	String title;

	Book(String title){
		this.title = title;
	}

	void shoutTitle (){
		System.out.println("My name is " + title);
	}
}