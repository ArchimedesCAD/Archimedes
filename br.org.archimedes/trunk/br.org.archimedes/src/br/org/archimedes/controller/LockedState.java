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
public class LockedState extends InputState {

    private String message;

    private InputState previous;

    private Drawing activeDrawing;

    private boolean canceled;


    /**
     * @param previousState
     *            The previous input state.
     * @param drawing
     *            A reference to the active drawing
     */
    public LockedState (InputState previousState, Drawing drawing) {

        this.activeDrawing = drawing;
        this.message = Messages.Locked + drawing.getTitle();
        this.previous = previousState;
        canceled = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        return message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        return canceled ? previous.getNext() : this;
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

        InputState state = null;
        if (currentDrawing == null) {
            state = previous.changedDrawing(currentDrawing);
        }
        else {
            state = (activeDrawing == currentDrawing) ? previous : this;
        }
        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        canceled = true;
        return previous.cancel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        return message;
    }
}
