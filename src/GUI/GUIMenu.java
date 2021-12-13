package GUI;

import javax.swing.*;

public class GUIMenu {
    JMenuBar mb;
    //JMenu nodes;
    //JMenu edges;
    //JMenu graph;

    JMenu m1;
    JMenu m2;
    JMenu m3;

    JMenuItem m11;
    JMenuItem m12;
    JMenuItem m21;
    JMenuItem m22;
    JMenuItem m23;
    JMenuItem m24;
    JMenuItem m31;
    JMenuItem m32;
    JMenuItem m33;
    JMenuItem m34;

    public GUIMenu(){
        this.mb = new JMenuBar();

        this.m1 = new JMenu("file");
        this.m2 = new JMenu("display");
        this.m3 = new JMenu("actions");

        this.mb.add(this.m1);
        this.mb.add(this.m2);
        this.mb.add(this.m3);

        this.m11 = new JMenuItem("Open");
        this.m12 = new JMenuItem("Save as");
        this.m21 = new JMenuItem("add node");
        this.m22 = new JMenuItem("remove node");
        this.m23 = new JMenuItem("add edge");
        this.m24 = new JMenuItem("remove edge");
        this.m31 = new JMenuItem("display shortest path and distance");
        this.m32 = new JMenuItem("display center");
        this.m33 = new JMenuItem("isConnected?");
        this.m34 = new JMenuItem("tsp");
        this.m1.add(this.m11);
        this.m1.add(this.m12);
        this.m2.add(this.m21);
        this.m2.add(this.m22);
        this.m2.add(this.m23);
        this.m2.add(this.m24);
        this.m3.add(this.m31);
        this.m3.add(this.m32);
        this.m3.add(this.m33);
        this.m3.add(this.m34);
    }
}
