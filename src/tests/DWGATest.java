package tests;

import MainClasses.DWGA;
import MainClasses.directeweightedgraph;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGATest {

    DWGA dw1 = new DWGA();
    DWGA dw2 = new DWGA();
    directeweightedgraphTest gt = new directeweightedgraphTest();


    public DWGATest(){
        init();
    }


    @Test
    void init() {
        dw1.init(gt.g1);
        dw2.init(gt.g2);
    }

    @Test
    void getGraph() {

        init();
        assertEquals(3,dw1.getGraph().nodeSize()); // sample to see if graph was "sucsessfully inserted"
        assertEquals(8,dw2.getGraph().nodeSize());
    }

    @Test
    void copy() {
        init();
        DirectedWeightedGraph c = dw1.copy();
        assertEquals(3,c.nodeSize());
        assertEquals(3, c.edgeSize());
    }

    @Test
    void isConnected() {
        init();
        assertEquals(true, dw1.isConnected());
        assertEquals(true, dw2.isConnected());
//        DWGA algo = new DWGA();
//        algo.load("data/G1.json");
//        algo.load("data/G2.json");
//        algo.load("data/G3.json");
//        algo.load("data/1000Nodes.json");
//        algo.load("data/10000Nodes.json");
//        algo.load("data/100000.json");
//        algo.isConnected();
    }

    @Test
    void shortestPathDist() {
        init();
        assertEquals(0, dw1.shortestPathDist(0,0));
        assertEquals(5, dw1.shortestPathDist(0,1));
        assertEquals(9, dw1.shortestPathDist(0,2));
        assertEquals(7, dw1.shortestPathDist(1,0));
        assertEquals(0, dw1.shortestPathDist(1,1));
        assertEquals(4, dw1.shortestPathDist(1,2));
        assertEquals(3, dw1.shortestPathDist(2,0));
        assertEquals(8, dw1.shortestPathDist(2,1));
        assertEquals(0, dw1.shortestPathDist(2,2));

        assertEquals(16, dw2.shortestPathDist(0,2));
        assertEquals(14, dw2.shortestPathDist(0,4));
        assertEquals(27, dw2.shortestPathDist(0,6));
        assertEquals(22, dw2.shortestPathDist(0,7));
        assertEquals(15, dw2.shortestPathDist(1,0));
        assertEquals(6, dw2.shortestPathDist(1,2));
        assertEquals(4, dw2.shortestPathDist(1,4));
        assertEquals(12, dw2.shortestPathDist(1,7));
        assertEquals(17, dw2.shortestPathDist(2,0));
        assertEquals(6, dw2.shortestPathDist(2,3));
        assertEquals(6, dw2.shortestPathDist(2,4));
        assertEquals(19, dw2.shortestPathDist(2,6));
        assertEquals(4, dw2.shortestPathDist(3,1));
        assertEquals(2, dw2.shortestPathDist(3,2));
        assertEquals(12, dw2.shortestPathDist(3,5));
        assertEquals(8, dw2.shortestPathDist(3,7));
        assertEquals(11, dw2.shortestPathDist(4,0));
        assertEquals(25, dw2.shortestPathDist(4,3));
        assertEquals(29, dw2.shortestPathDist(4,5));
        assertEquals(38, dw2.shortestPathDist(4,6));
        assertEquals(42, dw2.shortestPathDist(5,3));
        assertEquals(9, dw2.shortestPathDist(5,6));
        assertEquals(12, dw2.shortestPathDist(5,7));
        assertEquals(19, dw2.shortestPathDist(6,0));
        assertEquals(29, dw2.shortestPathDist(6,1));
        assertEquals(8, dw2.shortestPathDist(6,4));
        assertEquals(16, dw2.shortestPathDist(7,0));
        assertEquals(26, dw2.shortestPathDist(7,1));
        assertEquals(30, dw2.shortestPathDist(7,3));
        assertEquals(5, dw2.shortestPathDist(7,4));
        assertEquals(43, dw2.shortestPathDist(7,6));
    }

    @Test
    void shortestPath() {
        init();
        List<NodeData> list = dw2.shortestPath(0,7);
        String result  = "";
        for(NodeData item : list){
            result += item.getKey() + ", ";
        }
        assertEquals("0, 1, 3, 2, 7, ", result);

        list = dw2.shortestPath(5,1);
        result  = "";
        for(NodeData item : list){
            result += item.getKey() + ", ";
        }
        assertEquals("5, 6, 7, 4, 0, 1, ", result);

        list = dw2.shortestPath(4,3);
        result  = "";
        for(NodeData item : list){
            result += item.getKey() + ", ";
        }
        assertEquals("4, 0, 1, 3, ", result);

        list = dw2.shortestPath(2,4);
        result  = "";
        for(NodeData item : list){
            result += item.getKey() + ", ";
        }
        assertEquals("2, 1, 4, ", result);

        list = dw2.shortestPath(3,0);
        result  = "";
        for(NodeData item : list){
            result += item.getKey() + ", ";
        }
        assertEquals("3, 2, 1, 4, 0, ", result);
    }

    @Test
    void center() {
        init();
        assertEquals(1, dw1.center().getKey());
        assertEquals(1, dw2.center().getKey());
        DWGA algo = new DWGA();

//        algo.load("data/G1.json");
//        algo.load("data/G2.json");
//        algo.load("data/1000Nodes.json");// need to be 362
//        algo.load("data/1000Nodes.json");// need to be 3846
//        algo.load("data/100000.json");// time out
          assertTimeout(Duration.ofMinutes(7),algo::center);


    }

    @Test
    void tsp() {
        init();
        List<NodeData> citiesInput = new LinkedList<>();
        citiesInput.add(gt.g1.getNode(0));
        citiesInput.add(gt.g1.getNode(1));
        citiesInput.add(gt.g1.getNode(2));
        List<NodeData> output = dw1.tsp(citiesInput);
        StringBuilder result = new StringBuilder();

        for(NodeData item : output){
            result.append(item.getKey()).append(", ");
        }

        assertEquals("2, 0, 1, ", result.toString());
        citiesInput = new LinkedList<>();
        citiesInput.add(gt.g1.getNode(2));
        citiesInput.add(gt.g1.getNode(1));
        output = dw1.tsp(citiesInput);
        result = new StringBuilder();
        for(NodeData item : output){
            result.append(item.getKey()).append(", ");
        }
        assertEquals("1, 2, ", result.toString());
        citiesInput = new LinkedList<>();
        citiesInput.add(gt.g2.getNode(4));
        citiesInput.add(gt.g2.getNode(3));
        citiesInput.add(gt.g2.getNode(1));
        citiesInput.add(gt.g2.getNode(0));
        citiesInput.add(gt.g2.getNode(7));
        citiesInput.add(gt.g2.getNode(5));
        citiesInput.add(gt.g2.getNode(2));
        citiesInput.add(gt.g2.getNode(6));
        output = dw2.tsp(citiesInput);
        result = new StringBuilder();

        for(NodeData item : output){
            result.append(item.getKey()).append(", ");
        }
        assertEquals("3, 2, 1, 5, 6, 7, 4, 0, ", result.toString());

        citiesInput = new LinkedList<>();
        citiesInput.add(gt.g2.getNode(6));
        citiesInput.add(gt.g2.getNode(2));
        citiesInput.add(gt.g2.getNode(7));
        citiesInput.add(gt.g2.getNode(6));
        citiesInput.add(gt.g2.getNode(3));
        citiesInput.add(gt.g2.getNode(0));
        output = dw2.tsp(citiesInput);
        result = new StringBuilder();

        for(NodeData item : output){
            result.append(item.getKey()).append(", ");
        }
        assertEquals("0, 1, 3, 2, 1, 5, 6, 7, ", result.toString());
//
//        DWGA algo = new DWGA();
//        algo.load("data/G1.json");
//        algo.load("data/G2.json");
//        algo.load("data/1000Nodes.json");
//        algo.load("data/1000Nodes.json");
//        algo.load("data/100000.json");
//        List<NodeData> cities = new LinkedList<NodeData>();
//        for(int i = 0; i < 10; i++){
//            cities.add(algo.getGraph().getNode((i * 3400) + 23));
//        }
//        System.out.println(algo.tsp(cities).size());
    }

    @Test
    void save(){

    }
    @Test
    void load(){


    }

}