/*
 * Created on 24/03/2006 Created on 24/03/2006
 */

package br.org.archimedes.model;

import java.util.Collection;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;

public class DrawingTest extends Tester {

    Drawing drawing;

    private Element element;


    @Before
    public void setUp () {

        drawing = new Drawing(null);
        element = EasyMock.createMock(Element.class);
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
            drawing.putElement(null);
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
            drawing.putElement(element);
            Collection<Element> contents = drawing.getUnlockedContents();
            Assert.assertTrue("The element should be in the drawing!", contents
                    .contains(element));
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
            drawing.putElement(element);
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

        Element line = EasyMock.createMock(Element.class);
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
        Assert.assertFalse("The drawing should not contain this line.",
                contents.contains(line));

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
    public void testSelectionIntersection () {

        // TODO Arrumar quando tiver interseccao
        // Element line1 = createSafeLine(0.1, 0.1, 0.9, 0.9);
        // Element line2 = createSafeLine(0.5, 0.5, 2, 2);
        Element line1 = null;
        Element line2 = null;
        Rectangle rect1 = new Rectangle(0, 0, 1, 1);
        Rectangle rect2 = new Rectangle(0, 0, -0.5, -0.5);
        try {
            putSafeElementOnDrawing(line1, drawing);
            Set<Element> sel = drawing.getSelectionIntersection(rect1);
            Assert.assertTrue("The line should be selected", sel
                    .contains(line1));

            putSafeElementOnDrawing(line2, drawing);
            sel = drawing.getSelectionIntersection(rect1);
            Assert.assertTrue("The lines should be selected", sel
                    .contains(line1)
                    && sel.contains(line2));

            sel = drawing.getSelectionIntersection(rect2);
            Assert.assertTrue("The lines should not be selected",
                    sel.size() == 0);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }

        try {
            drawing.getSelectionIntersection(null);
            Assert.fail("Should not accept null rectangle.");
        }
        catch (NullArgumentException e) {
            /* Should throw this exception */
        }
    }

    @Test
    public void testSelectionInside () {

        // TODO arrumar com mock certo
        Element line1 = null;// createSafeLine(0.1, 0.1, 14.9, 14.9);
        Element line2 = null;// createSafeLine(15, 0, 15, 15);
        Element line3 = null;// createSafeLine(1, 2, 2, 3);
        Element line4 = null;// createSafeLine(1, 0, 1, 16);
        Element line5 = null;// createSafeLine(16, 16, 17, 17);
        Element infiniteLine = null;// createSafeInfiniteLine(0, 0, 15, 15);

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
        Assert.assertTrue("The line should not be selected", sel
                .contains(line2));

        Assert.assertFalse("The line should not be selected", sel
                .contains(line4));
        Assert.assertFalse("The line should not be selected", sel
                .contains(line5));
        Assert.assertFalse("The line should not be selected", sel
                .contains(infiniteLine));
    }

    @Test
    public void testBoundaryCalculations () {

        // TODO Arrumar
        Element line0 = null;// createSafeLine(10.0, 5.0, 10.0, 10.0);
        Element line1 = null;// createSafeLine(0.0, 0.0, 15.0, 20.0);
        Element line2 = null;// createSafeLine( -5.0, -2.0, 16.0, 17.0);
        Element xLine = null;// createSafeInfiniteLine(0.0, 0.0, 1.0, -1.0);

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

    @Test
    public void testUndo () {

        // TODO Refazer
    }

    @Test
    public void testSetCurrentLayer () {

        Layer layer = new Layer(Constant.RED, "layerTest",
                LineStyle.CONTINUOUS, 2);
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

    // private void addCommand (Command newCommand) {
    //
    // try {
    // drawing.setLastCommand(newCommand.toExecuted());
    // }
    // catch (NonReversibleException e) {
    // Assert.fail("Should not throw this exception " + e.toString());
    // }
    // if (newCommand.isRevertable()) {
    // Assert.assertFalse("The history shouldn't be empty.",
    // drawing.getHistory()
    // .isEmpty());
    // Assert.assertTrue("The last command in history is wrong.", drawing
    // .getHistory().peek().equals(newCommand));
    // }
    // else {
    // Assert.assertFalse("The last command in history is wrong.", drawing
    // .getHistory().isEmpty()
    // || drawing.getHistory().peek().equals(newCommand));
    // }
    // }

    /**
     * Removes an element known to be safe in the drawing. Fails if any
     * exception is thrown.
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

}
