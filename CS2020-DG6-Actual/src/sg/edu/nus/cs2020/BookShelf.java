package sg.edu.nus.cs2020;

import java.io.*;
import java.math.*;
import java.util.*;

class BookShelf implements Iterable<Book>{
	public ArrayList<Book> bookList;

	BookShelf(){
		bookList = new ArrayList<>();
	}

	public void add(Book b){
		bookList.add(b);
	}

	public Iterator<Book> iterator(){
		return new BookIterator(bookList);
	}

	public class BookIterator implements Iterator<Book> {
		ArrayList<Book> bookList;
		int index = 0;

		BookIterator(ArrayList<Book> bookList){
			this.bookList = bookList;
			index = 0;
		}

		public boolean hasNext(){
			if(index == bookList.size()) return false;
			return true;
		}

		public Book next(){
			Book book = bookList.get(index);
			index++;
			return book;
		}

		public void remove(){
			
		}
	}

	public static void main(String[] args) {
		BookShelf bl = new BookShelf();
		bl.add(new Book("Harry Potter"));
		bl.add(new Book("Narnia"));
		bl.add(new Book("Doraemon"));
		for(Book b : bl){
			b.shoutTitle();
		}
	}
}