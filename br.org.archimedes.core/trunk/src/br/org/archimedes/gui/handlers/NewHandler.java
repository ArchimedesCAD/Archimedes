
package br.org.archimedes.gui.handlers;

import br.org.archimedes.Messages;
import br.org.archimedes.model.Drawing;

import org.eclipse.swt.widgets.Shell;

public class NewHandler extends OpenHandler {

    private static int drawingNumber = 0;


    /*
     * (non-Javadoc)
     * @see br.org.archimedes.gui.rca.actions.OpenDrawing#obtainDrawing()
     */
    @Override
    protected Drawing obtainDrawing (Shell shell) {

        return new Drawing(Messages.bind(Messages.NewDrawingName, fetchAndAdd()));
    }

    private static int fetchAndAdd () {

        return drawingNumber++;
    }
}
