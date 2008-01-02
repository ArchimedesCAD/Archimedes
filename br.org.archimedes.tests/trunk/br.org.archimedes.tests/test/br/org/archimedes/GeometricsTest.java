
package br.org.archimedes;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.
 * 
 * @author marivb
 */
public class GeometricsTest extends Tester {

    /*
     * Test method for
     * 'com.tarantulus.archimedes.Geometrics.calculateAngle(Point, Point)' and
     * 'com.tarantulus.archimedes.Geometrics.calculateAngle(double, double,
     * double, double)'
     */
    @Test
    public void testCalculateAngle () {

        assertExpectedAngle(0, 0, 0, 0, 0);
        assertExpectedAngle(0, 0, 1, 0, 0);
        assertExpectedAngle(0, 0, 0, 1, Math.PI / 2);
        assertExpectedAngle(0, 0, -1, 0, Math.PI);
        assertExpectedAngle(0, 0, 0, -1, Math.PI * 3 / 2);
        assertExpectedAngle(0, 0, 1, 1, Math.PI / 4);
        assertExpectedAngle(0, 0, -1, 1, Math.PI * 3 / 4);

        assertExpectedAngle(1, 2, 1, 2, 0);
        assertExpectedAngle(1, 2, 2, 2, 0);
        assertExpectedAngle(1, 2, 1, 3, Math.PI / 2);
        assertExpectedAngle(1, 2, 0, 2, Math.PI);
        assertExpectedAngle(1, 2, 1, 1, Math.PI * 3 / 2);
        assertExpectedAngle(1, 2, 2, 3, Math.PI / 4);
        assertExpectedAngle(1, 2, 0, 3, Math.PI * 3 / 4);
    }

    private void assertExpectedAngle (double x1, double y1, double x2,
            double y2, double angle) {

        assertEqualsWithinEpsilon(angle, Geometrics.calculateAngle(x1, y1, x2,
                y2));
        try {
            assertEqualsWithinEpsilon(angle, Geometrics.calculateAngle(
                    new Point(x1, y1), new Point(x2, y2)));
        }
        catch (NullArgumentException e) {
            Assert.fail("Shouldn't throw this exception.");
            e.printStackTrace();
        }
    }

    /**
     * @param angle
     * @param calculatedAngle
     */
    private void assertEqualsWithinEpsilon (double angle, double calculatedAngle) {

        if (Math.abs(angle - calculatedAngle) > Constant.EPSILON) {
            Assert.fail("Values " + angle + " and " + calculatedAngle
                    + " should be aproximatedly the same.");
        }
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.Geometrics.calculateDistance(Point, Point)'
     * and 'com.tarantulus.archimedes.Geometrics.calculateDistance(double,
     * double, double, double)'
     */
    @Test
    public void testCalculateDistance () {

        /* zero distance */
        assertExpectedDistance(1, 1, 1, 1, 0);

        /* non-zero distance */
        assertExpectedDistance(1, 0, 2, 0, 1);
        assertExpectedDistance(0, -0.5, 0, 2, 2.5);
        assertExpectedDistance(1, 1, 2, 2, Math.sqrt(2));
        assertExpectedDistance(2, 2, 1, 1, Math.sqrt(2));
        assertExpectedDistance( -1, -1, 0, 0, Math.sqrt(2));

        /* null points */
        try {
            Geometrics.calculateDistance((Point) null, (Point) null);
            Assert.fail("Some exception should be thrown.");
        }
        catch (NullArgumentException e) {
            // Expected exception
        }
        catch (NullPointerException e) {
            Assert.fail("I don't want THIS exception");
        }
    }

    private void assertExpectedDistance (double x1, double y1, double x2,
            double y2, double distance) {

        Assert.assertEquals(distance, Geometrics.calculateDistance(x1, y1, x2,
                y2));
        try {
            Assert.assertEquals(distance, Geometrics.calculateDistance(
                    new Point(x1, y1), new Point(x2, y2)));
        }
        catch (NullArgumentException e) {
            Assert.fail("Shouldn't throw this exception.");
            e.printStackTrace();
        }
    }

