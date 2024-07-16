package view.panels;

import controller.EnemyBuilder;
import controller.EntityData;
import controller.PanelController;
import controller.Util.Direction;
import model.Collectible;
import model.Entity;
import model.Epsilon;
import model.Shot;
import model.enemies.miniBoss.Barricados;
import model.enemies.miniBoss.BlackOrb;
import model.enemies.miniBoss.Orb;
import model.enemies.normal.*;
import view.MyFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static controller.Util.Constant.*;
import static controller.Util.Constant.WYRM_SIZE;

public abstract class GamePanel extends MyPanel {
    private static final ArrayList<GamePanel> gamePanels = new ArrayList<>();
    protected final Epsilon epsilon;
    protected boolean rigid;
    protected boolean isometric;
    protected boolean exertion;
    protected boolean movingUp, movingDown, movingRight, movingLeft;
    private boolean isAthenaEmpower;
    protected boolean running;
    protected Thread movePanelTimer;
    protected Thread resizePanelTimer;
    protected Timer increasePanelTimer;

    public GamePanel() {
        super();
        running = true;
        epsilon = Epsilon.getInstance();
        gamePanels.add(this);
    }

    public static ArrayList<GamePanel> getGamePanels() {
        return gamePanels;
    }

    @Override
    protected void setContent() {
        setResizePanelTimer();
        setMovePanelTimer();
        setShootListener();
    }

    //panel bound process
    protected void setResizePanelTimer() {
        resizePanelTimer = new Thread(() -> {
            while (true) {
                if (!isometric && epsilon.getLocalPanel() == this) minimizePanel();
                waiting(60);
            }
        });
        resizePanelTimer.start();
    }

    protected void minimizePanel() {
        if (running) {
            if (getHeight() > 500) {
                setSize(getWidth(), getHeight() - 2);
                setLocation(getX(), getY() + 1);
            }
            if (getWidth() > 700) {
                setSize(getWidth() - 2, getHeight());
                setLocation(getX() + 1, getY());
            }
        }
    }

    protected void setMovePanelTimer() {
        movePanelTimer = new Thread(() -> {
            if (!exertion) {
                while (true) {
                    if (running) {
                        checkPanelCollisionInMoving();
                        checkOutOfScreen();
                        moving();
                        waiting(30);
                    }
                }
            }
        });
        movePanelTimer.start();
    }

    private void checkPanelCollisionInMoving() {
        if (PanelController.panelCollision()) {
            movingUp = movingDown = movingRight = movingLeft = false;
            setSize(getWidth() - 2, getHeight() - 2);
            setLocation(getX() + 1, getY() + 1);
        }
    }

    private void checkOutOfScreen() {
        if (getX() < 0 && movingLeft) {
            setSize(getWidth() - 2, getHeight());
            setLocation(getX() + 2, getY());
            movingLeft = false;
        }
        if (getX() + getWidth() > FRAME_SIZE.getWidth() && movingRight) {
            setSize(getWidth() - 2, getHeight());
            movingRight = false;
        }
        if (getY() < 0 && movingUp) {
            setSize(getWidth(), getHeight() - 2);
            setLocation(getX(), getY() + 2);
            movingUp = false;
        }
        if (getY() + getHeight() > FRAME_SIZE.getHeight() && movingDown) {
            setSize(getWidth(), getHeight() - 2);
            movingDown = false;
        }
    }

    private void moving() {
        if (movingUp) setLocation(getX(), getY() - GAME_PANEL_SPEED);
        if (movingDown) setLocation(getX(), getY() + GAME_PANEL_SPEED);
        if (movingRight) setLocation(getX() + GAME_PANEL_SPEED, getY());
        if (movingLeft) setLocation(getX() - GAME_PANEL_SPEED, getY());
    }

    protected static void waiting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void move(Direction direction) {
        if (running) {
            switch (direction) {
                case UP -> {
                    movingDown = false;
                    movingUp = true;
                    endMoving(direction);
                }
                case DOWN -> {
                    movingUp = false;
                    movingDown = true;
                    endMoving(direction);
                }
                case RIGHT -> {
                    movingLeft = false;
                    movingRight = true;
                    endMoving(direction);
                }
                case LEFT -> {
                    movingRight = false;
                    movingLeft = true;
                    endMoving(direction);
                }
            }
        }
    }

