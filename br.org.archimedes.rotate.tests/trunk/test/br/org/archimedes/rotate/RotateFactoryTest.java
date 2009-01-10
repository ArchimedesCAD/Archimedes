/*
 * Created on 25/09/2006
 */

package br.org.archimedes.rotate;

import java.util.HashSet;
import java.util.Set;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.factories.
 * 
 * @author marivb
 */
public class RotateFactoryTest extends FactoryTester {

    private Set<Element> selection;

    private Point reference;

    private Vector vector;

    private RotateFactory factory;


    @Before
    public void setUp () {

        Element element1 = EasyMock.createMock(Element.class);
        Element element2 = EasyMock.createMock(Element.class);
        factory = new RotateFactory();

        // Arguments
        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);
        reference = new Point(100, 100);
        vector = new Vector(reference, new Point(0, 0));

        Controller controller = br.org.archimedes.Utils.getController();
        controller.deselectAll();
        controller.setActiveDrawing(new Drawing("Test"));
    }

    @After
    public void tearDown () {

        factory = null;
        vector = null;
        reference = null;
        selection = null;
        Controller controller = br.org.archimedes.Utils.getController();
        controller.deselectAll();
        controller.setActiveDrawing(null);
    }

    @Test
    public void testUsualRotate () {

        // Begin
        assertBegin(factory, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, vector);

        // Selection
        assertSafeNext(factory, selection, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, vector);

        // Point
        assertSafeNext(factory, reference, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, reference);

        // Vector
        assertSafeNext(factory, vector, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, vector);

        // Use the same factory
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, vector, true);
    }

    @Test
    public void testRedefineRotate () {

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        // "r"
        assertSafeNext(factory, "r", false);

        // Point
        Point point = new Point(50, 50);
        assertSafeNext(factory, point, false);

        // Vector
        Vector myVector = new Vector(point, new Point(0, -50));
        assertSafeNext(factory, myVector, false);

        // Vector
        assertSafeNext(factory, vector, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, "r");
        assertInvalidNext(factory, vector);

        // Use the same factory
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, "r", false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, myVector, false);
        assertSafeNext(factory, vector, true);
    }

    /**
     * Sends garbage to the command.
     * 
     * @param command
     *            The command to be used.
     */
    private void sendsInvalids (CommandFactory command) {

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
    }

    @Test
    public void testCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, vector, true);
    }
}
