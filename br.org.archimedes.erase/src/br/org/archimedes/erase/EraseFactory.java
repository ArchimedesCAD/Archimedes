/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Gustavo Menezes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/04/04, 22:54:26, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.erase on the br.org.archimedes.erase project.<br>
 */
package br.org.archimedes.erase;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.commands.
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
     * 
     * @see br.org.archimedes.factories.SelectorFactory#finishFactory(java.util.Set)
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

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
