/*
 * Created on 09/09/2006
 */

package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.element.MockElement;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class MacroCommandTest extends Tester {

    private MacroCommand command;

    private Drawing drawing;

    private MockElement element;


    @Before
    public void setUp () {

        makeMacroCommand();
        drawing = new Drawing("Drawing");
    }

    private void makeMacroCommand () {

        element = new MockElement();
        List<UndoableCommand> cmds = new ArrayList<UndoableCommand>();
        try {
            cmds.add(new PutOrRemoveElementCommand(element, false));
            Collection<Point> points = new ArrayList<Point>();
            points.add(element.getPoint());
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
     * 'com.tarantulus.archimedes.controller.commands.MacroCommand.MacroCommand(List<UndoableCommand>)'
     */
    @Test
    public void testMacroCommand () {

        try {
            new MacroCommand(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}
        catch (IllegalActionException e) {
            Assert
                    .fail("Should throw a NullArgumentException not an IllegalActionException.");
        }

        try {
            new MacroCommand(new ArrayList<UndoableCommand>());
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (NullArgumentException e) {
            Assert
                    .fail("Should throw an IllegalActionException not a NullArgumentException.");
        }
        catch (IllegalActionException e) {}
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.controller.commands.MacroCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws InvalidArgumentException {

        try {
            command.doIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert
                    .fail("Should throw a NullArgumentException not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        safeDoIt();

        assertCollectionContains(drawing.getUnlockedContents(), element);
        MockElement expected = new MockElement(new Point(10, 43));
        Assert.assertEquals("The element should have been moved", expected,
                this.element);
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.controller.commands.MacroCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws InvalidArgumentException {

        try {
            command.undoIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert
                    .fail("Should throw a NullArgumentException not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        safeDoIt();
        try {
            command.undoIt(drawing);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw an exception with message: "
                    + e.getMessage());
        }

        assertCollectionTheSame(drawing.getUnlockedContents(),
                new ArrayList<Element>());
        MockElement expected = new MockElement();
        Assert.assertEquals("The element should have been moved", expected,
                this.element);
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
