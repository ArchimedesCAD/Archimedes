/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:07:39, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.erase on the br.org.archimedes.erase.tests project.<br>
 */
package br.org.archimedes.erase;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

public class EraseFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);

        factory = new EraseFactory();
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testErase () {

        // Arguments
        Element element1 = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element1, drawing);
        // TODO Usar o ponto new Point(1, 1)
        Element element2 = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element2, drawing);
        Set<Element> selection = new HashSet<Element>();
        selection.add(element2);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Selection
        assertSafeNext(factory, selection, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, true);
    }

    public void testCancel () throws InvalidArgumentException {

        Element element = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element, drawing);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        Set<Element> selection = new HashSet<Element>();
        selection.add(element);
        assertSafeNext(factory, selection, true);
    }
}
