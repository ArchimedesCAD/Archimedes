/*
 * Created on 30/03/2006
 */

package br.org.archimedes.orto;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
 * 
 * @author night
 */
public class OrtoFactory implements CommandFactory {

    private Command command;


    public String begin () {

        boolean isOn = Workspace.getInstance().isOrtoOn();
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
}
