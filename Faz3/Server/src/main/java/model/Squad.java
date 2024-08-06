package model;

import java.util.ArrayList;

public class Squad {
    private String name;
    private User owner;
    private int vault;
    private ArrayList<User> members = new ArrayList<>();

    public Squad() {}

    public Squad(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return members.size();
    }
}
