package model.enemies.normal;

import controller.EntityData;
import model.Epsilon;
import model.enemies.Enemy;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

import static controller.Util.Constant.*;

public class Squarantine extends Enemy implements Serializable {
    private boolean dashing;
    private Timer specialMoveTimer;

    public Squarantine(double x, double y) {
        super(x, y);
        xVertex = new double[4];
        yVertex = new double[4];
        lines = new Line2D[4];
        setVertices();
        setLines();
        HP = SQUARANTINE_HP;
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
        dashing = false;
        setSpecialMove();
        EntityData.addSquarantine(this);
    }

    private void setSpecialMove() {
        specialMoveTimer = new Timer(5000, e -> {
            if (dashing) {
                SQUARANTINE_SPEED = 0.15 * ENTITY_TIMER_PERIOD;
            } else {
                SQUARANTINE_SPEED = 0.25 * ENTITY_TIMER_PERIOD;
            }
            dashing = !dashing;
        });
        specialMoveTimer.start();
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
        moveTimer.stop();
        specialMoveTimer.stop();
        alive = false;
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> {
            setDirectionOfEnemyMove();
            move();
            updateVertices();
        });
        moveTimer.start();
    }

    private void setDirectionOfEnemyMove() {
        double xEpsilon = Epsilon.getInstance().getX();
        double yEpsilon = Epsilon.getInstance().getY();
        double distance = Math.hypot(xEpsilon - x, yEpsilon - y);
        xMove = getSpeed() * (xEpsilon - x) / distance;
        yMove = getSpeed() * (yEpsilon - y) / distance;
    }

    @Override
    public int getSize() {
        return SQUARANTINE_SIZE;
    }

    @Override
    public double getSpeed() {
        return SQUARANTINE_SPEED;
    }

    @Override
    public void stopMove() {
        super.stopMove();
        moveTimer.stop();
        specialMoveTimer.stop();
    }

    @Override
    public void continueMove() {
        super.continueMove();
        moveTimer.start();
        specialMoveTimer.start();
    }

    @Override
    public int getValue() {
        return SQUARANTINE_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return SQUARANTINE_MELEE_DAMAGE;
    }

    @Override
    public int getNumberOfCollectible() {
        return SQUARANTINE_COLLECTIBLE_NUMBER;
    }

    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }
}