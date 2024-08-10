package controller;

import view.panels.menuPanels.MainMenuPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameManager {
    private static GameManager gameManager;
    private int totalXP;

    private GameManager() {
        minimizeAllTabs();
        MainMenuPanel.getInstance().setVisible(true);
    }

    public static GameManager getInstance() {
        if (gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    private void minimizeAllTabs() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_META);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_H);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_M);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_M);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_H);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_META);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTotalXP(int totalXP) {
        this.totalXP += totalXP;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }
}