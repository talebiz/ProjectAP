package view.panels;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public class MainMenuPanel extends MyPanel {
    private static MainMenuPanel mainMenuPanel;
    JButton exitButton, startButton, settingButton, tutorialButton, skillTreeButton;

    private MainMenuPanel() {
        super();
        this.setSize(NORMAL_PANEL_SIZE);
        this.setLocation(NORMAL_PANEL_LOCATION);
        this.setContent();
    }

    public static MainMenuPanel getInstance() {
        if (mainMenuPanel == null) mainMenuPanel = new MainMenuPanel();
        return mainMenuPanel;
    }

    @Override
    protected void setContent() {
        setExitButton();
        setStartButton();
        setSettingButton();
        setTutorialButton();
        setSkillTreeButton();
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
            this.setVisible(false);
            GamePanel.getInstance().makeNew();
        });
        add(startButton);
    }

    private void setSettingButton() {
        settingButton = new JButton("Settings");
        settingButton.setBounds(530, 280, 150, 80);
        settingButton.setFont(new Font("", Font.BOLD, 20));
        settingButton.addActionListener(e -> {
            this.setVisible(false);
            SettingsPanel.getInstance().setVisible(true);
        });
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