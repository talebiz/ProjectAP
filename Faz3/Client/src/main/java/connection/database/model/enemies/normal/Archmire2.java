package connection.database.model.enemies.normal;

import connection.database.model.MyLine;
import connection.database.model.enemies.Enemy2;

import java.util.ArrayList;

import static controller.Util.Constant.*;

public class Archmire2 extends Enemy2 {
    private final ArrayList<AoEArea> aoeAreas = new ArrayList<>();

    public Archmire2() {
    }

    public Archmire2(double x, double y) {
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