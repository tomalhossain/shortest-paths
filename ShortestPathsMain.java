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
	/*
	public void addAdjVert2 (HashMap gridPaths, String hashKey, String adjVertex, int weight) {

		Integer weightInteger = new Integer (weight); 
		AbstractMap.SimpleEntry<String, Integer> pair = new AbstractMap.SimpleEntry<String, Integer>(adjVertex, weightInteger); 
		gridPaths.get(hashKey).add(pair);
	}
	*/

	public void addVertToCell2 (HashMap gridPaths, String hashKey) {

		AdjacentList adj = new AdjacentList();
		//System.out.println(Integer.toString(i) + " " + Integer.toString(j));  
		gridPaths.put(hashKey, adj); 

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

				for (int i = 0; i < 2; i++) { 
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
					String temp = vert1; 
					vert1 = vert2; 
					vert2 = temp; 
				}
			}
		}
		catch (IOException e) {
	    	System.out.println("Buffered reader error"); 
	    }
	}

	public void addWeights2 (HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths, String gridVert, int h, int w, int k, int y, int z) {
		//HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths = new HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>>();
		// String and Integer representations of the grid dimensions
		h --;
		w --;

		// String and integer representations of vertex coordinates
		String heightChar =  Character.toString(gridVert.charAt(1));
		String widthChar = Character.toString(gridVert.charAt(3));
		int heightCharIndex = Integer.parseInt(heightChar);
		int widthCharIndex = Integer.parseInt(widthChar); 
	
		// Adding outer grid points, 4 corners only belong to a only 1 cell while side grid points belong to 2 cells
		if ((heightCharIndex == 0 || heightCharIndex == h + 1) || (widthCharIndex == 0 || widthCharIndex == w + 1)) {
			if (heightCharIndex == 0) { 
				if (widthCharIndex  == 0) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
				}
				else if (widthCharIndex  == w + 1) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z); 	 
				}
				else {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1);  
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1); 		 
				}	 
			}
			else if (heightCharIndex == h + 1) { 
				if (widthCharIndex  == 0) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 	 
				}
				else if (widthCharIndex  == w + 1) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
				}
				else {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1);
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1); 
				}	 
			}
			else {
				if (widthCharIndex  == 0) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y-1, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y-1, z); 
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z);  
				}
				else if (widthCharIndex  == w + 1) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y-1, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y-1, z); 
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z); 
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z);   
				}
			}
		}
		// Adding inner grid points, each of which belongs to 4 cells
		else {  
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y-1, z-1);
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y-1, z-1);
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y-1, z);
			addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y-1, z); 
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z);
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z); 
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1);
			addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z-1); 
		}
	}

	public void addPath (HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths, String source, String goal, int h , int w, int k, int y, int z) {
		//System.out.println("y : " + y + "  z : " + z); 
		AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = this.dijkstra(this.masterGrid, source, goal, h, w, k, y, z); 
		AbstractMap.SimpleEntry<String, String> vertexPair = new AbstractMap.SimpleEntry<String, String>(source, goal);
		gridPaths.put(vertexPair, shortestPath); 
	}

	public void initializeGrid (int h, int w, int k, PriorityQueue distances) {

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

	public static void printMap (Cell cell) {
		Iterator it = cell.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();
		    System.out.print(pair.getKey() + " --->" ); 
		    AdjacentList adjVerts = pair.getValue();
		    for (int i = 0; i < adjVerts.size(); i++) {
		    	System.out.print(" (" + adjVerts.get(i).getKey() + ", " + adjVerts.get(i).getValue() + ")");
		    }
		    System.out.println(); 
		    
		}
	}

	public void printGrid (ArrayList<ArrayList<Cell>> grid, int h, int w, int k) {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				printMap(grid.get(i).get(j)); 
			}
		}
	}

	public AbstractMap.SimpleEntry<LinkedList<String>,Integer> dijkstra (ArrayList<ArrayList<Cell>> grid, String source, String goal, int h, int w, int k, int y, int z) {
		// Constructs a priority queue in which items are vertex name strings and 
		// priorities are discovered distances from the given source vertex. 

				String [] parents = new String [2*k]; //WATCH OUT
				PriorityQueue<String,Integer> priority = new PriorityQueue<String,Integer>(); 
				
				Map<String, Integer> visited = new HashMap(priority.getSize()); 

				Cell cell = grid.get(y).get(z);
				//Cell cell = grid.get(0).get(0);
				Iterator it = cell.section.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();
					//System.out.println(pair.getKey()); 
				    priority.addItem(pair.getKey(), Integer.MAX_VALUE);

				}

				Map<String, Integer> priorityMap = priority.getMap(); 
				Map<String, Integer> itemMap = new HashMap(priorityMap.size()); 

				Iterator iterator = priorityMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry <String,Integer> pear = (Map.Entry<String,Integer>)iterator.next();
				    itemMap.put(pear.getKey(), pear.getValue());

				}

				int ind = itemMap.get(source); 
				parents[ind] = null; 

				priority.getMap(); 

				priority.changePriority(source, 0);

				while (priority.getSize() > 0) {
					String curr = priority.peekTop(); 
					//System.out.println("currstring = " + curr);
					Integer currPri = new Integer(priority.getPriority(curr));
					//System.out.println("CURRPRI = " + currPri);
					
					Character vertTypeChar = curr.charAt(0);
					String currVertType = Character.toString(vertTypeChar);
					//System.out.println("currVertType = " + "'" + currVertType + "'" );
				
					if (curr != goal) { 

						AdjacentList adjList = cell.get(curr);
						//System.out.println("adjlist = " + adjList);
						
						for (int i = 0; i < adjList.size(); i++) {
							
							AbstractMap.SimpleEntry <String, Integer> entry = adjList.get(i);
							String adjCurr = entry.getKey();
							
							Character adjVertTypeChar = adjCurr.charAt(0);
							String adjVertType = Character.toString(adjVertTypeChar);
							//System.out.println("adjVertType = " + "'" + adjVertType + "'" );
							//System.out.println("adjvert = "  + adjCurr);

							Character rowChar = adjCurr.charAt(1);
							String row = Character.toString(rowChar);
							int rowInt = Integer.parseInt(row);
							//System.out.println("rowInt = " + rowInt);

							Character colChar = adjCurr.charAt(3);
							String col = Character.toString(colChar);
							int colInt = Integer.parseInt(col);
							//System.out.println("colInt = " + colInt);
						
							if (!visited.containsKey(adjCurr))  {
								
								if (currVertType.equals("v")) {
									
									int dist = entry.getValue();
									Integer distInt = new Integer(dist);
									Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));
									//System.out.println("pricurradjbefore = " + adjCurrPri);
									
									if (adjCurrPri > currPri + distInt) {
										adjCurrPri = currPri + distInt;
										int newPri = adjCurrPri.intValue(); 
										priority.changePriority(adjCurr, newPri); 
										parents[itemMap.get(adjCurr)] = curr; 
									}
									//System.out.println("pricurradjafter = " + adjCurrPri);
								}

								else if (currVertType.equals("g")) {

									if (adjVertType.equals("v") && (rowInt != y || colInt != z)) {
										continue;
									}
									else {
										int dist = entry.getValue();
										Integer distInt = new Integer(dist);
										Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));
										//System.out.println("pricurradj = " + adjCurrPri);
										
										if (adjCurrPri > currPri + distInt) {
											adjCurrPri = currPri + distInt;
											int newPri = adjCurrPri.intValue(); 
											priority.changePriority(adjCurr, newPri); 
											parents[itemMap.get(adjCurr)] = curr; 
										}
										//System.out.println("pricurradjafter = " + adjCurrPri);
									}
								}
							}
						}
					}
					else {
						break; 
					}
					visited.put(priority.removeItem(), currPri);
					//System.out.println(visited);
					//System.out.println("visited-size = " + visited.size());
					//System.out.println("priority-size = " + priority.getSize()); 
				}
				
				//System.out.println("PRINTING THE SHORTEST PATH");
				// A mapping of pairs of grid vertices in a given cell to the shortest path
				// that connects said vertices

				LinkedList<String> path = new LinkedList<String>(); 
				int shortest = visited.get(goal); 

				while (goal != null) {
					//System.out.println(goal + " = " + visited.get(goal));
					path.addFirst(goal); 
					int goalIndex = itemMap.get(goal);
					goal = parents[goalIndex]; 
				}
				AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = new AbstractMap.SimpleEntry<LinkedList<String>, Integer>(path, shortest); 
				return shortestPath; 
	}

	// To be run on the Grid Graph
	public AbstractMap.SimpleEntry<LinkedList<String>,Integer> dijkstra2 (Cell cell, String source, String goal, int h, int w, int k) {
	// Constructs a priority queue in which items are vertex name strings and 
	// priorities are discovered distances from the given source vertex. 

			String [] parents = new String [2*k]; //WATCH OUT
			PriorityQueue<String,Integer> priority = new PriorityQueue<String,Integer>(); 
			
			Map<String, Integer> visited = new HashMap(priority.getSize()); 

			Iterator it = cell.section.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();
				//System.out.println(pair.getKey()); 
			    priority.addItem(pair.getKey(), Integer.MAX_VALUE);

			}
			Map<String, Integer> priorityMap = priority.getMap(); 
			Map<String, Integer> itemMap = new HashMap(priorityMap.size()); 

			Iterator iterator = priorityMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry <String,Integer> pear = (Map.Entry<String,Integer>)iterator.next();
			    itemMap.put(pear.getKey(), pear.getValue());

			}

			int ind = itemMap.get(source); 
			parents[ind] = null; 
			priority.changePriority(source, 0);

			
			Integer currPri = 0; 
			while (priority.getSize() > 0) {
				String curr = priority.peekTop(); 
				//System.out.println("currstring = " + curr);
				currPri = new Integer(priority.getPriority(curr));
				//System.out.println("CURRPRI = " + currPri);
				
				Character vertTypeChar = curr.charAt(0);
				String currVertType = Character.toString(vertTypeChar);
				//System.out.println("currVertType = " + "'" + currVertType + "'" );
			
				if (curr != goal) { 

					AdjacentList adjList = cell.get(curr);
					//System.out.println("adjlist = " + adjList);
					
					for (int i = 0; i < adjList.size(); i++) {
						
						AbstractMap.SimpleEntry <String, Integer> entry = adjList.get(i);
						String adjCurr = entry.getKey();
						
						Character adjVertTypeChar = adjCurr.charAt(0);
						String adjVertType = Character.toString(adjVertTypeChar);
						//System.out.println("adjVertType = " + "'" + adjVertType + "'" );
						//System.out.println("adjvert = "  + adjCurr);

						Character rowChar = adjCurr.charAt(1);
						String row = Character.toString(rowChar);
						int rowInt = Integer.parseInt(row);
						//System.out.println("rowInt = " + rowInt);

						Character colChar = adjCurr.charAt(3);
						String col = Character.toString(colChar);
						int colInt = Integer.parseInt(col);
						//System.out.println("colInt = " + colInt);
					
						if (!visited.containsKey(adjCurr))  {
							
							int dist = entry.getValue();
							Integer distInt = new Integer(dist);
							Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));
							//System.out.println("pricurradjbefore = " + adjCurrPri);
							
							if (adjCurrPri > currPri + distInt) {
								adjCurrPri = currPri + distInt;
								int newPri = adjCurrPri.intValue(); 
								priority.changePriority(adjCurr, newPri); 
								parents[itemMap.get(adjCurr)] = curr; 
							}
						}
					}
				}
				else {
					break; 
				}
				visited.put(priority.removeItem(), currPri);
				//System.out.println(visited);
				//System.out.println("visited-size = " + visited.size());
				//System.out.println("priority-size = " + priority.getSize()); 
			}
			visited.put(priority.removeItem(), currPri);
			//System.out.println(visited);
			//System.out.println("PRINTING THE SHORTEST PATH");
			// A mapping of pairs of grid vertices in a given cell to the shortest path
			// that connects said vertices

			LinkedList<String> path = new LinkedList<String>(); 
		
			int shortest = visited.get(goal);
		
			while (goal != null) {
				//System.out.println(goal + " = " + visited.get(goal));
				path.addFirst(goal); 
				int goalIndex = itemMap.get(goal);
				goal = parents[goalIndex]; 
			}
			AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = new AbstractMap.SimpleEntry<LinkedList<String>, Integer>(path, shortest); 
			return shortestPath; 
	}

	public LinkedList<String> removeDuplicate (LinkedList<String> list) {
      LinkedList<String> copy = new LinkedList<String>();
		for (String s : list) {
	    	if (!copy.contains(s)) {
	        	copy.add(s);
	    	}
	    }
    return copy;
	} 

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
	    PriorityQueue<String,Integer> distances = new PriorityQueue<String,Integer>(); 

	    shortestMain.initializeGrid(h, w, k, distances); 
	    shortestMain.addWeights(br, h, w, k); 
	    //shortestMain.printGrid(shortestMain.masterGrid, h, w, k);

	    //System.out.println("Initializing gridPaths");

	    // Constructing the Grid Graph
		HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths = new HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>>();
		//ArrayList<String> gridVerts = new ArrayList<String>(); 
		
		String gridVert = ""; 
		int index1 = 0; 
		int index2 = 0;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				for (int di = 0; di <= 1; di++) {
					for (int dj = 0; dj <= 1; dj++) {
						gridVert = "g" + (i + di) + "." + (j + dj);
						//gridVerts.add(gridVert); 
						//shortestMain.addVertToCell2(gridPaths, gridVert);
						//System.out.println("ADDED VERT TO CELL : " + ); 
						index1 = i + di; 
						index2 = j + dj; 
						if (index1 == h) {index1 --;} 
						if (index2 == w) {index2 --;} 

						shortestMain.addWeights2(gridPaths, gridVert, h, w, k, index1, index2); 
						//System.out.println("ADDED VERT TO CELL : " + gridVert); 
					}
				}
			}
		}		

		// ADDING THE SOURCE AND GOAL VERTICES TO THE GRID GRAPH

		String[] spliced2 = new String [2];
		String source = "";
		String goal = ""; 
		
	    try {
			while (!(line = br.readLine()).equals("")) {
				//System.out.println("'" + line + "'");
				spliced2 = line.split("\\s+");
				source = spliced2[0];
				goal = spliced2[1];

				//System.out.println("source: " + source + " ---> " + "goal: " + goal);

				for (int q = 0; q < spliced2.length; q++) {

					String point = spliced2[q]; 

					Character rowCharPoint = point.charAt(1);
					String rowPoint = Character.toString(rowCharPoint);
					int rowIntPoint = Integer.parseInt(rowPoint);

					Character colCharPoint = point.charAt(3);
					String colPoint = Character.toString(colCharPoint);
					int colIntPoint = Integer.parseInt(colPoint);

					for (int r = 0; r <= 1; r++) {
						for (int s =0; s <=1; s++) {
							String adjGridPoint = ("g" + (rowIntPoint + r) + "." + (colIntPoint + s));
							shortestMain.addPath (gridPaths, point, adjGridPoint, h , w, k, rowIntPoint, colIntPoint);
							shortestMain.addPath (gridPaths, adjGridPoint, point, h , w, k, rowIntPoint, colIntPoint);
						}
					}
				}
			
				//System.out.println(gridPaths); //GRID PATHS
				//ArrayList<AbstractMap.SimpleEntry<String, String>> testList = new ArrayList <AbstractMap.SimpleEntry<String, String>> (); 
				//CONSTRUCTING THE REDUCTION GRAPH
				Cell reducedGrid = new Cell (); 		
				int gridSize = gridPaths.size(); 
				Iterator iterator = gridPaths.entrySet().iterator();
				//System.out.println("SIZE OF GRID PATHS : " + gridSize); 
				int count = 0; 
				
				while (iterator.hasNext()) {

					Map.Entry <AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> pair = (Map.Entry <AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>>)iterator.next();
				    
					AbstractMap.SimpleEntry<String, String> stringPair = pair.getKey(); 
					String start = stringPair.getKey(); 
					String end = stringPair.getValue();

					AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortPath = pair.getValue();
					LinkedList<String> thePath = shortPath.getKey(); 
					int dist = shortPath.getValue(); 

					//if (start.equals("g1.4")) {
					//	testList.add(stringPair);
					//}
					AbstractMap.SimpleEntry <String, Integer> adjacentVert = new AbstractMap.SimpleEntry<String, Integer>(end, dist); 

					if (reducedGrid.containsKey(start)) {
						reducedGrid.get(start).add(adjacentVert); 
					}
					else {
						AdjacentList adj = new AdjacentList();
						reducedGrid.put(start, adj); 
						reducedGrid.get(start).add(adjacentVert); 
					}
					count++; 
				}

				//System.out.println("PRINTING THE COUNT : " + count);
				//System.out.println("PRINTING REDUCED GRID WITH QUERY VERTS");
			    //printMap(reducedGrid); 
			    AbstractMap.SimpleEntry<LinkedList<String>, Integer> finalPath = shortestMain.dijkstra2(reducedGrid, source, goal, h, w, k);
			    LinkedList<String> reducedList = finalPath.getKey(); 
			    List<String> tempList = new ArrayList(reducedList);

			    LinkedList<String> finalList = new LinkedList<String> ();  

			    for (int x = 0; x < tempList.size() - 1; x++) {
			    	String one = tempList.get(x);
			    	String two = tempList.get(x+1);
			    	AbstractMap.SimpleEntry<String, String> tempPair = new AbstractMap.SimpleEntry<String, String>(one, two); 
			    	AbstractMap.SimpleEntry<LinkedList<String>, Integer> tempPath = gridPaths.get(tempPair);
			    	LinkedList<String> tempPathList = tempPath.getKey(); 
			    	for (String s : tempPathList) {
			    		finalList.add(s); 
			    	}
			    }
			    finalList = shortestMain.removeDuplicate(finalList);
			    int finalDist = finalPath.getValue(); 
			    System.out.println(finalDist + " " + finalList); 
			}
		}

		catch (IOException e) {
	    	System.out.println("Buffered reader error"); 
		}
	}	
}

