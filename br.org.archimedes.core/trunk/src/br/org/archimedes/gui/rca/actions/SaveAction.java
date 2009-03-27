/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/18, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.gui.actions.SaveCommand;
import br.org.archimedes.model.Drawing;

public class SaveAction implements IWorkbenchWindowActionDelegate {

    protected IWorkbenchWindow window;

    protected IStructuredSelection selection;

    protected boolean showDialog;


    public SaveAction () {

        this.showDialog = false;
    }

    public void dispose () {

        selection = null;
    }

    public void init (IWorkbenchWindow window) {

        this.window = window;
    }

    public void run (IAction action) {

        Iterator<?> iterator = selection.iterator();
        boolean keepGoing = true;

        while (iterator.hasNext() && keepGoing) {
            Object selectedElement = iterator.next();
            if (Drawing.class.isAssignableFrom(selectedElement.getClass())) {
                Drawing drawing = (Drawing) selectedElement;
                SaveCommand command = new SaveCommand(window.getShell(),
                        showDialog, drawing);
                keepGoing = command.execute();
            }
        }
    }

    public void selectionChanged (IAction action, ISelection selection) {

        if (IStructuredSelection.class.isAssignableFrom(selection.getClass())) {
            this.selection = (IStructuredSelection) selection;
        }
    }

}
