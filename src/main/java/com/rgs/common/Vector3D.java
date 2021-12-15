package com.rgs.common;

import java.util.Objects;

public class Vector3D implements IVector {

    private double x, y, z;
    private double length;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.length = Math.sqrt(x * x + y * y + z * z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public double getLength() {
        return this.length;
    }

    public Vector3D add(Vector3D that) {
        return new Vector3D(this.x + that.getX(),
                            this.y + that.getY(),
                            this.z + that.getZ());
    }

    public Vector3D subtract(Vector3D that) {
        return new Vector3D(this.x - that.getX(),
                            this.y - that.getY(),
                            this.z - that.getZ());
    }

    public Vector3D rotateAboutX(double d) {
        return rotateAboutX(Math.cos(d), Math.sin(d));
    }

    public Vector3D rotateAboutX(double cosDegrees, double sinDegrees) {
        // 1     0      0
        // 0     cos(d) -sin(d)
        // 0     sin(d) cos(d)

        return new Vector3D(1 * this.x /* + (0 * this.y) + (0 * this.z) */,
            /* 0 + */ (cosDegrees * this.y) + (sinDegrees * this.z * -1),
            /* 0 + */ (sinDegrees * this.y) + (cosDegrees + this.z));
    }

    public double dot(Vector3D that) {
        return this.x * that.getX() + this.y * that.getY() + this.z * that.getZ();
    }

    @Override
    public Vector3D normalize() {
        return new Vector3D(x / this.length, y / this.length, z / this.length);
    }

    @Override
    public Vector3D scale(double scale) {
        return new Vector3D(x * scale, y * scale, z * scale);
    }

    public Vector3D vectorTo(Vector3D point) {
        return new Vector3D(point.getX() - this.x,
                            point.getY() - this.y,
                            point.getZ() - this.z);
    }

    public static Vector3D of(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector3D{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vector3D vector3D = (Vector3D) o;
        return Double.compare(vector3D.x, x) == 0 && Double.compare(vector3D.y, y) == 0 && Double.compare(vector3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
