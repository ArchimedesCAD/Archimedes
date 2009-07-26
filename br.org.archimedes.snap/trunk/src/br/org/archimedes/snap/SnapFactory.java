/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/30, 17:40:08, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.snap on the br.org.archimedes.snap project.<br>
 */
package br.org.archimedes.snap;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author night
 */
public class SnapFactory implements CommandFactory {

    private Command command;


    public String begin () {

        boolean isOn = br.org.archimedes.Utils.getWorkspace().isSnapOn();
        command = new SnapCommand();

        return !isOn ? Messages.SnapOn : Messages.SnapOff;
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

        return "snap"; //$NON-NLS-1$
    }

    public Parser getNextParser () {

        return null;
    }

    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
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
