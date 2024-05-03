package controller.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Constant {
    //Epsilon
    public static final Image EPSILON_IMAGE = new ImageIcon(
            "src/main/resources/epsilon.png").getImage();
    public static final int EPSILON_SIZE = 45;
    public static int EPSILON_SPEED = 10;

    //Shot
    public static final int SHOT_SPEED = 15;
    public static final int SHOT_SIZE = 15;
    public static final File SHOOT_SOUND = new File("src/main/resources/Glug.wav").getAbsoluteFile();

    //Squarantine
    public static final Image SQUARANTINE_IMAGE = new ImageIcon(
            "src/main/resources/squarantine.png").getImage();
    public static final int SQUARANTINE_SIZE = 20;
    public static int SQUARANTINE_SPEED = 2;
    public static int SQUARANTINE_HP = 10;


    //Frame
    public static final Dimension FRAME_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    //Main Menu Panel
    public static final Dimension MAIN_MENU_PANEL_SIZE = new Dimension(1000, 800);
    public static final Point MAIN_MENU_PANEL_LOCATION = new Point((int) (FRAME_SIZE.getWidth() / 2 - 500),
            (int) (FRAME_SIZE.getHeight() / 2 - 400));

    //Game Panel
    public static int GAME_PANEL_WIDTH = 1000;
    public static int GAME_PANEL_HEIGHT = 800;
    public static int GAME_PANEL_X = 400;
    public static int GAME_PANEL_Y = 150;
    public static int GAME_PANEL_SPEED = 1;
}
