package model.game.enemies.miniBoss;

import model.game.MyLine;
import model.game.enemies.Enemy;

public class Orb extends Enemy {
    private OrbPanel panel;

    public Orb() {
    }

    public Orb(double x, double y) {
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