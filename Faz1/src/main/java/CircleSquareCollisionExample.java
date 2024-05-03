import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircleSquareCollisionExample extends JPanel implements ActionListener {

    private int cx = 200; // Circle center X-coordinate
    private int cy = 200; // Circle center Y-coordinate
    private int radius = 50; // Circle radius

    private int sx = 200; // Square top-left corner X-coordinate
    private int sy = 200; // Square top-left corner Y-coordinate
    private int sideLength = 50; // Square side length

    public CircleSquareCollisionExample() {
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawOval(cx - radius, cy - radius, 2 * radius, 2 * radius); // Draw the circle
        g.setColor(Color.BLUE);
        g.drawRect(sx, sy, sideLength, sideLength); // Draw the square
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move the circle (for demonstration purposes)
//        cx += 1;

        // Check for collision
        if (isCollision()) {
//            System.out.println("Collision detected!");
//            System.out.println(sx);
//            System.out.println(cx);
//            System.exit(0);
        }

        repaint();
    }

    private boolean isCollision() {
        // Calculate the closest point on the square to the circle center
        int closestX = Math.max(sx, Math.min(cx, sx + sideLength));
        int closestY = Math.max(sy, Math.min(cy, sy + sideLength));

        // Calculate the distance between the closest point and the circle center
        int dx = cx - closestX;
        int dy = cy - closestY;
        int distanceSquared = dx * dx + dy * dy;

        // Check if the distance is less than or equal to the circle's radius
        return distanceSquared <= radius * radius;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle-Square Collision Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new CircleSquareCollisionExample());
        frame.setVisible(true);
    }
}
