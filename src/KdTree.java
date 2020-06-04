import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KdTree {
    int size;
    private Node root;

    public KdTree() {
        size = 0;
    }

    private static class Node {
        private final Point2D point;
        private RectHV boundingRect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV boundingRect) {
            point = p;
            this.boundingRect = boundingRect;
            left = null;
            right = null;
        }

        public Point2D point() {
            return point;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public void setLeft(Node node) {
            left = node;
        }

        public void setRight(Node node) {
            right = node;
        }

        public RectHV boundingRect() {
            return boundingRect;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insertNode(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private Node insertNode(Node node, Point2D p, boolean isVertical, RectHV boundingRect) {
        if (node == null) {
            size++;
            return new Node(p, boundingRect);
        }

        if (node.point().equals(p)) return node;

        RectHV rect = getRect(p, node, isVertical);
        if (lessOrEqual(node.point(), p, isVertical)) {
            Node leftSubTree = insertNode(node.left(), p, !isVertical, rect);
            node.setLeft(leftSubTree);
        } else if (more(node.point(), p, isVertical)) {
            Node rightSubTree = insertNode(node.right(), p, !isVertical, rect);
            node.setRight(rightSubTree);
        }
        return node;
    }

    private RectHV getRect(Point2D p, Node parent, boolean isVertical) {
        if (parent == null) return new RectHV(0, 0, 1, 1);

        RectHV parentRect = parent.boundingRect();

        double left = parentRect.xmin();
        double right = parentRect.xmax();
        double bottom = parentRect.ymin();
        double top = parentRect.ymax();

        if (isVertical) {
            if (p.x() < parent.point().x()) {
                right = parent.point().x();
            } else {
                left = parent.point().x();
            }
        } else {
            if (p.y() < parent.point().y()) {
                top = parent.point().y();
            } else {
                bottom = parent.point().y();
            }
        }

        return new RectHV(left, bottom, right, top);
    }

    private boolean more(Point2D p1, Point2D p2, boolean isVertical) {
        if (isVertical) return p1.x() > p2.x();
        else return p1.y() > p2.y();
    }

    private boolean lessOrEqual(Point2D p1, Point2D p2, boolean isVertical) {
        if (isVertical) return p1.x() <= p2.x();
        else return p1.y() <= p2.y();
    }

    public boolean contains(Point2D p) {
        return isInSubtree(root, p);
    }

    public void draw() {
        draw(root, true, null);
    }

    private void draw(Node node, boolean isVertical, Node parent) {
        if (node == null) return;

        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.03);
        StdDraw.point(node.point.x(), node.point.y());

        double left = node.boundingRect.xmin();
        double right = node.boundingRect.xmax();
        double bottom = node.boundingRect.ymin();
        double top = node.boundingRect.ymax();

        if (isVertical) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(Color.red);
            if (parent != null) {
                if (node.point().y() < parent.point().y()) {
                    top = parent.point().y();
                } else {
                    bottom = parent.point().y();
                }
            }
            StdDraw.line(node.point.x(), bottom, node.point.x(), top);
        } else {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(Color.blue);
            if (node.point().x() < parent.point().x()) {
                right = parent.point().x();
            } else {
                left = parent.point().x();
            }
            StdDraw.line(left, node.point.y(), right, node.point.y());
        }

        draw(node.left(), !isVertical, node);
        draw(node.right(), !isVertical, node);
    }

    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    public Point2D nearest(Point2D p) {
        Node node = nearestInSubtree(root, p, null, true);
        assert (node != null);
        return node.point();

    }

    private Node nearestInSubtree(Node node, Point2D p, Node closestNode, boolean isVertical) {
        if (node == null) return closestNode;
        double dist = node.point().distanceTo(p);
        if (closestNode == null || dist < closestNode.point().distanceTo(p)) {
            closestNode = node;
        }
        // 2. recurse right if result from recursion is longer than distance to bounding rect.
        if (closestNode.point().distanceTo(p) < node.boundingRect().distanceTo(p)) {
            return closestNode;
        }

        // 1. recurse in side with p
        Node searchFirst = sideWithPoint(node, p, isVertical);
        Node searchSecond = sideWithoutPoint(node, p, isVertical);

        closestNode = nearestInSubtree(searchFirst, p, closestNode, !isVertical);
        closestNode = nearestInSubtree(searchSecond, p, closestNode, !isVertical);

        return closestNode;
    }

    private Node sideWithPoint(Node node, Point2D p, boolean isVertical) {
        if (isVertical) {
            return p.x() <= node.point().x() ? node.left() : node.right();
        } else {
            return p.y() <= node.point().y() ? node.left() : node.right();
        }
    }

    private Node sideWithoutPoint(Node node, Point2D p, boolean isVertical) {
        if (isVertical) {
            return p.x() <= node.point().x() ? node.right() : node.left();
        } else {
            return p.y() <= node.point().y() ? node.right() : node.left();
        }
    }

    private boolean isInSubtree(Node node, Point2D p) {
        if (node == null) return false;
        if (node.point().x() == p.x() && node.point().y() == p.y()) return true;
        return isInSubtree(node.left(), p) || isInSubtree(node.right(), p);
    }
}
