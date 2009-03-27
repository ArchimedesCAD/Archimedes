/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/29, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import java.util.Collection;

import br.org.archimedes.exceptions.IllegalActionException;

public interface Trimmable extends PointSortable {

    /**
     * Removes the segments delimited by the references and the click.
     * 
     * @param references
     *            The cutting references.
     * @param click
     *            The point that determines the segment to be removed..
     * @return The collection with the new elements.
     */
    public Collection<Element> trim (Collection<Element> references, Point click)
            throws IllegalActionException;
}
