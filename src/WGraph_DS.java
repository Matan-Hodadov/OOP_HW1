package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

//class which represent graph in this assignment
public class WGraph_DS implements weighted_graph, Serializable {

    //declare  the class private variables
    private HashMap<Integer, node_info> g;  //represent the nodes in the graph
    private int edges;  //represent the number of the edges in the graph
    private int mc;  //represent the number of chances in the graph
    private HashSet<Integer> keys;

    //constructor which gets weighted_graph
    public WGraph_DS(weighted_graph graph) {
        WGraph_DS g_ds = (WGraph_DS) graph;
        this.g = new HashMap<>(g_ds.g);
        this.keys = new HashSet<>(g_ds.keys);
        this.edges = graph.edgeSize();
        this.mc = graph.getMC();
    }

    //construcor which isn't getting any variables
    public WGraph_DS() {
        this.g = new HashMap<Integer, node_info>();
        this.keys = new HashSet<Integer>();
        this.edges = 0;
        this.mc = 0;
    }

    //function that gets key and return the node with the given key
    @Override
    public node_info getNode(int key) {
        return this.g.get(key);
    }

    //function that gets 2 integers and retunn true is they are connected. else return false
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (this.g.containsKey(node1) && this.g.containsKey(node2)){  //if nodes are in the graph
            if(node1==node2){  //if this is the same node
                return true;  //if this is the same node return true
            }
            NodeInfo n1 = (NodeInfo) this.g.get(node1);  //create n1 which is the node it the given key node1
            return n1.hasNi(node2);  //return if node2 is in the node1's neighbors
        }
        return false;  //if one of the nodes is not in the graph return false
    }

    //function that gets 2 integers and return the weight of the edge between those 2 nodes with the given keys
    @Override
    public double getEdge(int node1, int node2) {
        if (this.g.get(node1) != null && this.g.get(node2) != null) {  //if the nodes are in the graph
            if (node1==node2){  //if this is the same node
                return 0;  //return 0
            }
            WGraph_DS.NodeInfo n1 = (NodeInfo) this.g.get(node1);  //create node n1 which is the node with the first given key
            if ((n1.neighbors.containsKey(node2))) {  //if the node2 is in n1's neighbors
                return n1.weightEdge.get(node2);  //return the weight of the edge with between node1 and node2
            }
        }
        return -1;  //if one of the nodes are not in the graph return -1
    }

    //function that add node with the given key to the graph
    @Override
    public void addNode(int key) {
        if (keys.contains(key)){  //if the node with the given key is already in the graph
            return;  //stop
        }
        NodeInfo n = new NodeInfo(key, "", 0);  //if the node is not in the graph create new node with the given key
        this.g.put(key, n);  //put the node in the node's hashmap
        this.mc++;  //increase the number of changes in the graph
    }

    //function that gets 2 keys and a weight and connect the nodes with the given keys with the given weight
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2){  //if those are different nodes
            NodeInfo n1 = (NodeInfo)this.g.get(node1);  //the node with node1 key
            NodeInfo n2 = (NodeInfo)this.g.get(node2);  //the node with node2 key
            if (n1 != null && n2 != null) {  //if each node is not null
                if (n1.hasNi(node2)){  //if they are already connected
                    n1.addNi(n2, w);  //add node2 to node1's neighbors
                    n2.addNi(n1, w);  //add node1 to node2's neighbors
                    this.mc++;  //increase the number of changes in the graph
                }
                else{  //if they are not already connected
                    n1.addNi(n2, w);  //add node2 to node1's neighbors
                    n2.addNi(n1, w);  //add node1 to node2's neighbors
                    this.edges++;  //increase the number of edges by 1
                    this.mc++;  //increase the number of changes in the graph
                }
            }
        }
    }

    //function which return a collection which is the nodes that are in the graph
    @Override
    public Collection<node_info> getV() {
        return this.g.values();  //return the values of this hashMap
    }

    //function gets integer and return collection which is the nodes neighbors of the node with the given key in the graph
    @Override
    public Collection<node_info> getV(int node_id) {
        ArrayList<node_info> neighbors = new ArrayList<node_info>();  //create new array
        if (this.g.containsKey(node_id)){  //if the node is in the graph
            neighbors.addAll(((NodeInfo)this.g.get(node_id)).getNi());  //use addAll function in ArrayList to add all the neighbors of the node with the given key
        }
        return neighbors;   //if the node is not in the graph return empty collection
    }

    //funcion that gets key and remove and return the node with the given key for the graph
    @Override
    public node_info removeNode(int key) {
        if (this.g.containsKey(key)) {  //if this node is in the graph
            NodeInfo n = (NodeInfo) this.g.get(key);  //new variable that hold the wanted node
            for (node_info neighbor : n.getNi()) {  //go through all the node neighbors
                ((NodeInfo)neighbor).removeNode(n);  //remove the node from his neighbors
                this.edges--;  //decrease the number of edges by 1
                this.mc++;  //increase the number of changes in the graph
            }
            this.g.remove(key);  //remove the node from the graph
            this.mc++;  //increase the number of changes
            return n;  //return the removed node
        }
        return null;  //if the node is not in the graph return null
    }

    //function that gets 2 keys and remove the edge between the 2 nodes with the given keys
    @Override
    public void removeEdge(int node1, int node2) {
        if (this.g.containsKey(node1) && this.g.containsKey(node2)){  //if both nodes are in the graph
            NodeInfo n1 = (NodeInfo) this.g.get(node1);  //get the node with the first given key
            NodeInfo n2 = (NodeInfo) this.g.get(node2);  //get the node with the second given key
            if (n1.neighbors.containsKey(node2)) {  //if the nodes are connected
                n1.removeNode(n2);  //remove node2 from node1
                n2.removeNode(n1);  //remove node1 from node2
                edges--;  //decrease te amount of edges
                mc++;  //increase the amount of changes
            }
        }
    }

    //function thats return the anoumt of nodes in the graph
    @Override
    public int nodeSize() {
        return this.g.size();  //return the size of g
    }

    //function that return the number of edges in the graph
    @Override
    public int edgeSize() {
        return edges;
    }

    //function that return the amount of changes in the graph
    @Override
    public int getMC() {
        return this.mc;
    }

    //equals function for this class. override the equals function in object
    @Override
    public boolean equals(Object obj) {
        WGraph_DS graph = (WGraph_DS) obj;  //cast the obj to WGraph_DS
        if (this.edges != graph.edges || this.nodeSize() != graph.nodeSize()){  //quick check if edges and nodes sizes are not the same
            return false;  //return false
        }
        if (this.nodeSize() == 0){  //if both have no nodes
            return true;  //return true
        }
        for (node_info node : this.g.values()) {  //go through each node in the this g. first direction to see if they are equals
            if (graph.getNode(node.getKey()) == null){  //if this node is not in the other graph
                return false;  //return false
            }
            if (!(((NodeInfo)graph.getNode(node.getKey())).equals(node))){  //use the node_info equals function to check if both nodes are equals
                return false;  //if they are not equals return false
            }
        }
        for (node_info node : graph.g.values()) {  //go through each node in the other graph. second direction to see if they are equals
            if (this.getNode(node.getKey()) == null){  //if the node is not in this graph
                return false;  //return false
            }
            if (!(((NodeInfo)this.getNode(node.getKey())).equals(node))){  //use the node_info equals function to check if both nodes are equals
                return false;  //return false
            }
        }
        return true;  //if we got here then all good with the graphs and therefor they are equals
    }

    //hidden class which represent node in the graph
    public class NodeInfo implements node_info, Serializable{

        //private variables and static variable count
        private int key;  //represent the unique key for each every node
        private String data;  //represent the data which every node holds
        private HashMap<Integer, node_info> neighbors;  //represent the connect nodes to this node
        private HashMap<Integer, Double> weightEdge;  //represent the weighted edge to each node
        private double tag;  //helping int variable to see if we visited in the node

        //copy constructor which gets key data and tag
        public NodeInfo(int key, String data, double tag) {
            this.key = key;
            this.data = data;
            this.neighbors = new HashMap<Integer, node_info>();
            this.weightEdge = new HashMap<Integer, Double>();
            this.tag = tag;
        }

        //function which return this node's key
        @Override
        public int getKey() {
            return this.key;
        }

        //function which return this node's data
        @Override
        public String getInfo() {
            return this.data;
        }

        //funcion that gets string and set the node's data to this string
        @Override
        public void setInfo(String s) {
            this.data = s;
        }

        //function that return this node's tag
        @Override
        public double getTag() {
            return this.tag;
        }

        //function that gets double and set this node's tag to the given double
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        //function that gets key and return true if there is a node with the given key in this node's neighbors
        public boolean hasNi(int key) {
            if(!this.neighbors.isEmpty()){  //if neighbors is not empty
                return this.neighbors.containsKey(key);  //return if the node is in the neighbors hashMap
            }
            return false;  //if neighbors is empty return false
        }

        //function that gets node_indo and double and add the given node to this node's neighbors with the given edge weight
        public void addNi(node_info t, double w) {
            neighbors.put(t.getKey(), t);  //use put function to add/update node_data t to neighbors
            weightEdge.put((t.getKey()), w);  //use put function to add/update node_data t to weightEdge
        }

        //function the return the nodes that are connected to this node
        public Collection<node_info> getNi(){
            return this.neighbors.values();
        }

        //function that gets key and return the weight of the edge of this node and the node with the given key
        public double getWeight(int key){
            return this.weightEdge.get(key);
        }

        //function the gets node and remove him from this node neighbors
        public void removeNode(node_info node){
            if (this.neighbors.containsValue(node)){  //if neighbors contain the given node
                this.neighbors.remove(node.getKey());  //remove him
                this.weightEdge.remove(node.getKey());  //remove the edge with this node from the hashmap
            }
        }

        //equals function to override the equals function of the super class objects
        @Override
        public boolean equals(Object obj){
            NodeInfo node = (NodeInfo)obj;  //cast the obj to nodeInfo
            if (node.key != this.key){  //if their keys are different
                return false;  //return false
            }
            for (node_info neighbor : node.neighbors.values()) {  //foreach loop to o through the neighbors hashMap of the other graph
                if (!(this.hasNi(neighbor.getKey()))){  //if this neighbor is not in the neighbors' hashmap of this graph
                    return false;  //return false
                }
                if (this.getWeight(neighbor.getKey()) != node.getWeight(neighbor.getKey())){  //if the weight of the edge between the nodes and the neighbor is the same
                    return false;  //return false
                }
            }
            return true;  //if we got here then all good with the nodes and therefor they are equals
        }
    }
}

