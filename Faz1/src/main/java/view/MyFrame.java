package view;

import controller.Util.Constant;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private static MyFrame frame;

    private MyFrame() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 50));
        setSize(Constant.FRAME_SIZE);
        setLayout(null);
        setVisible(true);
    }

    public static MyFrame getInstance() {
        if (frame == null) frame = new MyFrame();
        return frame;
    }
}