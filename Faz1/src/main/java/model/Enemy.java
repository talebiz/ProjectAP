package model;

import view.panels.GamePanel;

import javax.swing.*;

import java.util.ArrayList;

import static view.panels.GamePanel.setDirectionOfEnemyMove;

public abstract class Enemy extends Entity {
    private static final ArrayList<Enemy> enemies = new ArrayList<>();
    protected double xMove, yMove;
    protected int[] xVertex;
    protected int[] yVertex;


    public Enemy(double x, double y) {
        super(x, y);
        enemies.add(this);
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    protected abstract void setVertices();

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(10, e -> {
            setDirectionOfEnemyMove(this);
            move();
            updateVertices();
            GamePanel.getInstance().repaint();
        });
        moveTimer.start();
    }

    @Override
    public void move() {
        x += xMove;
        y += yMove;
    }

    public void updateVertices() {
        setVertices();
    }

    public int[] getXVertex() {
        return xVertex;
    }

    public int getXVertex(int index) {
        return xVertex[index];
    }

    public int[] getYVertex() {
        return yVertex;
    }

    public int getYVertex(int index) {
        return yVertex[index];
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
