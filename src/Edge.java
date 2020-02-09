/**
 * This class represents an edge object of a graph
 * 
 * @author Bazillah Zargar
 *
 */
public class Edge {

	private Node firstEndpoint; // First endpoint of the edge
	private Node secondEndpoint; // Second endpoint of the edge
	private int type; // Type of edge: 0 = public road, 1 = private road, -1 = reward road

	// Constructor for the Edge object
	public Edge(Node u, Node v, int edgeType) {
		this.firstEndpoint = u;
		this.secondEndpoint = v;
		this.type = edgeType;
	}

	// Returns the first endpoint of the edge
	public Node firstEndpoint() {
		return this.firstEndpoint;
	}

	// Returns the second endpoint of the edge
	public Node secondEndpoint() {
		return this.secondEndpoint;
	}

	// Returns the type of the edge
	public int getType() {
		return type;
	}

}
