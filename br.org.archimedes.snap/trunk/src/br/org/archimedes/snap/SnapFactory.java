/*
 * Created on 30/03/2006
 */

package br.org.archimedes.snap;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
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
}
