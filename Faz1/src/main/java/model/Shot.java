package model;


import controller.Util.Constant;

import javax.swing.*;
import java.util.ArrayList;

public class Shot {
    private static ArrayList<Shot> shots;
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
    }

    public static ArrayList<Shot> list() {
        if (shots == null) shots = new ArrayList<>();
        return shots;
    }

    public int getSize() {
        return Constant.SHOT_SIZE;
    }

    public Timer getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(Timer moveTimer) {
        this.moveTimer = moveTimer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void move() {
        x += xMove;
        y += yMove;
    }

    public double getXMove() {
        return xMove;
    }

    public double getYMove() {
        return yMove;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }
}
