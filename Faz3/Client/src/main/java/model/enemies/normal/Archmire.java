package model.enemies.normal;

import controller.EntityData;
import model.Epsilon;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Archmire extends AdvanceEnemy {
    private final ArrayList<AoEArea> aoeAreas = new ArrayList<>();
    private Timer aoeAttackTimer;

    public Archmire(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new Line2D[0];
        HP = ARCHMIRE_HP;
        hovering = true;
        canMeleeAttack = false;
        exertion = false;
        setAoEAttackTimer();
        EntityData.addArchmire(this);
    }


    private void setAoEAttackTimer() {
        aoeAttackTimer = new Timer(250, e -> aoeAreas.add(new AoEArea(x, y)));
        aoeAttackTimer.start();
    }

    @Override
    protected void setVertices() {
    }

    @Override
    public void dieProcess() {
        super.dieProcess();
        moveTimer.stop();
        aoeAttackTimer.stop();
        alive = false;
        new MiniArchmire(x - 40, y);
        new MiniArchmire(x + 40, y);
    }

    @Override
    public void setMoveTimer() {
        moveTimer = new Timer(ENTITY_TIMER_PERIOD, e -> {
            setDirectionOfEnemyMove();
            move();
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
        return ARCHMIRE_SIZE;
    }

    @Override
    public double getSpeed() {
        return ARCHMIRE_SPEED;
    }

    @Override
    public void stopMove() {
        super.stopMove();
        moveTimer.stop();
        aoeAttackTimer.stop();
    }

    @Override
    public void continueMove() {
        super.continueMove();
        moveTimer.start();
        aoeAttackTimer.start();
    }

    @Override
    public int getValue() {
        return ARCHMIRE_COLLECTIBLE_VALUE;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public int getNumberOfCollectible() {
        return ARCHMIRE_COLLECTIBLE_NUMBER;
    }

    public int getAoeAttackDamage() {
        return ARCHMIRE_AOE_DAMAGE;
    }

    public int getDrownDamage() {
        return ARCHMIRE_DROWN_DAMAGE;
    }

    public ArrayList<AoEArea> getAoEAreas() {
        return aoeAreas;
    }

    public class AoEArea {
        private final double x, y;

        private AoEArea(double x, double y) {
            this.x = x;
            this.y = y;
            setRemoveArea();
        }

        private void setRemoveArea() {
            new Timer(5000, e -> aoeAreas.remove(this)) {{
                setRepeats(false);
            }}.start();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}