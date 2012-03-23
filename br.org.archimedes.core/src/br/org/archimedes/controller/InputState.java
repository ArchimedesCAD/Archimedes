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
public abstract class InputState {

    /**
     * Receives a text from the user.
     * 
     * @param text
     *            The text from the user.
     * @return A message to the user.
     */
    public abstract String receiveText (String text);

    /**
     * @return An instance of the next state to be used.
     */
    public abstract InputState getNext ();

    /**
     * Warns the state that a selection has been completed.
     * 
     * @return A message to the user
     */
    public String gotSelection () {

        return null;
    }

    /**
     * @return true if the next state should handle the text, false otherwise.
     */
    public abstract boolean nextShouldHandle ();

    /**
     * Notifies the state that the current drawing has changed.
     * 
     * @param currentDrawing
     *            The current drawing, or null if no drawing is open.
     * @return The state to change to
     */
    public abstract InputState changedDrawing (Drawing currentDrawing);

    /**
     * Cancels anything the state is doing.
     * 
     * @return A message to the user.
     */
    public abstract String cancel ();

    /**
     * @return The current factory or null if there is none.
     */
    public CommandFactory getCurrentFactory () {

        return null;
    }

    /**
     * @return true if the state wants to receive a space character, false
     *         otherwise.
     */
    public boolean wantsSpace () {

        return false;
    }

    /**
     * @param factory
     *            The factory to be activated in the state.
     * @return A nice message to the user.
     */
    protected abstract String setCurrentFactory (CommandFactory factory);

    /**
     * @return A unique string identifying that state context
     */
    public abstract String getContextId ();
}
