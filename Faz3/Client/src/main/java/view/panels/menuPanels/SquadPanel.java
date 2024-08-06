package view.panels.menuPanels;

import connection.ClientManager;
import connection.User;
import view.MyFrame;
import view.panels.MyPanel;

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
        g.drawString("username: " + User.getInstance().getUsername(), 550, 70);
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
                    User.getInstance().setUsername(newName);
                    repaint();
                    ClientManager.getInstance().sendUserInformation();
                }
        );
        add(renameButton);
    }
}
