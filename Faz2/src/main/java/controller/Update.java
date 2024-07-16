package controller;

import view.MyFrame;
import view.panels.GamePanel;
import view.panels.StorePanel;

import static controller.EnemyBuilder.amount;

public abstract class Update {
    public static void updatePaint() {
        new Thread(() -> {
            while (true) {
                PanelController.setAllLocalPanel();
                PanelController.keepEpsilonInPanel();
                for (GamePanel panel : GamePanel.getGamePanels()) panel.repaint();
                StorePanel.getInstance().repaint();
                MyFrame.getInstance().repaint();
                waiting();
            }
        }).start();
    }

    private static void waiting() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
