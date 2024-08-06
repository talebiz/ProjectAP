package database;

import model.User;

import java.util.ArrayList;

public class Users {
    private static final ArrayList<User> users = new ArrayList<>();

//    static {
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(users.size());
//                if (users.size() == 1) {
//                    System.out.println(users.getFirst().getUsername());
//                    System.out.println(users.getFirst().getXP());
//                    System.out.println(users.getFirst().getSquad());
//                    if (users.getFirst().getSquad() != null)
//                        System.out.println(users.getFirst().getSquad().getName());
//                }
//            }
//        }).start();
//    }

    public static void addUser(User user) {
        for (User value : users) {
            if (value.getMacAddress().equals(user.getMacAddress())) return;
        }
        users.add(user);
    }

    public static User getUser(int index) {
        return users.get(index);
    }

    public static User getUser(String macAddress) {
        for (User user : users) {
            if (user.getMacAddress().equals(macAddress)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
