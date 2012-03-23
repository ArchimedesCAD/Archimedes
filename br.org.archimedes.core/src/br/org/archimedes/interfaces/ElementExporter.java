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

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface ElementExporter <T extends Element> {

    /**
     * @param element
     *            The element of type T.
     * @param output
     *            The output to which the element must be written. Users must known which class this
     *            is.
     * @throws IOException
     *             In case of any IO problem.
     * @throws NotSupportedException
     *             In case the method is not supported.
     */
    public void exportElement (T element, Object outputObject) throws IOException,
            NotSupportedException;

    /**
     * @param element
     *            The element of type T.
     * @param output
     *            The output to which the element must be written. Users must known which class this
     *            is.
     * @param boundingBox
     * @throws IOException
     *             In case of any IO problem.
     * @throws NotSupportedException
     *             In case the method is not supported.
     */
    public void exportElement (T element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException;
}
