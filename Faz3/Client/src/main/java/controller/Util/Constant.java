package controller.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Constant {
    //resources path
    private static final String resourcesPath = "Client/src/main/resources/";

    //timelines
    public static int COLLISION_TIMER_PERIOD = 20;
    public static int ENTITY_TIMER_PERIOD = 25;

    //Epsilon
    public static final Image EPSILON_IMAGE = new ImageIcon(
            resourcesPath + "epsilon.png").getImage();
    public static int EPSILON_SIZE = 45;
    public static double EPSILON_SPEED = 0.5 * ENTITY_TIMER_PERIOD;
    public static int EPSILON_HP = 100;
    public static double EPSILON_X;
    public static double EPSILON_Y;

    //Shot
    public static final Image SHOT_EPSILON_IMAGE = new ImageIcon(
            resourcesPath + "shotEpsilon.png").getImage();
    public static final Image SHOT_ENEMY_IMAGE = new ImageIcon(
            resourcesPath + "shotEnemy.png").getImage();
    public static final double SHOT_SPEED = ENTITY_TIMER_PERIOD;
    public static final int SHOT_SIZE = 18;
    public static final File SHOOT_SOUND = new File(resourcesPath + "Glug.wav").getAbsoluteFile();

    //Squarantine
    public static final Image SQUARANTINE_IMAGE = new ImageIcon(
            resourcesPath + "squarantine.png").getImage();
    public static final int SQUARANTINE_SIZE = 50;
    public static double SQUARANTINE_SPEED = 0.12 * ENTITY_TIMER_PERIOD;
    public static int SQUARANTINE_HP = 10;
    public static int SQUARANTINE_COLLECTIBLE_VALUE = 5;
    public static int SQUARANTINE_MELEE_DAMAGE = 6;
    public static int SQUARANTINE_COLLECTIBLE_NUMBER = 1;

    //Trigorath
    public static final Image TRIGORATH_IMAGE = new ImageIcon(
            resourcesPath + "trigorath.png").getImage();
    public static final int TRIGORATH_SIZE = 57;
    public static double TRIGORATH_SPEED = 0.15 * ENTITY_TIMER_PERIOD;
    public static int TRIGORATH_HP = 15;
    public static int TRIGORATH_COLLECTIBLE_VALUE = 5;
    public static int TRIGORATH_MELEE_DAMAGE = 10;
    public static int TRIGORATH_COLLECTIBLE_NUMBER = 2;


    //Omenoct
    public static final Image OMENOCT_IMAGE = new ImageIcon(
            resourcesPath + "omenoct.png").getImage();
    public static final int OMENOCT_SIZE = 80;
    public static double OMENOCT_SPEED = 0.2 * ENTITY_TIMER_PERIOD;
    public static int OMENOCT_HP = 20;
    public static int OMENOCT_COLLECTIBLE_VALUE = 4;
    public static int OMENOCT_MELEE_DAMAGE = 8;
    public static int OMENOCT_COLLECTIBLE_NUMBER = 8;
    public static double OMENOCT_SHOT_SPEED = 0.3 * ENTITY_TIMER_PERIOD;
    public static int OMENOCT_SHOT_DAMAGE = 4;

    //Necropick
    public static final Image NECROPICK_IMAGE = new ImageIcon(
            resourcesPath + "necropick.png").getImage();
    public static final Image NECROPICK2_IMAGE = new ImageIcon(
            resourcesPath + "necropick2.png").getImage();
    public static final int NECROPICK_SIZE = 60;
    public static double NECROPICK_SPEED = 0.2 * ENTITY_TIMER_PERIOD;
    public static int NECROPICK_HP = 10;
    public static int NECROPICK_COLLECTIBLE_VALUE = 2;
    public static int NECROPICK_COLLECTIBLE_NUMBER = 4;

    //Archmire
    public static final Image ARCHMIRE_IMAGE = new ImageIcon(
            resourcesPath + "archmire.png").getImage();
    public static final Image ARCHMIRE2_IMAGE = new ImageIcon(
            resourcesPath + "archmire2.png").getImage();
    public static final int ARCHMIRE_SIZE = 130;
    public static final int MINI_ARCHMIRE_SIZE = ARCHMIRE_SIZE / 2;
    public static double ARCHMIRE_SPEED = 0.1 * ENTITY_TIMER_PERIOD;
    public static int ARCHMIRE_HP = 30;
    public static int MINI_ARCHMIRE_HP = 15;
    public static int ARCHMIRE_COLLECTIBLE_VALUE = 6;
    public static int MINI_ARCHMIRE_COLLECTIBLE_VALUE = 3;
    public static int ARCHMIRE_COLLECTIBLE_NUMBER = 5;
    public static int MINI_ARCHMIRE_COLLECTIBLE_NUMBER = 2;
    public static int ARCHMIRE_DROWN_DAMAGE = 10;
    public static int MINI_ARCHMIRE_DROWN_DAMAGE = ARCHMIRE_DROWN_DAMAGE / 2;
    public static int ARCHMIRE_AOE_DAMAGE = 2;
    public static int MINI_ARCHMIRE_AOE_DAMAGE = ARCHMIRE_AOE_DAMAGE / 2;

    //Wyrm
    public static final Image WYRM_IMAGE = new ImageIcon(
            resourcesPath + "wyrm.png").getImage();
    public static final int WYRM_SIZE = 100;
    public static double WYRM_SPEED = 0.1 * ENTITY_TIMER_PERIOD;
    public static int WYRM_HP = 12;
    public static int WYRM_COLLECTIBLE_VALUE = 8;
    public static int WYRM_COLLECTIBLE_NUMBER = 2;
    public static double WYRM_SHOT_SPEED = 0.3 * ENTITY_TIMER_PERIOD;

    //Barricados
    public static final Image BARRICADOS_IMAGE = new ImageIcon(
            resourcesPath + "barricados.png").getImage();
    public static final int BARRICADOS_SIZE = 140;
    public static int BARRICADOS_HP = Integer.MAX_VALUE;

    //Orb
    public static final Image ORB_IMAGE = new ImageIcon(
            resourcesPath + "orb.png").getImage();
    public static final int ORB_SIZE = 60;
    public static int ORB_HP = 30;
    public static int ORB_COLLECTIBLE_VALUE = 30;
    public static int ORB_COLLECTIBLE_NUMBER = 1;
    public static int ORB_DISTANCE = 300;

    //Frame
    public static final Dimension FRAME_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    //Panel
    public static final Dimension NORMAL_PANEL_SIZE = new Dimension(1000, 800);
    public static final Point NORMAL_PANEL_LOCATION = new Point((int) (FRAME_SIZE.getWidth() / 2 - 500),
            (int) (FRAME_SIZE.getHeight() / 2 - 400));

    //Game Panel
    public static int GAME_PANEL_SPEED = 1;

    //Store Panel
    public static final Dimension STORE_PANEL_SIZE = new Dimension(800, 500);
    public static final Point STORE_PANEL_LOCATION = new Point((int) (FRAME_SIZE.getWidth() / 2 - 400),
            (int) (FRAME_SIZE.getHeight() / 2 - 250));

    //Action Sounds
    public static File COLLECT_SOUND = new File(resourcesPath + "Collect.wav");
    public static File ENEMY_DAMAGE_SOUND = new File(resourcesPath + "hit2.wav");
}