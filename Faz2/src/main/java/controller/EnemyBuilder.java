package controller;

import model.Epsilon;
import model.enemies.*;
import view.panels.FirstPanel;
import view.panels.SettingsPanel;

import javax.swing.*;

public class EnemyBuilder {
    private static Timer nextWave;
    private static Timer timer;
    public static int amount = 0;

    public static void build(int wave) {
        int level = SettingsPanel.getInstance().getLevel();
        nextWave = new Timer(1000, e -> {
            if (EntityData.getEnemies().isEmpty()) {
                nextWave.stop();
                FirstPanel.getInstance().nextWave();
            }
        });
        timer = new Timer(3000, e -> {
            if (amount == 1) {
                amount = 0;
                timer.stop();
                nextWave.start();
            }
            FirstPanel.getInstance().setShowWave(false);
            makeEnemyWave(level, wave);
            amount++;
        });
//        timer.start();
//        new Omenoct(2000, -200);
//        new Squarantine(-100, 900);
//        new Trigorath(-100, 200);
//        new Necropick(500, 600);
//        new Archmire(1500, 600);
//        new MiniArchmire(500, 400);
//        new Timer(1000, e -> {
//            new Wyrm(Epsilon.getInstance().getLocalPanel().getX()+ Epsilon.getInstance().getLocalPanel().getWidth()+60,600);
//        }) {{
//            setRepeats(false);
//            start();
//        }};
        new Wyrm(1300, 500);
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