/*
 * Created on 22/06/2006
 */

package br.org.archimedes.circle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.curvedshape.CurvedShape;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

/**
 * Belongs to package com.tarantulus.archimedes.model.
 */
public class Circle extends CurvedShape {

    private Point center;

    private double radius;

    private Layer parentLayer;


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

    public CurvedShape clone() {

        Circle circle = null;

        try {
            circle = new Circle(center.clone(), radius);
            circle.setLayer(parentLayer);
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
     * 
     * @see com.tarantulus.archimedes.model.Element#getIntersection(com.tarantulus.archimedes.model.Element)
     */
    //TODO As soon as the way to intersect elements is (re)defined
    //implement this method correctly
    public Collection<Point> getIntersection (Element element)
            throws NullArgumentException {

        if (element == null) {
            throw new NullArgumentException();
        }

        return new ArrayList<Point>();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getBoundaryRectangle()
     */
    public Rectangle getBoundaryRectangle () {

        double left = center.getX() - radius;
        double right = center.getX() + radius;
        double bottom = center.getY() - radius;
        double top = center.getY() + radius;

        return new Rectangle(left, bottom, right, top);
    }



    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getReferencePoints(com.tarantulus.archimedes.model.Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            ReferencePoint reference = new CirclePoint(center, center);
            if (reference.isInside(area)) {
                references.add(reference);
            }
            for (double angle = 0; angle < 360; angle += 90) {
                double radians = (angle / 180) * Math.PI;
                Point point = new Point(radius * Math.cos(radians)
                        + center.getX(), radius * Math.sin(radians)
                        + center.getY());
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
     * 
     * @see com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus.archimedes.model.Point)
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Point projection = null;
        if(getCenter().equals(point)) {
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
     * Returns the Points that are on the circle and on the line
     * defined by the given Point and the center of the Circle.
     * 
     * @param point Point
     * 
     * @return Collection
     * @throws NullArgumentException If the given argument is null
     */
    private Collection<Point> getCirclePoints(Point point) throws NullArgumentException {
        if (point == null) {
            throw new NullArgumentException();
        }
        
        Collection<Point> points = new ArrayList<Point>();
        
        Vector vec = new Vector(getCenter(), point);
        vec = Geometrics.normalize(vec);
        vec.multiply(getRadius());
        
        Point p1 = getCenter().addVector(vec);
        points.add(p1);
        vec.multiply(-1);
        Point p2 = getCenter().addVector(vec);
        if(!p2.equals(p1)) {
        	points.add(p2);
        }
        
    	return points;
	}

	/*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#contains(com.tarantulus.archimedes.model.Point)
     */
    public boolean contains (Point point) throws NullArgumentException {

        double distance = Geometrics.calculateDistance(getCenter(), point);
        return Math.abs(distance - radius) <= Constant.EPSILON;
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

    public Element cloneWithDistance (double distance)
            throws IllegalActionException {

        if (distance < 0) {
            if (Math.abs(radius - distance) <= Constant.EPSILON
                    || Math.abs(distance) > radius) {
                throw new IllegalActionException();
            }
        }
        Circle clone = null;
        try {
            clone = new Circle(center.clone(), radius + distance);
            clone.setLayer(parentLayer);
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
            if (Geometrics.calculateDistance(center, point) > radius) {
                isOutside = true;
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return isOutside;
    }

    public boolean isCollinearWith (Element element) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#isParallelTo(com.tarantulus.archimedes.model.elements.Element)
     */
    public boolean isParallelTo (Element element) {

        return false;
    }

    
    public void scale (Point reference, double proportion)
            throws NullArgumentException, IllegalActionException {

        center.scale(reference, proportion);
        radius *= proportion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(center);
        return points;
    }

    public String toString () {

        return center.toString() + " with radius " + radius; //$NON-NLS-1$
    }

    public boolean isClosed () {

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#getNearestExtremePoint(com.tarantulus.archimedes.model.Point)
     */
    @Override
    public Point getNearestExtremePoint (Point point)
            throws NullArgumentException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Trimmable#trim(java.util.Collection,
     *      com.tarantulus.archimedes.model.Point)
     */
    public Collection<Element> trim (Collection<Element> references, Point click) {

        Collection<Element> trimResult = new ArrayList<Element>();
        Collection<Point> intersectionPoints = getIntersectionPoints(references);
        Point initialPoint = center.clone();
        initialPoint.move(getRadius(), 0);
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(
                initialPoint, intersectionPoints);

        ComparablePoint clickPoint = null;

        try {
            double key = Geometrics.calculateRelativeAngle(center,
                    initialPoint, click);
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        SortedSet<ComparablePoint> negativeIntersections = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> positiveIntersections = sortedPointSet.tailSet(clickPoint);

//        try {
            if (negativeIntersections.size() == 0
                    && positiveIntersections.size() > 0) {
//                Point firstPositive = positiveIntersections.first().getPoint();
//                Point lastPositive = positiveIntersections.last().getPoint();
                // TODO Trim
//                Element arc = new Arc(firstPositive, lastPositive, center, true);
//                arc.setLayer(getLayer());
//
//                trimResult.add(arc);
            }
            else if (positiveIntersections.size() == 0
                    && negativeIntersections.size() > 0) {
//                Point lastNegative = negativeIntersections.last().getPoint();
//                Point firstNegative = negativeIntersections.first().getPoint();
                // TODO Trim
//                Element arc = new Arc(firstNegative, lastNegative, center, true);
//                arc.setLayer(getLayer());
//
//                trimResult.add(arc);
            }
            else if (negativeIntersections.size() > 0
                    && positiveIntersections.size() > 0) {
//                Point firstPositive = positiveIntersections.first().getPoint();
//                Point lastNegative = negativeIntersections.last().getPoint();
                // TODO Trim
//                Element arc = new Arc(firstPositive, lastNegative, center, true);
//                arc.setLayer(getLayer());
//
//                trimResult.add(arc);
            }
//        }
//        catch (NullArgumentException e) {
//            // Should not catch this exception
//            e.printStackTrace();
//        }
//        catch (InvalidArgumentException e) {
//            // Should not catch this exception
//            e.printStackTrace();
//        }

        return trimResult;
    }

    /**
     * Gets all the proper intersections of the collection of references with
     * this element.
     * 
     * @param references
     *            A collection of references
     * @return A collection of proper intersections points
     */
    private Collection<Point> getIntersectionPoints (
            Collection<Element> references) {

        Collection<Point> intersectionPoints = new ArrayList<Point>();

        for (Element element : references) {
            try {
                if (element != this) {
                    Collection<Point> inter = element.getIntersection(this);
                    for (Point point : inter) {
                        if (this.contains(point) && element.contains(point)) {
                            intersectionPoints.add(point);
                        }
                    }
                }
            }
            catch (NullArgumentException e) {
                // Should never catch this exception
                e.printStackTrace();
            }
        }

        return intersectionPoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.PointSortable#getSortedPointSet(com.tarantulus.archimedes.model.Point,
     *      java.util.Collection)
     */
    public SortedSet<ComparablePoint> getSortedPointSet (Point referencePoint,
            Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        for (Point point : intersectionPoints) {
            try {
                double key = Geometrics.calculateRelativeAngle(center,
                        referencePoint, point);
                ComparablePoint orderedPoint = new ComparablePoint(point,
                        new DoubleKey(key));
                sortedSet.add(orderedPoint);
            }
            catch (NullArgumentException e) {
                // Should not catch this exception
                e.printStackTrace();
            }

        }

        return sortedSet;
        
    }

	@Override
	public void draw(OpenGLWrapper wrapper) {
		Point center = this.getCenter();

		this.drawCurvedShape(wrapper, center, 0, 2 * Math.PI);		
	}

	@Override
	public boolean intersects(Rectangle rectangle) throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
	}
}
