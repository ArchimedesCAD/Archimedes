/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/15, 23:45:38, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.zoom on the br.org.archimedes.zoom.tests project.<br>
 */
package br.org.archimedes.zoom;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class ZoomFactoryTest extends FactoryTester {

    private ZoomFactory factory;


    @Before
    public void setUp () {

        factory = new ZoomFactory();
        Drawing drawing = new Drawing("Drawing 1");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testZoomPrevious () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, "p", true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, "p", true);
    }

    @Test
    public void testZoomExtend () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, "e", true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, "e");
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, "e", true);
    }

    @Test
    public void testRelativeZoom () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, 2.0, true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, 0.5);
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, 0.5, true);
    }

    @Test
    public void testZoomByArea () {

        // Begin
        assertBegin(factory, false);

        Point p1 = new Point(10, 50);
        Vector vector = new Vector(p1, new Point(50, 10));

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, vector);

        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, 0.5);
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);

        assertSafeNext(factory, vector, true);

        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, vector, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
