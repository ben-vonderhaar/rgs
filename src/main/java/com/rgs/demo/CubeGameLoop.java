package com.rgs.demo;

import javax.swing.*;

import com.rgs.render3d.CubeInPerspective;

public class CubeGameLoop {

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop(new CubeInPerspective());
        new Timer(1000 / 30, gameLoop).start();
    }
}
