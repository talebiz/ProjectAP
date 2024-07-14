package view.panels;

import view.MyFrame;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanel extends JPanel {
    public MyPanel() {
        MyFrame.getInstance().add(this);
        setBackground(new Color(40,40,40));
        setVisible(false);
        setLayout(null);
    }

    protected abstract void setContent();
}