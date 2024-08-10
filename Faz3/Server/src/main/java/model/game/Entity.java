package model.game;

public abstract class Entity {
    protected double x, y;
    protected boolean hovering;
    protected boolean exertion;
    protected int aoeDamage;

    public Entity() {
    }

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
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
}