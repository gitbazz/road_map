# Road Map

Finds a path between two specified points in a road map, if such a path exists. 

3 kinds of roads:
  - public roads that have no cost for using them
  - private roads that have a positive cost or toll for using them
  - reward roads that pay the user a certain amount of money for using them (as the user of such roads is required to perform a certain task, like cleaning the road).
  
Program receives as input a file with a description of the road map, the starting point s, the destination e, an amount of money k
that the program initially has for paying for the use of private roads, the cost of using each private road, and the amount gained by using a reward road. find a path from s to e that can be traversed with the given amount of money plus the money earned from using the reward roads.
Stores the road map as an undirected graph where every edge of the graph represents a road and every node represents either the intersection of two roads or the end of a road. Two special nodes in this graph denote the starting point s and the destination e.

**Node.java** class that represents a node of the graph.

**Edge.java** class that represents an edge of the graph.

**Graph.java** class that represents an undirected graph using an adjacency matrix.

**RoadMap.java** class that represents the road map.





