package model.game.enemies.miniBoss;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class BlackOrb {
    private double x, y;
    private final ArrayList<Orb> orbs = new ArrayList<>();
    private final ArrayList<Line2D> lasers = new ArrayList<>();

    public BlackOrb() {
    }

    public BlackOrb(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Line2D> getLasers() {
        return lasers;
    }

    public ArrayList<Orb> getOrbs() {
        return orbs;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}