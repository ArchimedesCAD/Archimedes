
package br.org.archimedes.gui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import br.org.archimedes.controller.commands.ZoomExtendCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.actions.LoadCommand;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;

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
                if(drawing.isCenterToOpen())
                    centerDrawingOnOpen(drawing);
            }
            catch (PartInitException e) {
                throw new ExecutionException(
                        "Can't open the file. Couldn't initialize the editor.", e);
            }
        }
        return null;
    }

    private void centerDrawingOnOpen (Drawing drawing) {

        try {
            List<Command> cmd = new ArrayList<Command>();
            ZoomExtendCommand command = new ZoomExtendCommand();
            command.doIt(drawing);
            cmd.add(command);
            br.org.archimedes.Utils.getController().execute(cmd);
        }
        catch (NoActiveDrawingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalActionException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (NullArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
