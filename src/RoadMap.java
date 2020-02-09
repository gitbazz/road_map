
/**
 * This class represents a road map.
 * 
 * @author Bazillah Zargar
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Stack;

public class RoadMap {

	private Graph graph;
	private int startingNode;
	private int destinationNode;
	private int width;
	private int length;
	private int initialBudget;
	private int toll;
	private int gain;
	private Stack<Node> stack;

	/**
	 * Consructor for the class. Builds a graph fro the input file specified in the
	 * parameter. This graph represents the road map.
	 * 
	 * @param inputFile a text file that represents the road map.
	 * @throws MapException if the input file does not exist
	 */
	public RoadMap(String inputFile) throws MapException {
		this.stack = new Stack<Node>();
		int horizontalCount = 0;
		int verticalCount = 0;

		try {
			BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			// ignore scale factor
			file.readLine();
			// store information from input file
			this.startingNode = Integer.valueOf(file.readLine());
			this.destinationNode = Integer.valueOf(file.readLine());
			this.width = Integer.valueOf(file.readLine());
			this.length = Integer.valueOf(file.readLine());
			this.initialBudget = Integer.valueOf(file.readLine());
			this.toll = Integer.valueOf(file.readLine());
			this.gain = Integer.valueOf(file.readLine());

			// Initialize a graph of the size indicated in the file
			int size = width * length;
			this.graph = new Graph(size);

			for (int i = 0; i < 2 * length - 1; i++) {
				String line = file.readLine();
				char character;
				for (int j = 0; j < 2 * width - 1; j++) {
					character = line.charAt(j);

					// if line number is even
					if (i % 2 == 0) {
						// Type of edge: 0 = F = public road, 1 = T = private/toll road, -1 = C = reward
						// road
						if (character == '+') {
							horizontalCount++;
						} else if (character == 'T') {
							graph.insertEdge(graph.getNode(horizontalCount - 1), graph.getNode(horizontalCount), 1);
						} else if (character == 'F') {
							graph.insertEdge(graph.getNode(horizontalCount - 1), graph.getNode(horizontalCount), 0);
						} else if (character == 'C') {
							graph.insertEdge(graph.getNode(horizontalCount - 1), graph.getNode(horizontalCount), -1);
						}
					}
					// if line number is odd
					else {
						// Type of edge: 0 = F = public road, 1 = T = private/toll road, -1 = C = reward
						// road
						if ((character == 'X') && (j % 2 == 1)) {
							verticalCount++;
						} else if (character == 'T') {
							graph.insertEdge(graph.getNode(verticalCount), graph.getNode(verticalCount + width), 1);
						} else if (character == 'F') {
							graph.insertEdge(graph.getNode(verticalCount), graph.getNode(verticalCount + width), 0);
						} else if (character == 'C') {
							graph.insertEdge(graph.getNode(verticalCount), graph.getNode(verticalCount + width), -1);
						}
						if (j == 2 * width - 2) {
							verticalCount++;
						}
					}

				}

			}
			file.close();
		} catch (FileNotFoundException e) {
			throw new MapException("File does not exist");
		} catch (IOException e) {
			throw new MapException("File does not exist");
		} catch (Exception e) {
			throw new MapException("Unable to create Road Map");
		}
	}

	/**
	 * Returns the graph representing the road map
	 * 
	 * @return graph representing road map
	 */
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * Returns the starting node contained in the input file
	 * 
	 * @return starting node
	 */
	public int getStartingNode() {
		return this.startingNode;
	}

	/**
	 * Returns the destination node contained in the input file
	 * 
	 * @return destination node
	 */
	public int getDestinationNode() {
		return this.destinationNode;
	}

	/**
	 * Returns the initial amount of money available to pay tolls
	 * 
	 * @return initial budget
	 */
	public int getInitialMoney() {
		return this.initialBudget;
	}

	/**
	 * Returns a Java iterator containing the nodes of a path from the start node to
	 * the destination node as specified if such a path exists. If the path does not
	 * exist, this method returns null
	 * 
	 * @param start        starting node for path
	 * @param destination  destination node for path
	 * @param initialMoney initial money available to pay tolls
	 * @return Java iterator containing the nodes of a path from the start node to
	 *         the destination node
	 */
	public Iterator findPath(int start, int destination, int initialMoney) {

		try {
			findPath(getGraph().getNode(start), getGraph().getNode(destination), initialMoney);

		} catch (GraphException e) {
			e.printStackTrace();
		}
		if (stack.empty()) {
			return null;
		}
		return stack.iterator();
	}

	/**
	 * Helper method for findPath(). Uses depth first search to find a path from the
	 * start node to the destination node as specified if such a path exists.
	 * 
	 * @param start        starting node for path
	 * @param destination  destination node for path
	 * @param initialMoney initial money available to pay tolls
	 * @return true is path exists between starting node and destination node, false
	 *         otherwise
	 */
	private boolean findPath(Node start, Node destination, int initialMoney) {
		int budget = initialMoney;
		int checkBudget;
		Node startNode = start;
		Node endNode = destination;

		stack.push(startNode);

		if (startNode == endNode) {
			return true;
		} else {
			startNode.setMark(true);

			try {
				Iterator<Edge> incidentEdges = getGraph().incidentEdges(startNode);

				while (incidentEdges.hasNext()) {
					Edge nextEdge = incidentEdges.next();
					// if the incident edge is a public road
					if (nextEdge.getType() == 0) {
						// Check if next node is a path that has already been visited
						if (nextEdge.secondEndpoint().getMark() == false) {
							if (findPath(nextEdge.secondEndpoint(), endNode, budget) == true) {
								return true;
							}
						}
					}
					// if incident edge is a reward road
					if (nextEdge.getType() == -1) {
						// Check if next node is a path that has already been visited
						if (nextEdge.secondEndpoint().getMark() == false) {
							checkBudget = budget + gain;
							if (checkBudget >= 0) {
								budget = checkBudget;
								if (findPath(nextEdge.secondEndpoint(), endNode, budget) == true) {
									return true;
								}
							}
						}

					}
					// If incident edge is a toll road
					if (nextEdge.getType() == 1) {
						// Check if next node is a path that has already been visited
						if (nextEdge.secondEndpoint().getMark() == false) {
							checkBudget = budget - toll;
							if (checkBudget >= 0) {
								budget = checkBudget;
								if (findPath(nextEdge.secondEndpoint(), endNode, budget) == true) {
									return true;
								}
							}
						}

					}

				}

				// If there no edge available to reach the next node, undo the changes that were
				// made to the budget
				Iterator<Edge> previousNode = getGraph().incidentEdges(startNode);
				while (previousNode.hasNext()) {
					Edge previous = previousNode.next();
					if (previous.getType() == -1) {
						budget = budget - gain;
						break;
					}

					if (previous.getType() == 1) {
						budget = budget + toll;
						break;
					}

				}

				// Unmark the current node
				startNode.setMark(false);
				stack.pop();

			} catch (GraphException e) {
				System.out.println(e.getMessage());
			}

		}
		return false;
	}

}
