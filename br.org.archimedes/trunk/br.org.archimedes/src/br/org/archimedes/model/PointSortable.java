/*
 * Created on 18/10/2006
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
