package connection.database;

import connection.database.model.Collectible2;
import connection.database.model.Entity2;
import connection.database.model.Epsilon2;
import connection.database.model.Shot2;
import connection.database.model.enemies.Enemy2;
import connection.database.model.enemies.miniBoss.Barricados2;
import connection.database.model.enemies.miniBoss.BlackOrb2;
import connection.database.model.enemies.normal.*;

import java.util.ArrayList;

public class EntityData2 {
    private static Epsilon2 epsilon = new Epsilon2(100, 100);
    private static final ArrayList<Shot2> shots = new ArrayList<>();
    private static final ArrayList<Entity2> entities = new ArrayList<>();
    private static final ArrayList<Enemy2> enemies = new ArrayList<>();
    private static final ArrayList<Squarantine2> squarantines = new ArrayList<>();
    private static final ArrayList<Trigorath2> trigoraths = new ArrayList<>();
    private static final ArrayList<Omenoct2> omenocts = new ArrayList<>();
    private static final ArrayList<Necropick2> necropicks = new ArrayList<>();
    private static final ArrayList<Archmire2> archmires = new ArrayList<>();
    private static final ArrayList<Wyrm2> wyrms = new ArrayList<>();
    private static final ArrayList<Barricados2> barricadoses = new ArrayList<>();
    private static final ArrayList<BlackOrb2> blackOrbs = new ArrayList<>();
    private static final ArrayList<Collectible2> collectibles = new ArrayList<>();

    public static void clear() {
        shots.clear();
        entities.clear();
        enemies.clear();
        squarantines.clear();
        trigoraths.clear();
        omenocts.clear();
        necropicks.clear();
        archmires.clear();
        wyrms.clear();
        barricadoses.clear();
        blackOrbs.clear();
        collectibles.clear();
    }

    public static void setEpsilon(Epsilon2 epsilon) {
        EntityData2.epsilon = epsilon;
    }

    public static void addShot(Shot2 shot) {
        shots.add(shot);
    }

    public static void addEntity(Entity2 entity) {
        entities.add(entity);
    }

    public static void addEnemy(Enemy2 enemy) {
        enemies.add(enemy);
    }

    public static void addSquarantine(Squarantine2 squarantine) {
        squarantines.add(squarantine);
    }

    public static void addTrigorath(Trigorath2 trigorath) {
        trigoraths.add(trigorath);
    }

    public static void addOmenoct(Omenoct2 omenoct) {
        omenocts.add(omenoct);
    }

    public static void addNecropick(Necropick2 necropick) {
        necropicks.add(necropick);
    }

    public static void addArchmire(Archmire2 archmire) {
        archmires.add(archmire);
    }

    public static void addWyrm(Wyrm2 wyrm) {
        wyrms.add(wyrm);
    }

    public static void addBarricados(Barricados2 barricados) {
        barricadoses.add(barricados);
    }

    public static void addBlackOrb(BlackOrb2 blackOrb) {
        blackOrbs.add(blackOrb);
    }

    public static void addCollectible(Collectible2 collectible) {
        collectibles.add(collectible);
    }

    public static Epsilon2 getEpsilon() {
        return epsilon;
    }

    public static ArrayList<Shot2> getShots() {
        return shots;
    }

    public static ArrayList<Entity2> getEntities() {
        return entities;
    }

    public static ArrayList<Enemy2> getEnemies() {
        return enemies;
    }

    public static ArrayList<Squarantine2> getSquarantines() {
        return squarantines;
    }

    public static ArrayList<Trigorath2> getTrigoraths() {
        return trigoraths;
    }

    public static ArrayList<Omenoct2> getOmenocts() {
        return omenocts;
    }

    public static ArrayList<Necropick2> getNecropicks() {
        return necropicks;
    }

    public static ArrayList<Archmire2> getArchmires() {
        return archmires;
    }

    public static ArrayList<Wyrm2> getWyrms() {
        return wyrms;
    }

    public static ArrayList<Barricados2> getBarricadoses() {
        return barricadoses;
    }

    public static ArrayList<BlackOrb2> getBlackOrbs() {
        return blackOrbs;
    }

    public static ArrayList<Collectible2> getCollectibles() {
        return collectibles;
    }
}