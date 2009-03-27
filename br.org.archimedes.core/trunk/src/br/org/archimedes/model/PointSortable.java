/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/10/18, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import java.util.Collection;
import java.util.SortedSet;

/**
 * Belongs to package br.org.archimedes.model.
 */
public interface PointSortable {

    /**
     * Get a collection of sorted points along this element starting from the
     * reference point.
     * 
     * @param referencePoint
     *            The point to be considered the starting point
     * @param points
     *            A collection of points contained in the element
     * @return The sorted set of the points
     */
    public SortedSet<ComparablePoint> getSortedPointSet (Point referencePoint,
            Collection<Point> intersectionPoints);
}
