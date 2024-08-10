package connection.database.model.enemies.normal;

import static controller.Util.Constant.*;

public class MiniArchmire2 extends Archmire2 {

    public MiniArchmire2() {
    }

    public MiniArchmire2(double x, double y) {
        super(x, y);
    }


    @Override
    public int getAoeAttackDamage() {
        return MINI_ARCHMIRE_AOE_DAMAGE;
    }

    @Override
    public int getDrownDamage() {
        return MINI_ARCHMIRE_DROWN_DAMAGE;
    }
}
