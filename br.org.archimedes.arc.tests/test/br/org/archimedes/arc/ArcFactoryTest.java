/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Eduardo O. de Souza - later contributions<br>
 * <br>
 * This file was created on 2007/03/26, 11:32:10, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.arc on the br.org.archimedes.arc.tests project.<br>
 */
package br.org.archimedes.arc;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Belongs to package br.org.archimedes.tests.commands.
 */
public class ArcFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () throws Exception {

        factory = new ArcFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () throws Exception {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void canCreateArcUsingDefaultPath () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Arguments
        Point p1 = new Point( -10, 0);
        Point p2 = new Point(0, 10);
        Point p3 = new Point(10, 0);
        Vector v1 = new Vector(p1,p2);
        Vector v2 = new Vector(p2,p3);

        // Initial point
        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);

        // Arc point
        assertSafeNext(factory, v1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p3);

        // Ending point
        assertSafeNext(factory, v2, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, v1, false);
        assertSafeNext(factory, v2, true);
    }

    @Test
    public void canCreateArcByCenter () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Create By Center protocol
        String s = "c";
        assertSafeNext(factory, s, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Arguments
        Point center = new Point(0, 0);
        Point p1 = new Point( -10, 0);
        Point p2 = new Point(10, 0);
        Point p3 = new Point(0, 10);
        Vector v1 = new Vector(center,p1);
        Vector v2 = new Vector(p1,p2);

        // Center point
        assertSafeNext(factory, center, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Initial vector
        assertSafeNext(factory, v1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Next vector
        assertSafeNext(factory, v2, false);

//        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
//        
//        assertSafeNext(factory, v2, false);
//        
//        assertInvalidNext(factory, null);
//        assertInvalidNext(factory, new Object());

        // Direction point
        assertSafeNext(factory, p3, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

    }

    @Test
    public void canCancelArcCreation () {

        Point point1 = new Point(2, 3);
        Point point2 = new Point(10, 3);
        Point point3 = new Point(0,10);
        Vector v1 = new Vector(point1,point2);
        Vector v2 = new Vector(point2,point3);
        String s = "c";

        // Start and cancel the command
        assertBegin(factory, false);
        assertCancel(factory, false);
        
        // Start, add a point and cancel the command
        assertBegin(factory, false);

        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);

        // Start, add two points and cancel the command
        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, v1, false);
        assertCancel(factory, false);
        assertInvalidNext(factory, point2);

        // Start command with c and cancel
        assertBegin(factory, false);
        assertSafeNext(factory, s, false);
        assertCancel(factory, false);
        
        // Start command with c, set the center point and cancel
        assertBegin(factory, false);
        assertSafeNext(factory, s, false);
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);
        
        // start command with c, set the first and second point (vector) and cancel
        assertBegin(factory, false);
        assertSafeNext(factory, s, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, v1, false);
        assertCancel(factory, false);
        
        // Start command with c, set the first and second point (vector) and cancel
        assertBegin(factory, false);
        assertSafeNext(factory, s, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, v1, false);
        assertSafeNext(factory, v2, false);
        assertCancel(factory, false);
    }
    
    @Test
    public void arcFactoryNameIsArc () throws Exception {

        assertEquals("The arc factory name should be 'arc'.", "arc", factory.getName());
    }

	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
