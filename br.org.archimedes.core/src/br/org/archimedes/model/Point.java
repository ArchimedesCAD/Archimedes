/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cristiane M. Sato - initial API and implementation<br>
 * Marcio Oshiro, Hugo Corbucci, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/03/23, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.model;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author Cristiane M. Sato e Marcio Oshiro
 */
public class Point implements Comparable<Point> {

    private double x;

    private double y;


    /**
     * Constructor
     * 
     * @param x
     *            The x coordinate of the point to be created
     * @param y
     *            The y coordinate of the point to be created
     */
    public Point (double x, double y) {

        this.x = x;
        this.y = y;
    }

    /**
     * @return Returns the x.
     */
    public double getX () {

        return x;
    }

    /**
     * @param x
     *            The x to set.
     */
    public void setX (double x) {

        this.x = x;
    }

    /**
     * @return Returns the y.
     */
    public double getY () {

        return y;
    }

    /**
     * @param y
     *            The y to set.
     */
    public void setY (double y) {

        this.y = y;
    }

    /**
     * This method verify if this point is inside the rectangle in the parameter.
     * 
     * @param rect
     *            The rectangle to test.
     * @return True if the point is inside the rectangle.
     */
    public boolean isInside (Rectangle rect) {

        Point lowerLeft = rect.getLowerLeft();
        Point upperRight = rect.getUpperRight();
        if (this.x >= lowerLeft.getX() && this.x <= upperRight.getX() && this.y >= lowerLeft.getY()
                && this.y <= upperRight.getY()) {
            return true;
        }

        return false;

    }

    public String toString () {

        return "(" + (Math.abs(x) < Constant.EPSILON ? "0.0" : x) + "," + (Math.abs(y) < Constant.EPSILON ? "0.0" : y) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return The area of the triangle formed by the points a, b and c.
     */
    public double area (Point a, Point b, Point c) {

        double returnValue = (a.getX() * b.getY() + b.getX() * c.getY() + c.getX() * a.getY())
                - (a.getX() * c.getY() + b.getX() * a.getY() + c.getX() * b.getY());

        return returnValue;
    }

    /**
     * @return A copy of this point.
     */
    public Point clone () {

        return new Point(x, y);
    }

    /**
     * @param otherPoint
     *            Yhe other point.
     * @return The distance between this point and the other point.
     */
    public double calculateDistance (Point otherPoint) {

        double xDist = getX() - otherPoint.getX();
        xDist *= xDist;

        double yDist = getY() - otherPoint.getY();
        yDist *= yDist;

        return Math.sqrt(xDist + yDist);
    }

    /**
     * This method compares this point with the parameter and, if the x coordinate is bigger than
     * the x coordiante of the parameter return 1. Otherwise the return value is -1. If the
     * coordinates are equals, the method do the same comparison with the y coordinates. Return 0 if
     * the points are equals.
     * 
     * @param o
     *            The point to be compared
     * @return The value referent to the comparison
     * @throws ClassCastException
     *             if cannot convert the object passed to a point
     */
    public int compareTo (Point o) throws ClassCastException {

        Point point = (Point) o;

        if (this.x < point.x) {
            return -1;
        }
        else if (this.x > point.x) {
            return 1;
        }
        else if (this.y < point.y) {
            return -1;
        }
        else if (this.y > point.y) {
            return 1;
        }
        return 0;
    }

    /**
     * @param vector
     *            The vector to be added
     * @return A point that is this point plus a vector in euclidian geometry
     */
    public Point addVector (Vector vector) {

        double x = getX() + vector.getX();
        double y = getY() + vector.getY();
        return new Point(x, y);
    }

    /**
     * Scales a point given a reference and a proportion
     * 
     * @param scaleReference
     *            The scale reference
     * @param proportion
     *            The porportion
     * @throws NullArgumentException
     *             In case the reference is null
     * @throws IllegalActionException
     *             In case the proportion is not positive
     */
    public void scale (Point scaleReference, double proportion) throws NullArgumentException,
            IllegalActionException {

        if (scaleReference == null) {
            throw new NullArgumentException();
        }
        if (proportion <= 0) {
            throw new IllegalActionException();
        }

        Vector translation = new Vector(new Point(0, 0), scaleReference);
        move( -translation.getX(), -translation.getY());

        setX(getX() * proportion);
        setY(getY() * proportion);

        move(translation.getX(), translation.getY());
    }

    /**
     * Rotate the point around the reference point (counterclockwise).
     * 
     * @param reference
     *            the reference point
     * @param angle
     *            the angle to rotate the element
     * @throws NullArgumentException
     *             Thrown if the reference is null.
     */
    public void rotate (Point reference, double angle) throws NullArgumentException {

        if (reference == null) {
            throw new NullArgumentException();
        }
        Point clonedRef = reference.clone(); // If the reference is myself, I still work.

        move( -clonedRef.getX(), -clonedRef.getY());
        double x = getX() * Math.cos(angle) - getY() * Math.sin(angle);
        double y = getX() * Math.sin(angle) + getY() * Math.cos(angle);
        setX(x + clonedRef.getX());
        setY(y + clonedRef.getY());
    }

    /**
     * Move the point
     * 
     * @param deltaX
     *            The X distance to be moved
     * @param deltaY
     *            The Y distance to be moved
     */
    public void move (double deltaX, double deltaY) {

        x += deltaX;
        y += deltaY;
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
        if ( !Point.class.isAssignableFrom(obj.getClass()))
            return false;

        Point other = (Point) obj;
        if (Math.abs(this.x - other.x) > Constant.EPSILON)
            return false;
        if (Math.abs(this.y - other.y) > Constant.EPSILON)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        final int prime = 31;
        int result = 1;
        double errorInsertion = Constant.EPSILON + 1;
        result = prime * result + (int) (this.x / errorInsertion);
        result = prime * result + (int) (this.y / errorInsertion);
        return result;
    }
}
