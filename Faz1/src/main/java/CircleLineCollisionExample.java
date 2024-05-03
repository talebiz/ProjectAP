import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircleLineCollisionExample extends JPanel implements ActionListener {

    private int cx = 100; // Circle center X-coordinate
    private int cy = 100; // Circle center Y-coordinate
    private int radius = 30; // Circle radius

    private int lineX1 = 300; // Line start X-coordinate
    private int lineY1 = 300; // Line start Y-coordinate
    private int lineX2 = 300; // Line end X-coordinate
    private int lineY2 = 100; // Line end Y-coordinate

    int a = 1;

    public CircleLineCollisionExample() {
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawOval(cx - radius, cy - radius, 2 * radius, 2 * radius); // Draw the circle
        g.setColor(Color.BLUE);
        g.drawLine(lineX1, lineY1, lineX2, lineY2); // Draw the line
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move the circle (for demonstration purposes)
        cx += a;

        // Check for collision
        if (isCollision()) {
            System.out.println("Collision detected!");
            a *= -1;
            if (lineX1 == 300) {
                lineX1 = 100;
                lineX2 = 100;
            } else {
                lineX1 = 300;
                lineX2 = 300;
            }
        }

        repaint();
    }

    private boolean isCollision() {
        // Calculate the distance between the circle center and the line
        double dx = lineX2 - lineX1;
        double dy = lineY2 - lineY1;
        double lineLength = Math.sqrt(dx * dx + dy * dy);

        double u = ((cx - lineX1) * dx + (cy - lineY1) * dy) / (lineLength * lineLength);

        double closestX = lineX1 + u * dx;
        double closestY = lineY1 + u * dy;

        double distance = Math.sqrt((cx - closestX) * (cx - closestX) + (cy - closestY) * (cy - closestY));

        return distance <= radius;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle-Line Collision Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new CircleLineCollisionExample());
        frame.setVisible(true);
    }
}
