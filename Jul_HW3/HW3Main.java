/**
 * This is the HW3, implementing karger's randomized min cut of undirected graph.<br />
 * We keep a list of nodes in graph class, for every node, it has adjacent nodes as neighbors<br />
 * The answer I submit is 17. Passed. <br />
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Antonio081014
 * @since Jul 20, 2013, 1:33:01 PM
 */
public class HW3Main {

  private static final int size = 200;

	public static void main(String[] args) throws Exception {
		HW3Main main = new HW3Main();
		int times = 10;
		while (times-- > 0) {
			// long start = System.currentTimeMillis();
			main.run();
			// System.out.println(System.currentTimeMillis() - start);
		}
		System.exit(0);
	}

	private void run() throws Exception {
		Graph graph = new Graph(size);
		// try {
		BufferedReader in = new BufferedReader(new FileReader(
				"kargerMinCut.txt"));
		for (int i = 0; i <= size; i++) {
			graph.addNode(new Node(i));
		}
		for (int i = 0; i < size; i++) {
			String[] line = in.readLine().split("\\s");
			int label = Integer.parseInt(line[0]);
			Node nodesrc = graph.getVertices().get(label);
			for (int j = 1; j < line.length; j++) {
				Node nodedst = graph.getVertices().get(
						Integer.parseInt(line[j]));
				graph.addAdjasent(nodesrc, nodedst);
			}
		}
		in.close();
		System.out.println("Cuts: " + graph.min_cut());
		// } catch (Exception e) {
		// }
	}

}

class Graph {
	// List of nodes in the graph;
	private ArrayList<Node> vertices;
	// The size of Graph;
	private int size;

	public Graph(int N) {
		vertices = new ArrayList<Node>(N + 1);
		this.size = N;
	}

	public void addNode(Node node) {
		int label = node.label;
		vertices.add(label, node);
	}

	public void addAdjasent(Node src, Node dst) {
		int label = src.label;
		vertices.get(label).adjacents.add(dst);
	}

	/**
	 * Get the minimum cut of undirected graph. <br />
	 * In the while loop:<br />
	 * 1st, Randomly select one node from node list; <br />
	 * 2nd, Randomly select a neighbor from the selected node's neighbor list; <br />
	 * 3rd, merge these connected two nodes into one.<br />
	 * Until there are only 2 valid nodes left.<br />
	 * 
	 * @return how many connections exist between two nodes left.
	 * */
	public int min_cut() {
		int count = 0;
		int n = size;
		while (n > 2) {
			int srcLabel = (int) (Math.random() * n) + 1;
			int dstLabel = (int) (Math.random() * vertices.get(srcLabel).adjacents
					.size());
			Node nodeA = vertices.get(srcLabel);
			merge(nodeA, nodeA.adjacents.get(dstLabel));
			n--;
		}
		// System.out.println(vertices.size());
		Node nodesrc = vertices.get(1);
		Node nodedst = vertices.get(2);
		for (Node node : nodesrc.adjacents) {
			if (nodedst.label == node.label)
				count++;
		}
		return count;
	}

	/**
	 * Key Step: <br />
	 * When two nodes selected randomized, we need to merge NodeB into NodeA;<br />
	 * 1st, remove link from A to B or B to A; <br />
	 * 2nd, add all the edges out from B to A; no matter if a neighbor is
	 * already in A's neighbor list, which will be just another cut in purpose. <br />
	 * 3rd, update other nodes; <br />
	 * */
	public void merge(Node nodeA, Node nodeB) {
		// System.out.println(nodeA + ", " + nodeB);
		nodeA.adjacents = removeAll(nodeA.adjacents, nodeB);
		nodeB.adjacents = removeAll(nodeB.adjacents, nodeA);
		for (int i = 0; i < nodeB.adjacents.size(); i++) {
			Node node2B = nodeB.adjacents.get(i);
			nodeA.adjacents.add(node2B);
			node2B.adjacents.add(nodeA);
			node2B.adjacents = removeAll(node2B.adjacents, nodeB);
		}
		vertices.remove(nodeB);
		// print();
	}

	/**
	 * Remove all edges from connect to Node @param dst;<br />
	 * This could avoid merge a node with itself.
	 * 
	 * @return ArrayList<Node>
	 * */
	private ArrayList<Node> removeAll(ArrayList<Node> list, Node dst) {
		ArrayList<Node> ret = new ArrayList<Node>();
		for (Node node : list) {
			if (node != dst)
				ret.add(node);
		}
		return ret;
	}

	/**
	 * Print Graph;
	 */
	public void print() {
		for (Node node : vertices) {
			System.out.print(node.label);
			for (Node neighbor : node.adjacents) {
				System.out.print(" " + neighbor.label);
			}
			System.out.println();
		}
	}

	public ArrayList<Node> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Node> vertices) {
		this.vertices = vertices;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}

class Node implements Comparable<Node> {
	// The number of node.
	public int label;
	// All of the neighbor nodes for this current node.
	public ArrayList<Node> adjacents;

	public Node(int label) {
		this.label = label;
		this.adjacents = new ArrayList<Node>();
	}

	// Has not been used.
	@Override
	public int compareTo(Node a) {
		return this.label - a.label;
	}

	// Be used to check if two nodes are equal, when we try to remove all
	// duplicate nodes.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + label;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (label != other.label)
			return false;
		return true;
	}

	// Print nodes for debugging purpose.
	public String toString() {
		return "Node: (" + label + ").";
	}
}
