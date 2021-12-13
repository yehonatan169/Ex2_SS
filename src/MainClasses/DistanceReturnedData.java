package MainClasses;
import java.util.*;
import api.NodeData;

//this class is used to retrieve all data from distance calculation in a single check
public class DistanceReturnedData {
    private double distance;
    private List<NodeData> path;
    private double[] allDists;
    private int[] indexesArray;

    public DistanceReturnedData(double dis, double[] allDistsInput, int[] indexesArrayInput){
        this.distance = dis;
        this.path = new LinkedList<NodeData>();
        distsArrayInitilize(allDistsInput);
        indssArrayInitilize(indexesArrayInput);
    }

    public double getDistance(){
        return this.distance;
    }

    public List<NodeData> getPath(){
        List<NodeData> r = new LinkedList<NodeData>();
        if(this.path == null){
            return null;
        }
        for(NodeData item : this.path){
            r.add(item);
        }
        return r;
    }

    public void addNodeToList(int ind,NodeData node){
        this.path.add(ind, node);
    }

    public void nullify(){
        this.path = null;
    }

    private void distsArrayInitilize(double[] original){

        this.allDists = new double[original.length];
        for(int i = 0; i < original.length; i++){
            this.allDists[i] = original[i];
        }
    }

    private void indssArrayInitilize(int[] original){
        this.indexesArray = new int[original.length];
        for(int i = 0; i < original.length; i++){
            this.indexesArray[i] = original[i];
        }
    }

}
