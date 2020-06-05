import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> pointsInRect = new ArrayList<>();
        for (Point2D p : points) {
            if (   p.x() >= rect.xmin()
                && p.x() <= rect.xmax()
                && p.y() >= rect.ymin()
                && p.y() <= rect.ymax()) {
                pointsInRect.add(p);
            }
        }
        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        Point2D closestPoint = null;
        for (Point2D point : points) {
            if (p != point && (closestPoint == null || point.distanceTo(p) < closestPoint.distanceTo(p))) {
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    public static void main(String[] args) {
    }
}