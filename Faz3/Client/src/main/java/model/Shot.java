package model;

import controller.EntityData;
import view.panels.gamePanels.GamePanel;

import javax.swing.*;
import java.awt.*;

import static controller.ShotController.*;
import static controller.Util.Constant.*;

public class Shot {
    private double x, y;
    private final double xMove, yMove;
    protected boolean appear;
    private Timer moveTimer;
    private final KindOfShot kind;
    private final boolean rigid;
    private final int damage;
    protected GamePanel localPanel;

    public Shot(double x, double y, double xMove, double yMove, int damage, KindOfShot kind, boolean rigid) {
        this.x = x;
        this.y = y;
        this.xMove = xMove;
        this.yMove = yMove;
        this.damage = damage;
        this.kind = kind;
        this.rigid = rigid;
        appear = true;
        EntityData.addShot(this);
        setMoveTimer();
    }

    private void setMoveTimer() {
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> {
            if (rigid) checkCollisionToWall(this);
            checkIfOut(this);
            move();
        });
        moveTimer.start();
    }

    public void stopMoveTimer() {
        moveTimer.stop();
    }

    public void continueMove() {
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

    public int getSize() {
        return SHOT_SIZE;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public int getDamage() {
        return damage;
    }

    public enum KindOfShot {
        EPSILON_SHOT, ENEMY_SHOT
    }

    public KindOfShot getKind() {
        return kind;
    }

    public Image getImage() {
        return switch (kind) {
            case EPSILON_SHOT -> SHOT_EPSILON_IMAGE;
            case ENEMY_SHOT -> SHOT_ENEMY_IMAGE;
        };
    }

    public GamePanel getLocalPanel() {
        return localPanel;
    }

    public void setLocalPanel(GamePanel localPanel) {
        this.localPanel = localPanel;
    }
}