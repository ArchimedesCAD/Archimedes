/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:05:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.copypaste on the br.org.archimedes.copypaste.tests project.<br>
 */
package br.org.archimedes.copypaste;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.model.Vector;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CopyPasteFactoryTest extends FactoryTester {

    private Drawing drawing;

    private Controller controller;

    private CommandFactory factory;

    private Point point1;

    private Vector vector;

    private Vector vector2;


    @Before
    public void setUp () {

        factory = new CopyPasteFactory();

        drawing = new Drawing("Teste");
        controller = br.org.archimedes.Utils.getController();
        controller.setActiveDrawing(drawing);

        // Arguments
        point1 = new Point(0, 0);
        Point point2 = new Point(10, 10);
        vector = new Vector(point1, point2);
        vector2 = new Vector(point2, new Point(15, 15));
    }

    @After
    public void tearDown () {

        controller.deselectAll();
        controller.setActiveDrawing(null);
        Utils.getWorkspace().getClipboard().clear();
    }

    @Test
    public void canCopyPastePassingSelection () {
        Set<Element> selection = new HashSet<Element>();
        selection.add(new StubElement());

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);
        assertInvalidNext(factory, vector);

        // Selection
        assertSafeNext(factory, selection, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, vector);

        // Reference point
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Vector
        assertSafeNext(factory, vector, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Another vector
        assertSafeNext(factory, vector2, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Cancel
        assertCancel(factory, false);

        // Again
        factory.begin();
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertSafeNext(factory, null, true, false);
    }
    

    @Test
    public void canCopyPasteWithPresetSelection () {
        Selection preset = new Selection();
        preset.add(new StubElement());
        drawing.setSelection(preset);
        
        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, preset);
        assertInvalidNext(factory, vector);

        // Reference point
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, preset);
        assertInvalidNext(factory, point1);

        // Vector
        assertSafeNext(factory, vector, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, preset);
        assertInvalidNext(factory, point1);

        // Another vector
        assertSafeNext(factory, vector2, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, preset);
        assertInvalidNext(factory, point1);

        // Cancel
        assertCancel(factory, false);

        drawing.setSelection(preset);
        // Again
        factory.begin();
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertSafeNext(factory, null, true, false);
    }

    @Test
    public void canCancelWithoutPreset () {
        Set<Element> selection = new HashSet<Element>();
        selection.add(new StubElement());

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertCancel(factory, false);
    }
    
    @Test
    public void canCancelWithPreset () {
        Selection preset = new Selection();
        preset.add(new StubElement());
        drawing.setSelection(preset);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertCancel(factory, false);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
