/*
 * Created on 23/03/2006
 */

package br.org.archimedes.controller;

import java.util.List;
import java.util.Observable;

import br.org.archimedes.Constant;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.parser.CommandParser;

/**
 * Belongs to package br.org.archimedes.interpreter.<br>
 * The input controller is responsible for controlling all input. That is, it
 * controlls the flow of input in the program. It executes command factories and
 * sends their result to the Controller.
 * 
 * @author gigante
 */
public class InputController extends Observable {

    private static InputController instance;

    private CommandParser commandParser;

    private InputState currentState;


    /**
     * The constructor initializes the tokens.
     */
    private InputController () {

        commandParser = new CommandParser();
        currentState = new DisabledState();
    }

    /**
     * Singleton pattern
     * 
     * @return The instance of the Interpreter
     */
    public static InputController getInstance () {

        if (instance == null) {
            instance = new InputController();
        }
        return instance;
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
            currentState = currentState.getNext();
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

        Controller controller = Controller.getInstance();
        if ( !controller.isThereActiveDrawing()) {
            controller.setActiveDrawing(drawing);
        }
        else if (this.getCurrentFactory() == null
                || this.getCurrentFactory().isDone()) {
            controller.setActiveDrawing(drawing);
            controller.deselectAll();
        }
        
        currentState = currentState.changedDrawing(drawing);
        setChanged();
        notifyObservers(drawing);
    }

    /**
     * Cancels the current command and sets the current command as selection.
     */
    public void cancelCurrentCommand () {

        String message = currentState.cancel();
        currentState = currentState.getNext();
        printInInterpreter(message);
    }

    /**
     * @return Returns the current command or null if there is no current
     *         command.
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

        return currentState.setCurrentFactory(factory);
    }

    public CommandParser getCommandParser () {

        return commandParser;
    }

    /**
     * @return true if the input controller wants to receive a space character,
     *         false otherwise.
     */
    public boolean wantsSpace () {

        return currentState.wantsSpace();
    }

    public List<String> getAvailableCommands () {

        return commandParser.getCommandList();
    }
}
