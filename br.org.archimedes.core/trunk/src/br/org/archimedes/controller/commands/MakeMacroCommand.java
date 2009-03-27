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
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
 */
public class MakeMacroCommand implements Command {

    private int commandCount;


    /**
     * @param commandCount
     */
    public MakeMacroCommand (int commandCount) {

        this.commandCount = commandCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        Stack<UndoableCommand> undoHistory = drawing.getUndoHistory();

        if (undoHistory.size() >= commandCount) {
            List<UndoableCommand> cmds = new ArrayList<UndoableCommand>();
            while (commandCount > 0) {
                UndoableCommand command = undoHistory.pop();
                cmds.add(0, command);
                commandCount--;
            }

            MacroCommand macro = new MacroCommand(cmds);
            undoHistory.push(macro);
        }
        else {
            // TODO Mensagem?
            throw new IllegalActionException();
        }
    }
}
