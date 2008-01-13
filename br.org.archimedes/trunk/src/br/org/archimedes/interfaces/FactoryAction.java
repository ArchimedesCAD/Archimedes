/**
 * This file was created on 13/02/2007, 11:53:28, by marivb.
 * It is part of br.org.archimedes.interfaces on the br.org.archimedes project.
 * 
 */
package br.org.archimedes.interfaces;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.controller.InputController;


/**
 * Belongs to package br.org.archimedes.interfaces.
 *
 * @author marivb
 */
public abstract class FactoryAction implements IWorkbenchWindowActionDelegate {

    private String factoryID;

    public FactoryAction(String factoryName) {
        this.factoryID = factoryName;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose () {

        // Nothing to dispose
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init (IWorkbenchWindow window) {

        // No init for now
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run (IAction action) {

        InputController.getInstance().receiveText(factoryID);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Nothing to do
    }
}
