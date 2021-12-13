package tests;

import org.junit.jupiter.api.Test;
import  MainClasses.geolocation;

import static org.junit.jupiter.api.Assertions.*;

class geolocationTest {
    geolocation p1 = new geolocation(1,1,0);
    geolocation p2 = new geolocation(-1,-1,0);
    geolocation p3 = new geolocation(2,2,0);
    geolocation p4 = new geolocation(-2,-2,0);
    geolocation p5 = new geolocation(3,3,0);
    geolocation p6 = new geolocation(p4);


    @Test
    void x() {
        assertEquals(1,p1.x());

        assertEquals(-1,p2.x());

        assertEquals(2,p3.x());

        assertEquals(-2,p4.x());

        assertEquals(3,p5.x());

        assertEquals(-2,p6.x());



    }

    @Test
    void y() {
        assertEquals(1,p1.y());

        assertEquals(-1,p2.y());

        assertEquals(2,p3.y());

        assertEquals(-2,p4.y());

        assertEquals(3,p5.y());

        assertEquals(-2,p6.y());


    }

    @Test
    void z() {
        assertEquals(0,p1.z());

        assertEquals(0,p2.z());

        assertEquals(0,p3.z());

        assertEquals(0,p4.z());

        assertEquals(0,p5.z());

        assertEquals(0,p6.z());

    }

    @Test
    void distance() {
        double thedist1_2= Math.sqrt(8);
        double dist1_2 = p1.distance(p2);
        assertEquals(Math.sqrt(8),dist1_2);
        double thedist3_4= Math.sqrt(32);
        double dist3_4 = p3.distance(p4);
        assertEquals(Math.sqrt(32),dist3_4);
        double thedist5_6= Math.sqrt(50);
        double dist5_6 = p5.distance(p6);
        assertEquals(Math.sqrt(50),dist5_6);



    }
}