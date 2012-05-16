/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/02, 08:48:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.circle on the br.org.archimedes.circle.tests project.<br>
 */
package br.org.archimedes.circle;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.tests.commands.
 */
public class CircleFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () throws Exception {

        factory = new CircleFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () throws Exception {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void canCreateCircleByDefault () {

        Point point1 = new Point(2, 3);
        Point point2 = new Point(0, 0);
        Vector vector = new Vector(point1, point2);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, vector);

        // Center
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);

        // Radius
        assertSafeNext(factory, 5.0, true);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, 5.0, true);
    }
    
    @Test
    public void testCancel () {

        Point point1 = new Point(2, 3);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
