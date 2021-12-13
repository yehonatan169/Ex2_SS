package MainClasses;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;
import java.util.function.Consumer;

public class directeweightedgraph implements DirectedWeightedGraph {
    private HashMap<Integer,Vertex>nodes;
    private HashMap<Integer,HashMap<Integer,HashIndex>> edgeIndexes;
    private HashMap<HashIndex ,EdgeData>allEdges;
    private int theMC=0;

    private int maxValueNodeIndex;
    private PriorityQueue<Integer> maxVal;


    public directeweightedgraph(){
        this.nodes = new HashMap<Integer,Vertex>();
        this.edgeIndexes = new HashMap<Integer,HashMap<Integer,HashIndex>>();
        this.allEdges = new HashMap<HashIndex, EdgeData>();
        this.theMC=0;
        this.maxValueNodeIndex = this.nodes.size();
        this.maxVal = new PriorityQueue<>(Collections.reverseOrder());
    }
    public directeweightedgraph(directeweightedgraph g){
        this.nodes = g.nodes;
        this.allEdges = g.allEdges;
        this.edgeIndexes = g.edgeIndexes;
        this.maxValueNodeIndex = g.getMaxValueNodeIndex();
        this.maxVal = g.maxVal;
        this.theMC=0;
    }

    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {return this.nodes.get(key);}

    public HashMap<Integer,Vertex> myNode(){
        return this.nodes;}
    public HashMap<HashIndex,EdgeData> allEdge(){
        return this.allEdges;}

    public int getMaxValueNodeIndex(){
        return this.maxValueNodeIndex;
    }

    public void setMaxValueNodeIndex(int maxValueNodeIndex) {
        this.maxValueNodeIndex = maxValueNodeIndex;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        HashIndex p = new HashIndex(src,dest);
        return this.allEdges.get(p);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        if(this.nodes.containsKey(n.getKey())){
            System.out.println("the node already exists");
            return;
        }
        this.nodes.put(n.getKey(), (Vertex) n);
        this.edgeIndexes.put(n.getKey(),new HashMap<Integer,HashIndex>());
        this.theMC++;

        if(n.getKey() > this.maxValueNodeIndex){
            this.maxValueNodeIndex = n.getKey();
        }
        this.maxVal.add(n.getKey());

    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
            EdgeData newedge = new Edgedata(src, dest, w, "", 0);
            HashIndex t = new HashIndex(src,dest);
            this.allEdges.put(t,newedge);
            Vertex v = (Vertex) (this.getNode(dest));
            v.getToVertix().put(src,src);
            this.edgeIndexes.get(src).put(dest,t);
            this.theMC++;
        }
    }

    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator<NodeData> iternode = new Iterator<NodeData>() {
            private Iterator<Vertex> it = nodes.values().iterator();
            private final int copymc = theMC;
            @Override
            public boolean hasNext() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                return it.next();
            }

            @Override
            public void remove() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super NodeData> action) {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.forEachRemaining(action);
            }

        };
        return iternode;
    }

    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() {

        Iterator<EdgeData> iterEdge = new Iterator<EdgeData>() {
            private Iterator<EdgeData> it = allEdges.values().iterator();
            private final int copymc = theMC;
            @Override
            public boolean hasNext() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                return it.next();
            }

            @Override
            public void remove() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.forEachRemaining(action);
            }

        };
        return iterEdge;

    }

    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return (Iterator<EdgeData>) new Iterator() {
            private Iterator<HashIndex> it = edgeIndexes.get(node_id).values().iterator();
            private final int copymc = theMC;

            @Override
            public boolean hasNext() {
                if (theMC != copymc) {//threads??
                    throw new RuntimeException("graph was changed");
                }

                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                HashIndex x = it.next();
                if (theMC != copymc) {//threads??
                    throw new RuntimeException("graph was changed");
                }
                return allEdges.get(x);
            }

            @Override
            public void remove() {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.remove();
            }

            @Override
            public void forEachRemaining(Consumer action) {
                if (theMC != copymc) {
                    throw new RuntimeException("graph was changed");
                }
                it.forEachRemaining(action);
            }
        };
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public NodeData removeNode(int key) {
        NodeData v = this.nodes.get(key);
        if(v != null) {
            for(Integer whereItCameFrom : this.nodes.get(key).getToVertix().values()) {
                this.allEdges.remove(new HashIndex(whereItCameFrom,key));
                this.edgeIndexes.get(whereItCameFrom).remove(key);
                theMC+=2;
            }


            if(key == this.maxValueNodeIndex){
                this.maxVal.poll();
                this.maxValueNodeIndex = this.maxVal.peek();
            }
            Iterator<EdgeData> u = this.edgeIter(key);
            int size = this.edgeIndexes.get(key).size();
            while(u.hasNext()){
                this.allEdges.remove(u.next());

            }
            this.edgeIndexes.remove(key);
            this.nodes.remove(key);
            theMC= 2 + size;
        }
        return v;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        HashIndex t = new HashIndex(src,dest);
        EdgeData e = this.allEdges.get(t);
        if(e != null) {
            this.allEdges.remove(t);
            this.edgeIndexes.get(src).remove(dest);
            Vertex v = (Vertex) (this.getNode(dest));
            v.getToVertix().remove(src);
            theMC++;
        }
        return e;
    }

    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.allEdges.size();
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.theMC;
    }
}
