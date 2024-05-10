package controller;

import view.panels.MainMenuPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameManger {
    private static GameManger gameManger;
    int totalXP;

    private GameManger() {
        minimizeAllTabs();
        MainMenuPanel.getInstance().setVisible(true);
    }

    public static GameManger getInstance() {
        if (gameManger == null) gameManger = new GameManger();
        return gameManger;
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
            robot.keyRelease(KeyEvent.VK_H);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_META);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}