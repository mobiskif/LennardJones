package ru;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Athom extends Container implements Runnable {
    private Image image;
    private final Model model;
    private boolean isStarted=false;

    public Athom(int x, int y, Model m) {
        super();
        model = m;
        setLocation(x, y);
        try {
            image = ImageIO.read(new File("src/res/athom.png"));
            int diameter = (int) (model.sigma / 1.5);
            image = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(diameter, diameter));
            setSize(diameter, diameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new Thread(this).start();
        addActionListener(actionEvent -> {
            System.out.println("---" + actionEvent);
        });

        Athom a=this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isStarted=!isStarted;
                if (isStarted) new Thread(a).start();
            }
        });


    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, getLocation().x, getLocation().y, this);
        Font oldfont = g.getFont();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        g.drawString("" + getX() + "," + getY(), getX() + 4, getY() + getHeight() / 2 + 8);
        g.setFont(oldfont);
        //g.drawString(String.format("%1$,.2f", Fy), getX(), getY());
        //g.drawString(String.format("%1$,.2f", Fx), getX() + getWidth() - g.getFont().getSize(), getY() + getHeight());
    }

    @Override
    public void run() {
        while (isStarted) {
            Dimension dimension = model.deltaXY(this);
            setLocation(dimension.width, dimension.height);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
