package view.panels;

import model.Shot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static controller.Util.Constant.MAIN_MENU_PANEL_SIZE;

public class StorePanel extends MyPanel {
    private static StorePanel storePanel;

    private StorePanel() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setSize(MAIN_MENU_PANEL_SIZE);
        this.setLocation(0,0);
        this.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        this.setContent();
    }

    public static StorePanel getInstance() {
        if (storePanel == null) storePanel = new StorePanel();
        return storePanel;
    }

    @Override
    protected void setContent() {
        setKeyListener();
    }

    private void setKeyListener() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) resumeGamePanel();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void resumeGamePanel() {
        GamePanel.getInstance().resizePanelTimer.start();
        GamePanel.getInstance().moveEpsilonTimer.start();
        for (Shot shot : Shot.list()) {
            shot.getMoveTimer().start();
        }
        this.setVisible(false);
        GamePanel.getInstance().setVisible(true);
    }
}
