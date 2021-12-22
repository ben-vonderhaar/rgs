package com.rgs.common;

import static java.awt.event.MouseEvent.BUTTON2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import com.rgs.render3d.Camera;
import com.rgs.vector.Vector3D;

public abstract class WASDPanel extends DemoReadyPanel {

    protected final Camera camera;

    private boolean isLooking = false;
    private int lookStartX = -1, lookStartY = -1;

    private final Set<Character> downKeys = new HashSet<>();

    public WASDPanel(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void tick() {
        // TODO for optimization?
        //camera.recalculateLocalCoords();

        Vector3D cameraDirection = this.camera.getDirection();
        Vector3D orthonormal = cameraDirection.cross(Vector3D.UP);

        if (downKeys.contains('A')) {
            this.camera.doInstantTranslation(orthonormal.normalize().scale(10));
        }

        if (downKeys.contains('D')) {
            this.camera.doInstantTranslation(orthonormal.normalize().scale(-10));
        }

        if (downKeys.contains('W')) {
            this.camera.doInstantTranslation(cameraDirection.normalize().scale(-10));
        }

        if (downKeys.contains('S')) {
            this.camera.doInstantTranslation(cameraDirection.normalize().scale(10));
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        camera.drawAxes(g);
        camera.drawVectorsAsVertices(g);
        camera.drawTris(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if (e.getButton() == BUTTON2) {
            this.isLooking = true;
            this.lookStartX = e.getX();
            this.lookStartY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (e.getButton() == BUTTON2) {
            this.isLooking = false;
            this.camera.clearInProgressOrientation();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (e.getButton() == BUTTON2 && isLooking) {
            // 400px for 180 rotation
            // currently only X orientation
            double tempOrientationX = ((e.getX() - this.lookStartX) / -400.0) * Math.PI;

            this.camera.setInProgressOrientation(tempOrientationX, 0);
        }
    }

    @Override
    public void keyPressed(KeyStroke key) {
        super.keyReleased(key);
        downKeys.add(key.getKeyChar());
    }

    @Override
    public void keyReleased(KeyStroke key) {
        super.keyReleased(key);
        downKeys.remove(key.getKeyChar());
    }
}
