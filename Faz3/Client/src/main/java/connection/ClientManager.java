package connection;

import controller.GameManager;
import view.MyFrame;
import view.panels.menuPanels.MainMenuPanel;
import view.panels.menuPanels.SquadPanel1;

import javax.swing.*;
import java.awt.*;
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
        startCheckConnection();
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
            showMessage("connected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            isConnecting = false;
            MainMenuPanel.getInstance().repaint();
            showMessage("disconnected");
        }
    }

    public void sendUserInformation() {
        printWriter.println("user information");
        printWriter.println(User.getInstance().getMacAddress());
        printWriter.println(User.getInstance().getUsername());
        printWriter.println(GameManager.getInstance().getTotalXP());
        printWriter.println(User.getInstance().getSquadName());
        printWriter.flush();
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

    private void startCheckConnection() {
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                if (isConnecting && socket.isClosed()) isConnecting = false;
//            }
//        }).start();
    }

    @Override
    public void run() {
        super.run();
        waitForServerMessage();
    }

    private void waitForServerMessage() {
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
            case "disconnect" -> {
                try {
                    socket.close();
                    isConnecting = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MainMenuPanel.getInstance().repaint();
            }
            case "squad created successfully" -> {
                JOptionPane.showMessageDialog(
                        MyFrame.getInstance(),
                        type,
                        "result",
                        JOptionPane.PLAIN_MESSAGE);
                User.getInstance().setSquadName(scanner.nextLine());
                GameManager.getInstance().addTotalXP(-100);
                SquadPanel1.getInstance().setVisible(false);
                MyFrame.getInstance().remove(SquadPanel1.getInstance());
                MyFrame.getInstance().revalidate();
                MyFrame.getInstance().add(MainMenuPanel.getInstance());
                MainMenuPanel.getInstance().setVisible(true);
            }
            case "this squad name already exist" -> JOptionPane.showMessageDialog(
                    MyFrame.getInstance(),
                    type,
                    "result",
                    JOptionPane.ERROR_MESSAGE);
            case "squads show" -> {
                showSquads();
            }
        }
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

    private void showMessage(String status) {
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
