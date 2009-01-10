
package br.org.archimedes.polyline.rectangle;

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
}
