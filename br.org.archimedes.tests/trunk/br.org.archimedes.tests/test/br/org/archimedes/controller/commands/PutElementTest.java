/*
 * Created on 18/08/2006
 */

package br.org.archimedes.controller.commands;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class PutElementTest extends TestCase {

	private Drawing drawing;

	/*
	 * @see TestCase#setUp()
	 */
    @Before
	public void setUp() {

		drawing = new Drawing("Drawing");
	}

	/*
	 * @see TestCase#tearDown()
	 */
    @After
	public void tearDown() {

		drawing = null;
	}

	/*
	 * Test method for
	 * 'com.tarantulus.archimedes.model.commands.PutElementCommand.PutElementCommand(Element)'
	 */
    @Test
	public void testPutElementCommand() throws InvalidArgumentException {

		Element element = null;
		try {
			new PutOrRemoveElementCommand(element, false);
			fail("Should throw a NullArgumentException.");
		} catch (NullArgumentException e) {
		}

		element = new MockElement();
		try {
			new PutOrRemoveElementCommand(element, false);
		} catch (NullArgumentException e) {
			fail("Should not throw a NullArgumentException.");
		}
	}

	/*
	 * Test method for
	 * 'com.tarantulus.archimedes.model.commands.PutElementCommand.doIt(Drawing)'
	 */
    @Test
	public void testDoIt() throws InvalidArgumentException {

		Element element = new MockElement();
		Command putElement = safeCommand(element);

		try {
			putElement.doIt(null);
			fail("Should throw a NullArgumentException.");
		} catch (IllegalActionException e) {
			fail("Should throw a NullArgumentException! Not an IllegalActionException.");
		} catch (NullArgumentException e) {
		}

		assertFalse("The current layer should not contain the element.",
				drawing.getCurrentLayer().contains(element));

		safeDoIt(putElement, drawing);
		assertTrue("The current layer should contain the element.", drawing
				.getCurrentLayer().contains(element));

		try {
			putElement.doIt(drawing);
			fail("Should throw an IllegalActionException.");
		} catch (IllegalActionException e) {
		} catch (NullArgumentException e) {
			fail("Should throw an IllegalActionException! Not a NullArgumentException.");
		}
		assertTrue("The current layer should contain the element.", drawing
				.getCurrentLayer().contains(element));
		assertEquals("The current layer should contain only the element once.",
				1, drawing.getCurrentLayer().getElements().size());
	}

	/**
	 * @param putElement
	 *            The command to be executed.
	 * @param drawing
	 *            The drawing in which the command should be done.
	 */
	private void safeDoIt(Command putElement, Drawing drawing) {

		try {
			putElement.doIt(drawing);
		} catch (Exception e) {
			fail("Should not throw an exception.");
		}
	}

	/**
	 * @param element
	 *            The element that should be used to create the command.
	 * @return The PutElementCommand created.
	 */
	private UndoableCommand safeCommand(Element element) {

		PutOrRemoveElementCommand putElement = null;
		try {
			putElement = new PutOrRemoveElementCommand(element, false);
		} catch (NullArgumentException e) {
			fail("Should not throw this exception");
		}
		return putElement;
	}

	/*
	 * Test method for
	 * 'com.tarantulus.archimedes.model.commands.PutElementCommand.undoIt(Drawing)'
	 */
    @Test
	public void testUndoIt() throws InvalidArgumentException {

		Element element = new MockElement();
		UndoableCommand putElement = safeCommand(element);

		try {
			putElement.undoIt(drawing);
			fail("Should throw an IllegalActionException.");
		} catch (IllegalActionException e) {
		} catch (NullArgumentException e) {
			fail("Should throw an IllegalActionException! Not a NullArgumentException.");
		}

		safeDoIt(putElement, drawing);

		try {
			putElement.undoIt(null);
			fail("Should throw a NullArgumentException.");
		} catch (IllegalActionException e) {
			fail("Should throw a NullArgumentException! Not an IllegalActionException.");
		} catch (NullArgumentException e) {
		}

		try {
			putElement.undoIt(drawing);
		} catch (IllegalActionException e) {
			fail("Should not throw an IllegalActionException.");
		} catch (NullArgumentException e) {
			fail("Should not throw a NullArgumentException.");
		}
		assertFalse("The drawing should not contain this element", drawing
				.getCurrentLayer().contains(element));

		try {
			putElement.undoIt(drawing);
			fail("Should throw an IllegalActionException.");
		} catch (IllegalActionException e) {
		} catch (NullArgumentException e) {
			fail("Should throw an IllegalActionException! Not a NullArgumentException.");
		}
	}
}