    @Test
    public void testOrthogonalizePointPoint () {

        Point point1;
        Point point2;
        Point point3;

        // Horizontal line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(1.0, 0.0);
        point3 = new Point(1.0, 0.0);

        try {
            Assert.assertTrue("The points should be equals.", point3
                    .equals(Geometrics.orthogonalize(point1, point2)));
        }
        catch (NullArgumentException e) {
            // Should not catch this exception.
            Assert.fail();
        }

        // Vertical line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(0.0, 1.0);
        point3 = new Point(0.0, 1.0);

        try {
            Assert.assertTrue("The points should be equals.", point3
                    .equals(Geometrics.orthogonalize(point1, point2)));
        }
        catch (NullArgumentException e) {
            // Should not catch this exception.
            Assert.fail();
        }

        // Oblique line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(0.75, 0.8);
        point3 = new Point(0.0, 0.8);

        try {
            Assert.assertTrue("The points should be equals.", point3
                    .equals(Geometrics.orthogonalize(point1, point2)));
        }
        catch (NullArgumentException e) {
            // Should not catch this exception.
            Assert.fail();
        }
    }

    @Test
    public void testOrthogonalizeVector () {

        Point point1;
        Point point2;

        // Horizontal line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(1.0, 0.0);

        Vector vector = new Vector(point1, point2);
        Vector expected = vector;
        Vector actual = null;
        try {
            actual = Geometrics.orthogonalize(vector);
            Assert.assertEquals("The vectors should be equal.", expected,
                    actual);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }

        // Vertical line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(0.0, 1.0);

        vector = new Vector(point1, point2);
        expected = vector;
        try {
            actual = Geometrics.orthogonalize(vector);
            Assert.assertEquals("The vectors should be equal.", expected,
                    actual);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }

        // Oblique line
        point1 = new Point(0.0, 0.0);
        point2 = new Point(0.75, 0.8);

        vector = new Vector(point1, point2);
        expected = new Vector(point1, new Point(0, 0.8));
        try {
            actual = Geometrics.orthogonalize(vector);
            Assert.assertEquals("The vectors should be equal.", expected,
                    actual);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }

        point1 = new Point(0.0, 0.0);
        point2 = new Point(0.8, 0.75);

        vector = new Vector(point1, point2);
        expected = new Vector(point1, new Point(0.8, 0.0));
        try {
            actual = Geometrics.orthogonalize(vector);
            Assert.assertEquals("The vectors should be equal.", expected,
                    actual);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not catch this exception.");
        }
    }

