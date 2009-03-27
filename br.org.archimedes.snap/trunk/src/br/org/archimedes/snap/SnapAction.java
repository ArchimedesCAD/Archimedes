/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/24, 18:08:11, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.snap on the br.org.archimedes.snap project.<br>
 */
package br.org.archimedes.snap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.interfaces.Command;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author nitao
 */
public class SnapAction implements IWorkbenchWindowActionDelegate {

    public void run (IAction action) {

        try {
            SnapCommand snapCommand = new SnapCommand();
            Controller controller = br.org.archimedes.Utils.getController();
            List<Command> commands = new ArrayList<Command>();
            commands.add(snapCommand);
            controller.execute(commands);
        }
        catch (NoActiveDrawingException e1) {
            /*
             * An snap activation can be ignored if no drawing is open.
             */
        }
        catch (IllegalActionException e1) {
            /*
             * There is no possible way to have an illegal action changing
             * snap's state.
             */
        }
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose () {

        // Nothing to dispose
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init (IWorkbenchWindow window) {

        // Nothing to init

    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Doesn't care about selections
    }
}
