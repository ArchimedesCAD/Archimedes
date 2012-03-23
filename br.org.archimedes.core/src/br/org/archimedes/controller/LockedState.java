/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/10/02, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.controller;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author Hugo Corbucci
 */
public class LockedState extends InputState {

    private static final String LOCKED = "br.org.archimedes.locked"; //$NON-NLS-1$

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
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        return message;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        return canceled ? previous.getNext() : this;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    public boolean nextShouldHandle () {

        return false;
    }

    /*
     * (non-Javadoc)
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
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        canceled = true;
        return previous.cancel();
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.
     * CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        return message;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getContextId()
     */
    @Override
    public String getContextId () {

        return LOCKED;
    }
}
