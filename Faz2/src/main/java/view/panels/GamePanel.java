package view.panels;

import controller.EntityData;
import controller.Util.Direction;
import model.Collectible;
import model.Epsilon;
import model.Shot;
import model.enemies.*;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static controller.Util.Constant.*;
import static controller.Util.Constant.WYRM_SIZE;

public abstract class GamePanel extends MyPanel {
    private static final ArrayList<GamePanel> gamePanels = new ArrayList<>();
    protected boolean rigid;
    protected boolean isometric;
    protected Thread movePanelTimer;
    protected Thread resizePanelTimer;
    protected boolean movingUp, movingDown, movingRight, movingLeft;
    protected final Epsilon epsilon;


    public GamePanel() {
        super();
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
    }

    protected void setResizePanelTimer() {
        if (!isometric && epsilon.getLocalPanel() == this) {
            resizePanelTimer = new Thread(() -> {
                while (true) {
                    minimizePanel();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            resizePanelTimer.start();
        }
    }

    protected void minimizePanel() {
        if (getHeight() > 500) {
            setSize(getWidth(), getHeight() - 2);
            setLocation(getX(), getY() + 1);
        }
        if (getWidth() > 700) {
            setSize(getWidth() - 2, getHeight());
            setLocation(getX() + 1, getY());
        }
    }

    protected void setMovePanelTimer() {
        movePanelTimer = new Thread(() -> {
            while (true) {
                if (movingUp) setLocation(getX(), getY() - GAME_PANEL_SPEED);
                if (movingDown) setLocation(getX(), getY() + GAME_PANEL_SPEED);
                if (movingRight) setLocation(getX() + GAME_PANEL_SPEED, getY());
                if (movingLeft) setLocation(getX() - GAME_PANEL_SPEED, getY());
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        movePanelTimer.start();
    }

    public void increasePanelSize(Direction direction) {
        if (!isometric) {
            Timer increase = new Timer(20, e -> {
                switch (direction) {
                    case Direction.UP:
                        if (getY() > 0) {
                            setSize(getWidth(), getHeight() + 4);
                            setLocation(getX(), getY() - 4);
                        }
                        break;
                    case Direction.DOWN:
                        if (getY() + getHeight() < FRAME_SIZE.getHeight()) {
                            setSize(getWidth(), getHeight() + 4);
                        }
                        break;
                    case Direction.RIGHT:
                        if (getX() + getWidth() < FRAME_SIZE.getWidth()) {
                            setSize(getWidth() + 4, getHeight());
                        }
                        break;
                    case Direction.LEFT:
                        if (getX() > 0) {
                            setSize(getWidth() + 4, getHeight());
                            setLocation(getX() - 4, getY());
                        }
                        break;
                }
            });
            increase.start();
            Timer end = new Timer(400, e -> increase.stop());
            end.setRepeats(false);
            end.start();
        }
    }

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

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isRigid() {
        return rigid;
    }

    public boolean isIsometric() {
        return isometric;
    }

    public void deletePanel() {
        movePanelTimer.interrupt();
        resizePanelTimer.interrupt();
        MyFrame.getInstance().remove(this);
    }
}
