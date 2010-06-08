
package br.org.archimedes.gui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import br.org.archimedes.gui.actions.SaveCommand;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.model.Drawing;

public class SaveHandler extends AbstractHandler {

    private static final String SAVE_ALL_PARAMETER_ID = "br.org.archimedes.core.save.all"; //$NON-NLS-1$

    private static final String SAVE_AS_PARAMETER_ID = "br.org.archimedes.core.save.as"; //$NON-NLS-1$


    /*
     * (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        String as = event.getParameter(SAVE_AS_PARAMETER_ID);
        String all = event.getParameter(SAVE_ALL_PARAMETER_ID);
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        IWorkbenchPage activePage = window.getActivePage();
        Shell shell = window.getShell();
        boolean showDialog = as != null && Boolean.parseBoolean(as);
        IEditorPart[] editors;
        if (all != null && Boolean.parseBoolean(all)) {
            editors = activePage.getDirtyEditors();
        }
        else {
            editors = new IEditorPart[] {activePage.getActiveEditor()};
        }

        for (IEditorPart editorPart : editors) {
            try {
                boolean canceled = save(shell, showDialog, editorPart);
                if (canceled)
                    break;
            }
            catch (ClassCastException e) {
                throw new ExecutionException("Can't save the file. Can't save the kind of editor.", e);
            }
        }
        return null;
    }

    /**
     * @param shell
     *            The shell to use to open a save window if needed
     * @param showDialog
     *            true if the dialog should be shown no matter what
     * @param editorPart
     *            The editor to save
     * @return true if the user canceled the save operation, false otherwise
     */
    private boolean save (Shell shell, boolean showDialog, IEditorPart editorPart) {

        DrawingEditor editor = (DrawingEditor) editorPart;
        Drawing drawing = ((DrawingInput) editor.getEditorInput()).getDrawing();
        SaveCommand command = new SaveCommand(shell, showDialog, drawing);
        return !command.execute();
    }
}
