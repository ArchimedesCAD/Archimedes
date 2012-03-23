/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/24, 11:32:46, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.opengl on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.gui.opengl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;

public class OpenGLWrapperTest {

    OpenGLWrapper openGL = br.org.archimedes.Utils.getOpenGLWrapper();

    @Ignore
    @Test
	public void testAddDrawingContext() {

		Drawing drawing = new Drawing("Drawing");
		GLCanvas canvas = new GLCanvas(new Shell(), SWT.NO_BACKGROUND,
				new GLData());

		/* drawing and context null */
		try {
			openGL.addDrawingCanvas(null, null);
			fail("Should not catch this exception.");
		} catch (NullArgumentException e) {
			/* Should catch this exception */
			assertTrue(true);
		}

		/* just context null */
		try {
			openGL.addDrawingCanvas(drawing, null);
			fail("Should not catch this exception.");
		} catch (NullArgumentException e) {
			/* Should catch this exception */
			assertTrue(true);
		}

		/* just drawing null */
		try {
			openGL.addDrawingCanvas(null, canvas);
			fail("Should not catch this exception.");
		} catch (NullArgumentException e) {
			/* Should catch this exception */
			assertTrue(true);
		}

		/* adding a drawing associated with a context */
		try {
			openGL.addDrawingCanvas(drawing, canvas);
			Map<Drawing, GLCanvas> map = openGL.getDrawingCanvas();
			assertTrue("The drawing should be in the map!",
					map.containsKey(drawing));
			assertTrue("The drawing should be associated to the context.", map
					.get(drawing).equals(canvas));

		} catch (NullArgumentException e) {
			fail("Should not catch this exception.");
		}
	}
}
