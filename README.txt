A Java program that computes shortest (least total weight) paths in a weighted, undirected graph. The graph can be broken down into a 2-D grid of cells, where each cell has 4 special "corner" vertices shared with its neighbors, and all of the edges are between two vertices in the same cell or between a non-corner vertex in a cell and one of the corner vertices of the same cell.

Input must be read from standard input where the first line of input is three integers giving the width and height of the grid, the rest of the input is divided into two sections separated by the word "queries" on a separate line, where the first part contains information about the edges, one edge per line in the format "endpoint endpoint weight"; and the second part contains the endpoints of shortest paths we wish to compute, one query per line in the format "source destination"; The output is written to standard output in the format "weight path" where "weight" is the total weight of a shortest (least total weight) path from the source to the destination, and "path" is the list of vertices on that path given by name in order from source to destination.

ASSUMPTIONS MADE - 

1. The weights are non-negative integers,
2. The names of the vertices are in the form "gi.j" for the corner vertex in the top left of the grid cell in row i, column j (0=i=h0=i=h and 0=j=w0=j=w) and "v.i.j.k" for the kth vertex in cell (i, j), where 0=k<n0=k<n,
each corner vertex has an edge to a non-corner vertex in each cell it is adjacent to, 
3. No edge is listed in the input twice,
4. Each cell is connected,
5. The graph is connected 
6. The source and destination vertices for shortest path queries exist and are non-corner vertices in different cells.

It is possible (even probable) that there will be more than one shortest path for the sample inputs. Only one of the shortest paths will be outputted as all of them have the same total weight.

O(k3logk+p)O(k3logk+p) 
O(k2logk+p)O(k2logk+p) solutions earn up to full credit

INCLUDED IN THIS REPOSITORY - 

1. 4 input files (.in) that correspond with 4 output files (.out) that serve merely as examples 

2. MakeRandomGraph.java - creates an input file appropriate to for ShortestPathsMain.java. Specify the desired width (w - 1st command line arg) and height (h - 2nd command line arg) of the master grid in terms of cells and the number of desired vertices per cell (k - 3rd command line argument) EXAMPLE -  

java MakeRandomGraph 2 2 4 

3. ShortestPathsMain - The class which contains the main method that executes the shortest path algorithm. Specify the name of an input file as the first command line argument. EXAMPLE - 

java ShortestPathsMain random_2_2_4.in

4. PriorityQueue.java - A priority written by Professor Jim Glenn of Amherst College which Dijkstra's algorithm relies on to determine the next best vertex to visit to ensure traversing the shortest possible path at all times. 

5. AdjacentList.java - A simple data structure that associates a given vertex with all of the vertices that it can directly reach and the weights of said edges. 

6. Cell - Another simple data structure that groups individual vertices into a matrix by the row and column designations of their names. 




EXAMPLE

--------------------------INPUT--------------------------

2 2 4
g0.1 v0.0.3 4
g0.1 v0.0.1 8
g1.0 v0.0.2 0
g0.0 v0.0.1 2
g0.0 v0.0.0 4
g1.1 v0.0.3 8
g1.1 v0.0.2 5
v0.0.1 v0.0.3 4
g1.1 v0.0.1 9
v0.0.1 v0.0.2 1
v0.0.0 v0.0.3 0
v0.0.0 v0.0.2 5
v0.0.0 v0.0.1 6
g1.2 v0.1.0 3
g0.2 v0.1.3 1
g0.2 v0.1.2 9
g0.2 v0.1.0 9
v0.1.2 v0.1.3 7
v0.1.1 v0.1.2 5
v0.1.1 v0.1.3 9
v0.1.0 v0.1.2 4
g1.1 v0.1.0 9
g0.1 v0.1.1 2
v0.1.0 v0.1.3 2
g1.0 v1.0.3 3
g1.1 v1.0.1 8
g1.0 v1.0.2 9
v1.0.0 v1.0.2 5
v1.0.0 v1.0.3 7
v1.0.0 v1.0.1 8
g2.0 v1.0.0 6
g2.0 v1.0.3 3
g2.0 v1.0.2 5
v1.0.1 v1.0.3 8
v1.0.1 v1.0.2 5
g2.1 v1.0.3 7
v1.1.0 v1.1.1 4
g1.2 v1.1.1 7
v1.1.0 v1.1.2 1
v1.1.0 v1.1.3 4
v1.1.1 v1.1.3 1
g2.2 v1.1.0 5
g2.2 v1.1.2 8
g1.1 v1.1.0 8
g2.1 v1.1.2 6
g1.1 v1.1.1 2
v1.1.2 v1.1.3 1
queries
v1.1.3 v0.1.2
v0.0.0 v1.0.1

--------------------------OUTPUT-------------------------- 

15 [v1.1.3, v1.1.1, g1.2, v0.1.0, v0.1.2]
16 [v0.0.0, v0.0.2, g1.0, v1.0.3, v1.0.1]




