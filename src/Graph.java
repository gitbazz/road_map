
/**
 * This class represents a graph object
 * 
 * @author Bazillah Zargar
 *
 */

import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT {

	private Node nodes[];
	private Edge edges[][]; // adjacency matrix storing all edges in the graph

	/**
	 * Constructor for the Graph object
	 * 
	 * @param n is the number of nodes contained in the graph
	 */
	public Graph(int n) {
		this.nodes = new Node[n];
		this.edges = new Edge[n][n];

		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
		}
	}

	/**
	 * Returns the node with the specified name. If no node with this name exists,
	 * it throws a GraphException
	 * 
	 * @param name is the name of the node
	 * @return the node with the specified name
	 */
	public Node getNode(int name) throws GraphException {
		if ((name >= 0) && (name < nodes.length)) {
			return nodes[name];
		} else {
			throw new GraphException("This node is not contained in the graph.");
		}
	}

	/**
	 * Adds an edge of the given type connecting nodeu and nodev. Throws a
	 * GraphException if either node does not exist or if in the graph there is
	 * already an edge connecting the given nodes
	 * 
	 * @param nodeu         is the first endpoint of the edge to be inserted
	 * @param nodev         is the second endpoint of the edge to be inserted
	 * @param nodedgeTypeeu is type type of the edge to be inserted
	 */
	public void insertEdge(Node nodeu, Node nodev, int edgeType) throws GraphException {
		int uName = nodeu.getName();
		int vName = nodev.getName();
		if ((uName >= 0) && (uName < nodes.length) && (vName >= 0) && (vName < nodes.length)) {
			if ((edges[uName][vName] == null)) {
				edges[uName][vName] = new Edge(nodeu, nodev, edgeType);
				edges[vName][uName] = new Edge(nodev, nodeu, edgeType);
			} else {
				throw new GraphException("An edge between these two nodes already exists");
			}
		} else {
			throw new GraphException("The node does not exist");
		}
	}

	/**
	 * Returns a Java Iterator storing all the edges incident on node u. Returns
	 * null if u does not have any edges incident on it. Throws a GraphException if
	 * either node does not exist
	 * 
	 * @param u is the node being checked for incident edges
	 * @return a Java iterator storing all incident edges.
	 */
	public Iterator incidentEdges(Node u) throws GraphException {
		int uName = u.getName();
		if ((uName >= 0) && (uName < nodes.length)) {
			Stack<Edge> s = new Stack<Edge>();
			for (int i = 0; i < nodes.length; i++) {
				if (edges[uName][i] != null) {
					s.push(edges[uName][i]);
				}
			}
			if (s.isEmpty()) {
				return null;
			} else {
				return s.iterator();
			}
		} else {
			throw new GraphException("The node does not exist");
		}

	}

	/**
	 * Returns the edge connecting nodes u and v. Throws a GraphException if there
	 * is no edge between u and v or if either node does not exist
	 * 
	 * @param u is a node in the graph
	 * @param v is another node in the graph
	 * @return the edge connecting nodes u and v.
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		int uName = u.getName();
		int vName = v.getName();
		if ((uName >= 0) && (uName < nodes.length) && (vName >= 0) && (vName < nodes.length)) {
			if (edges[uName][vName] == null) {
				throw new GraphException("There is no edge between these nodes");
			} else {
				return edges[uName][vName];
			}
		} else {
			throw new GraphException("The node does not exist");
		}

	}

	/**
	 * Returns true if nodes u and v are adjacent and false otherwise. Throws a
	 * GraphException if either node does not exist.
	 * 
	 * @param u is a node in the graph
	 * @param v is another node in the graph
	 * @return true if nodes are adjacent, false if nodes are not adjacent
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		int uName = u.getName();
		int vName = v.getName();
		if ((uName >= 0) && (uName < nodes.length) && (vName >= 0) && (vName < nodes.length)) {
			Edge edgeFound = edges[uName][vName];
			if (edgeFound == null) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new GraphException("The node does not exist");
		}
	}

}
