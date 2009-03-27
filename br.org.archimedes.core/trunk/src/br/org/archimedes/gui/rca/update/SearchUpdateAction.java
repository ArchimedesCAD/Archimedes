/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/01/25, 19:21:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.update on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.update;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.update.ui.UpdateManagerUI;

/**
 * Belongs to package br.org.archimedes.gui.rca.update.
 * 
 * @author night
 */
public class SearchUpdateAction implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose () {

        window = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init (IWorkbenchWindow window) {

        this.window = window;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run (IAction action) {

        BusyIndicator.showWhile(window.getShell().getDisplay(), new Runnable() {

            public void run () {

                UpdateManagerUI.openInstaller(window.getShell());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Ignores selection changes
    }
}
