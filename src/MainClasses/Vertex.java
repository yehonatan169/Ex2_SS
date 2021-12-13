package MainClasses;

import api.NodeData;

import java.util.HashMap;

public class Vertex implements NodeData {
    private geolocation ver;
    private double weight;
    private int key;
    private String info;
    private int tag;
    private HashMap<Integer,Integer> toVertix;


    public Vertex(int key, geolocation p, double weight, String info, int tag) {// need to cheack if we need to send the key
        this.ver = new geolocation(p.x(),p.y(),p.z());
        this.weight = weight;
        this.key = key;
        this.info = info;
        this.tag = tag;
        this.toVertix = new HashMap<Integer,Integer>();
    }
    public Vertex(int key, geolocation location)
    {
        this.key=key;
        this.ver=location;
        this.info = "";
        this.tag = 0;
        this.toVertix = new HashMap<Integer,Integer>();
    }
    public Vertex(int key, geolocation p, double weight) {// need to cheack if we need to send the key
        this.ver = new geolocation(p.x(),p.y(),p.z());
        this.weight = weight;
        this.key = key;
        this.info = "";
        this.tag = 0;
        this.toVertix = new HashMap<Integer,Integer>();
    }
    public Vertex(Vertex p) {// need to check if we need to send the key
        this.ver = new geolocation(p.ver);
        this.weight = p.weight;
        this.key = p.key; // need to be checked
        this.info = p.info;
        this.tag = p.tag;
        this.toVertix = new HashMap<Integer,Integer>(p.toVertix);
    }

    /**
     * Returns the key (id) associated with this node.
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    public HashMap<Integer,Integer> getToVertix(){
        return this.toVertix;
    }

    /** Returns the location of this node, if none return null.
     * @return
     */
    @Override
    public api.GeoLocation getLocation() {
        return this.ver;
    }

    /** Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(api.GeoLocation p) {
        this.ver = (geolocation)p;
    }

    /**
     * Returns the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    public void setkey(int k) {
        this.key = k;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
