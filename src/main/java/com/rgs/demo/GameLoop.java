package com.rgs.demo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rgs.common.GameReadyPanel;

public class GameLoop implements ActionListener {

    private final GameReadyPanel panel;

    public GameLoop(GameReadyPanel panel) {
        this.panel = panel;
        this.panel.setBackground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.tick();
    }

}
