/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/04, 22:53:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline.explode on the br.org.archimedes.polyline.explode project.<br>
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
 * Belongs to package br.org.archimedes.polyline.explode.
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
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
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
     * @see br.org.archimedes.factories.SelectorFactory#finishFactory(java.util.Set)
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
