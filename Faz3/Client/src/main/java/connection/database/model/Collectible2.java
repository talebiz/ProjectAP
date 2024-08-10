package connection.database.model;

public class Collectible2 {
    double x, y;
    int value;

    public Collectible2() {
    }

    public Collectible2(double x, double y, int value) {
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