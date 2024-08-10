package model.game;

public class Collectible {
    double x, y;
    int value;

    public Collectible() {
    }

    public Collectible(double x, double y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}