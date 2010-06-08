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

import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author Hugo Corbucci
 */
public class ActiveState extends InputState {

    private static final String ACTIVE = "br.org.archimedes.active"; //$NON-NLS-1$

    private CommandFactory factory;

    private InputState previousState;

    private Parser currentParser;

    private InputState nextState;

    private boolean nextShould;

    private Drawing myDrawing;


    /**
     * @param state
     *            The previous state
     * @param factory
     *            The factory to be executed. It was already begun.
     * @param drawing
     *            The drawing on which the factory will be executed
     */
    public ActiveState (InputState state, CommandFactory factory, Drawing drawing) {

        System.setProperty("ActiveState", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        this.previousState = state;
        this.factory = factory;
        this.myDrawing = drawing;
        this.currentParser = factory.getNextParser();
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        String returnValue = null;
        if (factory.isDone()) {
            nextState = previousState;
            nextShould = true;
        }
        else {
            nextShould = false;
            nextState = this;
            returnValue = factory.getName().toUpperCase() + ": "; //$NON-NLS-1$
            try {
                String handlesToParser = handlesToParser(text);
                if (handlesToParser == null) {
                    returnValue = null;
                }
                else {
                    returnValue += handlesToParser;
                }
            }
            catch (InvalidParameterException e) {
                if (Utils.isPoint(text)) {
                    nextState = new SelectionState(this);
                    nextShould = true;
                }
                else {
                    returnValue += e.getMessage();
                }
            }
            if (returnValue != null) {
                returnValue = returnValue.replaceAll(Constant.NEW_LINE, Constant.NEW_LINE
                        + factory.getName().toUpperCase() + ": "); //$NON-NLS-1$
            }
            if (factory.isDone()) {
                if (returnValue != null) {
                    returnValue += Constant.NEW_LINE;
                }
                else {
                    returnValue = ""; //$NON-NLS-1$
                }
                returnValue += Messages.Waiting;
                nextState = previousState;
            }
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        if (nextState == previousState) {
            System.setProperty("ActiveState", "false"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return nextState;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#gotSelection()
     */
    public String gotSelection () {

        String returnMessage = null;
        String handlesToParser = null;
        try {
            handlesToParser = handlesToParser(null);
        }
        catch (InvalidParameterException e) {
            // Ignore it in this case
        }
        if (handlesToParser != null) {
            returnMessage = factory.getName().toUpperCase() + ": " //$NON-NLS-1$
                    + handlesToParser;
        }
        return returnMessage;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    public boolean nextShouldHandle () {

        return nextShould;
    }

    /**
     * @param text
     *            Text to be handled by the parser
     * @return A nice message for the user.
     * @throws InvalidParameterException
     *             In case the parser does not accept the text parameter.
     */
    private String handlesToParser (String text) throws InvalidParameterException {

        String returnValue = null;
        returnValue = currentParser.next(text);
        if (currentParser.isDone()) {
            try {
                returnValue = factory.next(currentParser.getParameter());
                currentParser = factory.getNextParser();
                handlesGeneratedCommands();
            }
            catch (InvalidParameterException e) {
                // Should not happen
                System.out.println("Probably the command gave a wrong parser."); //$NON-NLS-1$
                returnValue = factory.cancel();
                e.printStackTrace();
                nextState = previousState;
            }
            catch (IllegalActionException e) {
                returnValue = e.getMessage();
            }
        }

        return returnValue;
    }

    /**
     * Handles the commands that the current factory might have produced.
     * 
     * @throws IllegalActionException
     *             Thrown if a command is illegal
     */
    private void handlesGeneratedCommands () throws IllegalActionException {

        List<Command> commands = factory.getCommands();
        try {
            br.org.archimedes.Utils.getController().execute(commands);
        }
        catch (NoActiveDrawingException e) {
            nextState = previousState.changedDrawing(null);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#changedDrawing(br.org.archimedes.model.Drawing)
     */
    public InputState changedDrawing (Drawing currentDrawing) {

        InputState state = this;
        if (currentDrawing == null) {
            cancel();
            state = previousState.changedDrawing(currentDrawing);
        }
        else if (currentDrawing != myDrawing) {
            state = new LockedState(this, myDrawing);
        }
        return state;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        String message = factory.getName().toUpperCase() + ": " //$NON-NLS-1$
                + factory.cancel();
        try {
            handlesGeneratedCommands();
        }
        catch (IllegalActionException e) {
            message = e.getMessage() + Constant.NEW_LINE + message;
        }
        message += Constant.NEW_LINE + Messages.Waiting;
        nextState = previousState;
        return message;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getCurrentFactory()
     */
    public CommandFactory getCurrentFactory () {

        return factory;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#wantsSpace()
     */
    public boolean wantsSpace () {

        return ("text".equals(factory.getName())); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.
     * CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        nextState = this;
        nextShould = false;
        return factory.getName().toUpperCase() + ": " + Messages.Active; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getContextId()
     */
    @Override
    public String getContextId () {

        return ACTIVE;
    }
}
