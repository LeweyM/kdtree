import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KdTree {
    int size;
    private Node root;

    public KdTree() {
        size = 0;
    }

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;

        public Node(Point2D p) {
            point = p;
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
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (size == 0) {
            root = new Node(p);
            size = 1;
        }
        root = insertNode(root, p, true);
    }

    private Node insertNode(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(p);
        }
        if (node.point().equals(p)) return node;
        if (lessOrEqual(node.point(), p, isVertical)) {
            Node leftSubTree = insertNode(node.left(), p, !isVertical);
            node.setLeft(leftSubTree);
        } else if (more(node.point(), p, isVertical)) {
            Node rightSubTree = insertNode(node.right(), p, !isVertical);
            node.setRight(rightSubTree);
        }
        return node;
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
        double left = 0;
        double right = 1;
        double bottom = 0;
        double top = 1;
        draw(root, true, null, left, right, bottom, top);
    }

    private void draw(Node node, boolean isVertical, Node parent, double left, double right, double bottom, double top) {
        if (node == null) return;
        StdDraw.setPenColor(Color.black);
        StdDraw.point(node.point.x(), node.point.y());

        if (isVertical) {
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
            StdDraw.setPenColor(Color.blue);
            if (node.point().x() < parent.point().x()) {
                right = parent.point().x();
            } else {
                left = parent.point().x();
            }
            StdDraw.line(left, node.point.y(), right, node.point.y());
        }

        draw(node.left(), !isVertical, node, left, right, bottom, top);
        draw(node.right(), !isVertical, node, left, right, bottom, top);
    }

    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    public Point2D nearest(Point2D p) {
        return null;
    }

    private boolean isInSubtree(Node node, Point2D p) {
        if (node == null) return false;
        if (node.point().x() == p.x() && node.point().y() == p.y()) return true;
        return isInSubtree(node.left(), p) || isInSubtree(node.right(), p);
    }
}
