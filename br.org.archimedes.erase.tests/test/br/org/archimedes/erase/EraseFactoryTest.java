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

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Selection;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
    public void canErasePasingSelection () {

        // Arguments
        Element element1 = new StubElement();
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new StubElement();
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

    @Test
    public void canEraseWithPresetSelection () {

        // Arguments
        Element element1 = new StubElement();
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new StubElement();
        putSafeElementOnDrawing(element2, drawing);
        Selection selection = new Selection();
        selection.add(element2);
        drawing.setSelection(selection);

        // Begin
        assertBegin(factory, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);

        // Again
        assertBegin(factory, true);
    }

    public void canCancelAtAnytimeWithoutPreset () throws InvalidArgumentException {

        Element element = new StubElement();
        putSafeElementOnDrawing(element, drawing);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        Set<Element> selection = new HashSet<Element>();
        selection.add(element);
        assertSafeNext(factory, selection, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
