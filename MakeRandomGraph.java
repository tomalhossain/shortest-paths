import java.util.*;

/**
 * Makes a random graph that is composed of a grid of m x n subgraphs,
 * each of which is connected at the corners.
 */

public class MakeRandomGraph
{
    public static void main(String[] args)
    {
	// read command line arguments
	if (args.length < 3)
	    {
		System.err.println("USAGE: java MakeRandomGraph w h k");
		System.err.println("\t where the grid size is h-by-w and k is the vertices in each grid cell");
		System.exit(1);
	    }

	int w = 0, h = 0, k = 0;
	try
	    {
		w = Integer.parseInt(args[0]);
		h = Integer.parseInt(args[1]);
		k = Integer.parseInt(args[2]);
	    }
	catch (NumberFormatException ex)
	    {
		System.err.println("All arguments must be integers");
		System.exit(0);
	    }

	if (w <= 0 || h <= 0 || k <= 0)
	    {
		System.err.println("All arguments must be positive");
		System.exit(1);
	    }

	if (w * h <= 1)
	    {
		System.err.println("Number of grid cells must be > 1");
		System.exit(1);
	    }

	System.out.println(w + " " + h + " " + k);

	for (int i = 0; i < h; i++)
	    {
		for (int j = 0; j < w; j++)
		    {
			// make random graph within grid square (i,j)
			List<String> verts = new ArrayList<String>();
			
			// make the corners 
			for (int di = 0; di <= 1; di++)
			    {
				for (int dj = 0; dj <= 1; dj++)
				    {
					verts.add("g" + (i + di) + "." + (j + dj));
				    }
			    }

			// make the rest
			for (int v = 0; v < k; v++)
			    {
				verts.add("v" + i + "." + j + "." + v);
			    }

			Map<String, Integer> edges = new HashMap<String, Integer>();

			// make random Hamiltonian path and add edges
			List<String> p1 = new ArrayList<String>(verts.subList(4, verts.size()));
			permutePath(p1);
			p1.add(0, "g" + i + "." + j);
			p1.add("g" + (i + 1) + "." + (j + 1));
			makePathRandomWeights(p1, 10, edges); // shouldn't hard-code the max weight

			// make another random Hamiltonian path and add edges
			List<String> p2 = new ArrayList<String>(verts.subList(4, verts.size()));
			permutePath(p2);
			p2.add(0, "g" + (i + 1) + "." + j);
			p2.add("g" + i + "." + (j + 1));
			makePathRandomWeights(p2, 10, edges);

			// make Theta(n) more random edges
			for (int e = 0; e < 2 * k; e++)
			    {
				// pick one random vertex...
				int v1 = (int)(Math.random() * verts.size());
				int v2;
				do
				    {
					// ...and another...
					v2 = (int)(Math.random() * verts.size());
				    }
				// .. try again if same vertex or corner to corner
				while (v1 == v2 || (v1 < 4 && v2 < 4));
				
				int weight = randomWeight(10); // hard-coding is easier but not better
				addEdge(verts.get(v1), verts.get(v2), weight, edges);
			    }

			printEdges(edges);
		    }
	    }

	// output k queries

	System.out.println("queries");

	for (int q = 0; q < k; q++)
	    {
		// randomly select two different cells
		int r1 = (int)(Math.random() * h);
		int c1 = (int)(Math.random() * w);

		int r2, c2;
		do
		    {
			r2 = (int)(Math.random() * h);
			c2 = (int)(Math.random() * w);
		    }
		while (r1 == r2 && c1 == c2);

		// randomly pick two vertices within cells
		int k1 = (int)(Math.random() * k);
		int k2 = (int)(Math.random() * k);

		// output the corresponding query
		System.out.println("v" + r1 + "." + c1 + "." + k1 + " v" + r2 + "." + c2 + "." + k2);
	    }
    }

    /**
     * Returns a random integer in 0, ..., max-1 
     *
     * @param max a positive integer
     */
    private static int randomWeight(int max)
    {
	return (int)(Math.random() * max);
    }

    /**
     * Adds the edge with given named endpoints and weight
     */
    private static void addEdge(String v1, String v2, int w, Map<String, Integer> edges)
    {
	// save the edge in canonical form -- endpoints given in lexicographic order
	if (v1.compareTo(v2) <= 0)
	    {
		edges.put(v1 + " " + v2, w);
	    }
	else
	    {
		addEdge(v2, v1, w, edges);
	    }
    }

    /**
     * Prints all edges along the given path with randomly chosen weights.
     */
    private static void makePathRandomWeights(List<String> p, int max, Map<String, Integer> edges)
    {
	String last = null;
	for (String v : p)
	    {
		if (last != null)
		    {
			int w = randomWeight(max);
			addEdge(last, v, w, edges);
		    }
		last = v;
	    }
    }

    /**
     * Randomly permutes the given path.
     */
    private static void permutePath(List<String> p)
    {
	for (int i = 0; i < p.size() - 1; i++)
	    {
		// swap p[i] with randomly chosen other vertex
		int j = i + (int)(Math.random() * (p.size() - i));
		String temp = p.get(j);
		p.set(j, p.get(i));
		p.set(i, temp);
	    }
    }

    /**
     * Writes to System.out all the edges in the given map.
     */
    private static void printEdges(Map<String, Integer> edges)
    {
	for (Map.Entry<String, Integer> e : edges.entrySet())
	    {
		System.out.println(e.getKey() + " " + e.getValue());
	    }
    }
}
