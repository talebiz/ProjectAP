package model;

import javax.swing.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Trigorath extends Enemy {
    private static final ArrayList<Trigorath> trigoraths = new ArrayList<>();

    public Trigorath(double x, double y) {
        super(x, y);
        xVertex = new int[3];
        yVertex = new int[3];
        HP = TRIGORATH_HP;
        trigoraths.add(this);
        setVertices();
        setSpecialMove();
    }

    private void setSpecialMove() {
        Timer timer = new Timer(200, a -> {
            if (Math.hypot(x - Epsilon.getInstance().getX(), y - Epsilon.getInstance().getY()) > 350) {
                TRIGORATH_SPEED = 4;
            } else {
                TRIGORATH_SPEED = 2;
            }
        });
        timer.start();
    }

    public static ArrayList<Trigorath> list() {
        return trigoraths;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - GAME_PANEL_X);
        yVertex[0] = (int) (y - 2 * TRIGORATH_SIZE - GAME_PANEL_Y);
        xVertex[1] = (int) (x + 1.7 * TRIGORATH_SIZE - GAME_PANEL_X);
        yVertex[1] = (int) (y + TRIGORATH_SIZE - GAME_PANEL_Y);
        xVertex[2] = (int) (x - 1.7 * TRIGORATH_SIZE - GAME_PANEL_X);
        yVertex[2] = (int) (y + TRIGORATH_SIZE - GAME_PANEL_Y);
    }

    @Override
    public int getSize() {
        return SQUARANTINE_SIZE;
    }

    @Override
    public int getSpeed() {
        return TRIGORATH_SPEED;
    }
}