/**
 * This class represents a node object of a graph
 * 
 * @author Bazillah Zargar
 *
 */

public class Node {

	private int name; // Integer value between 0 and n-1 where n is the number of nodes in the graph
	private boolean mark; // Indicates whether the node has been visited

	// Constructor for the class
	public Node(int name) {
		this.name = name;
		this.mark = false;
	}

	// Marks the node with the specified value, either true or false
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	// Returns the value with which the node has been marked
	public boolean getMark() {
		return this.mark;
	}

	// Returns the name of the vertex
	public int getName() {
		return this.name;
	}

}
