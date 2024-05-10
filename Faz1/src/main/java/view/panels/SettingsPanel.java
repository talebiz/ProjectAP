package view.panels;

import controller.Util.Constant;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.NORMAL_PANEL_LOCATION;
import static controller.Util.Constant.NORMAL_PANEL_SIZE;

public class SettingsPanel extends MyPanel {
    private static SettingsPanel settingsPanel;
    private JSlider levelSlider, soundSlider, sensitivitySlider;
    private JLabel levelLabel, soundLabel, sensitivityLabel;
    private JButton backButton;

    private SettingsPanel() {
        super();
        this.setBackground(Color.DARK_GRAY);
        this.setSize(NORMAL_PANEL_SIZE);
        this.setLocation(NORMAL_PANEL_LOCATION);
        setContent();
    }

    public static SettingsPanel getInstance() {
        if (settingsPanel == null) settingsPanel = new SettingsPanel();
        return settingsPanel;
    }

    @Override
    protected void setContent() {
        setLevel();
        setSound();
        setSensitivity();
        setBackButton();
    }

    private void setLevel() {
        levelLabel = new JLabel();
        levelLabel.setBounds(200, 120, 300, 150);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("", Font.BOLD, 25));
        levelLabel.setText("Level");
        this.add(levelLabel);
        levelSlider = new JSlider(1, 3, 1);
        levelSlider.setBounds(350, 190, 400, 20);
        this.add(levelSlider);
    }

    private void setSound() {
        soundLabel = new JLabel();
        soundLabel.setBounds(200, 320, 300, 150);
        soundLabel.setForeground(Color.WHITE);
        soundLabel.setFont(new Font("", Font.BOLD, 25));
        soundLabel.setText("Sound");
        this.add(soundLabel);
        soundSlider = new JSlider(0, 100, 50);
        soundSlider.setBackground(Color.WHITE);
        soundSlider.setBounds(350, 390, 400, 20);
        this.add(soundSlider);
    }

    private void setSensitivity() {
        sensitivityLabel = new JLabel();
        sensitivityLabel.setBounds(200, 520, 300, 150);
        sensitivityLabel.setForeground(Color.WHITE);
        sensitivityLabel.setFont(new Font("", Font.BOLD, 25));
        sensitivityLabel.setText("Sensitivity");
        this.add(sensitivityLabel);
        sensitivitySlider = new JSlider(5, 15, 10);
        sensitivitySlider.setBounds(350, 590, 400, 20);
        this.add(sensitivitySlider);
    }

    private void setBackButton() {
        backButton = new JButton("back");
        backButton.setBounds(40, 40, 80, 50);
        backButton.setFont(new Font("", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            Constant.EPSILON_SPEED = sensitivitySlider.getValue();
            this.setVisible(false);
            MainMenuPanel.getInstance().setVisible(true);
        });
        add(backButton);
    }

    public int getLevel() {
        return levelSlider.getValue();
    }
}
