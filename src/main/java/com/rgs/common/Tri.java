package com.rgs.common;

import java.util.List;

import com.rgs.vector.Vector3D;

public class Tri {

    private Vector3D p0, p1, p2;

    public Tri(Vector3D p0, Vector3D p1, Vector3D p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vector3D getP0() {
        return p0;
    }

    public Vector3D getP1() {
        return p1;
    }

    public Vector3D getP2() {
        return p2;
    }

    public static List<Tri> trisForSquareSurface(Vector3D p0, Vector3D p1, Vector3D p2, Vector3D p3) {
        return List.of(new Tri(p0, p1, p2),
                       new Tri(p1, p2, p3));
    }

    public static Tri of(Vector3D p0, Vector3D p1, Vector3D p2) {
        return new Tri(p0, p1, p2);
    }

    public List<Vector3D> getPoints() {
        return List.of(p0, p1, p2);
    }

    @Override
    public String toString() {
        return "Tri{" + "p0=" + p0 + ", p1=" + p1 + ", p2=" + p2 + '}';
    }
}
