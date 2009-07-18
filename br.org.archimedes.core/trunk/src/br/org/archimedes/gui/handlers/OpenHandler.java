
package br.org.archimedes.gui.handlers;

import br.org.archimedes.gui.actions.LoadCommand;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.model.Drawing;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenHandler extends AbstractHandler {

    /**
     * @param shell
     *            The shell to use if any dialog needs to be open
     * @return The obtained Drawing
     */
    protected Drawing obtainDrawing (Shell shell) {

        LoadCommand command = new LoadCommand(shell);
        return command.execute();
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        Drawing drawing = obtainDrawing(window.getShell());

        if (drawing != null) {
            try {
                window.getActivePage().openEditor(new DrawingInput(drawing),
                        DrawingEditor.EDITOR_ID);
            }
            catch (PartInitException e) {
                throw new ExecutionException("Can't open the file. Couldn't initialize the editor.", e);
            }
        }
        return null;
    }
}
