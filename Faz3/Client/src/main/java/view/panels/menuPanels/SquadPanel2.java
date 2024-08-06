package view.panels.menuPanels;

import connection.ClientManager;
import controller.GameManager;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;

public class SquadPanel2 extends SquadPanel {
    private static SquadPanel2 squadPanel2;

    private SquadPanel2() {
        super();
    }

    public static SquadPanel2 getInstance() {
        if (squadPanel2 == null) squadPanel2 = new SquadPanel2();
        return squadPanel2;
    }

    @Override
    protected void setContent() {
        super.setContent();
        setCreateButton();
        setSquadListButton();
    }

    private void setCreateButton() {
        JButton createButton = new JButton("Create Squad");
        createButton.setBounds(350, 350, 300, 70);
        createButton.setFont(new Font("", Font.BOLD, 20));
        createButton.addActionListener(e -> {
            if (GameManager.getInstance().getTotalXP() < 100) {
                JOptionPane.showMessageDialog(MyFrame.getInstance(),
                        "you need 100 XP for creating",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String name = JOptionPane.showInputDialog(MyFrame.getInstance(),
                        "100 XP\nenter your squad name:",
                        "create squad",
                        JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.isBlank()) ClientManager.getInstance().sendCreateSquad(name);
            }
        });
        add(createButton);
    }

    private void setSquadListButton() {
        JButton squadListButton = new JButton("Squads List");
        squadListButton.setBounds(350, 430, 300, 70);
        squadListButton.setFont(new Font("", Font.BOLD, 20));
        squadListButton.addActionListener(e -> ClientManager.getInstance().sendMessage("show squad list"));
        add(squadListButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
