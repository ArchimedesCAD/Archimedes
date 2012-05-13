/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Marcos P. Moreti, Paulo L. Huaman, Hugo Corbucci - later contributions<br>
 * Bruno da Hora and Bruno Klava - later contributions <br>
 * <br>
 * This file was created on 2007/03/22, 10:02:32, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.infiniteline on the br.org.archimedes.infiniteline
 * project.<br>
 */

package br.org.archimedes.infiniteline;


import br.org.archimedes.semiline.Semiline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Filletable;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.TrianglePoint;

public class InfiniteLine extends Element implements Offsetable, Filletable{

    private Point initialPoint;

    private Point endingPoint;


    /**
     * Creates an infinite line given two points.
     * 
     * @param x1
     *            The X coordinate of the first point
     * @param y1
     *            The Y coordinate of the first point
     * @param x2
     *            The X coordinate of the second point
     * @param y2
     *            The Y coordinate of the second point
     * @throws InvalidArgumentException
     *             Thrown if boths points are equal.
     */
    public InfiniteLine (double x1, double y1, double x2, double y2)
            throws InvalidArgumentException {

        initialPoint = new Point(x1, y1);
        endingPoint = new Point(x2, y2);
        if (initialPoint.equals(endingPoint)) {
            throw new InvalidArgumentException();
        }
    }

    /**
     * Creates an infinite line given two points.
     * 
     * @param initialPoint
     *            The first point
     * @param endingPoint
     *            The second point
     * @throws NullArgumentException
     *             Thrown if any point is null.
     * @throws InvalidArgumentException
     *             Thrown if boths points are equal.
     */
    public InfiniteLine (Point initialPoint, Point endingPoint) throws NullArgumentException,
            InvalidArgumentException {

        if (initialPoint == null || endingPoint == null) {
            throw new NullArgumentException();
        }

        if (initialPoint.equals(endingPoint)) {
            throw new InvalidArgumentException();
        }

        this.initialPoint = initialPoint.clone();
        this.endingPoint = endingPoint.clone();
    }

