package com.rgs.common;

import static com.rgs.common.PerlinNoiseDistribution.gradientAt;
import static com.rgs.common.PerlinNoiseDistribution.permFromVector2D;
import static com.rgs.common.Vector2DUtils.dotProduct;
import static com.rgs.common.Vector2DUtils.lerp;
import static com.rgs.common.Vector2DUtils.smoothstepValue;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DrawPerlinNoise extends GameReadyPanel {

    private static final int TABLE_SIZE = 256;
    private static final int TABLE_SIZE_MASK = TABLE_SIZE - 1;

    private static final int BETWEEN_PANEL_SPACING = 10;
    private static final int OUTSIDE_PANEL_SPACING = 20;
    private static final int CONTROL_PANEL_WIDTH = 120;

    private int gridSize = 35;
    private int x0 = 20, y0 = 20;
    private int xOffset = 0, yOffset = 0;
    private int xOffsetScale = 0, yOffsetScale = 0;

    private final JPanel controlPanel;
    private final JTextField gridSizeDisplay, xOffsetScaleDisplay, yOffsetScaleDisplay;

    public DrawPerlinNoise() {
        gridSizeDisplay = new JTextField(String.valueOf(gridSize));
        gridSizeDisplay.setMinimumSize(new Dimension(60, 50));
        gridSizeDisplay.setEnabled(false);
        gridSizeDisplay.setColumns(2);

        xOffsetScaleDisplay = new JTextField(String.valueOf(gridSize));
        xOffsetScaleDisplay.setMinimumSize(new Dimension(60, 50));
        xOffsetScaleDisplay.setEnabled(false);
        xOffsetScaleDisplay.setColumns(2);

        yOffsetScaleDisplay = new JTextField(String.valueOf(gridSize));
        yOffsetScaleDisplay.setMinimumSize(new Dimension(60, 50));
        yOffsetScaleDisplay.setEnabled(false);
        yOffsetScaleDisplay.setColumns(2);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        createAdjuster("Grid Size", gridSizeDisplay, e -> gridSize++, e -> gridSize--);
        createAdjuster("X Offset", xOffsetScaleDisplay, e -> xOffsetScale++, e -> xOffsetScale--);
        createAdjuster("Y Offset", yOffsetScaleDisplay, e -> yOffsetScale++, e -> yOffsetScale--);

        add(controlPanel);
    }

    public void tick() {
        xOffset += xOffsetScale;
        yOffset += yOffsetScale;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gridSizeDisplay.setText(String.valueOf(gridSize));
        xOffsetScaleDisplay.setText(String.valueOf(xOffsetScale));
        yOffsetScaleDisplay.setText(String.valueOf(yOffsetScale));

        for (int i = 0; i < getWidth() - (OUTSIDE_PANEL_SPACING * 2) - BETWEEN_PANEL_SPACING - CONTROL_PANEL_WIDTH; i += 1) {
            for (int j = 0; j < getHeight() - (OUTSIDE_PANEL_SPACING * 2); j += 1) {
                drawPixel(g, i, j);
            }
        }

        g.setColor(Color.WHITE);

        Rectangle controlPanelBounds = new Rectangle(getWidth() - OUTSIDE_PANEL_SPACING + BETWEEN_PANEL_SPACING - CONTROL_PANEL_WIDTH,
                                                     OUTSIDE_PANEL_SPACING,
                                                     CONTROL_PANEL_WIDTH - BETWEEN_PANEL_SPACING,
                                                     getHeight() - OUTSIDE_PANEL_SPACING * 2);

        controlPanel.setBounds(controlPanelBounds);
        controlPanel.setMaximumSize(new Dimension(CONTROL_PANEL_WIDTH, Integer.MAX_VALUE));
        controlPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void drawPixel(Graphics g, int x, int y) {

        int fullGridSize = gridSize * TABLE_SIZE;

        // Reposition x and y relative to display window
        Vector2D v = new Vector2D(x0 + x, y0 + y);

        x -= xOffset;
        y -= yOffset;

        if (x < 0) {
            x = fullGridSize - ((x % fullGridSize) * -1);
        }
        if (y < 0) {
            y = fullGridSize - ((y % fullGridSize) * -1);
        }

        x %= fullGridSize;
        y %= fullGridSize;

        int boundingBoxLoX = x / gridSize;
        int boundingBoxHiX = (boundingBoxLoX + 1) & TABLE_SIZE_MASK;
        int boundingBoxLoY = y / gridSize;
        int boundingBoxHiY = (boundingBoxLoY + 1) & TABLE_SIZE_MASK;

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

        drawGrayscalePixel(g, v, l2);
    }

    private void createAdjuster(String title, JTextField display, ActionListener incrementAction, ActionListener decrementAction) {
        JPanel adjusterPanel = new JPanel();
        adjusterPanel.setLayout(new FlowLayout());
        adjusterPanel.setMaximumSize(new Dimension(CONTROL_PANEL_WIDTH, 50));
        adjusterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        adjusterPanel.add(display);

        JPanel adjusterButtonPanel = new JPanel();
        adjusterButtonPanel.setLayout(new BoxLayout(adjusterButtonPanel, BoxLayout.PAGE_AXIS));
        adjusterButtonPanel.setMaximumSize(new Dimension(20, 50));

        JButton increaseButton = new JButton(("^"));
        increaseButton.setMaximumSize(new Dimension(20, 20));
        increaseButton.addActionListener(incrementAction);

        JButton decreaseButton = new JButton(("v"));
        decreaseButton.setMaximumSize(new Dimension(20, 20));
        decreaseButton.addActionListener(decrementAction);

        adjusterButtonPanel.add(increaseButton);
        adjusterButtonPanel.add(decreaseButton);
        adjusterPanel.add(adjusterButtonPanel);

        JLabel gridSizeLabel = new JLabel(title);
        adjusterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        controlPanel.add(gridSizeLabel);
        controlPanel.add(adjusterPanel);
    }

    private void drawGrayscalePixel(Graphics g, Vector2D v, double d) {
        // convert final interpolated value to [0, 255) range
        int grayscaleFactor = (int) ((d + 1) * (255 / 2.0));

        // Draw x and y relative to display window
        g.setColor(new Color(grayscaleFactor, grayscaleFactor, grayscaleFactor));
        g.drawLine((int) v.getX(), (int) v.getY(), (int) v.getX(), (int) v.getY());
    }
}
