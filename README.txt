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

EXAMPLE IMPUT (for a graph with 4 vertices in each cell of a 2x2 grid) -  

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
Size	input	output
k=16 (4x4 grid, 16 interior vertices per cell)	random_4_4_16.in	random_4_4_16.out
k=64 (8x8 grid, 64 interior vertices per cell)	random_8_8_64.in	random_8_8_64.out
k=256 (16x16 grid, 256 interior vertices per cell)	random_16_16_256.in	random_16_16_256.out
It is possible (even probable) that there will be more than one shortest path for the sample inputs. You only need to output one of the shortest paths; it need not be the same one as in the sample output, although it should have the same total weight.

Files

PriorityQueue.java
MakeRandomGraph.java
Submissions

Submit your source code files (for example, .java files or .py files) along with a printout of your source code in PDF format (2 pages per sheet if possible). All files should be bundled into an archive (zip or gzip-ed tar) and submitted through the department's electronic submission system. You do not need to include any instructor-supplied files if you did not change them. Please name your file "cosc301_proj3_user.zip" where "user" is replaced by your Amherst username (and "zip" is replaced by "tgz" if you use gzip+tar).
Intellectual Responsibility

This project is intended to practice your ability to implement an algorithm carefully rather than your ability to search the Internet. You may not examine or use existing implementations of shortest paths algorithms or graph data structures while working on this project.
Grading

Grading will be based on correctness and efficiency on inputs containing kk vertices and T(k)T(k) edges in each cell of a kv×kvk×k grid, with T(k)T(k) shortest paths queries.
O(k3logk+p)O(k3log?k+p) solutions earn up to 90%
O(k2logk+p)O(k2log?k+p) solutions earn up to full credit
(pp is the total length in edges of all of the shortest paths reported).