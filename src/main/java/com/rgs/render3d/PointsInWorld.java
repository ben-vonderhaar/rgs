package com.rgs.render3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rgs.vector.Vector3D;

public class PointsInWorld {

    private static final Set<Vector3D> pointsSet = new HashSet<>();

    public static void addPoints(List<Vector3D> points) {
        pointsSet.addAll(points);
    }

    public static Set<Vector3D> getPoints() {
        return pointsSet;
    }

}
