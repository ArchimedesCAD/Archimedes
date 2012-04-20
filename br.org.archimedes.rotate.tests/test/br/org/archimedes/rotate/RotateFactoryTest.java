/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/25, 10:12:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rotate on the br.org.archimedes.rotate.tests project.<br>
 */
package br.org.archimedes.rotate;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.rotate.
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

        Element element1 = new StubElement();
        Element element2 = new StubElement();
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
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
