/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:10:13, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.paste on the br.org.archimedes.paste.tests project.<br>
 */
package br.org.archimedes.paste;

import java.util.Collection;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

public class PasteFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Controller controller;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new PasteFactory();
        controller = br.org.archimedes.Utils.getController();
        drawing = new Drawing("Teste");
        controller.setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        drawing = null;
        controller.deselectAll();
        controller.setActiveDrawing(null);
        br.org.archimedes.Utils.getWorkspace().getClipboard().clear();
    }

    @Test
    public void testPaste () {

        Element element = EasyMock.createMock(Element.class);
        Collection<Element> clipboard = br.org.archimedes.Utils.getWorkspace().getClipboard();
        clipboard.add(element);

        assertBegin(factory, true);

        Element element2 = EasyMock.createMock(Element.class);
        clipboard.clear();
        clipboard.add(element2);

        assertBegin(factory, true);
    }
}
