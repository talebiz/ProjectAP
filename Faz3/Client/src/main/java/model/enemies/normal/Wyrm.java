package model.enemies.normal;

import controller.EntityData;
import model.Epsilon;
import model.Shot;
import model.KindOfShot;
import view.panels.gamePanels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

import static controller.Util.Constant.*;

public class Wyrm extends AdvanceEnemy {
    private WyrmPanel panel;
    private double angle;
    private int direction = 1;
    private final double xCenter = FRAME_SIZE.getWidth() / 2.0;
    private final double yCenter = FRAME_SIZE.getHeight() / 2.0;
    private Timer rangedAttackTimer;

    public Wyrm(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new Line2D[0];
        HP = WYRM_HP;
        canMeleeAttack = false;
        hovering = false;
        exertion = false;
        panel = new WyrmPanel();
        setRangedAttackTimer();
        EntityData.addWyrm(this);
    }

    @Override
    protected void setVertices() {

    }

    @Override
    public void dieProcess() {
        super.dieProcess();
        alive = false;
        rangedAttackTimer.stop();
        moveTimer.stop();
        panel.deletePanel();
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> moveAroundCenter());
        moveTimer.start();

    }

    private void moveAroundCenter() {
        angle = (angle > 360 || angle < -360) ? 0 : angle + direction * 0.3;
        x = xCenter + Math.cos(Math.toRadians(angle)) * 700;
        y = yCenter + Math.sin(Math.toRadians(angle)) * 400;
    }

    public void changeDirection() {
        direction *= -1;
    }

    private void setRangedAttackTimer() {
        rangedAttackTimer = new Timer(2000, e -> {
            double xEpsilon = Epsilon.getInstance().getX();
            double yEpsilon = Epsilon.getInstance().getY();
            double distance = Math.hypot(xEpsilon - x, yEpsilon - y);
            double xMove = WYRM_SHOT_SPEED * (xEpsilon - x) / distance;
            double yMove = WYRM_SHOT_SPEED * (yEpsilon - y) / distance;
            new Shot(x, y, xMove, yMove, 8, KindOfShot.ENEMY_SHOT, false);
        });
        rangedAttackTimer.start();
    }

    @Override
    public int getSize() {
        return WYRM_SIZE;
    }

    @Override
    public double getSpeed() {
        return WYRM_SPEED;
    }

    @Override
    public void stopMove() {
        super.stopMove();
        moveTimer.stop();
        rangedAttackTimer.stop();
        panel.deletePanel();
    }

    @Override
    public void continueMove() {
        super.continueMove();
        moveTimer.start();
        rangedAttackTimer.start();
        panel = new WyrmPanel();
    }

    @Override
    public int getValue() {
        return WYRM_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public int getNumberOfCollectible() {
        return WYRM_COLLECTIBLE_NUMBER;
    }

    public class WyrmPanel extends GamePanel {
        public WyrmPanel() {
            super();
            setContent();
            setVisible(true);
            isometric = true;
            rigid = false;
            exertion = true;
        }

        @Override
        protected void setMovePanelTimer() {
            movePanelTimer = new Thread(() -> {
                while (true) {
                    setSize(new Dimension(
                            Wyrm.this.getSize() + 60,
                            (int) (0.8 * Wyrm.this.getSize()) + 60));
                    setLocation(new Point(
                            (int) (x - Wyrm.this.getSize() / 2.0 - 30),
                            (int) (y - 0.8 * (Wyrm.this.getSize() / 2.0) - 30)));
                    waiting(40);
                }
            });
            movePanelTimer.start();
        }
    }
}