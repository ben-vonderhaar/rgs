package com.rgs.common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameLoop implements ActionListener {

    private GameReadyPanel panel;

    public GameLoop() {
        panel = new DrawPerlinNoise();
        panel.setBackground(Color.BLACK);
        var frame = new JFrame("Graphics Loop");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.tick();
    }

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        new Timer(1000 / 30, gameLoop).start();
    }
}
