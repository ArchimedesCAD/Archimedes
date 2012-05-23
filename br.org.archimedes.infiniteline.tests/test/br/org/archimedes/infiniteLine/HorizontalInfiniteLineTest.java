/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Marcos P. Moreti - initial API and implementation<br>
 * Paulo L. Huaman, Hugo Corbucci - later contributions<br>
 * Bruno Klava and Bruno da Hora - later contributions<br>
 * <br>
 * This file was created on 2007/04/12, 08:50:12, by Marcos P. Moreti.<br>
 * It is part of package br.org.archimedes.infiniteLine on the br.org.archimedes.infiniteline.tests
 * project.<br>
 */

package br.org.archimedes.infiniteLine;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Horizontal infinite line tests
 * 
 * @author Marcos P. Moreti
 */
public class HorizontalInfiniteLineTest extends InfiniteLineTestCase {

    private InfiniteLine line;

    private final double COS_45 = Math.sqrt(2.0) / 2.0;


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
     * @throws Exception
     *             Thrown in case of problems
     */
    private void testInfiniteLineCase (double x1, double y1, double x2, double y2) throws Exception {

        line = new InfiniteLine(x1, y1, x2, y2);

        Assert.assertNotNull("The object Line is null!", line);

        testContains(x1, y1);

        testContains(x2, y2);

        testContains((x1 + x2) / 2, (y1 + y2) / 2); // mid point

        if ( !(Math.abs(x2 - x1) <= Constant.EPSILON && Math.abs(y2 - y1) <= Constant.EPSILON)) {
            testContains(2 * x1 - x2, 2 * y1 - y2); // outside of bounders

            testContains(2 * x2 - x1, 2 * y2 - y1); // outside of bounders

            testNotContains(x1 + (y1 - y2), y1 + (x2 - x1));

            testNotContains(x1 - (y1 - y2), y1 - (x2 - x1));
        }
        else {
            testContains(x1 + 1, y1);
            testContains(x1 - 1, y1);
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
                    + "does not belong to the infinite line", line.contains(point));
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
            Assert.assertFalse("The point " + point.toString() + " belongs to the infinite line",
                    line.contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * Safely projects a point on the line. Fails if any exception is thrown.
     * 
     * @param xline
     *            The line
     * @param toProject
     *            The point to project
     * @return The projection
     */
    protected Point getSafeProjectionOf (InfiniteLine xline, Point toProject) {

        Point projection = null;
        try {
            projection = xline.getProjectionOf(toProject);
        }
        catch (NullArgumentException e) {
            Assert.fail("Somebody gave me a null point to project!");
        }
        return projection;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.infiniteLine.InfiniteLineTestCase#makeLine()
     */
    @Override
    protected InfiniteLine makeLine () throws Exception {
    
        return new InfiniteLine(0, 10, 10, 10);
    }

    @Test
    public void testCreateInfiniteLine () throws Exception {

        /* simple cases */
        testInfiniteLineCase(0, 1, 2, 3);
        testInfiniteLineCase(0, 1, -1, 0);

        /* oblique cases */
        testInfiniteLineCase(0, 2, 4, 5);
        testInfiniteLineCase(0, 2, 4, 3);
        testInfiniteLineCase(0, 2, 4, 1);
        testInfiniteLineCase(0, 2, 4, -1);

        /* horizontal lines */
        testInfiniteLineCase(0, 0, 5, 0);
        testInfiniteLineCase(0, 1, 5, 1);

        /* vertical lines */
        testInfiniteLineCase(0, 0, 0, 4);
        testInfiniteLineCase(1, 2, 1, 4);
    }

    // TODO Horizontal infinite line contains points on same y

    @Test
    public void testEquals () throws Exception {

        // Horizontal line
        InfiniteLine xLine = new InfiniteLine(2.0, 1.0, 1.0, 1.0);

        InfiniteLine xLine1 = new InfiniteLine(2.0, 2.0, -1.0, 2.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine1));
        InfiniteLine xLine2 = new InfiniteLine(3.0, 1.0, 4.0, 1.0);
        Assert.assertTrue("Should be equal", xLine.equals(xLine2));
        InfiniteLine xLine3 = new InfiniteLine(0.0, 0.0, 1.0, 1.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine3));

        // Other line
        xLine = new InfiniteLine(0.0, 0.0, 1.0, 1.0);

        xLine1 = new InfiniteLine(2.0, 2.0, -1.0, 2.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine1));
        xLine2 = new InfiniteLine(2.2, 2.2, -1.0, -1.0);
        Assert.assertTrue("Should be equal", xLine.equals(xLine2));
        xLine3 = new InfiniteLine(0.1, 0.0, 1.0, 1.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine3));
    }

    @Test
    public void testIsPositiveDirection () throws Exception {

        // Horizontal line
        InfiniteLine xLine = new InfiniteLine(1.0, 1.0, 2.0, 1.0);
        Point point1 = new Point( -2.0, 1.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point1));
        Point point2 = new Point(1.5, 3.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point2));
        Point point3 = new Point(0.5, 0.5);
        Assert.assertFalse("The infinite line should be right of this point", xLine
                .isPositiveDirection(point3));

        // Ascending line
        xLine = new InfiniteLine(1.0, 1.0, 2.0, 2.0);
        point1 = new Point(3.0, 3.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point1));
        point2 = new Point(0.5, 1.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point2));
        point3 = new Point(1.5, 1.0);
        Assert.assertFalse("The infinite line should be right of this point", xLine
                .isPositiveDirection(point3));

        // Descendig line
        xLine = new InfiniteLine(1.0, 1.0, 0.0, 2.0);
        point1 = new Point(3.0, -1.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point1));
        point2 = new Point(0.0, 3.0);
        Assert.assertFalse("The infinite line should be right of this point", xLine
                .isPositiveDirection(point2));
        point3 = new Point(2.0, -1.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point3));
    }

    /**
     * Tests infinity line's copy. TODO Verificar testes com erros.
     * 
     * @throws Exception
     */
    @Test
    public void testCopy () throws Exception {

        InfiniteLine xLine;
        InfiniteLine expected;
        InfiniteLine copiedXLine;

        // double sqr2 = Math.sqrt(2);
        // Point initialPoint = null;
        // Vector direction = null;

        // Horizontal line
        xLine = new InfiniteLine(0.0, 0.0, 1.0, 0.0);
        expected = new InfiniteLine(0.0, 0.0, 1.0, 0.0);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(0.0);
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);

        expected = new InfiniteLine(0.0, 0.5, 1.0, 0.5);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(0.5);
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);

        /*
         * expected = new InfiniteLine(0.0, -0.5, 1.0, -0.5); copiedXLine =
         * safeCloneWithDistance(xLine, -0.5); Assert.assertEquals("The copied xLine and the
         * original should be the same.", expected, copiedXLine);
         */

        // Ascending line
        xLine = new InfiniteLine(0.0, 0.0, 1.0, 1.0);
        expected = new InfiniteLine(0.0, 0.0, 1.0, 1.0);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(0.0);
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);

        // direction = new Vector(new Point(1.0, 1.0));

        /*
         * initialPoint = new Point( -0.5 / sqr2, 0.5 / sqr2); expected = new
         * InfiniteLine(initialPoint, direction); copiedXLine = safeCloneWithDistance(xLine, 0.5);
         * Assert.assertEquals("The copied xLine and the original should be the same.", expected,
         * copiedXLine);
         */

        /*
         * initialPoint = new Point(0.5 / sqr2, -0.5 / sqr2); expected = new
         * InfiniteLine(initialPoint, direction); copiedXLine = safeCloneWithDistance(xLine, -0.5);
         * Assert.assertEquals("The copied xLine and the original should be the same.", expected,
         * copiedXLine);
         */

        // Descending line
        xLine = new InfiniteLine(0.0, 0.0, -1.0, 1.0);
        expected = new InfiniteLine(0.0, 0.0, -1.0, 1.0);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(0.0);
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);

        /*
         * initialPoint = new Point( -0.5 / sqr2, -0.5 / sqr2); direction = new Vector(new Point(
         * -1.0, 1.0)); expected = new InfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, 0.5); Assert.assertEquals("The copied xLine and the original
         * should be the same.", expected, copiedXLine); initialPoint = new Point(0.5 / sqr2, 0.5 /
         * sqr2); expected = new InfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, -0.5); Assert.assertEquals("The copied xLine and the
         * original should be the same.", expected, copiedXLine);
         */
    }

    @Test
    public void testProjection () throws Exception {

        // Line going like this: \
        InfiniteLine xLine = new InfiniteLine(100, 0, -50, 150);
        Point toProject = new Point( -40, 150);
        Point expected = new Point( -45, 145);
        Point projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Same line, same expected, simetric point
        toProject = new Point( -50, 140);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Project (0,0)
        toProject = new Point(0, 0);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Project point of the line
        toProject = new Point(50, 50);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Line to the other direction: /
        xLine = new InfiniteLine( -10, 0, 0, 20);
        toProject = new Point(5, 5);
        expected = new Point( -5, 10);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Horizontal line
        xLine = new InfiniteLine(0, 20, 100, 20);
        toProject = new Point(20, 10);
        expected = new Point(20, 20);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        toProject = new Point(20, 30);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Test exception
        try {
            xLine.getProjectionOf(null);
            Assert.fail("Should not reach this point");
        }
        catch (NullArgumentException e) {
            // This is excpected behavior
        }
    }

    @Test
    public void testRotate () throws Exception {

        // Line going like this: / in all cases
        InfiniteLine line = new InfiniteLine(0, 0, 100, 0);
        InfiniteLine expected;

        try {
            line.rotate(null, Math.PI / 2);
            Assert.fail("Should throw a exception.");
        }
        catch (NullArgumentException e) {}

        safeRotate(line, new Point(0, 0), Math.PI / 2);
        expected = new InfiniteLine(0, 0, 0, 100);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = new InfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(50, 0), Math.PI / 2);
        expected = new InfiniteLine(50, -50, 50, 50);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = new InfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(100, 0), Math.PI / 2);
        expected = new InfiniteLine(100, -100, 100, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = new InfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), Math.PI / 4);
        expected = new InfiniteLine(100 * COS_45, 100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = new InfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), -Math.PI / 4);
        expected = new InfiniteLine(100 * COS_45, -100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);
        
        Point p1 = new Point(1,1);
        Point p2 = new Point(0,2);
        line = new InfiniteLine(p1, p2);
        safeRotate(line, new Point(0, 0), Math.PI / 4);
        expected = new InfiniteLine(0, Math.sqrt(2), Math.sqrt(2), Math.sqrt(2));
        Assert.assertEquals("point should be equal.", expected.getInitialPoint(), new Point(0,Math.sqrt(2)));
        Assert.assertEquals("point should be equal.", expected.getEndingPoint(), new Point(Math.sqrt(2),Math.sqrt(2)));
        Assert.assertEquals("Line should be equals.", expected, line);
        
    }

    @Test
    public void testScale () throws Exception {

        InfiniteLine xline = null;
        InfiniteLine expected = null;
        try {
            // Line like /
            xline = new InfiniteLine(2, 2, 12, 12);
            xline.scale(new Point(0, 0), 0.8);
            expected = new InfiniteLine(2, 2, 12, 12);
            Assert.assertEquals("Infinite line should be as expected", expected, xline);

            // Vertical line
            xline = new InfiniteLine(10, 0, 10, 15);
            xline.scale(new Point(0, 0), 0.5);
            expected = new InfiniteLine(5, 0, 5, 15);
            Assert.assertEquals("Infinite line should be as expected", expected, xline);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw any exception");
        }

        xline = new InfiniteLine(2, 2, 12, 12);
        try {
            xline.scale(new Point(0, 0), -0.5);
            Assert.fail("Should throw IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (IllegalActionException e) {
            // It's OK
        }

        try {
            xline.scale(null, 0.5);
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
    public void testInfiniteLineCreationBoundaryRectangle () {

        Rectangle boundary = testedLine.getCreationBoundaryRectangle();
        assertEquals(boundary.getLowerLeft().getX(), 0, Constant.EPSILON);
        assertEquals(boundary.getLowerLeft().getY(), 10, Constant.EPSILON);
        assertEquals(boundary.getUpperRight().getX(), 10, Constant.EPSILON);
        assertEquals(boundary.getUpperRight().getY(), 10, Constant.EPSILON);
    }
}
