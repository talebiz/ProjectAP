package connection.view;

import connection.database.EntityData2;
import connection.database.model.Collectible2;
import connection.database.model.Shot2;
import connection.database.model.enemies.miniBoss.Barricados2;
import connection.database.model.enemies.miniBoss.BlackOrb2;
import connection.database.model.enemies.miniBoss.Orb2;
import connection.database.model.enemies.normal.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static controller.Util.Constant.*;
import static controller.Util.Constant.ORB_SIZE;

public class PaintServer {
    private static int x, y;

    public static void paintFromServer(Graphics2D g2D, int xx, int yy) {
        x = xx;
        y = yy;
        drawArchmire(g2D);
        drawShots(g2D);
        drawSquarantine(g2D);
        drawTrigorath(g2D);
        drawOmenoct(g2D);
        drawNecropick(g2D);
        drawWyrm(g2D);
        drawBarricados(g2D);
        drawBlackOrb(g2D);
        drawCollectible(g2D);
        drawEpsilon(g2D);
    }

    private static void drawEpsilon(Graphics2D g2D) {
        g2D.drawImage(EPSILON_IMAGE,
                (int) (EntityData2.getEpsilon().getX() - 0.5 * EPSILON_SIZE - x),
                (int) (EntityData2.getEpsilon().getY() - 0.5 * EPSILON_SIZE - y),
                EPSILON_SIZE,
                EPSILON_SIZE,
                null);
    }

    private static void drawShots(Graphics2D g2D) {
        ArrayList<Shot2> list = EntityData2.getShots();
        for (int i = 0; i < list.size(); i++) {
            Shot2 shot = list.get(i);
            if (shot.getKind().equals("EPSILON_SHOT")) {
                g2D.drawImage(SHOT_EPSILON_IMAGE,
                        (int) (shot.getX() - x - 0.5 * SHOT_SIZE),
                        (int) (shot.getY() - y - 0.5 * SHOT_SIZE),
                        SHOT_SIZE,
                        SHOT_SIZE,
                        null);
            } else {
                g2D.drawImage(SHOT_ENEMY_IMAGE,
                        (int) (shot.getX() - x - 0.5 * SHOT_SIZE),
                        (int) (shot.getY() - y - 0.5 * SHOT_SIZE),
                        SHOT_SIZE,
                        SHOT_SIZE,
                        null);
            }
        }
    }

    private static void drawSquarantine(Graphics2D g2D) {
        ArrayList<Squarantine2> list = EntityData2.getSquarantines();
        for (int i = 0; i < list.size(); i++) {
            Squarantine2 squarantine = list.get(i);
            g2D.drawImage(SQUARANTINE_IMAGE,
                    (int) (squarantine.getX() - x - 0.5 * SQUARANTINE_SIZE),
                    (int) (squarantine.getY() - y - 0.5 * SQUARANTINE_SIZE),
                    SQUARANTINE_SIZE,
                    SQUARANTINE_SIZE,
                    null);
        }
    }

    private static void drawTrigorath(Graphics2D g2D) {
        ArrayList<Trigorath2> list = EntityData2.getTrigoraths();
        for (int i = 0; i < list.size(); i++) {
            Trigorath2 trigorath = list.get(i);
            g2D.drawImage(TRIGORATH_IMAGE,
                    (int) (trigorath.getX() - x - 0.5 * TRIGORATH_SIZE),
                    (int) (trigorath.getY() - y - 0.58 * TRIGORATH_SIZE),
                    TRIGORATH_SIZE,
                    (int) (0.87 * TRIGORATH_SIZE),
                    null);
        }
    }

    private static void drawOmenoct(Graphics2D g2D) {
        ArrayList<Omenoct2> list = EntityData2.getOmenocts();
        for (int i = 0; i < list.size(); i++) {
            Omenoct2 omenoct = list.get(i);
            g2D.drawImage(OMENOCT_IMAGE,
                    (int) (omenoct.getX() - x - 0.5 * OMENOCT_SIZE),
                    (int) (omenoct.getY() - y - 0.5 * OMENOCT_SIZE),
                    OMENOCT_SIZE,
                    OMENOCT_SIZE,
                    null);
        }
    }

