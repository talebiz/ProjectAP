package model;

import controller.Util.Constant;

public class Trigorath extends Entity{
    double xCenter, yCenter;
    double[] vertices;

    public Trigorath(double xCenter, double yCenter) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;

    }

    @Override
    public int getSize() {
        return Constant.SQUARANTINE_SIZE;
    }
}
