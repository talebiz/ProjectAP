package model.enemies.miniBoss;

import model.enemies.Enemy;
import view.panels.GamePanel;

import java.awt.*;
import java.awt.geom.Line2D;

import static controller.Util.Constant.*;

public class Orb extends Enemy {
    private OrbPanel panel;

    public Orb(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new Line2D[0];
        HP = ORB_HP;
        canMeleeAttack = false;
        hovering = false;
        exertion = true;
        panel = new OrbPanel();
    }

    @Override
    protected void setVertices() {

    }

    @Override
    public void dieProcess() {
        super.dieProcess();
        alive = false;
        panel.deletePanel();
    }

    @Override
    public void setMoveTimer() {

    }

    @Override
    public int getSize() {
        return ORB_SIZE;
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public void stopMove() {
        super.stopMove();
        panel.deletePanel();
    }

    @Override
    public void continueMove() {
        super.continueMove();
        panel = new OrbPanel();
    }

    @Override
    public int getValue() {
        return ORB_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public int getNumberOfCollectible() {
        return ORB_COLLECTIBLE_NUMBER;
    }

    public class OrbPanel extends GamePanel {
        public OrbPanel() {
            setContent();
            setVisible(true);
            isometric = true;
            rigid = false;
            exertion = true;
            setSize(new Dimension(
                    Orb.this.getSize() + 100,
                    Orb.this.getSize() + 100));
            setLocation(new Point(
                    (int) (x - Orb.this.getSize() / 2.0 - 50),
                    (int) (y - Orb.this.getSize() / 2.0 - 50)));
        }
    }
}