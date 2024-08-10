package connection.database.model.enemies.miniBoss;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class BlackOrb2 {
    private double x, y;
    private final ArrayList<Orb2> orbs = new ArrayList<>();
    private final ArrayList<Line2D> lasers = new ArrayList<>();

    public BlackOrb2() {
    }

    public BlackOrb2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Line2D> getLasers() {
        return lasers;
    }

    public ArrayList<Orb2> getOrbs() {
        return orbs;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}