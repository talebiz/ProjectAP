package model.game.enemies.normal;

import model.game.MyLine;
import model.game.enemies.Enemy;

import static database.Constant.OMENOCT_SIZE;

public class Omenoct extends Enemy {

    public Omenoct() {
    }

    public Omenoct(double x, double y) {
        super(x, y);
        xVertex = new double[8];
        yVertex = new double[8];
        lines = new MyLine[8];
        setVertices();
        setLines();
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.21 * OMENOCT_SIZE);
        yVertex[0] = (int) (y - 0.5 * OMENOCT_SIZE);
        xVertex[1] = (int) (x + 0.21 * OMENOCT_SIZE);
        yVertex[1] = (int) (y - 0.5 * OMENOCT_SIZE);
        xVertex[2] = (int) (x + 0.5 * OMENOCT_SIZE);
        yVertex[2] = (int) (y - 0.21 * OMENOCT_SIZE);
        xVertex[3] = (int) (x + 0.5 * OMENOCT_SIZE);
        yVertex[3] = (int) (y + 0.21 * OMENOCT_SIZE);
        xVertex[4] = (int) (x + 0.21 * OMENOCT_SIZE);
        yVertex[4] = (int) (y + 0.5 * OMENOCT_SIZE);
        xVertex[5] = (int) (x - 0.21 * OMENOCT_SIZE);
        yVertex[5] = (int) (y + 0.5 * OMENOCT_SIZE);
        xVertex[6] = (int) (x - 0.5 * OMENOCT_SIZE);
        yVertex[6] = (int) (y + 0.21 * OMENOCT_SIZE);
        xVertex[7] = (int) (x - 0.5 * OMENOCT_SIZE);
        yVertex[7] = (int) (y - 0.21 * OMENOCT_SIZE);
    }
}