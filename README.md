NodeIndo: implements from node_data and Serializable.
	This class represent node in the graph.
	This is an hidden class inside WGraph_DS
	Variables:
	--Private variables:
		private int key
       		private String data
        	private HashMap<Integer, node_info> neighbors
        	private HashMap<Integer, Double> weightEdge
	        private double tag
	Functions:
	--This class implements each function from the interfce node_info.
	--Function not from the interface (helping functions):
		boolean hasNi(int key) - function that gets key and return true if there is a node with the given key in this node's neighbors
		addNi(node_info t, double w) - function that gets node_indo and double and add the given node to this node's neighbors with the given edge weight
		Collection<node_info> getNi - function the return the nodes that are connected to this node
		double getWeight(int key) - function that gets key and return the weight of the edge of this node and the node with the given key
		removeNode(node_info node) - function the gets node and remove him from this node neighbors
		boolean equals(Object obj) - equals function to override the equals function of the super class objects

WGraph_DS: implements from weighted_graph and Serializable.
	This class represent nweighted graph which contain several nodes
	Variables:
	--Private variables:
		private HashMap<Integer, node_info> g
    		private int edges
		private int mc
		private HashSet<Integer> keys
	Functions:
	--This class implements each function from the interface.
	
WGraph_Algo: implements from weighted_graph_algorithms.
	This class represent all the algorithms we can do on a graph.
	Variables:
	--Private variables:
		private weighted_graph g
    		private HashMap<node_info, node_info> pred
	Functions:
	--This class implements each function from the interfce.
	--Function not from the interface (helping functions):
		BFS - helping function for the isConnected function. this function check if you can get to one node from any node in the graph
		dijkstra - helping function to the shortestPath function. this function gets the shortestPath from src to each node and put it in the shortest_path hashmap















