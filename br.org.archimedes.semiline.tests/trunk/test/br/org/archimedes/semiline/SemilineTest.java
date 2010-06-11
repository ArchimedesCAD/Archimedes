/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Bruno Klava and Luiz Real - changed behavior of its boundary rectangle<br>
 * Ricardo Sider and Luiz Real - reverted the behavior of its boundary rectangle<br>
 * Bruno Klava and Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.semiline on the br.org.archimedes.semiline.tests project.<br>
 */

package br.org.archimedes.semiline;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.SquarePoint;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SemilineTest extends Tester {

    private Point point;

    private Point otherPoint;

    private Semiline sl;


    @Before
    public void setUp () throws Exception {

        point = new Point(0, 0);
        otherPoint = new Point(100, 100);
        sl = new Semiline(point, otherPoint);
    }

    @Test(expected = InvalidArgumentException.class)
    public void doesNotCreateWhenPassingInvalidPoints () throws Exception {

        new Semiline(point, point);
    }

    @Test(expected = InvalidArgumentException.class)
    public void doesNotCreateWhenPassingInvalidCoordinates () throws Exception {

        new Semiline(0, 0, 0, 0);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingNullPoints () throws Exception {

        new Semiline(null, null);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingPointAndNull () throws Exception {

        new Semiline(point, null);
    }

    @Test(expected = NullArgumentException.class)
    public void doesNotCreateWhenPassingNullAndPoint () throws Exception {

        new Semiline(null, point);
    }

    @Test
    public void testContainsWorksWithValidPoints () throws Exception {

        Semiline sline = new Semiline(0, 0, -4, 0);
        Point p1 = new Point( -5, 0);
        Point p2 = new Point( -4, 0);
        Point p3 = new Point( -2, 0);
        Point p4 = new Point(0, 0);
        Point p5 = new Point(2, 0);
        Point p6 = new Point(5, 5);
        Point p7 = new Point(5, -5);

        assertTrue("Should contain the point " + p1.toString(), sline.contains(p1));
        assertTrue("Should contain the point " + p2.toString(), sline.contains(p2));
        assertTrue("Should contain the point " + p3.toString(), sline.contains(p3));
        assertTrue("Should contain the point " + p4.toString(), sline.contains(p4));
        assertFalse("Should not contain the point " + p5.toString(), sline.contains(p5));
        assertFalse("Should not contain the point " + p6.toString(), sline.contains(p6));
        assertFalse("Should not contain the point " + p7.toString(), sline.contains(p7));

        sline = new Semiline(0, 0, 0, 4);
        p1 = new Point(0, 5);
        p2 = new Point(0, 4);
        p3 = new Point(0, 2);
        p4 = new Point(0, 0);
        p5 = new Point(0, -2);
        p6 = new Point(5, 5);
        p7 = new Point( -5, 5);

        assertTrue("Should contain the point " + p1.toString(), sline.contains(p1));
        assertTrue("Should contain the point " + p2.toString(), sline.contains(p2));
        assertTrue("Should contain the point " + p3.toString(), sline.contains(p3));
        assertTrue("Should contain the point " + p4.toString(), sline.contains(p4));
        assertFalse("Should not contain the point " + p5.toString(), sline.contains(p5));
        assertFalse("Should not contain the point " + p6.toString(), sline.contains(p6));
        assertFalse("Should not contain the point " + p7.toString(), sline.contains(p7));
    }

    @Test(expected = NullArgumentException.class)
    public void containsThrowsNullArgumentIfReceivedANullArgument () throws Exception {

        sl.contains(null);
    }

    @Test
    public void testEquals () throws InvalidArgumentException {

        Semiline equal = new Semiline(0, 0, 50, 50);
        Semiline notEqual = new Semiline(0, 0, 50, 100);
        Object someElement = new Line(0, 0, 100, 100);

        assertTrue("Should be the same semi line", sl.equals(sl));
        assertTrue("Should be the same semi line", sl.equals(equal));
        assertFalse("Semi lines should not be the same", sl.equals(notEqual));
        assertFalse("Objects should not be the same", sl.equals(someElement));
    }

    @Test
    public void testMove () throws Exception {

        sl.move(10, -20);
        Semiline expected = new Semiline(10, -20, 60, 30);
        assertTrue("Semi lines should be the same", expected.equals(sl));
    }

    @Test
    public void testNotContainable () {

        assertNull(sl.getBoundaryRectangle());
        assertFalse(sl.isInside(new Rectangle( -10, -10, 1000, 1000)));
    }

    @Test
    public void testIsPositiveDirection () throws Exception {

        Semiline sline;

        /* horizontal line */
        sline = new Semiline(0.0, 0.0, 1.0, 0.0);
        Point point = new Point(0.0, 1.0);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        point = new Point(0.0, -1.0);
        assertFalse("The point should be right of the line", getDirection(sline, point));

        point = new Point(0.5, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        /* vertical line */
        sline = new Semiline(0.0, 0.0, 0.0, 1.0);
        point = new Point( -1.0, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        point = new Point(1.0, 0.0);
        assertFalse("The point should be right of the line", getDirection(sline, point));

        point = new Point(0.0, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        /* oblique line */
        sline = new Semiline(0.0, 0.0, 1.0, 1.0);
        point = new Point(0.0, 1.0);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        point = new Point(1.0, 0.0);
        assertFalse("The point should be right of the line", getDirection(sline, point));

        point = new Point(0.5, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        sline = new Semiline(0.0, 0.0, -1.0, 1.0);
        point = new Point(0.0, 1.0);
        assertFalse("The point should be right of the line", getDirection(sline, point));

        point = new Point( -1.0, 0.0);
        assertTrue("The point should be left of the line", getDirection(sline, point));

        point = new Point( -0.5, 0.5);
        assertTrue("The point should be left of the line", getDirection(sline, point));
    }

    /**
     * @param sline
     *            The semiline to be tested
     * @param point
     *            The point to be tested
     * @return true if the point is at a positive direction to the line
     */
    private boolean getDirection (Semiline sline, Point point) {

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
        Semiline expected = new Semiline( -5, 5, 95, 105);
        assertEquals("Should be the same line.", expected, copied);
        assertFalse("Should not be the same reference.", copied == sl);

        copied = sl.cloneWithDistance( -Math.sqrt(50));
        expected = new Semiline(5, -5, 105, 95);
        assertEquals("Should be the same line.", expected, copied);
        assertFalse("Should not be the same reference.", copied == sl);
    }
    
    @Test
    public void clonningShouldKeepSameLayer () throws Exception {

        Layer layer = new Layer(new Color(0,200,20), "layer", LineStyle.CONTINUOUS, 1);
        sl.setLayer(layer);
        Element clone = sl.clone();
        
        assertEquals(layer, clone.getLayer());
    }

    @Test
    public void testReferencePoints () throws Exception {

        Collection<ReferencePoint> references = sl.getReferencePoints(new Rectangle( -10, -10,
                -100, -100));
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
        Semiline sline = new Semiline(100, 0, 0, 100);
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
        sline = new Semiline( -10, 0, 0, 20);
        toProject = new Point(5, 5);
        expected = new Point( -5, 10);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Horizontal line
        sline = new Semiline(0, 20, 100, 20);
        toProject = new Point(20, 10);
        expected = new Point(20, 20);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        toProject = new Point(20, 30);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        // Vertical line
        sline = new Semiline(50, 20, 50, 100);
        toProject = new Point(25, 70);
        expected = new Point(50, 70);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);

        toProject = new Point(75, 70);
        projection = getSafeProjectionOf(sline, toProject);
        assertPointsTheSame(expected, projection);
    }

    @Test(expected = NullArgumentException.class)
    public void projectionOfNullShouldThrowNullArgumentException () throws Exception {

        sl.getProjectionOf(null);
    }

    /**
     * Safely projects a point on the semi line. Fails if any exception is thrown.
     * 
     * @param line
     *            The line
     * @param toProject
     *            The point to project
     * @return The projection
     */
    private Point getSafeProjectionOf (Semiline line, Point toProject) {

        Point projection = null;
        try {
            projection = line.getProjectionOf(toProject);
        }
        catch (Exception e) {
            fail("Somebody gave me a null point to project!");
        }
        return projection;
    }

    @Test
    public void testRotate () throws Exception {

        Semiline line = new Semiline(0, 0, 100, 0);
        Semiline expected;

        safeRotate(line, point, Math.PI / 2);
        expected = new Semiline(0, 0, 0, 100);
        assertEquals("Line should be equals.", expected, line);

        line = new Semiline(0, 0, 100, 0);
        safeRotate(line, new Point(50, 0), Math.PI / 2);
        expected = new Semiline(50, -50, 50, 50);
        assertEquals("Line should be equals.", expected, line);

        line = new Semiline(0, 0, 100, 0);
        safeRotate(line, new Point(100, 0), Math.PI / 2);
        expected = new Semiline(100, -100, 100, 0);
        assertEquals("Line should be equals.", expected, line);

        line = new Semiline(0, 0, 100, 0);
        safeRotate(line, point, Math.PI / 4);
        expected = new Semiline(0, 0, 100 * COS_45, 100 * COS_45);
        assertEquals("Line should be equals.", expected, line);

        line = new Semiline(0, 0, 100, 0);
        safeRotate(line, point, -Math.PI / 4);
        expected = new Semiline(0, 0, 100 * COS_45, -100 * COS_45);
        assertEquals("Line should be equals.", expected, line);
    }

    @Test(expected = NullArgumentException.class)
    public void rotatingWithANullArgumentThrowNullArgumentException () throws Exception {

        sl.rotate(null, Math.PI / 2);
    }

    @Test
    public void testScale () throws Exception {

        Semiline sline = null;
        Semiline expected = null;

        sline = new Semiline(0, 0, 10, 10);
        sline.scale(point, 0.8);
        expected = new Semiline(0, 0, 10, 10);
        assertEquals("Semi line should be as expected", expected, sline);

        sline = new Semiline(2, 2, 12, 12);
        sline.scale(point, 0.5);
        expected = new Semiline(1, 1, 12, 12);
        assertEquals("Semi line should be as expected", expected, sline);

        sline = new Semiline(0, 0, 12, 12);
        sline.scale(new Point(0, 12), 0.5);
        expected = new Semiline(0, 6, 6, 12);
        assertEquals("Semi line should be as expected", expected, sline);
    }

    @Test(expected = NullArgumentException.class)
    public void scalingFromNullPointShouldThrowNullArgumentException () throws Exception {

        sl.scale(null, -0.5);
    }

    @Test(expected = IllegalActionException.class)
    public void scalingWithInvalidArgumentShouldThrowInvalidArgumentException () throws Exception {

        Semiline sline = new Semiline(2, 2, 12, 12);
        sline.scale(point, -0.5);
    }

    @Test(expected = NullArgumentException.class)
    public void testGetNullPointsCrossing () throws Exception {

        sl.getPointsCrossing(null);
    }

    @Test
    public void testGetNoPointsCrossing () throws Exception {

        Collection<Point> pointsCrossing = sl
                .getPointsCrossing(new Rectangle( -100, -100, -50, -50));
        assertEquals(Collections.emptyList(), pointsCrossing);

        pointsCrossing = sl.getPointsCrossing(new Rectangle( -100, 0, -50, 50));
        assertEquals(Collections.emptyList(), pointsCrossing);

        pointsCrossing = sl.getPointsCrossing(new Rectangle(0, -50, 50, -100));
        assertEquals(Collections.emptyList(), pointsCrossing);

        pointsCrossing = new Semiline(new Point( -50, -100), new Point(100, -100))
                .getPointsCrossing(new Rectangle(0, 0, 100, 100));
        assertEquals(Collections.emptyList(), pointsCrossing);

        pointsCrossing = new Semiline(new Point(150, 0), new Point(150, 100))
                .getPointsCrossing(new Rectangle(0, 0, 100, 100));
        assertEquals(Collections.emptyList(), pointsCrossing);
        
        pointsCrossing = new Semiline(new Point(102, 50), new Point(150, 50))
                .getPointsCrossing(new Rectangle(0, 0, 100, 100));
        assertEquals(Collections.emptyList(), pointsCrossing);
        
        pointsCrossing = new Semiline(new Point(50, 102), new Point(50, 150))
            .getPointsCrossing(new Rectangle(0, 0, 100, 100));
        assertEquals(Collections.emptyList(), pointsCrossing);
    }

    @Test
    public void testGetOnePointCrossingVertically () throws Exception {

        // vertical semiline going up
        Collection<Point> pointsCrossing = new Semiline(new Point(0, 0), new Point(0, 100))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point(0, 50)), pointsCrossing);

        // vertical semiline going down
        pointsCrossing = new Semiline(new Point(0, 0), new Point(0, -100))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point(0, -50)), pointsCrossing);

    }

    @Test
    public void testGetOnePointCrossingHorizontally () throws Exception {

        // horizontal semiline going left
        Collection<Point> pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, 0))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point( -50, 0)), pointsCrossing);

        // horizontal semiline going right
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 0))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point(50, 0)), pointsCrossing);
    }

    @Test
    public void testGetOnePointCrossing () throws Exception {

        // above and touching the initial point
        Collection<Point> pointsCrossing = sl.getPointsCrossing(new Rectangle( -100, 0, 0, 100));
        assertEquals(Collections.singletonList(new Point(0, 0)), pointsCrossing);

        // below and touching the initial point
        pointsCrossing = sl.getPointsCrossing(new Rectangle(0, -100, 100, 0));
        assertEquals(Collections.singletonList(new Point(0, 0)), pointsCrossing);

        // on the side of the initial point and touching
        pointsCrossing = sl.getPointsCrossing(new Rectangle( -100, -100, 0, 0));
        assertEquals(Collections.singletonList(new Point(0, 0)), pointsCrossing);

        // biased semiline going up-right
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle( -50, -50, 100, 50));
        assertEquals(Collections.singletonList(new Point(50, 50)), pointsCrossing);

        // biased semiline going right-up
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 100));
        assertEquals(Collections.singletonList(new Point(50, 50)), pointsCrossing);

        // biased semiline going right-down
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, -100))
                .getPointsCrossing(new Rectangle( -50, -100, 50, 50));
        assertEquals(Collections.singletonList(new Point(50, -50)), pointsCrossing);

        // biased semiline going down-right
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, -100))
                .getPointsCrossing(new Rectangle( -100, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point(50, -50)), pointsCrossing);

        // biased semiline going down-left
        pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, -100))
                .getPointsCrossing(new Rectangle( -100, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point( -50, -50)), pointsCrossing);

        // biased semiline going left-down
        pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, -100))
                .getPointsCrossing(new Rectangle( -50, -100, 50, 50));
        assertEquals(Collections.singletonList(new Point( -50, -50)), pointsCrossing);

        // biased semiline going left-up
        pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, 100))
                .getPointsCrossing(new Rectangle( -50, -50, 50, 100));
        assertEquals(Collections.singletonList(new Point( -50, 50)), pointsCrossing);

        // biased semiline going up-left
        pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, 100))
                .getPointsCrossing(new Rectangle( -100, -50, 50, 50));
        assertEquals(Collections.singletonList(new Point( -50, 50)), pointsCrossing);
    }

    @Test
    public void testGetTwoPointsCrossingVertically () throws Exception {

        // vertical semiline crossing
        Collection<Point> expected = new LinkedList<Point>();
        Collection<Point> pointsCrossing = new Semiline(new Point(0, 0), new Point(0, 100))
                .getPointsCrossing(new Rectangle( -50, 10, 50, 60));
        expected.clear();
        expected.add(new Point(0, 10));
        expected.add(new Point(0, 60));
        assertCollectionTheSame(expected, pointsCrossing);
    }

    @Test
    public void testGetTwoPointsCrossingHorizontally () throws Exception {

        // horizontal semiline crossing
        Collection<Point> expected = new LinkedList<Point>();
        Collection<Point> pointsCrossing = new Semiline(new Point(0, 0), new Point( -100, 0))
                .getPointsCrossing(new Rectangle( -60, -50, -10, 50));
        expected.clear();
        expected.add(new Point( -60, 0));
        expected.add(new Point( -10, 0));
        assertCollectionTheSame(expected, pointsCrossing);
    }

    @Test
    public void testGetTwoPointsCrossing () throws Exception {

        Collection<Point> expected = new LinkedList<Point>();

        // Crossing through corners down-left to right-up
        Collection<Point> pointsCrossing = sl.getPointsCrossing(new Rectangle(10, 10, 60, 60));
        expected.add(new Point(10, 10));
        expected.add(new Point(60, 60));
        assertCollectionTheSame(expected, pointsCrossing);

        // Crossing through corners up-left to right-down
        pointsCrossing = new Semiline(0, 100, 100, 0).getPointsCrossing(new Rectangle(10, 10, 90,
                90));
        expected.clear();
        expected.add(new Point(10, 90));
        expected.add(new Point(90, 10));
        assertCollectionTheSame(expected, pointsCrossing);

        // biased semiline crossing down-left and up-right
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle(0, 10, 100, 60));
        expected.clear();
        expected.add(new Point(10, 10));
        expected.add(new Point(60, 60));
        assertCollectionTheSame(expected, pointsCrossing);

        // biased semiline crossing down-left and right-up
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle(0, 10, 60, 100));
        expected.clear();
        expected.add(new Point(10, 10));
        expected.add(new Point(60, 60));
        assertCollectionTheSame(expected, pointsCrossing);

        // biased semiline crossing left-down and right-up
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle(10, 0, 60, 100));
        expected.clear();
        expected.add(new Point(10, 10));
        expected.add(new Point(60, 60));
        assertCollectionTheSame(expected, pointsCrossing);

        // biased semiline crossing left-down and up-right
        pointsCrossing = new Semiline(new Point(0, 0), new Point(100, 100))
                .getPointsCrossing(new Rectangle(10, 0, 100, 60));
        expected.clear();
        expected.add(new Point(10, 10));
        expected.add(new Point(60, 60));
        assertCollectionTheSame(expected, pointsCrossing);
    }

    @Test
    public void testExtremePoints () throws Exception {

        List<Point> extremes = new LinkedList<Point>();
        extremes.add(point);

        List<Point> extremesComputed = sl.getExtremePoints();

        assertCollectionTheSame(extremes, extremesComputed);

    }
}
