package model;

public class User {
    private final String username;
    private final String macAddress;

    public User(String username, String macAddress) {
        this.username = username;
        this.macAddress = macAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getMacAddress() {
        return macAddress;
    }
}