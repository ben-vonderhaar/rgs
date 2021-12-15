package com.rgs.render3d;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.rgs.common.GameReadyPanel;
import com.rgs.common.Tri;
import com.rgs.common.Vector3D;
import com.rgs.common.Vector3DUtils;

public class Camera {

    private Set<Vector3D> localPoints;

    private Vector3D position, direction;
    private double focalLength;

    private Vector3D viewingPlanePoint;
    private double viewingPlaneConstant;

    public Camera(Vector3D position, Vector3D direction, double focalLength) {

        this.localPoints = new HashSet<>();

        this.position = position;
        this.direction = direction.normalize();
        // TODO get x, y, z rotation angles from direction vector
        // TODO then https://en.wikipedia.org/wiki/3D_projection#Mathematical_formula
        // Quaternion from two Vector3D
        this.focalLength = focalLength;

        // find point on viewing plane
        viewingPlanePoint = this.position.add(
            this.direction.scale(this.focalLength / this.direction.getLength()));

        System.out.println("camera at: " + this.position);
        System.out.println("looking to: " + this.direction);
        System.out.println("focal length: " + this.focalLength);
        System.out.println("viewing plane point: " + viewingPlanePoint);

        viewingPlaneConstant = this.direction.dot(viewingPlanePoint);

        System.out.println("viewing plane constant: " + viewingPlaneConstant);
    }

    public Vector3D vectorTo(Vector3D point) {
        return this.position.vectorTo(point);
    }

    public void viewingPlaneIntersectionToPoint(Vector3D point) {
        Vector3D vectorTo = vectorTo(point);

        // parameterized line = this.position + t * vectorTo
        // x = p_x + t*vt_x
        // y = p_y + t*vt_y
        // z = p_z + t*vt_z

        // vpp_x * (p_x + t*vt_x) + vpp_y * (p_y + t*vt_y) + vpp_z * (p_z + t*vt_z) = vpc
        // (p_x*vpp_x + p_y*vpp_y + p_z*vpp_z) + t*(vt_x*vpp_x + vt_y*vpp_y + vt_z*vpp_z) = vpc
        // (p . vpp) + t*(vt . vpp) = vpc
        // t*(vt . vpp) = vpc - (p . vpp)
        // t = (vpc - (p . vpp)) / (vt . vpp)

        // TODO no intersection, all points intersecting cases
        // https://ocw.mit.edu/courses/mathematics/18-02sc-multivariable-calculus-fall-2010/1.-vectors-and-matrices/part-c-parametric-equations-for-curves/session-16-intersection-of-a-line-and-a-plane/MIT18_02SC_we_9_comb.pdf

        double t = (viewingPlaneConstant - this.position.dot(this.viewingPlanePoint)) / vectorTo.dot(this.viewingPlanePoint);

        Vector3D intersection = new Vector3D(this.position.getX() + t * vectorTo.getX(),
                                             this.position.getY() + t * vectorTo.getY(),
                                             this.position.getZ() + t * vectorTo.getZ());

        //System.out.println("t = " + t);
        //System.out.println("intersection = " + intersection);

    }

    public void recalculateLocalCoords() {
        Set<Vector3D> globalCoordinatePoints = PointsInWorld.getPoints();

        this.localPoints.clear();

        for (Vector3D point : globalCoordinatePoints) {
            this.localPoints.add(convertGlobalPointToLocalPoint(point));
        }
    }

    // TODO move to more generic location, as this is not camera-specific functionality
    private Vector3D convertGlobalPointToLocalPoint(Vector3D globalPoint) {

        // Using http://paulbourke.net/geometry/rotate/

        double xyPlaneProjectionLength = Math.sqrt(this.direction.getY() * this.direction.getY()
                                                       + this.direction.getZ() * this.direction.getZ());

        globalPoint.subtract(this.position)
                   .rotateAboutX(this.direction.getY() / xyPlaneProjectionLength,
                                 this.direction.getZ() / xyPlaneProjectionLength);


        return null;
    }

