/**
 * This file was created on 2007/05/21, 11:29:58, by nitao. It is part of
 * br.org.archimedes.controller on the br.org.archimedes.tests project.
 */

package br.org.archimedes.controller;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.parser.PointParser;

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
        state = new InputState() {

            @Override
            public String cancel () {

                return null;
            }

            @Override
            public InputState changedDrawing (Drawing currentDrawing) {

                return null;
            }

            @Override
            public InputState getNext () {

                return null;
            }

            @Override
            public boolean nextShouldHandle () {

                return false;
            }

            @Override
            public String receiveText (String text) {

                return null;
            }

            @Override
            protected String setCurrentFactory (CommandFactory factory) {

                return null;
            }

        };
        factory = new CommandFactory() {

            private boolean finished = false;


            public String begin () {

                finished = false;
                return null;
            }

            public String cancel () {

                finished = true;
                return null;
            }

            public void drawVisualHelper () {

            }

            public List<Command> getCommands () {

                if ( !finished) {
                    return null;
                }
                Command command = new Command() {

                    public void doIt (Drawing drawing)
                            throws IllegalActionException,
                            NullArgumentException {

                    }
                };
                return Collections.singletonList(command);
            }

            public String getName () {

                return "mock";
            }

            public Parser getNextParser () {

                return new PointParser();
            }

            public boolean isDone () {

                return finished;
            }

            public String next (Object parameter)
                    throws InvalidParameterException {

                finished = true;
                return null;
            }

        };
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
