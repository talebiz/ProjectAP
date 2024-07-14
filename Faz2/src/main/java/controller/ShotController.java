package controller;

import controller.Util.Direction;
import model.Shot;
import view.panels.GamePanel;

import javax.swing.*;

public class ShotController {

    public static void checkCollisionToWall(Shot shot) {
        if (isPanelOverlapping(shot)) return;
        GamePanel panel = shot.getLocalPanel();
        double xShot = shot.getX();
        double yShot = shot.getY();
        if (xShot + shot.getSize() / 2.0 < panel.getX()) {
            Collision.makeImpact(xShot, yShot, null);
            panel.setMovingLeft(true);
            panel.setMovingRight(false);
            Timer end = new Timer(3000, a -> panel.setMovingLeft(false));
            end.setRepeats(false);
            end.start();
            if (!panel.isIsometric()) panel.increasePanelSize(Direction.LEFT);
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
        if (xShot - shot.getSize() / 2.0 > panel.getX() + panel.getWidth()) {
            Collision.makeImpact(xShot, yShot, null);
            panel.setMovingRight(true);
            panel.setMovingLeft(false);
            Timer end = new Timer(3000, a -> panel.setMovingRight(false));
            end.setRepeats(false);
            end.start();
            if (!panel.isIsometric()) panel.increasePanelSize(Direction.RIGHT);
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
        if (yShot + shot.getSize() / 2.0 < panel.getY()) {
            Collision.makeImpact(xShot, yShot, null);
            panel.setMovingUp(true);
            panel.setMovingDown(false);
            Timer end = new Timer(3000, a -> panel.setMovingUp(false));
            end.setRepeats(false);
            end.start();
            if (!panel.isIsometric()) panel.increasePanelSize(Direction.UP);
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
        if (yShot - shot.getSize() / 2.0 > panel.getY() + panel.getHeight()) {
            Collision.makeImpact(xShot, yShot, null);
            panel.setMovingDown(true);
            panel.setMovingUp(false);
            Timer end = new Timer(3000, a -> panel.setMovingDown(false));
            end.setRepeats(false);
            end.start();
            if (!panel.isIsometric()) panel.increasePanelSize(Direction.DOWN);
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
    }

    public static boolean isPanelOverlapping(Shot shot) {
        double xShot = shot.getX();
        double yShot = shot.getY();
        for (GamePanel panel : GamePanel.getGamePanels()) {
            if (panel != shot.getLocalPanel()) {
                if (xShot - shot.getSize() / 2.0 > panel.getX() &&
                        xShot + shot.getSize() / 2.0 < panel.getX() + panel.getWidth() &&
                        yShot - shot.getSize() / 2.0 > panel.getY() &&
                        yShot + shot.getSize() / 2.0 < panel.getY() + panel.getHeight()) {
                    return true;
                }
//                if (Collision.distanceOfEpsilonAndLine(
//                        xShot, yShot,
//                        panel.getX(), panel.getY(),
//                        panel.getX() + panel.getWidth(), panel.getY())
//                        < shot.getSize() / 2.0 && shot.getYMove() < 0) {
//                    return true;
//                }
//                if (Collision.distanceOfEpsilonAndLine(
//                        xShot, yShot,
//                        panel.getX() + panel.getWidth(), panel.getY(),
//                        panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight())
//                        < shot.getSize() / 2.0 && shot.getXMove() < 0) {
//                    return true;
//                }
//                if (Collision.distanceOfEpsilonAndLine(
//                        xShot, yShot,
//                        panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight(),
//                        panel.getX(), panel.getY() + panel.getHeight())
//                        < shot.getSize() / 2.0 && shot.getY() > 0) {
//                    return true;
//                }
//                if (Collision.distanceOfEpsilonAndLine(
//                        xShot, yShot,
//                        panel.getX(), panel.getY() + panel.getHeight(),
//                        panel.getX(), panel.getY())
//                        < shot.getSize() / 2.0 && shot.getXMove() > 0) {
//                    return true;
//                }
            }
        }
        return false;
    }

    public static void checkIfOut(Shot shot) {
        double xShot = shot.getX();
        double yShot = shot.getY();
        if (xShot < -10 || xShot > 1800 || yShot < -10 || yShot > 1130) {
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
    }
}