package controller;

import model.*;
import model.enemies.miniBoss.BlackOrb;
import model.enemies.miniBoss.Orb;
import model.enemies.normal.Archmire;
import model.enemies.Enemy;
import model.enemies.normal.Squarantine;
import model.enemies.normal.Wyrm;
import view.panels.FirstPanel;

import static controller.Util.Constant.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Collision {
    static private Timer checkCollisionTimer;
    static private final Epsilon epsilon = Epsilon.getInstance();

    public static void startCheckCollision() {
        checkCollisionTimer = new Timer(COLLISION_TIMER_PERIOD, e -> {
            checkMeleeAttack();
            epsilonAndEnemyCollision();
            enemiesCollision();
            shotAndEnemyCollision();
            rangedShotAndEpsilon();
            epsilonAndCollectibleCollision();
            drownAttackCollision();
            laserAttackCollision();
        });
        checkCollisionTimer.start();
    }

    public static void stopCheckCollision() {
        checkCollisionTimer.stop();
    }

    public static void makeImpact(double x, double y, File sound) {
        playCollisionSound(sound);
        for (Entity entity : EntityData.getEntities()) {
            if (!entity.isHovering() && !entity.isExertion() && !(entity instanceof Wyrm)) {
                double xEntity = entity.getX();
                double yEntity = entity.getY();
                double distance = Math.hypot(xEntity - x, yEntity - y);
                if (distance > 400) continue;
                double xMove = (100 - distance / 5) * (xEntity - x) / (distance * 8);
                double yMove = (100 - distance / 5) * (yEntity - y) / (distance * 8);
                Timer impact = new Timer(10, e -> {
                    entity.setX(entity.getX() + xMove);
                    entity.setY(entity.getY() + yMove);
                });
                impact.start();
                Timer end = new Timer(100, e -> impact.stop());
                end.setRepeats(false);
                end.start();
            }
        }
    }

    private static void checkMeleeAttack() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        for (Enemy enemy : EntityData.getEnemies()) {
            if (enemy.isCanMeleeAttack()) {
                if (Math.abs(enemy.getX() - xEpsilon) > 100 ||
                        Math.abs(enemy.getY() - yEpsilon) > 100) continue;
                for (int i = 0; i < enemy.getVerticesNumber(); i++) {
                    double distance = Math.hypot(
                            xEpsilon - enemy.getXVertex(i),
                            yEpsilon - enemy.getYVertex(i));
                    if (distance < EPSILON_SIZE / 2.0) {
                        double xCollision = (xEpsilon + enemy.getX()) / 2.0;
                        double yCollision = (yEpsilon + enemy.getY()) / 2.0;
                        makeImpact(xCollision, yCollision, null);
                        epsilon.damage(enemy.getMeleeDamage());
                        if (epsilon.getHP() <= 0) {
                            GameManger.getInstance().totalXP += Epsilon.getInstance().getXP();
                            FirstPanel.getInstance().closeGame();
                        }
                        return;
                    }
                }
            }
        }
    }

    private static void epsilonAndEnemyCollision() {
        for (Enemy enemy : EntityData.getEnemies()) {
            if (!enemy.isHovering()) {
                double xEpsilon = epsilon.getX();
                double yEpsilon = epsilon.getY();
                if (Math.abs(enemy.getX() - xEpsilon) > 100 ||
                        Math.abs(enemy.getY() - yEpsilon) > 100) continue;
                if (enemy.getLines().length == 0) {
                    double distance = Math.hypot(enemy.getX() - xEpsilon, enemy.getY() - yEpsilon);
                    if (distance < (enemy.getSize() + epsilon.getSize()) / 2.0) {
                        double xCollision = (xEpsilon + enemy.getX()) / 2.0;
                        double yCollision = (yEpsilon + enemy.getY()) / 2.0;
                        makeImpact(xCollision, yCollision, null);
                        return;
                    }
                } else {
                    for (Line2D line : enemy.getLines()) {
                        if (distanceOfEntityAndLine(
                                xEpsilon, yEpsilon,
                                line.getX1(), line.getY1(),
                                line.getX2(), line.getY2()) < EPSILON_SIZE / 2.0) {
                            if (enemy instanceof Squarantine && ((Squarantine) enemy).isDashing()) {
                                SQUARANTINE_SPEED *= 2.0 / 3;
                                ((Squarantine) enemy).setDashing(false);
                            }
                            double xCollision = (xEpsilon + enemy.getX()) / 2.0;
                            double yCollision = (yEpsilon + enemy.getY()) / 2.0;
                            makeImpact(xCollision, yCollision, null);
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void enemiesCollision() {
        for (int i = 0; i < EntityData.getEnemies().size() - 1; i++) {
            for (int j = i + 1; j < EntityData.getEnemies().size(); j++) {
                Enemy enemy1 = EntityData.getEnemies().get(i);
                Enemy enemy2 = EntityData.getEnemies().get(j);
                if (Math.abs(enemy1.getX() - enemy2.getX()) > 100 ||
                        Math.abs(enemy1.getY() - enemy2.getY()) > 100) continue;
                if (!enemy1.isHovering() && !enemy2.isHovering()) {
                    if (enemy1.getLines().length == 0 && enemy2.getLines().length == 0) {
                        double distance = Math.hypot(enemy1.getX() - enemy2.getX(),
                                enemy1.getY() - enemy2.getX());
                        if (distance < (enemy1.getSize() + enemy2.getSize()) / 2.0) {
                            double xCollision = (enemy1.getX() + enemy2.getX()) / 2.0;
                            double yCollision = (enemy1.getY() + enemy2.getY()) / 2.0;
                            makeImpact(xCollision, yCollision, null);
                            return;
                        }
                    } else if (enemy1.getLines().length == 0 && enemy2.getLines().length != 0) {
                        for (Line2D line : enemy2.getLines()) {
                            if (distanceOfEntityAndLine(
                                    enemy1.getX(), enemy1.getY(),
                                    line.getX1(), line.getY1(),
                                    line.getX2(), line.getY2()) < enemy1.getSize() / 2.0) {
                                double xCollision = (enemy1.getX() + enemy2.getX()) / 2.0;
                                double yCollision = (enemy1.getY() + enemy2.getY()) / 2.0;
                                makeImpact(xCollision, yCollision, null);
                                return;
                            }
                        }
                    } else if (enemy1.getLines().length != 0 && enemy2.getLines().length == 0) {
                        for (Line2D line : enemy2.getLines()) {
                            if (distanceOfEntityAndLine(
                                    enemy2.getX(), enemy2.getY(),
                                    line.getX1(), line.getY1(),
                                    line.getX2(), line.getY2()) < enemy2.getSize() / 2.0) {
                                double xCollision = (enemy1.getX() + enemy2.getX()) / 2.0;
                                double yCollision = (enemy1.getY() + enemy2.getY()) / 2.0;
                                makeImpact(xCollision, yCollision, null);
                                return;
                            }
                        }
                    } else {
                        for (Line2D line1 : enemy1.getLines()) {
                            for (Line2D line2 : enemy2.getLines()) {
                                if (line1.intersectsLine(line2)) {
                                    double xCollision = (enemy1.getX() + enemy2.getX()) / 2.0;
                                    double yCollision = (enemy1.getY() + enemy2.getY()) / 2.0;
                                    makeImpact(xCollision, yCollision, null);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void shotAndEnemyCollision() {
        for (Shot shot : EntityData.getShots()) {
            if (shot.getKind() == Shot.KindOfShot.EPSILON_SHOT) {
                double xShot = shot.getX();
                double yShot = shot.getY();
                for (Enemy enemy : EntityData.getEnemies()) {
                    if (Math.abs(enemy.getX() - xShot) > 100 ||
                            Math.abs(enemy.getY() - yShot) > 100) continue;
                    if (enemy.getLines().length == 0) {
                        double distance = Math.hypot(enemy.getX() - xShot, enemy.getY() - yShot);
                        if (distance < (enemy.getSize() + SHOT_SIZE) / 2.0) {
                            makeImpact(shot.getX(), shot.getY(), ENEMY_DAMAGE_SOUND);
                            shot.setAppear(false);
                            shot.stopMove();
                            if (enemy instanceof Wyrm) ((Wyrm) enemy).changeDirection();
                            enemy.damage(shot.getDamage());
                            if (enemy.getHP() <= 0) {
                                makeCollectible(enemy);
                                enemy.dieProcess();
                            }
                            return;
                        }
                    } else {
                        for (Line2D line : enemy.getLines()) {
                            if (distanceOfEntityAndLine(
                                    xShot, yShot,
                                    line.getX1(), line.getY1(),
                                    line.getX2(), line.getY2()) < SHOT_SIZE / 2.0) {
                                shot.stopMove();
                                makeImpact(shot.getX(), shot.getY(), ENEMY_DAMAGE_SOUND);
                                shot.setAppear(false);
                                shot.stopMove();
                                enemy.damage(shot.getDamage());
                                if (enemy.getHP() <= 0) {
                                    makeCollectible(enemy);
                                    enemy.dieProcess();
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void rangedShotAndEpsilon() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        for (Shot shot : EntityData.getShots()) {
            if (shot.getKind() == Shot.KindOfShot.ENEMY_SHOT) {
                double xShot = shot.getX();
                double yShot = shot.getY();
                double distance = Math.hypot(xEpsilon - xShot, yEpsilon - yShot);
                if (distance < (SHOT_SIZE + EPSILON_SIZE) / 2.0) {
                    shot.stopMove();
                    shot.setAppear(false);
                    shot.stopMove();
                    epsilon.damage(shot.getDamage());
                    if (epsilon.getHP() <= 0) {
                        GameManger.getInstance().totalXP += Epsilon.getInstance().getXP();
                        FirstPanel.getInstance().closeGame();
                        return;
                    }
                }
            }
        }
    }

    private static void makeCollectible(Enemy enemy) {
        Random random = new Random();
        for (int i = 0; i < enemy.getNumberOfCollectible(); i++) {
            int x = random.nextInt(-enemy.getSize(), enemy.getSize());
            int y = random.nextInt(-enemy.getSize(), enemy.getSize());
            new Collectible(enemy.getX() + x, enemy.getY() + y, enemy.getValue());
        }
    }

    private static void epsilonAndCollectibleCollision() {
        for (Collectible collectible : EntityData.getCollectibles()) {
            if (Math.abs(collectible.getX() - epsilon.getX()) < 70 &&
                    Math.abs(collectible.getY() - epsilon.getY()) < 70) {
                collectible.setAppear(false);
                epsilon.changeXP(collectible.getValue());
                playCollisionSound(COLLECT_SOUND);
            }
        }
    }

    private static void drownAttackCollision() {
        ArrayList<Archmire> list = EntityData.getArchmires();
        for (Archmire archmire : list) {
            q:
            for (Entity entity : EntityData.getEntities()) {
                if (!(entity instanceof Archmire)) {
                    double distance = Math.hypot(
                            entity.getX() - archmire.getX(),
                            entity.getY() - archmire.getY());
                    if (distance < (archmire.getSize() - entity.getSize()) / 2.0) {
                        entity.setAoeDamage(archmire.getDrownDamage());
                        entity.startAoEDamage();
                        break;
                    } else {
                        for (Archmire.AoEArea aoEArea : archmire.getAoEAreas()) {
                            distance = Math.hypot(
                                    entity.getX() - aoEArea.getX(),
                                    entity.getY() - aoEArea.getY());
                            if (distance < (archmire.getSize() - entity.getSize()) / 2.0) {
                                entity.setAoeDamage(archmire.getAoeAttackDamage());
                                entity.startAoEDamage();
                                break q;
                            }
                        }
                        entity.setAoeDamage(0);
                    }

                }
            }
        }
    }

    private static void laserAttackCollision() {
        for (BlackOrb blackOrb : EntityData.getBlackOrbs()) {
            for (Entity entity : EntityData.getEntities()) {
                if (!(entity instanceof Orb)) {
                    for (Line2D laser : blackOrb.getLasers()) {
                        if (distanceOfEntityAndLine(entity.getX(), entity.getY(),
                                laser.getX1(), laser.getY1(),
                                laser.getX2(), laser.getY2()) < 2 * entity.getSize() / 3.0) {
                            entity.setAoeDamage(12);
                            entity.startAoEDamage();
                            break;
                        }
                        entity.setAoeDamage(0);
                    }
                }
            }
        }
    }

    public static double distanceOfEntityAndLine(double x1, double y1, double x2,
                                                 double y2, double x3, double y3) {
        double dx = x3 - x2;
        double dy = y3 - y2;
        double lenSquared = dx * dx + dy * dy;
        if (lenSquared == 0) return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double t = ((x1 - x2) * dx + (y1 - y2) * dy) / lenSquared;
        t = Math.max(0, Math.min(1, t));
        double closestX = x2 + t * dx;
        double closestY = y2 + t * dy;
        return Math.sqrt((x1 - closestX) * (x1 - closestX) + (y1 - closestY) * (y1 - closestY));
    }

    private static void playCollisionSound(File sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }
}