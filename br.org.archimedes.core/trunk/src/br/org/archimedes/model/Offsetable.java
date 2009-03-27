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

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;



public interface Offsetable {

    /**
     * @param point
     *            The point to be checked
     * @return true if the point indicates that the offset of the element is to
     *         be done by positive direction, false otherwise.
     * @throws NullArgumentException Thrown if the point in null
     */
    public boolean isPositiveDirection (Point point) throws NullArgumentException;

    /**
     * @param distance
     *            the distance from the original element to be copied.
     * @return the new element dislocated by the distance.
     */
    public Element cloneWithDistance (double distance) throws InvalidParameterException;
}