    public void drawAxes(Graphics g) {

        g.setColor(Color.CYAN);
        // Z Axis
        IntStream.range(0, 7).forEach(i -> drawPoint(g, new Vector3D(0, 0, 1.5 - (i * 0.5))));

        g.setColor(Color.ORANGE);
        drawPoint(g, new Vector3D(0, 0, 1.5));

        g.setColor(Color.GREEN);
        // Y Axis
        IntStream.range(0, 7).forEach(i -> drawPoint(g, new Vector3D(0, 1.5 - (i * 0.5), 0)));

        g.setColor(Color.ORANGE);
        drawPoint(g, new Vector3D(0, 1.5, 0));

        g.setColor(Color.RED);
        // X Axis
        IntStream.range(0, 6).forEach(i -> drawPoint(g, new Vector3D(1.5 - (i * 0.5), 0, 0)));

        g.setColor(Color.ORANGE);
        drawPoint(g, new Vector3D(1.5, 0, 0));
    }

    public void drawTris(Graphics g) {
        g.setColor(Color.YELLOW);
        for(Tri tri : TrisInWorld.getTris()) {
            System.out.println(tri.getP0());
            System.out.println(tri.getP1());
            System.out.println(tri.getP2());
            drawLineBetweenPoints(g, tri.getP0(), tri.getP1());
            drawLineBetweenPoints(g, tri.getP1(), tri.getP2());
            drawLineBetweenPoints(g, tri.getP2(), tri.getP0());
        }
    }

    public void drawVectorsAsVertices(Graphics g) {

        g.setColor(Color.WHITE);

        for (Vector3D point : PointsInWorld.getPoints()) {
            drawPoint(g, point);
        }
    }

    private void drawLineBetweenPoints(Graphics g, Vector3D v0, Vector3D v1) {
        System.out.println("draw between: " + v0 + " -> " + v1);
        // Draw pixels between points, including a pixel at each point
        IntStream.range(0, 11)
                 .forEach(i -> drawPoint(g, Vector3DUtils.lerp(v0, v1, i * .1)));
    }

    private void drawPoint(Graphics g, Vector3D point) {

        viewingPlaneIntersectionToPoint(point);
        Vector3D diff = point.subtract(this.position);

        // Assuming looking straight forward aka (0,0,0) direction vector
        // Accommodating for direction needs component-specific cos/sin calcs
        double cos = Math.cos(0.0);
        double sin = Math.sin(0.0);

        //c_y * (s_z * y + c_z * x) - s_y * z
        double transformedX = cos * (sin * diff.getY() + cos * diff.getX()) - sin * diff.getZ();

        //s_x * (c_y * z + s_y * (s_z * y + c_z * x)) + c_x * (c_z * y - s_z * x)
        double transformedY = sin * (cos * diff.getZ() + sin * (sin * diff.getY() + cos * diff.getX())) + cos * (cos * diff.getY() - sin * diff.getX());

        //c_x * (c_y * z + s_y * (s_z * y + c_z * x)) - s_x * (c_z * y - s_z * x)
        double transformedZ = cos * (cos * diff.getZ() + sin * (sin * diff.getY() + cos * diff.getX())) - sin * (cos * diff.getY() - sin * diff.getX());

        System.out.println("point:       (" + point.getX() + ", " + point.getY() + ", " + point.getZ() + ")");
        System.out.println("transformed: (" + transformedX + ", " + transformedY + ", " + transformedZ + ")");

        double displayX = (viewingPlanePoint.getZ() / transformedZ) * transformedX + viewingPlanePoint.getX();
        double displayY = (viewingPlanePoint.getZ() / transformedZ) * transformedY + viewingPlanePoint.getY();

        //System.out.println("display at: (" + displayX + ", " + displayY + ")");

        // TODO configurable/dynamic offsets
        int xOffset = 400 /*panel.getWidth()*/ / 2;
        int yOffset = 400 /*panel.getHeight()*/ / 2;

        int finalX = 425 + (int)(displayX * 100);
        int finalY = 725 - (int)(displayY * 100);

        //System.out.println("final: (" + finalX + ", " + finalY + ")");

        g.drawLine(finalX,
                   finalY,
                   finalX,
                   finalY);
    }

    // TODO find intersection of resulting vector and a plane

}
