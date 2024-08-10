package database;

import model.conection.User;

import java.util.ArrayList;

public class Users {
    private static final ArrayList<User> users = new ArrayList<>();

    public static User addUser(User user) {
        for (User user0 : users) {
            //TODO CHANGE ALL NAME COMPARISON TO MAC ADDRESS
            if (user0.getUsername().equals(user.getUsername())) return user0;
        }
        users.add(user);
        return user;
    }

    public static User getUser(int index) {
        return users.get(index);
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
