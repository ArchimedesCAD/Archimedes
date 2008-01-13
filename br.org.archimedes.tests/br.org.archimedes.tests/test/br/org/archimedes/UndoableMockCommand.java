/**
 * This file was created on 2007/04/23, 07:35:05, by nitao. It is part of
 * br.org.archimedes on the br.org.archimedes.tests project.
 */

package br.org.archimedes;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author nitao
 */
public class UndoableMockCommand implements UndoableCommand {

    /**
     * @see br.org.archimedes.interfaces.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        // Does nothing
    }

    /**
     * @see br.org.archimedes.interfaces.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        // Does nothing either
    }
}
