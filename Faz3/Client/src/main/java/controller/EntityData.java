package controller;

import model.Collectible;
import model.Entity;
import model.Shot;
import model.enemies.*;
import model.enemies.miniBoss.*;
import model.enemies.normal.*;

import java.util.ArrayList;

public class EntityData {
    private static final ArrayList<Shot> shots = new ArrayList<>();
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static final ArrayList<Enemy> enemies = new ArrayList<>();
    private static final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private static final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private static final ArrayList<Omenoct> omenocts = new ArrayList<>();
    private static final ArrayList<Necropick> necropicks = new ArrayList<>();
    private static final ArrayList<Archmire> archmires = new ArrayList<>();
    private static final ArrayList<Wyrm> wyrms = new ArrayList<>();
    private static final ArrayList<Barricados> barricadoses = new ArrayList<>();
    private static final ArrayList<BlackOrb> blackOrbs = new ArrayList<>();
    private static final ArrayList<Collectible> collectibles = new ArrayList<>();

    public static void addShot(Shot shot) {
        shots.add(shot);
    }

    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void addSquarantine(Squarantine squarantine) {
        squarantines.add(squarantine);
    }

    public static void addTrigorath(Trigorath trigorath) {
        trigoraths.add(trigorath);
    }

    public static void addOmenoct(Omenoct omenoct) {
        omenocts.add(omenoct);
    }

    public static void addNecropick(Necropick necropick) {
        necropicks.add(necropick);
    }

    public static void addArchmire(Archmire archmire) {
        archmires.add(archmire);
    }

    public static void addWyrm(Wyrm wyrm) {
        wyrms.add(wyrm);
    }

    public static void addBarricados(Barricados barricados) {
        barricadoses.add(barricados);
    }

    public static void addBlackOrb(BlackOrb blackOrb) {
        blackOrbs.add(blackOrb);
    }

    public static void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
    }

    public static ArrayList<Shot> getShots() {
        return shots;
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static ArrayList<Squarantine> getSquarantines() {
        return squarantines;
    }

    public static ArrayList<Trigorath> getTrigoraths() {
        return trigoraths;
    }

    public static ArrayList<Omenoct> getOmenocts() {
        return omenocts;
    }

    public static ArrayList<Necropick> getNecropicks() {
        return necropicks;
    }

    public static ArrayList<Archmire> getArchmires() {
        return archmires;
    }

    public static ArrayList<Wyrm> getWyrms() {
        return wyrms;
    }

    public static ArrayList<Barricados> getBarricadoses() {
        return barricadoses;
    }

    public static ArrayList<BlackOrb> getBlackOrbs() {
        return blackOrbs;
    }

    public static ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }
}