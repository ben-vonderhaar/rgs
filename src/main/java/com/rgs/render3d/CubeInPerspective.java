package com.rgs.render3d;

import static java.awt.event.MouseEvent.BUTTON2;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.rgs.common.GameReadyPanel;
import com.rgs.common.Tri;
import com.rgs.common.Vector3D;

public class CubeInPerspective extends GameReadyPanel {

    private final Camera camera;

    private boolean isDragging = false;
    private int dragStartX = -1, dragStartY = -1;

    public CubeInPerspective() {
        super();

        camera = new Camera(Vector3D.of(-1, 3, -15),
                            Vector3D.of(0, 0, 1),
                            1.25);

        this.setBackground(Color.BLACK);
        var frame = new JFrame("Cube In Perspective");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(1, 0, 1),
                                                     Vector3D.of(1, 1, 1),
                                                     Vector3D.of(2, 0, 1),
                                                     Vector3D.of(2, 1, 1)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(1, 0, 2),
                                                     Vector3D.of(1, 1, 2),
                                                     Vector3D.of(2, 0, 2),
                                                     Vector3D.of(2, 1, 2)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(1, 1, 1),
                                                     Vector3D.of(1, 0, 1),
                                                     Vector3D.of(1, 1, 2),
                                                     Vector3D.of(1, 0, 2)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(2, 1, 1),
                                                     Vector3D.of(2, 0, 1),
                                                     Vector3D.of(2, 1, 2),
                                                     Vector3D.of(2, 0, 2)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(1, 0, 1),
                                                     Vector3D.of(2, 0, 1),
                                                     Vector3D.of(1, 0, 2),
                                                     Vector3D.of(2, 0, 2)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(1, 1, 1),
                                                     Vector3D.of(2, 1, 1),
                                                     Vector3D.of(1, 1, 2),
                                                     Vector3D.of(2, 1, 2)));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        camera.drawAxes(g);
        camera.drawVectorsAsVertices(g);
        camera.drawTris(g);
    }

    @Override
    public void tick() {
        // TODO
        //camera.recalculateLocalCoords();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if (e.getButton() == BUTTON2) {
            this.isDragging = true;
            this.dragStartX = e.getX();
            this.dragStartY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (e.getButton() == BUTTON2) {
            this.isDragging = false;
            this.camera.clearInProgressTranslation();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (e.getButton() == BUTTON2 && isDragging) {
            System.out.println("X travel: " + (e.getX() - this.dragStartX));
            System.out.println("Y travel: " + (e.getY() - this.dragStartY));
            this.camera.setInProgressTranslation((e.getX() - this.dragStartX), (e.getY() - this.dragStartY));
        }
    }
}
