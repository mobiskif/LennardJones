package ru;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame("Lennard-Jones");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);
    }
}
