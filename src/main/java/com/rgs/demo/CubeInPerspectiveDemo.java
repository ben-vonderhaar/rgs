package com.rgs.demo;

import javax.swing.*;

import com.rgs.render3d.Cube;

public class CubeInPerspectiveDemo {

    public static void main(String[] args) {
        DemoRenderLoop gameLoop = new DemoRenderLoop(new Cube(/*Vector3D.of(1, 0, 1),
                                                                           Vector3D.of(1, 1, 1),
                                                                           Vector3D.of(2, 0, 2)*/));
        new Timer(1000 / 30, gameLoop).start();
    }
}
