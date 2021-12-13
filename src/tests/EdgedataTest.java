package tests;

import org.junit.jupiter.api.Test;
import MainClasses.Edgedata;

import static org.junit.jupiter.api.Assertions.*;

class EdgedataTest {
    Edgedata e1 = new Edgedata(1, 3, 8, "e1", 0);
    Edgedata e2 = new Edgedata(3, 2, 11.5, "e2", 0);
    Edgedata e3 = new Edgedata(1, 4, 2.7, "e3", 0);
    Edgedata e4 = new Edgedata(2, 4, 7, "e4", 0);
    Edgedata e5 = new Edgedata(4, 2, 6.5, "e5", 0);

    @Test
    void getSrc() {
        assertEquals(1, e1.getSrc());
        assertEquals(3, e2.getSrc());
        assertEquals(1, e3.getSrc());
        assertEquals(2, e4.getSrc());
        assertEquals(4, e5.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(3, e1.getDest());
        assertEquals(2, e2.getDest());
        assertEquals(4, e3.getDest());
        assertEquals(4, e4.getDest());
        assertEquals(2, e5.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(8, e1.getWeight());
        assertEquals(11.5, e2.getWeight());
        assertEquals(2.7, e3.getWeight());
        assertEquals(7, e4.getWeight());
        assertEquals(6.5, e5.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("e1", e1.getInfo());
        assertEquals("e2", e2.getInfo());
        assertEquals("e3", e3.getInfo());
        assertEquals("e4", e4.getInfo());
        assertEquals("e5", e5.getInfo());
    }

    @Test
    void setInfo() {
        e1.setInfo("e10");
        assertEquals("e10", e1.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0, e1.getTag());
        assertEquals(0, e2.getTag());
        assertEquals(0, e3.getTag());
        assertEquals(0, e4.getTag());
        assertEquals(0, e5.getTag());
    }

    @Test
    void setTag() {
        e1.setTag(1);
        assertEquals(1, e1.getTag());
    }
}