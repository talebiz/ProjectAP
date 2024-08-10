package model.conection;

import java.util.ArrayList;

public class User {
    private String username;
    private String macAddress;
    private int XP;
    private String squad = "";
    private Status status;
    private final ArrayList<Message> messages = new ArrayList<>();


    public User() {
    }

    public User(String macAddress, String username, int XP) {
        this.macAddress = macAddress;
        this.username = username;
        this.XP = XP;
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

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(String receiver) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getReceiver().equals(receiver)) {
                messages.remove(i--);
            }
        }
    }
}
