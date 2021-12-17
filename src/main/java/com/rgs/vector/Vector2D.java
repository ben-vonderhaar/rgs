package com.rgs.vector;

public class Vector2D implements IVector {

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

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public Vector2D normalize() {
        return new Vector2D(x / this.length, y / this.length);
    }

    @Override
    public Vector2D scale(double scale) {
        return new Vector2D(x * scale, y * scale);
    }

    public static Vector2D of(double x, double y) {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + '}';
    }
}
