package view.panels;

import controller.Collision;
import controller.EnemyBuilder;
import controller.EntityData;
import controller.Update;
import model.*;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public final class FirstPanel extends GamePanel {
    private static final FirstPanel firstPanel = new FirstPanel();
    private Timer elapsedTime;
    private int minute, second, wave;
    private boolean showWave;

    private FirstPanel() {
        super();
        rigid = false;
        isometric = false;
        exertion = false;
        setSize(NORMAL_PANEL_SIZE);
        setLocation(NORMAL_PANEL_LOCATION);
        wave = 1;
        showWave = true;
        setContent();
        setVisible(true);
        epsilon.setX(1000);
        epsilon.setY(500);
        wave = 1;
        Collision.startCheckCollision();
        EnemyBuilder.build(wave);
        Update.updatePaint();
    }

    public static FirstPanel getInstance() {
        return firstPanel;
    }

    @Override
    protected void setContent() {
        super.setContent();
        setElapsedTime();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawHUI(g2D);
        if (showWave) drawWave(g2D);
    }

    private void drawHUI(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("", Font.PLAIN, 18));
        g2D.drawString(minute + " : " + second, getWidth() - 65, 30);
        g2D.drawString("❤ " + epsilon.getHP(), 20, 30);
        g2D.drawString("☘︎ " + epsilon.getXP(), getWidth() / 2 - 130, 30);
        g2D.drawString("Wave: " + wave, getWidth() / 2 + 70, 30);
    }

    private void drawWave(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("", Font.BOLD, 50));
        g2D.drawString("Wave " + wave,
                getWidth() / 2 - 100,
                getHeight() / 2);
    }

    private void setElapsedTime() {
        elapsedTime = new Timer(1000, e -> {
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
            repaint();
        });
        elapsedTime.start();
    }

    public void nextWave() {
        wave++;
        if (wave > 3) {
            win();
            return;
        }
        EnemyBuilder.build(wave);
        showWave = true;
    }

    private void win() {
//        resizePanelTimer.stop();
//        movePanelTimer.stop();
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (EPSILON_SIZE > 1000) timer.cancel();
//                epsilon.moveX(-2);
//                epsilon.moveY(-2);
//                EPSILON_SIZE += 4;
//            }
//        }, 10, 20);
//        java.util.Timer timer2 = new java.util.Timer();
//        timer2.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (GAME_PANEL_HEIGHT < 4 || GAME_PANEL_WIDTH < 4) {
//                    timer.cancel();
//                    closeGame();
//                }
//                GAME_PANEL_HEIGHT -= 2;
//                GAME_PANEL_Y++;
//                GAME_PANEL_WIDTH -= 2;
//                GAME_PANEL_X++;
//                GamePanel.this.setBounds(
//                        GAME_PANEL_X,
//                        GAME_PANEL_Y,
//                        GAME_PANEL_WIDTH,
//                        GAME_PANEL_HEIGHT);
//            }
//        }, 6000, 20);
    }

    public void closeGame() {
        EntityData.getEntities().clear();
        EntityData.getEnemies().clear();
        EntityData.getShots().clear();
        EntityData.getSquarantines().clear();
        EntityData.getTrigoraths().clear();
        EntityData.getOmenocts().clear();
        EntityData.getNecropicks().clear();
        EntityData.getArchmires().clear();
        EntityData.getWyrms().clear();
        revalidate();
        repaint();
        Epsilon.getInstance().setHP(EPSILON_HP);
        Collision.stopCheckCollision();
        EnemyBuilder.stopBuild();
        EnemyBuilder.amount = 0;
        minute = 0;
        second = 0;
        MyFrame.getInstance().remove(this);
        MyFrame.getInstance().revalidate();
        MyFrame.getInstance().repaint();
        MainMenuPanel.getInstance().setVisible(true);
    }

    public void pauseGamePanel() {
        for (Entity entity : EntityData.getEntities()) entity.stopMove();
        for (Shot shot : EntityData.getShots()) shot.stopMove();
        for(GamePanel gamePanel:GamePanel.getGamePanels()) gamePanel.pause();
        if (increasePanelTimer != null && increasePanelTimer.isRunning()) increasePanelTimer.stop();
        elapsedTime.stop();
        EnemyBuilder.stopBuild();
        Collision.stopCheckCollision();
    }

    public void resumeGamePanel() {
        for (Entity entity : EntityData.getEntities()) entity.continueMove();
        for (Shot shot : EntityData.getShots()) shot.continueMove();
        for(GamePanel gamePanel:GamePanel.getGamePanels()) gamePanel.resume();
        elapsedTime.start();
        EnemyBuilder.continueBuild();
        Collision.startCheckCollision();
    }

    //TODO ACCELERATION IN LISTENER CLASS ANH THIS METHOD
//    private void setEpsilonAcceleration() {
//        EPSILON_SPEED = 5;
//        Timer acceleration = new Timer(50, e -> EPSILON_SPEED++);
//        acceleration.start();
//        Timer end = new Timer(500, e -> acceleration.stop());
//        end.setRepeats(false);
//        end.start();
//    }

    public void setShowWave(boolean showWave) {
        this.showWave = showWave;
    }
}