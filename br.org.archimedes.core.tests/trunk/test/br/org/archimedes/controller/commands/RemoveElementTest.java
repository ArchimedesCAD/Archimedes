/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/18, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.controller.commands;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author night
 */
public class RemoveElementTest extends Tester {

    private Drawing drawing;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () throws Exception {

        drawing = new Drawing("Drawing");
    }

    /*
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown () {

        drawing = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.PutElementCommand.PutElementCommand(Element)'
     */
    @Test
    public void testRemoveElementCommand () throws InvalidArgumentException {

        Element element = null;
        try {
            new PutOrRemoveElementCommand(element, true);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        element = new StubElement();
        try {
            new PutOrRemoveElementCommand(element, true);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException.");
        }
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.PutElementCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws InvalidArgumentException {

        Element element = new StubElement();
        Command removeElement = safeCommand(element);

        try {
            removeElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert
                    .fail("Should throw an IllegalActionException! Not a NullArgumentException.");
        }

        putSafeElementOnDrawing(element, drawing);

        try {
            removeElement.doIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert
                    .fail("Should throw a NullArgumentException! Not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        safeDoIt(removeElement, drawing);
        Assert.assertFalse("The current layer should not contain the element.",
                drawing.getCurrentLayer().contains(element));

        try {
            removeElement.doIt(drawing);
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert
                    .fail("Should throw an IllegalActionException! Not a NullArgumentException.");
        }
        Assert.assertFalse("The current layer should not contain the element.",
                drawing.getCurrentLayer().contains(element));
    }

    /**
     * @param removeElement
     *            The command to be executed.
     * @param drawing
     *            The drawing in which the command should be done.
     */
    private void safeDoIt (Command removeElement, Drawing drawing) {

        try {
            removeElement.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw an exception.");
        }
    }

    /**
     * @param element
     *            The element that should be used to create the command.
     * @return The PutElementCommand created.
     */
    private UndoableCommand safeCommand (Element element) {

        PutOrRemoveElementCommand removeElement = null;
        try {
            removeElement = new PutOrRemoveElementCommand(element, true);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw this exception");
        }
        return removeElement;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.PutElementCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws InvalidArgumentException {

        Element element = new StubElement();
        UndoableCommand removeElement = safeCommand(element);
        putSafeElementOnDrawing(element, drawing);

        try {
            removeElement.undoIt(drawing);
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert
                    .fail("Should throw an IllegalActionException! Not a NullArgumentException.");
        }

        safeDoIt(removeElement, drawing);

        try {
            removeElement.undoIt(null);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (IllegalActionException e) {
            Assert
                    .fail("Should throw a NullArgumentException! Not an IllegalActionException.");
        }
        catch (NullArgumentException e) {}

        try {
            removeElement.undoIt(drawing);
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw an IllegalActionException.");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException.");
        }
        Assert.assertTrue("The drawing should contain this element", drawing
                .getCurrentLayer().contains(element));

        try {
            removeElement.undoIt(drawing);
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (IllegalActionException e) {}
        catch (NullArgumentException e) {
            Assert
                    .fail("Should throw an IllegalActionException! Not a NullArgumentException.");
        }
    }
}
