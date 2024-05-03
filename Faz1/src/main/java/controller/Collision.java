package controller;

import model.Entity;
import model.Epsilon;
import model.Shot;
import model.Squarantine;
import view.panels.GamePanel;

import static controller.Util.Constant.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static Timer checkCollisionTimer;
    //    static private final ArrayList<Entity> entities = GamePanel.getInstance().getEntities();
    static private final Epsilon epsilon = Epsilon.getInstance();
    static ArrayList<Squarantine> squarantines = Squarantine.list();
//    static private ArrayList<Entity> entities;



    public static void startCheckCollision() {
        checkCollisionTimer = new Timer(50, e -> {
            squarantinesCollision();
            epsilonAndSquarantineCollision();
//            shotAndEnemyCollision();
        });
        checkCollisionTimer.start();
    }

    private static void squarantinesCollision() {
        for (int i = 0; i < squarantines.size() - 1; i++) {
            for (int j = i + 1; j < squarantines.size(); j++) {
                int x1 = (int) squarantines.get(i).getX();
                int y1 = (int) squarantines.get(i).getY();
                int x2 = (int) squarantines.get(j).getX();
                int y2 = (int) squarantines.get(j).getY();
                Rectangle square1 = new Rectangle(x1, y1, SQUARANTINE_SIZE, SQUARANTINE_SIZE);
                Rectangle square2 = new Rectangle(x2, y2, SQUARANTINE_SIZE, SQUARANTINE_SIZE);
                if (square1.intersects(square2)) {
//                    for (Squarantine squarantine : squarantines) {
//                        squarantine.getMoveTimer().stop();
//                    }
                    double xCollision = (x1 + x2) / 2.0 + SQUARANTINE_SIZE / 2.0;
                    double yCollision = (y1 + y2) / 2.0 + SQUARANTINE_SIZE / 2.0;
                    makeImpact(xCollision, yCollision);
                }
            }
        }
    }

    private static void epsilonAndSquarantineCollision() {
        for (Squarantine squarantine : squarantines) {
            double distanceSquared = getDistanceSquared(
                    squarantine.getX() - SQUARANTINE_SIZE,
                    squarantine.getY() - SQUARANTINE_SIZE,
                    SQUARANTINE_SIZE * 2,
                    epsilon.getX(),
                    epsilon.getY(),
                    EPSILON_SIZE);
            if (distanceSquared <= EPSILON_SIZE / 2.0 * EPSILON_SIZE / 2.0) {
                double xCollision = (epsilon.getX() + EPSILON_SIZE / 2.0 +
                        squarantine.getX() + SQUARANTINE_SIZE / 2.0) / 2.0;
                double yCollision = (epsilon.getY() + EPSILON_SIZE / 2.0 +
                        squarantine.getY() + SQUARANTINE_SIZE / 2.0) / 2.0;
                makeImpact(xCollision, yCollision);
            }
        }
    }

    private static double getDistanceSquared(double sX, double sY, int sSize, double cX, double cY, int cSize) {
        double closestX = Math.max(sX, Math.min(cX + cSize / 2.0, sX + sSize));
        double closestY = Math.max(sY, Math.min(cY + cSize / 2.0, sY + sSize));
        double dx = cX + cSize / 2.0 - closestX;
        double dy = cY + cSize / 2.0 - closestY;
        return dx * dx + dy * dy;
    }

    public static void makeImpact(double x, double y) {
        for (Entity entity : GamePanel.getInstance().getEntities()) {
            double xEntity = entity.getX() + entity.getSize() / 2.0;
            double yEntity = entity.getY() + entity.getSize() / 2.0;
            double distance = Math.hypot(xEntity - x, yEntity - y);
            if (distance > 400) continue;
            double xMove = (100 - distance / 5) * (xEntity - x) / (distance * 8);
            double yMove = (100 - distance / 5) * (yEntity - y) / (distance * 8);
            Timer impact = new Timer(20, e -> {
                entity.setX(entity.getX() + xMove);
                entity.setY(entity.getY() + yMove);
                GamePanel.getInstance().repaint();
            });
            impact.start();
            Timer end = new Timer(100, e -> impact.stop());
            end.setRepeats(false);
            end.start();
        }
    }

    private static void shotAndEnemyCollision() {
        for (Entity enemy : GamePanel.getInstance().getEnemies()) {
            for (Shot shot : Shot.list()) {
                double distanceSquared = getDistanceSquared(enemy.getX(), enemy.getY(), enemy.getSize(),
                        shot.getX(), shot.getY(), SHOT_SIZE);
                if (distanceSquared <= SHOT_SIZE / 2.0 * SHOT_SIZE / 2.0) {
                    shot.getMoveTimer().stop();
                    shot.setAppear(false);
                    makeImpact(shot.getX(), shot.getY());
                }
            }
        }
    }
}