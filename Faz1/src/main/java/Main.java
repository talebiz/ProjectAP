import controller.GameManger;

public class Main {
    public static void main(String[] args) {
        GameManger.getInstance();
//        System.out.println(distanceOfEpsilonAndLine(2,2,4,2,0,5));
    }

    public static double distanceOfEpsilonAndLine(double x1, double y1,
                                                  double x2, double y2, double x3, double y3) {
        double dx = x3 - x2;
        double dy = y3 - y2;
        double lenSquared = dx * dx + dy * dy;
        if (lenSquared == 0) return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double t = ((x1 - x2) * dx + (y1 - y2) * dy) / lenSquared;
        t = Math.max(0, Math.min(1, t));
        double closestX = x2 + t * dx;
        double closestY = y2 + t * dy;
        return Math.sqrt((x1 - closestX) * (x1 - closestX) + (y1 - closestY) * (y1 - closestY));
    }
}