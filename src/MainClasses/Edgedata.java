package MainClasses;

import api.EdgeData;

public class Edgedata implements EdgeData {
    private int src;
    private int des;
    private double weightedge;
    private String infoedge;
    private int tagedge;

    public Edgedata(int s, int d, double w, String inf, int tag){
        this.src = s;
        this.des = d;
        this.weightedge = w;
        this.infoedge = inf;
        this.tagedge = tag;
    }
    /**
     * The id of the source node of this edge.
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() {
        return this.des;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weightedge;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return this.infoedge;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.infoedge = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return
     */
    @Override
    public int getTag() {
        return this.tagedge;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tagedge = t;
    }
}
