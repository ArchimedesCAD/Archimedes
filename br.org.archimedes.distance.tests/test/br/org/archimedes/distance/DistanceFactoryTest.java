/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:07:13, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.distance on the br.org.archimedes.distance.tests project.<br>
 */
package br.org.archimedes.distance;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.distance.
 */
public class DistanceFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new DistanceFactory();
        drawing = new Drawing("Drawing");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        factory = null;
        drawing = null;
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testDistCommand () {

        // Arguments
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 5);
        Vector vector = new Vector(p1, p2);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, vector);

        // First point
        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, p2);

        // Vector
        assertSafeNext(factory, vector, true, false);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, vector, true, false);
    }

    @Test
    public void testCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, new Point(10, 0), false);
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

        String message = null;
        try {
            message = factory.next(vector);
        }
        catch (InvalidParameterException e) {
            Assert.fail("Should not throw any exception");
        }
        Assert.assertNotNull("Returned message should not be null", message);
        Assert.assertEquals("Message should be the distance", "" + 5.0, message);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
