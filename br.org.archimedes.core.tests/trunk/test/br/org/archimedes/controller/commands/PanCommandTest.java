/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/09/09, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.controller.commands;

import junit.framework.TestCase;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

public class PanCommandTest extends TestCase {

	private PanCommand pan;

	private Drawing drawing;

	protected void setUp() throws Exception {
		super.setUp();
		Point original = new Point(0, 0);
		Point viewport = new Point(14, 42);
		pan = new PanCommand(original, viewport);
		drawing = new Drawing("Drawing");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		pan = null;
		drawing = null;
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.PanCommand(Point,
	 * Point)'
	 */
	public void testPanCommand() {
		try {
			new PanCommand(null, null);
			fail("Should throw a NullArgumentException.");
		} catch (NullArgumentException e) {
		} catch (IllegalActionException e) {
			fail("Should throw a NullArgumentException not an IllegalActionException.");
		}

		try {
			new PanCommand(new Point(10, 10), new Point(10, 10));
			fail("Should throw an IllegalActionException.");
		} catch (NullArgumentException e) {
			fail("Should throw an IllegalActionException not a NullArgumentException.");
		} catch (IllegalActionException e) {
		}
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.doIt(Drawing)'
	 */
	public void testDoIt() {
		try {
			pan.doIt(null);
			fail("Should throw a NullArgumentException");
		} catch (IllegalActionException e) {
			fail("Should throw a NullArgumentException not IllegalActionException");
		} catch (NullArgumentException e) {
		}

		double zoom = drawing.getZoom();
		safeDoIt();
		assertEquals("The viewport position should have been updated.",
				new Point(14, 42), drawing.getViewportPosition());
		assertEquals("The zoom should be the same.", zoom, drawing.getZoom());
	}

	private void safeDoIt() {
		try {
			pan.doIt(drawing);
		} catch (Exception e) {
			fail("Should not throw any exception.");
		}
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.undoIt(Drawing)'
	 */
	public void testUndoIt() {
		try {
			pan.undoIt(null);
			fail("Should throw a NullArgumentException");
		} catch (IllegalActionException e) {
			fail("Should throw a NullArgumentException not IllegalActionException");
		} catch (NullArgumentException e) {
		}

		double zoom = drawing.getZoom();
		safeDoIt();
		try {
			pan.undoIt(drawing);
		} catch (Exception e) {
			fail("Should not throw any exception.");
		}
		assertEquals("The viewport position should be back to the original.",
				new Point(0, 0), drawing.getViewportPosition());
		assertEquals("The zoom should be the same.", zoom, drawing.getZoom());
	}
}
