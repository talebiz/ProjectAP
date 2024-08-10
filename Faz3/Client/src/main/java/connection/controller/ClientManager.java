package connection.controller;

import controller.GameManager;
import connection.model.User;
import view.MyFrame;
import view.panels.menuPanels.MainMenuPanel;
import view.panels.menuPanels.squadPanels.SquadOutPanel;
import view.panels.menuPanels.squadPanels.SquadInPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientManager extends Thread {
    private static ClientManager clientManager;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private boolean isConnecting;

    private ClientManager() {
        setShutdownHook();
    }

    private void setShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (isConnecting) {
                    printWriter.println("disconnect");
                    printWriter.flush();
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static ClientManager getInstance() {
        if (clientManager == null) clientManager = new ClientManager();
        return clientManager;
    }

    public void makeSocket() {
        try {
            socket = new Socket("localhost", 9000);
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
            isConnecting = true;
            sendUserInformation();
            MainMenuPanel.getInstance().repaint();
            showStatus("connected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            isConnecting = false;
            MainMenuPanel.getInstance().repaint();
            showStatus("disconnected");
        }
    }

    public void sendUserInformation() {
        try {
            printWriter.println("user information");
            printWriter.println(User.getInstance().getMacAddress());
            printWriter.println(User.getInstance().getUsername());
            printWriter.println(GameManager.getInstance().getTotalXP());
            printWriter.flush();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendCreateSquad(String name) {
        printWriter.println("create squad");
        printWriter.println(name);
        printWriter.flush();
    }

    public void sendJoinRequest(String name) {
        printWriter.println("join request");
        printWriter.println(name);
        printWriter.flush();
    }

    public void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }

    @Override
    public void run() {
        super.run();
        waitForServerMessage();
    }

    private void waitForServerMessage() {
        //noinspection InfiniteLoopStatement
        while (true) {
            if (isConnecting) {
                if (scanner.hasNextLine()) {
                    try {
                        String type = scanner.nextLine();
                        processMessage(type);
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private void processMessage(String type) {
        switch (type) {
            case "disconnect" -> disconnectProcess();
            case "user information" -> updateUserInformation();
            case "squad created successfully" -> showCreateDone(type);
            case "this squad name already exist" -> showDuplicateSquad(type);
            case "squads show" -> showSquads();
            case "users show" -> showUsers();
            case "messages show" -> showMessages();
            case "result of remove" -> removeResultProcess();
            case "ownerResult" -> ownerResultProcess();
        }
    }

    private void disconnectProcess() {
        try {
            socket.close();
            isConnecting = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainMenuPanel.getInstance().repaint();
    }

    private void updateUserInformation() {
        GameManager.getInstance().setTotalXP(scanner.nextInt());
        scanner.nextLine();
        User.getInstance().setSquadName(scanner.nextLine());
    }

    private void showCreateDone(String message) {
        JOptionPane.showMessageDialog(
                MyFrame.getInstance(),
                message,
                "result",
                JOptionPane.PLAIN_MESSAGE);
        User.getInstance().setSquadName(scanner.nextLine());
        GameManager.getInstance().addTotalXP(-100);
        SquadOutPanel.getInstance().setVisible(false);
        MyFrame.getInstance().remove(SquadOutPanel.getInstance());
        MyFrame.getInstance().revalidate();
        MyFrame.getInstance().add(MainMenuPanel.getInstance());
        MainMenuPanel.getInstance().setVisible(true);
    }

    private static void showDuplicateSquad(String message) {
        JOptionPane.showMessageDialog(
                MyFrame.getInstance(),
                message,
                "result",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showSquads() {
        int numbers = scanner.nextInt();
        scanner.nextLine();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < numbers; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            String name = scanner.nextLine();
            int size = scanner.nextInt();
            scanner.nextLine();
            JButton button = new JButton("join");
            button.addActionListener(e -> {
                sendJoinRequest(name);
                JOptionPane.showMessageDialog(
                        MyFrame.getInstance(),
                        "join request sent to squad owner",
                        "result",
                        JOptionPane.PLAIN_MESSAGE);
            });
            rowPanel.add(button);
            String text = "name: " + name + "\t".repeat(Math.max(0, 40 - name.length())) +
                    "members number: " + size;
            rowPanel.add(new JLabel(text));
            panel.add(rowPanel);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showConfirmDialog(
                MyFrame.getInstance(),
                scrollPane,
                "Squads List",
                JOptionPane.OK_CANCEL_OPTION);
    }

    private void showUsers() {
        int numbers = scanner.nextInt();
        scanner.nextLine();
        String[][] usersList = new String[numbers][3];
        for (int i = 0; i < numbers; i++) {
            usersList[i][0] = scanner.nextLine();
            usersList[i][1] = scanner.nextLine();
            usersList[i][2] = scanner.nextLine();
        }
        SquadInPanel.getInstance().setUsersList(usersList);
    }

    private void showMessages() {
        int numbers = scanner.nextInt();
        scanner.nextLine();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < numbers; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            String username = scanner.nextLine();
            String message = scanner.nextLine();
            JButton button = new JButton("accept");
            button.addActionListener(acceptActionListener(username));
            rowPanel.add(button);
            String text = "username: " + username +
                    "\t".repeat(Math.max(0, 30 - username.length())) +
                    "message: " + message;
            rowPanel.add(new JLabel(text));
            panel.add(rowPanel);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showConfirmDialog(
                MyFrame.getInstance(),
                scrollPane,
                "Message List",
                JOptionPane.OK_CANCEL_OPTION);
    }

    private ActionListener acceptActionListener(String username) {
        return e -> {
            sendAcceptJoin(username);
            deleteJOptionPane();
            SquadInPanel.getInstance().setVisible(false);
            MyFrame.getInstance().remove(SquadInPanel.getInstance());
            MyFrame.getInstance().revalidate();
            MyFrame.getInstance().add(MainMenuPanel.getInstance());
            MainMenuPanel.getInstance().setVisible(true);
            JOptionPane.showMessageDialog(
                    MyFrame.getInstance(),
                    "new member has joined",
                    "result",
                    JOptionPane.PLAIN_MESSAGE);
        };
    }

    private void sendAcceptJoin(String username) {
        sendMessage("accept join");
        printWriter.println(username);
        printWriter.flush();
    }

    private static void deleteJOptionPane() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JDialog dialog) {
                if (dialog.getContentPane().getComponentCount() == 1 &&
                        dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
                    dialog.dispose();
                }
            }
        }
    }

    private void removeResultProcess() {
        if (scanner.nextBoolean()) {
            scanner.nextLine();
            String username = scanner.nextLine();
            int answer = JOptionPane.showConfirmDialog(
                    MyFrame.getInstance(),
                    "Are you sur want to remove " + username,
                    "removing",
                    JOptionPane.YES_NO_OPTION);
            if (answer == 0) {
                sendMessage("removed correctly\n" + username);
                SquadInPanel.getInstance().setVisible(false);
                MyFrame.getInstance().remove(SquadInPanel.getInstance());
                MyFrame.getInstance().revalidate();
                MyFrame.getInstance().add(MainMenuPanel.getInstance());
                MainMenuPanel.getInstance().setVisible(true);
            }
            showRemoveDone(username);
        } else {
            JOptionPane.showMessageDialog(
                    MyFrame.getInstance(),
                    "unknown username!",
                    "removing",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ownerResultProcess() {
        if (scanner.nextBoolean()) {
            scanner.nextLine();
            SquadInPanel.getInstance().setRemoveButton();
        }
    }

    private void showRemoveDone(String username) {
        JOptionPane.showMessageDialog(
                MyFrame.getInstance(),
                username + " was removed",
                "result",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showStatus(String status) {
        switch (status) {
            case "connected" -> JOptionPane.showMessageDialog(
                    MyFrame.getInstance(),
                    "Connected Successfully",
                    "Connection Status",
                    JOptionPane.PLAIN_MESSAGE);
            case "disconnected" -> {
                String[] options = {"try again", "cancel"};
                int ans = JOptionPane.showOptionDialog(MyFrame.getInstance(),
                        "Connection refused!",
                        "Connection Status",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        null);
                if (ans == 0) makeSocket();
            }
        }
    }

    public boolean isConnecting() {
        return isConnecting;
    }
}
