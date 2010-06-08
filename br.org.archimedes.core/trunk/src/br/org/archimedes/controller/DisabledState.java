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
public class DisabledState extends InputState {

    private static final String DISABLED = "br.org.archimedes.disabled"; //$NON-NLS-1$

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
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        return Messages.NoDrawing;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        return this;
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

        return idleState.changedDrawing(currentDrawing);
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.
     * CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        return Messages.NoDrawing;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getContextId()
     */
    @Override
    public String getContextId () {

        return DISABLED;
    }
}
