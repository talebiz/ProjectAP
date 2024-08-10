package connection.model;

import controller.GameManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class User {
    private  static User user;
    private String username;
    private String macAddress;
    private String squadName = "";

    private User() {
        setUser();
        setShutdownHook();
    }

    private void setUser() {
        if (isNewUser()) {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                StringBuilder sb = new StringBuilder();
                for (byte b : mac) {
                    sb.append(String.format("%02X%s", b, ""));
                }
                System.out.println("MAC address: " + sb);
                macAddress = sb.toString();
                username = macAddress;
            } catch (UnknownHostException | SocketException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static User getInstance() {
        if (user == null) user = new User();
        return user;
    }

    private boolean isNewUser() {
        try {
            File file = new File("Client/src/main/resources/user.txt");
            Scanner sc = new Scanner(file);
            if (!sc.nextBoolean()) {
                sc.close();
                return true;
            }
            readFile(sc);
            return false;
        } catch (FileNotFoundException e) {
            return true;
        }
    }

    private void readFile(Scanner sc) {
        sc.nextLine();
        sc.next();
        macAddress = sc.next();
        sc.nextLine();
        sc.next();
        username = sc.next();
        sc.nextLine();
        sc.next();
        GameManager.getInstance().addTotalXP(sc.nextInt());
        sc.nextLine();
        sc.next();
        if (sc.hasNext()) squadName = sc.next();
        sc.close();
    }

    private void setShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                File file = new File("Client/src/main/resources/user.txt");
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(true);
                printWriter.println("macAddress: " + macAddress);
                printWriter.println("username: " + username);
                printWriter.println("XP: " + GameManager.getInstance().getTotalXP());
                printWriter.println("squad: " + User.getInstance().getSquadName());
                printWriter.flush();
                printWriter.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }
}
