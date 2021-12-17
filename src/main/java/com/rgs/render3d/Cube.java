package com.rgs.render3d;

import java.awt.*;

import javax.swing.*;

import com.rgs.common.DraggablePanel;
import com.rgs.common.Tri;
import com.rgs.vector.Vector3D;

public class Cube extends DraggablePanel {


    public Cube() {
        super(new Camera(Vector3D.of(-10, 30, -300),
                         Vector3D.of(0, 0, 1),
                         10));

        this.setBackground(Color.BLACK);
        var frame = new JFrame("Cube In Perspective");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(10, 10, 10),
                                                     Vector3D.of(10, 100, 10),
                                                     Vector3D.of(100, 10, 10),
                                                     Vector3D.of(100, 100, 10)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(10, 10, 100),
                                                     Vector3D.of(10, 100, 100),
                                                     Vector3D.of(100, 10, 100),
                                                     Vector3D.of(100, 100, 100)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(10, 100, 10),
                                                     Vector3D.of(10, 10, 10),
                                                     Vector3D.of(10, 100, 100),
                                                     Vector3D.of(10, 10, 100)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(100, 100, 10),
                                                     Vector3D.of(100, 10, 10),
                                                     Vector3D.of(100, 100, 100),
                                                     Vector3D.of(100, 10, 100)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(10, 10, 10),
                                                     Vector3D.of(100, 10, 10),
                                                     Vector3D.of(10, 10, 100),
                                                     Vector3D.of(100, 10, 100)));

        TrisInWorld.addTris(Tri.trisForSquareSurface(Vector3D.of(10, 100, 10),
                                                     Vector3D.of(100, 100, 10),
                                                     Vector3D.of(10, 100, 100),
                                                     Vector3D.of(100, 100, 100)));

    }
}
