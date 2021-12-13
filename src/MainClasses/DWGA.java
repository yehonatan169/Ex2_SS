package MainClasses;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DWGA implements DirectedWeightedGraphAlgorithms {
    private directeweightedgraph graph;
    private int NSize;

    /**
     * Inits the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = new directeweightedgraph((directeweightedgraph) g);
        this.NSize = this.graph.getMaxValueNodeIndex() + 1;
    }

    /**
     * Returns the underlying graph of which this class works.
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {
        directeweightedgraph copied = new directeweightedgraph();
        int nodeSize = this.graph.nodeSize();

        //copy nodes
        Iterator<NodeData> n = this.graph.nodeIter();
        NodeData node;
        while (n.hasNext()) {
            node = n.next();
            Vertex t = new Vertex(this.graph.getNode(node.getKey()).getKey(),
                    new geolocation(
                            this.graph.getNode(node.getKey()).getLocation().x(),
                            this.graph.getNode(node.getKey()).getLocation().y(),
                            this.graph.getNode(node.getKey()).getLocation().z()),
                    this.graph.getNode(node.getKey()).getWeight(),
                    this.graph.getNode(node.getKey()).getInfo(),
                    this.graph.getNode(node.getKey()).getTag());

            copied.addNode(t);
            if (node.getKey() > copied.getMaxValueNodeIndex()) {
                copied.setMaxValueNodeIndex(node.getKey());
            }
        }

        //copy edges
        Iterator<EdgeData> u = this.graph.edgeIter();
        EdgeData e;
        while (u.hasNext()) {

            e = u.next();
            copied.connect(e.getSrc(), e.getDest(), e.getWeight());
        }
        return copied;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.graph.myNode().isEmpty()) {
            return false;
        }
        HashMap<Integer, Vertex> original = this.graph.myNode();
        if (BFS(original, this.graph)) {
            directeweightedgraph g = new directeweightedgraph();
            for (NodeData t : this.graph.myNode().values()) {
                g.addNode(t);
            }
            for (HashIndex k : this.graph.allEdge().keySet()) {
                Iterator<EdgeData> it2 = this.graph.edgeIter(k.getSrc());
                while (it2.hasNext()) {
                    Edgedata e = (Edgedata) it2.next();
                    g.connect(e.getDest(), e.getSrc(), e.getWeight());
                }
            }
            if (BFS(original, g)) {
                return true;
            }
        }
        return false;
    }

    private boolean BFS(HashMap<Integer, Vertex> o, directeweightedgraph g) {
        Queue<NodeData> qn = new LinkedList<NodeData>();
        if (o.isEmpty()) {
            return false;
        }
        set_to_zero_all(g.myNode());
        Iterator<Vertex> n = o.values().iterator();
        NodeData start_node = n.next();
        start_node.setTag(1);//visited
        qn.add(start_node);
        while (!qn.isEmpty()) {
            NodeData out = qn.remove();
            Iterator<EdgeData> neighbors = g.edgeIter(out.getKey());
            if (neighbors == null) { // if there is no edge that means we can't go from him
                // to the others nodes which means the Graph isn't connected
                return false;
            }
            while (neighbors.hasNext()) {
                EdgeData e = neighbors.next();
                if (g.getNode(e.getDest()).getTag() == 0) {
                    qn.add(g.getNode(e.getDest()));
                    g.getNode(e.getDest()).setTag(1);
                }

            }
        }
        return check_visited(o);
    }

    static private void set_to_zero_all(HashMap<Integer, Vertex> hashnode) {
        for (NodeData nodeData : hashnode.values()) {
            nodeData.setTag(0);
        }
    }

    private static boolean check_visited(HashMap<Integer, Vertex> o) {
        for (NodeData nodeData : o.values()) {
            if (nodeData.getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        return distanceInit(src, dest, -1).getDistance();
    }

    public DistanceReturnedData getBothDistanceAndPath(int src, int dest) {
        return this.distanceInit(src, dest, -1);
    }

    private DistanceReturnedData distanceInit(int src, int dest, double key) {
        double[] allDists = new double[this.NSize];
        int[] indexesArray = new int[this.NSize];
        Iterator<NodeData> n = this.graph.nodeIter();
        NodeData node;

        //initiating nodes data for distance checks
        while (n.hasNext()) {
            node = n.next();
            this.graph.getNode(node.getKey()).setTag(1);
            allDists[node.getKey()] = Integer.MAX_VALUE;
            indexesArray[node.getKey()] = -1;
            indexesArray[src] = src;

        }
        allDists[src] = 0;
        return ShortestDistAid(src, src, dest, 0, allDists, indexesArray, key);
    }

    private DistanceReturnedData ShortestDistAid(int initial, int src, int dest, double dist3, double[] allDists, int[] indexesArray, double key) {

        // indexes enter base on distance to get to them from source
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((a, b) -> (int) (allDists[a] - allDists[b]));

        double tempMax = -1; // for center only
        int nonInf = graph.nodeSize();//for center only

        while (src != dest) {
            nonInf--; //this decrease means there is another node we did get to - means there is a path to it.

            graph.getNode(src).setTag(2);//every node we get - we set to mode 2, means we visited it
            //and will not visit it again

            Iterator<EdgeData> u = graph.edgeIter(src);

            while (u.hasNext()) {
                EdgeData current = u.next();
                double instantDist = current.getWeight();

                //these conditions are used solely for center
                if (key > 0 && allDists[src] >= key) {
                    return new DistanceReturnedData(key, allDists, indexesArray);
                    /*this conditions means for center, if for node x, there is a distance found that
                    is greater than previous node maximum possible distance -
                    it means that this node is definitely not the center node, thus we have no
                    need to further check the node, saving calculations and decreasing the running time.
                    * */

                } else if (key >= 0 && allDists[src] > tempMax && src != initial) {

                    tempMax = allDists[src];
                    /*
                    this condition set the stage for maximum possible distance for each node, and if at the end
                    it is the lowest of maximums, for next checks it will be the bar for other nodes center checks.
                     */
                }
                //

                if (instantDist + dist3 < allDists[current.getDest()] && graph.getNode(current.getDest()).getTag() == 1) {
                    allDists[current.getDest()] = current.getWeight() + dist3;
                    q.add(current.getDest());
                    indexesArray[current.getDest()] = src;
                    //if to a destination there is a new lower distance - update data


                }
            }
            int k = -1;

            //retrieve the next unused node with the lowest distance to get to
            if (q.size() > 0 && allDists[q.peek()] != Integer.MAX_VALUE) {
                while (q.size() > 0 && graph.getNode(q.peek()).getTag() == 2) {
                    q.poll();
                }
                if (q.size() > 0) {
                    k = q.poll();
                    dist3 = allDists[k];
                }

            }

            //if no possible next source was found - we can make no further calculations, thus returning the data
            //we already gathered
            if (k == -1) {
                if (key >= 0) {

                    if (tempMax == -1 || nonInf > 0) {
                        tempMax = Integer.MAX_VALUE;
                        //if nonInf is higher than 0, it means there is a node somewhere we couldn't get to,
                        //meaning the maximum possible distance from a source to any destination is infinity
                    }
                    return new DistanceReturnedData(tempMax, allDists, indexesArray);
                }
                DistanceReturnedData data = new DistanceReturnedData(-1, allDists, indexesArray);
                data.nullify();
                return data;
            }
            src = k;
        }


        int t = src;
        DistanceReturnedData data = new DistanceReturnedData(dist3, allDists, indexesArray);
        data.addNodeToList(0, graph.getNode(t));
        while (t != initial) {
            data.addNodeToList(0, graph.getNode(indexesArray[t]));
            t = indexesArray[t];
        }
        //collect route for path return
        return data;

    }

    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        DistanceReturnedData data = distanceInit(src, dest, -1);
        if (data.getPath() == null) {
            return null;
        }
        List<NodeData> givenP = data.getPath();
        return givenP;
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        DistanceReturnedData currentData;
        double k = 0;
        double currentMaxDistance = 0;
        int chosenInd = 0;
        DistanceReturnedData data;
        NodeData node;
        Iterator<NodeData> n = this.graph.nodeIter();
        int counter = 0;
        while (n.hasNext()) {
            node = n.next();
            currentData = distanceInit(node.getKey(), -1, k);
            currentMaxDistance = currentData.getDistance();
            if (node.getKey() == 0) {
                k = currentMaxDistance;
            } else if (node.getKey() > 0 && currentMaxDistance < k) {
                k = currentMaxDistance;
                chosenInd = node.getKey();
            }
        }
        if (k == Integer.MAX_VALUE) {
            return null;
        }
        return graph.getNode(chosenInd);
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        cities = removeDuplicates(cities);
        List<NodeData> cityChain = new LinkedList<NodeData>();
        cityChain.add(cities.get(0));
        cities.remove(0);

        double minimum = 0;
        double tempDistance = 0;
        int k = 0;
        // main loop for checking each city with the existing route which is being built.
        for (int i = 0; i < cities.size(); i++) {
            minimum = 0;
            k = 0;
           //inner loop to insert the checked city with every possible positions
            for (int j = 0; j <= cityChain.size(); j++) {

                tempDistance = 0;
                cityChain.add(j, cities.get(i));
                if (j == 0) {
                    for (int l = 0; l < cityChain.size() - 1; l++) {
                        double d = shortestPathDist(cityChain.get(l).getKey(), cityChain.get(l + 1).getKey());

                        if (d == -1) {
                            minimum = Integer.MAX_VALUE;
                            break;
                        }
                        minimum += d;
                    }
                } else {
                    for (int l = 0; l < cityChain.size() - 1; l++) {
                        double d = shortestPathDist(cityChain.get(l).getKey(), cityChain.get(l + 1).getKey());
                        if (d == -1) {
                            tempDistance = Integer.MAX_VALUE;
                            break;
                        }
                        tempDistance += d;
                    }
                    if (tempDistance < minimum) {
                        minimum = tempDistance;
                        k = j;
                    }
                }
                cityChain.remove(j);
            }
            if (minimum == Integer.MAX_VALUE) {
                return null;
            }
            cityChain.add(k, cities.get(i));
            List<NodeData> distChainA = null;

            //if in the path between city A and city B, there are extra cities, we can immediately insert them
            //to the output list, and removing them from the original list, saving time and calculations
            if (k > 0) {
                distChainA = shortestPath(cityChain.get(k - 1).getKey(), cityChain.get(k).getKey());
            }
            List<NodeData> distChainB = null;
            if (k < cityChain.size() - 1) {
                distChainB = shortestPath(cityChain.get(k).getKey(), cityChain.get(k + 1).getKey());
            }

            if (distChainA != null) {
                for (NodeData nodeData : distChainA) {
                    for (int n = 1; n < cities.size(); n++) {
                        if (nodeData.getKey() == cities.get(n).getKey()) {
                            cityChain.add(k, cities.get(n));
                            k++;
                            cities.remove(n);
                            n--;
                        }
                    }
                }
            }
            if (distChainB != null) {
                for (NodeData nodeData : distChainB) {
                    for (int n = 1; n < cities.size(); n++) {
                        if (nodeData.getKey() == cities.get(n).getKey()) {
                            cityChain.add(k + 1, cities.get(n));
                            k++;
                            cities.remove(n);
                            n--;
                        }
                    }
                }
            }
            //
            cities.remove(0);
            i--;
        }

        //in this loop we add to the output any non city nodes which is needed to make working route.
        List<NodeData> endResult = new LinkedList<NodeData>();
        for (int i = 0; i < cityChain.size() - 1; i++) {
            List<NodeData> t = shortestPath(cityChain.get(i).getKey(), cityChain.get(i + 1).getKey());
            for (int j = 0; j < t.size(); j++) {
                if (i == 0 || j > 0) {
                    endResult.add(t.get(j));
                }
            }
        }
        return endResult;
    }

    //if in the cities input for tsp there are duplicate nodes, this function removes any duplicates.
    private List<NodeData> removeDuplicates(List<NodeData> cities) {
        List<NodeData> newList = new LinkedList<NodeData>();
        newList.add(cities.get(0));
        cities.remove(0);
        int i = 0;
        while (cities.size() > 0) {
            for (i = 0; i < newList.size(); i++) {
                if (cities.get(0).getKey() == newList.get(i).getKey()) {
                    cities.remove(0);
                    break;
                }
            }
            if (i == newList.size()) {
                newList.add(cities.get(0));
                cities.remove(0);
            }
        }
        return newList;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        JSONObject jo = new JSONObject();
        JSONArray edgs = new JSONArray();
        JSONArray verts = new JSONArray();
        Iterator<NodeData> i = this.graph.nodeIter();
        while (i.hasNext()) {
            Vertex v = (Vertex) i.next();
            String p = v.getLocation().x() + "," + v.getLocation().y() + "," + v.getLocation().z();
            int id = v.getKey();
            JSONObject a = new JSONObject();
            a.put("pos", p);
            a.put("id", id);
            verts.add(a);
        }
        for (EdgeData edgeData : this.graph.allEdge().values()) {
            Edgedata e = (Edgedata) edgeData;
            int src = e.getSrc(), dest = e.getDest();
            double w = e.getWeight();
            JSONObject a = new JSONObject();
            a.put("src", src);
            a.put("w", w);
            a.put("dest", dest);
            edgs.add(a);
        }

        jo.put("Edges", edgs);
        jo.put("Nodes", verts);

        try {
            FileWriter fr = new FileWriter(file);
            fr.write(jo.toJSONString());
            fr.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            directeweightedgraph loadGraph = new directeweightedgraph();
            JSONParser jsonparser = new JSONParser();
            Object objfile = jsonparser.parse(new FileReader(file));
            JSONObject jfile = (JSONObject) objfile;
            //////reading the nodes
            JSONArray nodes = (JSONArray) jfile.get("Nodes");
            Iterator i = nodes.iterator();
            while (i.hasNext()) {
                HashMap<String, Object> datanode = (HashMap<String, Object>) i.next();
                String pos = (String) datanode.get("pos");
                int key = (int) (long) (datanode.get("id"));
                String[] geoloc = pos.split(",");
                geolocation loc = new geolocation(Double.parseDouble(geoloc[0]), Double.parseDouble(geoloc[1])
                        , Double.parseDouble(geoloc[2]));
                Vertex node = new Vertex(key, loc);
                loadGraph.addNode(node);
            }

            ///////reading the edges
            JSONArray edges = (JSONArray) jfile.get("Edges");
            i = edges.iterator();
            while (i.hasNext()) {
                HashMap<String, Object> dataedge = (HashMap<String, Object>) i.next();
                int src = (int) (long) dataedge.get("src");
                int dest = (int) (long) dataedge.get("dest");
                double w = (double) dataedge.get("w");
                loadGraph.connect(src, dest, w);
            }
            this.init(loadGraph);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }
}