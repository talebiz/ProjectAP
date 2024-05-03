import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircleTriangleCollisionExample extends JPanel implements ActionListener {

    private int cx = 100; // Circle center X-coordinate
    private int cy = 300; // Circle center Y-coordinate
    private int radius = 30; // Circle radius

    private int[] triangleX = {200, 250, 300}; // Triangle vertices X-coordinates
    private int[] triangleY = {200, 300, 250}; // Triangle vertices Y-coordinates

    public CircleTriangleCollisionExample() {
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawOval(cx - radius, cy - radius, 2 * radius, 2 * radius); // Draw the circle
        g.setColor(Color.BLUE);
        g.drawPolygon(triangleX, triangleY, 3); // Draw the triangle
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move the circle (for demonstration purposes)
        cx += 1;

        // Check for collision
        if (isCollision()) {
            System.out.println("Collision detected!");
        }

        repaint();
    }

    private boolean isCollision() {
        // Check if the circle's center is inside the triangle
        Polygon triangle = new Polygon(triangleX, triangleY, 3);
        return triangle.contains(cx, cy);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle-Triangle Collision Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new CircleTriangleCollisionExample());
        frame.setVisible(true);
    }
}
