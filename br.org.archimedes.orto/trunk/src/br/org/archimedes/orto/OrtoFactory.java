/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/30, 11:08:12, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.orto on the br.org.archimedes.orto project.<br>
 */

package br.org.archimedes.orto;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.commands.
 * 
 * @author night
 */
public class OrtoFactory implements CommandFactory {

    private Command command;


    public String begin () {

        boolean isOn = br.org.archimedes.Utils.getWorkspace().isOrtoOn();
        command = new OrtoCommand();

        return !isOn ? Messages.OrtoOn : Messages.OrtoOff;
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

        return "orto"; //$NON-NLS-1$
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
