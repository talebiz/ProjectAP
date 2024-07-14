package controller;

import model.Entity;
import model.Epsilon;
import model.Shot;
import view.panels.GamePanel;


import static controller.Util.Constant.EPSILON_SIZE;

public class PanelController {
    static Epsilon epsilon = Epsilon.getInstance();

    public static void setAllLocalPanel() {
        for (Entity entity : EntityData.getEntities()) {
            double xEntity = entity.getX();
            double yEntity = entity.getY();
            GamePanel panel = entity.getLocalPanel();
            if (panel == null ||
                    xEntity - entity.getSize() / 2.0 < panel.getX() ||
                    xEntity + entity.getSize() / 2.0 > panel.getX() + panel.getWidth() ||
                    yEntity - entity.getSize() / 2.0 < panel.getY() ||
                    yEntity + entity.getSize() / 2.0 > panel.getY() + panel.getHeight()) {
                for (GamePanel newLocalPanel : GamePanel.getGamePanels()) {
                    if (xEntity - entity.getSize() / 2.0 > newLocalPanel.getX() &&
                            xEntity + entity.getSize() / 2.0 < newLocalPanel.getX() + newLocalPanel.getWidth() &&
                            yEntity - entity.getSize() / 2.0 > newLocalPanel.getY() &&
                            yEntity + entity.getSize() / 2.0 < newLocalPanel.getY() + newLocalPanel.getHeight()) {
                        entity.setLocalPanel(newLocalPanel);
                        return;
                    }
                }
            }
        }
        for (Shot shot : EntityData.getShots()) {
            double xShot = shot.getX();
            double yShot = shot.getY();
            GamePanel panel = shot.getLocalPanel();
            if (panel == null ||
                    xShot - shot.getSize() / 2.0 < panel.getX() ||
                    xShot + shot.getSize() / 2.0 > panel.getX() + panel.getWidth() ||
                    yShot - shot.getSize() / 2.0 < panel.getY() ||
                    yShot + shot.getSize() / 2.0 > panel.getY() + panel.getHeight()) {
                for (GamePanel newLocalPanel : GamePanel.getGamePanels()) {
                    if (xShot - shot.getSize() / 2.0 > newLocalPanel.getX() &&
                            xShot + shot.getSize() / 2.0 < newLocalPanel.getX() + newLocalPanel.getWidth() &&
                            yShot - shot.getSize() / 2.0 > newLocalPanel.getY() &&
                            yShot + shot.getSize() / 2.0 < newLocalPanel.getY() + newLocalPanel.getHeight()) {
                        shot.setLocalPanel(newLocalPanel);
                        return;
                    }
                }
            }
        }
    }

    public static void keepEpsilonInPanel() {
        GamePanel localPanel = epsilon.getLocalPanel();
        if (isPanelOverlapping()) return;
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        if (xEpsilon - epsilon.getSize() / 2.0 < localPanel.getX())
            epsilon.setX(localPanel.getX() + EPSILON_SIZE / 2.0);
        if (xEpsilon + epsilon.getSize() / 2.0 > localPanel.getX() + localPanel.getWidth())
            epsilon.setX(localPanel.getX() + localPanel.getWidth() - EPSILON_SIZE / 2.0);
        if (yEpsilon - epsilon.getSize() / 2.0 < localPanel.getY())
            epsilon.setY(localPanel.getY() + EPSILON_SIZE / 2.0);
        if (yEpsilon + epsilon.getSize() / 2.0 > localPanel.getY() + localPanel.getHeight())
            epsilon.setY(localPanel.getY() + localPanel.getHeight() - EPSILON_SIZE / 2.0);
    }

    public static boolean isPanelOverlapping() {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        for (GamePanel panel : GamePanel.getGamePanels()) {
            if (panel != epsilon.getLocalPanel()) {
                if (Collision.distanceOfEpsilonAndLine(
                        xEpsilon, yEpsilon,
                        panel.getX(), panel.getY(),
                        panel.getX() + panel.getWidth(), panel.getY())
                        < EPSILON_SIZE / 2.0 && epsilon.isMovingDown()) {
                    System.out.println(1);
                    return true;
                }
                if (Collision.distanceOfEpsilonAndLine(
                        xEpsilon, yEpsilon,
                        panel.getX() + panel.getWidth(), panel.getY(),
                        panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight())
                        < EPSILON_SIZE / 2.0 && epsilon.isMovingLeft()) {
                    System.out.println(2);
                    return true;
                }
                if (Collision.distanceOfEpsilonAndLine(
                        xEpsilon, yEpsilon,
                        panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight(),
                        panel.getX(), panel.getY() + panel.getHeight())
                        < EPSILON_SIZE / 2.0 && epsilon.isMovingUp()) {
                    System.out.println(3);
                    return true;
                }
                if (Collision.distanceOfEpsilonAndLine(
                        xEpsilon, yEpsilon,
                        panel.getX(), panel.getY() + panel.getHeight(),
                        panel.getX(), panel.getY())
                        < EPSILON_SIZE / 2.0 && epsilon.isMovingRight()) {
                    System.out.println(4
                    );
                    return true;
                }
            }
        }
        return false;
    }
}