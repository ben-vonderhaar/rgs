package com.rgs.common;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

/**
 * Abstraction layer between java.awt.event Listeners and a JPanel containing a demo.
 * Each demo JPanel must implement the tick method and can override whichever event
 * handlers are appropriate for a given demo.
 */
public abstract class DemoReadyPanel
    extends JPanel implements MouseListener, MouseMotionListener {

    public abstract void tick();

    private Set<Integer> keysCurrentlyDown = new HashSet<>();

    protected DemoReadyPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (!keysCurrentlyDown.contains(e.getKeyCode())) {
                    keysCurrentlyDown.add(e.getKeyCode());
                    keyPressed(KeyStroke.getKeyStroke((char) e.getKeyCode()));
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                keysCurrentlyDown.remove(e.getKeyCode());
                keyReleased(KeyStroke.getKeyStroke((char) e.getKeyCode()));
            }

            return false;
        });
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

    public void keyTyped(KeyStroke key) {
        // by default, do nothing
    }

    public void keyPressed(KeyStroke key) {
        // by default, do nothing
    }

    public void keyReleased(KeyStroke key) {
        // by default, do nothing
    }
}
