/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/22, 01:07:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.undo on the br.org.archimedes.undo project.<br>
 */
package br.org.archimedes.undo;

import java.util.Stack;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.undo.internal.Messages;

public class UndoCommand implements Command {

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
        Stack<UndoableCommand> redoHistory = drawing.getRedoHistory();

        if (undoHistory.size() > 0) {
            UndoableCommand command = undoHistory.pop();
            redoHistory.push(command);
            command.undoIt(drawing);
        }
        else {
            throw new IllegalActionException(
                    Messages.notPerformed);
        }

        // TODO Modificar isso para usar o Observer nos bot�es
        // Criar m�todo no drawing void
        // manipulateHistories(Stack<UndoableCommand> toBePoped,
        // Stack<UndoableCommand> toBePushed) throws ...
        // Ele manipula as pilhas e j� notifica os observadores de uma mudan�a
        // nestes estados.
    }
}
