package model.game.enemies.normal;

import model.game.enemies.Enemy;
import model.game.MyLine;

import static database.Constant.SQUARANTINE_SIZE;

public class Squarantine extends Enemy {
    private boolean dashing;

    public Squarantine() {
    }

    public Squarantine(double x, double y) {
        super(x, y);
        xVertex = new double[4];
        yVertex = new double[4];
        lines = new MyLine[4];
        setVertices();
        setLines();
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
        dashing = false;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.5 * SQUARANTINE_SIZE);
        yVertex[0] = (int) (y - 0.5 * SQUARANTINE_SIZE);
        xVertex[1] = (int) (x + 0.5 * SQUARANTINE_SIZE);
        yVertex[1] = (int) (y - 0.5 * SQUARANTINE_SIZE);
        xVertex[2] = (int) (x + 0.5 * SQUARANTINE_SIZE);
        yVertex[2] = (int) (y + 0.5 * SQUARANTINE_SIZE);
        xVertex[3] = (int) (x - 0.5 * SQUARANTINE_SIZE);
        yVertex[3] = (int) (y + 0.5 * SQUARANTINE_SIZE);

    }


    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }
}