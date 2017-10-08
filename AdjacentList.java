import java.io.*;
import java.lang.*; 
import java.util.*;

public class AdjacentList {

	ArrayList<AbstractMap.SimpleEntry<String, Integer>> adjacent; 

	public AdjacentList () {

		adjacent = new ArrayList<AbstractMap.SimpleEntry<String, Integer>>(); 

	} 

	// Constructor with parameters if needed 
	//public AdjacentList()


	public void add (AbstractMap.SimpleEntry<String, Integer> pair) {

		this.adjacent.add(pair); 

	}

	public AbstractMap.SimpleEntry<String, Integer> get (int index) {

		return this.adjacent.get(index); 

	}

	public int size () {

		return this.adjacent.size();

	}

	public AbstractMap.SimpleEntry<String,Integer> remove (int index) {
		return this.adjacent.remove(index);
	}

	public String toString () {
		return this.adjacent.toString();  
	}

}