package ru;

import java.awt.*;

public class Model {
    double epsilon = 10.0; //Эпсилон - глубина потенциальной ямы (энергия связи), эВ
    double sigma = 150; //Сигма - расстояние, где F=0, типовое 3.40 A
    double R[][], F[][], Fx[][], Fy[][];
    int W = 500, H = 500, D;

    double F(double r) {
        double a = Math.pow((sigma / r), 12);
        double b = Math.pow((sigma / r), 6);
        double ff = 4 * epsilon * (a - b);
        return ff;
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
            ((Athom) components[i]).Fx = ffx;
            ((Athom) components[i]).Fy = ffy;
            //System.out.println(i+" Fx="+ffx+", Fy="+ffy);
        }

    }

    public void animate(Component[] components) {
        for (int i = 0; i < components.length; i++) {
            Athom athom = (Athom) components[i];

            int dX = (int) Math.round(Math.log(athom.Fx));
            int x = components[i].getLocation().x + dX;
            if (x < 0) x = 5;
            if (x > (W - D)) x = W - D-5;

            int dY = (int) Math.round(Math.log(athom.Fy));
            int y = components[i].getLocation().y - dY;
            if (y < 0) y = 5;
            if (y > (H - D)) y = H - D-5;

            //System.out.println(i+" X: "+x+" "+ dX + ", Y: "+y+" " + dY);
            components[i].setLocation(x, y);
        }
    }

    public void setConstrains(int width, int height) {
        W = width;
        H = height;
    }
}
