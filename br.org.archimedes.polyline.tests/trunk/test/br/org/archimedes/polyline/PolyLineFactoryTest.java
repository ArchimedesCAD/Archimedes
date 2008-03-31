
package br.org.archimedes.polyline;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.tests.commands.
 * 
 * @author marivb
 */
public class PolyLineFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new PolylineFactory();
        drawing = new Drawing("Drawing");
        Controller.getInstance().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        Controller.getInstance().setActiveDrawing(null);
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
}