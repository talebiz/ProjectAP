package model.game.enemies.miniBoss;

import model.game.enemies.Enemy;
import model.game.MyLine;

import java.util.Random;

import static database.Constant.BARRICADOS_SIZE;

public class Barricados extends Enemy {
    private BarricadosPanel panel;

    public Barricados() {
    }

    public Barricados(double x, double y) {
        super(x, y);
        xVertex = new double[4];
        yVertex = new double[4];
        lines = new MyLine[4];
        setVertices();
        setLines();
        canMeleeAttack = false;
        hovering = false;
        exertion = true;
        panel = new BarricadosPanel();
    }

    @Override
    protected void setVertices() {
        xVertex[0] = (int) (x - 0.5 * BARRICADOS_SIZE);
        yVertex[0] = (int) (y - 0.5 * BARRICADOS_SIZE);
        xVertex[1] = (int) (x + 0.5 * BARRICADOS_SIZE);
        yVertex[1] = (int) (y - 0.5 * BARRICADOS_SIZE);
        xVertex[2] = (int) (x + 0.5 * BARRICADOS_SIZE);
        yVertex[2] = (int) (y + 0.5 * BARRICADOS_SIZE);
        xVertex[3] = (int) (x - 0.5 * BARRICADOS_SIZE);
        yVertex[3] = (int) (y + 0.5 * BARRICADOS_SIZE);
    }

    public class BarricadosPanel {
        boolean isometric, rigid, exertionPanel;

        public BarricadosPanel() {
            isometric = true;
            rigid = new Random().nextDouble() > 0.5;
            exertionPanel = true;
        }
    }
}