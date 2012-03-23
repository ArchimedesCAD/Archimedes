/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci, Luiz C. Real - later contributions<br>
 * <br>
 * This file was created on 2007/04/27, 11:08:06, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.dimension.test on the br.org.archimedes.dimension.test
 * project.<br>
 */

package br.org.archimedes.dimension.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.text.Text;

public class DimensionTest extends Tester {

    private static final double FONT_SIZE = 18;

    private Point point1;

    private Point point2;

    private Point distance;

    private double ddistance;


    @Before
    public void setUp () {

        // To load the correct font within the controller.
        // This hack is needed since the singletons within Utils set an activator so the Constant
        // cant find out it is a test.
        Assert.assertNotNull(Constant.DEFAULT_FONT);

        point1 = new Point(50, 50);
        point2 = new Point(50, 70);
        distance = new Point(70, 50);
        ddistance = 20.0;
    }

    @Test
    public void testDimensionConstructor () {

        try {
            new Dimension(null, point1, distance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, null, distance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            Point point = null;
            new Dimension(point1, point2, point, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, point1.clone(), distance, FONT_SIZE);
            Assert.fail("Should throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            // Ok
        }

        try {
            new Dimension(point1, point2, distance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
    }

    @Test
    public void testDimensionConstructorWithDouble () {

        try {
            new Dimension(null, point1, ddistance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, null, ddistance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, point1.clone(), ddistance, FONT_SIZE);
            Assert.fail("Should throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            // Ok
        }

        try {
            new Dimension(point1, point2, ddistance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
    }
    
    // TODO Test dimension constructor that receives a font (Mock it)

    /**
     * Creates a dimension. Fails if any exception is thrown.
     * 
     * @param initial
     *            The initial point
     * @param ending
     *            The ending point
     * @param distance
     *            The distance
     * @return The new dimension
     */
    private Dimension createSafeDimension (Point initial, Point ending, Point distance) {

        Dimension dim = null;
        try {
            dim = new Dimension(initial, ending, distance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
        return dim;
    }

    @Test
    public void testClone () {

        Dimension toClone = createSafeDimension(point1, point2, distance);
        Element clone = toClone.clone();
        Assert.assertNotNull("The clone should not be null", clone);
        Assert.assertEquals("The cloned dimension should be equal to the original", toClone, clone);
        Assert.assertFalse("The cloned dimension should not be the same instance as the original",
                toClone == clone);
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.elements.Dimension#contains(br.org.archimedes.model.Point)}.
     */
    public void testContains () {
    
        // TODO Test a dimension contains the right points
    }

    @Test
    public void testEqualsObject () {

        // Equal to self and not null
        Element dimension1 = createSafeDimension(point1, point2, distance);
        Assert.assertFalse("Should not be equal to null", dimension1.equals(null));
        Assert.assertTrue("Should be equal to itself", dimension1.equals(dimension1));

        // Equal to clone
        Element dimension2 = createSafeDimension(point1.clone(), point2.clone(), distance);
        Assert.assertTrue("Should be equal to a dimension with the same arguments", dimension1
                .equals(dimension2));
        Assert.assertTrue("Should be equal to a dimension with the same arguments", dimension2
                .equals(dimension1));

        // Change first point
        Vector vector = new Vector(new Point(2, 2));
        dimension2 = createSafeDimension(point1.addVector(vector), point2, distance);
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));

        // Change second point
        dimension2 = createSafeDimension(point1, point2.addVector(vector), distance);
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));

        // Change distance
        dimension2 = createSafeDimension(point1, point2, distance.addVector(vector));
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse("Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));
    }

    @Test
    public void testGetBoundaryRectangle () {

        Dimension dimension = createSafeDimension(point1, point2, distance);
        Rectangle boundary = dimension.getBoundaryRectangle();

        double distX = point1.getX() + Dimension.DIST_FROM_ELEMENT;
        double distY = point1.getY() - Dimension.DIST_AFTER_LINE;
        Point p1 = new Point(distX, distY);
        distX = distance.getX() + Dimension.DIST_AFTER_LINE;
        distY = point2.getY() + Dimension.DIST_AFTER_LINE;
        Point p2 = new Point(distX, distY);
        Rectangle expected = new Rectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        Assert.assertEquals("Boundary should be as expected", expected, boundary);
    }

    /**
     * Test method for {@link br.org.archimedes.model.elements.Dimension#getPoints()}.
     */
    public void testGetPoints () {
    
        // TODO Test get points for a diension gives all relevant points
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.elements.Dimension#getProjectionOf(br.org.archimedes.model.Point)}
     * .
     */
    public void testGetProjectionOf () {
    
        // TODO Test project of a point over a dimension
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.elements.Dimension#getReferencePoints(br.org.archimedes.model.Rectangle)}
     * .
     * @throws NullArgumentException 
     * @throws InvalidArgumentException 
     */
    @Test
    public void testGetReferencePoints () throws NullArgumentException, InvalidArgumentException {

    	Text text = mock(Text.class);
    	Rectangle area = new Rectangle(0, 0, 1, 1);
    	Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
    	Collection<ReferencePoint> returnedReferences;
    	references.add(new CirclePoint(new Point(0,0)));
    	references.add(new CirclePoint(new Point(1,1)));
    	references.add(new CirclePoint(new Point(2,3)));
    	when(text.getReferencePoints(area)).thenReturn(references);
    	
    	Dimension dimension = new Dimension(new Point(1,0), new Point(1,1), new Point(1,2), 1.0, text);
    	returnedReferences = dimension.getReferencePoints(area);
    	verify(text).getReferencePoints(area);
    	for(ReferencePoint point:references) {
    		assertTrue(returnedReferences.contains(point));
    	}
    }

    // TODO Test moving a dimension
    
    // TODO Test dimensions return the correct lines to draw
}
