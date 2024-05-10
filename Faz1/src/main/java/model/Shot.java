package model;

import controller.Collision;
import controller.Util.Direction;
import view.panels.GamePanel;

import javax.swing.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Shot {
    private static final ArrayList<Shot> shots = new ArrayList<>();
    private double x, y;
    private final double xMove, yMove;
    protected boolean appear;
    private Timer moveTimer;

    public Shot(double x, double y, double xMove, double yMove) {
        this.x = x;
        this.y = y;
        this.xMove = xMove;
        this.yMove = yMove;
        appear = true;
        shots.add(this);
        setMoveTimer();
    }

    public static ArrayList<Shot> list() {
        return shots;
    }

    private void setMoveTimer() {
        moveTimer = new Timer(20, e -> {
            checkCollisionToWall(GamePanel.getInstance());
            move();
            GamePanel.getInstance().repaint();
        });
        moveTimer.start();
    }

    private void checkCollisionToWall(GamePanel gamePanel) {
        if (x < GAME_PANEL_X) {
            Collision.makeImpact(x, y, null);
            gamePanel.setMovingLeft(true);
            gamePanel.setMovingRight(false);
            Timer end = new Timer(3000, a -> gamePanel.setMovingLeft(false));
            end.setRepeats(false);
            end.start();
            gamePanel.increasePanelSize(Direction.LEFT);
            moveTimer.stop();
            appear = false;
        }
        if (x + SHOT_SIZE > GAME_PANEL_X + GAME_PANEL_WIDTH) {
            Collision.makeImpact(x, y, null);
            gamePanel.setMovingRight(true);
            gamePanel.setMovingLeft(false);
            Timer end = new Timer(3000, a -> gamePanel.setMovingRight(false));
            end.setRepeats(false);
            end.start();
            gamePanel.increasePanelSize(Direction.RIGHT);
            moveTimer.stop();
            appear = false;
        }
        if (y < GAME_PANEL_Y) {
            Collision.makeImpact(x, y, null);
            gamePanel.setMovingUp(true);
            gamePanel.setMovingDown(false);
            Timer end = new Timer(3000, a -> gamePanel.setMovingUp(false));
            end.setRepeats(false);
            end.start();
            gamePanel.increasePanelSize(Direction.UP);
            moveTimer.stop();
            appear = false;
        }
        if (y + SHOT_SIZE > GAME_PANEL_Y + GAME_PANEL_HEIGHT) {
            Collision.makeImpact(x, y, null);
            gamePanel.setMovingDown(true);
            gamePanel.setMovingUp(false);
            Timer end = new Timer(3000, a -> gamePanel.setMovingDown(false));
            end.setRepeats(false);
            end.start();
            gamePanel.increasePanelSize(Direction.DOWN);
            moveTimer.stop();
            appear = false;
        }
    }


    public void startMove() {
        moveTimer.start();
    }

    public void stopMove() {
        moveTimer.stop();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move() {
        x += xMove;
        y += yMove;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }
}