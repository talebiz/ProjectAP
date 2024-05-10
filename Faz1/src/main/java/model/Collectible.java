package model;

import javax.swing.*;
import java.util.ArrayList;

public class Collectible {
    private static final ArrayList<Collectible> collectibles = new ArrayList<>();
    double x, y;
    boolean appear;

    public Collectible(double x, double y) {
        this.x = x;
        this.y = y;
        appear = true;
        collectibles.add(this);
        Timer remove = new Timer(10000, e -> appear = false);
        remove.setRepeats(false);
        remove.start();
    }

    public static ArrayList<Collectible> list() {
        return collectibles;
    }


    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public boolean isAppear() {
        return appear;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}