/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/08, 23:20:39, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline.rectangle on the br.org.archimedes.polyline.rectangle.tests project.<br>
 */
package br.org.archimedes.polyline.rectangle;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.polyline.rectangle.
 * 
 * @author Mariana V. Bravo
 */
public class RectangleFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;

    private Controller controller;

    private Vector vector;

    private Point point;


    @Before
    public void setUp () {

        factory = new RectangleFactory();
        controller = br.org.archimedes.Utils.getController();
        drawing = new Drawing("Drawing");
        controller.setActiveDrawing(drawing);

        point = new Point(0, 0);
        vector = new Vector(point, new Point(2, 3));
    }

    @After
    public void tearDown () throws Exception {

        controller.setActiveDrawing(null);
    }

    @Test
    public void testCreateRectangle () {

        // Begin
        assertBegin(factory, false);

        sendInvalids();
        assertInvalidNext(factory, vector);

        // Point
        assertSafeNext(factory, point, false);

        sendInvalids();
        assertInvalidNext(factory, point);

        // Vector
        assertSafeNext(factory, vector, true);

        sendInvalids();
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, true);
    }

    /**
     * Sends invalid parameters to the command.
     */
    private void sendInvalids () {

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
    }

    @Test
    public void testCancelRectangleCreation () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, true);
        assertCancel(factory, false);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
