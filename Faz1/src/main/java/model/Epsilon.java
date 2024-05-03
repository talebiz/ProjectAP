package model;

import controller.Util.Constant;

public class Epsilon extends Entity{
    private static Epsilon epsilon;
    private boolean movingUp, movingDown, movingRight, movingLeft;

    public Epsilon(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Epsilon getInstance() {
        if (epsilon == null) epsilon = new Epsilon(800, 500);
        return epsilon;
    }

    @Override
    public int getSize() {
        return Constant.EPSILON_SIZE;
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
}
