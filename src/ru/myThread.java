package ru;

import javax.swing.*;
import java.awt.*;

public class myThread extends Thread {
    JCheckBox checkBox;

    public myThread() {
        super();
        //checkBox=chb;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            System.out.println("qwe");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
