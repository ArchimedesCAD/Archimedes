/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Marcio Oshiro - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/04/18, 11:32:46, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;

import java.util.Collection;
import java.util.LinkedList;

/**
 * This is a helper class for tests. It contains useful Assert.assertions for our model. Any test
 * that wishes to use such Assert.assertions must subclass this test.<br>
 * Belongs to package br.org.archimedes.
 * 
 * @author oshiro
 */
public abstract class Tester {

    protected final double COS_45 = Math.sqrt(2.0) / 2.0;

    protected final double COS_30 = Math.sqrt(3.0) / 2.0;

    protected final double COS_60 = 0.5;


    /**
     * Constructor.
     */
    public Tester () {

        // To ensure Constant will be correctly loaded for tests.
        Assert.assertNotNull(Constant.DEFAULT_FONT);
    }

    @Before
    public void setUp() throws Exception{
        // Nothing to set I think
    }
    
    /**
     * Asserts two points are the same, considering precision errors.
     * 
     * @param expectedPoint
     *            The point with the expected values
     * @param realPoint
     *            The real point
     */
    public void assertPointsTheSame (Point expectedPoint, Point realPoint) {

        if (expectedPoint == null) {
            Assert.assertNull(realPoint);
        }
        else {
            Assert.assertNotNull(realPoint);
            assertWithinDelta("Point: is not same x", expectedPoint.getX(), realPoint.getX());
            assertWithinDelta("Point: is not same y", expectedPoint.getY(), realPoint.getY());
        }
    }

    /**
     * Asserts the difference between two double values is at most EPSILON.
     * 
     * @param message
     *            The message to be displayed in case the Assert.assertion fails.
     * @param value1
     *            One of the doubles to be compared.
     * @param value2
     *            The other double.
     */
    public void assertWithinDelta (String message, double value1, double value2) {

        Assert.assertTrue(message, Math.abs(value1 - value2) < Constant.EPSILON);
    }

    /**
     * Asserts the collections contain the same elements.
     * 
     * @param expected
     *            The collection with the expected elements
     * @param real
     *            The collection with the real elements
     */
    public void assertCollectionTheSame (Collection<? extends Object> expected,
            Collection<? extends Object> real) {

        Collection<Object> expectedList = new LinkedList<Object>();
        expectedList.addAll(expected);

        Collection<Object> realList = new LinkedList<Object>();
        realList.addAll(real);

        Assert.assertTrue("The expected collection " + expectedList
                + " should contain the actual one " + realList, expectedList.containsAll(realList));
        Assert.assertTrue("The actual collection " + realList + " should contain the expected one "
                + expectedList, realList.containsAll(expectedList));
    }

    /**
     * Asserts the collections do not contain the same elements.
     * 
     * @param expected
     *            The collection with the expected elements
     * @param real
     *            The collection with the real elements
     */
    public void assertCollectionNotTheSame (Collection<?> expected, Collection<?> real) {

        Assert.assertFalse("The expected collection " + expected
                + " should not contain the real one " + real + " and vice-versa.", expected
                .containsAll(real)
                || real.containsAll(expected));
    }

    /**
     * Asserts the collection of elements contain the given element.
     * 
     * @param collection
     *            The collection
     * @param element
     *            The element
     */
    public void assertCollectionContains (Collection<Element> collection, Element element) {

        boolean foundOne = false;
        for (Element existingElement : collection) {
            if (element.equals(existingElement)) {
                foundOne = true;
                break;
            }
        }
        Assert.assertTrue("Should have found the element " + element + " in the collection "
                + collection, foundOne);
    }

    /**
     * Asserts the collection of elements does not contain the given element.
     * 
     * @param collection
     *            The collection
     * @param element
     *            The element
     */
    public void assertCollectionNotContains (Collection<Element> collection, Element element) {

        boolean foundOne = false;
        for (Element existingElement : collection) {
            if (element.equals(existingElement)) {
                foundOne = true;
                break;
            }
        }
        Assert.assertFalse("Should not have found the element " + element + " in the collection "
                + collection, foundOne);
    }

    /**
     * Safely selects a point using the controller.<br>
     * 
     * @param point
     *            The point
     * @param invertSelection
     *            Indicates if the selection is to be inverted
     */
    protected void safeSelect (Point point, boolean invertSelection) {

        try {
            br.org.archimedes.Utils.getController().select(point, invertSelection);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should be a safe selection");
        }
        catch (NoActiveDrawingException e) {
            e.printStackTrace();
            Assert.fail("Should be a safe selection");
        }
    }

    /**
     * Safely selects an area using the controller.<br>
     * 
     * @param firstPoint
     *            The first point to define the area
     * @param secondPoint
     *            The second point to define the area
     * @param invertSelection
     *            Indicates if the selection is to be inverted
     */
    protected void safeSelect (Point firstPoint, Point secondPoint, boolean invertSelection) {

        try {
            br.org.archimedes.Utils.getController()
                    .select(firstPoint, secondPoint, invertSelection);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should be a safe selection");
        }
        catch (NoActiveDrawingException e) {
            e.printStackTrace();
            Assert.fail("Should be a safe selection");
        }
    }

    /**
     * Puts an element known to be safe in the drawing. Fails if any exception is thrown.
     * 
     * @param element
     *            The element to put in the drawing.
     * @param drawing
     *            The drawing where the element will be put.
     */
    public void putSafeElementOnDrawing (Element element, Drawing drawing) {

        try {
            drawing.putElement(element, drawing.getCurrentLayer());
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Element was not safe!");
        }
        catch (IllegalActionException e) {
            e.printStackTrace();
            Assert.fail("Element was not safe!");
        }
    }

    /**
     * Safely rotates an element around a reference point, by an angle. Fails if the operation
     * throws any exception
     * 
     * @param element
     *            The element to be rotated
     * @param reference
     *            The reference point
     * @param angle
     *            The angle
     */
    protected void safeRotate (Element element, Point reference, double angle) {

        try {
            element.rotate(reference, angle);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
    }
}
