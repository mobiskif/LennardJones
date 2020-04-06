package ru;

import javax.swing.*;
import java.awt.*;

public class AthomPanel extends JPanel {
    final Model model = new Model();

    public AthomPanel() {
        super();
        setPreferredSize(new Dimension(model.W, model.H));
        addRandomAthoms();
    }

    public void addRandomAthoms() {
        this.removeAll();
        int id=0;
        add(new Athom(id++,30, 150 - (int) (model.sigma * 1.12), model));
        add(new Athom(id++, 30, 150, model));
        add(new Athom(id++, 30 + (int) (model.sigma * 1.12), 150, model));
        add(new Athom(id++, 30, (int) (150 + model.sigma), model));
        add(new Athom(id++, 150, 160, model));
        add(new Athom(id++, 125, 80, model));
        add(new Athom(id++, 175, 230, model));
        model.setComponents(getComponents());
    }

    void drawCommonInfoStrings(Graphics g) {
        g.drawString(model.W + "," + model.H, getWidth() - 55, 14);
        g.drawString("E=" + model.epsilon + ", S=" + model.sigma, getWidth() / 2, 14);
        g.drawString("X " + getWidth(), getWidth() - 45, getHeight() - 8);
        g.drawString("Y " + getHeight(), 4, 14);
        g.drawString("0,0", 4, getHeight() - 8);
    }

    void drawAthomsConnectionLines(Component[] components, Graphics g) {
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
                    //if (model.F != null) g.drawString(String.format("%1$,.2f", model.F[i][j]), (Xi + Xj) / 2, (Yi + Yj) / 2);
                    //if (model.R != null) g.drawString(String.format("%1$,.0f", model.R[i][j]), (Xi + Xj) / 2, (Yi + Yj) / 2 + 12);
                    g.setFont(oldfont);
                }
            }
        }
    }

    void paintAllAthoms(Component[] components, Graphics g) {
        for (Component c : components) c.paint(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        model.setConstrains(getWidth(), getHeight());
        drawCommonInfoStrings(g);
        drawAthomsConnectionLines(getComponents(), g);
        paintAllAthoms(getComponents(), g);
    }

    public void computeAthomsLocationsAndPaint() {
        //model.animate(getComponents());
        model.calculate(getComponents());
        repaint();
    }

}
