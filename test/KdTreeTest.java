import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {

    private KdTree pSet;

    @BeforeEach
    void setUp() {
        pSet = new KdTree();
    }

    @Test
    void is_empty_and_size() {
        assertTrue(pSet.isEmpty());
        insertPoint(0, 0);
        assertFalse(pSet.isEmpty());
        assertEquals(1, pSet.size());
    }

    @Test
    void contains() {
        Point2D p = new Point2D(0, 0);
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(0.5, 0.5);
        assertFalse(pSet.contains(p));
        pSet.insert(p);
        assertTrue(pSet.contains(p));
        pSet.insert(p1);
        pSet.insert(p2);
        assertTrue(pSet.contains(p1));
        assertTrue(pSet.contains(p2));
    }

//    @Test
//    void nearest_point() {
//        insertPoint(0, 0);
//        insertPoint(0, 1);
//        insertPoint(1, 1);
//        assertEquals(pSet.nearest(new Point2D(0.1, 0.1)), new Point2D(0, 0));
//    }
//
//    @Test
//    void range() {
//        insertPoint(0, 0);
//        insertPoint(0.5, 0.5);
//        insertPoint(0, 1);
//        Iterable<Point2D> range = pSet.range(new RectHV(0.2, 0.2, 0.8, 0.8));
//        assertThat(range, Matchers.contains(new Point2D(0.5, 0.5)));
//        assertEquals(range.spliterator().getExactSizeIfKnown(), 1);
//    }

    private void insertPoint(double x, double y) {
        pSet.insert(new Point2D(x, y));
    }
}