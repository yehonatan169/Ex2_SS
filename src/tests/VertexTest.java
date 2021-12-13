package tests;

import MainClasses.geolocation;
import api.GeoLocation;
import org.junit.jupiter.api.Test;
import api.NodeData;
import MainClasses.Vertex;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    NodeData v1 = new Vertex(0, new geolocation(2, 3, 4), 5.3, "v1", 0);
    NodeData v2 = new Vertex(1, new geolocation(5, 6, 7), 22.6, "v2", 0);
    NodeData v3 = new Vertex(2, new geolocation(8, 9, 10), 18.5, "v3", 0);
    NodeData v4 = new Vertex(3, new geolocation(11, 12, 13), 2.1, "v4", 0);
    NodeData v5 = new Vertex(4, new geolocation(14, 15, 16), 10, "v5", 0);

    @Test
    void getKey() {
        assertEquals(0, v1.getKey());
        assertEquals(1, v2.getKey());
        assertEquals(2, v3.getKey());
        assertEquals(3, v4.getKey());
        assertEquals(4, v5.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(2, v1.getLocation().x());
        assertEquals(3, v1.getLocation().y());
        assertEquals(4, v1.getLocation().z());
        assertEquals(5, v2.getLocation().x());
        assertEquals(6, v2.getLocation().y());
        assertEquals(7, v2.getLocation().z());
        assertEquals(8, v3.getLocation().x());
        assertEquals(9, v3.getLocation().y());
        assertEquals(10, v3.getLocation().z());
        assertEquals(11, v4.getLocation().x());
        assertEquals(12, v4.getLocation().y());
        assertEquals(13, v4.getLocation().z());
        assertEquals(14, v5.getLocation().x());
        assertEquals(15, v5.getLocation().y());
        assertEquals(16, v5.getLocation().z());
    }

    @Test
    void setLocation() {
        v1.setLocation(new geolocation(100, 101, 102));
        assertEquals(100, v1.getLocation().x());
        assertEquals(101, v1.getLocation().y());
        assertEquals(102, v1.getLocation().z());
    }

    @Test
    void getWeight() {

        assertEquals(5.3, v1.getWeight());
        assertEquals(22.6, v2.getWeight());
        assertEquals(18.5, v3.getWeight());
        assertEquals(2.1, v4.getWeight());
        assertEquals(10, v5.getWeight());

    }

    @Test
    void setWeight() {
        v1.setWeight(100);
        assertEquals(100, v1.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("v1", v1.getInfo());
        assertEquals("v2", v2.getInfo());
        assertEquals("v3", v3.getInfo());
        assertEquals("v4", v4.getInfo());
        assertEquals("v5", v5.getInfo());
    }

    @Test
    void setInfo() {
        v1.setInfo("v10");
        assertEquals("v10", v1.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, v1.getTag());
        assertEquals(0, v2.getTag());
        assertEquals(0, v3.getTag());
        assertEquals(0, v4.getTag());
        assertEquals(0, v5.getTag());
    }

    @Test
    void setTag() {
        v1.setTag(1);
        assertEquals(1, v1.getTag());

    }
}