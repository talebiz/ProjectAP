package connection.database.model.enemies.miniBoss;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

public class Orb2 extends Enemy2 {
    private OrbPanel panel;

    public Orb2() {
    }

    public Orb2(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new MyLine[0];
        canMeleeAttack = false;
        hovering = false;
        exertion = true;
        panel = new OrbPanel();
    }

    @Override
    protected void setVertices() {

    }

    public class OrbPanel {
        boolean isometric, rigid, exertionPanel;

        public OrbPanel() {
            isometric = true;
            rigid = false;
            exertionPanel = true;
        }
    }
}