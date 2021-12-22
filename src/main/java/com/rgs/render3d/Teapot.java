package com.rgs.render3d;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.rgs.common.Tri;
import com.rgs.common.WASDPanel;
import com.rgs.vector.Vector3D;

public class Teapot extends WASDPanel {

    public Teapot() {
        super(new Camera(Vector3D.of(30, 60, 100),
                         Vector3D.of(0, 0, 1),
                         2));

        this.setBackground(Color.BLACK);
        var frame = new JFrame("HTTP 418");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        List<Vector3D> points = new ArrayList<>();
        int lineNumber = 0;
        int countsLineNumber = 1;
        int numPoints = 0, numFaces = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/models/teapot.off"))) {
            for (String line; (line = br.readLine()) != null; lineNumber++) {
                if (lineNumber == countsLineNumber) {
                    String[] counts = line.replaceAll("\\s+", ",").split(",");
                    numPoints = Integer.parseInt(counts[0]);
                    numFaces = Integer.parseInt(counts[1]);
                }

                if (lineNumber > countsLineNumber && lineNumber <= countsLineNumber + numPoints) {
                    System.out.println("point: " + line);
                    String[] coords = line.replaceAll("\\s+", ",").split(",");
                    points.add(Vector3D.of(50 + 100 * Double.parseDouble(coords[0]), 100 * Double.parseDouble(coords[2]), 100 * Double.parseDouble(coords[1])));
                } else if (lineNumber > countsLineNumber && lineNumber <= countsLineNumber + numPoints + numFaces) {
                    System.out.println("quad: " + line);
                    String[] vertices = line.replaceAll("\\s+", ",").split(",");
                    if (vertices[0].equals("3")) {
                        TrisInWorld.addTri(Tri.of(points.get(Integer.parseInt(vertices[1])),
                                                  points.get(Integer.parseInt(vertices[2])),
                                                  points.get(Integer.parseInt(vertices[3]))));
                    } else if (vertices[0].equals("4")) {
                        TrisInWorld.addTris(Tri.trisForSquareSurface(points.get(Integer.parseInt(vertices[1])),
                                                                     points.get(Integer.parseInt(vertices[2])),
                                                                     points.get(Integer.parseInt(vertices[3])),
                                                                     points.get(Integer.parseInt(vertices[4]))));
                    } else {
                        System.out.println("Unknown type of face with " + vertices[0] + " edges");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
