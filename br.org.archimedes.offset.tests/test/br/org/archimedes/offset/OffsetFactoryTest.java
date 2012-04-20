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

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class OffsetFactoryTest extends FactoryTester {

    
    /**
     * Belongs to package br.org.archimedes.offset.
     *
     * @author "Hugo Corbucci"
     */
    public class OffsetableStubElement extends StubElement implements Offsetable {

        /* (non-Javadoc)
         * @see br.org.archimedes.model.Offsetable#cloneWithDistance(double)
         */
        public Element cloneWithDistance (double distance) throws InvalidParameterException {

            return null;
        }

        /* (non-Javadoc)
         * @see br.org.archimedes.model.Offsetable#isPositiveDirection(br.org.archimedes.model.Point)
         */
        public boolean isPositiveDirection (Point point) throws NullArgumentException {

            return false;
        }
    }

    private Controller controller;

    private Drawing drawing;

    private CommandFactory factory;

    @Before
    public void setUp () {

        factory = new OffsetFactory();

        controller = br.org.archimedes.Utils.getController();
        drawing = new Drawing("Teste");
        controller.setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        controller.setActiveDrawing(null);
        controller.deselectAll();
    }

    @Test
    public void testOffset () {
        Element element = new OffsetableStubElement();
        putSafeElementOnDrawing(element, drawing);

        HashSet<Element> selection = new HashSet<Element>();
        selection.add(element);

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
    public void testOffsetWithSelection () {
        Element element = new OffsetableStubElement();
        putSafeElementOnDrawing(element, drawing);

        Selection selection = new Selection();
        selection.add(element);
        drawing.setSelection(selection);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, true);

        // Distance
        assertSafeNext(factory, 10.0, false);

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
        Element element = new OffsetableStubElement();
        putSafeElementOnDrawing(element, drawing);

        HashSet<Element> selection = new HashSet<Element>();
        selection.add(element);

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
    
    @Test
    public void testCancelWithSelection () {
        Element element = new OffsetableStubElement();
        putSafeElementOnDrawing(element, drawing);

        Selection selection = new Selection();
        selection.add(element);
        drawing.setSelection(selection);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, 10.0, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, 10.0, false);
        assertSafeNext(factory, true, false, true);
        assertCancel(factory, false);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
