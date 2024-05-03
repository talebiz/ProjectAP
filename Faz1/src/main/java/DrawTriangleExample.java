import javax.swing.*;
import java.awt.*;

public class DrawTriangleExample extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] xPoints = {100, 50, 150}; // X coordinates of triangle vertices
        int[] yPoints = {100, 180, 180}; // Y coordinates of triangle vertices

//        g.drawPolygon(xPoints, yPoints, 3);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.setColor(Color.GREEN);
        graphics2D.drawPolygon(xPoints, yPoints, 3);// Draw the triangle
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Draw Triangle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new DrawTriangleExample());
            frame.setSize(500, 500);
            frame.setVisible(true);
        });
    }
}
