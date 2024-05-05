package model;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Entity {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    protected double x, y;
    protected int HP;
    protected boolean appear;
    protected Timer moveTimer;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        appear = true;
        entities.add(this);
        setMoveTimer();
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public abstract void setMoveTimer();

    public abstract void move();

    public abstract int getSize();

    public abstract int getSpeed();

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

    public void startMove() {
        moveTimer.start();
    }

    public void stopMove() {
        moveTimer.stop();
    }

    public void damage(int damage) {
        HP -= damage;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public boolean isRunning() {
        return moveTimer.isRunning();
    }
}