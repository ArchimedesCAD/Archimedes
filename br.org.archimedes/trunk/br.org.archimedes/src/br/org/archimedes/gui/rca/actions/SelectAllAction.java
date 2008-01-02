/**
 * This file was created on 2007/04/21, 19:01:16, by nitao. It is part of
 * br.org.archimedes.gui.rca.actions on the br.org.archimedes project.
 */

package br.org.archimedes.gui.rca.actions;

import java.util.Collection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.controller.Controller;
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
            Drawing activeDrawing = Controller.getInstance().getActiveDrawing();
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
