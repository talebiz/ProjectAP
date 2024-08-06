package model.enemies.miniBoss;

import controller.EntityData;
import model.enemies.Enemy;
import view.panels.gamePanels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;

import static controller.Util.Constant.*;

public class Barricados extends Enemy {
    private BarricadosPanel panel;

    public Barricados(double x, double y) {
        super(x, y);
        xVertex = new double[4];
        yVertex = new double[4];
        lines = new Line2D[4];
        setVertices();
        setLines();
        HP = BARRICADOS_HP;
        canMeleeAttack = false;
        hovering = false;
        exertion = true;
        panel = new BarricadosPanel();
        EntityData.addBarricados(this);
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.5 * getSize());
        yVertex[0] = (int) (y - 0.5 * getSize());
        xVertex[1] = (int) (x + 0.5 * getSize());
        yVertex[1] = (int) (y - 0.5 * getSize());
        xVertex[2] = (int) (x + 0.5 * getSize());
        yVertex[2] = (int) (y + 0.5 * getSize());
        xVertex[3] = (int) (x - 0.5 * getSize());
        yVertex[3] = (int) (y + 0.5 * getSize());
    }

    @Override
    public void dieProcess() {
        super.dieProcess();
        alive = false;
        moveTimer.stop();
        panel.deletePanel();
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(120000, e -> dieProcess());
        moveTimer.setRepeats(false);
        moveTimer.start();
    }

    @Override
    public int getSize() {
        return BARRICADOS_SIZE;
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public void stopMove() {
        super.stopMove();
        moveTimer.stop();
        panel.deletePanel();
    }

    @Override
    public void continueMove() {
        super.continueMove();
        moveTimer.start();
        panel = new BarricadosPanel();
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public int getNumberOfCollectible() {
        return 0;
    }

    public class BarricadosPanel extends GamePanel {
        public BarricadosPanel() {
            setVisible(true);
            isometric = true;
            rigid = new Random().nextDouble() > 0.5;
            exertion = true;
            setSize(new Dimension(
                    Barricados.this.getSize() + 50,
                    Barricados.this.getSize() + 50));
            setLocation(new Point(
                    (int) (x - Barricados.this.getSize() / 2.0 - 25),
                    (int) (y - Barricados.this.getSize() / 2.0 - 25)));
        }
    }
}