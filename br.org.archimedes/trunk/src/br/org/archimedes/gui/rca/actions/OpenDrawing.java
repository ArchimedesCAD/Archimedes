
package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

import br.org.archimedes.gui.actions.LoadCommand;
import br.org.archimedes.gui.rca.Messages;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.model.Drawing;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class OpenDrawing implements IWorkbenchWindowActionDelegate {

    private static int drawingNumber = 0;

    private IWorkbenchWindow window;

    private boolean load;


    /**
     * The constructor.
     */
    public OpenDrawing () {

        load = false;
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run (IAction action) {

        Drawing drawing = null;
        if (load) {
            LoadCommand command = new LoadCommand(window.getShell());
            drawing = command.execute();
        }
        else {
            drawing = new Drawing(Messages.NewDrawingName + " " //$NON-NLS-1$
                    + OpenDrawing.fetchAndAdd());
        }

        if (drawing != null) {

            try {
                window.getActivePage().openEditor(new DrawingInput(drawing),
                        "br.org.archimedes.gui.rca.editor.DrawingEditor"); //$NON-NLS-1$
            }
            catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }

    private static int fetchAndAdd () {

        return drawingNumber++;
    }

    /**
     * Selection in the workbench has been changed. We can change the state of
     * the 'real' action here if we want, but this can only happen after the
     * delegate has been created.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged (IAction action, ISelection selection) {

    }

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose () {

    }

    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init (IWorkbenchWindow window) {

        this.window = window;
    }
}
