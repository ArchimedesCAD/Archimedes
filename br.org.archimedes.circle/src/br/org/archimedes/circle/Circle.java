/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Eduardo O. de Souza, Victor D. Lopes, Marcos P. Moreti, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/06/22, 13:06:39, by Eduardo O. de Souza.<br>
 * It is part of package br.org.archimedes.circle on the br.org.archimedes.circle project.<br>
 */

package br.org.archimedes.circle;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.curvedshape.CurvedShape;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.model.
 */
public class Circle extends CurvedShape implements Offsetable {

    private Point center;

    private double radius;

    /**
     * Constructor.
     * 
     * @param center
     *            The circle's center
     * @param radius
     *            The circle's radius
     * @throws NullArgumentException
     *             In case the point is null
     * @throws InvalidArgumentException
     *             In case the radius is 0
     */
    public Circle (Point center, Double radius) throws NullArgumentException,
            InvalidArgumentException {

        if (center == null) {
            throw new NullArgumentException();
        }
        if (Math.abs(radius) <= Constant.EPSILON) {
            throw new InvalidArgumentException();
        }
        this.center = center;
        this.radius = Math.abs(radius);
    }

    public CurvedShape clone () {

        Circle circle = null;

        try {
            circle = new Circle(center.clone(), radius);
            circle.setLayer(getLayer());
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }

        return circle;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        final int prime = 31;
        int result = 1 * prime;
        result = prime * result + getCenter().hashCode();
        result = prime * result + Double.valueOf(getRadius()).hashCode();
        return result;
    }

    public boolean equals (Object object) {

        boolean result = false;

        if (object != null) {
            try {
                Circle circle = (Circle) object;
                result = this.equals(circle);
            }
            catch (ClassCastException e) {
                // The elements are not equal
            }
        }

        return result;
    }

    public boolean equals (Circle circle) {

        boolean result = true;

        if (circle == null) {
            result = false;
        }
        else if ( !this.center.equals(circle.getCenter())) {
            result = false;
        }
        else if (Math.abs(radius - circle.getRadius()) > Constant.EPSILON) {
            result = false;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getBoundaryRectangle()
     */
    public Rectangle getBoundaryRectangle () {

        Point myCenter = this.getCenter();
        double myRadius = this.getRadius();
        double left = myCenter.getX() - myRadius;
        double right = myCenter.getX() + myRadius;
        double bottom = myCenter.getY() - myRadius;
        double top = myCenter.getY() + myRadius;

        return new Rectangle(left, bottom, right, top);
    }

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes.model
     * .Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            ReferencePoint reference = new CirclePoint(this.getCenter(), this.getCenter());
            if (reference.isInside(area)) {
                references.add(reference);
            }
            for (double angle = 0; angle < 360; angle += 90) {
                double radians = (angle / 180) * Math.PI;
                double x = this.getRadius() * Math.cos(radians) + this.getCenter().getX();
                double y = this.getRadius() * Math.sin(radians) + this.getCenter().getY();
                Point point = new Point(x, y);
                reference = new RhombusPoint(point);
                if (reference.isInside(area)) {
                    references.add(reference);
                }
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }

        return references;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point
     * )
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Point projection = null;
        if (getCenter().equals(point)) {
            return new Point(getCenter().getX() + getRadius(), getCenter().getY());
        }

        Collection<Point> intersectionWithLine = getCirclePoints(point);

        double closestDist = Double.MAX_VALUE;
        for (Point intersection : intersectionWithLine) {
            double dist = Geometrics.calculateDistance(point, intersection);
            if (dist < closestDist) {
                projection = intersection;
                closestDist = dist;
            }
        }

        return projection;
    }

    /**
     * Returns the Points that are on the circle and on the line defined by the given Point and the
     * center of the Circle.
     * 
     * @param point
     *            a Point
     * @return a Collection with the intersections of the infinite line formed by the center of this
     *         circle with the point.
     * @throws NullArgumentException
     *             If the given argument is null
     */
    private Collection<Point> getCirclePoints (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Collection<Point> points = new ArrayList<Point>();

        Vector vec = new Vector(getCenter(), point);
        vec = Geometrics.normalize(vec);
        vec.multiply(getRadius());

        Point p1 = getCenter().addVector(vec);
        points.add(p1);
        vec.multiply( -1);
        Point p2 = getCenter().addVector(vec);
        if ( !p2.equals(p1)) {
            points.add(p2);
        }

        return points;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
     */
    public boolean contains (Point point) throws NullArgumentException {

        double distance = Geometrics.calculateDistance(getCenter(), point);
        return Math.abs(distance - this.getRadius()) <= Constant.EPSILON;
    }

    /**
     * @return The circle's center
     */
    public Point getCenter () {

        return center;
    }

    /**
     * @return The circle's radius
     */
    public double getRadius () {

        return radius;
    }

    public Element cloneWithDistance (double distance) throws InvalidParameterException {

        if (distance < 0) {
        	if(Math.abs(this.getRadius() - Math.abs(distance)) <= Constant.EPSILON)
        		throw new InvalidParameterException();
        	
            if (Math.abs(distance) > this.getRadius())
                throw new InvalidParameterException();
        }

        Circle clone = null;
        try {
            clone = new Circle(this.getCenter().clone(), this.getRadius() + distance);
            clone.setLayer(getLayer());
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return clone;
    }

    public boolean isPositiveDirection (Point point) {

        boolean isOutside = false;

        try {
            if (Geometrics.calculateDistance(this.getCenter(), point) > this.getRadius()) {
                isOutside = true;
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return isOutside;
    }

    public void scale (Point reference, double proportion) throws NullArgumentException,
            IllegalActionException {

        center.scale(reference, proportion);
        radius *= proportion;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        return Collections.singletonList(this.getCenter());
    }

    public String toString () {

        return "Circle centered at " + this.getCenter().toString() + " with radius " + this.getRadius(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public boolean isClosed () {

        return true;
    }

    @Override
    public void draw (OpenGLWrapper wrapper) {

        this.drawCurvedShape(wrapper, this.getCenter(), 0, 2 * Math.PI);
    }
}
