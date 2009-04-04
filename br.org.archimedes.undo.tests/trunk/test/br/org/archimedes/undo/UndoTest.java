/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/23, 10:13:29, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.undo on the br.org.archimedes.undo.tests project.<br>
 */
package br.org.archimedes.undo;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.stub.StubCommand;
import br.org.archimedes.stub.StubElement;
import br.org.archimedes.stub.StubUndoableCommand;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author night
 */
public class UndoTest {

    private Drawing drawing;

    private UndoCommand undo;

    private List<Command> commands;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () throws Exception {

        drawing = new Drawing("Drawing");
        commands = new ArrayList<Command>();
        Point p1 = new Point(0, 10);
        Point p2 = new Point(60, 48);
        Element element = new StubElement();
        Collection<Point> points = new ArrayList<Point>();
        points.add(p1);
        points.add(p2);

        commands.add(new PutOrRemoveElementCommand(element, false));
        Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
        pointsToMove.put(element, points);
        commands.add(new StubUndoableCommand());
        commands.add(new StubCommand());
        commands.add(new PutOrRemoveElementCommand(element, true));

        undo = new UndoCommand();
    }

    /*
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown () throws Exception {

        drawing = null;
        commands = null;
        undo = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.UndoCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () {

        try {
            undo.doIt(null);
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException");
        }
        catch (NullArgumentException e) {}

        try {
            undo.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should throw an IllegalActionException");
        }

        Stack<UndoableCommand> undoHistory = drawing.getUndoHistory();
        Stack<UndoableCommand> redoHistory = drawing.getRedoHistory();
        try {
            drawing.execute(commands);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertEquals("The undo history size is wrong.", 3, undoHistory
                .size());
        Assert.assertEquals("The first element in the undo history is wrong",
                commands.get(commands.size() - 1), undoHistory.peek());
        Assert.assertEquals("The redo history size is wrong.", 0, redoHistory
                .size());

        try {
            undo.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertEquals("The undo history size is wrong.", 2, undoHistory
                .size());
        Assert.assertEquals("The first element in the undo history is wrong",
                commands.get(commands.size() - 3), undoHistory.peek());
        Assert.assertEquals("The redo history size is wrong.", 1, redoHistory
                .size());
        Assert.assertEquals("The first element in the redo history is wrong",
                commands.get(commands.size() - 1), redoHistory.peek());

        try {
            undo.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertEquals("The undo history size is wrong.", 1, undoHistory
                .size());
        Assert.assertEquals("The first element in the undo history is wrong",
                commands.get(commands.size() - 4), undoHistory.peek());
        Assert.assertEquals("The redo history size is wrong.", 2, redoHistory
                .size());
        Assert.assertEquals("The first element in the redo history is wrong",
                commands.get(commands.size() - 3), redoHistory.peek());

        try {
            undo.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertEquals("The undo history size is wrong.", 0, undoHistory
                .size());
        Assert.assertEquals("The redo history size is wrong.", 3, redoHistory
                .size());
        Assert.assertEquals("The first element in the redo history is wrong",
                commands.get(commands.size() - 4), redoHistory.peek());

        try {
            undo.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert.fail("Should throw an IllegalActionException");
        }
        Assert.assertEquals("The undo history size is wrong.", 0, undoHistory
                .size());
        Assert.assertEquals("The redo history size is wrong.", 3, redoHistory
                .size());
        Assert.assertEquals("The first element in the redo history is wrong",
                commands.get(commands.size() - 4), redoHistory.peek());
    }

}
