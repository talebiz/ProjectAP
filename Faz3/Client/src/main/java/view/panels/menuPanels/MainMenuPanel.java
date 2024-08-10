package view.panels.menuPanels;

import connection.controller.ClientManager;
import connection.model.User;
import controller.Collision;
import controller.EnemyBuilder;
import controller.GameManager;
import controller.Update;
import view.MyFrame;
import view.panels.MyPanel;
import view.panels.gamePanels.FirstPanel;
import view.panels.menuPanels.squadPanels.SquadOutPanel;
import view.panels.menuPanels.squadPanels.SquadInPanel;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public final class MainMenuPanel extends MyPanel {
    private static MainMenuPanel mainMenuPanel;

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
        setSquadModeButton();
        setReloadButton();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        g.drawString("status:", 650, 70);
        if (ClientManager.getInstance().isConnecting()) {
            g.setFont(new Font("", Font.PLAIN, 20));
            g.setColor(Color.GREEN);
            g.drawString("online", 720, 70);
        } else {
            g.setFont(new Font("", Font.PLAIN, 20));
            g.setColor(Color.RED);
            g.drawString("offline", 720, 70);
        }
        g.setColor(Color.WHITE);
        g.drawString("total XP: " + GameManager.getInstance().getTotalXP(), 250, 70);
    }

    private void setExitButton() {
        JButton exitButton = new JButton("exit");
        exitButton.setBounds(40, 40, 80, 50);
        exitButton.setFont(new Font("", Font.BOLD, 20));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private void setStartButton() {
        JButton startButton = new JButton("Start");
        startButton.setBounds(250, 220, 500, 70);
        startButton.setFont(new Font("", Font.BOLD, 20));
        startButton.addActionListener(e -> {
            setVisible(false);
            MyFrame.getInstance().remove(this);
            MyFrame.getInstance().revalidate();
            FirstPanel.getInstance().openGame();
            Collision.startCheckCollision();
            EnemyBuilder.build(1);
            Update.updatePaint();
        });
        add(startButton);
    }

    private void setSettingButton() {
        JButton settingButton = new JButton("Settings");
        settingButton.setBounds(250, 320, 500, 70);
        settingButton.setFont(new Font("", Font.BOLD, 20));
        settingButton.addActionListener(e -> {
            setVisible(false);
            MyFrame.getInstance().remove(this);
            MyFrame.getInstance().revalidate();
            MyFrame.getInstance().add(SettingsPanel.getInstance());
            SettingsPanel.getInstance().setVisible(true);
        });
        add(settingButton);
    }

    private void setTutorialButton() {
        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.setBounds(250, 420, 500, 70);
        tutorialButton.setFont(new Font("", Font.BOLD, 20));
        tutorialButton.addActionListener(e -> {
            String tutorial = """
                    use "WASD" to move epsilon
                    use "LMB" to shoot at enemies
                    use "P" to open store""";
            JOptionPane.showMessageDialog(this, tutorial, "tutorial", JOptionPane.PLAIN_MESSAGE);
        });
        add(tutorialButton);
    }

    private void setSkillTreeButton() {
        JButton skillTreeButton = new JButton("Skill Tree");
        skillTreeButton.setBounds(250, 520, 500, 70);
        skillTreeButton.setFont(new Font("", Font.BOLD, 20));
        skillTreeButton.addActionListener(e -> System.out.println("doesn't implement"));
        add(skillTreeButton);
    }

    private void setSquadModeButton() {
        JButton squadMode = new JButton("Multiplayer");
        squadMode.setBounds(250, 620, 500, 70);
        squadMode.setFont(new Font("", Font.BOLD, 20));
        squadMode.addActionListener(e -> {
            if (ClientManager.getInstance().isConnecting()) {
                setVisible(false);
                MyFrame.getInstance().remove(this);
                MyFrame.getInstance().revalidate();
                if (User.getInstance().getSquadName().isEmpty()) {
                MyFrame.getInstance().add(SquadOutPanel.getInstance());
                SquadOutPanel.getInstance().setVisible(true);
                } else {
                    ClientManager.getInstance().sendMessage("show user list");
                    MyFrame.getInstance().add(SquadInPanel.getInstance());
                    SquadInPanel.getInstance().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(MyFrame.getInstance(),
                        "Connection refused!",
                        "Connection Status",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(squadMode);
    }

    private void setReloadButton() {
        JButton reloadButton = new JButton("reload");
        reloadButton.setBounds(860, 40, 90, 50);
        reloadButton.setFont(new Font("", Font.BOLD, 20));
        reloadButton.addActionListener(e -> ClientManager.getInstance().makeSocket());
        add(reloadButton);
    }
}