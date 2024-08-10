package connection.database.model.enemies.normal;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

import java.io.Serializable;

import static controller.Util.Constant.SQUARANTINE_SIZE;

public class Squarantine2 extends Enemy2 {
    private boolean dashing;

    public Squarantine2() {
    }

    public Squarantine2(double x, double y) {
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