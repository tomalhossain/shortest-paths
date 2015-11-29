/*import java.util.Map;
import java.util.AbstractMap; 
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;*/

/*import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;*/
import java.io.*;
import java.lang.*; 
import java.util.*;

/*
- The overall data type that will house the entire list of vertices. 
- The outermost level of the mastergrid is a 2-D Arraylist which represents the matrix of cells
each of which contains k vertices. 
- Within each cell is a HashMap which maps a given vertex in the cell to its adjecent vertices. 
- The adjacent vertices are stored in an ArrayList, and each element in this Arraylist is a 
pair of values (AbstractMap.SimpleEntry), namely the name of the adjacent vertext and the 
weight to that vertex. 

d[v] // distance of v from src - initialize to MAX.VALUE
p[v] // parent of v in shortest path - initialize to nil
d[s] = 0  

// S set of all nodes that already have found shortest path
// Q all verts in graph

@author Tomal Hossain
@version 0.1 2015-11-23

*/

public class ShortestPathsMain {

	ArrayList<ArrayList<Cell>> masterGrid;

	public ShortestPathsMain (int h, int w, int k) {

		masterGrid = new ArrayList<ArrayList<Cell>>(); 

		// initializing the rows of the master grid 
		for (int i = 0; i < h; i++) {
			ArrayList<Cell> cols = new ArrayList<Cell>();
			for (int j = 0; j < w; j++) {
				Cell init = new Cell(); 
				cols.add(init);
			}
			masterGrid.add(cols);
		}
	} 

	public int getWeight (int i, int j, String hashKey, int adjVertIndex) {

		return masterGrid.get(i).get(j).get(hashKey).get(adjVertIndex).getValue();

	}

	public void addAdjVert (int i, int j, String hashKey, String adjVertex, int weight) {

		Integer weightInteger = new Integer (weight); 
		AbstractMap.SimpleEntry<String, Integer> pair = new AbstractMap.SimpleEntry<String, Integer>(adjVertex, weightInteger); 
		masterGrid.get(i).get(j).get(hashKey).add(pair);
	}
	

	public void addVertToCell (int i, int j, String hashKey) {

		AdjacentList adj = new AdjacentList();
		//System.out.println(Integer.toString(i) + " " + Integer.toString(j));  
		masterGrid.get(i).get(j).put(hashKey, adj); 

	}

	public void addWeights (BufferedReader br, int h, int w, int k) {

		// String and Integer representations of the grid dimensions
		h --;
		w --;
		
		String line;
		String q = "q";  
		Character qChar = q.charAt(0);

		try {
  			while ((line = br.readLine()) != null && line.charAt(0) != qChar) {
				
				String[] spliced = line.split("\\s+");
				String vert1 = spliced[0]; 
				String vert2 = spliced[1]; 
				String edge = spliced[2]; 
				int edgeWeight = Integer.parseInt(edge); 

				String[] internal = vert1.split("\\.");

				// String and integer representations of vertex coordinates
				String heightChar =  Character.toString(internal[0].charAt(1));
				String widthChar = internal[1]; 
				int heightCharIndex = Integer.parseInt(heightChar);
				int widthCharIndex = Integer.parseInt(widthChar); 
				
				if (internal.length == 2) {
					// Adding outer grid points, 4 corners only belong to a only 1 cell while side gird points belong to 2 cells
					if ((heightCharIndex == 0 || heightCharIndex == h + 1) || (widthCharIndex == 0 || widthCharIndex == w + 1)) {
						if (heightCharIndex == 0) { 
							if (widthCharIndex  == 0) {
								addAdjVert(0, 0, vert1, vert2, edgeWeight); 	 
							}
							else if (widthCharIndex  == w + 1) {
								addAdjVert(0, w, vert1, vert2, edgeWeight); 	 
							}
							else {
								addAdjVert(0, widthCharIndex-1, vert1, vert2, edgeWeight);
								addAdjVert(0, widthCharIndex, vert1, vert2, edgeWeight); 
							}	 
						}
						else if (heightCharIndex == h + 1) { 
							if (widthCharIndex  == 0) {
								addAdjVert(h, 0, vert1, vert2, edgeWeight); 	 
							}
							else if (widthCharIndex  == w + 1) {
								addAdjVert(h, w, vert1, vert2, edgeWeight); 
							}
							else {
								addAdjVert(h, widthCharIndex-1, vert1, vert2, edgeWeight);
								addAdjVert(h, widthCharIndex, vert1, vert2, edgeWeight); 
							}	 
						}
						else {
							if (widthCharIndex  == 0) {
								addAdjVert(heightCharIndex, 0, vert1, vert2, edgeWeight);
								addAdjVert(heightCharIndex-1, 0, vert1, vert2, edgeWeight); 
							}
							else if (widthCharIndex  == w + 1) {
								addAdjVert(heightCharIndex, w, vert1, vert2, edgeWeight);
								addAdjVert(heightCharIndex-1, w, vert1, vert2, edgeWeight); 
							}
						}
					}
				// Adding inner grid points, each of which belongs to 4 cells
					else {  

						addAdjVert (heightCharIndex, widthCharIndex, vert1, vert2, edgeWeight);
						addAdjVert (heightCharIndex-1, widthCharIndex, vert1, vert2, edgeWeight);
						addAdjVert (heightCharIndex, widthCharIndex-1, vert1, vert2, edgeWeight);
						addAdjVert (heightCharIndex-1, widthCharIndex-1, vert1, vert2, edgeWeight);
					}
				}
				// Add all remaining internal cell vertices (each belong to only 1 cell)
				else {
					addAdjVert (heightCharIndex, widthCharIndex, vert1, vert2, edgeWeight);
				}
			}
		}
		catch (IOException e) {
	    	System.out.println("Buffered reader error"); 
	    }
	}

