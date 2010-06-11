/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cristiane M. Sato - initial API and implementation<br>
 * Marcio Oshiro, Hugo Corbucci, Mariana V. Bravo, Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2006/03/23, 10:09:12, by Cristiane M. Sato.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line.tests project.<br>
 */

package br.org.archimedes.line;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Belongs to package br.org.archimedes.line.
 * 
 * @author Cristiane M. Sato and Marcio Oshiro
 */
public class LineTest extends Tester {

    private Line line;


    @Test
    public void canCreateALineFrom4Coordinates () {

        /* simple cases */

        testLineCreation(0, 1, 2, 3);
        testLineCreation(0, 1, -1, 0);

        /* oblique cases */

        testLineCreation(0, 2, 4, 5);
        testLineCreation(0, 2, 4, 3);
        testLineCreation(0, 2, 4, 1);
        testLineCreation(0, 2, 4, -1);

        /* horizontal lines */

        testLineCreation(0, 0, 5, 0);
        testLineCreation(0, 1, 5, 1);

        /* vertical lines */
        testLineCreation(0, 0, 0, 4);
        testLineCreation(1, 2, 1, 4);
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
    private void testLineCreation (double x1, double y1, double x2, double y2) {

        line = createSafeLine(x1, y1, x2, y2);

        Assert.assertNotNull("The object Line is null!", line);

        Point firstPoint = line.getInitialPoint();

        Assert.assertTrue(Double.compare(firstPoint.getX(), x1) == 0);
        Assert.assertTrue(Double.compare(firstPoint.getY(), y1) == 0);

        Point secondPoint = line.getEndingPoint();

        Assert.assertTrue(Double.compare(secondPoint.getX(), x2) == 0);
        Assert.assertTrue(Double.compare(secondPoint.getY(), y2) == 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void cantCreateALineFromEqualPointsPassingCoordinates () throws Exception {

        new Line(0, 0, 0, 0);
    }

    @Test(expected = NullArgumentException.class)
    public void cantCreateALineFromPassingBothNullPoints () throws Exception {

        new Line(null, null);
    }

    @Test(expected = NullArgumentException.class)
    public void cantCreateALineFromPassingTheFirstPointNull () throws Exception {

        new Line(null, new Point(0, 0));
    }

    @Test(expected = NullArgumentException.class)
    public void cantCreateALineFromPassingTheSecondPointNull () throws Exception {

        new Line(new Point(0, 0), null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void cantCreateALineFromEqualPointsPassingPoints () throws Exception {

        new Line(new Point(0, 0), new Point(0, 0));
    }

    // TODO Test creation of line from Point,Point constructor

    // TODO Test a line contains the points in it and not others

    // TODO Test cloning a line makes an equal line with

    @Test
    public void lineContainsPointsBetweenItsInitialAndEndingPoints () throws Exception {

        testContainsPointsButNotOutside(1, 1, 5, 5);
        testContainsPointsButNotOutside(0, 1, 0, 5);
        testContainsPointsButNotOutside(1, 0, 5, 0);
    }

    /**
     * @param x1
     *            X coordinate of point 1
     * @param y1
     *            Y coordinate of point 1
     * @param x2
     *            X coordinate of point 2
     * @param y2
     *            Y coordinate of point 2
     */
    private void testContainsPointsButNotOutside (int x1, int y1, int x2, int y2) {

        line = createSafeLine(x1, y1, x2, y2);
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
            Assert.assertTrue("The point " + point.toString() + "does not belong to the line", line
                    .contains(point));
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
            Assert.assertFalse("The point " + point.toString() + " belongs to the line", line
                    .contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void clonesWithDistanceMakingParallelLineToCorrectSideAndDistance () {

        /* horizontal line */
        line = createSafeLine(0.0, 0.0, 1.0, 0.0);
        Line expected = createSafeLine(0.0, 0.5, 1.0, 0.5);
        Line copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        expected = createSafeLine(0.0, -0.5, 1.0, -0.5);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        /* vertical line */
        line = createSafeLine(0.0, 0.0, 0.0, 1.0);
        expected = createSafeLine( -0.5, 0.0, -0.5, 1.0);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        expected = createSafeLine(0.5, 0.0, 0.5, 1.0);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        /* oblique line */
        line = createSafeLine(0.0, 0.0, 1.0, 1.0);
        double movement = 0.5 / Math.sqrt(2);
        expected = createSafeLine( -movement, movement, 1 - movement, 1 + movement);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        expected = createSafeLine(movement, -movement, 1 + movement, 1 - movement);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be the same.", expected, copyLine);

        line = createSafeLine(0.0, 0.0, -1.0, 1.0);
        expected = createSafeLine( -movement, -movement, -1 - movement, 1 - movement);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);

        expected = createSafeLine(movement, movement, -1 + movement, 1 + movement);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);

        line = createSafeLine(0.0, 0.0, -1.0, -1.0);
        expected = createSafeLine(movement, -movement, -1 + movement, -1 - movement);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);

        expected = createSafeLine( -movement, movement, -1 - movement, -1 + movement);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);

        line = createSafeLine(0.0, 0.0, 1.0, -1.0);
        expected = createSafeLine(movement, movement, 1 + movement, -1 + movement);
        copyLine = (Line) line.cloneWithDistance(0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);

        expected = createSafeLine( -movement, -movement, 1 - movement, -1 - movement);
        copyLine = (Line) line.cloneWithDistance( -0.5);
        Assert.assertEquals("The lines should be aproximately the same.", expected, copyLine);
    }
    
    @Test
    public void cloningKeepsSameLayer () throws Exception {
        
        Layer layer = new Layer(new Color(0,200,20), "layer", LineStyle.CONTINUOUS, 1);
        line = createSafeLine(0, 0, 1, 1);
        line.setLayer(layer);
        Element clone = line.clone();
        
        assertEquals(layer, clone.getLayer());
    }

    @Test
    public void testIsPositiveDirection () {

        /* horizontal line */
        line = createSafeLine(0.0, 0.0, 1.0, 0.0);
        Point point = new Point(0.0, 1.0);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        point = new Point(0.0, -1.0);
        Assert.assertFalse("The point should be at the right of the line", getDirection(point));

        point = new Point(0.5, 0.0);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        /* vertical line */
        line = createSafeLine(0.0, 0.0, 0.0, 1.0);
        point = new Point( -1.0, 0.0);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        point = new Point(1.0, 0.0);
        Assert.assertFalse("The point should be at the right of the line", getDirection(point));

        point = new Point(0.0, 0.5);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        /* oblique line */
        line = createSafeLine(0.0, 0.0, 1.0, 1.0);
        point = new Point(0.0, 1.0);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        point = new Point(1.0, 0.0);
        Assert.assertFalse("The point should be at the right of the line", getDirection(point));

        point = new Point(0.5, 0.5);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        line = createSafeLine(0.0, 0.0, -1.0, 1.0);
        point = new Point(0.0, 1.0);
        Assert.assertFalse("The point should be at the right of the line", getDirection(point));

        point = new Point( -1.0, 0.0);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));

        point = new Point( -0.5, 0.5);
        Assert.assertTrue("The point should be at the left of the line", getDirection(point));
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

        Assert.assertFalse("These lines should be equal.", line1.equals(null));
        Assert.assertFalse("These lines should be equal.", line1.equals(new Object()));
        Assert.assertTrue("These lines should be equal.", line1.equals(line1));
        Assert.assertTrue("These lines should be equal.", line1.equals(line2));
        Assert.assertTrue("These lines should be equal.", line1.equals(line3));
        Assert.assertFalse("These lines should not be equal.", line1.equals(line4));
    }

    // TODO Test a line can tell if it is inside a rectangle

    // TODO Test a line can be moved correctly

    // TODO Test a line knows its boundary rectangle

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
            // This is expected behavior
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

    // TODO Test a line is its own segment

    // TODO Test the reference points of a line only consider the points withint the rectangle

    // TODO Test the points of a line are the initial and ending

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
     *            X coordinate of point 1
     * @param y1
     *            Y coordinate of point 1
     * @param x2
     *            X coordinate of point 2
     * @param y2
     *            Y coordinate of point 2
     * @return The corresponding line
     */
    private Line createSafeLine (double x1, double y1, double x2, double y2) {

        Line result = null;
        try {
            result = new Line(x1, y1, x2, y2);
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not thrown an InvalidArgumentException creating a line.");
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

    @Test
    public void testExtremePoints () throws Exception {

        Point p1 = new Point(2, 8);
        Point p2 = new Point(1.7, -7);
        Line line = new Line(p1, p2);

        List<Point> extremes = new LinkedList<Point>();
        extremes.add(p1);
        extremes.add(p2);

        List<Point> extremesComputed = line.getExtremePoints();

        assertCollectionTheSame(extremes, extremesComputed);

    }
    
    @Test
    public void testPointToBeMovedForFilletWithDifferentAreaSign() throws InvalidArgumentException, NullArgumentException {
    	Line line = new Line(0, 0, 2, 0);
    	Point movedPoint = line.getPointToBeMovedForFillet(new Point(1, 1), new Point(1, 0), new Point(1.5,  1));
    	Point expectedMovedPoint = line.getEndingPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(1, -1), new Point(1, 0), new Point(1.5,  -1));
    	expectedMovedPoint = line.getEndingPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(1, 1), new Point(1, 0), new Point(.5,  1));
    	expectedMovedPoint = line.getInitialPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(1, -1), new Point(1, 0), new Point(.5,  -1));
    	expectedMovedPoint = line.getInitialPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    }
    
