package view.panels;

import controller.Collision;
import controller.EnemyBuilder;
import controller.Util.Direction;
import model.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

import static controller.Util.Constant.*;

public class GamePanel extends MyPanel {
    private static GamePanel gamePanel;
    Timer elapsedTime;
    Timer resizePanelTimer;
    Timer movePanelTimer;
    private final Epsilon epsilon;
    private boolean movingUp, movingDown, movingRight, movingLeft;
    private int minute, second, wave;
    private boolean showWave;
    private boolean isAthenaEmpower;

    private GamePanel() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setSize(NORMAL_PANEL_SIZE);
        this.setLocation(NORMAL_PANEL_LOCATION);
        epsilon = Epsilon.getInstance();
        wave = 1;
        showWave = true;
        setContent();
    }

    public static GamePanel getInstance() {
        if (gamePanel == null) gamePanel = new GamePanel();
        return gamePanel;
    }

    public void makeNew() {
        this.setVisible(true);
        this.setKeyListener();
        this.setSize(NORMAL_PANEL_SIZE);
        this.setLocation(NORMAL_PANEL_LOCATION);
        epsilon.setX(1000);
        epsilon.setY(500);
        wave = 1;
        Collision.startCheckCollision();
        EnemyBuilder.build(wave);
    }

    @Override
    protected void setContent() {
        setResizePanelTimer();
        setMovePanelTimer();
        setElapsedTime();
        setKeyListener();
        setShootListener();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawHUI(g2D);
        drawEpsilon(g2D);
        drawShots(g2D);
        drawSquarantine(g2D);
        drawTrigorath(g2D);
        drawCollectible(g2D);
        if (showWave) drawWave(g2D);
    }

    private void drawHUI(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("", Font.PLAIN, 18));
        g2D.drawString(minute + " : " + second, GAME_PANEL_WIDTH - 65, 30);
        g2D.drawString("❤ " + epsilon.getHP(), 20, 30);
        g2D.drawString("☘︎ " + epsilon.getXP(), GAME_PANEL_WIDTH / 2 - 130, 30);
        g2D.drawString("Wave: " + wave, GAME_PANEL_WIDTH / 2 + 70, 30);
    }

    private void drawEpsilon(Graphics2D g2D) {
        g2D.drawImage(EPSILON_IMAGE,
                (int) epsilon.getX() - GAME_PANEL_X,
                (int) epsilon.getY() - GAME_PANEL_Y,
                EPSILON_SIZE,
                EPSILON_SIZE,
                null);
    }

    private void drawShots(Graphics2D g2D) {
        for (int i = 0; i < Shot.list().size(); i++) {
            if (Shot.list().get(i).isAppear()) {
                g2D.setColor(Color.WHITE);
                g2D.fillOval(
                        (int) Shot.list().get(i).getX() - GAME_PANEL_X,
                        (int) Shot.list().get(i).getY() - GAME_PANEL_Y,
                        SHOT_SIZE,
                        SHOT_SIZE);
            } else {
                Shot.list().remove(i);
            }
        }
    }

    private void drawSquarantine(Graphics2D g2D) {
        for (int i = 0; i < Squarantine.list().size(); i++) {
            Squarantine squarantine = Squarantine.list().get(i);
            if (Squarantine.list().get(i).isAppear()) {
                g2D.setStroke(new BasicStroke(6));
                g2D.setColor(Color.GREEN);
                g2D.drawPolygon(squarantine.getXVertex(), squarantine.getYVertex(), 4);
            } else {
                Squarantine.list().remove(squarantine);
                Enemy.getEnemies().remove(squarantine);
                Entity.getEntities().remove(squarantine);
            }
        }
    }

    private void drawTrigorath(Graphics2D g2D) {
        for (int i = 0; i < Trigorath.list().size(); i++) {
            Trigorath trigorath = Trigorath.list().get(i);
            if (Trigorath.list().get(i).isAppear()) {
                g2D.setStroke(new BasicStroke(6));
                g2D.setColor(Color.YELLOW);
                g2D.drawPolygon(trigorath.getXVertex(), trigorath.getYVertex(), 3);
            } else {
                Trigorath.list().remove(trigorath);
                Enemy.getEnemies().remove(trigorath);
                Entity.getEntities().remove(trigorath);
            }
        }
    }

    private void drawCollectible(Graphics2D g2D) {
        for (int i = 0; i < Collectible.list().size(); i++) {
            if (Collectible.list().get(i).isAppear()) {
                g2D.setColor(Color.PINK);
                g2D.drawString("☘︎",
                        (int) Collectible.list().get(i).getX() - GAME_PANEL_X,
                        (int) Collectible.list().get(i).getY() - GAME_PANEL_Y);
            } else {
                Collectible.list().remove(i);
            }
        }
    }

    private void drawWave(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("", Font.BOLD, 50));
        g2D.drawString("Wave " + wave,
                GAME_PANEL_WIDTH / 2 - 100,
                GAME_PANEL_HEIGHT / 2);
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

    private void setResizePanelTimer() {
        resizePanelTimer = new Timer(40, e -> {
            minimizePanel();
            keepEpsilonInPanel();
        });
        resizePanelTimer.start();
    }

    private void minimizePanel() {
        if (GAME_PANEL_HEIGHT > 500) {
            GAME_PANEL_HEIGHT -= 2;
            GAME_PANEL_Y++;
        }
        if (GAME_PANEL_WIDTH > 700) {
            GAME_PANEL_WIDTH -= 2;
            GAME_PANEL_X++;
        }
        this.setBounds(
                GAME_PANEL_X,
                GAME_PANEL_Y,
                GAME_PANEL_WIDTH,
                GAME_PANEL_HEIGHT);
    }

    private void keepEpsilonInPanel() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        if (xEpsilon < GAME_PANEL_X) epsilon.setX(GAME_PANEL_X);
        if (xEpsilon + EPSILON_SIZE > GAME_PANEL_X + GAME_PANEL_WIDTH)
            epsilon.setX(GAME_PANEL_X + GAME_PANEL_WIDTH - EPSILON_SIZE);
        if (yEpsilon < GAME_PANEL_Y) epsilon.setY(GAME_PANEL_Y);
        if (yEpsilon + EPSILON_SIZE > GAME_PANEL_Y + GAME_PANEL_HEIGHT)
            epsilon.setY(GAME_PANEL_Y + GAME_PANEL_HEIGHT - EPSILON_SIZE);
    }

    private void setMovePanelTimer() {
        movePanelTimer = new Timer(30, e -> {
            if (movingUp) GAME_PANEL_Y -= GAME_PANEL_SPEED;
            if (movingDown) GAME_PANEL_Y += GAME_PANEL_SPEED;
            if (movingRight) GAME_PANEL_X += GAME_PANEL_SPEED;
            if (movingLeft) GAME_PANEL_X -= GAME_PANEL_SPEED;
            setLocation(GAME_PANEL_X, GAME_PANEL_Y);
            keepEpsilonInPanel();
        });
        movePanelTimer.start();
    }

    private void setShootListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDirectionOfShot(e.getX() + GAME_PANEL_X, e.getY() + GAME_PANEL_Y);
                setShootSound();
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
        });
    }

    private void setDirectionOfShot(double xClicked, double yClicked) {
        double xEpsilon = epsilon.getX() + EPSILON_SIZE / 2.0;
        double yEpsilon = epsilon.getY() + EPSILON_SIZE / 2.0;
        double distance = Math.hypot(xClicked - xEpsilon, yClicked - yEpsilon);
        double xMove = SHOT_SPEED * (xClicked - xEpsilon) / distance;
        double yMove = SHOT_SPEED * (yClicked - yEpsilon) / distance;
        if (isAthenaEmpower) {
            athenaEmpower(xEpsilon, yEpsilon, xMove, yMove);
        } else {
            new Shot(xEpsilon - SHOT_SIZE / 2.0, yEpsilon - SHOT_SIZE / 2.0, xMove, yMove);
        }
    }

    private void athenaEmpower(double xEpsilon, double yEpsilon, double xMove, double yMove) {
        Timer timer = new Timer(50, e -> new Shot(
                xEpsilon - SHOT_SIZE / 2.0,
                yEpsilon - SHOT_SIZE / 2.0,
                xMove,
                yMove));
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

    public void increasePanelSize(Direction direction) {
        Timer increase = new Timer(20, e -> {
            switch (direction) {
                case Direction.UP:
                    if (GAME_PANEL_Y > 0) {
                        GAME_PANEL_HEIGHT += 4;
                        GAME_PANEL_Y -= 4;
                    }
                    break;
                case Direction.DOWN:
                    if (GAME_PANEL_Y + GAME_PANEL_HEIGHT < FRAME_SIZE.getHeight()) {
                        GAME_PANEL_HEIGHT += 4;
                    }
                    break;
                case Direction.RIGHT:
                    if (GAME_PANEL_X + GAME_PANEL_WIDTH < FRAME_SIZE.getWidth()) {
                        GAME_PANEL_WIDTH += 4;
                    }
                    break;
                case Direction.LEFT:
                    if (GAME_PANEL_X > 0) {
                        GAME_PANEL_WIDTH += 4;
                        GAME_PANEL_X -= 4;
                    }
                    break;
            }
            this.setBounds(
                    GAME_PANEL_X,
                    GAME_PANEL_Y,
                    GAME_PANEL_WIDTH,
                    GAME_PANEL_HEIGHT);

        });
        increase.start();
        Timer end = new Timer(400, e -> increase.stop());
        end.setRepeats(false);
        end.start();
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
        resizePanelTimer.stop();
        movePanelTimer.stop();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (EPSILON_SIZE > 1000) timer.cancel();
                epsilon.moveX(-2);
                epsilon.moveY(-2);
                EPSILON_SIZE += 4;
            }
        }, 10, 20);
        java.util.Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (GAME_PANEL_HEIGHT < 4 || GAME_PANEL_WIDTH < 4) {
                    timer.cancel();
                    closeGame();
                }
                GAME_PANEL_HEIGHT -= 2;
                GAME_PANEL_Y++;
                GAME_PANEL_WIDTH -= 2;
                GAME_PANEL_X++;
                GamePanel.this.setBounds(
                        GAME_PANEL_X,
                        GAME_PANEL_Y,
                        GAME_PANEL_WIDTH,
                        GAME_PANEL_HEIGHT);
            }
        }, 6000, 20);
    }

    public void closeGame() {
        Entity.getEntities().clear();
        Enemy.getEnemies().clear();
        Squarantine.list().clear();
        Trigorath.list().clear();
        Collision.stopCheckCollision();
        EnemyBuilder.stopBuild();
        EnemyBuilder.amount = 0;
        minute = 0;
        second = 0;
        setLocation(2000, 1500);
        MainMenuPanel.getInstance().setVisible(true);
    }

    private void pauseGamePanel() {
        for (Entity entity : Entity.getEntities()) entity.stopMove();
        for (Shot shot : Shot.list()) shot.stopMove();
        movePanelTimer.stop();
        resizePanelTimer.stop();
        elapsedTime.stop();
        Collision.stopCheckCollision();
        EnemyBuilder.stopBuild();
        setLocation(2000, 1500);
        StorePanel.getInstance().setVisible(true);
    }

    public void resumeGamePanel() {
        for (Entity entity : Entity.getEntities()) {
            entity.startMove();
        }
        for (Shot shot : Shot.list()) {
            shot.startMove();
        }
        movePanelTimer.start();
        resizePanelTimer.start();
        elapsedTime.start();
        Collision.startCheckCollision();
        EnemyBuilder.continueBuild();
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

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
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
}