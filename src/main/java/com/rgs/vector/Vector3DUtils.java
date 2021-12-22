package com.rgs.vector;

public class Vector3DUtils {

    public static Vector3D lerp(Vector3D v0, Vector3D v1, double t) {
        return new Vector3D(Vector2DUtils.lerp(v0.getX(), v1.getX(), t),
                            Vector2DUtils.lerp(v0.getY(), v1.getY(), t),
                            Vector2DUtils.lerp(v0.getZ(), v1.getZ(), t));
    }

    public static Vector3D cross(Vector3D v0, Vector3D v1) {
        return Vector3D.of(v0.getY() * v1.getZ() - v0.getZ() * v1.getY(),
                           v0.getZ() * v1.getX() - v0.getX() * v1.getZ(),
                           v0.getX() * v1.getY() - v0.getY() * v1.getX());
    }

    public static double dot(Vector3D v0, Vector3D v1) {
        return v0.getX() * v1.getX()
             + v0.getY() * v1.getY()
             + v0.getZ() * v1.getZ();
    }
}
