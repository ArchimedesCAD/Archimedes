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

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.actions.SelectionCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author Hugo Corbucci
 */
public class SelectionState extends InputState {

    private static final String SELECTION_CONTEXT = "br.org.archimedes.selection"; //$NON-NLS-1$

    private InputState previous;

    private SelectionCommand command;

    private boolean nextShould;


    /**
     * @param previousState
     *            The previous state
     */
    public SelectionState (InputState previousState) {

        this.previous = previousState;
        command = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        nextShould = false;
        if (Utils.isPoint(text)) {
            Point point = Utils.getPointCoordinates(text);
            if (command == null) {
                command = new SelectionCommand(point);
                command.execute();
            }
            else {
                command.execute(point);
            }
        }
        else {
            cancel();
            nextShould = true;
        }

        Integer selectionCount = 0;
        String returnValue = null;
        if (command != null && command.isDone() && !nextShould) {
            try {
                selectionCount = br.org.archimedes.Utils.getController().getActiveDrawing()
                        .getSelection().getSelectedElements().size();
            }
            catch (NoActiveDrawingException e) {
                changedDrawing(null);
            }
            returnValue = selectionCount.toString() + " " //$NON-NLS-1$
                    + Messages.SelectionCount;
            String gotSelection = previous.gotSelection();
            if (gotSelection != null) {
                returnValue = gotSelection + Constant.NEW_LINE + returnValue;
            }
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        InputState nextState;
        if (command == null || !command.isDone()) {
            nextState = this;
        }
        else {
            nextState = previous;
        }
        return nextState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    public boolean nextShouldHandle () {

        return nextShould;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#changedDrawing(br.org.archimedes.model.Drawing)
     */
    public InputState changedDrawing (Drawing currentDrawing) {

        cancel();
        return previous.changedDrawing(currentDrawing);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        if (command != null) {
            command.cancel();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#getCurrentFactory()
     */
    public CommandFactory getCurrentFactory () {

        return previous.getCurrentFactory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        cancel();
        nextShould = false;
        return previous.setCurrentFactory(factory);
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getContextId()
     */
    @Override
    public String getContextId () {

        return SELECTION_CONTEXT;
    }
}
