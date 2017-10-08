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


// S set of all nodes that already have found shortest path
// Q all verts in graph

@author Tomal Hossain
@version 0.1 2015-11-23: k^3 algorithm
@version 0.2 2015-12-19: k^2 algorithm

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
		masterGrid.get(i).get(j).put(hashKey, adj);

	}


	public void addVertToCell2 (HashMap gridPaths, String hashKey) {

		AdjacentList adj = new AdjacentList();
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
					String theHeight = internal[0];

					// String and integer representations of vertex coordinates
					String heightChar =  "";
					for (int ab = 1; ab < theHeight.length(); ab++) {
						heightChar += Character.toString(theHeight.charAt(ab));
					}

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

		// String and Integer representations of the grid dimensions
		h --;
		w --;

		// String and integer representations of vertex coordinates

		String[] internal = gridVert.split("\\.");
		String widthChar = internal[1];
		String theHeight = internal[0];

		// String and integer representations of vertex coordinates
		String heightChar =  "";
		for (int ab = 1; ab < theHeight.length(); ab++) {
			heightChar += Character.toString(theHeight.charAt(ab));
		}

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
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);

					AbstractMap.SimpleEntry<String, String> vPair = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar));
					if (!gridPaths.containsKey(vPair)) {

						int one = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z + 1);
						int two = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z);
						if (one < two) {
							addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z + 1);
						}
						else {
							addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y, z);
						}
					}

					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
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
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);

					AbstractMap.SimpleEntry<String, String> vPair2 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar));
					if (!gridPaths.containsKey(vPair2)) {

						int one = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z + 1);
						int two = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
						if (one < two) {
							addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z + 1);
						}
						else {
							addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
						}
					}

					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
				}
			}
			else {
				if (widthCharIndex  == 0) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z);

					AbstractMap.SimpleEntry<String, String> vPair3 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)));
					if (!gridPaths.containsKey(vPair3)) {
						int one = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z);
						int two = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z);
						if (one < two) {
							addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z);
						}
						else {
							addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z);
						}
					}
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z);
				}
				else if (widthCharIndex  == w + 1) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);

					AbstractMap.SimpleEntry<String, String> vPair4 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)));
					if (!gridPaths.containsKey(vPair4)) {
						int one = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
						int two = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
						if (one < two) {
							addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
						}
						else {
							addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
						}
					}
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z);
				}
			}
		}
		// Adding inner grid points, each of which belongs to 4 cells
		else {
			//1
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
			//2
			AbstractMap.SimpleEntry<String, String> vPair5 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar));
			if (!gridPaths.containsKey(vPair5)) {
				int one = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
				int two = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z + 1);
				if (one < two) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z);
				}
				else {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + widthChar) , h, w, k, y, z + 1);
				}
			}
			//3
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex - 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);
			//4
			AbstractMap.SimpleEntry<String, String> vPair6 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)));
			if (!gridPaths.containsKey(vPair6)) {
				int three = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);
				int four = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z + 1);
				if (three < four) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y, z + 1);
				}
				else {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z + 1);
				}
			}
			//5
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex + 1)) , h, w, k, y + 1, z + 1);
			//6
			AbstractMap.SimpleEntry<String, String> vPair7 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar));
			if (!gridPaths.containsKey(vPair7)) {
				int five = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z + 1);
				int six = checkPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z);
				if (five < six) {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z + 1);
				}
				else {
					addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + widthChar) , h, w, k, y + 1, z);
				}
			}
			//7
			addPath(gridPaths, gridVert, ("g" + Integer.toString(heightCharIndex + 1) + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
			//8
			AbstractMap.SimpleEntry<String, String> vPair8 = new AbstractMap.SimpleEntry<String, String>(gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)));
			if (!gridPaths.containsKey(vPair8)) {
				int seven = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
				int eight = checkPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
				if (seven < eight) {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y, z);
				}
				else {
					addPath(gridPaths, gridVert, ("g" + heightChar + "." + Integer.toString(widthCharIndex - 1)) , h, w, k, y + 1, z);
				}
			}
		}
	}

	public void addPath (HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths, String source, String goal, int h , int w, int k, int y, int z) {
		AbstractMap.SimpleEntry<String, String> vertexPair = new AbstractMap.SimpleEntry<String, String>(source, goal);
		if (!gridPaths.containsKey(vertexPair)) {
			AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = this.dijkstra(this.masterGrid, source, goal, h, w, k, y, z);
			gridPaths.put(vertexPair, shortestPath);
		}
	}

	public int checkPath (HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths, String source, String goal, int h , int w, int k, int y, int z) {
		AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortestPath = this.dijkstra(this.masterGrid, source, goal, h, w, k, y, z);
		int shortDist = shortestPath.getValue();
		return shortDist;
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

			String [] parents = new String [k*3 + (h+1)*(w+1)];
			PriorityQueue<String,Integer> priority = new PriorityQueue<String,Integer>();

			Map<String, Integer> visited = new HashMap(priority.getSize());

			Cell cell = grid.get(y).get(z);
			Iterator it = cell.section.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();
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
			Integer currPri = Integer.MAX_VALUE;

			while (priority.getSize() > 0) {
				String curr = priority.peekTop();

				currPri = new Integer(priority.getPriority(curr));

				Character vertTypeChar = curr.charAt(0);
				String currVertType = Character.toString(vertTypeChar);

				visited.put(priority.removeItem(), currPri);
				if (!curr.equals(goal)) {

					AdjacentList adjList = cell.get(curr);

					for (int i = 0; i < adjList.size(); i++) {

						AbstractMap.SimpleEntry <String, Integer> entry = adjList.get(i);
						String adjCurr = entry.getKey();

						Character adjVertTypeChar = adjCurr.charAt(0);
						String adjVertType = Character.toString(adjVertTypeChar);

						String[] internal = adjCurr.split("\\.");
						String col = internal[1];
						String theRow = internal[0];
						String row =  "";
						// String and integer representations of vertex coordinates
						for (int bc = 1; bc < theRow.length(); bc++) {
							row += Character.toString(theRow.charAt(bc));
						}

						int rowInt = Integer.parseInt(row);
						int colInt = Integer.parseInt(col);

						if (!visited.containsKey(adjCurr))  {

							if (currVertType.equals("v")) {

								int dist = entry.getValue();
								Integer distInt = new Integer(dist);
								Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));

								if (adjCurrPri > currPri + distInt) {
									adjCurrPri = currPri + distInt;
									int newPri = adjCurrPri.intValue();
									priority.decreasePriority(adjCurr, newPri);
									parents[itemMap.get(adjCurr)] = curr;
								}
							}

							else if (currVertType.equals("g")) {

								if (adjVertType.equals("v") && (rowInt != y || colInt != z)) {
									continue;
								}
								else {
									int dist = entry.getValue();
									Integer distInt = new Integer(dist);
									Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));

									if (adjCurrPri > currPri + distInt) {
										adjCurrPri = currPri + distInt;
										int newPri = adjCurrPri.intValue();
										priority.changePriority(adjCurr, newPri);
										parents[itemMap.get(adjCurr)] = curr;
									}
								}
							}
						}
					}
				}
				else {
					break;
				}

			}

			// A mapping of pairs of grid vertices in a given cell to the shortest path
			// that connects said vertices

			LinkedList<String> path = new LinkedList<String>();
			int shortest = visited.get(goal);

			while (goal != null) {
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

			String [] parents = new String [k*3 + (h+1)*(w+1)];
			PriorityQueue<String,Integer> priority = new PriorityQueue<String,Integer>();

			Map<String, Integer> visited = new HashMap(priority.getSize());

			Iterator it = cell.section.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry <String, AdjacentList> pair = (Map.Entry<String, AdjacentList>)it.next();

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
				currPri = new Integer(priority.getPriority(curr));

				Character vertTypeChar = curr.charAt(0);
				String currVertType = Character.toString(vertTypeChar);

				visited.put(priority.removeItem(), currPri);
				if (!curr.equals(goal)) {

					AdjacentList adjList = cell.get(curr);

					for (int i = 0; i < adjList.size(); i++) {

						AbstractMap.SimpleEntry <String, Integer> entry = adjList.get(i);
						String adjCurr = entry.getKey();

						Character adjVertTypeChar = adjCurr.charAt(0);
						String adjVertType = Character.toString(adjVertTypeChar);

						String[] internal = adjCurr.split("\\.");
						String col = internal[1];
						String theRow = internal[0];
						String row =  "";
						// String and integer representations of vertex coordinates
						for (int bc = 1; bc < theRow.length(); bc++) {
							row += Character.toString(theRow.charAt(bc));
						}

						int rowInt = Integer.parseInt(row);
						int colInt = Integer.parseInt(col);

						if (!visited.containsKey(adjCurr))  {

							int dist = entry.getValue();
							Integer distInt = new Integer(dist);
							Integer adjCurrPri = new Integer(priority.getPriority(adjCurr));

							if (adjCurrPri > currPri + distInt) {
								adjCurrPri = currPri + distInt;
								int newPri = adjCurrPri.intValue();
								priority.decreasePriority(adjCurr, newPri);
								parents[itemMap.get(adjCurr)] = curr;
							}
						}
					}
				}
				else {
					break;
				}

			}

			// A mapping of pairs of grid vertices in a given cell to the shortest path
			// that connects said vertices

			LinkedList<String> path = new LinkedList<String>();

			int shortest = visited.get(goal);

			while (goal != null) {
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

	    // Constructing the Grid Graph
		HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> gridPaths = new HashMap<AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>>();

		String gridVert = "";
		int index1 = 0;
		int index2 = 0;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				for (int di = 0; di <= 1; di++) {
					for (int dj = 0; dj <= 1; dj++) {
						gridVert = "g" + (i + di) + "." + (j + dj);
						shortestMain.addWeights2(gridPaths, gridVert, h, w, k, i, j);
					}
				}
			}
		}

		// ADDING THE SOURCE AND GOAL VERTICES TO THE GRID GRAPH

		String[] spliced2 = new String [2];
		String source = "";
		String goal = "";

	    try {
			while ((line = br.readLine()) != null) {
				spliced2 = line.split("\\s+");
				source = spliced2[0];
				goal = spliced2[1];

				for (int q = 0; q < spliced2.length; q++) {

					String point = spliced2[q];

					String[] internal = point.split("\\.");
					String colPoint = internal[1];
					String theRow = internal[0];
					String rowPoint =  "";
					// String and integer representations of vertex coordinates
					for (int bc = 1; bc < theRow.length(); bc++) {
						rowPoint += Character.toString(theRow.charAt(bc));
					}

					int rowIntPoint = Integer.parseInt(rowPoint);
					int colIntPoint = Integer.parseInt(colPoint);

					for (int r = 0; r <= 1; r++) {
						for (int s =0; s <=1; s++) {
							String adjGridPoint = ("g" + (rowIntPoint + r) + "." + (colIntPoint + s));
							shortestMain.addPath (gridPaths, point, adjGridPoint, h , w, k, rowIntPoint, colIntPoint);
							shortestMain.addPath (gridPaths, adjGridPoint, point, h , w, k, rowIntPoint, colIntPoint);
						}
					}
				}

				//CONSTRUCTING THE REDUCTION GRAPH
				Cell reducedGrid = new Cell ();
				int gridSize = gridPaths.size();
				Iterator iterator = gridPaths.entrySet().iterator();
				int count = 0;

				while (iterator.hasNext()) {

					Map.Entry <AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>> pair = (Map.Entry <AbstractMap.SimpleEntry<String, String>, AbstractMap.SimpleEntry<LinkedList<String>, Integer>>)iterator.next();

					AbstractMap.SimpleEntry<String, String> stringPair = pair.getKey();
					String start = stringPair.getKey();
					String end = stringPair.getValue();

					AbstractMap.SimpleEntry<LinkedList<String>, Integer> shortPath = pair.getValue();
					LinkedList<String> thePath = shortPath.getKey();
					int dist = shortPath.getValue();

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
