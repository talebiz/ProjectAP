package model;

import view.panels.GamePanel;

import javax.swing.*;

import static controller.Util.Constant.*;

public final class Epsilon extends Entity {
    private static Epsilon epsilon;
    private boolean movingUp, movingDown, movingRight, movingLeft;
    private int XP;

    private Epsilon(int x, int y) {
        super(x, y);
        HP = EPSILON_HP;
        XP = 0;
        hovering = false;
    }

    public static Epsilon getInstance() {
        if (epsilon == null) epsilon = new Epsilon(1000, 500);
        return epsilon;
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> {
            if (movingUp) moveY(-EPSILON_SPEED);
            if (movingDown) moveY(EPSILON_SPEED);
            if (movingRight) moveX(EPSILON_SPEED);
            if (movingLeft) moveX(-EPSILON_SPEED);
        });
    }

    @Override
    public int getSize() {
        return EPSILON_SIZE;
    }

    @Override
    public double getSpeed() {
        return EPSILON_SPEED;
    }

    @Override
    public void move() {
    }

    public void moveX(double xMove) {
        x += xMove;
    }

    public void moveY(double yMove) {
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

    public void changeXP(int amount) {
        XP += amount;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}