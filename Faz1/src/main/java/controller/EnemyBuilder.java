package controller;

import model.Enemy;
import model.Squarantine;
import model.Trigorath;
import view.panels.GamePanel;
import view.panels.SettingsPanel;

import javax.swing.*;

public class EnemyBuilder {
    private static Timer nextWave;
    private static Timer timer;
    public static int amount = 0;

    public static void build(int wave) {
        int level = SettingsPanel.getInstance().getLevel();
        nextWave = new Timer(1000, e->{
            if (Enemy.getEnemies().isEmpty()) {
                nextWave.stop();
                GamePanel.getInstance().nextWave();
            }
        });
        timer = new Timer(3000, e -> {
            System.out.println(amount);
            if (amount == 1) {
                amount = 0;
                timer.stop();
                nextWave.start();
            }
            GamePanel.getInstance().setShowWave(false);
            makeEnemyWave(level, wave);
            amount++;
        });
        timer.start();

    }

    private static void makeEnemyWave(int level, int wave) {
        int location = 1;
        int number = level * wave;
        for (int i = 0; i < number; i++) {
            if (location == 1) {
                new Squarantine(-100, -200);
                location++;
            } else if (location == 2) {
                new Trigorath(1800, 0);
                location++;
            } else if (location == 3) {
                new Squarantine(2000, 1100);
                location++;
            } else if (location == 4) {
                new Trigorath(100, 0);
                location = 1;
            }
        }
    }

    public static void stopBuild() {
        if (timer.isRunning()) timer.stop();
    }

    public static void continueBuild() {
        timer.start();
    }
}