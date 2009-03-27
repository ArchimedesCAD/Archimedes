/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/27, 09:19:45, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.offset on the br.org.archimedes.offset.tests project.<br>
 */
package br.org.archimedes.offset;

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

public class OffsetFactoryTest extends FactoryTester {

    private Controller controller;

    private Drawing drawing;

    private CommandFactory factory;

    private Set<Element> selection;


    @Before
    public void setUp () {

        factory = new OffsetFactory();

        controller = br.org.archimedes.Utils.getController();
        drawing = new Drawing("Teste");
        controller.setActiveDrawing(drawing);

        // TODO Fazer o mock ser offsetable
        Element element = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element, drawing);

        selection = new HashSet<Element>();
        selection.add(element);
    }

    @After
    public void tearDown () {

        controller.setActiveDrawing(null);
        controller.deselectAll();
    }

    @Test
    public void testOffset () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, true);

        // Distance
        assertSafeNext(factory, 10.0, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, 10.0);
        assertInvalidNext(factory, true);

        // Selection
        assertSafeNext(factory, selection, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, 10.0);
        assertInvalidNext(factory, selection);

        // true/false
        assertSafeNext(factory, true, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, 10.0);
        assertInvalidNext(factory, selection);

        // true/false
        assertSafeNext(factory, false, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, 10.0);
        assertInvalidNext(factory, selection);

        // Cancel
        assertCancel(factory, false);
    }

    @Test
    public void testCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, 10.0, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, 10.0, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, 10.0, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, true, false, true);
        assertCancel(factory, false);
    }
}
