package conection;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.Squads;
import database.Users;
import model.Squad;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {
    private final ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ServerManager() {
//        readUsers();
        readSquads();
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        setShutdownHook();
        waitForClient();
    }

    private void readUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; true; i++) {
            File file = new File("Server/src/main/resources/users/user" + i + ".json");
            if (file.exists()) {
                try {
                    Users.addUser(objectMapper.readValue(file, User.class));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }
        }
    }

    private void readSquads() {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; true; i++) {
            File file = new File("Server/src/main/resources/squads/squad" + i + ".json");
            if (file.exists()) {
                try {
                    Squads.addSquad(objectMapper.readValue(file, Squad.class));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }
        }
    }

    private void setShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            writeUsers();
            writeSquads();
        }));
    }

    private static void writeUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < Users.getUsers().size(); i++) {
            File file = new File("Server/src/main/resources/users/user" + i + ".json");
            try {
                objectMapper.writeValue(file, Users.getUser(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void writeSquads() {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < Squads.getSquads().size(); i++) {
            File file = new File("Server/src/main/resources/squads/squad" + i + ".json");
            try {
                objectMapper.writeValue(file, Squads.getSquad(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void waitForClient() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);
                clientHandler.start();
                Thread.sleep(300);
                deleteAdditionalClientHandler();
                System.out.println(clientHandlers.size() + "th client connected");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteAdditionalClientHandler() {
        for (int i = 0; i < clientHandlers.size(); i++)
            if (!clientHandlers.get(i).isAlive()) {
                clientHandlers.get(i).interrupt();
                clientHandlers.remove(i--);
            }
    }
}