    @Override
    public Element clone () {

        InfiniteLine clone = null;
        try {
            clone = new InfiniteLine(getInitialPoint(), getEndingPoint());
            clone.setLayer(getLayer());
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.model.Offsetable#cloneWithDistance(double)
     */
    public Element cloneWithDistance (double distance) {

        Vector direction = new Vector(getInitialPoint(), getEndingPoint());

        direction = Geometrics.normalize(direction);
        direction = direction.getOrthogonalVector();
        direction = direction.multiply(distance);

        InfiniteLine returnLine = (InfiniteLine) this.clone();
        returnLine.move(direction.getX(), direction.getY());
        returnLine.setLayer(getLayer());

        return returnLine;
    }

    @Override
    public boolean equals (Object object) {

        boolean equals = false;
        if (object != null && InfiniteLine.class.isAssignableFrom(object.getClass())) {
            equals = this.equals(((InfiniteLine) object));
        }

        return equals;
    }

    private boolean equals (InfiniteLine other) {

        if (other == null)
            return false;

        try {
            Vector direction = getDirectionVector();
            Vector otherDirection = other.getDirectionVector();
            boolean sameSense = direction.equals(otherDirection);
            boolean oppositeSense = direction.multiply(-1).equals(otherDirection);
            boolean sameDirection = sameSense || oppositeSense;
            return sameDirection && contains(other.getInitialPoint());
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return The vector formed by my initial to my ending point
     */
    private Vector getDirectionVector () {

        Vector direction = new Vector(getInitialPoint(), getEndingPoint());
        return direction.multiply(1.0/direction.getNorm());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        int prime = 31;
        int result = 1;
        Vector direction = getDirectionVector();
        if (direction.getX() < 0 || (direction.getX() <= Constant.EPSILON && direction.getY() < 0))
            direction = direction.multiply(-1);
        result = prime * result + direction.hashCode();
        return result;
    }

    /**
     * @param point
     *            The point to be tested.
     * @return Return true if the line contains the point, false otherwise.<br>
     *         (A line does not contains a null point).
     * @throws NullArgumentException
     *             In case the point is null.
     */
    @Override
    public boolean contains (Point point) throws NullArgumentException {

        boolean contains = false;

        if (point == null) {
            throw new NullArgumentException();
        }
        if ( !point.equals(getInitialPoint())) {
            double angle1 = Geometrics.calculateAngle(getInitialPoint(), point);
            double angle2 = Geometrics.calculateAngle(point, getInitialPoint());

            if (Math.abs(getAngle() - angle1) <= Constant.EPSILON
                    || Math.abs(getAngle() - angle2) <= Constant.EPSILON) {
                contains = true;
            }
        }
        else {
            contains = true;
        }

        return contains;
    }

    @Override
    public boolean isInside (Rectangle rectangle) {

        return false;
    }

    @Override
    public Rectangle getBoundaryRectangle () {

        return null;

    }

    public Rectangle getCreationBoundaryRectangle () {

        return new Rectangle(Math.min(initialPoint.getX(), endingPoint.getX()), Math.min(
                initialPoint.getY(), endingPoint.getY()), Math.max(initialPoint.getX(), endingPoint
                .getX()), Math.max(initialPoint.getY(), endingPoint.getY()));

    }

    /**
     * @param rectangle
     *            The rectangle that this line should cross in model coordinates.
     * @return null if this infinite line does not cross the rectangle, crosses it on infinite
     *         points or crosses it on a corner. Otherwise it will return two points that constitute
     *         the intersections of this infinite line with the rectangle or the starting point with
     *         an intersection.
     */
    public List<Point> getPointsCrossing (Rectangle rectangle) {

        List<Point> points;

        double sen = (initialPoint.getX() - endingPoint.getX());
        double tan = (initialPoint.getY() - endingPoint.getY()) / sen;
        if (Math.abs(sen) < Constant.EPSILON) {
            points = getVerticalLine(rectangle.getLowerLeft().getY(), rectangle.getUpperRight()
                    .getY());
        }
        else if (Math.abs(tan) < Constant.EPSILON) {
            points = getHorizontalLine(rectangle.getLowerLeft().getX(), rectangle.getUpperRight()
                    .getX());
        }
        else {
            double b = initialPoint.getY() - (tan) * initialPoint.getX();
            Point lowerLeftModel = rectangle.getLowerLeft();
            Point upperRightModel = rectangle.getUpperRight();

            Point lefterPoint = new Point(lowerLeftModel.getX(), (tan * lowerLeftModel.getX() + b));
            Point righterPoint = new Point(upperRightModel.getX(),
                    (tan * upperRightModel.getX() + b));
            Point lowerPoint = new Point((lowerLeftModel.getY() - b) / tan, lowerLeftModel.getY());
            Point upperPoint = new Point((upperRightModel.getY() - b) / tan, upperRightModel.getY());

            points = new LinkedList<Point>();
            if (lefterPoint.getY() <= upperRightModel.getY()
                    && lefterPoint.getY() >= lowerLeftModel.getY() && points.size() < 2) {
                points.add(lefterPoint);
            }
            if (righterPoint.getY() <= upperRightModel.getY()
                    && righterPoint.getY() >= lowerLeftModel.getY() && points.size() < 2) {
                points.add(righterPoint);
            }
            if (upperPoint.getX() <= upperRightModel.getX()
                    && upperPoint.getX() >= lowerLeftModel.getX() && points.size() < 2) {
                points.add(upperPoint);
            }
            if (lowerPoint.getX() <= upperRightModel.getX()
                    && lowerPoint.getX() >= lowerLeftModel.getX() && points.size() < 2) {
                points.add(lowerPoint);
            }

            if (points.size() < 2) {
                points = null;
            }
        }

        return points;
    }

    /**
     * @param minX
     *            The lowest X coordinate of the containing rectangle.
     * @param maxX
     *            The highest X coordinate of the containing rectangle.
     * @return List<Point> A list with the Points that define the horizontal line.
     */
    private List<Point> getHorizontalLine (double minX, double maxX) {

        List<Point> points = new LinkedList<Point>();
        points.add(new Point(minX, this.getInitialPoint().getY()));
        points.add(new Point(maxX, this.getInitialPoint().getY()));
        return points;
    }

    /**
     * @param minY
     *            The lowest Y coordinate of the containing rectangle.
     * @param maxY
     *            The highest Y coordinate of the containing rectangle.
     * @return List<Point> A list with the Points that define the vertical line.
     */
    private List<Point> getVerticalLine (double minY, double maxY) {

        List<Point> points = new LinkedList<Point>();
        points.add(new Point(this.getInitialPoint().getX(), minY));
        points.add(new Point(this.getInitialPoint().getX(), maxY));
        return points;
    }

    /**
     * @return Return the angle between the line and the x-axis.
     */
    public double getAngle () {

        return Geometrics.calculateAngle(initialPoint.getX(), initialPoint.getY(), endingPoint
                .getX(), endingPoint.getY());
    }

    /**
     * @return the initialPoint
     */
    public Point getInitialPoint () {

        return initialPoint;
    }

    /**
     * @return the endingPoint
     */
    public Point getEndingPoint () {

        return endingPoint;
    }

    @Override
    public List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(initialPoint);
        points.add(endingPoint);
        return points;
    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        double lineA = (endingPoint.getY() - initialPoint.getY())
                / (endingPoint.getX() - initialPoint.getX());

        if (lineA == 0) {
            return new Point(point.getX(), this.initialPoint.getY());
        }
        else if (lineA == Double.POSITIVE_INFINITY || lineA == Double.NEGATIVE_INFINITY) {
            return new Point(this.initialPoint.getX(), point.getY());
        }

        double lineB = endingPoint.getY() - (lineA * endingPoint.getX());

        double perpA = -1 / lineA;
        double perpB = point.getY() - (perpA * point.getX());

        double targetX = (perpB - lineB) / (lineA - perpA);
        double targetY = (perpA * targetX) + perpB;

        return new Point(targetX, targetY);
    }

    @Override
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new LinkedList<ReferencePoint>();

        List<Point> pointsCrossing = getPointsCrossing(area);
        if (pointsCrossing != null) {
            try {
                Iterator<Point> iterator = pointsCrossing.iterator();
                Point meanPoint = Geometrics.getMeanPoint(iterator.next(), iterator.next());
                if (meanPoint.isInside(area)) {
                    TrianglePoint meanReference = new TrianglePoint(meanPoint, initialPoint,
                            endingPoint);
                    references.add(meanReference);
                }
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
        return references;
    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.model.Offsetable#isPositiveDirection(br.org.archimedes.model.Point)
     */
    public boolean isPositiveDirection (Point point) throws NullArgumentException {

        double determinant = Geometrics.calculateDeterminant(getInitialPoint(), getEndingPoint(),
                point);

        return (determinant >= 0);
    }

    @Override
    public void draw (OpenGLWrapper wrapper) {

        Rectangle modelRect = br.org.archimedes.Utils.getWorkspace().getCurrentViewportArea();
        List<Point> pointsToDraw = getPointsCrossing(modelRect);
        if (pointsToDraw != null) {
            try {
                wrapper.drawFromModel(pointsToDraw);
            }
            catch (NullArgumentException e) {
                // Should not happen since I checked for null
                e.printStackTrace();
            }
        }
    }

    public String toString ( ) {
        return "Infinite Line: from =" + getInitialPoint() + " moving by " + getDirectionVector(); //$NON-NLS-1$ //$NON-NLS-2$
    }

	public Collection<UndoableCommand> getFilletCommands(Point arcCenter,
			Point arcIntersectionWithThisElement,
			Point arcIntersectionWithThatElement, Point force)
			throws NullArgumentException {
		
		Collection<UndoableCommand> ret = new ArrayList<UndoableCommand>(); 
		try {
			
			Point candidateDirectionPoint1 = initialPoint;
			Point candidateDirectionPoint2 = arcIntersectionWithThisElement.addVector(new Vector(candidateDirectionPoint1, arcIntersectionWithThisElement));
			Line line = new Line(candidateDirectionPoint1, candidateDirectionPoint2);
			
			Point pointToBeMoved = line.getPointToBeMovedForFillet(arcCenter, arcIntersectionWithThisElement, arcIntersectionWithThatElement);
			Point fixedPoint = (pointToBeMoved.equals(candidateDirectionPoint1))? candidateDirectionPoint2 : candidateDirectionPoint1;

			Semiline producedSemiline;
			if (force == null )
				producedSemiline = new Semiline(arcIntersectionWithThisElement, fixedPoint);
			else
				producedSemiline = new Semiline(force, fixedPoint);
			
			ret.add(new PutOrRemoveElementCommand(producedSemiline, false));
			ret.add(new PutOrRemoveElementCommand(this, true));
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	public Point getTangencyLinePoint(Point intersection, Point click) {
		if (click.equals(intersection)) {			
			return initialPoint.equals(intersection)? endingPoint : initialPoint;
		}
		return click;
	}
	
	
	
	
    
}