    @Test
    public void testGetMeanPoint () {

        Collection<Point> points = new ArrayList<Point>();
        Point result = null;
        Point expected = new Point(1, 1);
        try {
            result = Geometrics.getMeanPoint(points);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            Assert.fail();
        }
        Assert.assertNull("The point should be null", result);

        // two points
        points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 2));

        result = null;
        expected = new Point(1, 1);
        try {
            result = Geometrics.getMeanPoint(points);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            Assert.fail();
        }
        Assert.assertEquals("The point should be (1,1)", expected, result);

        // five points
        points = new ArrayList<Point>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(4, 1));
        points.add(new Point(3, 1));

        result = null;
        expected = new Point(2, 1);
        try {
            result = Geometrics.getMeanPoint(points);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            Assert.fail();
        }
        Assert.assertEquals("The point should be " + expected.getX() + ","
                + expected.getY(), expected, result);

    }

    @Test
    public void testCalculatePoint () {

        Point startPoint = null;
        Point directionPoint = null;
        double distance = 50;
        Point createdPoint;
        try {
            createdPoint = Geometrics.calculatePoint(distance, startPoint,
                    directionPoint);
            Assert.fail("This should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {

        }

        startPoint = new Point(0, 1);
        directionPoint = new Point( -10, 1);
        createdPoint = safeCalculatePoint(distance, startPoint, directionPoint);

        assertWithinDelta("The x coordinate should be -50 but was "
                + createdPoint.getX(), -50.0, createdPoint.getX());
        assertWithinDelta("The y coordinate should be 1 but was "
                + createdPoint.getY(), 1.0, createdPoint.getY());

        startPoint = new Point(0, 0);
        directionPoint = new Point(60, 80);
        distance = 50;
        createdPoint = safeCalculatePoint(distance, startPoint, directionPoint);

        assertWithinDelta("The x coordinate should be 30 but was "
                + createdPoint.getX(), 30.0, createdPoint.getX());
        assertWithinDelta("The y coordinate should be 40 but was "
                + createdPoint.getY(), 40.0, createdPoint.getY());

        startPoint = new Point(0, 0);
        directionPoint = new Point( -10, -10);
        distance = Math.sqrt(2);
        createdPoint = safeCalculatePoint(distance, startPoint, directionPoint);

        assertWithinDelta("The x coordinate should be -1 but was "
                + createdPoint.getX(), -1, createdPoint.getX());
        assertWithinDelta("The y coordinate should be -1 but was "
                + createdPoint.getY(), -1, createdPoint.getY());

        startPoint = new Point(0, 0);
        directionPoint = new Point(10, -10);
        createdPoint = safeCalculatePoint(distance, startPoint, directionPoint);

        assertWithinDelta("The x coordinate should be 1 but was "
                + createdPoint.getX(), 1, createdPoint.getX());
        assertWithinDelta("The y coordinate should be -1 but was "
                + createdPoint.getY(), -1, createdPoint.getY());

        try {
            Geometrics.calculatePoint(null, 10, 20.0);
            Assert.fail("This should throw an exception");
        }
        catch (NullArgumentException e) {}

    }

    private Point safeCalculatePoint (double distance, Point startPoint,
            Point directionPoint) {

        Point point = null;
        try {
            point = Geometrics.calculatePoint(distance, startPoint,
                    directionPoint);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }
        return point;
    }

    @Test
    public void testGetCircumcenter () {

        Point a = null, b = null, c = null, expected;
        try {
            Geometrics.getCircumcenter(a, b, c);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw this exception.");
        }
        catch (NullArgumentException e) {}

        a = new Point(0, 1);
        b = new Point(0, 2);
        try {
            Geometrics.getCircumcenter(a, b, c);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw this exception.");
        }
        catch (NullArgumentException e) {}

        c = new Point(0, 3);
        try {
            Geometrics.getCircumcenter(a, b, c);
            Assert.fail("Should throw an InvalidArgumentException.");
        }
        catch (InvalidArgumentException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw this exception.");
        }

        a = new Point(0, 5);
        b = new Point( -4, -3);
        c = new Point(4, -3);
        expected = new Point(0, 0);
        getCircumcenter(a, b, c, expected);
        getCircumcenter(b, a, c, expected);

    }

    private void getCircumcenter (Point a, Point b, Point c, Point expected) {

        Point result = null;
        try {
            result = Geometrics.getCircumcenter(a, b, c);
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw any exception");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertEquals("The result should be the same as the expected",
                expected, result);
    }

    @Test
    public void testIsParallelTo () {

        // TODO IMplementar o teste
        // Line line1 = createSafeLine(0, 0, 1, 0);
        // Line line2 = createSafeLine(0, 1, 1, 1);
        //
        // Assert.assertTrue("The line should be parallel",
        // Geometrics.isParallelTo(
        // line1, line2));
        //
        // line1 = createSafeLine(0, 0, 0, 1);
        // line2 = createSafeLine(1, 0, 1, 1);
        //
        // Assert.assertTrue("The line should be parallel",
        // Geometrics.isParallelTo(
        // line1, line2));
        //
        // line1 = createSafeLine(0, 0, 5, 4);
        // line2 = createSafeLine(0, 1, 5, 5);
        //
        // Assert.assertTrue("The line should be parallel",
        // Geometrics.isParallelTo(
        // line1, line2));
    }

    @Test
    public void testRotate () {

        Point point = new Point(0, 0);
        Point reference = new Point(0, 0);
        Point expected = new Point(0, 0);
        safeRotate(point, reference, Math.PI);
        Assert.assertEquals("The point should be equal.", expected, point);

        point = new Point(50, 0);
        expected = new Point(0, 50);
        safeRotate(point, reference, Math.PI / 2);
        Assert.assertEquals("The point should be equals.", expected, point);

        point = new Point(50, 0);
        reference = new Point(25, 0);
        expected = new Point(25, 25);
        safeRotate(point, reference, Math.PI / 2);
        Assert.assertEquals("The point should be equals.", expected, point);
    }

    private void safeRotate (Point point, Point reference, double angle) {

        try {
            point.rotate(reference, angle);
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }
}
