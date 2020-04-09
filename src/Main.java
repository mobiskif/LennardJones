import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class Main extends Applet {
    public Main() throws HeadlessException {
        super();
        setLayout( new BorderLayout());
        add(new MainForm().$$$getRootComponent$$$());
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Lennard-Jones");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);
    }
}
