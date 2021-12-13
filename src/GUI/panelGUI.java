package GUI;

import MainClasses.DWGA;
import MainClasses.DistanceReturnedData;
import MainClasses.directeweightedgraph;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

public class panelGUI extends JPanel implements ActionListener {

    private directeweightedgraph graph;
    private Dimension screenSize;
    private int[] scaledX;
    private int[] scaledY;
    private DWGA dgwa;
    private HashMap<Integer, Point2D.Double> scales;
    PriorityQueue<Integer> deletedNodes;

    private int centerCond = -1;

    private int[] nodesToPaintDist;


    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    private double scaleY;
    private double scaleX;

    private Graphics2D g2D;
    private Path2D.Double q = new Path2D.Double();

    panelGUI(DirectedWeightedGraph graph){
        super();
        this.dgwa = new DWGA();
        this.scales = new HashMap<Integer, Point2D.Double>();
        this.graph = (directeweightedgraph) graph;
        this.dgwa.init(this.graph);

        directeweightedgraph t = (directeweightedgraph)graph;


        this.scaledX = new int[this.graph.getMaxValueNodeIndex()];
        this.scaledY = new int[this.graph.getMaxValueNodeIndex()];

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);
        deletedNodes = new PriorityQueue<Integer>();

    }

    private void scaling(){
        NodeData node;
        Iterator<NodeData> n = graph.nodeIter();
        if(n.hasNext()){
            node = n.next();
            this.minX = graph.getNode(node.getKey()).getLocation().x();
            this.minY = graph.getNode(node.getKey()).getLocation().y();
            this.maxX = graph.getNode(node.getKey()).getLocation().x();
            this.maxY = graph.getNode(node.getKey()).getLocation().y();
        }
        else{
            return;
        }



        while(n.hasNext()){
            node = n.next();
            if(node.getLocation().x() < this.minX){
                this.minX = node.getLocation().x();
            }
            if(node.getLocation().x() > this.maxX){
                this.maxX = node.getLocation().x();
            }
            if(node.getLocation().y() < this.minY){
                this.minY = node.getLocation().y();
            }
            if(node.getLocation().y() > this.maxY){
                this.maxY = node.getLocation().y();
            }

        }
        this.scaleX = (this.screenSize.width) / (this.maxX - this.minX) * 0.9;
        this.scaleY = (this.screenSize.height) / (this.maxY - this.minY) * 0.8;

        n = graph.nodeIter();
        while(n.hasNext()){
            node = n.next();
            scales.put(node.getKey(), new Point2D.Double((scaleX * (graph.getNode(node.getKey()).getLocation().x() - this.minX)), (scaleY * (graph.getNode(node.getKey()).getLocation().y() - this.minY))));
        }
    }

    public void paint(Graphics g){
        g2D = (Graphics2D) g;

        InitialDisplay();
    }


    private void InitialDisplay(){
        NodeData node;
        Iterator<NodeData> n = graph.nodeIter();
        Iterator<EdgeData> u = graph.edgeIter();
        EdgeData e;
        scaling();

        boolean breakCond;
        while(u.hasNext()){
            e = u.next();
            breakCond = false;
            for(int i = 0;this.nodesToPaintDist != null && i < this.nodesToPaintDist.length - 1; i++){
                if(e.getSrc() == this.nodesToPaintDist[i] && e.getDest() == this.nodesToPaintDist[i+1]){
                    breakCond = true;
                    break;
                }
            }
            if(breakCond){
                continue;
            }
            else{
                this.drawArrow(e.getSrc(),e.getDest(), 255, 200,0);
            }


        }

        //special nodePaint for markedNodes (tsp/shortestDistancePath)
        for(int i = 0;this.nodesToPaintDist != null && i < this.nodesToPaintDist.length - 1; i++){
            this.drawArrow(this.nodesToPaintDist[i],this.nodesToPaintDist[i+1], 202, 40,186);
        }

        for(int i = 0;this.nodesToPaintDist != null && i < this.nodesToPaintDist.length; i++){
            int size;
            size = this.nodesToPaintDist[i] == 0 ? 0 : (int)Math.log10(this.nodesToPaintDist[i]);
            this.drawNodeSpecial(this.nodesToPaintDist[i], size);
        }
        //


        n = graph.nodeIter();
        int size;

        //int i = 0;
        while(n.hasNext()){
            breakCond = false;
            node = n.next();
            for(int i = 0;this.nodesToPaintDist != null && i < this.nodesToPaintDist.length; i++){
                if(this.nodesToPaintDist[i] == node.getKey()){
                    breakCond = true;
                    break;
                }
            }
            if(breakCond){
                continue;
            }
            size = node.getKey() == 0 ? 0 : (int)Math.log10(node.getKey());
            if(centerCond == node.getKey()){
                this.drawNodeSpecial(node.getKey(), size);
                centerCond = -1;
                continue;
            }
            g2D.setColor(Color.BLACK);
            g2D.fillOval((int)this.scales.get(node.getKey()).x, (int)this.scales.get(node.getKey()).y, 20, 20);

            g2D.setColor(Color.red);

            g2D.drawString(String.valueOf(node.getKey()),(int)this.scales.get(node.getKey()).x + 7 - (size * 5), (int)this.scales.get(node.getKey()).y + 14);
        }
        this.nodesToPaintDist = null;
    }

    private void drawArrow(int src, int dest, int r, int g, int b){

        g2D.setColor(new Color(r,g,b));
        g2D.setStroke(new BasicStroke(3));
        double theta;
        double thetaValueX = this.scales.get(src).x;
        double thetaValueY = this.scales.get(src).y;

        if(scales.get(src).x > scales.get(dest).x  || graph.getEdge(dest,src) == null){
            //g2D.setColor(new Color(r,g,b));
            g2D.drawLine((int)scales.get(src).x + 10,(int)scales.get(src).y + 10,(int)scales.get(dest).x + 10, (int)scales.get(dest).y + 10);
        }
        else{
            if(r == 255){
                g2D.setColor(new Color(187, 156, 18));
            }
            //these calculations are used to find the right Points Coordinates to place for curved arrows
            double x1 = scales.get(src).x + 10;
            double y1 = scales.get(src).y + 10;
            double x2 = scales.get(dest).x + 10;
            double y2 = scales.get(dest).y + 10;

            double slope = (x1-x2 == 0) ? Double.MAX_VALUE : ((y1-y2) / (x1-x2));
            double slopeNegative = -1 / slope;

            double newX1 = (Math.abs(x1-x2) / 4) + x1;
            double newX2 = (Math.abs(x1-x2) / 4 * 3) + x1;
            double newY1 = (Math.abs(y1-y2) / 4) + y1;
            double newY2 = (Math.abs(y1-y2) / 4 * 3) + y1;

            double dist = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)) / 8;
            double[] p1 = curveAid(newX1, newY1,slopeNegative,dist);
            double[] p2 = curveAid(newX2, newY2,slopeNegative,dist);

            thetaValueX = p2[0];
            thetaValueY = p2[1];

            q = new Path2D.Double();
            q.moveTo((int)scales.get(src).x + 10,(int)scales.get(src).y + 10);
            q.curveTo(p1[0],p1[1],p2[0], p2[1],(int)scales.get(dest).x + 10,(int)scales.get(dest).y + 10);
            g2D.draw(q);
        }


        int x1;
        theta = Math.atan2(this.scales.get(dest).y - thetaValueY, this.scales.get(dest).x - thetaValueX);
        drawArrowHead(g2D, theta, this.scales.get(dest).x + 10, this.scales.get(dest).y + 10);
    }

    //this function is used to find the right Points Coordinates to place for curved arrows
    //m = slope, l = length(distance)
    private double[] curveAid(double orgX, double orgY, double m, double l){
        double[] xy = new double[2];
        if (m == 0){
            xy[0] = orgX + l;
            xy[1] = orgY;
        }

        // If slope is infinite
        else if (m == Double.MAX_VALUE){
            xy[0] = orgX;
            xy[1] = orgY + l;
        }
        else {
            double dx = (l / Math.sqrt(1 + (m * m)));
            double dy = m * dx;
            xy[0] = orgX + dx;
            xy[1] = orgY + dy;
        }
        return xy;
    }

    //arrow making function found online
    private void drawArrowHead(Graphics2D g2, double theta, double x0, double y0)
    {
        double barb = 20;
        double phi = Math.PI / 6;

        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }

    private void drawNodeSpecial(int nodeKey, int size){
        g2D.setColor(new Color(229, 155, 155));
        g2D.fillOval((int)this.scales.get(nodeKey).x, (int)this.scales.get(nodeKey).y, 20, 20);

        g2D.setColor(new Color(7, 32, 51));
        g2D.drawOval((int)this.scales.get(nodeKey).x - 1, (int)this.scales.get(nodeKey).y - 1, 22, 22);

        g2D.setColor(new Color(21, 82, 93));
        g2D.drawString(String.valueOf(nodeKey),(int)this.scales.get(nodeKey).x + 7 - (size * 5), (int)this.scales.get(nodeKey).y + 14);
    }

    public void addNode(){
        double x = getValue("enter x value for new node on screen size: ", "x input is invalid", "double");
        if(x == -1){
            return;
        }
        double y =  getValue("enter y value for new node on screen size: ", "y input is invalid", "double");
        if(y == -1){
            return;
        }
        int newKey = this.deletedNodes.size() == 0 ? graph.nodeSize() : this.deletedNodes.poll();
        double newX = (x / this.scaleX) + this.minX;
        double newY = (y / this.scaleY) + this.minY;

        NodeData nodeToAdd = new MainClasses.Vertex(newKey, new MainClasses.geolocation(newX, newY, 0), 2, "", 1);
        graph.addNode(nodeToAdd);
        scales.put(newKey, new Point2D.Double(nodeToAdd.getLocation().x(), nodeToAdd.getLocation().y()));
        reMake();
    }

    public void deleteNode(){

        int k = (int)getValue("enter key value of a Node to Delete: ", "key input invalid", "int");
        if(k == -1){
            return;
        }
        if(!scales.containsKey(k)){
            JOptionPane.showMessageDialog(null,"selected node does not exists on graph");
            return;
        }
        if(!this.deletedNodes.contains(k)){
            deletedNodes.add(k);
        }

        graph.removeNode(k);
        reMake();
    }

    public void addEdge(){
        int s = (int)getValue("enter source node key value", "source node input is invalid", "int");
        NodeData source = graph.getNode(s);
        if(source == null){
            JOptionPane.showMessageDialog(null,"no source node found");
            return;
        }

        int d = (int)getValue("enter destination node key value", "destination node input is invalid", "int");
        NodeData destination = graph.getNode(d);
        if(destination == null){
            JOptionPane.showMessageDialog(null,"no destination node found");
            return;
        }

        double w = getValue("enter weight value", "weight input is invalid", "double");
        graph.connect(s,d,w);
        reMake();


    }

    public void deleteEdge(){

        int s = (int)getValue("enter source node key value", "source node input is invalid", "int");
        NodeData source = graph.getNode(s);
        if(source == null){
            JOptionPane.showMessageDialog(null,"no source node found");
            return;
        }
        if(!scales.containsKey(s)){
            JOptionPane.showMessageDialog(null,"selected source node does not exists on graph");
            return;
        }

        int d = (int)getValue("enter destination node key value", "destination node input is invalid", "int");
        NodeData destination = graph.getNode(d);
        if(destination == null){
            JOptionPane.showMessageDialog(null,"no destination node found");
            return;
        }
        if(!scales.containsKey(d)){
            JOptionPane.showMessageDialog(null,"selected destination node does not exists on graph");
            return;
        }
        if(graph.getEdge(s,d) == null){
            JOptionPane.showMessageDialog(null,"selected edge does not exists on graph");
            return;
        }
        graph.removeEdge(s,d);
        reMake();
    }

    public void isConnected(){
        boolean con = this.dgwa.isConnected();
        if(con){
            JOptionPane.showMessageDialog(null,"graph is connected");
        }
        else{
            JOptionPane.showMessageDialog(null,"graph is not connected");
        }
        reMake();
    }

    public void center(){
        NodeData centerNode = this.dgwa.center();
        if(centerNode == null){
            JOptionPane.showMessageDialog(null,"this graph has no center");
            return;
        }
        int center = this.dgwa.center().getKey();
        this.centerCond = center;
        reMake();
        JOptionPane.showMessageDialog(null,"the center of the graph is " + center);
    }

    public void shortestDistanceDisplay(){
        int s = (int)getValue("enter source node key value", "source node input is invalid", "int");
        NodeData source = graph.getNode(s);
        if(source == null){
            JOptionPane.showMessageDialog(null,"no source node found");
            return;
        }
        if(!scales.containsKey(s)){
            JOptionPane.showMessageDialog(null,"selected source node does not exists on graph");
            return;
        }

        int d = (int)getValue("enter destination node key value", "destination node input is invalid", "int");
        NodeData destination = graph.getNode(d);
        if(destination == null){
            JOptionPane.showMessageDialog(null,"no destination node found");
            return;
        }
        if(!scales.containsKey(d)){
            JOptionPane.showMessageDialog(null,"selected destination node does not exists on graph");
            return;
        }

        DistanceReturnedData data = this.dgwa.getBothDistanceAndPath(s,d);
        String message = data.getDistance() == -1 ? "no path was found." : "the distance of the shortest path from \n" +
                "node " + s + " to " + d + " is " + data.getDistance();

        List<NodeData> pathList;
        pathList = data.getPath();
        if(pathList != null){
            listToArrConverstion(pathList);
        }
        reMake();
        JOptionPane.showMessageDialog(null,message);
    }

    public void getTSP(){
        List<NodeData> cities = new LinkedList<NodeData>();

        try{
            String citiesInput = JOptionPane.showInputDialog("enter nodes keys to be used as cities. \n" +
                    "important: the accepted input format is \"x,y,z\" such a way that the node \n" +
                    "keys are seperated with commas alone." +
                    "\n all cities must be existing nodes.");
            String temp = "";
            while(!citiesInput.isEmpty()) {
                temp = "";
                while(!citiesInput.equals("") && citiesInput.length() > 0 && citiesInput.charAt(0) != ',') {
                    temp+=citiesInput.charAt(0);
                    citiesInput = citiesInput.substring(1, citiesInput.length());
                }
                if(Integer.parseInt(temp) < 0){
                    throw new Exception();
                }
                cities.add(this.graph.getNode(Integer.parseInt(temp)));
                if(!citiesInput.equals("")) {
                    citiesInput = citiesInput.substring(1, citiesInput.length());
                }
            }
            List<NodeData> route = this.dgwa.tsp(cities);
            if(route != null){
                listToArrConverstion(route);
            }
            else{
                JOptionPane.showMessageDialog(null,"no possible path was found.");
            }

            reMake();
        }
        catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null,"one of the entered cities keys is not an existing nodes.");
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"invalid input was entered, please follow instructions.");
        }
    }

    public void load(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(".")); //sets current directory
            int response = fileChooser.showOpenDialog(null); //select file to open
            File file;
            if(response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
            else throw new Exception();
            String json_file = file.toString();

            boolean loader = dgwa.load(json_file);
            if(!loader){
                throw new Exception();
            }
            this.graph = (directeweightedgraph) dgwa.getGraph();
            reMake();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"selected json-graph could not be loaded");
        }
    }

    public void save(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(".")); //sets current directory
            fileChooser.showSaveDialog(this); //select file to save
            File file= fileChooser.getSelectedFile();
            String json_file = file.toString() + ".json";
            boolean loader = dgwa.save(json_file);
            if(!loader){
                throw new Exception();
            }
            this.graph = (directeweightedgraph) dgwa.getGraph();
            reMake();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"graph could not be saved");
        }
    }

    private void listToArrConverstion(List<NodeData> input){
        this.nodesToPaintDist = new int[input.size()];
        int i = 0;
        for(NodeData item : input){
            this.nodesToPaintDist[i] = item.getKey();
            i++;
        }
    }

    private double getValue(String openingMessage, String failedMessage, String inputType){
        String sVal = JOptionPane.showInputDialog(openingMessage);
        if(sVal == "" || sVal == null){
            return - 1;
        }
        try{
            double val =  Double.parseDouble(sVal);
            if(val < 0 || (inputType == "int" && val % 1 != 0)){
                throw new Exception();
            }
            return val;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,failedMessage);
            return -1;
        }
    }

    private void reMake(){
        this.setVisible(false);
        this.graph = (directeweightedgraph) this.dgwa.copy();
        this.dgwa.init(this.graph);
        repaint();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
