/*
 * Created on 23/03/2006
 */

package br.org.archimedes.line;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package com.tarantulus.archimedes.tests.model.
 * 
 * @author cris e oshiro
 */
public class LineTest extends Tester {

    Line line;


    /**
     * Test the creation of a line, given two points.
     */
    @Test
    public void testCreateLine () {

        /* simple cases */

        testLineCase(0, 1, 2, 3);
        testLineCase(0, 1, -1, 0);

        /* oblique cases */

        testLineCase(0, 2, 4, 5);
        testLineCase(0, 2, 4, 3);
        testLineCase(0, 2, 4, 1);
        testLineCase(0, 2, 4, -1);

        /* horizontal lines */

        testLineCase(0, 0, 5, 0);
        testLineCase(0, 1, 5, 1);

        /* vertical lines */

        testLineCase(0, 0, 0, 4);
        testLineCase(1, 2, 1, 4);

        /* the line is just a point */

        // TODO Teste invalido. Testar que falha.
        // testLineCase(0, 0, 0, 0);
    }

    /**
     * Creates a line and tests if it was correctly created.
     * 
     * @param x1
     *            the x coordinate of the first point
     * @param y1
     *            the y coordinate of the first point
     * @param x2
     *            the x coordinate of the second point
     * @param y2
     *            the y coordinate of the second point
     */
    private void testLineCase (double x1, double y1, double x2, double y2) {

        line = createSafeLine(x1, y1, x2, y2);

        Assert.assertNotNull("The object Line is null!", line);

        Point firstPoint = line.getInitialPoint();

        Assert.assertEquals("The x coordinate of the first point is wrong!",
                firstPoint.getX(), x1);
        Assert.assertEquals("The y coordinate of the first point is wrong!",
                firstPoint.getY(), y1);

        Point secondPoint = line.getEndingPoint();

        Assert.assertEquals("The x coordinate of the second point is wrong!",
                secondPoint.getX(), x2);
        Assert.assertEquals("The y coordinate of the second point is wrong!",
                secondPoint.getY(), y2);

        testContains(x1, y1);

        testContains(x2, y2);

        testContains((x1 + x2) / 2, (y1 + y2) / 2); // mid point

        if ( !(Math.abs(x2 - x1) <= Constant.EPSILON && Math.abs(y2 - y1) <= Constant.EPSILON)) {
            testNotContains(2 * x1 - x2, 2 * y1 - y2); // outside of bounders

            testNotContains(2 * x2 - x1, 2 * y2 - y1); // outside of bounders

            testNotContains(x1 + (y1 - y2), y1 + (x2 - x1));

            testNotContains(x1 - (y1 - y2), y1 - (x2 - x1));
        }
        else {
            testNotContains(x1 + 1, y1);
            testNotContains(x1 - 1, y1);
            testNotContains(x1, y1 + 1);
            testNotContains(x1, y1 - 1);
            testNotContains(x1 + 1, y1 + 1);
            testNotContains(x1 + 1, y1 - 1);
            testNotContains(x1 - 1, y1 + 1);
            testNotContains(x1 - 1, y1 - 1);
        }
    }

