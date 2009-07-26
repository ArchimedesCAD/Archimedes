/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman - later contributions<br>
 * <br>
 * This file was created on 2007/04/27, 01:04:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rotate on the br.org.archimedes.rotate.tests project.<br>
 */

package br.org.archimedes.rotate;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author night
 */
public class RotateTest extends Tester {

    private Drawing drawing;

    private List<Element> selection;

    private List<Element> expected;

    private double angle;

    private Point reference;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () throws Exception {

        super.setUp();
        drawing = new Drawing("Drawing");
        createOriginalSelection();
        createOriginalExpected();
        angle = Math.PI / 2.0;
        reference = new Point(0, 0);
    }

    /**
     * @throws InvalidArgumentException
     *             Not thrown
     * @throws NullArgumentException
     *             Not thrown
     */
    private void createOriginalSelection () throws NullArgumentException, InvalidArgumentException {

        selection = new ArrayList<Element>();
        // selection.add(new Line(10, 0, 20, 0));
        // selection.add(new InfiniteLine(1, 0, 2, 0));
        // selection.add(new Semiline(2, 0, 3, 0));
        // selection.add(new Circle(new Point(5, 0), 5));
        // selection.add(new Arc(new Point( -10, 0), new Point(0, 10), new
        // Point(
        // 10, 0)));
    }

    /**
     * @throws NullArgumentException
     *             Not thrown.
     * @throws InvalidArgumentException
     *             Not thrown.
     */
    private void createOriginalExpected () throws NullArgumentException, InvalidArgumentException {

        expected = new ArrayList<Element>();
        // expected.add(new Line(0, 10, 0, 20));
        // expected.add(new InfiniteLine(0, 1, 0, 2));
        // expected.add(new Semiline(0, 2, 0, 3));
        // expected.add(new Circle(new Point(0, 5), 5));
        // expected.add(new Arc(new Point(0, -10), new Point( -10, 0), new
        // Point(
        // 0, 10)));
    }

    /*
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown () throws Exception {

        drawing = null;
        selection = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.MoveElementCommand.MoveElementCommand(Set<Element>,
     * Vector)'
     */
    @Test
    public void testRotateCommand () {

        List<Element> elements = null;
        try {
            new RotateCommand(elements, new Point(0, 0), 0.0);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        elements = new ArrayList<Element>();
        try {
            new RotateCommand(elements, null, 0.0);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        try {
            new RotateCommand(null, null, 0.0);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        try {
            new RotateCommand(elements, new Point(0, 0), 0.0);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

    }

    /*
     * Test method for 'br.org.archimedes.model.commands.MoveElementCommand.doIt(Drawing)'
     */
    public void testDoIt () throws InvalidArgumentException {

        Command rotateElement = null;
        try {
            rotateElement = new RotateCommand(selection, reference, angle);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any Exception");
        }

        try {
            rotateElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        addsSelectionToDrawing();

        try {
            rotateElement.doIt(drawing);
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw an IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        assertCollectionTheSame(expected, selection);

        removesSelectionToDrawing();

        try {
            rotateElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        addsSelectionToDrawing();
        try {
            drawing.removeElement(selection.get(2));
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        try {
            rotateElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        expected = new ArrayList<Element>();
        // TODO ARRumar
        // expected.add(new Line( -10, 0, -20, 0));
        // expected.add(new InfiniteLine( -1, 0, -2, 0));
        // expected.add(new Semiline(0, 2, 0, 3));
        // try {
        // expected.add(new Circle(new Point( -5, 0), 5));
        // expected.add(new Arc(new Point(10, 0), new Point(0, -10),
        // new Point( -10, 0)));
        // }
        // catch (Exception e) {
        // Assert.fail("Should not throw any exception");
        // }

        assertCollectionTheSame(expected, selection);
    }

    /**
     * Adds the selection to the drawing.
     */
    private void addsSelectionToDrawing () {

        for (Element element : selection) {
            try {
                drawing.putElement(element, drawing.getCurrentLayer());
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

        for (Element element : selection) {
            try {
                drawing.removeElement(element);
            }
            catch (Exception e) {
                Assert.fail("Should not throw any exception.");
            }
        }
    }

    /*
     * Test method for 'br.org.archimedes.model.commands.MoveElementCommand.undoIt(Drawing)'
     */
    public void testUndoIt () throws InvalidArgumentException {

        UndoableCommand moveElement = null;
        try {
            moveElement = new RotateCommand(selection, reference, angle);
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
        assertCollectionTheSame(expected, selection);

        try {
            createOriginalSelection();
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        drawing.addLayer(new Layer(Constant.BLACK, "Layer2", LineStyle.CONTINUOUS, 1.0));
        try {
            drawing.setCurrentLayer(1);
        }
        catch (IllegalActionException e1) {
            Assert.fail("Should not throw any exception");
        }
        addsSelectionToDrawing();
        try {
            moveElement = new RotateCommand(selection, reference, angle);
            moveElement.doIt(drawing);
            moveElement.doIt(drawing);
            moveElement.undoIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        assertCollectionTheSame(expected, selection);
    }

}
