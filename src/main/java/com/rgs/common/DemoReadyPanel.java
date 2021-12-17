package com.rgs.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

/**
 * Abstraction layer between java.awt.event Listeners and a JPanel containing a demo.
 * Each demo JPanel must implement the tick method and can override whichever event
 * handlers are appropriate for a given demo.
 */
public abstract class DemoReadyPanel
    extends JPanel implements MouseListener, MouseMotionListener {

    public abstract void tick();

    protected DemoReadyPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // by default, do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // by default, do nothing
    }
}
