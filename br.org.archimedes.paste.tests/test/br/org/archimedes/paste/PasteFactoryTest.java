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

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.Constant;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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
        Layer defaultLayer = new Layer(Constant.WHITE, "", LineStyle.CONTINUOUS, 1);

        Element element = new StubElement();
        element.setLayer(defaultLayer);
        
        Collection<Element> clipboard = br.org.archimedes.Utils.getWorkspace().getClipboard();
        clipboard.add(element);

        assertBegin(factory, true);

        Element element2 = new StubElement();
        element2.setLayer(defaultLayer);
        clipboard.clear();
        clipboard.add(element2);

        assertBegin(factory, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
