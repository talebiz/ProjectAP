package controller;

import model.*;
import view.panels.GamePanel;

import static controller.Util.Constant.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static private Timer checkCollisionTimer;
    static private final Epsilon epsilon = Epsilon.getInstance();
    static private final ArrayList<Squarantine> squarantines = Squarantine.list();
    static private final ArrayList<Trigorath> trigoraths = Trigorath.list();


    public static void startCheckCollision() {
        checkCollisionTimer = new Timer(50, e -> {
            checkMeleeAttack();
            squarantinesCollision();
            epsilonAndSquarantineCollision();
            trigorathsCollision();
            epsilonAndTrigorathCollision();
            squarantineAndTrigorathCollision();
            shotAndEnemyCollision();
            epsilonAndCollectible();
        });
        checkCollisionTimer.start();
    }

    public static void stopCheckCollision() {
        checkCollisionTimer.stop();
    }

    public static void makeImpact(double x, double y) {
        for (Entity entity : Entity.getEntities()) {
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

    private static void checkMeleeAttack() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        for (Enemy enemy : Enemy.getEnemies()) {
            for (int i = 0; i < enemy.getXVertex().length; i++) {
                double distance = Math.hypot(
                        xEpsilon - enemy.getXVertex(i) - GAME_PANEL_X + 20,
                        yEpsilon - enemy.getYVertex(i) - GAME_PANEL_Y + 20);
                if (distance < EPSILON_SIZE / 2.0) {
                    double xCollision = (epsilon.getX() + EPSILON_SIZE / 2.0 + enemy.getX()) / 2.0;
                    double yCollision = (epsilon.getY() + EPSILON_SIZE / 2.0 + enemy.getY()) / 2.0;
                    makeImpact(xCollision, yCollision);
                    if (enemy instanceof Squarantine) epsilon.damage(6);
                    else if (enemy instanceof Trigorath) epsilon.damage(10);
                }
            }
        }
    }

    private static void squarantinesCollision() {
        for (int i = 0; i < squarantines.size() - 1; i++) {
            for (int j = i + 1; j < squarantines.size(); j++) {
                int x1 = (int) squarantines.get(i).getX();
                int y1 = (int) squarantines.get(i).getY();
                int x2 = (int) squarantines.get(j).getX();
                int y2 = (int) squarantines.get(j).getY();
                if (Math.abs(x1 - x2) > 100 || Math.abs(y1 - y2) > 100) continue;
                Rectangle square1 = new Rectangle(x1 - SQUARANTINE_SIZE, y1 - SQUARANTINE_SIZE,
                        2 * SQUARANTINE_SIZE, SQUARANTINE_SIZE * 2);
                Rectangle square2 = new Rectangle(x2 - SQUARANTINE_SIZE, y2 - SQUARANTINE_SIZE,
                        2 * SQUARANTINE_SIZE, SQUARANTINE_SIZE * 2);
                if (square1.intersects(square2)) {
                    double xCollision = (x1 + x2) / 2.0;
                    double yCollision = (y1 + y2) / 2.0;
                    makeImpact(xCollision, yCollision);
                }
            }
        }
    }

    private static void epsilonAndSquarantineCollision() {
        for (Squarantine squarantine : squarantines) {
            if (Math.abs(squarantine.getX() - epsilon.getX()) > 100 ||
                    Math.abs(squarantine.getY() - epsilon.getY()) > 100) continue;
            double distanceSquared = getDistanceSquared(
                    squarantine.getX() - SQUARANTINE_SIZE,
                    squarantine.getY() - SQUARANTINE_SIZE,
                    SQUARANTINE_SIZE * 2,
                    epsilon.getX(),
                    epsilon.getY(),
                    EPSILON_SIZE);
            if (distanceSquared <= EPSILON_SIZE / 2.0 * EPSILON_SIZE / 2.0) {
                double xCollision = (epsilon.getX() + EPSILON_SIZE / 2.0 + squarantine.getX()) / 2.0;
                double yCollision = (epsilon.getY() + EPSILON_SIZE / 2.0 + squarantine.getY()) / 2.0;
                makeImpact(xCollision, yCollision);
            }
        }
    }

    private static double getDistanceSquared(double sX, double sY, int sSize,
                                             double cX, double cY, int cSize) {
        double closestX = Math.max(sX, Math.min(cX + cSize / 2.0, sX + sSize));
        double closestY = Math.max(sY, Math.min(cY + cSize / 2.0, sY + sSize));
        double dx = cX + cSize / 2.0 - closestX;
        double dy = cY + cSize / 2.0 - closestY;
        return dx * dx + dy * dy;
    }

    private static void trigorathsCollision() {
        for (int i = 0; i < trigoraths.size() - 1; i++) {
            for (int j = i + 1; j < trigoraths.size(); j++) {
                Trigorath trigorath1 = trigoraths.get(i);
                Trigorath trigorath2 = trigoraths.get(j);
                if (Math.abs(trigorath1.getX() - trigorath2.getX()) > 100 ||
                        Math.abs(trigorath1.getY() - trigorath2.getY()) > 100) continue;
                int x1 = trigorath1.getXVertex(0), y1 = trigorath1.getYVertex(0);
                int x2 = trigorath1.getXVertex(1), y2 = trigorath1.getYVertex(1);
                int x3 = trigorath1.getXVertex(2), y3 = trigorath1.getYVertex(2);
                int x4 = trigorath2.getXVertex(0), y4 = trigorath2.getYVertex(0);
                int x5 = trigorath2.getXVertex(1), y5 = trigorath2.getYVertex(1);
                int x6 = trigorath2.getXVertex(2), y6 = trigorath2.getYVertex(2);
                boolean n1 = isTwoLinesCollision(x1, y1, x2, y2, x4, y4, x5, y5);
                boolean n2 = isTwoLinesCollision(x1, y1, x2, y2, x4, y4, x6, y6);
                boolean n3 = isTwoLinesCollision(x1, y1, x2, y2, x5, y5, x6, y6);
                boolean n4 = isTwoLinesCollision(x1, y1, x3, y3, x4, y4, x5, y5);
                boolean n5 = isTwoLinesCollision(x1, y1, x3, y3, x4, y4, x6, y6);
                boolean n6 = isTwoLinesCollision(x1, y1, x3, y3, x5, y5, x6, y6);
                boolean n7 = isTwoLinesCollision(x2, y2, x3, y3, x4, y4, x5, y5);
                boolean n8 = isTwoLinesCollision(x2, y2, x3, y3, x4, y4, x6, y6);
                boolean n9 = isTwoLinesCollision(x2, y2, x3, y3, x5, y5, x6, y6);
                if (n1 || n2 || n3 || n4 || n5 || n6 || n7 || n8 || n9) {
                    double xCollision = (trigorath1.getX() + trigorath2.getX()) / 2.0;
                    double yCollision = (trigorath1.getY() + trigorath2.getY()) / 2.0;
                    makeImpact(xCollision, yCollision);
                }
            }
        }
    }

    private static boolean isTwoLinesCollision(double x1, double y1, double x2, double y2,
                                               double x3, double y3, double x4, double y4) {
        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denominator == 0) {
            return false;
        }
        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;
        return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
    }

    private static void epsilonAndTrigorathCollision() {
        for (Trigorath trigorath : trigoraths) {
            if (Math.abs(trigorath.getX() - epsilon.getX()) > 100 ||
                    Math.abs(trigorath.getY() - epsilon.getY()) > 100) continue;
            int x1 = trigorath.getXVertex(0) + GAME_PANEL_X;
            int y1 = trigorath.getYVertex(0) + GAME_PANEL_Y;
            int x2 = trigorath.getXVertex(1) + GAME_PANEL_X;
            int y2 = trigorath.getYVertex(1) + GAME_PANEL_Y;
            int x3 = trigorath.getXVertex(2) + GAME_PANEL_X;
            int y3 = trigorath.getYVertex(2) + GAME_PANEL_Y;
            double x = epsilon.getX() + EPSILON_SIZE / 2.0;
            double y = epsilon.getY() + EPSILON_SIZE / 2.0;
            boolean n1 = distanceOfEpsilonAndLine(x, y, x1, y1, x2, y2) < EPSILON_SIZE / 2.0;
            boolean n2 = distanceOfEpsilonAndLine(x, y, x1, y1, x3, y3) < EPSILON_SIZE / 2.0;
            boolean n3 = distanceOfEpsilonAndLine(x, y, x2, y2, x3, y3) < EPSILON_SIZE / 2.0;
            if (n1 || n2 || n3) {
                double xCollision = (x + trigorath.getX()) / 2.0;
                double yCollision = (y + trigorath.getY()) / 2.0;
                makeImpact(xCollision, yCollision);
            }
        }
    }

    private static void squarantineAndTrigorathCollision() {
        for (Squarantine squarantine : squarantines) {
            for (Trigorath trigorath : trigoraths) {
                if (Math.abs(trigorath.getX() - squarantine.getX()) > 100 ||
                        Math.abs(trigorath.getY() - squarantine.getY()) > 100) continue;
                int sx1 = squarantine.getXVertex(0), sy1 = squarantine.getYVertex(0);
                int sx2 = squarantine.getXVertex(1), sy2 = squarantine.getYVertex(1);
                int sx3 = squarantine.getXVertex(2), sy3 = squarantine.getYVertex(2);
                int sx4 = squarantine.getXVertex(3), sy4 = squarantine.getYVertex(3);
                int tx1 = trigorath.getXVertex(0), ty1 = trigorath.getYVertex(0);
                int tx2 = trigorath.getXVertex(1), ty2 = trigorath.getYVertex(1);
                int tx3 = trigorath.getXVertex(2), ty3 = trigorath.getYVertex(2);
                boolean n1 = isTwoLinesCollision(sx1, sy1, sx2, sy2, tx1, ty1, tx2, ty2);
                boolean n2 = isTwoLinesCollision(sx1, sy1, sx2, sy2, tx1, ty1, tx3, ty3);
                boolean n3 = isTwoLinesCollision(sx1, sy1, sx2, sy2, tx2, ty2, tx3, ty3);

                boolean n4 = isTwoLinesCollision(sx1, sy1, sx4, sy4, tx1, ty1, tx2, ty2);
                boolean n5 = isTwoLinesCollision(sx1, sy1, sx4, sy4, tx1, ty1, tx3, ty3);
                boolean n6 = isTwoLinesCollision(sx1, sy1, sx4, sy4, tx2, ty2, tx3, ty3);

                boolean n7 = isTwoLinesCollision(sx2, sy2, sx3, sy3, tx1, ty1, tx2, ty2);
                boolean n8 = isTwoLinesCollision(sx2, sy2, sx3, sy3, tx1, ty1, tx3, ty3);
                boolean n9 = isTwoLinesCollision(sx2, sy2, sx3, sy3, tx2, ty2, tx3, ty3);

                boolean n10 = isTwoLinesCollision(sx3, sy3, sx4, sy4, tx1, ty1, tx2, ty2);
                boolean n11 = isTwoLinesCollision(sx3, sy3, sx4, sy4, tx1, ty1, tx3, ty3);
                boolean n12 = isTwoLinesCollision(sx3, sy3, sx4, sy4, tx2, ty2, tx3, ty3);

                if (n1 || n2 || n3 || n4 || n5 || n6 || n7 || n8 || n9 || n10 || n11 || n12) {
                    double xCollision = (squarantine.getX() + trigorath.getX()) / 2.0;
                    double yCollision = (squarantine.getY() + trigorath.getY()) / 2.0;
                    makeImpact(xCollision, yCollision);
                }
            }
        }
    }

    public static double distanceOfEpsilonAndLine(double x1, double y1, double x2,
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

    private static void shotAndEnemyCollision() {
        for (Squarantine squarantine : squarantines) {
            for (Shot shot : Shot.list()) {
                if (Math.abs(squarantine.getX() - shot.getX()) > 100 ||
                        Math.abs(squarantine.getY() - shot.getY()) > 100) continue;

                double distanceSquared = getDistanceSquared(
                        squarantine.getX() - SQUARANTINE_SIZE,
                        squarantine.getY() - SQUARANTINE_SIZE,
                        SQUARANTINE_SIZE * 2,
                        shot.getX(),
                        shot.getY(),
                        SHOT_SIZE);
                if (distanceSquared <= SHOT_SIZE / 2.0 * SHOT_SIZE / 2.0) {
                    shot.stopMove();
                    makeImpact(shot.getX(), shot.getY());
                    shot.setAppear(false);
                    squarantine.damage(5);
                    if (squarantine.getHP() <= 0) {
                        squarantine.setAppear(false);
                        new Collectible(
                                squarantine.getX(),
                                squarantine.getY());
                    }
                }
            }
        }
        for (Trigorath trigorath : trigoraths) {
            for (Shot shot : Shot.list()) {
                if (Math.abs(trigorath.getX() - shot.getX()) > 100 ||
                        Math.abs(trigorath.getY() - shot.getY()) > 100) continue;
                int x1 = trigorath.getXVertex(0) + GAME_PANEL_X;
                int y1 = trigorath.getYVertex(0) + GAME_PANEL_Y;
                int x2 = trigorath.getXVertex(1) + GAME_PANEL_X;
                int y2 = trigorath.getYVertex(1) + GAME_PANEL_Y;
                int x3 = trigorath.getXVertex(2) + GAME_PANEL_X;
                int y3 = trigorath.getYVertex(2) + GAME_PANEL_Y;
                double x = shot.getX() + SHOT_SIZE / 2.0;
                double y = shot.getY() + SHOT_SIZE / 2.0;
                boolean n1 = distanceOfEpsilonAndLine(x, y, x1, y1, x2, y2) < SHOT_SIZE / 2.0;
                boolean n2 = distanceOfEpsilonAndLine(x, y, x1, y1, x3, y3) < SHOT_SIZE / 2.0;
                boolean n3 = distanceOfEpsilonAndLine(x, y, x2, y2, x3, y3) < SHOT_SIZE / 2.0;
                if (n1 || n2 || n3) {
                    shot.stopMove();
                    double xCollision = (x + trigorath.getX()) / 2.0;
                    double yCollision = (y + trigorath.getY()) / 2.0;
                    makeImpact(xCollision, yCollision);
                    shot.setAppear(false);
                    trigorath.damage(5);
                    if (trigorath.getHP() <= 0) {
                        trigorath.setAppear(false);
                        new Collectible(
                                trigorath.getX() - 7,
                                trigorath.getY());
                        new Collectible(
                                trigorath.getX() + 7 ,
                                trigorath.getY());
                    }
                }
            }
        }
    }

    private static void epsilonAndCollectible() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        for (Collectible collectible : Collectible.list()) {
            if (Math.abs(collectible.getX() - xEpsilon) < 70 && Math.abs(collectible.getY() - yEpsilon) < 70) {
                collectible.setAppear(false);
                epsilon.takeXP();
            }
        }
    }
}