    @Test
    public void testPointToBeMovedForFilletWithEqualAreaSign() throws InvalidArgumentException, NullArgumentException {
    	Line line = new Line(0, 0, 2, 0);
    	Point movedPoint = line.getPointToBeMovedForFillet(new Point(2.5, 1), new Point(2.5, 0), new Point(3,  1));
    	Point expectedMovedPoint = line.getEndingPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(2.5, -1), new Point(2.5, 0), new Point(3,  -1));
    	expectedMovedPoint = line.getEndingPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(-0.5, 1), new Point(-0.5, 0), new Point(-1,  1));
    	expectedMovedPoint = line.getInitialPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    	
    	movedPoint = line.getPointToBeMovedForFillet(new Point(-0.5, -1), new Point(-0.5, 0), new Point(-1,  -1));
    	expectedMovedPoint = line.getInitialPoint();
    	assertEquals(expectedMovedPoint, movedPoint);
    }
    
    @Test 
    public void testTangencyLinePoint() throws InvalidArgumentException {
    	Line line = new Line(0, 0, 2, 0);
    	Point point1 = new Point(0, 1);
    	Point point2 = new Point(0, 0);
    	Point point3 = new Point(0, 2);

    	assertEquals(point2, line.getTangencyLinePoint(point1, point2));
    	assertEquals(line.getInitialPoint(), line.getTangencyLinePoint(point3, point3));
    	assertEquals(line.getEndingPoint(), line.getTangencyLinePoint(point2, point2));
    }


}
