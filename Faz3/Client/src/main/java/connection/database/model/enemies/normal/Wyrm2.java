package connection.database.model.enemies.normal;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

public class Wyrm2 extends Enemy2 {
    private WyrmPanel panel;

    public Wyrm2() {
    }

    public Wyrm2(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new MyLine[0];
        canMeleeAttack = false;
        hovering = false;
        exertion = false;
        panel = new WyrmPanel();
    }

    @Override
    protected void setVertices() {

    }

    public class WyrmPanel {
        boolean isometric, rigid, exertionPanel;

        public WyrmPanel() {
            super();
            isometric = true;
            rigid = false;
            exertionPanel = true;
        }

    }
}