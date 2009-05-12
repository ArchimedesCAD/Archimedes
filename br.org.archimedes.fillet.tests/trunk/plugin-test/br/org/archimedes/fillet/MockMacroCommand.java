/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 05/12/2009, 13:15:34.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */

package br.org.archimedes.fillet;

import java.util.Collections;

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.stub.StubUndoableCommand;

/**
 * @author Ricardo Sider
 */
public class MockMacroCommand extends MacroCommand {

    private boolean calledDoIt = false;

    private boolean calledUndoIt = false;


    public MockMacroCommand () throws NullArgumentException, IllegalActionException {

        super(Collections.singletonList(new StubUndoableCommand()));
    }

    @Override
    public void doIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        calledDoIt = true;
    }

    @Override
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        calledUndoIt = true;
    }

    public boolean calledDoIt () {

        return calledDoIt;
    }

    public boolean calledUndoIt () {

        return calledUndoIt;
    }
}
