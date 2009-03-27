/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/21, 19:01:16, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import java.util.Collection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Selection;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author nitao
 */
public class SelectAllAction implements IWorkbenchWindowActionDelegate {

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
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run (IAction action) {

        try {
            Drawing activeDrawing = br.org.archimedes.Utils.getController().getActiveDrawing();
            if (activeDrawing != null) {
                Collection<Element> unlockedContents = activeDrawing
                        .getUnlockedContents();
                Selection selection = new Selection();
                for (Element element : unlockedContents) {
                    selection.add(element);
                }
                activeDrawing.setSelection(selection);
            }
        }
        catch (NoActiveDrawingException e) {
            // Should not happen since we are carefull to check it.
            System.err
                    .println("Error retrieving the active drawing while selecting all: " //$NON-NLS-1$
                            + e);
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Ignores the selection
    }

}
