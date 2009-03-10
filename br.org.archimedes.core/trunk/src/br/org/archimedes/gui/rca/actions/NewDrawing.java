/*
 * Created on Jun 13, 2008 for br.org.archimedes.core
 */

package br.org.archimedes.gui.rca.actions;

import br.org.archimedes.gui.rca.Messages;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author night
 */
public class NewDrawing extends OpenDrawing {

    private static int drawingNumber = 0;


    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.gui.rca.actions.OpenDrawing#obtainDrawing()
     */
    @Override
    protected Drawing obtainDrawing () {

        Drawing drawing = new Drawing(Messages.NewDrawingName + " " //$NON-NLS-1$
                + fetchAndAdd());
        return drawing;
    }

    private static int fetchAndAdd () {

        return drawingNumber++;
    }

}
