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
        state = new IdleState(null);
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

    // TODO Test active state text receiving

    // TODO Test active state next state

    // TODO Test active state selection listener

    // TODO Test active state argument delegation

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#changedDrawing(br.org.archimedes.model.Drawing)}
     * .
     */
    public void testChangedDrawing () {

        InputState newState = activeSate.changedDrawing(new Drawing("oi"));
        assertEquals(LockedState.class, newState.getClass());
    }

    /**
     * Test method for {@link br.org.archimedes.controller.ActiveState#cancel()}.
     */
    public void testCancel () {

        String string = activeSate.cancel();
        assertNotNull(string);
        assertTrue(activeSate.getNext() instanceof IdleState);
        assertFalse(activeSate.nextShouldHandle());
    }
}