	public void initializeGrid (int h, int w, int k) {


		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				// make the corners 
				for (int di = 0; di <= 1; di++) {
					for (int dj = 0; dj <= 1; dj++) {
					    String hashKey1 = "g" + Integer.toString(i + di) + "." + Integer.toString(j + dj); 
						addVertToCell(i, j, hashKey1);
					    }
				    }
				// make the rest
				for (int v = 0; v < k; v++) {
					String hashKey2 = "v" + i + "." + j + "." + v; 
					addVertToCell(i, j, hashKey2);
				}
			}
		}
	}

	public static void printMap (Cell map) {
	 Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();
		    System.out.print(pair.getKey() + " --->" ); 
		    AdjacentList adjVerts = pair.getValue();
		    for (int i = 0; i < adjVerts.size(); i++) {
		    	System.out.print(" (" + adjVerts.get(i).getKey() + ", " + adjVerts.get(i).getValue() + ")");
		    }
		    System.out.println(); 
		    it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public void printGrid (ArrayList<ArrayList<Cell>> grid, int h, int w, int k) {
	
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				printMap(grid.get(i).get(j)); 
			}
		}
	}

	/*
	public void recalculate (int u, int v, int w) {

		if (d[v] > d[u] + w (u,v)) {
			d[v] = d[u]  + w (u,v);
			p[v] = u; 
		}
	}
*/

	public static void main (String args[]) {

		if (args.length == 0) {
			System.err.println("USAGE: specify a graph input file as the first command line argument");
			System.exit(1);  
		}

		FileReader reader = null;
		try {
			reader = new FileReader(args[0]); 	
		} 
		catch (FileNotFoundException e)
    	{
      		System.err.println("File does not exit. Program will now terminate.");
      		System.exit(0);
    	}

		BufferedReader br = new BufferedReader(reader);
	    String line = "";

	    try {
	    	line = br.readLine();
	    }
	    catch (IOException e) {
	    	System.out.println("Buffered reader error"); 
	    }

	    String[] spliced = line.split("\\s+"); 
		
		int h = Integer.parseInt (spliced[1]); 
	    int w = Integer.parseInt(spliced[0]);   
	    int k = Integer.parseInt(spliced[2]);
	    
	    ShortestPathsMain shortestMain = new ShortestPathsMain(h, w, k);  

	    shortestMain.initializeGrid(h, w, k); 
	    shortestMain.addWeights(br, h, w, k); 
	    shortestMain.printGrid(shortestMain.masterGrid, h, w, k);
	}
}
