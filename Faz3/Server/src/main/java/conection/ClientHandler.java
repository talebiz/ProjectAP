package conection;

import database.Squads;
import database.Users;
import model.conection.Message;
import model.conection.Squad;
import model.conection.Status;
import model.conection.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Scanner scanner;
    private final PrintWriter printWriter;
    private boolean running;
    private User user = new User();

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

    private void sendUserInformation() {
        printWriter.println("user information");
        printWriter.println(user.getXP());
        printWriter.println(user.getSquad());
        printWriter.flush();
    }

    private void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
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
            case "disconnect" -> disconnectProcess();
            case "user information" -> updateUserInformation();
            case "create squad" -> createSquadProcess();
            case "show squad list" -> sendSquadsForShow();
            case "show user list" -> sendUsersForShow();
            case "show message list" -> sendMessagesForShow();
            case "join request" -> joinRequestProcess();
            case "left squad" -> leftProcess();
            case "accept join" -> acceptJoinProcess();
            case "remove" -> removeProcess();
            case "removed correctly" -> removeDone();
            case "check if owner" -> checkIfOwner();
        }
    }

    private void disconnectProcess() {
        try {
            user.setStatus(Status.OFFLINE);
            socket.close();
            running = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateUserInformation() {
        user.setMacAddress(scanner.nextLine());
        user.setUsername(scanner.nextLine());
        user.setXP(scanner.nextInt());
        scanner.nextLine();
        user = Users.addUser(user);
        user.setStatus(Status.ONLINE);
        sendUserInformation();
    }

    private void createSquadProcess() {
        String name = scanner.nextLine();
        if (Squads.getSquad(name) == null) {
            Squad squad = new Squad(name, user.getUsername());
            user.setSquad(name);
            Squads.addSquad(squad);
            sendMessage("squad created successfully");
            sendMessage(name);
        } else sendMessage("this squad name already exist");
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
            printWriter.println(squad.getMembers().size());
            printWriter.flush();
        }
    }

    private void sendUsersForShow() {
        sendMessage("users show");
        printWriter.println(Squads.getSquad(user.getSquad()).getMembers().size());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (User user0 : Squads.getSquad(user.getSquad()).getMembers()) {
            printWriter.println(user0.getUsername());
            printWriter.println(user0.getXP());
            printWriter.println(user0.getStatus());
            printWriter.flush();
        }
    }

    private void sendMessagesForShow() {
        sendMessage("messages show");
        printWriter.println(user.getMessages().size());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Message message : user.getMessages()) {
            printWriter.println(message.getSender());
            printWriter.println(message.getMessage());
            printWriter.flush();
        }
    }

    private void joinRequestProcess() {
        User receiver = Users.getUser(Squads.getSquad(scanner.nextLine()).getOwner());
        receiver.addMessage(new Message("join request", user.getUsername(), receiver.getUsername()));
    }

    private void leftProcess() {
        Squad squad = Squads.getSquad(user.getSquad());
        if (squad.getOwner().equals(user.getUsername())) {
            ArrayList<User> members = squad.getMembers();
            for (int i = 0; i < members.size(); i++) {
                User user0 = members.get(i);
                user0.setSquad("");
                ServerManager.getClientHandler(user0.getUsername()).sendUserInformation();
                squad.removeMember(user0);
                i--;
            }
            Squads.remove(squad);
            return;
        }
        user.setSquad("");
        sendUserInformation();
        squad.removeMember(user);
    }

    private void acceptJoinProcess() {
        Squad squad = Squads.getSquad(user.getSquad());
        User newUser = Users.getUser(scanner.nextLine());
        System.out.println("accept " + newUser.getUsername());
        newUser.setSquad(squad.getName());
        squad.addMember(newUser);
        user.removeMessage(user.getUsername());
        ServerManager.getClientHandler(newUser.getUsername()).sendUserInformation();
    }

    private void removeProcess() {
        User removedUser = Users.getUser(scanner.nextLine());
        sendMessage("result of remove");
        if (removedUser != null && removedUser.getSquad().equals(user.getSquad())) {
            sendMessage("true");
            sendMessage(removedUser.getUsername());
        } else {
            sendMessage("false");
        }
    }

    private void removeDone() {
        User removesUser = Users.getUser(scanner.nextLine());
        removesUser.setSquad("");
        Squads.getSquad(user.getSquad()).removeMember(removesUser);
        ServerManager.getClientHandler(removesUser.getUsername()).sendUserInformation();
    }

    private void checkIfOwner() {
        sendMessage("ownerResult");
        if (Squads.getSquad(user.getSquad()).getOwner().equals(user.getUsername())) {
            sendMessage("true");
        } else {
            sendMessage("false");
        }
    }

    public User getUser() {
        return user;
    }
}
