package controller;

import view.MyFrame;
import view.panels.GamePanel;

import javax.swing.*;

public abstract class Update {
    public static void updatePaint() {
//        new Thread(() -> {
//            while (true) {
//                GamePanel.getInstance().repaint();
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
        new Timer(20, e -> {
            for (GamePanel panel : GamePanel.getGamePanels()) panel.repaint();
            PanelController.setAllLocalPanel();
            PanelController.keepEpsilonInPanel();
            MyFrame.getInstance().repaint();

        }).start();
    }
}
