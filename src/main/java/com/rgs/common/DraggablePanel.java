package com.rgs.common;

import static java.awt.event.MouseEvent.BUTTON2;

import java.awt.*;
import java.awt.event.MouseEvent;

import com.rgs.render3d.Camera;

public abstract class DraggablePanel extends DemoReadyPanel {

    protected final Camera camera;

    private boolean isDragging = false;
    private int dragStartX = -1, dragStartY = -1;

    public DraggablePanel(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void tick() {
        // TODO
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
            //System.out.println("X travel: " + (e.getX() - this.dragStartX));
            //System.out.println("Y travel: " + (e.getY() - this.dragStartY));
            this.camera.setInProgressTranslation((e.getX() - this.dragStartX), (e.getY() - this.dragStartY));
        }
    }
}
