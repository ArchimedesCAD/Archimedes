/*
 * Created on 04/04/2006
 */

package br.org.archimedes.polyline.explode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.polyline.Polyline;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
 * 
 * @author gigante
 */
public class ExplodeFactory extends SelectorFactory {

    private UndoableCommand command;


    protected String getCancelMessage () {

        return Messages.Canceled;
    }

    public String getName () {

        return "explode"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.SelectorFactory#finishFactory(java.util.Set)
     */
    @Override
    protected String finishFactory (Set<Element> selection) {

        String returnValue = Messages.NoPolylineSelected;
        try {
            List<UndoableCommand> commands = new ArrayList<UndoableCommand>();

            for (Element element : selection) {
                if (Polyline.class.isAssignableFrom(element.getClass())) {
                    Polyline polyLine = (Polyline) element;

                    List<Line> lines = polyLine.getLines();
                    Collection<Element> toInsert = new ArrayList<Element>(lines);

                    UndoableCommand insertCmd = new PutOrRemoveElementCommand(
                            toInsert, false);
                    UndoableCommand removeCmd = new PutOrRemoveElementCommand(
                            element, true);

                    commands.add(insertCmd);
                    commands.add(removeCmd);
                }
            }

            if (commands.size() > 0) {
                command = new MacroCommand(commands);
                returnValue = Messages.Exploded;
            }
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (IllegalActionException e) {
            // Should never happen
            e.printStackTrace();
        }

        return returnValue;
    }
}
