package com.rgs.common;

import java.awt.event.ActionEvent;
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

        InputMap inputMap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        // TODO make this cleaner

        KeyStroke aKeyStroke = KeyStroke.getKeyStroke('a');
        KeyStroke dKeyStroke = KeyStroke.getKeyStroke('d');
        KeyStroke wKeyStroke = KeyStroke.getKeyStroke('w');
        KeyStroke sKeyStroke = KeyStroke.getKeyStroke('s');

        inputMap.put(aKeyStroke, "a");
        inputMap.put(dKeyStroke, "d");
        inputMap.put(wKeyStroke, "w");
        inputMap.put(sKeyStroke, "s");

        actionMap.put("a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyPressed(aKeyStroke);
            }
        });

        actionMap.put("d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyPressed(dKeyStroke);
            }
        });

        actionMap.put("w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyPressed(wKeyStroke);
            }
        });

        actionMap.put("s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyPressed(sKeyStroke);
            }
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
