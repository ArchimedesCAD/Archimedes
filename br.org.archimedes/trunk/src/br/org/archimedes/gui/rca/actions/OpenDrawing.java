
package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

import br.org.archimedes.gui.actions.LoadCommand;
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

    private static final String DRAWING_EDITOR_ID = "br.org.archimedes.gui.rca.editor.DrawingEditor";

    private IWorkbenchWindow window;


    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public final void run (IAction action) {

        Drawing drawing = obtainDrawing();

        if (drawing != null) {
            try {
                window.getActivePage().openEditor(new DrawingInput(drawing),
                        DRAWING_EDITOR_ID);
            }
            catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The obtained Drawing
     */
    protected Drawing obtainDrawing () {

        LoadCommand command = new LoadCommand(window.getShell());
        return command.execute();
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
