/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/02/01, 23:19:00, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface ElementImporter <T extends Element> {

    /**
     * @param input
     *            The input from which the element must be read.
     * @return The element of type T.
     * @throws InvalidFileFormatException
     *             In case the input is not in the expected format.
     * @throws IOException
     *             In case of any other IO problem.
     */
    public T importElement (InputStream input)
            throws InvalidFileFormatException, IOException;
}
