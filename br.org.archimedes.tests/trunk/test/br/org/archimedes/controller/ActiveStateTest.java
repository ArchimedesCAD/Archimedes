/**
 * This file was created on 2007/05/21, 11:29:58, by nitao. It is part of
 * br.org.archimedes.controller on the br.org.archimedes.tests project.
 */

package br.org.archimedes.controller;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;

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
        factory = EasyMock.createMock(CommandFactory.class);
        state = EasyMock.createMock(IdleState.class);
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
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#getNext()}.
     */
    public void testGetNext () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#gotSelection()}.
     */
    public void testGotSelection () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#nextShouldHandle()}.
     */
    public void testNextShouldHandle () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.controller.ActiveState#changedDrawing(br.org.archimedes.model.Drawing)}.
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
