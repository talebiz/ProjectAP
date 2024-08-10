package view.panels.menuPanels.squadPanels;

import connection.controller.ClientManager;
import connection.model.User;
import view.MyFrame;
import view.panels.menuPanels.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

public class SquadInPanel extends SquadPanel {
    private static SquadInPanel squadInPanel;
    private JScrollPane scrollPane;

    private SquadInPanel() {
        super();
    }

    public static SquadInPanel getInstance() {
        if (squadInPanel == null) squadInPanel = new SquadInPanel();
        return squadInPanel;
    }

    @Override
    protected void setContent() {
        super.setContent();
        setLeftButton();
        setMessagesButton();
        ClientManager.getInstance().sendMessage("check if owner");
    }

    public void setRemoveButton() {
        JTextField textField = new JTextField();
        textField.setForeground(Color.RED);
        textField.setFont(new Font("", Font.PLAIN, 18));
        textField.setBounds(180, 725, 200, 50);
        JButton removeButton = new JButton("remove");
        removeButton.setBounds(50, 725, 120, 50);
        removeButton.setFont(new Font("", Font.BOLD, 20));
        removeButton.addActionListener(e ->
                ClientManager.getInstance().sendMessage("remove\n" + textField.getText())
        );
        add(textField);
        add(removeButton);
    }

    private void setLeftButton() {
        JButton leftButton = new JButton("left");
        leftButton.setBounds(450, 40, 80, 50);
        leftButton.setFont(new Font("", Font.BOLD, 20));
        leftButton.addActionListener(e -> {
                    int answer = JOptionPane.showConfirmDialog(
                            MyFrame.getInstance(),
                            "Are you sure want to leave?\n" +
                                    "(if you are the owner, the squad will be deleted)",
                            "left",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == 0) {
                        ClientManager.getInstance().sendMessage("left squad");
                        setVisible(false);
                        MyFrame.getInstance().remove(this);
                        MyFrame.getInstance().revalidate();
                        MyFrame.getInstance().add(MainMenuPanel.getInstance());
                        MainMenuPanel.getInstance().setVisible(true);
                    }
                }
        );
        add(leftButton);
    }

    public void setUsersList(String[][] usersList) {
        if (scrollPane != null) remove(scrollPane);
        String[] title = {"username", "XP", "status"};
        JTable table = new JTable(usersList, title);
        table.setBackground(Color.WHITE);
        table.setFont(new Font("", Font.PLAIN, 18));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 900, 550);
        add(scrollPane);
    }

    private void setMessagesButton() {
        JButton messagesButton = new JButton("messages");
        messagesButton.setBounds(400, 725, 200, 50);
        messagesButton.setFont(new Font("", Font.BOLD, 20));
        messagesButton.addActionListener(e ->
                ClientManager.getInstance().sendMessage("show message list")
        );
        add(messagesButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("squad: " + User.getInstance().getSquadName(), 250, 70);
    }
}
