package database;

import model.game.Collectible;
import model.game.Entity;
import model.game.Epsilon;
import model.game.Shot;
import model.game.enemies.Enemy;
import model.game.enemies.miniBoss.Barricados;
import model.game.enemies.miniBoss.BlackOrb;
import model.game.enemies.normal.*;

import java.util.ArrayList;

public class EntityData {
    private Epsilon epsilon = new Epsilon(100, 100);
    private final ArrayList<Shot> shots = new ArrayList<>();
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final ArrayList<Omenoct> omenocts = new ArrayList<>();
    private final ArrayList<Necropick> necropicks = new ArrayList<>();
    private final ArrayList<Archmire> archmires = new ArrayList<>();
    private final ArrayList<Wyrm> wyrms = new ArrayList<>();
    private final ArrayList<Barricados> barricadoses = new ArrayList<>();
    private final ArrayList<BlackOrb> blackOrbs = new ArrayList<>();
    private final ArrayList<Collectible> collectibles = new ArrayList<>();

    public void clear() {
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

    public void setEpsilon(Epsilon epsilon) {
        this.epsilon = epsilon;
    }

    public void addShot(Shot shot) {
        shots.add(shot);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addSquarantine(Squarantine squarantine) {
        squarantines.add(squarantine);
    }

    public void addTrigorath(Trigorath trigorath) {
        trigoraths.add(trigorath);
    }

    public void addOmenoct(Omenoct omenoct) {
        omenocts.add(omenoct);
    }

    public void addNecropick(Necropick necropick) {
        necropicks.add(necropick);
    }

    public void addArchmire(Archmire archmire) {
        archmires.add(archmire);
    }

    public void addWyrm(Wyrm wyrm) {
        wyrms.add(wyrm);
    }

    public void addBarricados(Barricados barricados) {
        barricadoses.add(barricados);
    }

    public void addBlackOrb(BlackOrb blackOrb) {
        blackOrbs.add(blackOrb);
    }

    public void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
    }

    public Epsilon getEpsilon() {
        return epsilon;
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Squarantine> getSquarantines() {
        return squarantines;
    }

    public ArrayList<Trigorath> getTrigoraths() {
        return trigoraths;
    }

    public ArrayList<Omenoct> getOmenocts() {
        return omenocts;
    }

    public ArrayList<Necropick> getNecropicks() {
        return necropicks;
    }

    public ArrayList<Archmire> getArchmires() {
        return archmires;
    }

    public ArrayList<Wyrm> getWyrms() {
        return wyrms;
    }

    public ArrayList<Barricados> getBarricadoses() {
        return barricadoses;
    }

    public ArrayList<BlackOrb> getBlackOrbs() {
        return blackOrbs;
    }

    public ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }
}