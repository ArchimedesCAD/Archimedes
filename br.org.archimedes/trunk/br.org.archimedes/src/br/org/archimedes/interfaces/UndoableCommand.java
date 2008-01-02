/*
 * Created on 16/08/2006
 */

package br.org.archimedes.interfaces;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author night
 */
public interface UndoableCommand extends Command {

    /**
     * Undoes the action.
     * 
     * @param drawing
     *            The drawing where this command should be undone.
     * @throws IllegalActionException
     *             Thrown if undoing this command is not allowed when called.
     * @throws NullArgumentException
     *             Thrown if the drawing is null.
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException;
}
