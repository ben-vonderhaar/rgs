package com.rgs.vector;

/**
 * @author <a href="mailto:benjamin.m.vonderhaar@gmail.com">[Ben VonDerHaar]</a>
 */
public class Vector2DUtils {

    public static Vector2D floorVector2D(Vector2D v) {
        return new Vector2D((int) Math.floor(v.getX()),
                            (int) Math.floor(v.getY()));
    }

    public static Vector2D vectorDistance(Vector2D v0, Vector2D v1) {
        return new Vector2D(Math.abs((int) v0.getX() - v1.getX()),
                            Math.abs((int) v0.getY() - v1.getY()));
    }

    public static Vector2D offsetVector(Vector2D v) {
        return new Vector2D(v.getX() - Math.floor(v.getX()),
                            v.getY() - Math.floor(v.getY()));
    }

    public static Vector2D randomVector2D() {
        return new Vector2D(2 * Math.random() - 1, 2 * Math.random() - 1);
    }

    public static double dotProduct(Vector2D v0, Vector2D v1) {
        return v0.getX() * v1.getX() + v0.getY() * v1.getY();
    }

    public static Vector2D lerpClamped(Vector2D v0, Vector2D v1, double t) {
        return new Vector2D(lerpClamped(v0.getX(), v1.getX(), t),
                            lerpClamped(v0.getY(), v1.getY(), t));
    }

    public static double lerpClamped(double d0, double d1, double t) {
        return Math.min(1, Math.max(-1, (1 - t) * d0 + t * d1));
    }

    public static double lerp(double d0, double d1, double t) {
        return (1 - t) * d0 + t * d1;
    }

    public static double smoothstep(double d0, double d1, double t) {
        return Math.min(1, Math.max(-1, (d1 - d0) * (3.0 - t * 2.0) * t * t + d0));
    }

    public static double smoothstepValue(double t) {
        return t * t * (3 - 2 * t);
    }
}
