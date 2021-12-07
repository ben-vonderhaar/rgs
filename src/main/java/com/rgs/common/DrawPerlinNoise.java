package com.rgs.common;

import static com.rgs.common.PerlinNoiseDistribution.gradientAt;
import static com.rgs.common.PerlinNoiseDistribution.permFromVector2D;
import static com.rgs.common.Vector2DUtils.dotProduct;
import static com.rgs.common.Vector2DUtils.lerp;
import static com.rgs.common.Vector2DUtils.smoothstepValue;

import java.awt.*;

import javax.swing.*;

public class DrawPerlinNoise extends JPanel {

    private static final int TABLE_SIZE = 256;
    private static final int TABLE_SIZE_MASK = TABLE_SIZE - 1;

    private int gridSize = 129;
    private int diameter;
    private int x0, y0, gridlinesCount;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point center = new Point(getWidth() / 2, getHeight() / 2);
        int radius = Math.min(getWidth() / 2, getHeight() / 2) - 50;
        diameter = radius * 2;
        x0 = center.x - radius;
        y0 = center.y - radius;

        gridlinesCount = diameter / gridSize;

        for (int i = 0; i < diameter; i += 1) {
            for (int j = 0; j < diameter; j += 1) {
                drawPixel(g, i, j);
            }
        }

    /*    g.setColor(Color.RED);

        for (int i = 0; i <= gridlinesCount; i += 1) {
            for (int j = 0; j <= gridlinesCount; j += 1) {
                drawGradientVector(
                    g, i, j, gradientFromVector2D(i, j));
            }
        }
*/
    }

    private void drawGradientVector(Graphics g, int x, int y, Vector2D v) {
        g.drawLine(x0 + (x * gridSize),
                   y0 + (y * gridSize),
                   x0 + (x * gridSize) + (int)(v.getX() * 10),
                   y0 + (y * gridSize) + (int)(v.getY() * 10));
    }

    private void drawPixel(Graphics g, int x, int y) {

        // Reposition x and y relative to display window
        Vector2D v = new Vector2D(x0 + x, y0 + y);

        int boundingBoxLoX = x / gridSize;
        int boundingBoxHiX = boundingBoxLoX + 1;
        int boundingBoxLoY = y / gridSize;
        int boundingBoxHiY = boundingBoxLoY + 1;

        Vector2D c0 = gradientAt(permFromVector2D(boundingBoxLoX, boundingBoxLoY));
        Vector2D c1 = gradientAt(permFromVector2D(boundingBoxLoX, boundingBoxHiY));
        Vector2D c2 = gradientAt(permFromVector2D(boundingBoxHiX, boundingBoxLoY));
        Vector2D c3 = gradientAt(permFromVector2D(boundingBoxHiX, boundingBoxHiY));

        double percentIntoBoundingBoxX = (x % gridSize) / ((double) gridSize);
        double percentIntoBoundingBoxY = (y % gridSize) / ((double) gridSize);

        // Interpolation weights
        Vector2D p0 = new Vector2D(percentIntoBoundingBoxX, percentIntoBoundingBoxY);
        Vector2D p1 = new Vector2D(percentIntoBoundingBoxX, percentIntoBoundingBoxY - 1);
        Vector2D p2 = new Vector2D(percentIntoBoundingBoxX - 1, percentIntoBoundingBoxY);
        Vector2D p3 = new Vector2D(percentIntoBoundingBoxX - 1, percentIntoBoundingBoxY - 1);

        Vector2D lerpSmoothingVector = new Vector2D(smoothstepValue(percentIntoBoundingBoxX),
                                                    smoothstepValue(percentIntoBoundingBoxY));

        // lerp stage 1
        double l0 = lerp(dotProduct(c0, p0), dotProduct(c1, p1), lerpSmoothingVector.getY());
        double l1 = lerp(dotProduct(c2, p2), dotProduct(c3, p3), lerpSmoothingVector.getY());

        // lerp stage 2
        double l2 = lerp(l0, l1, lerpSmoothingVector.getX());

        // convert final interpolated value to [0, 255) range
        int grayscaleFactor = (int) ((l2 + 1) * (255 / 2.0));

        // Draw x and y relative to display window
        g.setColor(new Color(grayscaleFactor, grayscaleFactor, grayscaleFactor));
        g.drawLine((int) v.getX(), (int) v.getY(), (int) v.getX(), (int) v.getY());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JPanel panel = new DrawPerlinNoise();
            panel.setBackground(Color.BLACK);
            var frame = new JFrame("Perlin Noize");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

}
