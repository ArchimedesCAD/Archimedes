/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 2, 2009, 1:02:23 PM.<br>
 * It is part of br.org.archimedes.redo on the br.org.archimedes.redo.tests project.<br>
 */

package br.org.archimedes.stub;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.redo.
 * 
 * @author "Hugo Corbucci"
 */
public class StubUndoableCommand extends StubCommand implements UndoableCommand {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
