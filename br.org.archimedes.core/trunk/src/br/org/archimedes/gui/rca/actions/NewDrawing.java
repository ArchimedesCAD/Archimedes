/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/13, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
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
