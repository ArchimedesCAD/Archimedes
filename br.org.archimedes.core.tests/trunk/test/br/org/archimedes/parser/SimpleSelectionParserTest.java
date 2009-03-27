/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/16, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.parser;

import junit.framework.TestCase;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class SimpleSelectionParserTest extends TestCase {
	public void testSelectionParser() {
        br.org.archimedes.Utils.getController().setActiveDrawing(new Drawing(""));
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
