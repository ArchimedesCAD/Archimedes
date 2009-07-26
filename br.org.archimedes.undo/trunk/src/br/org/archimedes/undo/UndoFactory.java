/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/08, 22:53:00, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.undo on the br.org.archimedes.undo project.<br>
 */
package br.org.archimedes.undo;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.undo.internal.Messages;

import java.util.ArrayList;
import java.util.List;

public class UndoFactory implements CommandFactory {

    private Command command;


    public String begin () {

        setCommand(new UndoCommand());
        return Messages.UndoPerformed;
    }

    /**
     * @param command
     *            The command to be set
     */
    protected void setCommand (Command command) {

        this.command = command;
    }

    public String next (Object parameter) throws InvalidParameterException {

        return null;
    }

    public boolean isDone () {

        return true;
    }

    public String cancel () {

        return null;
    }

    public void drawVisualHelper () {

        // No visual helper
    }

    public String getName () {

        return "undo"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        return null;
    }

    public List<Command> getCommands () {

        List<Command> cmds = null;
        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            setCommand(null);
        }
        return cmds;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return false;
    }
}
