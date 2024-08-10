package connection.database.model.enemies.normal;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

import static controller.Util.Constant.NECROPICK_SIZE;

public class Necropick2 extends Enemy2 {
    private double xGoingTo, yGoingTo;

    public Necropick2() {
    }

    public Necropick2(double x, double y) {
        super(x, y);
        xVertex = new double[14];
        yVertex = new double[14];
        lines = new MyLine[14];
        setVertices();
        setLines();
        canMeleeAttack = false;
        hovering = false;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.14 * NECROPICK_SIZE);
        yVertex[0] = (int) (y - 0.5 * NECROPICK_SIZE);
        xVertex[1] = (int) (x + 0.14 * NECROPICK_SIZE);
        yVertex[1] = (int) (y - 0.5 * NECROPICK_SIZE);
        xVertex[2] = (int) (x + 0.5 * NECROPICK_SIZE);
        yVertex[2] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[3] = (int) (x + 0.5 * NECROPICK_SIZE);
        yVertex[3] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[4] = (int) (x + 0.3 * NECROPICK_SIZE);
        yVertex[4] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[5] = (int) (x + 0.2 * NECROPICK_SIZE);
        yVertex[5] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[6] = (int) (x + 0.07 * NECROPICK_SIZE);
        yVertex[6] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[7] = (int) (x + 0.07 * NECROPICK_SIZE);
        yVertex[7] = (int) (y + 0.55 * NECROPICK_SIZE);
        xVertex[8] = (int) (x - 0.07 * NECROPICK_SIZE);
        yVertex[8] = (int) (y + 0.55 * NECROPICK_SIZE);
        xVertex[9] = (int) (x - 0.07 * NECROPICK_SIZE);
        yVertex[9] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[10] = (int) (x - 0.2 * NECROPICK_SIZE);
        yVertex[10] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[11] = (int) (x - 0.3 * NECROPICK_SIZE);
        yVertex[11] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[12] = (int) (x - 0.5 * NECROPICK_SIZE);
        yVertex[12] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[13] = (int) (x - 0.5 * NECROPICK_SIZE);
        yVertex[13] = (int) (y - 0.24 * NECROPICK_SIZE);
    }

    public double getXGoingTo() {
        return xGoingTo;
    }

    public double getYGoingTo() {
        return yGoingTo;
    }
}