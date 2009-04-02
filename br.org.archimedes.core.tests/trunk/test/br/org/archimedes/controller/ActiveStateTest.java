/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/21, 11:29:58, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.controller;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.stub.StubCommandFactory;

import junit.framework.TestCase;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author nitao
 */
public class ActiveStateTest extends TestCase {

    private Drawing drawing;

    private InputState state;

    private CommandFactory factory;

    private ActiveState activeSate;


    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp () throws Exception {

        super.setUp();
        drawing = new Drawing("hi");
        factory = new StubCommandFactory();
        state = new StubState();
        activeSate = new ActiveState(state, factory, drawing);
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown () throws Exception {

        super.tearDown();
        drawing = null;
        state = null;
        factory = null;
        activeSate = null;
    }

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#receiveText(java.lang.String)}.
     */
    public void testReceiveText () {

        fail("Not yet implemented"); // TODO Tests for all states.
    }

    /**
     * Test method for {@link br.org.archimedes.controller.ActiveState#getNext()}.
     */
    public void testGetNext () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.controller.ActiveState#gotSelection()}.
     */
    public void testGotSelection () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.controller.ActiveState#nextShouldHandle()}.
     */
    public void testNextShouldHandle () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#changedDrawing(br.org.archimedes.model.Drawing)}
     * .
     */
    public void testChangedDrawing () {

        InputState newState = activeSate.changedDrawing(new Drawing("oi"));
        assertEquals(LockedState.class, newState);
    }

    /**
     * Test method for {@link br.org.archimedes.controller.ActiveState#cancel()}.
     */
    public void testCancel () {

        String string = activeSate.cancel();
        assertNotNull(string);
        assertEquals(IdleState.class, activeSate.getNext().getClass());
        assertFalse(activeSate.nextShouldHandle());
    }
}
