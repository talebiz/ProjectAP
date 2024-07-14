package model.enemies;

import controller.EntityData;
import model.Epsilon;
import model.Shot;

import javax.swing.*;
import java.awt.geom.Line2D;

import static controller.Util.Constant.*;

public class Omenoct extends Enemy {
    private Timer rangedAttackTimer;

    public Omenoct(double x, double y) {
        super(x, y);
        xVertex = new double[8];
        yVertex = new double[8];
        lines = new Line2D[8];
        setVertices();
        setLines();
        HP = OMENOCT_HP;
        canMeleeAttack = true;
        hovering = false;
        exertion = false;
        EntityData.addOmenoct(this);
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.21 * OMENOCT_SIZE);
        yVertex[0] = (int) (y - 0.5 * OMENOCT_SIZE);
        xVertex[1] = (int) (x + 0.21 * OMENOCT_SIZE);
        yVertex[1] = (int) (y - 0.5 * OMENOCT_SIZE);
        xVertex[2] = (int) (x + 0.5 * OMENOCT_SIZE);
        yVertex[2] = (int) (y - 0.21 * OMENOCT_SIZE);
        xVertex[3] = (int) (x + 0.5 * OMENOCT_SIZE);
        yVertex[3] = (int) (y + 0.21 * OMENOCT_SIZE);
        xVertex[4] = (int) (x + 0.21 * OMENOCT_SIZE);
        yVertex[4] = (int) (y + 0.5 * OMENOCT_SIZE);
        xVertex[5] = (int) (x - 0.21 * OMENOCT_SIZE);
        yVertex[5] = (int) (y + 0.5 * OMENOCT_SIZE);
        xVertex[6] = (int) (x - 0.5 * OMENOCT_SIZE);
        yVertex[6] = (int) (y + 0.21 * OMENOCT_SIZE);
        xVertex[7] = (int) (x - 0.5 * OMENOCT_SIZE);
        yVertex[7] = (int) (y - 0.21 * OMENOCT_SIZE);
    }

    @Override
    public void dieProcess() {
        moveTimer.stop();
        rangedAttackTimer.stop();
        alive = false;
    }

    @Override
    public void setMoveTimer() {
        setRangedAttackTimer();
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> {
            rangedAttackMaking();
            setDirectionOfEnemyMove();
            move();
            updateVertices();
        });
        moveTimer.start();
    }

    private void rangedAttackMaking() {
        if (Math.abs(x - GAME_PANEL_X - getSize() / 2.0) < 20 && !rangedAttackTimer.isRunning())
            rangedAttackTimer.start();
        else if (Math.abs(x - GAME_PANEL_X - getSize() / 2.0) > 20 && rangedAttackTimer.isRunning())
            rangedAttackTimer.stop();
    }

    private void setDirectionOfEnemyMove() {
        double yEpsilon = Epsilon.getInstance().getY();
        double distance = Math.hypot(GAME_PANEL_X + getSize() / 2.0 - x, yEpsilon - y);
        xMove = (Math.abs(x - GAME_PANEL_X - getSize() / 2.0) < 10) ? 0 : getSpeed() * (GAME_PANEL_X + getSize() / 2.0 - x) / distance;
        yMove = getSpeed() * (yEpsilon - y) / distance;
    }

    private void setRangedAttackTimer() {
        rangedAttackTimer = new Timer(2000, e -> {
            double xEpsilon = Epsilon.getInstance().getX();
            double yEpsilon = Epsilon.getInstance().getY();
            double distance = Math.hypot(xEpsilon - x, yEpsilon - y);
            double xMove = OMENOCT_SHOT_SPEED * (xEpsilon - x) / distance;
            double yMove = OMENOCT_SHOT_SPEED * (yEpsilon - y) / distance;
            new Shot(x, y, xMove, yMove, 4, Shot.KindOfShot.ENEMY_SHOT, false);
        });
    }

    @Override
    public int getValue() {
        return OMENOCT_COLLECTIBLE_VALUE;
    }

    @Override
    public int getSize() {
        return OMENOCT_SIZE;
    }

    @Override
    public double getSpeed() {
        return OMENOCT_SPEED;
    }

    @Override
    public int getMeleeDamage() {
        return OMENOCT_MELEE_DAMAGE;
    }

    @Override
    public int getNumberOfCollectible() {
        return OMENOCT_COLLECTIBLE_NUMBER;
    }
}