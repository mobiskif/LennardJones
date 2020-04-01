package ru;

import javax.swing.*;
import java.awt.*;

public class AthomPanel extends JPanel {
    Component[] components;
    Model model = new Model();

    public AthomPanel() {
        super();
        setPreferredSize(new Dimension(model.W, model.H));
        //setLayout(null);
        add(new Athom(50, 250));
        add(new Athom(50 + (int) (150 * 1.12), 250));
        add(new Athom(50, 390));
        add(new Athom(50, 250 - (int) (150 * 1.12)));

        add(new Athom(15, 360));
        add(new Athom(125, 180));
        add(new Athom(185, 340));
/*
        add(new Athom(85, 40));
        add(new Athom(285, 140));
        add(new Athom(385, 340));
        add(new Athom(305, 300));
        add(new Athom(400, 80));
        add(new Athom(200, 10));
        add(new Athom(400, 250));
        add(new Athom(190, 235));
*/

        components = getComponents();
        model.calculate(components);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        model.setConstrains(getWidth(), getHeight());
        g.drawString(model.W + "," + model.H, getWidth() - 55, 14);
        g.drawString("E=" + model.epsilon + ", S=" + model.sigma, getWidth() / 2, 14);
        g.drawString("X " + getWidth(), getWidth() - 45, getHeight() - 8);
        g.drawString("Y " + getHeight(), 4, 14);
        g.drawString("0,0", 4, getHeight() - 8);

        for (int i = 0; i < components.length; i++) {
            int dr = components[i].getWidth() / 2;
            for (int j = i; j < components.length; j++) {
                if (i != j) {
                    int Xi = components[i].getLocation().x + dr;
                    int Xj = components[j].getLocation().x + dr;
                    int Yi = components[i].getLocation().y + dr;
                    int Yj = components[j].getLocation().y + dr;

                    Color oldcolor = g.getColor();
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawLine(Xi, Yi, Xj, Yj);
                    g.setColor(oldcolor);

                    Font oldfont = g.getFont();
                    g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
                    if (model.F != null)
                        //g.drawString(String.format("%1$,.2f", model.F[i][j]), (Xi + Xj) / 2, (Yi + Yj) / 2);
                    if (model.R != null)
                        //g.drawString(String.format("%1$,.0f", model.R[i][j]), (Xi + Xj) / 2, (Yi + Yj) / 2 + 12);
                    g.setFont(oldfont);
                }
            }
        }

        for (int i = 0; i < components.length; i++) {
            components[i].paint(g);
        }

    }

    public void calc() {
        model.animate(getComponents());
        model.calculate(getComponents());
        repaint();
    }

}