    private static void drawNecropick(Graphics2D g2D) {
        ArrayList<Necropick2> list = EntityData2.getNecropicks();
        for (int i = 0; i < list.size(); i++) {
            Necropick2 necropick = list.get(i);
            g2D.drawImage(NECROPICK2_IMAGE,
                    (int) (necropick.getXGoingTo() - x - 0.5 * NECROPICK_SIZE),
                    (int) (necropick.getYGoingTo() - y - 0.5 * NECROPICK_SIZE),
                    NECROPICK_SIZE,
                    NECROPICK_SIZE,
                    null);
            g2D.drawImage(NECROPICK_IMAGE,
                    (int) (necropick.getX() - x - 0.5 * NECROPICK_SIZE),
                    (int) (necropick.getY() - y - 0.5 * NECROPICK_SIZE),
                    NECROPICK_SIZE,
                    NECROPICK_SIZE,
                    null);
        }
    }

    private static void drawArchmire(Graphics2D g2D) {
        ArrayList<Archmire2> list = EntityData2.getArchmires();
        for (int i = 0; i < list.size(); i++) {
            Archmire2 archmire = list.get(i);
            for (Archmire2.AoEArea aoEArea : archmire.getAoEAreas()) {
                g2D.drawImage(ARCHMIRE2_IMAGE,
                        (int) (aoEArea.getX() - x - 0.5 * ARCHMIRE_SIZE),
                        (int) (aoEArea.getY() - y - 0.5 * ARCHMIRE_SIZE),
                        ARCHMIRE_SIZE,
                        (int) (0.83 * ARCHMIRE_SIZE),
                        null);
            }
            g2D.drawImage(ARCHMIRE_IMAGE,
                    (int) (archmire.getX() - x - 0.5 * ARCHMIRE_SIZE),
                    (int) (archmire.getY() - y - 0.5 * ARCHMIRE_SIZE),
                    ARCHMIRE_SIZE,
                    (int) (0.83 * ARCHMIRE_SIZE),
                    null);

        }
    }

    private static void drawWyrm(Graphics2D g2D) {
        ArrayList<Wyrm2> list = EntityData2.getWyrms();
        for (int i = 0; i < list.size(); i++) {
            Wyrm2 wyrm = list.get(i);
            g2D.drawImage(WYRM_IMAGE,
                    (int) (wyrm.getX() - x - 0.5 * WYRM_SIZE),
                    (int) (wyrm.getY() - y - 0.4 * WYRM_SIZE),
                    WYRM_SIZE,
                    (int) (0.81 * WYRM_SIZE),
                    null);
        }
    }

    private static void drawBarricados(Graphics2D g2D) {
        ArrayList<Barricados2> list = EntityData2.getBarricadoses();
        for (int i = 0; i < list.size(); i++) {
            Barricados2 barricados = list.get(i);
            g2D.drawImage(BARRICADOS_IMAGE,
                    (int) (barricados.getX() - x - 0.5 * BARRICADOS_SIZE),
                    (int) (barricados.getY() - y - 0.5 * BARRICADOS_SIZE),
                    BARRICADOS_SIZE,
                    BARRICADOS_SIZE,
                    null);
        }
    }

    private static void drawBlackOrb(Graphics2D g2D) {
        ArrayList<BlackOrb2> list = EntityData2.getBlackOrbs();
        for (int i = 0; i < list.size(); i++) {
            BlackOrb2 blackOrb = list.get(i);
            ArrayList<Orb2> orbs = blackOrb.getOrbs();
            ArrayList<Line2D> lasers = blackOrb.getLasers();
            g2D.setColor(Color.MAGENTA);
            g2D.setStroke(new BasicStroke(20));
            for (Line2D laser : lasers) {
                g2D.draw(new Line2D.Double(
                        laser.getX1() - x,
                        laser.getY1() - x,
                        laser.getX2() - y,
                        laser.getY2() - y));
            }
            for (int j = 0; j < orbs.size(); j++) {
                Orb2 orb = orbs.get(j);
                g2D.drawImage(ORB_IMAGE,
                        (int) (orb.getX() - x - 0.5 * ORB_SIZE),
                        (int) (orb.getY() - y - 0.5 * ORB_SIZE),
                        ORB_SIZE,
                        ORB_SIZE,
                        null);
            }
            if (blackOrb.getOrbs().isEmpty()) list.remove(i--);
        }
    }

    private static void drawCollectible(Graphics2D g2D) {
        ArrayList<Collectible2> list = EntityData2.getCollectibles();
        for (int i = 0; i < list.size(); i++) {
            g2D.setColor(Color.PINK);
            g2D.drawString("☘︎",
                    (int) list.get(i).getX() - x,
                    (int) list.get(i).getY() - y);
        }
    }
}
