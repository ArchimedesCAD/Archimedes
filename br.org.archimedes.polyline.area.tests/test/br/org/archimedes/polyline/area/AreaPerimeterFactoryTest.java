/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2007/04/19, 10:22:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline.area on the
 * br.org.archimedes.polyline.area.tests project.<br>
 */

package br.org.archimedes.polyline.area;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.polyline.area.
 */
public class AreaPerimeterFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new AreaPerimeterFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testAreaPerimeterFromClosingPoints () {

        // Arguments
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 5);
        Point p3 = new Point(2.5, 2.5);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new Vector(p1, p2));
        assertInvalidNext(factory, "c");

        // First point
        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);
        assertInvalidNext(factory, "c");

        // Second Point
        assertSafeNext(factory, new Vector(p1, p2), false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p1);
        assertInvalidNext(factory, "c");

        // Third Point
        assertSafeNext(factory, new Vector(p2, p3), false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);

        // Done
        assertSafeNext(factory, null, true, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new Vector(p1, p2));
        assertInvalidNext(factory, p1);
        assertInvalidNext(factory, "c");

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, new Vector(p1, p2), false);
        assertSafeNext(factory, new Vector(p2, p3), false);
        assertSafeNext(factory, new Vector(p3, p1), false); // Should also work if I close it manually
        assertSafeNext(factory, null, true, false);
    }

    @Test
    public void testAreaPerimeterClosingWithC () {

        // Arguments
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 5);
        Point p3 = new Point(2.5, 2.5);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new Vector(p1, p2));
        assertInvalidNext(factory, "c");

        // First point
        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);
        assertInvalidNext(factory, "c");

        // Second Point
        assertSafeNext(factory, new Vector(p1, p2), false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);
        assertInvalidNext(factory, "c");

        // Third Point
        assertSafeNext(factory, new Vector(p2, p3), false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p1);

        // Closing
        assertSafeNext(factory, "c", true, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new Vector(p1, p2));
        assertInvalidNext(factory, p1);
        assertInvalidNext(factory, "c");

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, new Vector(p1, p2), false);
        assertSafeNext(factory, new Vector(p2, p3), false);
        assertSafeNext(factory, "c", true, false);
    }

    @Test
    public void testCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        Point p1 = new Point(10, 0);
        assertSafeNext(factory, p1, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        Point p2 = new Point(0, 0);
        assertSafeNext(factory, new Vector(p1, p2), false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, new Vector(p1, p2), false);
        assertSafeNext(factory, new Vector(p2, new Point(5, 5)), false);
        assertCancel(factory, false);
    }

    @Test
    public void testAnswer () {

        // Arguments
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 5);
        Vector vector = new Vector(p1, p2);

        // Go to last
        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, vector, false);
        assertSafeNext(factory, new Vector(p2, new Point(5, 5)), false);

        String message = null;
        try {
            message = factory.next(null);
        }
        catch (InvalidParameterException e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertNotNull("Returned message should not be null", message);
        Assert.assertEquals("Message should be the area and the perimeter", "Area 12.5, Perimeter "
                + (10 + 5 * Math.sqrt(2)), message);

    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
