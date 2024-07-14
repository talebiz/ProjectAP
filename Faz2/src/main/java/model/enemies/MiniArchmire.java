package model.enemies;

import static controller.Util.Constant.*;

public class MiniArchmire extends Archmire{
    public MiniArchmire(double x, double y) {
        super(x, y);
        HP = MINI_ARCHMIRE_HP;
    }

    @Override
    public void dieProcess() {
        moveTimer.stop();
        aoeDamageTimer.stop();
        alive = false;
    }

    @Override
    public int getSize() {
        return MINI_ARCHMIRE_SIZE;
    }

    @Override
    public int getValue() {
        return MINI_ARCHMIRE_COLLECTIBLE_VALUE;
    }

    @Override
    public int getNumberOfCollectible() {
        return MINI_ARCHMIRE_COLLECTIBLE_NUMBER;
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
