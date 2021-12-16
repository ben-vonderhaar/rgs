package com.rgs.demo;

import javax.swing.*;

import com.rgs.common.DrawPerlinNoise;

public class PerlinNoiseDemo {

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop(new DrawPerlinNoise());
        new Timer(1000 / 30, gameLoop).start();
    }
}
