package model.enemies.normal;

import controller.EntityData;
import model.Epsilon;
import model.Shot;
import model.enemies.Enemy;
import view.panels.gamePanels.GamePanel;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.Random;

import static controller.Util.Constant.*;

public class Necropick extends Enemy {
    private Timer specialMoveTimer;
    private double xGoingTo, yGoingTo;

    public Necropick(double x, double y) {
        super(x, y);
        xGoingTo = x;
        yGoingTo = y;
        xVertex = new double[14];
        yVertex = new double[14];
        lines = new Line2D[14];
        setVertices();
        setLines();
        HP = NECROPICK_HP;
        canMeleeAttack = false;
        hovering = false;
        setSpecialMove();
        EntityData.addNecropick(this);
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.14 * NECROPICK_SIZE);
        yVertex[0] = (int) (y - 0.5 * NECROPICK_SIZE);
        xVertex[1] = (int) (x + 0.14 * NECROPICK_SIZE);
        yVertex[1] = (int) (y - 0.5 * NECROPICK_SIZE);
        xVertex[2] = (int) (x + 0.5 * NECROPICK_SIZE);
        yVertex[2] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[3] = (int) (x + 0.5 * NECROPICK_SIZE);
        yVertex[3] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[4] = (int) (x + 0.3 * NECROPICK_SIZE);
        yVertex[4] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[5] = (int) (x + 0.2 * NECROPICK_SIZE);
        yVertex[5] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[6] = (int) (x + 0.07 * NECROPICK_SIZE);
        yVertex[6] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[7] = (int) (x + 0.07 * NECROPICK_SIZE);
        yVertex[7] = (int) (y + 0.55 * NECROPICK_SIZE);
        xVertex[8] = (int) (x - 0.07 * NECROPICK_SIZE);
        yVertex[8] = (int) (y + 0.55 * NECROPICK_SIZE);
        xVertex[9] = (int) (x - 0.07 * NECROPICK_SIZE);
        yVertex[9] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[10] = (int) (x - 0.2 * NECROPICK_SIZE);
        yVertex[10] = (int) (y - 0.24 * NECROPICK_SIZE);
        xVertex[11] = (int) (x - 0.3 * NECROPICK_SIZE);
        yVertex[11] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[12] = (int) (x - 0.5 * NECROPICK_SIZE);
        yVertex[12] = (int) (y - 0.14 * NECROPICK_SIZE);
        xVertex[13] = (int) (x - 0.5 * NECROPICK_SIZE);
        yVertex[13] = (int) (y - 0.24 * NECROPICK_SIZE);
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
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> updateVertices());
        moveTimer.start();
    }

    private void setSpecialMove() {
        specialMoveTimer = new Timer(8000, e -> {
            x = xGoingTo;
            y = yGoingTo;
            hovering = false;
            exertion = true;
            rangedAttackMaking();
            new Timer(2000, a -> {
                hovering = true;
                exertion = false;
                y = 1100;
                setRandomLocation();
            }) {{
                setRepeats(false);
            }}.start();
        });
        specialMoveTimer.setInitialDelay(2000);
        specialMoveTimer.start();
    }

    private void setRandomLocation() {
        double xEpsilon = Epsilon.getInstance().getX();
        double yEpsilon = Epsilon.getInstance().getY();
        double xRandom = new Random().nextDouble(xEpsilon - 200, xEpsilon + 200);
        int c = (new Random().nextDouble() > 0.5) ? 1 : -1;
        double yRandom = c * Math.sqrt(40000 - (xRandom - xEpsilon) * (xRandom - xEpsilon));
        xGoingTo = xRandom;
        yGoingTo = yEpsilon + yRandom;
        GamePanel gamePanel = Epsilon.getInstance().getLocalPanel();
        if (xGoingTo < gamePanel.getX() + getSize() ||
                xGoingTo > gamePanel.getX() + gamePanel.getWidth() - getSize() ||
                yGoingTo < gamePanel.getY() + getSize() ||
                yGoingTo > gamePanel.getY() + gamePanel.getHeight() - getSize()) {
            setRandomLocation();
        }
    }

    private void rangedAttackMaking() {
        for (int i = 0; i < 8; i++) {
            double shotXMove = new Random().nextDouble(-getSpeed(), getSpeed());
            double a = (new Random().nextDouble() > 0.5) ? 1 : -1;
            double shotYMove = a * Math.sqrt(getSpeed() * getSpeed() - shotXMove * shotXMove);
            new Shot(x, y, shotXMove, shotYMove, 5, Shot.KindOfShot.ENEMY_SHOT, false);
        }
    }

    @Override
    public int getSize() {
        return NECROPICK_SIZE;
    }

    @Override
    public double getSpeed() {
        return NECROPICK_SPEED;
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
        return NECROPICK_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public int getNumberOfCollectible() {
        return NECROPICK_COLLECTIBLE_NUMBER;
    }

    public double getXGoingTo() {
        return xGoingTo;
    }

    public double getYGoingTo() {
        return yGoingTo;
    }
}