/*
 * Created on 22/08/2006
 */

package br.org.archimedes.move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.model.commands.
 * 
 * @author night
 */
public class MoveElementTest extends Tester {

    private Drawing drawing;

    private Selection selection;

    private Vector vector;

    private Collection<Element> expected;

// private Circle circle;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () throws Exception{

        drawing = new Drawing("Drawing");
        createOriginalSelection();

        createOriginalExpected();

        vector = new Vector(new Point(10, 10));
    }

    /**
     * @throws InvalidArgumentException
     *             Not thrown
     * @throws NullArgumentException
     *             Not thrown
     */
    private void createOriginalSelection () throws NullArgumentException,
            InvalidArgumentException {

        selection = new Selection();
// selection.add(new Line(0, 0, 100, 100));
// selection.add(new InfiniteLine(10, 10, 50, 50));
// selection.add(new SemiLine(10, -10, 50, 50));
// circle = new Circle(new Point(0, 0), 50);
// selection.add(circle);
// selection.add(new Arc(new Point( -100, 0), new Point(0, 100),
// new Point(100, 0)));
    }

    /**
     * @throws NullArgumentException
     *             Not thrown.
     * @throws InvalidArgumentException
     *             Not thrown.
     */
    private void createOriginalExpected () throws NullArgumentException,
            InvalidArgumentException {

        expected = new ArrayList<Element>();
// expected.add(new Line(10, 10, 110, 110));
// expected.add(new InfiniteLine(20, 20, 60, 60));
// expected.add(new SemiLine(20, 0, 60, 60));
// expected.add(new Circle(new Point(10, 10), 50));
// expected.add(new Arc(new Point( -90, 10), new Point(10, 110),
// new Point(110, 10)));
    }

    /*
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown () throws Exception {

        drawing = null;
        selection = null;
        vector = null;
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.MoveElementCommand.MoveElementCommand(Set<Element>,
     * Vector)'
     */
    public void testMoveElementCommand () {

        Selection elements = null;
        Vector vector = null;
        try {
            new MoveCommand(getPoints(elements), vector);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        elements = new Selection();
        try {
            new MoveCommand(getPoints(elements), vector);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        vector = new Vector(new Point(850, -60));
        try {
            new MoveCommand(null, vector);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        try {
            new MoveCommand(getPoints(elements), vector);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

    }

    /**
     * @param selection
     *            The selection with the elements that should be considered
     * @return The collection of points or null if null was passsed.
     */
    private Map<Element, Collection<Point>> getPoints (Selection selection) {

        if (selection == null) {
            return null;
        }

        Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
        for (Element element : selection.getSelectedElements()) {
            pointsToMove.put(element, element.getPoints());
        }

        return pointsToMove;
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.MoveElementCommand.doIt(Drawing)'
     */
    public void testDoIt () throws InvalidArgumentException {

        Command moveElement = null;
        try {
            moveElement = new MoveCommand(getPoints(selection), vector);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any Exception");
        }

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        addsSelectionToDrawing();

        try {
            moveElement.doIt(drawing);
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw an IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        assertCollectionTheSame(expected, selection.getSelectedElements());

        removesSelectionToDrawing();

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

//        addsSelectionToDrawing();
//        try {
//            drawing.removeElement(circle);
//        }
//        catch (Exception e) {
//            Assert.fail("Should not throw any exception");
//        }

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        expected = new ArrayList<Element>();
//        expected.add(new Line(108, 38, 795, 3));
//        expected.add(new InfiniteLine( -546, 336, 123, -12));
//        expected.add(new SemiLine(165, 9, 192, 8));
//        try {
//            expected.add(new Circle(new Point(574, 606), 50));
//            expected.add(new Arc(new Point(8, -12), new Point(108, 88),
//                    new Point(208, -12)));
//        }
//        catch (Exception e) {
//            Assert.fail("Should not throw any exception");
//        }

        assertCollectionTheSame(selection.getSelectedElements(), expected);
    }

    /**
     * Adds the selection to the drawing.
     */
    private void addsSelectionToDrawing () {

        for (Element element : selection.getSelectedElements()) {
            try {
                drawing.putElement(element);
            }
            catch (Exception e) {
                Assert.fail("Should not throw any exception.");
            }
        }
    }

    /**
     * Adds the selection to the drawing.
     */
    private void removesSelectionToDrawing () {

        for (Element element : selection.getSelectedElements()) {
            try {
                drawing.removeElement(element);
            }
            catch (Exception e) {
                Assert.fail("Should not throw any exception.");
            }
        }
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.MoveElementCommand.undoIt(Drawing)'
     */
    public void testUndoIt () throws InvalidArgumentException {

        UndoableCommand moveElement = null;
        try {
            moveElement = new MoveCommand(getPoints(selection), vector);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any Exception");
        }
        try {
            moveElement.undoIt(null);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw any exception");
        }
        catch (NullArgumentException e) {}

        try {
            moveElement.undoIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e1) {
            Assert.fail("Should throw an IllegalActionException");
        }

        testDoIt(); // Do it

        try {
            createOriginalExpected();
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception.");
        }

        try {
            moveElement.undoIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should throw an IllegalActionException");
        }
        assertCollectionTheSame(expected, selection.getSelectedElements());

        try {
            createOriginalSelection();
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        drawing.addLayer(new Layer(new Color(0, 0, 0), "Layer2",
                LineStyle.CONTINUOUS, 1.0));
        try {
			drawing.setCurrentLayer(1);
		} catch (IllegalActionException e1) {
			Assert.fail("Should not throw any exception");
		}
        addsSelectionToDrawing();
        try {
            moveElement = new MoveCommand(getPoints(selection), vector);
            moveElement.doIt(drawing);
            moveElement.doIt(drawing);
            moveElement.undoIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        assertCollectionTheSame(expected, selection.getSelectedElements());
    }

}
