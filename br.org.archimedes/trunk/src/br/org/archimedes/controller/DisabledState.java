/*
 * Created on 02/10/2006
 */

package br.org.archimedes.controller;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author night
 */
public class DisabledState extends InputState {

    private IdleState idleState;


    /**
     * @param previousState
     *            The previous input state.
     */
    public DisabledState () {

        idleState = new IdleState(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        return Messages.NoDrawing;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    public boolean nextShouldHandle () {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#changedDrawing(br.org.archimedes.model.Drawing)
     */
    public InputState changedDrawing (Drawing currentDrawing) {

        return idleState.changedDrawing(currentDrawing);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        return Messages.NoDrawing;
    }
}
