
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
