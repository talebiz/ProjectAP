package view.panels;

import controller.GameManger;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanel extends JPanel {
    public MyPanel() {
        MyFrame.getInstance().add(this);
        setBackground(Color.BLACK);
        setVisible(true);
        setLayout(null);
    }

    protected abstract void setContent();
}