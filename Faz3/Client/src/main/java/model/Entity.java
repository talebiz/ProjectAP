package model;

import controller.EntityData;
import view.panels.gamePanels.GamePanel;

import javax.swing.*;

public abstract class Entity {
    protected double x, y;
    protected int HP;
    protected boolean alive;
    protected Timer moveTimer;
    protected boolean hovering;
    protected boolean exertion;
    protected Timer aoeDamageTimer;
    protected int aoeDamage;
    protected GamePanel localPanel;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        alive = true;
        EntityData.addEntity(this);
        setMoveTimer();
        setAoEDamageTimer();
    }

    private void setAoEDamageTimer() {
        aoeDamageTimer = new Timer(1000, e -> {
            if (aoeDamage == 0) aoeDamageTimer.stop();
            damage(aoeDamage);
        }) {{
            setInitialDelay(0);
        }};
    }

    public void startAoEDamage() {
        if (!aoeDamageTimer.isRunning()) aoeDamageTimer.start();
    }

    public abstract void setMoveTimer();

    public abstract void move();

    public abstract int getSize();

    public abstract double getSpeed();

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
    }

    public void continueMove() {
    }

    public void damage(int damage) {
        HP -= damage;
    }

    public int getHP() {
        return HP;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isRunning() {
        return moveTimer.isRunning();
    }

    public boolean isHovering() {
        return hovering;
    }

    public boolean isExertion() {
        return exertion;
    }

    public void setExertion(boolean exertion) {
        this.exertion = exertion;
    }

    public void setAoeDamage(int aoeDamage) {
        this.aoeDamage = aoeDamage;
    }

    public GamePanel getLocalPanel() {
        return localPanel;
    }

    public void setLocalPanel(GamePanel localPanel) {
        this.localPanel = localPanel;
    }
}