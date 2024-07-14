package model.enemies;

import controller.EntityData;
import model.Epsilon;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Squarantine extends Enemy {
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
        EntityData.addSquarantine(this);
        setSpecialMove();
    }

    private void setSpecialMove() {
         specialMoveTimer = new Timer(5000, e -> {
            if (dashing) {
                SQUARANTINE_SPEED *= 2.0 / 3;
            } else {
                SQUARANTINE_SPEED *= 3.0 / 2;
            }
            dashing = !dashing;
        });
        specialMoveTimer.start();
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.5 * SQUARANTINE_SIZE);
        yVertex[0] = (int) (y - 0.5 * SQUARANTINE_SIZE);
        xVertex[1] = (int) (x + 0.5 * SQUARANTINE_SIZE);
        yVertex[1] = (int) (y - 0.5 * SQUARANTINE_SIZE);
        xVertex[2] = (int) (x + 0.5 * SQUARANTINE_SIZE);
        yVertex[2] = (int) (y + 0.5 * SQUARANTINE_SIZE);
        xVertex[3] = (int) (x - 0.5 * SQUARANTINE_SIZE);
        yVertex[3] = (int) (y + 0.5 * SQUARANTINE_SIZE);

    }

    @Override
    public void dieProcess() {
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