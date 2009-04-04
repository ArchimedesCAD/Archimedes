/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/09/09, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests
 * project.<br>
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MacroCommandTest extends Tester {

    private class StubPointsEqualElement extends StubElement {

        private LinkedList<Point> points;

        public StubPointsEqualElement (Point... points) {
            this.points = new LinkedList<Point>();
            for (Point point : points) {
                this.points.add(point);
            }
        }

        @Override
        public boolean equals (Object object) {
            if(!(object instanceof Element))
                    return false;
            
            Element other = (Element) object;
            return getPoints().equals(other.getPoints());
        }

        @Override
        public List<Point> getPoints () {

            return points;
        }
    }

    private MacroCommand command;

    private Drawing drawing;

    private Element element;


    @Before
    public void setUp () {

        makeMacroCommand();
        drawing = new Drawing("Drawing");
    }

    private void makeMacroCommand () {

        element = new StubPointsEqualElement(new Point(10, 10));

        List<UndoableCommand> cmds = new ArrayList<UndoableCommand>();
        try {
            cmds.add(new PutOrRemoveElementCommand(element, false));
            Collection<Point> points = new ArrayList<Point>();
            points.addAll(element.getPoints());
            Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
            pointsToMove.put(element, points);
            Vector vector = new Vector(new Point(10, 43));
            cmds.add(new QuickMoveCommand(pointsToMove, vector));
            command = new MacroCommand(cmds);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }
    }

    @After
    public void tearDown () throws Exception {

        command = null;
        drawing = null;
        element = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.controller.commands.MacroCommand.MacroCommand(List<UndoableCommand>)'
     */
    @Test
    public void testMacroCommand () {

        try {
            new MacroCommand(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException not an IllegalActionException.");
        }

        try {
            new MacroCommand(new ArrayList<UndoableCommand>());
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw an IllegalActionException not a NullArgumentException.");
        }
        catch (IllegalActionException e) {}
    }

    /*
     * Test method for 'br.org.archimedes.controller.commands.MacroCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws InvalidArgumentException {

        try {
            command.doIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        safeDoIt();

        assertCollectionContains(drawing.getUnlockedContents(), element);

        Element expected = new StubPointsEqualElement(new Point(20, 53));
        Assert.assertEquals("The element should have been moved", expected, this.element);
    }

    /*
     * Test method for 'br.org.archimedes.controller.commands.MacroCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws InvalidArgumentException {

        try {
            command.undoIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        safeDoIt();
        try {
            command.undoIt(drawing);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw an exception with message: " + e.getMessage());
        }

        assertCollectionTheSame(drawing.getUnlockedContents(), Collections.emptyList());

        Element expected = new StubPointsEqualElement(new Point(10, 10));

        Assert.assertEquals("The element should have been moved", expected, this.element);
    }

    private void safeDoIt () {

        try {
            command.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception.");
        }
    }
}
