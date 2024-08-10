package model;

import controller.EntityData;

import javax.swing.*;

public class Collectible {
    double x, y;
    boolean appear;
    int value;

    public Collectible(double x, double y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        appear = true;
        EntityData.addCollectible(this);
        Timer remove = new Timer(10000, e -> appear = false);
        remove.setRepeats(false);
        remove.start();
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

    public int getValue() {
        return value;
    }
}