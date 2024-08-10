package model.enemies.normal;

import controller.EntityData;
import model.Epsilon;

import javax.swing.*;
import java.awt.geom.Line2D;

import static controller.Util.Constant.*;

public class Trigorath extends BasicEnemy {
    private Timer specialMoveTimer;

    public Trigorath(double x, double y) {
        super(x, y);
        xVertex = new double[3];
        yVertex = new double[3];
        lines = new Line2D[3];
        setVertices();
        setLines();
        HP = TRIGORATH_HP;
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
        EntityData.addTrigorath(this);
        setSpecialMove();
    }

    private void setSpecialMove() {
        specialMoveTimer = new Timer(200, a -> {
            if (Math.hypot(x - Epsilon.getInstance().getX(), y - Epsilon.getInstance().getY()) > 350) {
                TRIGORATH_SPEED = 0.35 * ENTITY_TIMER_PERIOD;
            } else {
                TRIGORATH_SPEED = 0.15 * ENTITY_TIMER_PERIOD;
            }
        });
        specialMoveTimer.start();
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) x;
        yVertex[0] = (int) (y - 0.58 * TRIGORATH_SIZE);
        xVertex[1] = (int) (x + 0.5 * TRIGORATH_SIZE);
        yVertex[1] = (int) (y + 0.29 * TRIGORATH_SIZE);
        xVertex[2] = (int) (x - 0.5 * TRIGORATH_SIZE);
        yVertex[2] = (int) (y + 0.29 * TRIGORATH_SIZE);
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
        return TRIGORATH_SIZE;
    }

    @Override
    public double getSpeed() {
        return TRIGORATH_SPEED;
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
        return TRIGORATH_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return TRIGORATH_MELEE_DAMAGE;
    }

    @Override
    public int getNumberOfCollectible() {
        return TRIGORATH_COLLECTIBLE_NUMBER;
    }
}