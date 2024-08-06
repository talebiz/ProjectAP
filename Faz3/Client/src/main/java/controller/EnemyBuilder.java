package controller;

import model.enemies.Enemy;
import model.enemies.miniBoss.Barricados;
import model.enemies.miniBoss.BlackOrb;
import model.enemies.normal.*;
import view.panels.gamePanels.FirstPanel;
import view.panels.menuPanels.SettingsPanel;

import javax.swing.*;

public class EnemyBuilder {
    private static Timer nextWave;
    private static Timer timer;
    public static int amount = 0;

    public static void build(int wave) {
        int level = SettingsPanel.getInstance().getLevel();
        nextWave = new Timer(1000, e -> {
            if (EntityData.getEnemies().isEmpty()) {
                amount = 0;
                nextWave.stop();
                FirstPanel.getInstance().nextWave();
            }
        });
//        timer = new Timer(3000, e -> {
//            if (amount == 1) {
//                amount = 0;
//                timer.stop();
//                nextWave.start();
//            }
//            FirstPanel.getInstance().setShowWave(false);
//            makeEnemyWave(level, wave);
//            amount++;
//        });
//        timer.start();

        timer = new Timer(17000, e -> {
            FirstPanel.getInstance().setShowWave(false);
            makeEnemyWave(wave);
        });
        timer.setInitialDelay(5000);
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

    private static void makeEnemyWave(int wave) {
        switch (wave) {
            case 1 -> {
                if (amount >= 3) {
                    timer.stop();
                    nextWave.start();
                }
                if (amount == 0) new Omenoct(4000, 1000);
                if (amount == 5) new Necropick(700, 700);
                new Squarantine(-500, -200);
                new Trigorath(1800, 0);
            }
            case 2 -> {
                if (amount >= 10) {
                    timer.stop();
                    nextWave.start();
                }
                if (amount == 0) new Wyrm(4000, 1000);
                if (amount == 5) new Archmire(-400, -1000);
                new Squarantine(-500, -200);
                new Trigorath(1800, 0);
            }
            case 3 -> {
                if (amount >= 10) {
                    timer.stop();
                    nextWave.start();
                }
                if (amount == 0) new Wyrm(4000, 1000);
                if (amount == 5) new Omenoct(4000, 1000);
                if (amount == 7) new Barricados(1350, 200);
                new Squarantine(-500, -200);
                new Trigorath(1800, 0);
            }
            case 4 -> {
                if (amount >= 15) {
                    timer.stop();
                    nextWave.start();
                }
                if (amount == 0) new Necropick(4000, 1000);
                if (amount == 5) new Archmire(-400, 1000);
                if (amount == 6) new BlackOrb(800, 500);
                new Squarantine(-500, -200);
                new Trigorath(1800, 0);
            }
            case 5 -> {
                if (amount >= 15) {
                    timer.stop();
                    nextWave.start();
                }
                if (amount == 0) new Necropick(4000, 1000);
                if (amount == 5) {
                    new Barricados(800, 800);
                    new Omenoct(4000, 1000);
                }
                if (amount == 12) {
                    for (Enemy enemy : EntityData.getEnemies()) {
                        if (enemy instanceof Barricados) {
                            enemy.dieProcess();
                            break;
                        }
                    }
                    new BlackOrb(800, 500);
                }
                new Squarantine(-500, -200);
                new Trigorath(1800, 0);
            }
        }
    }

    public static void stopBuild() {
        if (timer.isRunning()) timer.stop();
    }

    public static void continueBuild() {
        timer.start();
    }

    public static void reset() {
        amount = 0;
        timer.stop();
        nextWave.stop();
    }
}