    /**
     * @param x
     *            First coordinate
     * @param y
     *            Second coordinate
     */
    private void testContains (double x, double y) {

        Point point = new Point(x, y);

        try {
            Assert.assertTrue("The point " + point.toString()
                    + "does not belong to the line", line.contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * @param x
     *            First coordinate
     * @param y
     *            Second coordinate
     */
    private void testNotContains (double x, double y) {

        Point point = new Point(x, y);

        try {
            Assert.assertFalse("The point " + point.toString()
                    + " belongs to the line", line.contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * Tests all rectangle cases.
     */
    @Test
    public void testRectangleCase () {

        Rectangle rect = new Rectangle(0.0, 0.0, 1.0, 1.0);
        testIntersects(rect);

        rect = new Rectangle(1.0, 1.0, 0.0, 0.0);
        testIntersects(rect);

        rect = new Rectangle(1.0, 0.0, 0.0, 1.0);
        testIntersects(rect);

        rect = new Rectangle(0.0, 1.0, 1.0, 0.0);
        testIntersects(rect);
    }

    /**
     * Tests all possible line-rectangle intersections
     * 
     * @param rect
     */
    private void testIntersects (Rectangle rect) {

        /*
         * Two points inside the rectangle.
         */
        Line line = createSafeLine(0.5, 0.5, 0.51, 0.51);
        testNotIntersectsWithLine(rect, line);

        /*
         * Two points outside the rectangle.
         */
        line = createSafeLine( -0.1, -0.1, -0.3, -0.3);
        testNotIntersectsWithLine(rect, line);

        /*
         * Two points outside the rectangle. One point that is not the initial
         * or ending point. The point intersects the corner of the rectangle.
         */
        line = createSafeLine( -0.5, 0.5, 0.5, -0.5);
        testIntersectsWithLine(rect, line);

        /*
         * One poitn inside and other outside the rectangle.
         */
        line = createSafeLine(0.5, 0.5, -0.5, -0.5);
        testIntersectsWithLine(rect, line);

        /*
         * The segment is passing through the rectangle.
         */
        line = createSafeLine( -0.5, 0.5, 1.5, 0.5);
        testIntersectsWithLine(rect, line);

        /*
         * One point in a segment from the rectangle, and the rest of the
         * segment out from the rectangle.
         */
        line = createSafeLine(0.5, 0.0, -0.5, -0.5);
        testIntersectsWithLine(rect, line);

        /*
         * Segment is all in a segment of the rectangle.
         */
        line = createSafeLine(0.5, 0.0, 0.8, 0.0);
        testIntersectsWithLine(rect, line);

        /*
         * One point in a segment of the rectangle, and the rest inside the
         * rectangle.
         */
        line = createSafeLine(0.5, 0.0, 0.5, 0.5);
        testIntersectsWithLine(rect, line);
    }

    /**
     * @param rect
     * @param line
     */
    private void testIntersectsWithLine (Rectangle rect, Line line) {

        try {
            Assert.assertTrue("The line should intersect the rectangle !"
                    + rect.toString(), line.intersects(rect));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * @param rect
     * @param line
     */
    private void testNotIntersectsWithLine (Rectangle rect, Line line) {

        try {
            Assert.assertFalse("The line should not intersect the rectangle !"
                    + rect.toString(), line.intersects(rect));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCopy () {

        /* horizontal line */
        line = createSafeLine(0.0, 0.0, 1.0, 0.0);
        Line expected = createSafeLine(0.0, 0.5, 1.0, 0.5);
        Line copyLine = (Line) line.cloneWithDistance(0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        expected = createSafeLine(0.0, -0.5, 1.0, -0.5);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        /* vertical line */
        line = createSafeLine(0.0, 0.0, 0.0, 1.0);
        expected = createSafeLine( -0.5, 0.0, -0.5, 1.0);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        expected = createSafeLine(0.5, 0.0, 0.5, 1.0);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        /* oblique line */
        line = createSafeLine(0.0, 0.0, 1.0, 1.0);
        double sqr2 = Math.sqrt(2);
        Point initialPoint = new Point( -0.5 / sqr2, 0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        initialPoint = new Point(0.5 / sqr2, -0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert
                .assertEquals("The lines should be the same.", expected,
                        copyLine);

        line = createSafeLine(0.0, 0.0, -1.0, 1.0);
        initialPoint = new Point( -0.5 / sqr2, -0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);

        initialPoint = new Point(0.5 / sqr2, 0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);

        line = createSafeLine(0.0, 0.0, -1.0, -1.0);
        initialPoint = new Point(0.5 / sqr2, -0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);

        initialPoint = new Point( -0.5 / sqr2, 0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);

        line = createSafeLine(0.0, 0.0, 1.0, -1.0);
        initialPoint = new Point(0.5 / sqr2, 0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);

        initialPoint = new Point( -0.5 / sqr2, -0.5 / sqr2);
        expected = createSafeLine(initialPoint, line.getLength(), line
                .getAngle());
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.",
                expected, copyLine);
    }

    /**
     * @param initialPoint
     *            The initial point
     * @param length
     *            The length of the line
     * @param angle
     *            The angle relative to the X axis
     * @return The corresponding line
     */
    private Line createSafeLine (Point initialPoint, double length, double angle) {

        Line result = null;
        try {
            result = new Line(initialPoint, length, angle);
        }
        catch (NullArgumentException e) {
            Assert
                    .fail("Should not throw a NullArgumentException creating this line.");
        }
        return result;
    }

    @Test
    public void testIsPositiveDirection () {

        /* horizontal line */
        line = createSafeLine(0.0, 0.0, 1.0, 0.0);
        Point point = new Point(0.0, 1.0);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        point = new Point(0.0, -1.0);
        Assert.assertFalse("The point should be at the right of the line",
                getDirection(point));

        point = new Point(0.5, 0.0);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        /* vertical line */
        line = createSafeLine(0.0, 0.0, 0.0, 1.0);
        point = new Point( -1.0, 0.0);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        point = new Point(1.0, 0.0);
        Assert.assertFalse("The point should be at the right of the line",
                getDirection(point));

        point = new Point(0.0, 0.5);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        /* oblique line */
        line = createSafeLine(0.0, 0.0, 1.0, 1.0);
        point = new Point(0.0, 1.0);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        point = new Point(1.0, 0.0);
        Assert.assertFalse("The point should be at the right of the line",
                getDirection(point));

        point = new Point(0.5, 0.5);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        line = createSafeLine(0.0, 0.0, -1.0, 1.0);
        point = new Point(0.0, 1.0);
        Assert.assertFalse("The point should be at the right of the line",
                getDirection(point));

        point = new Point( -1.0, 0.0);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));

        point = new Point( -0.5, 0.5);
        Assert.assertTrue("The point should be at the left of the line",
                getDirection(point));
    }

    /**
     * @param point
     *            The point to find the direction
     * @return true if it is positive direction, false otherwise
     */
    private boolean getDirection (Point point) {

        boolean positiveDirection = false;
        try {
            positiveDirection = line.isPositiveDirection(point);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not thrown a NullArgumentException");
        }
        return positiveDirection;
    }

    @Test
    public void testEquals () {

        Line line1 = createSafeLine(1.5, 1.5, 2.4, 2.4);
        Line line2 = createSafeLine(1.5, 1.5, 2.4, 2.4);
        Line line3 = createSafeLine(2.4, 2.4, 1.5, 1.5);
        Line line4 = createSafeLine(1.55, 1.55, 2.4, 2.4);

        Assert.assertTrue("These lines should be equal.", line1.equals(line2));
        Assert.assertTrue("These lines should be equal.", line1.equals(line3));
        Assert.assertFalse("These lines should not be equal.", line1
                .equals(line4));
    }

    @Test
    public void testProjection () {

        // Line going like this: \
        Line line = createSafeLine(100, 0, -50, 150);
        Point toProject = new Point( -40, 150);
        Point expected = new Point( -45, 145);
        Point projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Same line, same expected, simetric point
        toProject = new Point( -50, 140);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Project (0,0)
        toProject = new Point(0, 0);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Project point of the line
        toProject = new Point(50, 50);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Line to the other direction: /
        line = createSafeLine( -10, 0, 0, 20);
        toProject = new Point(5, 5);
        expected = new Point( -5, 10);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Horizontal line
        line = createSafeLine(0, 20, 100, 20);
        toProject = new Point(20, 10);
        expected = new Point(20, 20);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        toProject = new Point(20, 30);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Vertical line
        line = createSafeLine(50, 20, 50, 100);
        toProject = new Point(25, 70);
        expected = new Point(50, 70);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        toProject = new Point(75, 70);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertEquals(expected, projection);

        // Project on nothing
        toProject = new Point(150, 0);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertNotNull("The projection should not be null", projection);

        // Project colinear point
        toProject = new Point(150, -50);
        projection = getSafeProjectionOf(line, toProject);
        Assert.assertNotNull("The projection should not be null", projection);

        // Test exception
        try {
            line.getProjectionOf(null);
            Assert.fail("Should not reach this point");
        }
        catch (NullArgumentException e) {
            // This is excpected behavior
        }
    }

    /**
     * Safely projects a point on the line. Fails if any exception is thrown.
     * 
     * @param line
     *            The line
     * @param toProject
     *            The point to project
     * @return The projection
     */
    private Point getSafeProjectionOf (Line line, Point toProject) {

        Point projection = null;
        try {
            projection = line.getProjectionOf(toProject);
        }
        catch (NullArgumentException e) {
            Assert.fail("Somebody gave me a null point to project!");
        }
        return projection;
    }

    @Test
    public void testRotate () {

        // Line going like this: \
        Line line = createSafeLine(0, 0, 100, 0);
        Line expected;

        try {
            line.rotate(null, Math.PI / 2);
            Assert.fail("Should throw a exception.");
        }
        catch (NullArgumentException e) {

        }

        safeRotate(line, new Point(0, 0), Math.PI / 2);
        expected = createSafeLine(0, 0, 0, 100);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeLine(0, 0, 100, 0);
        safeRotate(line, new Point(50, 0), Math.PI / 2);
        expected = createSafeLine(50, -50, 50, 50);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeLine(0, 0, 100, 0);
        safeRotate(line, new Point(100, 0), Math.PI / 2);
        expected = createSafeLine(100, -100, 100, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), Math.PI / 4);
        expected = createSafeLine(100 * COS_45, 100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), -Math.PI / 4);
        expected = createSafeLine(100 * COS_45, -100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);
    }

    /**
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return The corresponding line
     */
    private Line createSafeLine (double i, double j, double k, double l) {

        Line result = null;
        try {
            result = new Line(new Point(i, j), new Point(k, l));
        }
        catch (NullArgumentException e) {
            Assert
                    .fail("Should not thrown a NullArgumentException creating a line.");
        }
        catch (InvalidArgumentException e) {
            Assert
                    .fail("Should not thrown an InvalidArgumentException creating a line.");
        }
        return result;
    }

    @Test
    public void testScale () {

        Line line = null;
        Line expected = null;
        try {
            line = createSafeLine(0, 0, 10, 10);
            line.scale(new Point(0, 0), 0.8);
            expected = createSafeLine(0, 0, 8, 8);
            Assert.assertEquals(expected, line);

            line = createSafeLine(2, 2, 12, 12);
            line.scale(new Point(2, 2), 0.8);
            expected = createSafeLine(2, 2, 10, 10);
            Assert.assertEquals(expected, line);

            line = createSafeLine(2, 2, 12, 12);
            line.scale(new Point(0, 0), 0.5);
            expected = createSafeLine(1, 1, 6, 6);
            Assert.assertEquals(expected, line);

            line = createSafeLine(3, 3, 13, 13);
            line.scale(new Point(2, 2), 2);
            expected = createSafeLine(4, 4, 24, 24);
            Assert.assertEquals(expected, line);

            line = createSafeLine(0, 0, 12, 12);
            line.scale(new Point(0, 12), 0.5);
            expected = createSafeLine(0, 6, 6, 12);
            Assert.assertEquals(expected, line);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw any exception");
        }

        line = createSafeLine(2, 2, 12, 12);
        try {
            line.scale(new Point(0, 0), -0.5);
            Assert.fail("Should throw IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (IllegalActionException e) {
            // It's OK
        }

        try {
            line.scale(null, 0.5);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // It's OK
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw IllegalActionException");
        }
    }
}
