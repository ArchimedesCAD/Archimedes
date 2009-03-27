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
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface Importer {

    /**
     * @param input
     *            The input that should be used to read the file
     * @return The resulting drawing
     * @throws InvalidFileFormatException
     *             Thrown if any problem is found on the file.
     * @throws InvalidFileFormatException
     *             Thrown if there is any problem while reading the input.
     */
    public Drawing importDrawing (InputStream input)
            throws InvalidFileFormatException, IOException;
}
