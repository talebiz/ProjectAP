package view.panels;

import controller.Collision;
import controller.Util.Direction;
import model.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static controller.Util.Constant.*;

public class GamePanel extends MyPanel {
    private static GamePanel gamePanel;
    Timer elapsedTime;
    Timer resizePanelTimer;
    Timer movePanelTimer;
    private final Epsilon epsilon;
    private boolean movingUp, movingDown, movingRight, movingLeft;
    int minute, second, wave;
    private boolean showHUI;


    private GamePanel() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        this.setLocation(GAME_PANEL_X, GAME_PANEL_Y);
        epsilon = Epsilon.getInstance();
//        new Squarantine(800, 800);
//        new Squarantine(1000, 150);
//        new Squarantine(1000, 800);
//        new Trigorath(1000, 800);
//        new Trigorath(1000, 300);
        Collision.startCheckCollision();
        setContent();
    }

    public static GamePanel getInstance() {
        if (gamePanel == null) gamePanel = new GamePanel();
        return gamePanel;
    }

    public static void makeNew() {
        gamePanel = new GamePanel();
    }

    @Override
    protected void setContent() {
//        setResizePanelTimer();
        setMovePanelTimer();
        setElapsedTime();
        setKeyListener();
        setShootListener();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (showHUI) drawHUI(g2D);
        drawEpsilon(g2D);
        drawShots(g2D);
        drawSquarantine(g2D);
        drawTrigorath(g2D);
        drawCollectible(g2D);
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
            if (Squarantine.list().get(i).isAppear()) {
                Squarantine squarantine = Squarantine.list().get(i);
                g2D.setStroke(new BasicStroke(6));
                g2D.setColor(Color.GREEN);
                g2D.drawPolygon(squarantine.getXVertex(), squarantine.getYVertex(), 4);
            } else {
                Squarantine squarantine = Squarantine.list().get(i);
                Squarantine.list().remove(squarantine);
                Enemy.getEnemies().remove(squarantine);
                Entity.getEntities().remove(squarantine);
            }
        }
    }

    private void drawTrigorath(Graphics2D g2D) {
        for (int i = 0; i < Trigorath.list().size(); i++) {
            if (Trigorath.list().get(i).isAppear()) {
                Trigorath trigorath = Trigorath.list().get(i);
                g2D.setStroke(new BasicStroke(6));
                g2D.setColor(Color.YELLOW);
                g2D.drawPolygon(trigorath.getXVertex(), trigorath.getYVertex(), 3);
            } else {
                Trigorath trigorath = Trigorath.list().get(i);
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
                g2D.fillOval(
                        (int) Collectible.list().get(i).getX() - GAME_PANEL_X,
                        (int) Collectible.list().get(i).getY() - GAME_PANEL_Y,
                        COLLECTIBLE_SIZE,
                        COLLECTIBLE_SIZE);
            } else {
                Collectible.list().remove(i);
            }
        }
    }

    private void setElapsedTime() {
        elapsedTime = new Timer(1000, e -> {
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
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

    public static void setDirectionOfEnemyMove(Enemy enemy) {
        double xEpsilon = Epsilon.getInstance().getX() + EPSILON_SIZE / 2.0;
        double yEpsilon = Epsilon.getInstance().getY() + EPSILON_SIZE / 2.0;
        double xSquarantine = enemy.getX();
        double ySquarantine = enemy.getY();
        double distance = Math.hypot(xEpsilon - xSquarantine, yEpsilon - ySquarantine);
        enemy.setXMove(enemy.getSpeed() * (xEpsilon - xSquarantine) / distance);
        enemy.setYMove(enemy.getSpeed() * (yEpsilon - ySquarantine) / distance);
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
        new Shot(xEpsilon - SHOT_SIZE / 2.0, yEpsilon - SHOT_SIZE / 2.0, xMove, yMove);
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
//        Timer increase = new Timer(20, e -> {
//            switch (direction) {
//                case Direction.UP:
//                    GAME_PANEL_HEIGHT += 4;
//                    GAME_PANEL_Y -= 4;
//                    break;
//                case Direction.DOWN:
//                    GAME_PANEL_HEIGHT += 4;
//                    break;
//                case Direction.RIGHT:
//                    GAME_PANEL_WIDTH += 4;
//                    break;
//                case Direction.LEFT:
//                    GAME_PANEL_WIDTH += 4;
//                    GAME_PANEL_X -= 4;
//                    break;
//            }
//            this.setBounds(
//                    GAME_PANEL_X,
//                    GAME_PANEL_Y,
//                    GAME_PANEL_WIDTH,
//                    GAME_PANEL_HEIGHT);
//
//        });
//        increase.start();
//        Timer end = new Timer(400, e -> {
//            increase.stop();
//        });
//        end.setRepeats(false);
//        end.start();
    }

    //TODO ADD STORE KEY LISTENER
    public void setKeyListener() {
        this.addKeyListener(new KeyListener() {
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
                    case KeyEvent.VK_H:
                        showHUI();
                        break;

//                    case KeyEvent.VK_P:
//                        pauseGamePanel();
//                        break;
                }
                if (!epsilon.isRunning()) {
                    epsilon.startMove();
//                    setEpsilonAcceleration();
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
        });
    }

    private void showHUI() {
        showHUI = true;
        Timer end = new Timer(3000, a -> {
            showHUI = false;
        });
        end.setRepeats(false);
        end.start();
    }

    //TODO ACCELERATION
    private void setEpsilonAcceleration() {
        EPSILON_SPEED = 5;
        Timer acceleration = new Timer(100, e -> EPSILON_SPEED++);
        acceleration.start();
        Timer end = new Timer(1000, e -> acceleration.stop());
        end.setRepeats(false);
        end.start();
    }

    //TODO ADD EVERYTHING THAT MAKE NEW TO PAUSE AND RESUME
    private void pauseGamePanel() {
        for (Entity entity : Entity.getEntities()) {
            entity.stopMove();
        }
        for (Shot shot : Shot.list()) {
            shot.stopMove();
        }
//        Collision.stopCheckCollision();
        StorePanel.makeNew();
        this.setEnabled(false);
        this.setVisible(false);
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
}