package controller;

import controller.Util.Direction;
import model.Shot;
import view.panels.gamePanels.GamePanel;

import static controller.Util.Constant.FRAME_SIZE;

public class ShotController {

    public static void checkCollisionToWall(Shot shot) {
        if (isPanelOverlapping(shot)) return;
        GamePanel panel = shot.getLocalPanel();
        double xShot = shot.getX();
        double yShot = shot.getY();
        try {
            if (xShot + shot.getSize() / 2.0 < panel.getX()) {
                Collision.makeImpact(xShot, yShot, null);
                panel.move(Direction.LEFT);
                panel.increasePanelSize(Direction.LEFT);
                shot.stopMoveTimer();
                shot.setAppear(false);
            }
            if (xShot - shot.getSize() / 2.0 > panel.getX() + panel.getWidth()) {
                Collision.makeImpact(xShot, yShot, null);
                panel.move(Direction.RIGHT);
                panel.increasePanelSize(Direction.RIGHT);
                shot.stopMoveTimer();
                shot.setAppear(false);
            }
            if (yShot + shot.getSize() / 2.0 < panel.getY()) {
                Collision.makeImpact(xShot, yShot, null);
                panel.move(Direction.UP);
                panel.increasePanelSize(Direction.UP);
                shot.stopMoveTimer();
                shot.setAppear(false);
            }
            if (yShot - shot.getSize() / 2.0 > panel.getY() + panel.getHeight()) {
                Collision.makeImpact(xShot, yShot, null);
                panel.move(Direction.DOWN);
                panel.increasePanelSize(Direction.DOWN);
                shot.stopMoveTimer();
                shot.setAppear(false);
            }
        } catch (Exception e) {
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
            }
        }
        return false;
    }

    public static void checkIfOut(Shot shot) {
        double xShot = shot.getX();
        double yShot = shot.getY();
        if (xShot < 20 || xShot + 20 > FRAME_SIZE.getWidth() ||
                yShot < 20 || yShot + 20 > FRAME_SIZE.getHeight()) {
            shot.stopMoveTimer();
            shot.setAppear(false);
        }
    }
}