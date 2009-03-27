/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/12, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;

/**
 * Belongs to package br.org.archimedes.model.
 */
public abstract class ReferencePoint {

    private List<Point> pointsToMove;

    private Point point;


    /**
     * @param point
     *            The point representing this reference.
     * @throws NullArgumentException
     *             Thrown if the point is null
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public ReferencePoint (Point point) throws NullArgumentException {

        this(point, Collections.EMPTY_LIST);
    }

    /**
     * @param point
     *            The point representing this reference point.
     * @param pointsToMove
     *            The list of points to be moved.
     * @throws NullArgumentException
     *             Thrown if the point or list are null
     */
    public ReferencePoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        if (point == null || pointsToMove == null) {
            throw new NullArgumentException();
        }
        this.point = point;
        this.pointsToMove = pointsToMove;
    }

    /**
     * @param point
     *            The point representing this reference point.
     * @param pointsToMove
     *            The list of points to be moved.
     * @throws NullArgumentException
     *             Thrown if the point is null
     */
    public ReferencePoint (Point point, Point... pointsToMove)
            throws NullArgumentException {

        this(point, new LinkedList<Point>());
        for (Point pointToMove : pointsToMove) {
            this.pointsToMove.add(pointToMove);
        }
    }

    /**
     * This method verify if this point is inside the rectangle in the
     * parameter.
     * 
     * @param rect
     *            The rectangle to test.
     * @return True if the point is inside the rectangle.
     */
    public boolean isInside (Rectangle rect) {

        return getPoint().isInside(rect);
    }

    /**
     * @return The point
     */
    public Point getPoint () {

        return this.point;
    }

    /**
     * Draws the point
     */
    public abstract void draw ();

    /**
     * @return The list of points that should be moved
     */
    public List<Point> getPointsToMove () {

        return pointsToMove;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        final int PRIME = 31;
        int result = 1;
        result = PRIME * result
                + ((this.getPoint() == null) ? 0 : this.getPoint().hashCode());
        result = PRIME
                * result
                + ((this.pointsToMove == null) ? 0 : this.pointsToMove
                        .hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ReferencePoint other = (ReferencePoint) obj;
        if (this.getPoint() == null) {
            if (other.getPoint() != null)
                return false;
        }
        else if ( !this.getPoint().equals(other.getPoint()))
            return false;
        if (this.pointsToMove == null) {
            if (other.pointsToMove != null)
                return false;
        }
        else if ( !this.pointsToMove.equals(other.pointsToMove))
            return false;
        return true;
    }
}
