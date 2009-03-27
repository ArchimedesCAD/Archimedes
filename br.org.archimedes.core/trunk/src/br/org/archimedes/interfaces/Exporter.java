/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/01/31, 23:46:41, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface Exporter {

    /**
     * @param drawing
     *            The drawing to be exported
     * @param output
     *            The output that should be used to write the file
     * @throws IOException
     *             Thrown if any problem is found writing the file.
     */
    public void exportDrawing (Drawing drawing, OutputStream output)
            throws IOException;
}
