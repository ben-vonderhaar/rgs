package com.rgs.demo;

import javax.swing.*;

import com.rgs.render3d.Cube;
import com.rgs.render3d.Parallelepiped;
import com.rgs.vector.Vector3D;

public class ParallelepipedInPerspectiveDemo {

    public static void main(String[] args) {
        DemoRenderLoop gameLoop = new DemoRenderLoop(
            new Parallelepiped(Vector3D.of(10, 10, 10),
                               Vector3D.of(75, 25, 15),
                               Vector3D.of(25, 100, 50),
                               50));
        new Timer(1000 / 30, gameLoop).start();
    }
}
