package flowNetwork;

//Java program for implementation of Ford Fulkerson algorithm 
import java.util.LinkedList;

class MaxFlow {
	private int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

	private int nodes = getRandomInteger(4, 10); // Number of vertices in graph

	/*
	 * Returns true if there is a path from source 's' to sink 't' in residual
	 * graph. Also fills parent[] to store the path
	 */
	private boolean bfs(int rGraph[][], int s, int t, int parent[]) {
		// Create a visited array and mark all vertices as not
		// visited
		boolean visited[] = new boolean[nodes];
		for (int i = 0; i < nodes; ++i)
			visited[i] = false;

		// Create a queue, enqueue source vertex and mark
		// source vertex as visited
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		visited[s] = true;
		parent[s] = -1;

		// Standard BFS Loop
		while (queue.size() != 0) {
			int u = queue.poll();

			for (int v = 0; v < nodes; v++) {
				if (visited[v] == false && rGraph[u][v] > 0) {
					queue.add(v);
					parent[v] = u;
					visited[v] = true;
				}
			}
		}

		// If we reached sink in BFS starting from source, then
		// return true, else false
		return (visited[t] == true);
	}

	// Returns the maximum flow from s to t in the given graph
	private int fordFulkerson(int graph[][], int s, int t) {
		int u, v;

		System.out.println("No of nodes in the graph : " + nodes);

		// Create a residual graph and fill the residual graph
		// with given capacities in the original graph as
		// residual capacities in residual graph

		// Residual graph where rGraph[i][j] indicates
		// residual capacity of edge from i to j (if there
		// is an edge. If rGraph[i][j] is 0, then there is
		// not)
		int rGraph[][] = new int[nodes][nodes];

		for (u = 0; u < nodes; u++)
			for (v = 0; v < nodes; v++)
				rGraph[u][v] = graph[u][v];
		// This array is filled by BFS and to store path
		int parent[] = new int[nodes];

		int max_flow = 0; // There is no flow initially

		// Augment the flow while there is path from source
		// to sink
		while (bfs(rGraph, s, t, parent)) {
			// Find minimum residual capacity of the edges
			// along the path filled by BFS. Or we can say
			// find the maximum flow through the path found.
			int path_flow = Integer.MAX_VALUE;
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				path_flow = Math.min(path_flow, rGraph[u][v]);
			}

			// update residual capacities of the edges and
			// reverse edges along the path
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				rGraph[u][v] -= path_flow;
				rGraph[v][u] += path_flow;
				System.out.println("v:" + v + "-->" + "u:" + u);
			}
			// Add path flow to overall flow
			max_flow += path_flow;
			System.out.println("capacity flowed in this path:" + path_flow + " " + " " + " ");

		}
		// Return the overall flow
		return max_flow;
	}

	private int[][] graph() {
		boolean check = false;
		if (nodes >= 4 && nodes <= 10) {
			check = true;
		}
		do {
			int capacities[][] = new int[nodes][nodes];
			for (int i = 0; i < capacities.length; i++) {
				for (int j = 0; j < capacities[i].length; j++) {
					if (i == j) {
						capacities[i][j] = 0;
					} else {
						capacities[i][j] = (getRandomInteger(5, 20));
					}
					System.out.print(capacities[i][j] + " ");
				}
				System.out.println();
			}
			return capacities;
		} while (check == true);

	}

	// main method to test above functions
	public static void main(String[] args) {
		MaxFlow algorithm = new MaxFlow();
		Stopwatch stopwatch = new Stopwatch();
		System.out.println("The maximum possible flow is " + algorithm.fordFulkerson(algorithm.graph(), 0, 5));
		double time = stopwatch.elapsedTime();
		System.out.println("Running time: " + time + " seconds");
	}
}