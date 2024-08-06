package conection;

import database.Squads;
import database.Users;
import model.Squad;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Scanner scanner;
    private final PrintWriter printWriter;
    private boolean running;
    private final User user = new User();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.scanner = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setShutdownHook();
    }

    private void setShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (running) {
                    printWriter.println("disconnect");
                    printWriter.flush();
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void run() {
        super.run();
        running = true;
        waitForClientMessage();
    }

    private void waitForClientMessage() {
        while (running) {
            if (scanner.hasNextLine()) {
                String type = scanner.nextLine();
                processMessage(type);
            }
        }
    }

    private void processMessage(String type) {
        switch (type) {
            case "disconnect" -> {
                try {
                    socket.close();
                    running = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "user information" -> {
                user.setMacAddress(scanner.nextLine());
                user.setUsername(scanner.nextLine());
                user.setXP(scanner.nextInt());
                user.setSquad(Squads.getSquad(scanner.nextLine()));
                Users.addUser(user);
            }
            case "create squad" -> {
                String name = scanner.nextLine();
                if (Squads.getSquad(name) == null) {
                    Squad squad = new Squad(name, user);
                    user.setSquad(squad);
                    Squads.addSquad(squad);
                    sendMessage("squad created successfully");
                    sendMessage(name);
                } else sendMessage("this squad name already exist");

            }
            case "show squad list" -> sendSquadsForShow();
        }
    }

    private void sendSquadsForShow() {
        sendMessage("squads show");
        printWriter.println(Squads.getSquads().size());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Squad squad : Squads.getSquads()) {
            printWriter.println(squad.getName());
            printWriter.println(squad.getSize());
            printWriter.flush();
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }
}
