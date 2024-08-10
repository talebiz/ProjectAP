package database;

import model.conection.Squad;

import java.util.ArrayList;

public class Squads {
    private static final ArrayList<Squad> squads = new ArrayList<>();

    public static void addSquad(Squad squad) {
        for (Squad value : squads) {
            if (value.getName().equals(squad.getName())) return;
        }
        squads.add(squad);
    }

    public static void remove(Squad squad) {
        squads.remove(squad);
    }

    public static Squad getSquad(int index) {
        return squads.get(index);
    }

    public static Squad getSquad(String name) {
        for (Squad squad : squads) {
            if (squad.getName().equals(name)) {
                return squad;
            }
        }
        return null;
    }

    public static ArrayList<Squad> getSquads() {
        return squads;
    }
}
