package model.game.enemies.normal;

import database.Constant;

public class MiniArchmire extends Archmire{

    public MiniArchmire() {
    }

    public MiniArchmire(double x, double y) {
        super(x, y);
    }

    @Override
    public int getAoeAttackDamage() {
        return Constant.MINI_ARCHMIRE_AOE_DAMAGE;
    }

    @Override
    public int getDrownDamage() {
        return Constant.MINI_ARCHMIRE_DROWN_DAMAGE;
    }
}
