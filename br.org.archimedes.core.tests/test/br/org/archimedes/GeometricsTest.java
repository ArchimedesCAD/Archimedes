/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/26, 11:32:46, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author marivb
 */
public class GeometricsTest extends Tester {

    /*
     * Test method for
     * 'br.org.archimedes.Geometrics.calculateAngle(Point, Point)' and
     * 'br.org.archimedes.Geometrics.calculateAngle(double, double,
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
     * 'br.org.archimedes.Geometrics.calculateDistance(Point, Point)'
     * and 'br.org.archimedes.Geometrics.calculateDistance(double,
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
                y2), Constant.EPSILON);
        try {
            Assert.assertEquals(distance, Geometrics.calculateDistance(
                    new Point(x1, y1), new Point(x2, y2)), Constant.EPSILON);
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
    
    @Test
    public void testBreakAnglesFromOrigin () {
    	String errorMessage = "The point should be equals";
    	Point p0 = new Point(0,0);
    	Assert.assertEquals(errorMessage, new Point(10,0), Geometrics.breakAngles(p0, new Point(10,0)));
    	Assert.assertEquals(errorMessage, new Point(5,0), Geometrics.breakAngles(p0, new Point(5, 0.525521176)));
    	Assert.assertEquals(errorMessage, new Point(5,0), Geometrics.breakAngles(p0, new Point(5, -0.525521176)));
    	Assert.assertEquals(errorMessage, new Point(-10,0), Geometrics.breakAngles(p0, new Point(-10,0)));
    	Assert.assertEquals(errorMessage, new Point(-5,0), Geometrics.breakAngles(p0, new Point(-5, 0.525521176)));
    	Assert.assertEquals(errorMessage, new Point(-5,0), Geometrics.breakAngles(p0, new Point(-5, -0.525521176)));
    	Assert.assertEquals(errorMessage, new Point(0.267949192, -1.0), Geometrics.breakAngles(p0, new Point(0.363970234, -1.0)));
    	Assert.assertEquals(errorMessage, new Point(0, 5), Geometrics.breakAngles(p0, new Point(0.525521176, 5)));
    	Assert.assertEquals(errorMessage, new Point(0, 5), Geometrics.breakAngles(p0, new Point(-0.525521176, 5)));
    	Assert.assertEquals(errorMessage, new Point(6.190658239, 10.722534603), Geometrics.breakAngles(p0, new Point(5, 10.722534603)));
    	Assert.assertEquals(errorMessage, new Point(0, -5), Geometrics.breakAngles(p0, new Point(0.525521176, -5)));
    	Assert.assertEquals(errorMessage, new Point(0, -5), Geometrics.breakAngles(p0, new Point(-0.525521176, -5)));
    	Assert.assertEquals(errorMessage, new Point(-6.190658239, 10.722534603), Geometrics.breakAngles(p0, new Point(-5, 10.722534603)));
    }
    
    @Test
    public void testBreakAnglesOutOfOrigin () {
    	String errorMessage = "The point should be equals";
    	Point o1 = new Point(1,1), o2 = new Point(3,0), o3 = new Point(-1,-1), o4 = new Point(-1,2);
    	
    	Point p = Geometrics.breakAngles(o2, new Point(2, 0.305730681));
    	Assert.assertEquals(errorMessage, new Point(3,1), Geometrics.breakAngles(o1, new Point(3,1)));
    	Assert.assertEquals(errorMessage, new Point(0,0), Geometrics.breakAngles(o2, new Point(0,0)));
    	Assert.assertEquals(errorMessage, new Point(2, 0.267949192), Geometrics.breakAngles(o2, new Point(2, 0.305730681)));
    	Assert.assertEquals(errorMessage, new Point(2, -0.267949192), Geometrics.breakAngles(o2, new Point(2, -0.305730681)));
    	Assert.assertEquals(errorMessage, new Point(4, 0.267949192), Geometrics.breakAngles(o2, new Point(4, 0.305730681)));
    	Assert.assertEquals(errorMessage, new Point(4, -0.267949192), Geometrics.breakAngles(o2, new Point(4, -0.305730681)));
    	Assert.assertEquals(errorMessage, new Point(0,-1), Geometrics.breakAngles(o3, new Point(0,-1)));
    	Assert.assertEquals(errorMessage, new Point(0,3), Geometrics.breakAngles(o4, new Point(0,3)));
    	
    	Assert.assertEquals(errorMessage, new Point(1,4), Geometrics.breakAngles(o1, new Point(1,4)));
    	Assert.assertEquals(errorMessage, new Point(-1,0), Geometrics.breakAngles(o3, new Point(-1,0)));
    	Assert.assertEquals(errorMessage, new Point(-1,0), Geometrics.breakAngles(o4, new Point(-1,0)));
    	
    }
    
    @Test
    public void testCalculateRelativeAngle() {
    	Point p0 = new Point(0,0);
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(-1,1);
    	
    	Assert.assertEquals(Math.PI/2.0, Geometrics.calculateRelativeAngle(p0, p1, p2), Constant.EPSILON);
    	Assert.assertEquals(3.0 * Math.PI/2.0, Geometrics.calculateRelativeAngle(p0, p2, p1), Constant.EPSILON);
    	Assert.assertEquals(0.0, Geometrics.calculateRelativeAngle(null, null, null), Constant.EPSILON);
    }
    
    @Test
    public void testCalculateDistancePointLine() throws NullArgumentException {
    	Point initLine, endLine;
    	Point point;
    	point = new Point(1,1);
    	initLine = new Point(1, -1);
    	endLine = new Point(-1, 1);
    	Assert.assertEquals(Math.sqrt(2.0), Geometrics.calculateDistance(initLine, endLine, point), Constant.EPSILON);
    	Assert.assertEquals(0.0, Geometrics.calculateDistance(initLine, endLine, new Point(0,0)), Constant.EPSILON);
    }
    
    @Test
    public void testGetMeanOfTwoPoints() throws NullArgumentException {
    	try {
			Assert.assertEquals(new Point(0, 0), Geometrics.getMeanPoint(new Point(0, 0), new Point(0, 0)));
			Assert.assertEquals(new Point(0.5, 0), Geometrics.getMeanPoint(new Point(1, 0), new Point(0, 0)));
			Assert.assertEquals(new Point(0, 0), Geometrics.getMeanPoint(new Point(-1, -1), new Point(1, 1)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void safeRotate (Point point, Point reference, double angle) {

        try {
            point.rotate(reference, angle);
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testIsHorizontal() {
    	Assert.assertTrue(Geometrics.isHorizontal(0.0));
    	Assert.assertTrue(Geometrics.isHorizontal(Math.PI));
    	Assert.assertFalse(Geometrics.isHorizontal(Math.PI/6));
    }
    
    @Test
    public void testCalculateDeterminant() throws NullArgumentException {
    	Assert.assertEquals(0.0, Geometrics.calculateDeterminant(new Point(1, 2), new Point(2, 2), new Point(3, 2)), Constant.EPSILON);
    	Assert.assertEquals(9.0, Geometrics.calculateDeterminant(new Point(0, 0), new Point(3, 1), new Point(0, 3)), Constant.EPSILON);
    	Assert.assertEquals(-9.0, Geometrics.calculateDeterminant(new Point(3, 1), new Point(0, 0), new Point(0, 3)), Constant.EPSILON);
    }
    
    @Test
    public void testNormalize() {
    	double r2 = Math.sqrt(2.0);
    	Assert.assertEquals(new Vector(new Point(0,0), new Point(1,0)), Geometrics.normalize(new Vector(new Point(0,0), new Point(3, 0))));
    	Assert.assertEquals(new Vector(new Point(0,0), new Point(0,1)), Geometrics.normalize(new Vector(new Point(0,0), new Point(0, 3))));
    	Assert.assertEquals(new Vector(new Point(0,0), new Point(1.0/r2,1.0/r2)), Geometrics.normalize(new Vector(new Point(0,0), new Point(1, 1))));
    	
    }
    
    @Test
    public void testCalculateArea() throws NullArgumentException, InvalidArgumentException {
    	List<Point> triangle = new ArrayList<Point>();
    	triangle.add(new Point(0,0));
    	triangle.add(new Point(2,0));
    	triangle.add(new Point(1,3));
    	Assert.assertEquals(3.0, Geometrics.calculateArea(triangle), Constant.EPSILON);
    	List<Point> square = new ArrayList<Point>();
    	square.add(new Point(0,0));
    	square.add(new Point(2,0));
    	square.add(new Point(2,2));
    	square.add(new Point(0,2));
    	Assert.assertEquals(4.0, Geometrics.calculateArea(square), Constant.EPSILON);
    	List<Point> buggie = new ArrayList<Point>();
    	buggie.add(new Point(0,0));
    	buggie.add(new Point(0,4));
    	buggie.add(new Point(3,0));
    	buggie.add(new Point(1,1));
    	Assert.assertEquals(4.5, Geometrics.calculateArea(buggie), Constant.EPSILON);
    	buggie.clear();
    	buggie.add(new Point(0,0));
    	buggie.add(new Point(0,4));
    	buggie.add(new Point(3,0));
    	buggie.add(new Point(1,-1));
    	Assert.assertEquals(7.5, Geometrics.calculateArea(buggie), Constant.EPSILON);
    }
    
    @Test
    public void testCalculatePerimeter() throws NullArgumentException, InvalidArgumentException {
    	List<Point> triangle = new ArrayList<Point>();
    	triangle.add(new Point(0,0));
    	triangle.add(new Point(2,0));
    	triangle.add(new Point(1,3));
    	Assert.assertEquals(2*Math.sqrt(10) + 2, Geometrics.calculatePerimeter(triangle), Constant.EPSILON);
    	List<Point> square = new ArrayList<Point>();
    	square.add(new Point(0,0));
    	square.add(new Point(2,0));
    	square.add(new Point(2,2));
    	square.add(new Point(0,2));
    	Assert.assertEquals(8.0, Geometrics.calculatePerimeter(square), Constant.EPSILON);
    	List<Point> buggie = new ArrayList<Point>();
    	buggie.add(new Point(0,0));
    	buggie.add(new Point(0,4));
    	buggie.add(new Point(3,0));
    	buggie.add(new Point(1,1));
    	Assert.assertEquals(9.0 + Math.sqrt(2.0) + Math.sqrt(5.0), Geometrics.calculatePerimeter(buggie), Constant.EPSILON);
    	buggie.clear();
    	buggie.add(new Point(0,0));
    	buggie.add(new Point(0,4));
    	buggie.add(new Point(3,0));
    	buggie.add(new Point(1,-1));
    	Assert.assertEquals(9.0 + Math.sqrt(2.0) + Math.sqrt(5.0), Geometrics.calculatePerimeter(buggie), Constant.EPSILON);
    }
}
