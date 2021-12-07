package com.rgs.common;

public class Vector2D {

    private final double x, y, length;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        this.length = Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return this.length;
    }

    public Vector2D normalize() {
        return new Vector2D(x / this.length, y / this.length);
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + '}';
    }
}
