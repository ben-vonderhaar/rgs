package com.rgs.demo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rgs.common.DemoReadyPanel;

public class DemoRenderLoop
    implements ActionListener {

    private final DemoReadyPanel panel;

    public DemoRenderLoop(DemoReadyPanel panel) {
        this.panel = panel;
        this.panel.setBackground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.tick();
    }

}
