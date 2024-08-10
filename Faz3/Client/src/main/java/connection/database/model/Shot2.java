package connection.database.model;

public class Shot2 {
    private double x, y;
    private String kind;
    private boolean rigid;
    private int damage;

    public Shot2() {
    }

    public Shot2(double x, double y, int damage, String kind, boolean rigid) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.kind = kind;
        this.rigid = rigid;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public String getKind() {
        return kind;
    }
}