    private void endMoving(Direction direction) {
        new Timer(3000, e -> {
            switch (direction) {
                case UP -> movingUp = false;
                case DOWN -> movingDown = false;
                case RIGHT -> movingRight = false;
                case LEFT -> movingLeft = false;
            }
        }) {{
            setRepeats(false);
        }}.start();
    }

    public void increasePanelSize(Direction direction) {
        if (!isometric) {
            if (increasePanelTimer != null &&
                    increasePanelTimer.isRunning()) increasePanelTimer.stop();
            increasePanelTimer = new Timer(20, e -> {
                if (running) {
                    checkPanelCollisionInIncreasing();
                    increasing(direction);
                }
            });
            increasePanelTimer.start();
            endIncreasing();
        }
    }

    private void checkPanelCollisionInIncreasing() {
        if (PanelController.panelCollision()) {
            setSize(getWidth() - 8, getHeight() - 8);
            setLocation(getX() + 4, getY() + 4);
            increasePanelTimer.stop();
        }
    }

    private void increasing(Direction direction) {
        switch (direction) {
            case Direction.UP -> {
                if (getY() > 0) {
                    setSize(getWidth(), getHeight() + 4);
                    setLocation(getX(), getY() - 4);
                } else {
                    setSize(getWidth(), getHeight() - 5);
                    setLocation(getX(), getY() + 5);
                    increasePanelTimer.stop();
                }
            }
            case Direction.DOWN -> {
                if (getY() + getHeight() < FRAME_SIZE.getHeight()) {
                    setSize(getWidth(), getHeight() + 4);
                } else {
                    setSize(getWidth(), getHeight() - 5);
                    increasePanelTimer.stop();
                }
            }
            case Direction.RIGHT -> {
                if (getX() + getWidth() < FRAME_SIZE.getWidth()) {
                    setSize(getWidth() + 4, getHeight());
                } else {
                    setSize(getWidth() - 5, getHeight());
                    increasePanelTimer.stop();
                }
            }
            case Direction.LEFT -> {
                if (getX() > 0) {
                    setSize(getWidth() + 4, getHeight());
                    setLocation(getX() - 4, getY());
                } else {
                    setSize(getWidth() - 5, getHeight());
                    setLocation(getX() + 5, getY());
                    increasePanelTimer.stop();
                }
            }
        }
    }

    private void endIncreasing() {
        Timer end = new Timer(400, e -> increasePanelTimer.stop());
        end.setRepeats(false);
        end.start();
    }

