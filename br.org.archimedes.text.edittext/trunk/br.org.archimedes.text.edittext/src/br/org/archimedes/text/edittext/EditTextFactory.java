/*
 * Created on 04/04/2006
 */

package br.org.archimedes.text.edittext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
 * 
 * @author gigante
 */
public class EditTextFactory extends SelectorFactory {

    private UndoableCommand command;

    public EditTextFactory () {
    }
    
    public EditTextFactory (Point point) throws InvalidArgumentException {

        Controller controller = Controller.getInstance();
        Text textUnder;
        try {
            textUnder = (Text) controller.getElementUnder(point, Text.class);
            if (textUnder == null) {
                throw new InvalidArgumentException();
            }
            command = new EditTextCommand(textUnder);
        }
        catch (NoActiveDrawingException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }

    }

    protected String getCancelMessage () {

        return Messages.Factory_Cancel;
    }

    public String getName() {
        return "editText"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.SelectorFactory#finishFactory(java.util.Set)
     */
    @Override
    protected String finishFactory (Set<Element> selection)
            throws InvalidParameterException {

        String message = null;
        try {
            Text text = tryGetText(selection);
            command = new EditTextCommand(text);
            message = Messages.Factory_Edited;
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }

        return message;
    }

    /**
     * @param selection
     *            The selection to check.
     * @return The text element found.
     * @throws InvalidParameterException
     *             In case the selection does not contain at least one text.
     */
    private Text tryGetText (Set<Element> selection)
            throws InvalidParameterException {

        if (selection == null || selection.isEmpty()) {
            throw new InvalidParameterException(Messages.Factory_InvalidSelection);
        }

        Text text = null;
        for (Element element : selection) {
            if (element.getClass() == Text.class) {
                text = (Text) element;
                break;
            }
        }
        if (text == null) {
            throw new InvalidParameterException(Messages.Factory_InvalidSelection);
        }
        return text;
    }

}
