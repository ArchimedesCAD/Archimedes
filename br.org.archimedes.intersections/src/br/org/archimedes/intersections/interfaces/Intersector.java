/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/05/15, 10:23:37, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersections.interfaces on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersections.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public interface Intersector {

    /**
     * Returns the intersection points of two elements.
     * 
     * @param element
     *            The first element.
     * @param otherElement
     *            The second element.
     * @return The collection of points of intersection.
     * @throws NullArgumentException
     *             If element or otherElement is null
     */
    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException;
}
