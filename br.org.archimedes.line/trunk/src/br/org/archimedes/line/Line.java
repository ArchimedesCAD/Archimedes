/*
 * Created on 23/03/2006
 */

package br.org.archimedes.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.FilletableElement;
import br.org.archimedes.model.JoinableElement;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.PointSortable;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Trimmable;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;

/**
 * Belongs to package com.tarantulus.archimedes.model.
 */
public class Line extends Element implements Offsetable, Trimmable,
        PointSortable, FilletableElement, JoinableElement {

    private Point initialPoint;

    private Point endingPoint;

    private Layer parentLayer;


    /**
     * Constructor.
     * 
     * @param x1
     *            the x coordinate of the first point
     * @param y1
     *            the y coordinate of the first point
     * @param x2
     *            the x coordinate of the second point
     * @param y2
     *            the y coordinate of the second point
     * @throws InvalidArgumentException
     *             Thrown if (x1,y1) equals (x2,y2)
     */
    public Line (double x1, double y1, double x2, double y2)
            throws InvalidArgumentException {

        this.initialPoint = new Point(x1, y1);
        this.endingPoint = new Point(x2, y2);

        if (initialPoint.equals(endingPoint)) {
            throw new InvalidArgumentException();
        }
    }

    /**
     * Constructor
     * 
     * @param initialPoint
     *            The first point
     * @param endingPoint
     *            The second point
     * @throws NullArgumentException
     *             In case some argument is null.
     * @throws InvalidArgumentException
     *             In case both points are equal.
     */
    public Line (Point initialPoint, Point endingPoint)
            throws NullArgumentException, InvalidArgumentException {

        if (initialPoint == null || endingPoint == null) {
            throw new NullArgumentException();
        }

        if (initialPoint.equals(endingPoint)) {
            throw new InvalidArgumentException();
        }

        this.initialPoint = initialPoint.clone();
        this.endingPoint = endingPoint.clone();
    }

    /**
     * Constructor
     * 
     * @param initialPoint
     *            The first point
     * @param length
     *            The length of the line
     * @param angle
     *            The angle between the line and the x axis
     * @throws NullArgumentException
     *             In case some argument is null.
     */
    public Line (Point initialPoint, double length, double angle)
            throws NullArgumentException {

        if (initialPoint != null) {
            this.initialPoint = initialPoint.clone();
            double x = length * Math.cos(angle) + getInitialPoint().getX();
            double y = length * Math.sin(angle) + getInitialPoint().getY();
            this.endingPoint = new Point(x, y);
        }
        else {
            throw new NullArgumentException();
        }
    }

    /**
     * @return Return the initial point (the first point passed).
     */
    public Point getInitialPoint () {

        return initialPoint;
    }

    /**
     * @return Return the ending point (the second point passed).
     */
    public Point getEndingPoint () {

        return endingPoint;
    }

    /**
     * @param point
     *            The point to be tested.
     * @return Return true if the line contains the point, false otherwise.<br>
     *         (A line does not contains a null point).
     * @throws NullArgumentException
     *             In case the point is null.
     */
    public boolean contains (Point point) throws NullArgumentException {

        boolean contains = false;

        if (point != null) {
            if ( !point.equals(getInitialPoint())) {
                double angle = Geometrics.calculateAngle(getInitialPoint(),
                        point);

                if (Math.abs(getAngle() - angle) <= Constant.EPSILON) {
                    double distance = Geometrics.calculateDistance(
                            getInitialPoint(), point);
                    if (distance <= getLength() + Constant.EPSILON) {
                        contains = true;
                    }
                }
            }
            else {
                contains = true;
            }
        }
        else {
            throw new NullArgumentException();
        }
        return contains;
    }

    /**
     * @return Return the angle between the line and the x-axis.
     */
    public double getAngle () {

        return Geometrics.calculateAngle(initialPoint.getX(), initialPoint
                .getY(), endingPoint.getX(), endingPoint.getY());
    }

    /**
     * This method verify if this line intersects the parameter rectangle.
     * 
     * @param rectangle
     *            The rectangle to be tested.
     * @return True if the line intersects the parameter rectangle.
     * @throws NullArgumentException
     *             In case the rectangle is null.
     */
    public boolean intersects (Rectangle rect) throws NullArgumentException {

        boolean intersects = false;
        // TODO Implementar
        // if (rect != null) {
        // Collection<Line> edges = rect.getEdges();
        // for (Line edge : edges) {
        // intersects = this.intersectsLine(edge);
        // if (intersects) {
        // break;
        // }
        // }
        // }
        // else {
        // throw new NullArgumentException();
        // }

        return intersects;

    }

    /**
     * This method verify if this line intersects the parameter line.
     * 
     * @param line
     *            The line to be tested.
     * @return True if the line intersects the parameter line.
     * @throws NullArgumentException
     *             In case the line is null.
     */
    // private boolean intersectsLine (Line line) throws NullArgumentException {
    //
    // if (line != null) {
    // Point a1 = getInitialPoint();
    // Point b1 = getEndingPoint();
    // Point a2 = line.getInitialPoint();
    // Point b2 = line.getEndingPoint();
    //
    // if (this.contains(a2) || this.contains(b2) || line.contains(a1)
    // || line.contains(b1)) {
    // return true;
    // }
    //
    // // TODO implementar corretamente
    // if (true) {
    // return true;
    // }
    // }
    // else {
    // throw new NullArgumentException();
    // }
    //
    // return false;
    // }
    /**
     * @see com.tarantulus.archimedes.model.elements.Element#getIntersection(com.tarantulus.archimedes.model.elements.Element)
     */
//    public Collection<Point> getIntersection (Element element)
//            throws NullArgumentException {
//
//        // TODO
//        return Collections.emptyList();
//    }

    /**
     * @param distance
     *            The distance where the line should be copied to
     * @return A copied line that is parallel to this one but passes by a point
     *         that is orthogonally distant of "distance" from the initial point
     */
    public Line cloneWithDistance (double distance) {

        Vector direction = new Vector(getInitialPoint(), getEndingPoint());
        direction = Geometrics.normalize(direction);
        direction = direction.getOrthogonalVector();
        direction = direction.multiply(distance);

        Line returnLine = (Line) this.clone();
        returnLine.move(direction.getX(), direction.getY());
        returnLine.setLayer(parentLayer);

        return returnLine;
    }

    /**
     * @param point
     *            The point to be checked
     * @return false if the determinant between the initial, the ending and the
     *         point is negative, true otherwise.<BR>
     *         Assuming you are heading from the initial to the ending point,
     *         false if the point is at your right, true otherwise.
     * @throws NullArgumentException
     *             Thrown if the point is null
     */
    public boolean isPositiveDirection (Point point)
            throws NullArgumentException {

        double determinant = Geometrics.calculateDeterminant(getInitialPoint(),
                getEndingPoint(), point);

        return (determinant >= 0);
    }

    /**
     * @param line
     *            The line to be compared.
     * @return True if both lines have the same extreme points.
     */
    public boolean equals (Line line) {

        boolean equals = false;
        equals = (Math.abs(line.getInitialPoint().getX()
                - this.getInitialPoint().getX()) <= Constant.EPSILON)
                && (Math.abs(line.getInitialPoint().getY()
                        - this.getInitialPoint().getY()) <= Constant.EPSILON)
                && (Math.abs(line.getEndingPoint().getX()
                        - this.getEndingPoint().getX()) <= Constant.EPSILON)
                && (Math.abs(line.getEndingPoint().getY()
                        - this.getEndingPoint().getY()) <= Constant.EPSILON);

        if ( !equals) {
            equals = (Math.abs(line.getInitialPoint().getX()
                    - this.getEndingPoint().getX()) <= Constant.EPSILON)
                    && (Math.abs(line.getInitialPoint().getY()
                            - this.getEndingPoint().getY()) <= Constant.EPSILON)
                    && (Math.abs(line.getEndingPoint().getX()
                            - this.getInitialPoint().getX()) <= Constant.EPSILON)
                    && (Math.abs(line.getEndingPoint().getY()
                            - this.getInitialPoint().getY()) <= Constant.EPSILON);
        }

        return equals;
    }

    /**
     * @return The length of the line
     */
    public double getLength () {

        return Geometrics.calculateDistance(initialPoint.getX(), initialPoint
                .getY(), endingPoint.getX(), endingPoint.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#clone()
     */
    public Element clone () {

        Line clone = null;
        try {
            clone = new Line(initialPoint, endingPoint);
            clone.setLayer(parentLayer);
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }
        return clone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#isInside(com.tarantulus.archimedes.model.Rectangle)
     */
    public boolean isInside (Rectangle rectangle) {

        return getInitialPoint().isInside(rectangle)
                && getEndingPoint().isInside(rectangle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#move(double, double)
     */
    public void move (double deltaX, double deltaY) {

        getInitialPoint().setX(getInitialPoint().getX() + deltaX);
        getInitialPoint().setY(getInitialPoint().getY() + deltaY);
        getEndingPoint().setX(getEndingPoint().getX() + deltaX);
        getEndingPoint().setY(getEndingPoint().getY() + deltaY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#equals(com.tarantulus.archimedes.model.Element)
     */
    public boolean equals (Object object) {

        boolean equals = false;
        try {
            equals = ((Line) object).equals(this);
        }
        catch (ClassCastException e) {
            // It's not equal.
        }
        return equals;
    }

    public Rectangle getBoundaryRectangle () {

        return new Rectangle(getInitialPoint().getX(),
                getInitialPoint().getY(), getEndingPoint().getX(),
                getEndingPoint().getY());
    }

    /**
     * @return the extreme points of the line.
     */
    public Collection<Point> getExtremePoints () {

        Collection<Point> extremePoints = new ArrayList<Point>();
        extremePoints.add(getInitialPoint());
        extremePoints.add(getEndingPoint());

        return extremePoints;
    }

    /**
     * @return the point that represents the center of the line
     */
    private Point getCentralPoint () {

        Collection<Point> points = getExtremePoints();
        Point point = null;

        try {

            point = Geometrics.getMeanPoint(points);
        }
        catch (NullArgumentException e) {

            System.err.println("Exception NullArgumentException caught."); //$NON-NLS-1$
            e.printStackTrace();
        }

        return point;
    }

    public String toString () {

        String string = "Line: from " + initialPoint.toString() + " to " //$NON-NLS-1$ //$NON-NLS-2$
                + endingPoint.toString();
        return string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus.archimedes.model.Point)
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        Vector direction = new Vector(initialPoint, endingPoint);
        Vector distance = new Vector(initialPoint, point);
        direction = direction.multiply(1.0 / direction.getNorm());
        direction = direction.multiply(direction.dotProduct(distance));
        return initialPoint.addVector(direction);
    }

    public Element fillet (Point intersection, Point direction) {

        Line result = (Line) this.clone();
        if ( !(intersection == null || intersection.equals(initialPoint) || intersection
                .equals(endingPoint))) {
            Point extremePoint;
            try {
                extremePoint = result.getNearestExtremePoint(direction);
                extremePoint.setX(intersection.getX());
                extremePoint.setY(intersection.getY());
            }
            catch (NullArgumentException e) {
                // Ignores when it happens
            }
        }
        return result;
    }

    public Line getSegment () {

        return this;
    }

    public Element join (Element element) {

        // JoinableElement joinableElement = (JoinableElement) element;
        // TODO implementar
        return this;
    }

    public Element joinWithLine (Line line) {

        Element element = null;
        Line line1 = this;
        Line line2 = (Line) line;
        try {
            if (line1.contains(line2.getInitialPoint())
                    && line1.contains(line2.getEndingPoint())) {
                element = line1;
            }
            else if (line2.contains(line1.getInitialPoint())
                    && line2.contains(line1.getEndingPoint())) {
                element = line2;
            }
            else {
                double dist1 = Geometrics.calculateDistance(line1
                        .getInitialPoint(), line2.getInitialPoint());
                double dist2 = Geometrics.calculateDistance(line1
                        .getInitialPoint(), line2.getEndingPoint());
                double dist3 = Geometrics.calculateDistance(line1
                        .getEndingPoint(), line2.getInitialPoint());
                double dist4 = Geometrics.calculateDistance(line1
                        .getEndingPoint(), line2.getEndingPoint());

                double max = Math.max(Math.max(dist1, dist2), Math.max(dist3,
                        dist4));

                Point initialPoint = null, endingPoint = null;
                if (max == dist1) {
                    initialPoint = line1.getInitialPoint();
                    endingPoint = line2.getInitialPoint();
                }
                else if (max == dist2) {
                    initialPoint = line1.getInitialPoint();
                    endingPoint = line2.getEndingPoint();
                }
                else if (max == dist3) {
                    initialPoint = line1.getEndingPoint();
                    endingPoint = line2.getInitialPoint();
                }
                else if (max == dist4) {
                    initialPoint = line1.getEndingPoint();
                    endingPoint = line2.getEndingPoint();
                }

                element = new Line(initialPoint, endingPoint);
            }
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }

        return element;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.JoinableElement#isJoinableWith(com.tarantulus.archimedes.model.Element)
     */
    public boolean isJoinableWith (Element element) {

        boolean result = false;
        /*
         * TODO Implementar if (Utils.isInterfaceOf(element,
         * JoinableElement.class)) { JoinableElement joinableElement =
         * (JoinableElement) element; result =
         * joinableElement.isJoinableWithLine(this); }
         */

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Trimmable#trim(java.util.Set,
     *      com.tarantulus.archimedes.model.Point)
     */
    public Collection<Element> trim (Collection<Element> references, Point click) {

        Collection<Element> trimResult = new ArrayList<Element>();
        Collection<Point> intersectionPoints = getIntersectionPoints(references);
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(
                initialPoint, intersectionPoints);

        Vector direction = new Vector(initialPoint, endingPoint);
        Vector clickVector = new Vector(initialPoint, click);
        double key = direction.dotProduct(clickVector);
        ComparablePoint clickPoint = null;
        try {
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        SortedSet<ComparablePoint> headSet = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> tailSet = sortedPointSet.tailSet(clickPoint);

        try {
            if (headSet.size() == 0 && tailSet.size() > 0) {
                Element line = new Line(tailSet.first().getPoint(), endingPoint);
                line.setLayer(getLayer());

                trimResult.add(line);
            }
            else if (tailSet.size() == 0 && headSet.size() > 0) {
                Element line = new Line(initialPoint, headSet.last().getPoint());
                line.setLayer(getLayer());

                trimResult.add(line);
            }
            else if (headSet.size() > 0 && tailSet.size() > 0) {
                Element line1 = new Line(initialPoint, headSet.last()
                        .getPoint());
                Element line2 = new Line(tailSet.first().getPoint(),
                        endingPoint);
                line1.setLayer(getLayer());
                line2.setLayer(getLayer());

                trimResult.add(line1);
                trimResult.add(line2);
            }
        }
        catch (Exception e) {
            // Should not catch any exception
            e.printStackTrace();
        }

        return trimResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.PointSortable#getSortedPointSet(com.tarantulus.archimedes.model.Point,
     *      java.util.Collection)
     */
    public SortedSet<ComparablePoint> getSortedPointSet (Point referencePoint,
            Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();

        Point otherPoint = initialPoint;
        if (referencePoint.equals(initialPoint)) {
            otherPoint = endingPoint;
        }

        Vector direction = new Vector(referencePoint, otherPoint);
        for (Point point : intersectionPoints) {
            Vector pointVector = new Vector(referencePoint, point);
            double key = direction.dotProduct(pointVector);
            ComparablePoint element;
            try {
                element = new ComparablePoint(point, new DoubleKey(key));
                sortedPointSet.add(element);
            }
            catch (NullArgumentException e) {
                // Should never reach
                e.printStackTrace();
            }
        }

        return sortedPointSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getReferencePoints(com.tarantulus.archimedes.model.Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Point centralPoint = getCentralPoint();
        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();

        if (centralPoint.isInside(area)) {
            try {
                references.add(new TrianglePoint(centralPoint, getPoints()));
            }
            catch (NullArgumentException e) {
                // Should never reach this block
                e.printStackTrace();
            }
        }

        if (initialPoint.isInside(area)) {
            try {
                references.add(new SquarePoint(initialPoint, initialPoint));
            }
            catch (NullArgumentException e) {
                // Should never reach this block
                e.printStackTrace();
            }
        }

        if (endingPoint.isInside(area)) {
            try {
                references.add(new SquarePoint(endingPoint, endingPoint));
            }
            catch (NullArgumentException e) {
                // Should never reach this block
                e.printStackTrace();
            }
        }

        return references;
    }

    /**
     * Gets all the proper intersections of the collection of references with
     * this element. The initial point and the ending point are not considered
     * intersections.
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
                        if (this.contains(point) && element.contains(point)
                                && !this.initialPoint.equals(point)
                                && !this.endingPoint.equals(point)) {
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

    public boolean isCollinearWith (Element element) {

        // TODO Implementar
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#isParallelTo(com.tarantulus.archimedes.model.elements.Element)
     */
    public boolean isParallelTo (Element element) {

        // TODO Implementar
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(getInitialPoint());
        points.add(getEndingPoint());
        return points;
    }

    public @Override
    Point getNearestExtremePoint (Point point) throws NullArgumentException {

        double distanceToInitial = Geometrics.calculateDistance(point,
                getInitialPoint());
        double distanceToEnding = Geometrics.calculateDistance(point,
                getEndingPoint());

        Point returnPoint = null;
        if (distanceToEnding < distanceToInitial) {
            returnPoint = getEndingPoint();
        }
        else {
            returnPoint = getInitialPoint();
        }
        return returnPoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.FilletableElement#getFilletSegment(com.tarantulus.archimedes.model.Point,
     *      com.tarantulus.archimedes.model.Point)
     */
    public Line getFilletSegment (Point intersection, Point click) {

        Line segment = null;

        try {
            double determinant = Geometrics.calculateDeterminant(initialPoint,
                    endingPoint, intersection);
            if (Math.abs(determinant) < Constant.EPSILON) {
                double distToInitial = Geometrics.calculateDistance(
                        initialPoint, intersection);
                double distToEnding = Geometrics.calculateDistance(endingPoint,
                        intersection);

                Point extremePoint = endingPoint;
                if (distToInitial < distToEnding) {
                    extremePoint = initialPoint;
                }

                segment = new Line(extremePoint, intersection);
            }
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }

        return segment;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

        List<Point> points = new ArrayList<Point>();
        points.add(initialPoint);
        points.add(endingPoint);
        try {
            wrapper.drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // Should not happen
        }
    }
}
