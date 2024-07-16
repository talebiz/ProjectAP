package view;

import controller.Util.Constant;
import model.Epsilon;
import view.panels.FirstPanel;
import view.panels.StorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame {
    private static MyFrame frame;

    private MyFrame() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 50));
        setSize(Constant.FRAME_SIZE);
        setLayout(null);
        setVisible(true);
        setKeyListener();
    }

    public static MyFrame getInstance() {
        if (frame == null) frame = new MyFrame();
        return frame;
    }

    public void setKeyListener() {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new GameKeyListener());
    }

    private static class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W -> Epsilon.getInstance().setMovingUp(true);
                case KeyEvent.VK_S -> Epsilon.getInstance().setMovingDown(true);
                case KeyEvent.VK_D -> Epsilon.getInstance().setMovingRight(true);
                case KeyEvent.VK_A -> Epsilon.getInstance().setMovingLeft(true);
                case KeyEvent.VK_P -> {
                    FirstPanel.getInstance().pauseGamePanel();
                    StorePanel.getInstance().setVisible(true);
                }
            }
            if (!Epsilon.getInstance().isRunning()) {
                Epsilon.getInstance().startMove();
//                setEpsilonAcceleration();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W -> Epsilon.getInstance().setMovingUp(false);
                case KeyEvent.VK_S -> Epsilon.getInstance().setMovingDown(false);
                case KeyEvent.VK_D -> Epsilon.getInstance().setMovingRight(false);
                case KeyEvent.VK_A -> Epsilon.getInstance().setMovingLeft(false);
            }
            if (!Epsilon.getInstance().isMovingUp() && !Epsilon.getInstance().isMovingDown() && !Epsilon.getInstance().isMovingRight()
                    && !Epsilon.getInstance().isMovingLeft() && Epsilon.getInstance().isRunning()) {
                Epsilon.getInstance().stopMove();
            }
        }
    }

}