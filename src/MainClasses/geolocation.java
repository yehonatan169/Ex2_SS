package MainClasses;

/**
 * This interface represents a geo location <x,y,z>, (aka Point3D data).
 *
 */
public class geolocation implements api.GeoLocation {
    private double x;
    private double y;
    private double z;

    public geolocation(geolocation p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
    public geolocation(double x1, double y1, double z1){
        this.x = x1;
        this.y = y1;
        this.z = z1;
    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(api.GeoLocation g) {
        geolocation n = (geolocation)g;
        double dis = Math.pow(n.x-this.x,2);
        dis = dis + Math.pow(n.y-this.y,2);
        dis = dis + Math.pow(n.z-this.z,2);
        dis = Math.sqrt(dis);
        return dis;
    }
}
