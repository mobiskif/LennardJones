package ru;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Athom extends Container implements Runnable {
    Image image;
    int W,H,id;
    double Fx,Fy;
    Model model;

    public Athom(int i, int x, int y, Model model) {
        super();
        id=i;
        this.model=model;
        double imgScale = 0.17; //размер атома водорода: 50А=5*10e-9, размер полотна 500А, т.е. 1 пиксель = 1 А
        try {
            image = ImageIO.read(new File("src/res/athom.png"));
            W = (int) (image.getWidth(this) * imgScale);
            H = (int) (image.getHeight(this) * imgScale);
            image = image.getScaledInstance(W, H, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(W, H));
            setSize(W,H);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLocation(x, y);
        //run();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, getLocation().x, getLocation().y, this);
        Font oldfont = g.getFont();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        g.drawString(""+getLocation().x +","+getLocation().y,getLocation().x+4,getLocation().y+H/2+8);
        g.setFont(oldfont);
        //g.drawString(String.format("%1$,.2f", Fy), getLocation().x, getLocation().y);
        //g.drawString(String.format("%1$,.2f", Fx),getLocation().x + W-10,getLocation().y+W);
    }

    @Override
    public void run() {
        /*
        Component[] components = model.components;
        if (model.components!=null) {
            for (int i = 0; i < components.length; i++) {
                if (i != this.id) {
                    System.out.println(id + " ->"+i + " X=" + components[i].getX());
                }
            }
        }
        */
       if (model.components!=null) model.calculate2(this);
    }
}
