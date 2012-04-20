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
 * This file was created on 2007/04/17, 10:12:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline on the br.org.archimedes.polyline.tests project.<br>
 */
package br.org.archimedes.polyline;

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
 * 
 * @author Mariana V. Bravo
 */
public class PolyLineFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new PolylineFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testCreatePolyLine () {

        // Arguments
        Point point1 = new Point(2, 3);
        Point point2 = new Point(3, 7);
        Vector vector1 = new Vector(point1, point2);
        Vector vector2 = new Vector(point2, new Point(5, 5));

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, vector1);

        // Point
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, point1);

        // Vector
        assertSafeNext(factory, vector1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);

        // Return
        assertSafeNext(factory, null, true);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector1, false);
        assertSafeNext(factory, vector2, false);
        assertSafeNext(factory, null, true);
    }
    

    @Test
    public void testCreateClosedPolyLineWithC () {

        // Arguments
        Point point1 = new Point(2, 3);
        Point point2 = new Point(3, 7);
        Vector vector1 = new Vector(point1, point2);
        Vector vector2 = new Vector(point2, new Point(5, 5));

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, vector1);
        assertInvalidNext(factory, "c");

        // Point
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, point1);
        assertInvalidNext(factory, "c");

        // Vector
        assertSafeNext(factory, vector1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);
        assertInvalidNext(factory, "c");

        // Vector
        assertSafeNext(factory, vector1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);
        
        // Close
        assertSafeNext(factory, "c", true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, point1);
        assertInvalidNext(factory, vector1);
        assertInvalidNext(factory, "c");

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector1, false);
        assertSafeNext(factory, vector2, false);
        assertSafeNext(factory, "c", true);
    }

    @Test
    public void testCancelPolyLineCreation () {

        // Arguments
        Point point1 = new Point(2, 3);
        Point point2 = new Point(3, 7);
        Vector vector1 = new Vector(point1, point2);
        Vector vector2 = new Vector(point2, new Point(5, 5));

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector1, false);
        assertCancel(factory, true);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector1, false);
        assertSafeNext(factory, vector2, false);
        assertCancel(factory, true);
    }
    
    @Test
    public void testUndo () {

        // Arguments
        Point point1 = new Point(2, 3);
        Point point2 = new Point(3, 7);
        Vector vector1 = new Vector(point1, point2);
        Vector vector2 = new Vector(point2, new Point(5, 5));

        assertBegin(factory, false);
        assertInvalidNext(factory, "u");
        assertCancel(factory, false);

        // Undo with a point
        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, "u", false);
        assertInvalidNext(factory, vector1);
        assertSafeNext(factory, point1, false);

        // Undo with a vector
        assertSafeNext(factory, vector1, false);
        assertSafeNext(factory, "u", false);
        assertInvalidNext(factory, point1);
        assertSafeNext(factory, vector1, false);

        // Two undos
        assertSafeNext(factory, vector2, false);
        assertSafeNext(factory, "u", false);
        assertSafeNext(factory, "u", false);
        assertInvalidNext(factory, point1);
        assertSafeNext(factory, vector1, false);
        assertSafeNext(factory, null, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
