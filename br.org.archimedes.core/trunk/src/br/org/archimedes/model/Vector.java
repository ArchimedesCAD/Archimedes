/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/05/26, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.model;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author jefsilva
 */
public class Vector {

    private Point point;
    

    /**
     * Constructor
     * 
     * @param initialPoint
     *            The vector initial point
     * @param endingPoint
     *            The vector ending point
     */
    public Vector (Point initialPoint, Point endingPoint) {    	
        this(new Point((endingPoint.getX() - initialPoint.getX()),
                (endingPoint.getY() - initialPoint.getY())));
    }

    /**
     * Constructor
     * 
     * @param point
     *            The vector ending point
     */
    public Vector (Point point) {

        // TODO Nao aceitar (0,0) ???
        this.point = point.clone();
    }

    /**
     * Calculates the dot product between two vectors.
     * 
     * @param vector
     *            The vector
     * @return The dot product between this vector and the other vector
     */
    public double dotProduct (Vector vector) {

        return (point.getX() * vector.getX()) + (point.getY() * vector.getY());
    }

    /**
     * @return The y coordinate
     */
    public double getY () {

        return point.getY();
    }

    /**
     * @return The x coordinate
     */
    public double getX () {

        return point.getX();
    }

    /**
     * Adds this vector with a second vector
     * 
     * @param v
     *            The vector to be added to this
     */
    public void add (Vector v) {

        point.setX(point.getX() + v.getX());
        point.setY(point.getY() + v.getY());
    }

    /**
     * Multiplies this vector by a scalar
     * 
     * @param s
     *            The scalar
     * @return The multiplyed vector by scalar
     */
    public Vector multiply (double s) {

        Point newPoint = this.point.clone();
        newPoint.setX(newPoint.getX() * s);
        newPoint.setY(newPoint.getY() * s);
        Vector vector = new Vector(newPoint);
        return vector;

    }

    /**
     * @return A vector orthogonal to this vector. The original vector rotated 90 degrees
     *         counter-clockwise.
     */
    public Vector getOrthogonalVector () {

        Point point = new Point( -getY(), getX());

        return new Vector(point);
    }

    public String toString () {

        return "Vector from (0, 0) to " + point.toString(); //$NON-NLS-1$
    }

    /**
     * @return The point represented by this vector
     */
    public Point getPoint () {

        return point;
    }

    /**
     * @return The norm of the vector
     */
    public double getNorm () {

        return Math.sqrt(dotProduct(this));
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals (Object object) {

        boolean equal = false;
        if ((object != null) && Vector.class.isAssignableFrom(object.getClass())) {
            Vector vector = (Vector) object;
            equal = getPoint().equals(vector.getPoint());
        }
        return equal;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {
    
        return getPoint().hashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Vector clone () {

        return new Vector(point);
    }
    
    public Vector normalized() {    	
    	return this.multiply(1/this.getNorm());
    }
    
    
    
}
