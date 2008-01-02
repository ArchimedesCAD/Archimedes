/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import junit.framework.TestCase;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package com.tarantulus.archimedes.interpreter.parser.
 */
public class SimpleSelectionParserTest extends TestCase {
	public void testSelectionParser() {
        Controller.getInstance().setActiveDrawing(new Drawing(""));
		SimpleSelectionParser sp = new SimpleSelectionParser();
		assertFalse("Should not be done yet", sp.isDone());
		assertNull("The parameter should be null", sp.getParameter());

		assertThrowsException(sp, null);
		assertThrowsException(sp, "bla");
		assertThrowsException(sp, "10;5");
		assertThrowsException(sp, "10,6");

		try {
			sp.next("");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			fail("Should not throw this exception");
		}
		assertTrue("Should be done", sp.isDone());
		assertNotNull("The parameter should not be null", sp.getParameter());
	}

	/**
	 * Asserts that the parser throws an exception with the given message, and
	 * assert it doesn't end after recieving it.
	 * 
	 * @param sp
	 *            The parser to use
	 * @param message
	 *            The message to pass
	 */
	private void assertThrowsException(SimpleSelectionParser sp, String message) {
		try {
			sp.next(message);
			fail("Should not reach this point.");
		}
		catch (InvalidParameterException e) {
			// Should throw this exception
		}
		assertFalse("Should not be done yet", sp.isDone());
		assertNull("The parameter should be null", sp.getParameter());
	}
}
