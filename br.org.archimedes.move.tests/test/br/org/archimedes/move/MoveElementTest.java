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
 * This file was created on 2006/08/22, 01:04:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.move on the br.org.archimedes.move.tests project.<br>
 */

package br.org.archimedes.move;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.model.Vector;
import br.org.archimedes.semiline.Semiline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.move.
 * 
 * @author night
 */
public class MoveElementTest extends Tester {

    private Drawing drawing;

    private Vector vector;

    private Circle circle;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () throws Exception {

        drawing = new Drawing("Drawing");
        vector = new Vector(new Point(10, 10));
    }

    private Selection createOriginalSelection () throws Exception {

        Selection selection = new Selection();
        selection.add(new Line(0, 0, 100, 100));
        selection.add(new InfiniteLine(10, 10, 50, 50));
        selection.add(new Semiline(10, -10, 50, 50));
        selection.add(new Arc(new Point( -100, 0), new Point(0, 100), new Point(100, 0)));
        circle = new Circle(new Point(0, 0), 50.0);
        selection.add(circle);
        return selection;
    }

    private Collection<Element> createOriginalExpected () throws Exception {

        Set<Element> expected = new HashSet<Element>();
        expected.add(new Line(10, 10, 110, 110));
        expected.add(new InfiniteLine(20, 20, 60, 60));
        expected.add(new Semiline(20, 0, 60, 60));
        expected.add(new Arc(new Point( -90, 10), new Point(10, 110), new Point(110, 10)));
        expected.add(new Circle(new Point(10, 10), 50.0));
        return expected;
    }

    /*
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown () throws Exception {

        drawing = null;
        vector = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.MoveElementCommand.MoveElementCommand(Set<Element>,
     * Vector)'
     */
    @Test
    public void moveElementCommand () throws Exception {

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

        new MoveCommand(getPoints(elements), vector);
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
     * Test method for 'br.org.archimedes.model.commands.MoveElementCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws Exception {
        
        
        Selection selection = createOriginalSelection();
        Command moveElement = new MoveCommand(getPoints(selection), vector);

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException since nothing is on the drawing");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        addsSelectionToDrawing(selection);
        moveElement.doIt(drawing);
        Collection<Element> expected = createOriginalExpected();
        assertCollectionTheSame(expected, selection.getSelectedElements());

        removesSelectionToDrawing(selection);

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        addsSelectionToDrawing(selection);
        drawing.removeElement(circle);

        try {
            moveElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {
            // Should throw an exception for the circle
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }

        expected = new ArrayList<Element>();
        expected.add(new Line(20, 20, 120, 120));
        expected.add(new InfiniteLine(30, 30, 70, 70));
        expected.add(new Semiline(30, 10, 70, 70));
        expected.add(circle);
        expected.add(new Arc(new Point( -80, 20), new Point(20, 120), new Point(120, 20)));

        assertCollectionTheSame(expected, selection.getSelectedElements());
    }

    /**
     * Adds the selection to the drawing.
     * @param selection The selection  to add 
     */
    private void addsSelectionToDrawing (Selection selection) throws Exception {

        for (Element element : selection.getSelectedElements()) {
            drawing.putElement(element, drawing.getCurrentLayer());
        }
    }

    /**
     * Removes the selection from the drawing.
     * @param selection The selection to remove
     */
    private void removesSelectionToDrawing (Selection selection) throws Exception {

        for (Element element : selection.getSelectedElements()) {
            drawing.removeElement(element);
        }
    }

    /*
     * Test method for 'br.org.archimedes.model.commands.MoveElementCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws Exception {
        Selection selection = createOriginalSelection();
        UndoableCommand moveElement = new MoveCommand(getPoints(selection), vector);

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
        catch (NullArgumentException e) {
            Assert.fail("Should throw an IllegalActionException");
        }

        addsSelectionToDrawing(selection);
        moveElement.doIt(drawing);
        moveElement.undoIt(drawing);

        Collection<Element> expected = createOriginalSelection().getSelectedElements();
        assertCollectionTheSame(expected, selection.getSelectedElements());

        selection = createOriginalSelection();
        drawing.addLayer(new Layer(Constant.BLACK, "Layer2", LineStyle.CONTINUOUS, 1.0));
        drawing.setCurrentLayer(1);
        addsSelectionToDrawing(selection);

        moveElement = new MoveCommand(getPoints(selection), vector);
        moveElement.doIt(drawing);
        moveElement.doIt(drawing);
        moveElement.undoIt(drawing);

        expected = createOriginalExpected();
        assertCollectionTheSame(expected, selection.getSelectedElements());
    }

}
