/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. da Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/08/25, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core
 * project.<br>
 */

package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
 */
public class MacroCommand implements UndoableCommand {

    private List<UndoableCommand> commands;


    /**
     * Contructor.
     * 
     * @param commandList
     *            The list of commands to be executed by this macro.
     * @throws NullArgumentException
     *             In case the command list is null
     * @throws IllegalActionException
     *             In case the command list is empty<br>
     *             TODO Change to Invalid Parameter?
     */
    public MacroCommand (List<? extends UndoableCommand> commandList) throws NullArgumentException,
            IllegalActionException {

        if (commandList == null) {
            throw new NullArgumentException();
        }
        if (commandList.isEmpty()) {
            throw new IllegalActionException();
        }

        commands = new ArrayList<UndoableCommand>(commandList);
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        for (UndoableCommand cmd : commands) {
            cmd.doIt(drawing);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        List<UndoableCommand> reversedCommands = new ArrayList<UndoableCommand>(commands);
        Collections.reverse(reversedCommands);
        for (UndoableCommand cmd : reversedCommands) {
            cmd.undoIt(drawing);
        }
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.
     * UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        boolean mergeable = true;
        for (UndoableCommand myCommand : commands) {
            mergeable = mergeable && command.canMergeWith(myCommand);
        }
        
        return mergeable;
    }
}
