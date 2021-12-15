package com.rgs.render3d;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.rgs.common.GameReadyPanel;
import com.rgs.common.Vector3D;

public class CubeInPerspective extends GameReadyPanel {

    private static Camera camera;

    private List<Vector3D> points;

    private CubeInPerspective() {

        this.setBackground(Color.BLACK);
        var frame = new JFrame("Cube In Perspective");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        this.points = List.of(Vector3D.of(1, 0, 1),
                              Vector3D.of(1, 1, 1),
                              Vector3D.of(2, 0, 1),
                              Vector3D.of(2, 1, 1),
                              Vector3D.of(1, 0, 2),
                              Vector3D.of(1, 1, 2),
                              Vector3D.of(2, 0, 2),
                              Vector3D.of(2, 1, 2));

        PointsInWorld.addPoints(this.points);
    }

    public static void main(String[] args) {

        camera = new Camera(Vector3D.of(-1, 3, -3),
                            Vector3D.of(0, 0, 1),
                            1.25);

        GameReadyPanel panel = new CubeInPerspective();

        // TODO
        //camera.recalculateLocalCoords();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        camera.drawAxes(g);
        camera.drawVectorsAsVertices(g);
    }

    @Override
    public void tick() {
        //camera.drawVectorsAsVertices(this);
    }
}
