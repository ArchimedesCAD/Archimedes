
package br.org.archimedes.circle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.tests.commands.
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
    public void testCreateCircle () {

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
}
