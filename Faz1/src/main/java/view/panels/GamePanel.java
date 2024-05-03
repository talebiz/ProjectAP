package view.panels;

import controller.Collision;
import controller.Util.Direction;
import model.Entity;
import model.Epsilon;
import model.Shot;
import model.Squarantine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class GamePanel extends MyPanel {
    private static GamePanel gamePanel;
    Timer resizePanelTimer;
    Timer movePanelTimer;
    Timer moveEpsilonTimer;
    private final Epsilon epsilon = Epsilon.getInstance();
    private boolean movingUp, movingDown, movingRight, movingLeft;
    ArrayList<Entity> entities;
    ArrayList<Entity> enemies;


    private GamePanel() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        this.setLocation(GAME_PANEL_X, GAME_PANEL_Y);
        Squarantine.list().add(new Squarantine(500, 800));
//        Squarantine.list().add(new Squarantine(700, 150));
//        Squarantine.list().add(new Squarantine(1400, 800));
        Collision.startCheckCollision();
        enemies = new ArrayList<>();
        enemies.addAll(Squarantine.list());
        entities = new ArrayList<>();
        entities.addAll(enemies);
        entities.add(Epsilon.getInstance());
        setContent();
    }

    public static GamePanel getInstance() {
        if (gamePanel == null) gamePanel = new GamePanel();
        return gamePanel;
    }

    @Override
    protected void setContent() {
//        setResizePanelTimer();
//        setMovePanelTimer();
        setMoveEpsilonTimer();
        setMoveSquarantineTimer();
        setKeyListener();
        setShootListener();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawEpsilon(g2D);
        drawShots(g2D);
        drawSquarantine(g2D);
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
//        for (Shot shot : Shot.list()) {
//            if (shot.isAppear()) {
//                g2D.setColor(Color.GREEN);
//                g2D.fillOval(
//                        (int) shot.getX() - GAME_PANEL_X,
//                        (int) shot.getY() - GAME_PANEL_Y,
//                        SHOT_SIZE,
//                        SHOT_SIZE);
//            } else {
//                Shot.list().remove(shot);
//            }
//        }
        for (int i = 0; i < Shot.list().size(); i++) {
            if (Shot.list().get(i).isAppear()) {
                g2D.setColor(Color.GREEN);
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
                g2D.drawPolygon(squarantine.getXVertex(), squarantine.getYVertex(),4);

            } else {
                Squarantine.list().remove(i);
            }
        }
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

    private void setMoveEpsilonTimer() {
        moveEpsilonTimer = new Timer(20, e -> {
            if (epsilon.isMovingUp()) epsilon.moveY(-EPSILON_SPEED);
            if (epsilon.isMovingDown()) epsilon.moveY(EPSILON_SPEED);
            if (epsilon.isMovingRight()) epsilon.moveX(EPSILON_SPEED);
            if (epsilon.isMovingLeft()) epsilon.moveX(-EPSILON_SPEED);
            repaint();
        });
    }

    private void setMoveSquarantineTimer() {
        for (Squarantine squarantine : Squarantine.list()) {
            squarantine.setMoveTimer(new Timer(10, e -> {
                setDirectionOfSquarantineMove(squarantine);
                squarantine.move();
                squarantine.updateVertices();
                repaint();
            }));
            squarantine.getMoveTimer().start();
        }
    }

    private void setDirectionOfSquarantineMove(Squarantine squarantine) {
        double xEpsilon = epsilon.getX() + EPSILON_SIZE / 2.0;
        double yEpsilon = epsilon.getY() + EPSILON_SIZE / 2.0;
        double xSquarantine = squarantine.getX() + SQUARANTINE_SIZE / 2.0;
        double ySquarantine = squarantine.getY() + SQUARANTINE_SIZE / 2.0;
        double distance = Math.hypot(xEpsilon - xSquarantine, yEpsilon - ySquarantine);
        squarantine.setXMove(SQUARANTINE_SPEED * (xEpsilon - xSquarantine) / distance);
        squarantine.setYMove(SQUARANTINE_SPEED * (yEpsilon - ySquarantine) / distance);
    }

    private void setShootListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDirectionOfShot(e);
                setShootSound();
                epsilonShoot();
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

    private void setDirectionOfShot(MouseEvent e) {
        double xEpsilon = epsilon.getX() + EPSILON_SIZE / 2.0;
        double yEpsilon = epsilon.getY() + EPSILON_SIZE / 2.0;
        double xClicked = e.getPoint().getX() + this.getX();
        double yClicked = e.getPoint().getY() + this.getY();
        double distance = Math.hypot(xClicked - xEpsilon, yClicked - yEpsilon);
        double xMove = SHOT_SPEED * (xClicked - xEpsilon) / distance;
        double yMove = SHOT_SPEED * (yClicked - yEpsilon) / distance;
        Shot.list().add(new Shot(xEpsilon - SHOT_SIZE / 2.0, yEpsilon - SHOT_SIZE / 2.0, xMove, yMove));
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

    private void epsilonShoot() {
        Shot shot = Shot.list().getLast();
        shot.setMoveTimer(new Timer(15, e -> {
            if (shot.getX() < GAME_PANEL_X) {
                Collision.makeImpact(shot.getX(), shot.getY());
                movingLeft = true;
                movingRight = false;
                increasePanelSize(Direction.LEFT);
                shot.getMoveTimer().stop();
                shot.setAppear(false);
            }
            if (shot.getX() + SHOT_SIZE > GAME_PANEL_X + GAME_PANEL_WIDTH) {
                Collision.makeImpact(shot.getX(), shot.getY());
                movingRight = true;
                movingLeft = false;
                increasePanelSize(Direction.RIGHT);
                shot.getMoveTimer().stop();
                shot.setAppear(false);
            }
            if (shot.getY() < GAME_PANEL_Y) {
                Collision.makeImpact(shot.getX(), shot.getY());
                movingUp = true;
                movingDown = false;
                increasePanelSize(Direction.UP);
                shot.getMoveTimer().stop();
                shot.setAppear(false);
            }
            if (shot.getY() + SHOT_SIZE > GAME_PANEL_Y + GAME_PANEL_HEIGHT) {
                Collision.makeImpact(shot.getX(), shot.getY());
                movingDown = true;
                movingUp = false;
                increasePanelSize(Direction.DOWN);
                shot.getMoveTimer().stop();
                shot.setAppear(false);
            }
            shot.move();
            repaint();
        }));
        shot.getMoveTimer().start();
    }

    private void increasePanelSize(Direction direction) {
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

    private void setKeyListener() {
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
//                    case KeyEvent.VK_P:
//                        pauseGamePanel();
//                        break;
                }
                if (!moveEpsilonTimer.isRunning()) {
                    moveEpsilonTimer.start();
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
                        && !epsilon.isMovingLeft() && moveEpsilonTimer.isRunning()) {
                    moveEpsilonTimer.stop();
                }
            }
        });
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
        resizePanelTimer.stop();
        moveEpsilonTimer.stop();
        for (Shot shot : Shot.list()) {
            shot.getMoveTimer().stop();
        }
        this.setVisible(false);
        StorePanel.getInstance().setVisible(true);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

}
