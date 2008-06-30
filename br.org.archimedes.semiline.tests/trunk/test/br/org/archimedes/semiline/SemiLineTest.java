
package br.org.archimedes.semiline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.SquarePoint;

public class SemiLineTest extends Tester {

    private Point point;

    private Point otherPoint;

    private SemiLine sl;


    @Before
    public void setUp () throws Exception {

        point = new Point(0, 0);
        otherPoint = new Point(100, 100);
        sl = new SemiLine(point, otherPoint);
    }

    @Test(expected = InvalidArgumentException.class)
    public void doesNotCreateWhenPassingInvalidPoints () throws Exception {

        new SemiLine(point, point);
    }

    @Test(expected = InvalidArgumentException.class)
    public void doesNotCreateWhenPassingInvalidCoordinates () throws Exception {

        new SemiLine(0, 0, 0, 0);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingNullPoints () throws Exception {

        new SemiLine(null, null);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingPointAndNull () throws Exception {

        new SemiLine(point, null);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingNullAndPoint () throws Exception {

        new SemiLine(null, point);
    }

    @Test
    public void testContainsWorksWithValidPoints () throws Exception {

        SemiLine sline = new SemiLine(0, 0, -4, 0);
        Point p1 = new Point( -5, 0);
        Point p2 = new Point( -4, 0);
        Point p3 = new Point( -2, 0);
        Point p4 = new Point(0, 0);
        Point p5 = new Point(2, 0);
        Point p6 = new Point(5, 5);
        Point p7 = new Point(5, -5);

        assertTrue("Should contain the point " + p1.toString(), sline
                .contains(p1));
        assertTrue("Should contain the point " + p2.toString(), sline
                .contains(p2));
        assertTrue("Should contain the point " + p3.toString(), sline
                .contains(p3));
        assertTrue("Should contain the point " + p4.toString(), sline
                .contains(p4));
        assertFalse("Should not contain the point " + p5.toString(), sline
                .contains(p5));
        assertFalse("Should not contain the point " + p6.toString(), sline
                .contains(p6));
        assertFalse("Should not contain the point " + p7.toString(), sline
                .contains(p7));

        sline = new SemiLine(0, 0, 0, 4);
        p1 = new Point(0, 5);
        p2 = new Point(0, 4);
        p3 = new Point(0, 2);
        p4 = new Point(0, 0);
        p5 = new Point(0, -2);
        p6 = new Point(5, 5);
        p7 = new Point( -5, 5);

        assertTrue("Should contain the point " + p1.toString(), sline
                .contains(p1));
        assertTrue("Should contain the point " + p2.toString(), sline
                .contains(p2));
        assertTrue("Should contain the point " + p3.toString(), sline
                .contains(p3));
        assertTrue("Should contain the point " + p4.toString(), sline
                .contains(p4));
        assertFalse("Should not contain the point " + p5.toString(), sline
                .contains(p5));
        assertFalse("Should not contain the point " + p6.toString(), sline
                .contains(p6));
        assertFalse("Should not contain the point " + p7.toString(), sline
                .contains(p7));
    }

    @Test(expected = NullArgumentException.class)
    public void containsThrowsNullArgumentIfReceivedANullArgument ()
            throws Exception {

        sl.contains(null);
    }

    @Test
    public void testEquals () throws InvalidArgumentException {

        SemiLine equal = new SemiLine(0, 0, 50, 50);
        SemiLine notEqual = new SemiLine(0, 0, 50, 100);
        Object someElement = new Line(0, 0, 100, 100);

        assertTrue("Should be the same semi line", sl.equals(sl));
        assertTrue("Should be the same semi line", sl.equals(equal));
        assertFalse("Semi lines should not be the same", sl.equals(notEqual));
        assertFalse("Objects should not be the same", sl.equals(someElement));
    }

    @Test
    public void testMove () throws Exception {

        sl.move(10, -20);
        SemiLine expected = new SemiLine(10, -20, 60, 30);
        assertTrue("Semi lines should be the same", expected.equals(sl));
    }

    @Test
    public void testNotContainable () {

        assertNull("The boundary rectangle should be null", sl
                .getBoundaryRectangle());
        assertFalse(sl.isInside(new Rectangle( -10, -10, 1000, 1000)));
    }

    @Test
    public void testIsPositiveDirection () throws Exception {

        SemiLine sline;

        /* horizontal line */
        sline = new SemiLine(0.0, 0.0, 1.0, 0.0);
        Point point = new Point(0.0, 1.0);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        point = new Point(0.0, -1.0);
        assertFalse("The point should be right of the line", getDirection(
                sline, point));

        point = new Point(0.5, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        /* vertical line */
        sline = new SemiLine(0.0, 0.0, 0.0, 1.0);
        point = new Point( -1.0, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        point = new Point(1.0, 0.0);
        assertFalse("The point should be right of the line", getDirection(
                sline, point));

        point = new Point(0.0, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        /* oblique line */
        sline = new SemiLine(0.0, 0.0, 1.0, 1.0);
        point = new Point(0.0, 1.0);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        point = new Point(1.0, 0.0);
        assertFalse("The point should be right of the line", getDirection(
                sline, point));

        point = new Point(0.5, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        sline = new SemiLine(0.0, 0.0, -1.0, 1.0);
        point = new Point(0.0, 1.0);
        assertFalse("The point should be right of the line", getDirection(
                sline, point));

        point = new Point( -1.0, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));

        point = new Point( -0.5, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline,
                point));
    }

    /**
     * @param sline
     *            The semiline to be tested
     * @param point
     *            The point to be tested
     * @return true if the point is at a positive direction to the line
     */
    private boolean getDirection (SemiLine sline, Point point) {

        boolean positiveDirection = false;
        try {
            positiveDirection = sline.isPositiveDirection(point);
        }
        catch (NullArgumentException e) {
            fail("Should not throw a NullArgumentException");
        }
        return positiveDirection;
    }

    @Test
    public void testCopyAndClone () throws Exception {

        // TODO Completar os testes pro offset

        Element cloned = sl.clone();
        assertTrue("Should be the same line.", sl.equals(cloned));
        assertFalse("Should not be the same reference.", sl == cloned);

        Element copied = sl.cloneWithDistance(Math.sqrt(50));
        SemiLine expected = new SemiLine( -5, 5, 95, 105);
        assertEquals("Should be the same line.", expected, copied);
        assertFalse("Should not be the same reference.", copied == sl);

        copied = sl.cloneWithDistance( -Math.sqrt(50));
        expected = new SemiLine(5, -5, 105, 95);
        assertEquals("Should be the same line.", expected, copied);
        assertFalse("Should not be the same reference.", copied == sl);
    }

    @Test
    public void testReferencePoints () throws Exception {

        Collection<ReferencePoint> references = sl
                .getReferencePoints(new Rectangle( -10, -10, -100, -100));
        Collection<ReferencePoint> expected = new ArrayList<ReferencePoint>();
        assertCollectionTheSame(expected, references);

        references = sl.getReferencePoints(new Rectangle(0, 0, -100, -100));
        expected.add(new SquarePoint(point, point, otherPoint));
        assertCollectionTheSame(expected, references);

        references = sl.getReferencePoints(new Rectangle(0, 0, 100, 100));
        assertCollectionTheSame(expected, references);

    }

    @Test
    public void testProjectionOf () throws Exception {

        // Semi line going like this: \
        SemiLine sline = new SemiLine(100, 0, 0, 100);
        Point toProject = new Point( -40, 150);
        Point expected = new Point( -45, 145);
        Point projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Same line, same expected, simetric point
        toProject = new Point( -50, 140);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Project (0,0)
        toProject = point;
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Project point of the line
        toProject = new Point(50, 50);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Project colinear not contained point
        toProject = new Point(150, -50);
        expected = new Point(150, -50);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Semi line to the other direction: /
        sline = new SemiLine( -10, 0, 0, 20);
        toProject = new Point(5, 5);
        expected = new Point( -5, 10);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Horizontal line
        sline = new SemiLine(0, 20, 100, 20);
        toProject = new Point(20, 10);
        expected = new Point(20, 20);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        toProject = new Point(20, 30);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Vertical line
        sline = new SemiLine(50, 20, 50, 100);
        toProject = new Point(25, 70);
        expected = new Point(50, 70);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        toProject = new Point(75, 70);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);
    }

    @Test(expected = NullArgumentException.class)
    public void projectionOfNullShouldThrowNullArgumentException ()
            throws Exception {

        sl.getProjectionOf(null);
    }

    /**
     * Safely projects a point on the semi line. Fails if any exception is
     * thrown.
     * 
     * @param line
     *            The line
     * @param toProject
     *            The point to project
     * @return The projection
     */
    private Point getSafeProjectionOf (SemiLine line, Point toProject) {

        Point projection = null;
        try {
            projection = line.getProjectionOf(toProject);
        }
        catch (NullArgumentException e) {
            fail("Somebody gave me a null point to project!");
        }
        return projection;
    }

    @Test
    public void testRotate () throws Exception {

        SemiLine line = new SemiLine(0, 0, 100, 0);
        SemiLine expected;

        safeRotate(line, point, Math.PI / 2);
        expected = new SemiLine(0, 0, 0, 100);
        assertEquals("Line should be equals.", expected, line);

        line = new SemiLine(0, 0, 100, 0);
        safeRotate(line, new Point(50, 0), Math.PI / 2);
        expected = new SemiLine(50, -50, 50, 50);
        assertEquals("Line should be equals.", expected, line);

        line = new SemiLine(0, 0, 100, 0);
        safeRotate(line, new Point(100, 0), Math.PI / 2);
        expected = new SemiLine(100, -100, 100, 0);
        assertEquals("Line should be equals.", expected, line);

        line = new SemiLine(0, 0, 100, 0);
        safeRotate(line, point, Math.PI / 4);
        expected = new SemiLine(0, 0, 100 * COS_45, 100 * COS_45);
        assertEquals("Line should be equals.", expected, line);

        line = new SemiLine(0, 0, 100, 0);
        safeRotate(line, point, -Math.PI / 4);
        expected = new SemiLine(0, 0, 100 * COS_45, -100 * COS_45);
        assertEquals("Line should be equals.", expected, line);
    }

    @Test(expected = NullArgumentException.class)
    public void rotatingWithANullArgumentThrowNullArgumentException ()
            throws Exception {

        sl.rotate(null, Math.PI / 2);
    }

    @Test
    public void testScale () throws Exception {

        SemiLine sline = null;
        SemiLine expected = null;

        sline = new SemiLine(0, 0, 10, 10);
        sline.scale(point, 0.8);
        expected = new SemiLine(0, 0, 10, 10);
        assertEquals("Semi line should be as expected", expected, sline);

        sline = new SemiLine(2, 2, 12, 12);
        sline.scale(point, 0.5);
        expected = new SemiLine(1, 1, 12, 12);
        assertEquals("Semi line should be as expected", expected, sline);

        sline = new SemiLine(0, 0, 12, 12);
        sline.scale(new Point(0, 12), 0.5);
        expected = new SemiLine(0, 6, 6, 12);
        assertEquals("Semi line should be as expected", expected, sline);
    }

    @Test(expected = NullArgumentException.class)
    public void scalingFromNullPointShouldThrowNullArgumentException ()
            throws Exception {

        sl.scale(null, -0.5);
    }

    @Test(expected = IllegalActionException.class)
    public void scalingWithInvalidArgumentShouldThrowInvalidArgumentException ()
            throws Exception {

        SemiLine sline = new SemiLine(2, 2, 12, 12);
        sline.scale(point, -0.5);
    }
}