//for (int t = 0; t < testList.size(); t++) {
		//}
		//System.out.println("PRINTING TEST LIST");
		//System.out.println(testList);
		//System.out.println("PRINTING REDUCED GRID WITHOUT QUERY VERTS");
		//printMap(reducedGrid); 

		// ADDING THE SOURCE AND GOAL VERTICES TO THE REDUCED GRAPH
		/*
		String[] spliced2 = new String [2];
		String source = "";
		String goal = ""; 
		
	    try {
			while (!(line = br.readLine()).equals("")) {
				//System.out.println("'" + line + "'");
				spliced2 = line.split("\\s+");
				source = spliced2[0];
				goal = spliced2[1];

				//System.out.println("source: " + source + " ---> " + "goal: " + goal);

				for (int q = 0; q < spliced2.length; q++) {

					String point = spliced2[q]; 

					Character rowCharSource = point.charAt(1);
					String rowSource = Character.toString(rowCharSource);
					int rowIntSource = Integer.parseInt(rowSource);

					Character colCharSource = point.charAt(3);
					String colSource = Character.toString(colCharSource);
					int colIntSource = Integer.parseInt(colSource);

					for (int r = 0; r <= 1; r++) {
						for (int s =0; s <=1; s++) {
							String adjGridPoint = ("g" + (rowIntSource + r) + "." + (colIntSource + s));
							AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = shortestMain.dijkstra(shortestMain.masterGrid, point, adjGridPoint, h, w, k, rowIntSource, colIntSource);
							
							int d = shortestPath.getValue(); 

							AbstractMap.SimpleEntry <String, Integer> edge = new AbstractMap.SimpleEntry<String, Integer>(adjGridPoint, d); 

							if (reducedGrid.section.containsKey(point)) {
								reducedGrid.get(point).add(edge); 
							}

							else {
								AdjacentList adj = new AdjacentList();
								reducedGrid.put(point, adj);
								reducedGrid.get(point).add(edge);  
							}
						}
					}
				}
				*/