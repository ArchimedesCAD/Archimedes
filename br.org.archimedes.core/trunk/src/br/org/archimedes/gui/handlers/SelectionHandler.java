
package br.org.archimedes.gui.handlers;

import java.util.Collection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Selection;

public class SelectionHandler extends AbstractHandler {

    private static final String SELECTION_PARAMETER_ID = "br.org.archimedes.core.select.data"; //$NON-NLS-1$


    public Object execute (ExecutionEvent event) throws ExecutionException {

        // TODO Learn how to get complex data from parameters
        String selectionParam = event.getParameter(SELECTION_PARAMETER_ID);
        if ("all".equals(selectionParam)) { //$NON-NLS-1$
            try {
                selectAll();
            }
            catch (NoActiveDrawingException e) {
                throw new ExecutionException("Cannot select everything without a drawing.", e);
            }
        }
        else
            Utils.getController().deselectAll();
        return null;
    }

    /**
     * @throws NoActiveDrawingException
     */
    private void selectAll () throws NoActiveDrawingException {

        Drawing activeDrawing =  Utils.getController().getActiveDrawing();
        if (activeDrawing != null) {
            Collection<Element> unlockedContents = activeDrawing.getUnlockedContents();
            Selection selection = new Selection();
            for (Element element : unlockedContents) {
                selection.add(element);
            }
            activeDrawing.setSelection(selection);
        }
    }

}
