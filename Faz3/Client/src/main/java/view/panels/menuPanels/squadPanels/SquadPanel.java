package view.panels.menuPanels.squadPanels;

import connection.controller.ClientManager;
import connection.model.User;
import view.MyFrame;
import view.panels.MyPanel;
import view.panels.menuPanels.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public abstract class SquadPanel extends MyPanel {

    public SquadPanel() {
        super();
        setSize(NORMAL_PANEL_SIZE);
        setLocation(NORMAL_PANEL_LOCATION);
        setContent();
    }

    @Override
    protected void setContent() {
        setBackButton();
        setRenameButton();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("username: " + User.getInstance().getUsername(), 600, 70);
    }

    private void setBackButton() {
        JButton backButton = new JButton("back");
        backButton.setBounds(40, 40, 80, 50);
        backButton.setFont(new Font("", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            setVisible(false);
            MyFrame.getInstance().remove(this);
            MyFrame.getInstance().revalidate();
            MyFrame.getInstance().add(MainMenuPanel.getInstance());
            MainMenuPanel.getInstance().setVisible(true);
        });
        add(backButton);
    }

    private void setRenameButton() {
        JButton renameButton = new JButton("rename");
        renameButton.setBounds(840, 40, 110, 50);
        renameButton.setFont(new Font("", Font.BOLD, 20));
        renameButton.addActionListener(e -> {
                    String newName = JOptionPane.showInputDialog(
                            MyFrame.getInstance(),
                            "new username:",
                            "rename",
                            JOptionPane.PLAIN_MESSAGE);
                    if (newName != null && !newName.isBlank()) {
                        User.getInstance().setUsername(newName);
                        ClientManager.getInstance().sendUserInformation();
                        repaint();
                    }
                }
        );
        add(renameButton);
    }
}
