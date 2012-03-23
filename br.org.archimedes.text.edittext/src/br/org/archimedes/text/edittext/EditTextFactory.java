/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Gustavo Menezes - initial API and implementation<br>
 * Hugo Corbucci, Marcos P. Moreti, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/04/04, 20:35:13, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.text.edittext on the br.org.archimedes.text.edittext
 * project.<br>
 */

package br.org.archimedes.text.edittext;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.text.edittext.
 * 
 * @author gigante
 */
public class EditTextFactory extends SelectorFactory {

    private UndoableCommand command;

    
    public EditTextFactory() {
		super();
	}

    protected String getCancelMessage () {

        return Messages.Factory_Cancel;
    }

    public String getName () {

        return "edittext"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
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
     * @see br.org.archimedes.factories.SelectorFactory#finishFactory(java.util.Set)
     */
    @Override
    protected String finishFactory (Set<Element> selection) throws InvalidParameterException {

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
    private Text tryGetText (Set<Element> selection) throws InvalidParameterException {

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

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }

}
