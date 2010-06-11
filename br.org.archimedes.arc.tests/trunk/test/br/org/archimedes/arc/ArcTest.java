/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * César Seragiotto, Victor D. Lopes, Mariana V. Bravo, Hugo Corbucci, Bruno Klava, Kenzo Yamada -
 * later contributions<br>
 * <br>
 * This file was created on 2007/04/09, 13:02:39, by Wellington R. Pinheiro.<br>
 * It is part of package br.org.archimedes.arc on the br.org.archimedes.arc.tests project.<br>
 */

package br.org.archimedes.arc;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ArcTest extends Tester {

    private Point ending;

    private Point middle;

    private Point initial;

    private Point center;

    private Arc arc1;

    private Arc arc2;

    private Arc arc3;
    
    private Arc arc4;

    private Arc arc;

    private Arc arc1Clone;

    private List<Point> points;


    @Before
    public void setUp () throws Exception {

        initial = new Point(1, 0);
        middle = new Point(0, 1);
        ending = new Point( -1, 0);
        center = new Point(0, 0);
        points = new LinkedList<Point>();
        points.add(initial);
        points.add(ending);
        points.add(middle);
        points.add(center);

        // They are all the same arc.
        arc1 = new Arc(ending, middle, initial);
        arc1Clone = new Arc(initial, middle, ending);
        arc2 = new Arc(ending, initial, center, false);
        arc3 = new Arc(ending, initial, center, middle);
        // This is different
        arc = new Arc(ending, initial, center, true);
    }

    @Test
    public void incorrectNullCreation () throws Exception {

        incorrectNullArcInstantiation(null, initial, center);
        incorrectNullArcInstantiation(ending, null, center);
        incorrectNullArcInstantiation(ending, initial, null);

        incorrectNullArcInstantiation(ending, initial, center, null);
        incorrectNullArcInstantiation(null, initial, center, middle);
        incorrectNullArcInstantiation(ending, null, center, middle);
        incorrectNullArcInstantiation(ending, initial, null, middle);

        incorrectNullArcInstantiation(null, initial, center, true);
        incorrectNullArcInstantiation(ending, null, center, true);
        incorrectNullArcInstantiation(ending, initial, null, true);

        incorrectNullArcInstantiation(null, initial, center, false);
        incorrectNullArcInstantiation(ending, null, center, false);
        incorrectNullArcInstantiation(ending, initial, null, false);
    }

    @Test(expected = InvalidArgumentException.class)
    public void shouldThrowInvalidArgumentWhenCreatingAnArcWithSameInitialAndEnding ()
            throws Exception {

        new Arc(ending, initial, ending, true);
    }

    @Test(expected = InvalidArgumentException.class)
    public void shouldThrowInvalidArgumentWhenCreatingAnArcWithDirectionEqualToInitialOrEnding ()
            throws Exception {

        new Arc(ending, initial, new Point(50, 0), initial);
    }

    /**
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second, Point third)
            throws InvalidArgumentException {

        try {
            new Arc(first, second, third);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    /**
     * @param fourth
     *            Fourth argument to the constructor
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second, Point third, Point fourth)
            throws InvalidArgumentException {

        try {
            new Arc(first, second, third, fourth);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    /**
     * @param fourth
     *            Fourth argument to the constructor
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second, Point third,
            boolean fourth) throws InvalidArgumentException {

        try {
            new Arc(first, second, third, fourth);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    @Test
    public void equalsAndHashCodeDontCareAboutHowArcAreCreated () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertTrue(arc1.equals(arc1));
        Assert.assertEquals(arc1.hashCode(), arc1.hashCode());

        Assert.assertTrue(arc1.equals(arc1Clone));
        Assert.assertEquals(arc1.hashCode(), arc1Clone.hashCode());

        Assert.assertTrue(arc2.equals(arc2));
        Assert.assertEquals(arc2.hashCode(), arc2.hashCode());

        Assert.assertTrue(arc3.equals(arc3));
        Assert.assertEquals(arc3.hashCode(), arc3.hashCode());

        Assert.assertTrue(arc1.equals(arc2));
        Assert.assertEquals(arc1.hashCode(), arc2.hashCode());

        Assert.assertTrue(arc1.equals(arc3));
        Assert.assertEquals(arc1.hashCode(), arc3.hashCode());

        Assert.assertTrue(arc2.equals(arc3));
        Assert.assertEquals(arc2.hashCode(), arc3.hashCode());

        Assert.assertFalse(arc1.equals(null));
        Assert.assertFalse(arc1.equals(new Object()));
        Arc difArc = new Arc(ending, initial, middle, true);
        Assert.assertFalse(arc1.equals(difArc));
        difArc = new Arc(middle, initial, center, true);
        Assert.assertFalse(arc1.equals(difArc));
        difArc = new Arc(ending, middle, center, true);
        Assert.assertFalse(arc1.equals(difArc));
        Assert.assertFalse(arc1.equals(arc));
    }

    @Test
    public void cloneAlwaysReturnsAnEqualArc () throws InvalidArgumentException,
            NullArgumentException {

        Arc clone = arc1.clone();
        Assert.assertEquals(arc1, clone);

        clone = arc2.clone();
        Assert.assertEquals(arc2, clone);

        clone = arc3.clone();
        Assert.assertEquals(arc3, clone);

        Arc arc = new Arc(new Point( -4, 0), new Point( -2, 2), new Point(0, 0));
        clone = arc.clone();
        Assert.assertEquals(arc, clone);
    }
    
    @Test
    public void cloningKeepsSameLayer () throws Exception {

        Layer layer = new Layer(new Color(0, 200, 0), "layer", LineStyle.CONTINUOUS, 1);
        arc1.setLayer(layer);
        Arc clone = arc1.clone();
        
        assertEquals(layer, clone.getLayer());
    }

    @Test
    public void radiusIsCalculatedCorrectly () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertEquals(1.0, arc1.getRadius(),Constant.EPSILON);
        Assert.assertEquals(1.0, arc2.getRadius(),Constant.EPSILON);
        Assert.assertEquals(1.0, arc3.getRadius(),Constant.EPSILON);

        Arc arc = new Arc(new Point( -2, 0), new Point(0, 2), new Point(2, 0));
        Assert.assertEquals(2.0, arc.getRadius(),Constant.EPSILON);
    }

    @Test
    public void centerPointIsCalculatedCorrectly () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertEquals(center, arc1.getCenter());
        Assert.assertEquals(center, arc2.getCenter());
        Assert.assertEquals(center, arc3.getCenter());
        Arc arc = new Arc(new Point( -4, 0), new Point( -2, 2), new Point(0, 0));
        Assert.assertEquals(new Point( -2, 0), arc.getCenter());
    }

    @Test
    public void boundaryRectangleIncludesTheWholeArc () throws InvalidArgumentException,
            NullArgumentException {

        Rectangle rect = new Rectangle( -1, 0, 1, 1);

        Assert.assertEquals(rect, arc1.getBoundaryRectangle());
        Assert.assertEquals(rect, arc2.getBoundaryRectangle());
        Assert.assertEquals(rect, arc3.getBoundaryRectangle());

        rect = new Rectangle( -1, 0, 1, -1);
        Assert.assertEquals(rect, arc.getBoundaryRectangle());

        Arc difArc = new Arc(middle, new Point(0, -1), center, true);
        rect = new Rectangle( -1, -1, 0, 1);
        Assert.assertEquals(rect, difArc.getBoundaryRectangle());

        difArc = new Arc(middle, new Point(0, -1), center, false);
        rect = new Rectangle(0, -1, 1, 1);
        Assert.assertEquals(rect, difArc.getBoundaryRectangle());
    }

    @Test
    public void referencePointsOnlyIncludeWhatIsInTheArea () throws Exception {

        Rectangle rect = new Rectangle(30, 30, 60, 60);
        Collection<ReferencePoint> expected = new LinkedList<ReferencePoint>();
        Assert.assertEquals("No reference point should be returned for a far away rectangle",
                expected, arc1.getReferencePoints(rect));

        expected.add(new SquarePoint(ending, ending));
        rect = new Rectangle( -1.5, -0.5, -0.5, 0.5);
        Assert.assertEquals("Only the initial should be returned for a rectangle surrounding it",
                expected, arc1.getReferencePoints(rect));

        expected.add(new SquarePoint(initial, initial));
        expected.add(new CirclePoint(center, points));
        expected.add(new TrianglePoint(middle, middle));
        rect = new Rectangle( -1, -1, 1, 1);
        Collection<ReferencePoint> refs = arc1.getReferencePoints(rect);
        assertCollectionTheSame(expected, refs);
    }

    @Test
    public void positiveDirectionIsOutsideOfTheCircle () throws Exception {

        Point inside = new Point(0.5, 0);
        Point outside = new Point(2, 0);

        assertTrue("A point outside the circle that contains the arc should be positive direction",
                arc1.isPositiveDirection(outside));
        assertFalse("A point inside the circle that contains the arc should be negative direction",
                arc1.isPositiveDirection(inside));
        assertFalse(
                "A point contained in the circle that contains the arc should be negative direction",
                arc1.isPositiveDirection(initial));
    }

    @Test
    public void anArcShouldContainAllPointsThatBelongToItAndNoOther () throws Exception {

        assertTrue("An arc should contain its own initial point", arc1.contains(arc1
                .getInitialPoint()));
        assertTrue("An arc should contain its own ending point", arc1.contains(arc1
                .getEndingPoint()));
        assertTrue("An arc should contain its own intermediate point", arc1.contains(arc1
                .getIntermediatePoint()));
        Point intermediate = new Point(Math.sqrt(2) / 2.0, Math.sqrt(2) / 2.0);
        assertTrue("An arc should contain other points in it", arc1.contains(intermediate));

        assertFalse("An arc should not contain external points", arc1.contains(new Point(1, 1)));
        assertFalse("An arc should not contain internal points", arc1.contains(center));

        Point p = null;
        try {
            arc1.contains(p);
            fail("Should have thrown a NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Expected
        }
    }

    @Test
    public void canCreateArcWithThreePoints(){
    	
    	//Horizontal arc
    	arcCreationWithThreePoints(1, 0, 0, 1, -1, 0);
    	arcCreationWithThreePoints(1, 0, 0, 0.2, -1, 0);
    	arcCreationWithThreePoints(5, 5, 0, 10, -5, 5);
    	arcCreationWithThreePoints(1, 0, COS_45, COS_45, 0, 1);
    	
    	//Vertical arc
    	arcCreationWithThreePoints(0, -1, 1, 0, 0, 1);
    	arcCreationWithThreePoints(0, -1, 0.2, 0, 0, 1);
    	arcCreationWithThreePoints(5, -5, 10, 0, 5, 5);
    	
    	//Oblique arc
    	arcCreationWithThreePoints(1, 1, -Math.sqrt(2.0), Math.sqrt(2.0), -1, -1);
    	
    }

    private void arcCreationWithThreePoints(double x1, double y1, double x2, double y2, double x3, double y3){
    	
    	Point initialPoint = new Point(x1, y1);
    	Point intermediatePoint = new Point(x2, y2);
    	Point endingPoint = new Point(x3, y3);
    	
    	arc4 = createSafeArc(initialPoint, intermediatePoint, endingPoint);
    	
    	Assert.assertEquals(x1, arc4.getInitialPoint().getX(),Constant.EPSILON);
    	Assert.assertEquals(y1, arc4.getInitialPoint().getY(),Constant.EPSILON);  	
    	Assert.assertEquals(x2, arc4.getIntermediatePoint().getX(),Constant.EPSILON);
    	Assert.assertEquals(y2, arc4.getIntermediatePoint().getY(),Constant.EPSILON);
    	Assert.assertEquals(x3, arc4.getEndingPoint().getX(),Constant.EPSILON);
    	Assert.assertEquals(y3, arc4.getEndingPoint().getY(),Constant.EPSILON);
    	
    }

    private Arc createSafeArc(Point initial, Point intermediate, Point ending){
    	Arc result = null;
        try {
			result = new Arc(initial, intermediate, ending);
		} catch (NullArgumentException e) {
			Assert.fail("Should not thrown a NullArgumentException creating an arc.");
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			Assert.fail("Should not thrown an InvalidArgumentException creating an arc.");
			e.printStackTrace();
		}
        return result;
    }

    // TODO Tests for the creation of an arc with 3 points and a boolean

    // TODO Tests for the creation of an arc with 4 points

    // TODO Tests for the projection of a point on an arc

    // TODO Tests for contains another arc

    // TODO Tests for cloneWithDistance of an arc

    @Test
    public void getPointsReturnsThePointsInstancesThatDefineTheArc () throws Exception {

        List<Point> arcPoints = arc.getPoints();
        for (Point point : arcPoints) {
            // Can't be the same instance as for creation.
            Assert.assertFalse(point == initial);
            Assert.assertFalse(point == ending);
            Assert.assertFalse(point == middle);
            Assert.assertFalse(point == center);
        }

        Vector vector = new Vector(new Point(1, 1));
        for (Point point : arcPoints) {
            point.addVector(vector);
        }

        List<Point> newPoints = arc.getPoints();
        assertCollectionTheSame(arcPoints, newPoints);
    }

    @Test
    public void moveMaitainsTheSameInstancesOfPointsForTheArc () throws Exception {

        List<Point> arcPoints = arc.getPoints();
        arc.move(1, 1);
        List<Point> newPoints = arc.getPoints();
        assertCollectionTheSame(arcPoints, newPoints);

        List<Point> pointsToMove = new LinkedList<Point>(arcPoints);
        pointsToMove.remove(0); // Not moving the initial
        Vector vector = new Vector(new Point( -5, 0));
        arc.move(pointsToMove, vector);
        newPoints = arc.getPoints();
        assertCollectionTheSame(arcPoints, newPoints);
    }

    @Test
    public void testExtremePoints () throws Exception {

        List<Point> extremes = new LinkedList<Point>();
        extremes.add(initial);
        extremes.add(ending);

        List<Point> extremesComputed = arc.getExtremePoints();

        assertCollectionTheSame(extremes, extremesComputed);

    }

    // TODO Tests for mirror of an arc
}
