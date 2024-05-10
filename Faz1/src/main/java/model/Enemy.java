package model;

import view.panels.GamePanel;

import javax.swing.*;

import java.util.ArrayList;

import static controller.Util.Constant.EPSILON_SIZE;

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
            setDirectionOfEnemyMove();
            move();
            updateVertices();
            GamePanel.getInstance().repaint();
        });
        moveTimer.start();
    }

    private void setDirectionOfEnemyMove() {
        double xEpsilon = Epsilon.getInstance().getX() + EPSILON_SIZE / 2.0;
        double yEpsilon = Epsilon.getInstance().getY() + EPSILON_SIZE / 2.0;
        double distance = Math.hypot(xEpsilon - x, yEpsilon - y);
        xMove = getSpeed() * (xEpsilon - x) / distance;
        yMove = getSpeed() * (yEpsilon - y) / distance;
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
}
