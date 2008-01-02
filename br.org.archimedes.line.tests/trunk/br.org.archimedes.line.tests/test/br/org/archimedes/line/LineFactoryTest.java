
package br.org.archimedes.line;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.tests.commands.
 * 
 * @author marivb
 */
public class LineFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Point point;

    private Vector vector1;

    private Vector vector2;


    @Before
    public void setUp () {

        point = new Point(2, 3);
        vector1 = new Vector(new Point(0, 0), point);
        vector2 = new Vector(point, new Point(3, 4));
        factory = new LineFactory();
    }

    @Test
    public void testCreateLine () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, vector1);

        // First point
        assertSafeNext(factory, point, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point);

        // Vector
        assertSafeNext(factory, vector1, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point);

        // Another vector (multi-line factory)
        assertSafeNext(factory, vector2, false, true);

        // Null (enter)
        assertSafeNext(factory, null, true, false);

        // All over again
        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertSafeNext(factory, null, true, false);
    }

    @Test
    public void testCancelLineCreation () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertSafeNext(factory, null, true, false);
    }
}
