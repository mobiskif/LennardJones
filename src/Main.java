import javax.swing.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame("Lennard-Jones");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);

        //System.out.println("Working Directory is: " + System.getProperty("user.dir"));

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + s);

        //System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());

        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        //System.out.println(location.getFile());
    }
}
