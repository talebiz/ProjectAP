package connection.database.model.enemies.normal;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

import static controller.Util.Constant.TRIGORATH_SIZE;

public class Trigorath2 extends Enemy2 {

    public Trigorath2() {
    }

    public Trigorath2(double x, double y) {
        super(x, y);
        xVertex = new double[3];
        yVertex = new double[3];
        lines = new MyLine[3];
        setVertices();
        setLines();
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) x;
        yVertex[0] = (int) (y - 0.58 * TRIGORATH_SIZE);
        xVertex[1] = (int) (x + 0.5 * TRIGORATH_SIZE);
        yVertex[1] = (int) (y + 0.29 * TRIGORATH_SIZE);
        xVertex[2] = (int) (x - 0.5 * TRIGORATH_SIZE);
        yVertex[2] = (int) (y + 0.29 * TRIGORATH_SIZE);
    }
}