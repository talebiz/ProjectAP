package model;

import controller.Util.Constant;

import javax.swing.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Squarantine extends Entity {
    private static ArrayList<Squarantine> squarantines;
    private double xMove, yMove;
    private final int[] xVertex;
    private final int[] yVertex;
    private Timer moveTimer;

    public Squarantine(int x, int y) {
        this.x = x;
        this.y = y;
        xVertex = new int[4];
        yVertex = new int[4];
        setVertices();
        appear = true;
        HP = Constant.SQUARANTINE_HP;
    }

    private void setVertices() {
        xVertex[0] = (int) x - SQUARANTINE_SIZE - GAME_PANEL_X;
        yVertex[0] = (int) y - SQUARANTINE_SIZE - GAME_PANEL_Y;
        xVertex[1] = (int) x + SQUARANTINE_SIZE - GAME_PANEL_X;
        yVertex[1] = (int) y - SQUARANTINE_SIZE - GAME_PANEL_Y;
        xVertex[2] = (int) x + SQUARANTINE_SIZE - GAME_PANEL_X;
        yVertex[2] = (int) y + SQUARANTINE_SIZE - GAME_PANEL_Y;
        xVertex[3] = (int) x - SQUARANTINE_SIZE - GAME_PANEL_X;
        yVertex[3] = (int) y + SQUARANTINE_SIZE - GAME_PANEL_Y;
    }

    public static ArrayList<Squarantine> list() {
        if (squarantines == null) squarantines = new ArrayList<>();
        return squarantines;
    }

    @Override
    public int getSize() {
        return SQUARANTINE_SIZE;
    }

    public void updateVertices() {
        setVertices();
    }

    public int[] getXVertex() {
        return xVertex;
    }

    public int[] getYVertex() {
        return yVertex;
    }


    public Timer getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(Timer moveTimer) {
        this.moveTimer = moveTimer;
    }

    public void move() {
        x += xMove;
        y += yMove;
    }

    public double getXMove() {
        return xMove;
    }

    public void setXMove(double xMove) {
        this.xMove = xMove;
    }

    public double getYMove() {
        return yMove;
    }

    public void setYMove(double yMove) {
        this.yMove = yMove;
    }
}
