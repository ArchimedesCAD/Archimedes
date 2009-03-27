/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/21, 19:10:21, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author nitao
 */
public class CloseAction implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;


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

        this.window = window;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run (IAction action) {

        // TODO mudar quando tiver save
        IWorkbenchPage activePage = this.window.getActivePage();
        IEditorPart activeEditor = activePage.getActiveEditor();
        activePage.closeEditor(activeEditor, false);
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Selection independent
    }
}
