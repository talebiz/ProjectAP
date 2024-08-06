package model;

public class User {
    private  String username;
    private  String macAddress;
    private int XP;
    private Squad squad;

    public User(){}

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

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
