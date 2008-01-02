/*
 * Created on 18/08/2006
 */

package br.org.archimedes.controller.commands;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.element.MockElement;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

/**
 * Belongs to package com.tarantulus.archimedes.model.commands.
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
     * 'com.tarantulus.archimedes.model.commands.PutElementCommand.PutElementCommand(Element)'
     */
    @Test
    public void testRemoveElementCommand () throws InvalidArgumentException {

        Element element = null;
        try {
            new PutOrRemoveElementCommand(element, true);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        element = new MockElement();
        try {
            new PutOrRemoveElementCommand(element, true);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException.");
        }
    }

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.PutElementCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws InvalidArgumentException {

        Element element = new MockElement();
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
     * 'com.tarantulus.archimedes.model.commands.PutElementCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws InvalidArgumentException {

        Element element = new MockElement();
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
