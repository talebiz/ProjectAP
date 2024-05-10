package model;

import javax.swing.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Squarantine extends Enemy {
    private static final ArrayList<Squarantine> squarantines = new ArrayList<>();

    public Squarantine(double x, double y) {
        super(x, y);
        xVertex = new int[4];
        yVertex = new int[4];
        HP = SQUARANTINE_HP;
        squarantines.add(this);
        setVertices();
        setSpecialMove();
    }

    private void setSpecialMove() {
        Timer timer = new Timer(5000, e -> {
            if (SQUARANTINE_SPEED == 2) {
                SQUARANTINE_SPEED = 3;
            } else {
                SQUARANTINE_SPEED = 2;
            }
        });
        timer.start();
    }

    public static ArrayList<Squarantine> list() {
        return squarantines;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - SQUARANTINE_SIZE - GAME_PANEL_X);
        yVertex[0] = (int) (y - SQUARANTINE_SIZE - GAME_PANEL_Y);
        xVertex[1] = (int) (x + SQUARANTINE_SIZE - GAME_PANEL_X);
        yVertex[1] = (int) (y - SQUARANTINE_SIZE - GAME_PANEL_Y);
        xVertex[2] = (int) (x + SQUARANTINE_SIZE - GAME_PANEL_X);
        yVertex[2] = (int) (y + SQUARANTINE_SIZE - GAME_PANEL_Y);
        xVertex[3] = (int) (x - SQUARANTINE_SIZE - GAME_PANEL_X);
        yVertex[3] = (int) (y + SQUARANTINE_SIZE - GAME_PANEL_Y);
    }

    @Override
    public int getSize() {
        return SQUARANTINE_SIZE;
    }

    @Override
    public int getSpeed() {
        return SQUARANTINE_SPEED;
    }
}
