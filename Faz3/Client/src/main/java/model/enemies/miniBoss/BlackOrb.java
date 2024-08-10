package model.enemies.miniBoss;

import controller.EntityData;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static controller.Util.Constant.ORB_DISTANCE;

public class BlackOrb {
    private final double x, y;
    private final ArrayList<Orb> orbs = new ArrayList<>();
    private final ArrayList<Line2D> lasers = new ArrayList<>();

    public BlackOrb(double x, double y) {
        this.x = x;
        this.y = y;
        addOrbs();
        EntityData.addBlackOrb(this);
    }

    private void addOrbs() {
        orbs.add(new Orb(x, y - ORB_DISTANCE));
        setLasers();
        new Timer(3000, e -> {
            orbs.add(new Orb(x + 0.95 * ORB_DISTANCE, y - 0.31 * ORB_DISTANCE));
            setLasers();
        }) {{
            setRepeats(false);
        }}.start();
        new Timer(6000, e -> {
            orbs.add(new Orb(x + 0.59 * ORB_DISTANCE, y + 0.81 * ORB_DISTANCE));
            setLasers();
        }) {{
            setRepeats(false);
        }}.start();
        new Timer(9000, e -> {
            orbs.add(new Orb(x - 0.59 * ORB_DISTANCE, y + 0.81 * ORB_DISTANCE));
            setLasers();
        }) {{
            setRepeats(false);
        }}.start();
        new Timer(12000, e -> {
            orbs.add(new Orb(x - 0.95 * ORB_DISTANCE, y - 0.31 * ORB_DISTANCE));
            setLasers();
        }) {{
            setRepeats(false);
        }}.start();
    }

    public void setLasers() {
        lasers.clear();
        for (int i = 0; i < orbs.size() - 1; i++) {
            for (int j = i + 1; j < orbs.size(); j++) {
                lasers.add(new Line2D.Double(orbs.get(i).getX(), orbs.get(i).getY(), orbs.get(j).getX(), orbs.get(j).getY()));
            }
        }
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