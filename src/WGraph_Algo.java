package ex1.src;

import java.io.*;
import java.util.*;
import java.util.List;

//
public class WGraph_Algo implements weighted_graph_algorithms {

    //declare  the class private variables
    private weighted_graph g;  //private graph variable
    private HashMap<node_info, node_info> pred;  //private hashmap for contain the pred of each node of the shortest path

    //default constructor which don't get any values
    public WGraph_Algo() {
        this.g = null;
        this.pred = new HashMap<node_info, node_info>();
    }

    //function that gets graph and make a shallow copy to g
    @Override
    public void init(weighted_graph g) {
        this.g = g;
        this.pred = new HashMap<node_info, node_info>();
    }

    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    //function that return a deep copy of the graph
    @Override
    public weighted_graph copy() {
        if (this.g != null) {  //if g is not null
            return new WGraph_DS(this.g);  //return new graph with g values
        }
        return null;  //if g is null return null
    }

    //function the return true if the graph is connected (you can get to any node from every node)
    @Override
    public boolean isConnected() {
        if (this.g.nodeSize() > 1) {  //if there is more than 1 node in the graph
            if (!(this.g.nodeSize()-1 <= this.g.edgeSize())) {  //if there are more nodes then edges+1
                return false;  //return false
            }
            BFS();  //call the BFS function
            for (node_info n : this.g.getV()) {  //foreach loop to go over all the nodes in the graph
                if (n.getTag() == 0) {  //if there is node with tag = 0
                    return false;  //return false
                }
            }
            return true;  //if all the nodes with tag != 0 return true
        }
        return true;  //if there is less than 2 nodes in the graph return true
    }

    //function the gets 2 int and return the shortest dist between the two nodes with the given keys
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.g.getNode(src) != null && this.g.getNode(dest) != null){  //if the nodes are in the graph
            if (src == dest) {  //if the nodes are the same node
                return 0;
            }
            Dijkstra(src, dest);  //call the helping function dijkstra
            return this.g.getNode(dest).getTag();  //return the tag of the node with the dest key
        }
        return -1;  //if one of the nodes are not in the graph return -1
    }

    //function that gets 2 keys and return List of nodes which represent the shortestPath between the two nodes with the given key
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        ArrayList<node_info> path= new ArrayList<node_info>();  //create new arrayList
        if (this.g.getNode(src) != null && this.g.getNode(dest) != null) {  //if the nodes are in the graph
            path.add(this.g.getNode(dest));  //add the node to the arrayList
            if (src == dest) {  //if the nodes are the same
                return path;  //retrun the arrayList
            }
            else {
                Dijkstra(src, dest);  //call the helping function dijkstra
                if (!this.pred.containsKey(this.g.getNode(dest))){  //if pred is not contain the dest key
                    return null;  //return null
                }
                node_info predNode = this.pred.get(this.g.getNode(dest));  //node to represent the pred of the dest node
                while (this.pred.get(predNode) != this.g.getNode(src)){  //while loop until the pred node is our wanted src node
                    path.add(predNode);  //add the current node to the path
                    predNode = this.pred.get(predNode);  //set predNode to the pred of the current node
                }
                path.add(predNode);  //add the node to the path
                path.add(this.pred.get(predNode));  //add the pred of the current node to the path
                Collections.reverse(path);  //use reverse function to reverse the values of path
                return path;  //return path
            }
        }
        return null;  //if the nodes are not in the graph
    }

    //function that gets file name and save the file
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream file_out = new FileOutputStream(file);
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(this.g);
            obj_out.close();
            file_out.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //function that gets file name and load from that file
    @Override
    public boolean load(String file) {
        try {
            FileInputStream file_in = new FileInputStream(file);
            ObjectInputStream obj_in = new ObjectInputStream(file_in);
            this.g = (weighted_graph) obj_in.readObject();
            obj_in.close();
            file_in.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //helping function for isConnected function.
    //this function check if you can get to one node from any node in the graph
    private void BFS() {
        for (node_info n : this.g.getV()) {  //for loop to go over all the nodes in the graph
            n.setTag(0);  //set the tag to 0 cause we haven't visited them yet
        }
        Queue<node_info> q = new LinkedList<>();  //create new queue
        Iterator<node_info> iter = this.g.getV().iterator();  //use iterator to go over the collection
        q.add(iter.next());  //add to the q the first node in the collection
        q.peek().setTag(1);  //set this node to visited -> tag = 1
        while (!q.isEmpty()) {  //while q is not empty
            node_info node = q.poll();  //poll the node and save him into node variable
            for (node_info n : this.g.getV(node.getKey())) {  //go through his neighbors
                if (n.getTag() != 1) {  //if he haven't been there yet
                    q.add(n);  //add this node to the q
                    n.setTag(1);  //set him to visited
                }
            }
        }
    }

    //helping function to the shortestPath function. this function gets the shortestPath from src to each node and update the pred hashmap
    //the function stop if we found the node dest as we wanted
    private void Dijkstra(int src, int dist){

        PriorityQueue<WGraph_DS.NodeInfo> pQueue = new PriorityQueue<WGraph_DS.NodeInfo>(this.g.nodeSize(), new NodeInfoCompare());  //create priorityQueue
        this.pred.clear();  //clear the pred hashMap

        for (node_info node: this.g.getV()) {  //loop for to go through every node in the graph
            node.setTag(-1);  //set every node tag to -1
        }

        pQueue.add((WGraph_DS.NodeInfo)this.g.getNode(src));  //add to the PQ the src node
        this.g.getNode(src).setTag(0);  //set the tag of the src node to 0

        while (!pQueue.isEmpty()){  //while out PQ is not empty
            WGraph_DS.NodeInfo n = pQueue.poll();  //poll from the pq the node at the head
            for (node_info neighbor : n.getNi()) {  //go through every neighbor of this node
                if (neighbor.getTag()==-1){  //if this neighbor's tag is -1 (first time we visit him)
                    neighbor.setTag(n.getTag() + n.getWeight(neighbor.getKey()));  //set his tag to his pred tag + the weight of the edge between those 2
                    pQueue.remove(neighbor);  //remove the neighbor from the pq
                    pQueue.add((WGraph_DS.NodeInfo)neighbor);  //add the neighbor to the pq after we updated him
                    pred.put(neighbor, n);  //update the neighbor in the pred hashMap
                }
                else{  //if his tag isn't -1 (we already visited him)
                    if (neighbor.getTag() > n.getTag() + n.getWeight(neighbor.getKey())){  //if the neighbor's tag is bigger the the tag of him pred and the weight of the edge
                        neighbor.setTag(n.getTag() + n.getWeight(neighbor.getKey()));  //update the tag to the smallest value
                        pQueue.remove(neighbor);  //remove the neighbor from the pq
                        pQueue.add((WGraph_DS.NodeInfo)neighbor);  //add the neighbor to the pq after we updated him
                        pred.put(neighbor, n);  //update the neighbor in the pred hashMap
                    }
                }
            }
        }
    }
}








