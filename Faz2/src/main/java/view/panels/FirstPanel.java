package view.panels;

import controller.Collision;
import controller.EnemyBuilder;
import controller.EntityData;
import controller.Update;
import controller.Util.Direction;
import model.*;
import model.enemies.*;
import view.MyFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static controller.Util.Constant.*;

public class FirstPanel extends GamePanel {
    private static FirstPanel firstPanel;
    private Timer elapsedTime;
    private int minute, second, wave;
    private boolean showWave;
    private boolean isAthenaEmpower;

    private FirstPanel() {
        super();
        rigid = false;
        isometric = false;
        setFocusable(true);
        requestFocusInWindow();
        setSize(NORMAL_PANEL_SIZE);
        setLocation(NORMAL_PANEL_LOCATION);
        wave = 1;
        showWave = true;
        setContent();
    }

    public static FirstPanel getInstance() {
        if (firstPanel == null) firstPanel = new FirstPanel();
        return firstPanel;
    }

    public void makeNew() {
//        new GamePanel(){{
//            setLocation(1200,800);
//            setSize(400,400);
//            setVisible(true);
//        }};
        setVisible(true);
        setKeyListener();
        setSize(NORMAL_PANEL_SIZE);
        setLocation(NORMAL_PANEL_LOCATION);
        epsilon.setX(1000);
        epsilon.setY(500);
        wave = 1;
        Collision.startCheckCollision();
        EnemyBuilder.build(wave);
        Update.updatePaint();
    }

    @Override
    protected void setContent() {
        super.setContent();
        //TODO UNCOMMENT THIS
//        setResizePanelTimer();
//        setMovePanelTimer();
        setElapsedTime();
        setKeyListener();
        setShootListener();
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

    private void setShootListener() {
        this.addMouseListener(new GameMouseListener());
    }

    private void setDirectionAndShoot(double xClicked, double yClicked) {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        double distance = Math.hypot(xClicked - xEpsilon, yClicked - yEpsilon);
        double xMove = SHOT_SPEED * (xClicked - xEpsilon) / distance;
        double yMove = SHOT_SPEED * (yClicked - yEpsilon) / distance;
        if (isAthenaEmpower) {
            athenaEmpower(xEpsilon, yEpsilon, xMove, yMove);
        } else {
            new Shot(xEpsilon, yEpsilon, xMove, yMove, 5, Shot.KindOfShot.EPSILON_SHOT, true);
        }
    }

    private void athenaEmpower(double xEpsilon, double yEpsilon, double xMove, double yMove) {
        Timer timer = new Timer(50, e ->
                new Shot(xEpsilon, yEpsilon, xMove, yMove, 5, Shot.KindOfShot.EPSILON_SHOT, true));
        timer.start();
        Timer end = new Timer(170, e -> timer.stop());
        end.setRepeats(false);
        end.start();
    }

    private void setShootSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SHOOT_SOUND);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
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

    private void pauseGamePanel() {
//        for (Entity entity : Entity.getEntities()) entity.stopMove();
//        for (Shot shot : Shot.list()) shot.stopMove();
//        movePanelTimer.stop();
//        resizePanelTimer.stop();
//        elapsedTime.stop();
//        Collision.stopCheckCollision();
//        EnemyBuilder.stopBuild();
//        setLocation(2000, 1500);
//        StorePanel.getInstance().setVisible(true);
    }

    public void resumeGamePanel() {
        for (Entity entity : EntityData.getEntities()) entity.startMove();
        for (Shot shot : EntityData.getShots()) shot.startMove();
        movePanelTimer.start();
        resizePanelTimer.start();
        elapsedTime.start();
        Collision.startCheckCollision();
        EnemyBuilder.continueBuild();
        setBounds(GAME_PANEL_X, GAME_PANEL_Y, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        setKeyListener();
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

    public void setKeyListener() {
        this.requestFocusInWindow();
        this.addKeyListener(new GameKeyListener());
    }

    public void setShowWave(boolean showWave) {
        this.showWave = showWave;
    }

    public void setAthenaEmpower(boolean athenaEmpower) {
        isAthenaEmpower = athenaEmpower;
    }

    class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W:
                    epsilon.setMovingUp(true);
                    break;
                case KeyEvent.VK_S:
                    epsilon.setMovingDown(true);
                    break;
                case KeyEvent.VK_D:
                    epsilon.setMovingRight(true);
                    break;
                case KeyEvent.VK_A:
                    epsilon.setMovingLeft(true);
                    break;
                case KeyEvent.VK_P:
                    pauseGamePanel();
                    break;
            }
            if (!epsilon.isRunning()) {
                epsilon.startMove();
//                setEpsilonAcceleration();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W:
                    epsilon.setMovingUp(false);
                    break;
                case KeyEvent.VK_S:
                    epsilon.setMovingDown(false);
                    break;
                case KeyEvent.VK_D:
                    epsilon.setMovingRight(false);
                    break;
                case KeyEvent.VK_A:
                    epsilon.setMovingLeft(false);
                    break;
            }
            if (!epsilon.isMovingUp() && !epsilon.isMovingDown() && !epsilon.isMovingRight()
                    && !epsilon.isMovingLeft() && epsilon.isRunning()) {
                epsilon.stopMove();
            }
        }
    }

    class GameMouseListener implements MouseListener {
        boolean canShot = true;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (canShot) {
                setDirectionAndShoot(e.getX() + getX(), e.getY() + getY());
                setShootSound();
                canShot = false;
                new Timer(250, a -> canShot = true) {{
                    setRepeats(false);
                }}.start();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}