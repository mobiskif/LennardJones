package ru;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Athom extends Container {
    Image image;
    int W,H;
    double Fx, Fy;

    public Athom(int x, int y) {
        super();
        double imgScale = 0.17; //размер атома водорода: 50А=5*10e-9, размер полотна 500А, т.е. 1 пиксель = 1 А
        try {
            image = ImageIO.read(new File("/res/athom.png"));
            W = (int) (image.getWidth(this) * imgScale);
            H = (int) (image.getHeight(this) * imgScale);
            image = image.getScaledInstance(W, H, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(W, H));
            setSize(W,H);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLocation(x, y);
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
}
