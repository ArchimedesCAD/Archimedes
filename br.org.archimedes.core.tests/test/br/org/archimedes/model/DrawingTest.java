/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman - later contributions<br>
 * <br>
 * This file was created on 2006/03/24, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.model;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DrawingTest extends Tester {

    public class BoundedStubElement extends StubElement {

        private Rectangle bounds;


        public BoundedStubElement (double x1, double y1, double x2, double y2) {

            this(new Rectangle(x1, y1, x2, y2));
        }

        public BoundedStubElement (Rectangle rectangle) {

            this.bounds = rectangle;
        }

        public BoundedStubElement () {

            this(null);
        }

        @Override
        public Rectangle getBoundaryRectangle () {

            return bounds;
        }
    }

    public class ContainedStubElement extends StubElement {

        private boolean inside;


        public ContainedStubElement (boolean inside) {

            this.inside = inside;
        }

        @Override
        public boolean isInside (Rectangle rectangle) {

            return inside;
        }
    }


    Drawing drawing;

    private Element element;


    @Before
    public void setUp () {

        drawing = new Drawing(null);
        element = new StubElement();
    }

    @After
    public void tearDown () {

        drawing = null;
    }

    @Test
    public void testInitialState () {

        Collection<Element> contents = drawing.getUnlockedContents();
        Assert.assertTrue("The drawing should be empty.", contents.isEmpty());

        Rectangle boundary = drawing.getBoundary();
        Assert.assertNull("The boundary should be null.", boundary);
    }

    @Test
    public void testPutElement () {

        /* Null element */
        try {
            drawing.putElement(null, null);
            Assert.fail("Should not accept a null element.");
        }
        catch (NullArgumentException e) {
            /* Should catch this exception */
            Assert.assertTrue(true);
        }
        catch (IllegalActionException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this excepion.");
        }

        /* Simple element */
        try {
            drawing.putElement(element, drawing.getCurrentLayer());
            Collection<Element> contents = drawing.getUnlockedContents();
            Assert.assertTrue("The element should be in the drawing!", contents.contains(element));
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this excepion.");
        }
        catch (IllegalActionException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this excepion.");
        }

        /* Duplicated element */
        try {
            drawing.putElement(element, drawing.getCurrentLayer());
            Assert.fail("Should not accept the exact same reference.");
        }
        catch (IllegalActionException e) {
            /* Should catch this exception. */
            Assert.assertTrue(true);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this excepion.");
        }
    }

    @Test
    public void testRemoveElement () {

        Element line = new StubElement();
        Collection<Element> contents = drawing.getUnlockedContents();

        // Put a line in there
        putSafeElementOnDrawing(line, drawing);

        // Remove the line
        try {
            drawing.removeElement(line);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }
        catch (IllegalActionException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }

        // Make sure it's not there
        Assert.assertFalse("The drawing should not contain this line.", contents.contains(line));

        // Remove it again
        try {
            drawing.removeElement(line);
            Assert.fail("The line has already been removed.");
        }
        catch (IllegalActionException e) {
            /* Should throw this exception */
            Assert.assertTrue(true);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }

        // Remove null
        try {
            drawing.removeElement(null);
            Assert.fail("Should not remove null");
        }
        catch (NullArgumentException e) {
            /* Should throw this exception */
            Assert.assertTrue(true);
        }
        catch (IllegalActionException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }
    }

    @Test
    public void testSelectionInside () {

        Element line1 = new ContainedStubElement(true);
        Element line2 = new ContainedStubElement(false);
        Element line3 = new ContainedStubElement(true);
        Element line4 = new ContainedStubElement(false);
        Element line5 = new ContainedStubElement(false);
        Element infiniteLine = new ContainedStubElement(false);

        Rectangle rectSelection = new Rectangle(0, 0, 15, 15);

        putSafeElementOnDrawing(line1, drawing);
        putSafeElementOnDrawing(line2, drawing);
        putSafeElementOnDrawing(line3, drawing);
        putSafeElementOnDrawing(line4, drawing);
        putSafeElementOnDrawing(line5, drawing);
        putSafeElementOnDrawing(infiniteLine, drawing);

        Set<Element> sel = null;

        try {
            sel = drawing.getSelectionInside(rectSelection);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw this exception.");
            e.printStackTrace();
        }

        Assert.assertNotNull("The selection should not be null.", sel);

        Assert.assertTrue("The line should be selected", sel.contains(line1));
        Assert.assertTrue("The line should be selected", sel.contains(line3));

        Assert.assertFalse("The line should not be selected", sel.contains(line2));
        Assert.assertFalse("The line should not be selected", sel.contains(line4));
        Assert.assertFalse("The line should not be selected", sel.contains(line5));
        Assert.assertFalse("The line should not be selected", sel.contains(infiniteLine));
    }

    @Test
    public void testBoundaryCalculations () {

        Element line0 = new BoundedStubElement(10.0, 5.0, 10.0, 10.0);
        Element line1 = new BoundedStubElement(0.0, 0.0, 15.0, 20.0);
        Element line2 = new BoundedStubElement( -5.0, -2.0, 16.0, 17.0);
        Element xLine = new BoundedStubElement();

        Rectangle expectedBoundary = null;
        Rectangle realBoundary = null;

        putSafeElementOnDrawing(line0, drawing);
        expectedBoundary = new Rectangle(10.0, 5.0, 10.0, 10.0);
        realBoundary = drawing.getBoundary();
        Assert.assertEquals(expectedBoundary, realBoundary);

        putSafeElementOnDrawing(line1, drawing);
        expectedBoundary = new Rectangle(0, 0, 15, 20);
        realBoundary = drawing.getBoundary();
        Assert.assertEquals(expectedBoundary, realBoundary);

        putSafeElementOnDrawing(line2, drawing);
        expectedBoundary = new Rectangle( -5, -2, 16, 20);
        realBoundary = drawing.getBoundary();
        Assert.assertEquals(expectedBoundary, realBoundary);

        putSafeElementOnDrawing(xLine, drawing);
        realBoundary = drawing.getBoundary();
        Assert.assertEquals(expectedBoundary, realBoundary);

        removeSafeElement(line1);
        expectedBoundary = new Rectangle( -5, -2, 16, 17);
        realBoundary = drawing.getBoundary();
        Assert.assertEquals(expectedBoundary, realBoundary);
    }

    // TODO Test undo on the drawing

    @Test
    public void testSetCurrentLayer () {

        Layer layer = new Layer(Constant.RED, "layerTest", LineStyle.CONTINUOUS, 2);
        Layer otherLayer = drawing.getCurrentLayer();
        drawing.addLayer(layer);

        try {
            drawing.setCurrentLayer(1);
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw any exception.");
        }
        Assert.assertEquals(layer, drawing.getCurrentLayer());

        otherLayer.setLocked(true);
        try {
            drawing.setCurrentLayer(0);
            Assert.fail("Should throw IllegalActionException.");
        }
        catch (IllegalActionException e) {
            // Expected behaviour
        }
        Assert.assertEquals(layer, drawing.getCurrentLayer());
    }

    /**
     * Removes an element known to be safe in the drawing. Fails if any exception is thrown.
     * 
     * @param element
     *            The element to remove from the drawing.
     */
    private void removeSafeElement (Element element) {

        try {
            drawing.removeElement(element);
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

    @Test
    public void equalsOnlyIfHasTheSameFile () throws Exception {

        assertTrue("A drawing equals itself", drawing.equals(drawing));
        assertEquals("The hashcode of a drawing must be the same of itself", drawing.hashCode(),
                drawing.hashCode());

        assertFalse("A drawing is never equal null", drawing.equals(null));
        assertFalse("A drawing is never equal a random Object", drawing.equals(new Object()));

        Drawing other = new Drawing(null);
        assertFalse("A new drawing does not equal a new drawing", drawing.equals(other));

        other.setFile(new File("test.xml"));
        assertFalse("A new drawing does not equal a drawing with a file", drawing.equals(other));

        drawing.setFile(new File("another.xml"));
        assertFalse("A drawing with a file does not equal a drawing with another file", drawing
                .equals(other));

        drawing.setFile(new File("test.xml"));
        assertTrue("A drawing with a file equals a drawing with the same file", drawing
                .equals(other));
        assertEquals("The hashcode of a drawing must be the same of an equal drawing", drawing
                .hashCode(), other.hashCode());
    }
}
