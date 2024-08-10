package connection.database.model.enemies;

import connection.database.model.Entity2;
import connection.database.model.MyLine;

public abstract class Enemy2 extends Entity2 {
    protected double[] xVertex;
    protected double[] yVertex;
    protected MyLine[] lines;
    protected boolean canMeleeAttack;

    public Enemy2() {
    }

    public Enemy2(double x, double y) {
        super(x, y);
    }

    protected abstract void setVertices();

    protected void setLines() {
        int lastIndex = lines.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            lines[i] = new MyLine(xVertex[i], yVertex[i], xVertex[i + 1], yVertex[i + 1]);
        }
        lines[lastIndex] = new MyLine(xVertex[lastIndex], yVertex[lastIndex], xVertex[0], yVertex[0]);
    }

    public double getXVertex(int index) {
        return xVertex[index];
    }

    public double getYVertex(int index) {
        return yVertex[index];
    }

    public MyLine[] getLines() {
        return lines;
    }

    public boolean isCanMeleeAttack() {
        return canMeleeAttack;
    }
}