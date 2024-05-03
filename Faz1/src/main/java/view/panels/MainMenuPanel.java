package view.panels;

import model.Epsilon;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public class MainMenuPanel extends MyPanel {
    JButton exitButton, startButton, settingButton, tutorialButton,skillTreeButton;
    Epsilon epsilon;
    Timer moveEpsilonTimer;
    int XMoveLength = -EPSILON_SPEED, YMoveLength = -EPSILON_SPEED;

    public MainMenuPanel() {
        super();
        this.setSize(MAIN_MENU_PANEL_SIZE);
        this.setLocation(MAIN_MENU_PANEL_LOCATION);
        epsilon = new Epsilon(900, 700);
        this.setContent();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(EPSILON_IMAGE,
                (int) epsilon.getX(),
                (int) epsilon.getY(),
                EPSILON_SIZE,
                EPSILON_SIZE,
                null);
    }

    @Override
    protected void setContent() {
        setExitButton();
        setStartButton();
        setSettingButton();
        setTutorialButton();
        setSkillTreeButton();
        setEpsilon();
    }

    private void setEpsilon() {
        moveEpsilonTimer = new Timer(10, e -> {
            if (epsilon.getX() < 0) XMoveLength *= -1;
            if (epsilon.getX() + 45 > 1000) XMoveLength *= -1;
            if (epsilon.getY() < 0) YMoveLength *= -1;
            if (epsilon.getY() + 45 > 800) YMoveLength *= -1;
            epsilon.moveX(XMoveLength);
           epsilon.moveY(YMoveLength);
           repaint();
        });
        moveEpsilonTimer.start();
    }



    private void setExitButton() {
        exitButton = new JButton("exit");
        exitButton.setBounds(40, 40, 80, 50);
        exitButton.setFont(new Font("", Font.BOLD, 20));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private void setStartButton() {
        startButton = new JButton("Start");
        startButton.setBounds(730, 130, 150, 80);
        startButton.setFont(new Font("", Font.BOLD, 20));
        startButton.addActionListener(e -> {
//            GameManger.getInstance().getCurrentPanel().setVisible(false);
            this.setVisible(false);
            GamePanel.getInstance();
        });
        add(startButton);
    }

    private void setSettingButton() {
        settingButton = new JButton("Settings");
        settingButton.setBounds(530, 280, 150, 80);
        settingButton.setFont(new Font("", Font.BOLD, 20));
        settingButton.addActionListener(e -> System.exit(0));
        add(settingButton);
    }

    private void setTutorialButton() {
        tutorialButton = new JButton("Tutorial");
        tutorialButton.setBounds(330, 430, 150, 80);
        tutorialButton.setFont(new Font("", Font.BOLD, 20));
        tutorialButton.addActionListener(e -> System.exit(0));
        add(tutorialButton);
    }

    private void setSkillTreeButton() {
        skillTreeButton = new JButton("Skill Tree");
        skillTreeButton.setBounds(130, 580, 150, 80);
        skillTreeButton.setFont(new Font("", Font.BOLD, 20));
        skillTreeButton.addActionListener(e -> System.exit(0));
        add(skillTreeButton);
    }
}