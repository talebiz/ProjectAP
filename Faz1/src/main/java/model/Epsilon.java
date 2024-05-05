package model;

import controller.Util.Constant;
import view.panels.GamePanel;

import javax.swing.*;

import static controller.Util.Constant.*;

public class Epsilon extends Entity {
    private static Epsilon epsilon;
    private boolean movingUp, movingDown, movingRight, movingLeft;
    private int XP;

    public Epsilon(int x, int y) {
        super(x, y);
        HP = EPSILON_HP;
    }

    public static Epsilon getInstance() {
        if (epsilon == null) epsilon = new Epsilon(1000, 500);
        return epsilon;
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(20, e -> {
            if (movingUp) moveY(-EPSILON_SPEED);
            if (movingDown) moveY(EPSILON_SPEED);
            if (movingRight) moveX(EPSILON_SPEED);
            if (movingLeft) moveX(-EPSILON_SPEED);
            GamePanel.getInstance().repaint();
        });
    }

    @Override
    public int getSize() {
        return Constant.EPSILON_SIZE;
    }

    @Override
    public int getSpeed() {
        return SQUARANTINE_SPEED;
    }

    @Override
    public void move() {
    }

    public void moveX(int xMove) {
        x += xMove;
    }

    public void moveY(int yMove) {
        y += yMove;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public void takeXP() {
        XP += 5;
    }
}