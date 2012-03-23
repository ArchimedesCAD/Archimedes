/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/16, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
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
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException;

    /**
     * @param command
     *            A command
     * @return true if this command can be merged to the received one
     */
    public boolean canMergeWith (UndoableCommand command);
}
