package model.conection;

import database.Users;

import java.util.ArrayList;

public class Squad {
    private String name;
    private String owner;
    private int vault;
    private final ArrayList<User> members = new ArrayList<>();

    public Squad() {
    }

    public Squad(String name, String owner) {
        this.name = name;
        this.owner = owner;
        members.add(Users.getUser(owner));
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public String getOwner() {
        return owner;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }
}
