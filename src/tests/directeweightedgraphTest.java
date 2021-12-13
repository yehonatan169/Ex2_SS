package tests;

import MainClasses.*;
import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class directeweightedgraphTest {
    public directeweightedgraph g1 = new directeweightedgraph();
    public directeweightedgraph g2 = new directeweightedgraph();
    Vertex v0,v1,v2,k0,k1,k2,k3,k4,k5,k6,k7,q1,q2;
    HashMap<Integer,HashMap<Integer, HashIndex>> e1,e2;
    HashMap<HashIndex, EdgeData> ed1,ed2,ed3;

    directeweightedgraphTest(){
        v0=new Vertex(0, new geolocation(1, 1, 0), 2, "", 1);
        v1=new Vertex(1, new geolocation(2, 1, 0), 7, "", 1);
        v2 = new Vertex(2, new geolocation(0, 1, 0), 4, "", 1);



        g1.addNode(v0);
        g1.addNode(v1);
        g1.addNode(v2);

        g1.connect(0,1,5);
        g1.connect(1,2,4);
        g1.connect(2,0,3);


        k0 = new Vertex(0, new geolocation(0, 1, 0), 4, "", 1);
        k1=new Vertex(1, new geolocation(1, 1, 0), 2, "", 1);
        k2=new Vertex(2, new geolocation(2, 1, 0), 7, "", 1);
        k3= new Vertex(3, new geolocation(-1, 1, 0), 12, "", 1);
        k4=new Vertex(4, new geolocation(-2, 1, 0), 9, "", 1);
        k5=new Vertex(5, new geolocation(3, 1, 0), 23, "", 1);
        k6=new Vertex(6, new geolocation(-3, 1, 0), 44, "", 1);
        k7=new Vertex(7, new geolocation(4, 1, 0), 1, "", 1);
        g2.addNode(k0);
        g2.addNode(k1);
        g2.addNode(k2);
        g2.addNode(k3);
        g2.addNode(k4);
        g2.addNode(k5);
        g2.addNode(k6);
        g2.addNode(k7);

        g2.connect(0,1,10);

        g2.connect(1,3,4);

        g2.connect(1,4,4);

        g2.connect(1,5,8);

        g2.connect(2,1,2);

        g2.connect(2,7,6);

        g2.connect(3,1,5);

        g2.connect(3,2,2);

        g2.connect(4,0,11);

        g2.connect(5,6,9);

        g2.connect(6,7,3);

        g2.connect(7,4,5);

        ////////////creat Hashmaps to check with ///////////
        e1 = new HashMap<Integer,HashMap<Integer,HashIndex>>();
        ed1= new HashMap<HashIndex, EdgeData>();
        Edgedata z1 = new Edgedata(v0.getKey(), v1.getKey(), 5,"",0);
        Edgedata z2 = new Edgedata(v1.getKey(), v2.getKey(), 4,"",0);
        Edgedata z3 = new Edgedata(v2.getKey(), v0.getKey(), 3,"",0);
        ed1.put(new HashIndex(v0.getKey(),v1.getKey()),z1);
        ed1.put(new HashIndex(v1.getKey(),v2.getKey()),z2);
        ed1.put(new HashIndex(v2.getKey(),v0.getKey()),z3);
        e1.put(v0.getKey(),new HashMap<Integer,HashIndex>());
        e1.put(v1.getKey(),new HashMap<Integer,HashIndex>());
        e1.put(v2.getKey(),new HashMap<Integer,HashIndex>());
        e1.get(v0.getKey()).put(v0.getKey(),new HashIndex(v0.getKey(),v1.getKey()));
        e1.get(v0.getKey()).put(v1.getKey(),new HashIndex(v1.getKey(),v2.getKey()));
        e1.get(v0.getKey()).put(v2.getKey(),new HashIndex(v2.getKey(),v0.getKey()));


        ed2 = new HashMap<HashIndex, EdgeData>();
        e2 = new HashMap<Integer,HashMap<Integer,HashIndex>>();
        e2.put(k0.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k1.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k2.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k3.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k4.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k5.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k6.getKey(),new HashMap<Integer,HashIndex>());
        e2.put(k7.getKey(),new HashMap<Integer,HashIndex>());

        Edgedata zz0 = new Edgedata(k0.getKey(), k1.getKey(), 10,"",0);
        Edgedata zz1 = new Edgedata(k1.getKey(), k3.getKey(), 4,"",0);
        Edgedata zz2 = new Edgedata(k1.getKey(), k4.getKey(), 4,"",0);
        Edgedata zz3 = new Edgedata(k1.getKey(), k5.getKey(), 8,"",0);
        Edgedata zz4 = new Edgedata(k2.getKey(), k1.getKey(), 2,"",0);
        Edgedata zz5 = new Edgedata(k2.getKey(), k7.getKey(), 6,"",0);
        Edgedata zz6 = new Edgedata(k3.getKey(), k1.getKey(), 5,"",0);
        Edgedata zz7 = new Edgedata(k3.getKey(), k2.getKey(), 2,"",0);
        Edgedata zz8 = new Edgedata(k4.getKey(), k2.getKey(), 11,"",0);
        Edgedata zz9 = new Edgedata(k5.getKey(), k6.getKey(), 9,"",0);
        Edgedata zz10 = new Edgedata(k6.getKey(), k7.getKey(), 3,"",0);
        Edgedata zz11 = new Edgedata(k7.getKey(), k4.getKey(), 5,"",0);

        e2.get(k0.getKey()).put(k0.getKey(),new HashIndex(k0.getKey(), k1.getKey()));
        e2.get(k1.getKey()).put(k0.getKey(),new HashIndex(k1.getKey(), k3.getKey()));
        e2.get(k1.getKey()).put(k0.getKey(),new HashIndex(k1.getKey(), k4.getKey()));
        e2.get(k1.getKey()).put(k0.getKey(),new HashIndex(k1.getKey(), k5.getKey()));
        e2.get(k2.getKey()).put(k0.getKey(),new HashIndex(k2.getKey(), k1.getKey()));
        e2.get(k2.getKey()).put(k0.getKey(),new HashIndex(k2.getKey(), k7.getKey()));
        e2.get(k3.getKey()).put(k0.getKey(),new HashIndex(k3.getKey(), k1.getKey()));
        e2.get(k3.getKey()).put(k0.getKey(),new HashIndex(k3.getKey(), k2.getKey()));
        e2.get(k3.getKey()).put(k0.getKey(),new HashIndex(k4.getKey(), k2.getKey()));
        e2.get(k4.getKey()).put(k0.getKey(),new HashIndex(k5.getKey(), k6.getKey()));
        e2.get(k5.getKey()).put(k0.getKey(),new HashIndex(k6.getKey(), k7.getKey()));
        e2.get(k6.getKey()).put(k0.getKey(),new HashIndex(k7.getKey(), k4.getKey()));

        ed2.put(new HashIndex(k0.getKey(), k1.getKey()),zz0);
        ed2.put(new HashIndex(k1.getKey(), k3.getKey()),zz1);
        ed2.put(new HashIndex(k1.getKey(), k4.getKey()),zz2);
        ed2.put(new HashIndex(k1.getKey(), k5.getKey()),zz3);
        ed2.put(new HashIndex(k2.getKey(), k1.getKey()),zz4);
        ed2.put(new HashIndex(k2.getKey(), k7.getKey()),zz5);
        ed2.put(new HashIndex(k3.getKey(), k1.getKey()),zz6);
        ed2.put(new HashIndex(k3.getKey(), k2.getKey()),zz7);
        ed2.put(new HashIndex(k4.getKey(), k2.getKey()),zz8);
        ed2.put(new HashIndex(k5.getKey(), k5.getKey()),zz9);
        ed2.put(new HashIndex(k6.getKey(), k7.getKey()),zz10);
        ed2.put(new HashIndex(k7.getKey(), k4.getKey()),zz11);
    }

    @Test
    void getNode() {
        assertEquals(v0.getKey(), g1.getNode(0).getKey());
        assertEquals(v1.getKey(), g1.getNode(1).getKey());
        assertEquals(v2.getKey(), g1.getNode(2).getKey());

        assertEquals(k0.getKey(), g2.getNode(0).getKey());
        assertEquals(k1.getKey(), g2.getNode(1).getKey());
        assertEquals(k2.getKey(), g2.getNode(2).getKey());
        assertEquals(k3.getKey(), g2.getNode(3).getKey());
        assertEquals(k4.getKey(), g2.getNode(4).getKey());
        assertEquals(k5.getKey(), g2.getNode(5).getKey());
        assertEquals(k6.getKey(), g2.getNode(6).getKey());
        assertEquals(k7.getKey(), g2.getNode(7).getKey());
    }

    @Test
    void myNode() {
        HashMap<Integer,Vertex> t1 = new HashMap<Integer,Vertex>();
        t1.put(v0.getKey(),v0);
        t1.put(1,new Vertex(1, new geolocation(1, 1, 0), 2, "", 1));
        t1.put(2,new Vertex(2, new geolocation(1, 1, 0), 2, "", 1));
        Iterator<NodeData> n = this.g1.nodeIter();
        NodeData singleNode;
        while(n.hasNext()){
            singleNode = n.next();
            assertEquals(t1.get(singleNode.getKey()).getKey(),singleNode.getKey());
        }


        HashMap<Integer,Vertex> t2 = new HashMap<Integer,Vertex>();
        t2.put(0,k0);
        t2.put(1,new Vertex(1, new geolocation(1, 1, 0), 2, "", 1));
        t2.put(2,new Vertex(2, new geolocation(2, 1, 0), 7, "", 1));
        t2.put(3,new Vertex(3, new geolocation(-1, 1, 0), 12, "", 1));
        t2.put(4,new Vertex(4, new geolocation(-2, 1, 0), 9, "", 1));
        t2.put(5,new Vertex(5, new geolocation(3, 1, 0), 23, "", 1));
        t2.put(6,new Vertex(6, new geolocation(-3, 1, 0), 44, "", 1));
        t2.put(7,new Vertex(7, new geolocation(4, 1, 0), 1, "", 1));
        n = this.g2.nodeIter();
        while(n.hasNext()){
            singleNode = n.next();
            assertEquals(t2.get(singleNode.getKey()).getKey(),singleNode.getKey());
        }
    }

    @Test
    void allEdge_and_myEdge() {
        Iterator<EdgeData> u = g1.edgeIter();
        while (u.hasNext()){
            EdgeData current = u.next();
            assertEquals(ed1.get(new HashIndex(current.getSrc(),current.getDest())).getSrc(), current.getSrc());
            assertEquals(ed1.get(new HashIndex(current.getSrc(),current.getDest())).getDest(), current.getDest());
        }

    }

    @Test
    void getEdge() {
        assertEquals(0,g1.getEdge(0,1).getSrc());
        assertEquals(1,g1.getEdge(0,1).getDest());
        assertEquals(0,g2.getEdge(0,1).getSrc());
        assertEquals(1,g2.getEdge(0,1).getDest());
    }

    @Test
    void addNode() {
        NodeData nodeToAdd = new Vertex(3, new geolocation(0, 1, 0), 4, "", 1);
        g1.addNode(nodeToAdd);
        assertEquals(nodeToAdd, g1.getNode(3));
    }

    @Test
    void connect() {
        g1.connect(2,1,0);
        g2.connect(4,5,4);
        assertEquals(2,g1.getEdge(2,1).getSrc());
        assertEquals(1,g1.getEdge(2,1).getDest());
        assertEquals(4,g2.getEdge(4,5).getSrc());
        assertEquals(5,g2.getEdge(4,5).getDest());
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> it = g1.nodeIter();
        for (Vertex v:g1.myNode().values()) {
            assertEquals(v.getKey(),it.next().getKey());
        }
        Iterator<NodeData> it2 = g2.nodeIter();
        for (Vertex v:g2.myNode().values()) {
            assertEquals(v.getKey(),it2.next().getKey());
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> it = g1.edgeIter();
        while(it.hasNext()){
            EdgeData e = it.next();
            assertEquals(e.getSrc(),g1.getEdge(e.getSrc(),e.getDest()).getSrc());
            assertEquals(e.getDest(),g1.getEdge(e.getSrc(),e.getDest()).getDest());
            assertEquals(e.getWeight(),g1.getEdge(e.getSrc(),e.getDest()).getWeight());
        }
    }

    @Test
    void removeNode() {
        g1.removeNode(0);
        assertFalse(g1.myNode().containsKey(0));
        g2.myNode().remove(10);
        assertFalse(g2.myNode().containsKey(10));

    }

    @Test
    void removeEdge() {
        g1.removeEdge(1, 2);
        assertFalse(g1.allEdge().containsValue(new HashIndex(1,2)));
        assertNull(g2.removeEdge(1, 20));
        assertNotNull(g2.getEdge(0, 1));
        g2.removeEdge(0, 1);
        assertNull(g2.getEdge(0, 1));
    }

    @Test
    void nodeSize() {
        assertEquals(3,g1.nodeSize());
        assertEquals(8,g2.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(3,g1.edgeSize());
        assertEquals(12,g2.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(6,g1.getMC());
        assertEquals(20,g2.getMC());
    }
}