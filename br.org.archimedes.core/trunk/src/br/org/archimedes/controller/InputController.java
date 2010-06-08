/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/23, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.controller;

import java.util.List;
import java.util.Observable;

import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.parser.CommandParser;

/**
 * Belongs to package br.org.archimedes.interpreter.<br>
 * The input controller is responsible for controlling all input. That is, it controlls the flow of
 * input in the program. It executes command factories and sends their result to the Controller.
 * 
 * @author gigante
 */
public class InputController extends Observable {

    private CommandParser commandParser;

    private InputState currentState;

    private IContextActivation previousActivation;


    /**
     * The constructor initializes the tokens.<br>
     * Do NOT use this constructor. It is only here so that the Activator can use it.<br>
     * To aquire the instance, refer to Utils.getInputController()
     */
    public InputController () {

        commandParser = new CommandParser();
        changeState(new DisabledState());
    }

    /**
     * Receives the text and interpretes it.
     */
    public void receiveText (String text) {

        String returnValue = ""; //$NON-NLS-1$
        String trimmedText = text.trim();

        boolean shouldHandleNext;
        do {
            String receivedText = currentState.receiveText(trimmedText);
            if (receivedText != null) {
                returnValue += receivedText;
            }
            shouldHandleNext = currentState.nextShouldHandle();
            changeState(currentState.getNext());
        }
        while (shouldHandleNext);

        if (returnValue != null && !returnValue.trim().equals("")) { //$NON-NLS-1$
            printInInterpreter(returnValue);
        }
    }

    /**
     * Prints a new line in the interpreter containing the parameter.
     * 
     * @param output
     *            The text to be printed.
     */
    public void printInInterpreter (String output) {

        setChanged();
        notifyObservers(output + Constant.NEW_LINE);
    }

    /**
     * @param drawing
     *            True if the interpreter is enabled, false otherwise.
     */
    public void setDrawing (Drawing drawing) {

        Controller controller = br.org.archimedes.Utils.getController();
        if ( !controller.isThereActiveDrawing()) {
            controller.setActiveDrawing(drawing);
        }
        else if (this.getCurrentFactory() == null || this.getCurrentFactory().isDone()) {
            controller.setActiveDrawing(drawing);
            controller.deselectAll();
        }

        changeState(currentState.changedDrawing(drawing));
        setChanged();
        notifyObservers(drawing);
    }

    /**
     * Cancels the current factory and sets the current factory as selection.
     */
    public void cancelCurrentFactory () {

        String message = currentState.cancel();
        changeState(currentState.getNext());
        printInInterpreter(message);
    }

    private void changeState (InputState newState) {

        currentState = newState;
        changeContext(newState.getContextId());
    }

    /**
     * @param newContextId
     *            The new context id
     */
    private void changeContext (String newContextId) {

        IContextService contextService = Utils.getContextService();
        if (previousActivation != null) {
            contextService.deactivateContext(previousActivation);
        }
        previousActivation = contextService.activateContext(newContextId);
    }

    /**
     * @return Returns the current command or null if there is no current command.
     */
    public CommandFactory getCurrentFactory () {

        return currentState.getCurrentFactory();
    }

    /**
     * Set the current command to the specified command and execute it.
     * 
     * @param factory
     *            The command to be executed.
     * @return The message that should be printed.
     */
    public String setCurrentFactory (CommandFactory factory) {

        if (factory == null) {
            cancelCurrentFactory();
            return null;
        }
        return currentState.setCurrentFactory(factory);
    }

    public CommandParser getCommandParser () {

        return commandParser;
    }

    /**
     * @return true if the input controller wants to receive a space character, false otherwise.
     */
    public boolean wantsSpace () {

        return currentState.wantsSpace();
    }

    public List<String> getAvailableCommands () {

        return commandParser.getCommandList();
    }
}
