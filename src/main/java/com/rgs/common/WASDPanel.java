package com.rgs.common;

import static java.awt.event.MouseEvent.BUTTON2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.rgs.render3d.Camera;
import com.rgs.vector.Vector3D;

public abstract class WASDPanel extends DemoReadyPanel {

    protected final Camera camera;

    private boolean isLooking = false;
    private int lookStartX = -1, lookStartY = -1;

    public WASDPanel(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void tick() {
        // TODO for optimization?
        //camera.recalculateLocalCoords();
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
            // TODO convert to radians

            // 400px for 180 rotation
            // currently only X orientation
            double tempOrientationX = ((e.getX() - this.lookStartX) / -400.0) * Math.PI;

            this.camera.setInProgressOrientation(tempOrientationX, 0);
        }
    }

    @Override
    public void keyPressed(KeyStroke e) {

        Vector3D cameraDirection = this.camera.getDirection();
        Vector3D orthonormal = cameraDirection.cross(Vector3D.UP);

        switch (e.getKeyChar()) {
            case 'a':
                this.camera.doInstantTranslation(orthonormal.normalize().scale(10));
                break;
            case 'd':
                this.camera.doInstantTranslation(orthonormal.normalize().scale(-10));
                break;
            case 'w':
                this.camera.doInstantTranslation(cameraDirection.normalize().scale(-10));
                break;
            case 's':
                this.camera.doInstantTranslation(cameraDirection.normalize().scale(10));
                break;
            default:
                System.out.println(e.getKeyCode() + " typed - unknown");
                break;
        }
    }
}