    //shoot process
    private void setShootSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SHOOT_SOUND);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }

    private void setShootListener() {
        this.addMouseListener(new GameMouseListener());
    }

    private void setDirectionAndShoot(double xClicked, double yClicked) {
        double xEpsilon = epsilon.getX();
        double yEpsilon = epsilon.getY();
        double distance = Math.hypot(xClicked - xEpsilon, yClicked - yEpsilon);
        double xMove = SHOT_SPEED * (xClicked - xEpsilon) / distance;
        double yMove = SHOT_SPEED * (yClicked - yEpsilon) / distance;
        if (isAthenaEmpower) {
            athenaEmpower(xEpsilon, yEpsilon, xMove, yMove);
        } else {
            new Shot(xEpsilon, yEpsilon, xMove, yMove, 5, Shot.KindOfShot.EPSILON_SHOT, true);
        }
    }

    private void athenaEmpower(double xEpsilon, double yEpsilon, double xMove, double yMove) {
        Timer timer = new Timer(50, e ->
                new Shot(xEpsilon, yEpsilon, xMove, yMove, 5, Shot.KindOfShot.EPSILON_SHOT, true));
        timer.start();
        Timer end = new Timer(170, e -> timer.stop());
        end.setRepeats(false);
        end.start();
    }

    //paint process
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
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

    private void drawEpsilon(Graphics2D g2D) {
        g2D.drawImage(EPSILON_IMAGE,
                (int) (Epsilon.getInstance().getX() - 0.5 * EPSILON_SIZE - getX()),
                (int) (Epsilon.getInstance().getY() - 0.5 * EPSILON_SIZE - getY()),
                EPSILON_SIZE,
                EPSILON_SIZE,
                null);
    }

    private void drawShots(Graphics2D g2D) {
        ArrayList<Shot> list = EntityData.getShots();
        for (int i = 0; i < list.size(); i++) {
            Shot shot = list.get(i);
            if (shot.isAppear()) {
                g2D.drawImage(shot.getImage(),
                        (int) (shot.getX() - getX() - 0.5 * SHOT_SIZE),
                        (int) (shot.getY() - getY() - 0.5 * SHOT_SIZE),
                        SHOT_SIZE,
                        SHOT_SIZE,
                        null);
            } else {
                list.remove(i--);
            }
        }
    }

    private void drawSquarantine(Graphics2D g2D) {
        ArrayList<Squarantine> list = EntityData.getSquarantines();
        for (int i = 0; i < list.size(); i++) {
            Squarantine squarantine = list.get(i);
            if (squarantine.isAlive()) {
                g2D.drawImage(SQUARANTINE_IMAGE,
                        (int) (squarantine.getX() - getX() - 0.5 * SQUARANTINE_SIZE),
                        (int) (squarantine.getY() - getY() - 0.5 * SQUARANTINE_SIZE),
                        SQUARANTINE_SIZE,
                        SQUARANTINE_SIZE,
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(squarantine);
                EntityData.getEntities().remove(squarantine);
            }
        }
    }

    private void drawTrigorath(Graphics2D g2D) {
        ArrayList<Trigorath> list = EntityData.getTrigoraths();
        for (int i = 0; i < list.size(); i++) {
            Trigorath trigorath = list.get(i);
            if (trigorath.isAlive()) {
                g2D.drawImage(TRIGORATH_IMAGE,
                        (int) (trigorath.getX() - getX() - 0.5 * TRIGORATH_SIZE),
                        (int) (trigorath.getY() - getY() - 0.58 * TRIGORATH_SIZE),
                        TRIGORATH_SIZE,
                        (int) (0.87 * TRIGORATH_SIZE),
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(trigorath);
                EntityData.getEntities().remove(trigorath);
            }
        }
    }

    private void drawOmenoct(Graphics2D g2D) {
        ArrayList<Omenoct> list = EntityData.getOmenocts();
        for (int i = 0; i < list.size(); i++) {
            Omenoct omenoct = list.get(i);
            if (omenoct.isAlive()) {
                g2D.drawImage(OMENOCT_IMAGE,
                        (int) (omenoct.getX() - getX() - 0.5 * OMENOCT_SIZE),
                        (int) (omenoct.getY() - getY() - 0.5 * OMENOCT_SIZE),
                        OMENOCT_SIZE,
                        OMENOCT_SIZE,
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(omenoct);
                EntityData.getEntities().remove(omenoct);
            }
        }
    }

    private void drawNecropick(Graphics2D g2D) {
        ArrayList<Necropick> list = EntityData.getNecropicks();
        for (int i = 0; i < list.size(); i++) {
            Necropick necropick = list.get(i);
            if (necropick.isAlive()) {
                g2D.drawImage(NECROPICK2_IMAGE,
                        (int) (necropick.getXGoingTo() - getX() - 0.5 * NECROPICK_SIZE),
                        (int) (necropick.getYGoingTo() - getY() - 0.5 * NECROPICK_SIZE),
                        NECROPICK_SIZE,
                        NECROPICK_SIZE,
                        null);
                g2D.drawImage(NECROPICK_IMAGE,
                        (int) (necropick.getX() - getX() - 0.5 * NECROPICK_SIZE),
                        (int) (necropick.getY() - getY() - 0.5 * NECROPICK_SIZE),
                        NECROPICK_SIZE,
                        NECROPICK_SIZE,
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(necropick);
                EntityData.getEntities().remove(necropick);
            }
        }
    }

    private void drawArchmire(Graphics2D g2D) {
        ArrayList<Archmire> list = EntityData.getArchmires();
        for (int i = 0; i < list.size(); i++) {
            Archmire archmire = list.get(i);
            if (archmire.isAlive()) {
                for (Archmire.AoEArea aoEArea : archmire.getAoEAreas()) {
                    g2D.drawImage(ARCHMIRE2_IMAGE,
                            (int) (aoEArea.getX() - getX() - 0.5 * archmire.getSize()),
                            (int) (aoEArea.getY() - getY() - 0.5 * archmire.getSize()),
                            archmire.getSize(),
                            (int) (0.83 * archmire.getSize()),
                            null);
                }
                g2D.drawImage(ARCHMIRE_IMAGE,
                        (int) (archmire.getX() - getX() - 0.5 * archmire.getSize()),
                        (int) (archmire.getY() - getY() - 0.5 * archmire.getSize()),
                        archmire.getSize(),
                        (int) (0.83 * archmire.getSize()),
                        null);

            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(archmire);
                EntityData.getEntities().remove(archmire);
            }
        }
    }

    private void drawWyrm(Graphics2D g2D) {
        ArrayList<Wyrm> list = EntityData.getWyrms();
        for (int i = 0; i < list.size(); i++) {
            Wyrm wyrm = list.get(i);
            if (wyrm.isAlive()) {
                g2D.drawImage(WYRM_IMAGE,
                        (int) (wyrm.getX() - getX() - 0.5 * WYRM_SIZE),
                        (int) (wyrm.getY() - getY() - 0.4 * WYRM_SIZE),
                        WYRM_SIZE,
                        (int) (0.81 * WYRM_SIZE),
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(wyrm);
                EntityData.getEntities().remove(wyrm);
            }
        }
    }

    private void drawBarricados(Graphics2D g2D) {
        ArrayList<Barricados> list = EntityData.getBarricadoses();
        for (int i = 0; i < list.size(); i++) {
            Barricados barricados = list.get(i);
            if (barricados.isAlive()) {
                g2D.drawImage(BARRICADOS_IMAGE,
                        (int) (barricados.getX() - getX() - 0.5 * BARRICADOS_SIZE),
                        (int) (barricados.getY() - getY() - 0.5 * BARRICADOS_SIZE),
                        BARRICADOS_SIZE,
                        BARRICADOS_SIZE,
                        null);
            } else {
                list.remove(i--);
                EntityData.getEnemies().remove(barricados);
                EntityData.getEntities().remove(barricados);
            }
        }
    }

    private void drawBlackOrb(Graphics2D g2D) {
        ArrayList<BlackOrb> list = EntityData.getBlackOrbs();
        for (int i = 0; i < list.size(); i++) {
            BlackOrb blackOrb = list.get(i);
            ArrayList<Orb> orbs = blackOrb.getOrbs();
            ArrayList<Line2D> lasers = blackOrb.getLasers();
            g2D.setColor(Color.MAGENTA);
            g2D.setStroke(new BasicStroke(20));
            for (Line2D laser : lasers) {
                g2D.draw(new Line2D.Double(
                        laser.getX1() - getX(),
                        laser.getY1() - getY(),
                        laser.getX2() - getX(),
                        laser.getY2() - getY()));
            }
            for (int j = 0; j < orbs.size(); j++) {
                Orb orb = orbs.get(j);
                if (orb.isAlive()) {
                    g2D.drawImage(ORB_IMAGE,
                            (int) (orb.getX() - getX() - 0.5 * ORB_SIZE),
                            (int) (orb.getY() - getY() - 0.5 * ORB_SIZE),
                            ORB_SIZE,
                            ORB_SIZE,
                            null);
                } else {
                    orbs.remove(j--);
                    blackOrb.setLasers();
                    EntityData.getEnemies().remove(orb);
                    EntityData.getEntities().remove(orb);
                }
            }
            if (blackOrb.getOrbs().isEmpty()) list.remove(i--);
        }
    }

    private void drawCollectible(Graphics2D g2D) {
        ArrayList<Collectible> list = EntityData.getCollectibles();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isAppear()) {
                g2D.setColor(Color.PINK);
                g2D.drawString("☘︎",
                        (int) list.get(i).getX() - getX(),
                        (int) list.get(i).getY() - getY());
            } else {
                list.remove(i--);
            }
        }
    }

    public void deletePanel() {
        try {
            movePanelTimer.interrupt();
            resizePanelTimer.interrupt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        gamePanels.remove(this);
        setVisible(false);
        MyFrame.getInstance().remove(this);
    }

    public void pause() {
        setLocation(getX() + 2000, getY() + 2000);
        running = false;
    }

    public void resume() {
        setLocation(getX() - 2000, getY() - 2000);
        running = true;
    }

    //getter and setter
    public boolean isRigid() {
        return rigid;
    }

    public void setAthenaEmpower(boolean athenaEmpower) {
        isAthenaEmpower = athenaEmpower;
    }

    //listener class
    private class GameMouseListener implements MouseListener {
        boolean canShot = true;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (canShot) {
                setDirectionAndShoot(e.getX() + getX(), e.getY() + getY());
                setShootSound();
                canShot = false;
                new Timer(250, a -> canShot = true) {{
                    setRepeats(false);
                }}.start();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}