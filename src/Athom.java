import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.URL;

public class Athom extends Component implements Runnable, MouseMotionListener {
    private Image image;
    private final Model model;
    private boolean isStarted=false;
    int x0,y0;

    public Athom(int x, int y, Model m) {
        super();
        model = m;
        setLocation(x, y);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("jpg/athom.png");
            image = ImageIO.read(is);
            int diameter = (int) (model.sigma / 1.5);
            image = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(diameter, diameter));
            setSize(diameter, diameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new Thread(this).start();
        //addActionListener(actionEvent -> System.out.println("---" + actionEvent));

        addMouseMotionListener(this);

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
        //g.drawRect(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void run() {
        while (isStarted) {
            Dimension dXY = model.calculateDeltaXY(this);
            setLocation(dXY.width, dXY.height);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getParent().repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x1,y1,dx,dy;
        x1 = e.getX(); y1 = e.getY();
        dx = x1 - x0; dy = y1 - y0;
        Athom n = (Athom) e.getSource();
        n.setBounds(n.getX() + dx, n.getY() + dy, n.getWidth(), n.getHeight());
        n.getParent().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
    }
}
