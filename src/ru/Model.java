package ru;

import java.awt.*;

public class Model {
    final double epsilon = 9.0; //Эпсилон - глубина потенциальной ямы (энергия связи), эВ
    final double sigma = 80; //Сигма - расстояние, где F=0, типовое 3.40 A
    int W = 400, H = 300; //размер атома водорода: 50А=5*10e-9, размер полотна 500А, т.е. 1 пиксель = 1 А
    //ArrayList<Component> components = new ArrayList<>();
    Component[] components;

    private boolean equal(Component a, Component b) {
        return a.getX()==b.getX() && a.getY()==b.getY();
    }

    double R(Component c1, Component c2) {
        int Xj, Xi, Yi, Yj;
        double Rij;
        Xi = c1.getLocation().x;
        Yi = c1.getLocation().y;
        Xj = c2.getLocation().x;
        Yj = c2.getLocation().y;
        Rij = Math.sqrt((Xj - Xi) * (Xj - Xi) + (Yj - Yi) * (Yj - Yi));
        return Rij;
    }

    double F(double r) {
        double a = Math.pow((sigma / r), 12);
        double b = Math.pow((sigma / r), 6);
        return 4 * epsilon * (a - b);
    }

    public Dimension calculateDeltaXY(Component A) {
        double Fx = 0;
        double Fy = 0;
        //System.out.println(A);
        if (components != null) {
            for (int j = 0; j < components.length; j++) {
                Component B = components[j];
                if (!equal(A,B)) {
                    //вычислить модуль силы взаимодействия с элементом
                    double R = R(A, B);
                    double F = F(R);

                    //вычислить углы
                    double cosX = (B.getX() - A.getX()) / R;
                    double sinY = (B.getY() - A.getY()) / R;
                    //System.out.println("     "+j+" cosX="+cosX+" sinY="+sinY);

                    //вычислить и сложить проекцию силы на оси
                    Fx += -F * cosX;
                    Fy += -F * sinY;
                    //System.out.println("     "+j+" R="+R+" F="+F);
                }
            }
        }
        //System.out.println("     Fx="+Fx+" Fy="+Fy);

        //вычислить смещение
        int x = A.getX();
        int y = A.getY();
        //System.out.println("     x="+x+" y="+y);
        if (Fx>=0) x = (int) Math.round(A.getX() + Math.log(Fx+1)); else x = (int) Math.round(A.getX() - Math.log(-Fx+1));
        if (Fy>=0) y = (int) Math.round(A.getY() + Math.log(Fy+1) ); else y = (int) Math.round(A.getY() - Math.log(-Fy+1));
        //System.out.println("     x="+x+" y="+y);
        return new Dimension(x,y);
    }

}
