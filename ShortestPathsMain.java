import java.util.Map;
import java.util.AbstractMap; 
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.*; 


public class ShortestPathsMain {
	
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

	*/
	ArrayList<ArrayList<HashMap<String, ArrayList<AbstractMap.SimpleEntry<String, Integer>>>>> masterGrid; 

	public recalculate (int u, int v, int w) {

		if (d[v] > d[u] + w (u,v)) {
			d[v] = d[u]  + w (u,v);
			p[v] = u; 
		}
	}

	public int getWeight (int i, int j, String hashKey, int adjVertIndex) {

		return masterGrid.get(i).get(j).get(hashKey).get(adjVertIndex).getValue();

	}

	public void addAdjVert (int i, int j, String hashKey, String adjVertex, int weight) {

			Integer weightInteger = new Integer (weight); 
			Map.Entry<String, Integer> pair = new AbstractMap.SimpleEntry<String, Integer>(adjVertex, weightInteger); 
			masterGrid.get(i).get(j).get(hashKey).add(pair);
		}
	}

	public void addVertToCell (int i, int j, String hashKey) {

		ArrayList adj = new ArrayList(); 
		masterGrid.get(i).get(j).put(hashKey, adj); 

	}

	public void addWeights (BufferedReader br, int w, int h, int k) {

		w --;
		h --;

		String widthIndex = String.valueOf(w); 
		String heightIndex = String.valueOf(h);
		String numVerts = String.valueOf(k);

		while ((line = br.readLine()) != null) {
			
			String[] spliced = line.split("\\s+");
			String[] internal = spliced[0].split(".");

			if (internal.length == 2) {

				String heightChar = internal[0].charAt(1);
				String widthChar = internal[1]; 

				addGridCorner(heightChar, widthChar, spliced);
				
				if (internal[0].charAt(1) == "0") { 
					if (internal[1]  == "0") {
						addAdjVert(0, 0, spliced[0], spliced[1], spliced[2]); 	 
					}
					else if (internal[1]  == widthIndex) {
						addAdjVert(0, w, spliced[0], spliced[1], spliced[2]); 
					}
					else {
						int innerWidthIndex = Integer.parseInt(internal[1]);
						addAdjVert(0, innerWidthIndex-1, spliced[0], spliced[1], spliced[2]);
						addAdjVert(0, innerWidthIndex, spliced[0], spliced[1], spliced[2]); 
					}	 
				}
			}
		}

	public void addGridCorner (String heightChar, String widthChar, String[] spliced, String widthIndex, String heightIndex) {

		if (internal[0].charAt(1) == heightIndex) { 
			if (internal[1]  == "0") {
				addAdjVert(0, 0, spliced[0], spliced[1], spliced[2]); 	 
			}
			else if (internal[1]  == widthIndex) {
				addAdjVert(0, w, spliced[0], spliced[1], spliced[2]); 
			}
			else {
				int innerWidthIndex = Integer.parseInt(internal[1]);
				addAdjVert(0, innerWidthIndex-1, spliced[0], spliced[1], spliced[2]);
				addAdjVert(0, innerWidthIndex, spliced[0], spliced[1], spliced[2]); 
			}	 
		}
	}

		/*
	public void Djikstra () {
		extract min from Q
		add to S

		for each v adj to u
			relax (u, v, w); 
	}
	*/

	public void initializeGrid (int w, int h, int k) {

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				// make the corners 
				for (int di = 0; di <= 1; di++) {
					for (int dj = 0; dj <= 1; dj++) {
					    String hashKey1 = "g" + Integer.toString(i + di) + "." + Integer.toString(j + dj); 
						masterGrid.addVertToCell(i, j, hashKey1);
					    }
				    }
				// make the rest
				for (int v = 0; v < k; v++) {
					String hashKey2 = "v" + i + "." + j + "." + v; 
					masterGrid.addVertToCell(i, j, hashKey2);
				}
			}
		}
	}

	public static void main (String args[]) {

		if (args.length == 0) {
			System.err.println("USAGE: specify a MakeRandomGraph input file
				as the first command line argument");
			System.exit(1);  
		}

		//ShortestPathsMain shortest = new ShortestPathsMain(); 

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
	    String line;

	    line = br.readLine()
	    String[] spliced = line.split("\\s+"); 

	    int w = Integer.parseInt(spliced[0]);
	    int h = Integer.parseInt (spliced[1]);  
	    int k = Integer.parseInt(spliced[1]); 

	    masterGrid = new ArrayList<ArrayList<HashMap<String, ArrayList<AbstractMap.SimpleEntry>>(k)>(h)>(w); 
	    initializeGrid(w, h, k); 

	    addWeights(br, width, height, numVerts); 

		    
		}
	}
}