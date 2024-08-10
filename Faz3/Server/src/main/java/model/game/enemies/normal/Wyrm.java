package model.game.enemies.normal;

import model.game.enemies.Enemy;
import model.game.MyLine;

public class Wyrm extends Enemy {
    private WyrmPanel panel;

    public Wyrm() {
    }

    public Wyrm(double x, double y) {
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