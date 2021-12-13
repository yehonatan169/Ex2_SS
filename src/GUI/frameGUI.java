package GUI;

import MainClasses.DWGA;
import api.DirectedWeightedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frameGUI extends JFrame implements ActionListener {
    private panelGUI panel;
    private DirectedWeightedGraph g;
    private GUIMenu menu;

    public frameGUI(DirectedWeightedGraph dg){
        super();
        g = dg;
        panel = new panelGUI(g);
        this.setResizable(false);
        menu = new GUIMenu();

        this.setTitle("Graph Task");
        this.getContentPane().setBackground(new Color(14, 187, 132));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(500,500);
        this.add(this.menu.mb);
        this.getContentPane().add(BorderLayout.NORTH, this.menu.mb);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.menu.m11.addActionListener(this);
        this.menu.m12.addActionListener(this);
        this.menu.m21.addActionListener(this);
        this.menu.m22.addActionListener(this);
        this.menu.m23.addActionListener(this);
        this.menu.m24.addActionListener(this);
        this.menu.m31.addActionListener(this);
        this.menu.m32.addActionListener(this);
        this.menu.m33.addActionListener(this);
        this.menu.m34.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu.m11){
            this.panel.load();
        }
        else if(e.getSource() == menu.m12){
            this.panel.save();
        }
        else if(e.getSource() == menu.m21){
            this.panel.addNode();
        }
        else if(e.getSource() == menu.m22){
            this.panel.deleteNode();
        }
        else if(e.getSource() == menu.m23){
            this.panel.addEdge();
        }
        else if(e.getSource() == menu.m24){
            this.panel.deleteEdge();
        }
        else if(e.getSource() == menu.m31){
            this.panel.shortestDistanceDisplay();
        }
        else if(e.getSource() == menu.m32){
            this.panel.center();
        }
        else if(e.getSource() == menu.m33){
            this.panel.isConnected();
        }
        else if(e.getSource() == menu.m34){
            this.panel.getTSP();
        }
    }
}
