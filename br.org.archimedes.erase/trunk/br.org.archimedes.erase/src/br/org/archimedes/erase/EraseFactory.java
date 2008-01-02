/*
 * Created on 04/04/2006
 */

package br.org.archimedes.erase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
 * 
 * @author gigante
 */
public class EraseFactory extends SelectorFactory {

    private UndoableCommand command;


    protected String getCancelMessage () {

        return Messages.Canceled;
    }

    public String getName () {

        return "erase"; //$NON-NLS-1$
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
    protected String finishFactory (Set<Element> selection) {

        try {
            command = new PutOrRemoveElementCommand(selection, true);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        return Messages.Erased;
    }
}
