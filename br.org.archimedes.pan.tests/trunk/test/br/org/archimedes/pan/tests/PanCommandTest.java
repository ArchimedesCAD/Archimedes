/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/17, 10:02:54, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.pan.tests on the br.org.archimedes.pan.tests project.<br>
 */
package br.org.archimedes.pan.tests;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.commands.PanCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

public class PanCommandTest extends Tester{
	
	private PanCommand pan;

	private Drawing drawing;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Point original = new Point(0, 0);
		Point viewport = new Point(14, 42);
		pan = new PanCommand(original, viewport);
		drawing = new Drawing("Drawing");
	}

	@After
	public void tearDown() throws Exception {		
		pan = null;
		drawing = null;
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.PanCommand(Point,
	 * Point)'
	 */
	@Test
	public void testPanCommand() {
		try {
			new PanCommand(null, null);
			Assert.fail("Should throw a NullArgumentException.");
		} catch (NullArgumentException e) {
		} catch (IllegalActionException e) {
			Assert.fail("Should throw a NullArgumentException not an IllegalActionException.");
		}

		try {
			new PanCommand(new Point(10, 10), new Point(10, 10));
			Assert.fail("Should throw an IllegalActionException.");
		} catch (NullArgumentException e) {
			Assert.fail("Should throw an IllegalActionException not a NullArgumentException.");
		} catch (IllegalActionException e) {
		}
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.doIt(Drawing)'
	 */
	@Test
	public void testDoIt() {
		try {
			pan.doIt(null);
			Assert.fail("Should throw a NullArgumentException");
		} catch (IllegalActionException e) {
			Assert.fail("Should throw a NullArgumentException not IllegalActionException");
		} catch (NullArgumentException e) {
		}

		double zoom = drawing.getZoom();
		safeDoIt();
		Assert.assertEquals("The viewport position should have been updated.",
				new Point(14, 42), drawing.getViewportPosition());
		Assert.assertEquals("The zoom should be the same.", zoom, drawing.getZoom());
	}

	private void safeDoIt() {
		try {
			pan.doIt(drawing);
		} catch (Exception e) {
			Assert.fail("Should not throw any exception.");
		}
	}

	/*
	 * Test method for
	 * 'br.org.archimedes.controller.commands.PanCommand.undoIt(Drawing)'
	 */
	@Test
	public void testUndoIt() {
		try {
			pan.undoIt(null);
			Assert.fail("Should throw a NullArgumentException");
		} catch (IllegalActionException e) {
			Assert.fail("Should throw a NullArgumentException not IllegalActionException");
		} catch (NullArgumentException e) {
		}

		double zoom = drawing.getZoom();
		safeDoIt();
		try {
			pan.undoIt(drawing);
		} catch (Exception e) {
			Assert.fail("Should not throw any exception.");
		}
		Assert.assertEquals("The viewport position should be back to the original.",
				new Point(0, 0), drawing.getViewportPosition());
		Assert.assertEquals("The zoom should be the same.", zoom, drawing.getZoom());
	}
}
