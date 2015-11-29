import java.io.*;
import java.lang.*; 
import java.util.*;

public class Cell {

	HashMap<String, AdjacentList> section; 

	public Cell () {

		section = new HashMap<String, AdjacentList>(); 

	}

	public AdjacentList get (String hashKey) {

		return this.section.get(hashKey); 

	}

	public void put (String hashKey, AdjacentList adj) {

		this.section.put(hashKey, adj); 

	}

	public Set<Map.Entry<String,AdjacentList>> entrySet () {

		return this.section.entrySet(); 

	}

}