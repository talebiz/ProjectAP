package model.game.enemies.normal;

import model.game.MyLine;
import model.game.enemies.Enemy;

import java.util.ArrayList;

import static database.Constant.ARCHMIRE_AOE_DAMAGE;
import static database.Constant.ARCHMIRE_DROWN_DAMAGE;

public class Archmire extends Enemy {
    private final ArrayList<AoEArea> aoeAreas = new ArrayList<>();

    public Archmire() {
    }

    public Archmire(double x, double y) {
        super(x, y);
        xVertex = new double[0];
        yVertex = new double[0];
        lines = new MyLine[0];
        hovering = true;
        canMeleeAttack = false;
        exertion = false;
    }

    @Override
    protected void setVertices() {
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
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}