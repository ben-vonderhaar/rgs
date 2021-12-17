package com.rgs.render3d;

import java.awt.*;

import javax.swing.*;

import com.rgs.common.DraggablePanel;
import com.rgs.common.Tri;
import com.rgs.vector.Vector3D;

/**
 * A parallelepiped is a three-dimensional shape that is easily defined in a
 * parametric way.  One can think of a parallelepiped as a parallelogram (a four-sided
 * shape with two sets of parallel sides) extruded along its normal vector.  Note that
 * a cube is a type of parallelepiped where the extruded face is a square, and the
 * extrusion is the same length as the length of the edges of the square.
 */
public class Parallelepiped extends DraggablePanel {

    // TODO extract panel creation to some subclass of DemoReadyPanel

    /**
     * Generates a parallelepiped from the specified parameters.  Uses v0 as an "origin", v0->v1
     * as one side of the base parallelogram, v0->v2 as the second side, and then extruded by
     * the specified height along the cross product of v0->v1 and v0->v2.  Uses the right hand rule
     * to determine the direction of the extrusion via cross product.
     *
     * @param v0
     * @param v1
     * @param v2
     * @param height
     */
    public Parallelepiped(Vector3D v0, Vector3D v1, Vector3D v2, double height) {

        super(new Camera(Vector3D.of(-10, 30, -300),
                         Vector3D.of(0, 0, 1),
                         10));

        this.setBackground(Color.BLACK);
        var frame = new JFrame("Cube In Perspective");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        Vector3D ab0 = v1.subtract(v0);
        Vector3D ac0 = v2.subtract(v0);
        Vector3D bd0 = v1.add(ab0);
        Vector3D cd0 = v2.add(ac0);

        Vector3D crossVector = ab0.cross(ac0).normalize().scale(height);

        Vector3D ab1 = ab0.add(crossVector);
        Vector3D ac1 = ac0.add(crossVector);
        Vector3D bd1 = bd0.add(crossVector);
        Vector3D cd1 = cd0.add(crossVector);

        // plane 0
        TrisInWorld.addTris(Tri.trisForSquareSurface(ab0, ac0, bd0, cd0));

        // plane 1
        TrisInWorld.addTris(Tri.trisForSquareSurface(bd1, cd1, ab1, ac1));
        //TrisInWorld.addTris(Tri.trisForSquareSurface(cd1, ab1, ac1, bd1));

        // side planes
        TrisInWorld.addTris(Tri.trisForSquareSurface(ab0, ac0, ab1, ac1));
        TrisInWorld.addTris(Tri.trisForSquareSurface(cd0, ac0, cd1, ac1));
        TrisInWorld.addTris(Tri.trisForSquareSurface(bd0, cd0, bd1, cd1));
        TrisInWorld.addTris(Tri.trisForSquareSurface(ab0, bd0, ab1, bd1));
    }

}
