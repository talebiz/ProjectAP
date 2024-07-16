package model.enemies;

import controller.EntityData;
import model.Entity;

import java.awt.geom.Line2D;

import static controller.EnemyBuilder.amount;

public abstract class Enemy extends Entity {
    protected double xMove, yMove;
    protected double[] xVertex;
    protected double[] yVertex;
    protected Line2D[] lines;
    protected boolean canMeleeAttack;

    public Enemy(double x, double y) {
        super(x, y);
        EntityData.addEnemy(this);
    }

    protected abstract void setVertices();

    protected void setLines() {
        int lastIndex = lines.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            lines[i] = new Line2D.Double(xVertex[i], yVertex[i], xVertex[i + 1], yVertex[i + 1]);
        }
        lines[lastIndex] = new Line2D.Double(xVertex[lastIndex], yVertex[lastIndex], xVertex[0], yVertex[0]);
    }

    public void dieProcess() {
        amount++;
    }

    @Override
    public abstract void setMoveTimer();

    @Override
    public void move() {
        x += xMove;
        y += yMove;
    }

    public void updateVertices() {
        setVertices();
        setLines();
    }

    public double getXVertex(int index) {
        return xVertex[index];
    }

    public double getYVertex(int index) {
        return yVertex[index];
    }

    public int getVerticesNumber() {
        return xVertex.length;
    }

    public Line2D[] getLines() {
        return lines;
    }

    public boolean isCanMeleeAttack() {
        return canMeleeAttack;
    }

    public abstract int getValue();

    public abstract int getMeleeDamage();

    public abstract int getNumberOfCollectible();

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}