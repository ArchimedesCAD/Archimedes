/*
 * Created on 22/08/2006
 */

package br.org.archimedes.undo;

import java.util.Stack;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;

public class UndoCommand implements Command {

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.commands.Command#doIt(com.tarantulus.archimedes.model.Drawing)
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
