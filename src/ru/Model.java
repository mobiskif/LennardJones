package ru;

import java.awt.*;

public class Model {
    final double epsilon = 9.0; //Эпсилон - глубина потенциальной ямы (энергия связи), эВ
    final double sigma = 80; //Сигма - расстояние, где F=0, типовое 3.40 A
    double[][] R;
    double[][] F;
    double[][] Fx;
    double[][] Fy;
    int W = 400, H = 300, D;
    Component[] components;

    double F(double r) {
        double a = Math.pow((sigma / r), 12);
        double b = Math.pow((sigma / r), 6);
        return 4 * epsilon * (a - b);
    }

    public void calculate(Component[] components) {

        D = components[0].getWidth();
        R = new double[components.length][components.length];
        F = new double[components.length][components.length];
        Fx = new double[components.length][components.length];
        Fy = new double[components.length][components.length];
        int Xi, Xj, Yi, Yj;
        for (int i = 0; i < components.length; i++) {
            Xi = components[i].getLocation().x;
            Yi = components[i].getLocation().y;
            for (int j = i; j < components.length; j++) {
                if (i == j) {
                    R[i][j] = 0;
                    F[i][j] = 0;
                    Fx[i][j] = 0;
                    Fy[i][j] = 0;
                } else {
                    Xj = components[j].getLocation().x;
                    Yj = components[j].getLocation().y;

                    R[i][j] = Math.sqrt((Xj - Xi) * (Xj - Xi) + (Yj - Yi) * (Yj - Yi));
                    F[i][j] = F(R[i][j]);

                    double cosF = (Xi-Xj)/R[i][j];
                    double sinF = (Yj-Yi)/R[i][j];
                    Fx[i][j] = F[i][j]*cosF;
                    Fy[i][j] = F[i][j]*sinF;
                    Fx[j][i]=-Fx[i][j];
                    Fy[j][i]=-Fy[i][j];
                }
            }

            double ffx = 0;
            double ffy = 0;
            for (int j = 0; j < components.length; j++) {
                ffx += Fx[i][j];
                ffy += Fy[i][j];
            }
            Athom athom = (Athom) components[i];
            athom.Fx = ffx;
            athom.Fy = ffy;
            //System.out.println(i+" Fx="+ffx+", Fy="+ffy);

            int dX = (int) Math.round(Math.log(athom.Fx));
            int x = components[i].getLocation().x + dX;
            if (x < 0) x = 0;
            if (x > (W - D)) x = W - D;

            int dY = (int) Math.round(Math.log(athom.Fy));
            int y = components[i].getLocation().y - dY;
            if (y < 0) y = 0;
            if (y > (H - D)) y = H - D;

            //System.out.println(i+" X: "+x+" "+ dX + ", Y: "+y+" " + dY);
            components[i].setLocation(x, y);
            //calculate2((Athom) components[i]);
        }

    }

    public void calculate2(Athom athom) {
        D = components[0].getWidth();
        double[] R = new double[components.length];
        double[] F = new double[components.length];
        double[] Fx = new double[components.length];
        double[] Fy = new double[components.length];
        int Xi, Xj, Yi, Yj;
        for (int i = 0; i < components.length; i++) {
            Xi = components[i].getLocation().x;
            Yi = components[i].getLocation().y;
            //int j = athom.id
            //for (int j = i; j < components.length; j++) {
                if (i == athom.id) {
                    R[i] = 0;
                    F[i] = 0;
                    Fx[i] = 0;
                    Fy[i] = 0;
                } else {
                    Xj = athom.getLocation().x;
                    Yj = athom.getLocation().y;

                    R[i] = Math.sqrt((Xj - Xi) * (Xj - Xi) + (Yj - Yi) * (Yj - Yi));
                    F[i] = F(R[i]);

                    double cosF = (Xi-Xj)/R[i];
                    double sinF = (Yj-Yi)/R[i];
                    Fx[i] = F[i]*cosF;
                    Fy[i] = F[i]*sinF;
                }
            //}

            double ffx = 0;
            double ffy = 0;
            for (int j = 0; j < components.length; j++) {
                ffx += Fx[j];
                ffy += Fy[j];
            }
            //Athom athom = (Athom) components[i];
            athom.Fx = ffx;
            athom.Fy = ffy;
            //System.out.println(i+" Fx="+ffx+", Fy="+ffy);

            int dX = (int) Math.round(Math.log(athom.Fx));
            int x = athom.getLocation().x + dX;
            if (x < 0) x = 0;
            if (x > (W - D)) x = W - D;

            int dY = (int) Math.round(Math.log(athom.Fy));
            int y = athom.getLocation().y - dY;
            if (y < 0) y = 0;
            if (y > (H - D)) y = H - D;

            System.out.println(i+" X: "+x+" "+ dX + ", Y: "+y+" " + dY);
            athom.setLocation(x, y);
        }

    }

    public void animate(Component[] components) {
        for (Component component : components) {
            Athom athom = (Athom) component;

            int dX = (int) Math.round(Math.log(athom.Fx));
            int x = component.getLocation().x + dX;
            if (x < 0) x = 5;
            if (x > (W - D)) x = W - D - 5;

            int dY = (int) Math.round(Math.log(athom.Fy));
            int y = component.getLocation().y - dY;
            if (y < 0) y = 5;
            if (y > (H - D)) y = H - D - 5;

            //System.out.println(i+" X: "+x+" "+ dX + ", Y: "+y+" " + dY);
            component.setLocation(x, y);
        }
    }

    public void setConstrains(int width, int height) {
        W = width;
        H = height;
    }

    public void setComponents(Component[] components) {
        this.components = components;

    }
}
