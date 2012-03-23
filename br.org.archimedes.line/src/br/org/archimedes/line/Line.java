/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Luiz C. Real, Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2006/03/23, 22:12:54, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line project.<br>
 */

package br.org.archimedes.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Filletable;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;
import br.org.archimedes.move.MoveCommand;

/**
 * Belongs to package br.org.archimedes.line.
 */
public class Line extends Element implements Offsetable, Filletable {

	private Point initialPoint;

	private Point endingPoint;

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
	public Line (double x1, double y1, double x2, double y2) throws InvalidArgumentException {

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
	public Line (Point initialPoint, Point endingPoint) throws NullArgumentException,
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
				double angle = Geometrics.calculateAngle(getInitialPoint(), point);

				if (Math.abs(getAngle() - angle) <= Constant.EPSILON) {
					double distance = Geometrics.calculateDistance(getInitialPoint(), point);
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
	private double getAngle () {

		return Geometrics.calculateAngle(initialPoint.getX(), initialPoint.getY(), endingPoint
				.getX(), endingPoint.getY());
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#clone()
	 */
	public Element clone () {

		Line clone = null;
		try {
			clone = new Line(initialPoint, endingPoint);
			clone.setLayer(getLayer());
		}
		catch (Exception e) {
			// Should never happen
			e.printStackTrace();
		}
		return clone;
	}

	/**
	 * @param distance
	 *            The distance where the line should be copied to
	 * @return A copied line that is parallel to this one but passes by a point that is orthogonally
	 *         distant of "distance" from the initial point
	 */
	public Line cloneWithDistance (double distance) {

		Vector direction = new Vector(getInitialPoint(), getEndingPoint());
		direction = Geometrics.normalize(direction);
		direction = direction.getOrthogonalVector();
		direction = direction.multiply(distance);

		Line returnLine = (Line) this.clone();
		returnLine.move(direction.getX(), direction.getY());
		returnLine.setLayer(getLayer());

		return returnLine;
	}

	/**
	 * @param point
	 *            The point to be checked
	 * @return false if the determinant between the initial, the ending and the point is negative,
	 *         true otherwise.<BR>
	 *         Assuming you are heading from the initial to the ending point, false if the point is
	 *         at your right, true otherwise.
	 * @throws NullArgumentException
	 *             Thrown if the point is null
	 */
	public boolean isPositiveDirection (Point point) throws NullArgumentException {

		double determinant = Geometrics.calculateDeterminant(getInitialPoint(), getEndingPoint(),
				point);

		return (determinant >= 0);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.endingPoint == null) ? 0 : this.endingPoint.hashCode());
		result = result + ((this.initialPoint == null) ? 0 : this.initialPoint.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#equals(br.org.archimedes.model.Element)
	 */
	public boolean equals (Object object) {

		boolean equals = false;
		try {
			equals = this.equals((Line) object);
		}
		catch (ClassCastException e) {
			// It's not equal.
		}
		return equals;
	}

	/**
	 * @param line
	 *            The line to be compared.
	 * @return True if both lines have the same extreme points.
	 */
	public boolean equals (Line line) {

		if (line == null) {
			return false;
		}

		return pointsMatch(line.getInitialPoint(), line.getEndingPoint())
		|| pointsMatch(line.getEndingPoint(), line.getInitialPoint());
	}

	/**
	 * @param initial
	 *            Point to match with my initial point
	 * @param ending
	 *            Point to match with my ending point
	 * @return true if the initial point matches my initial and the ending matches my ending, false
	 *         otherwise
	 */
	private boolean pointsMatch (Point initial, Point ending) {

		return getInitialPoint().equals(initial) && getEndingPoint().equals(ending);
	}

	/**
	 * @return The length of the line
	 */
	private double getLength () {

		return Geometrics.calculateDistance(initialPoint.getX(), initialPoint.getY(), endingPoint
				.getX(), endingPoint.getY());
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#isInside(br.org.archimedes.model.Rectangle)
	 */
	public boolean isInside (Rectangle rectangle) {

		return getInitialPoint().isInside(rectangle) && getEndingPoint().isInside(rectangle);
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#move(double, double)
	 */
	public void move (double deltaX, double deltaY) {

		getInitialPoint().setX(getInitialPoint().getX() + deltaX);
		getInitialPoint().setY(getInitialPoint().getY() + deltaY);
		getEndingPoint().setX(getEndingPoint().getX() + deltaX);
		getEndingPoint().setY(getEndingPoint().getY() + deltaY);
	}

	public Rectangle getBoundaryRectangle () {

		return new Rectangle(getInitialPoint().getX(), getInitialPoint().getY(), getEndingPoint()
				.getX(), getEndingPoint().getY());
	}

	/**
	 * @return the point that represents the center of the line
	 */
	private Point getCentralPoint () {

		Collection<Point> points = getPoints();
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
	 * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point )
	 */
	public Point getProjectionOf (Point point) throws NullArgumentException {

		if (point == null) {
			throw new NullArgumentException();
		}

		Vector direction = new Vector(initialPoint, endingPoint);
		Vector distance = new Vector(initialPoint, point);
		direction = direction.multiply(1.0 / direction.getNorm());
		direction = direction.multiply(direction.dotProduct(distance));
		return initialPoint.addVector(direction);
	}

	public Line getSegment () {

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes.model .Rectangle)
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

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.elements.Element#getPoints()
	 */
	public @Override
	List<Point> getPoints () {

		List<Point> points = new ArrayList<Point>();
		points.add(getInitialPoint());
		points.add(getEndingPoint());
		return points;
	}

	/*
	 * (non-Javadoc)
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
			// Should never reach this block
			e.printStackTrace();
		}
	}

	@Override
	public List<Point> getExtremePoints () {

		List<Point> extremes = new LinkedList<Point>();
		extremes.add(getInitialPoint());
		extremes.add(getEndingPoint());
		return extremes;
	}





	public Collection<UndoableCommand> getFilletCommands(Point arcCenter, Point arcIntersectionWithThisElement, Point arcIntersectionWithThatElement, Point force) throws NullArgumentException {

		Point pointToBeMoved;				

		pointToBeMoved = getPointToBeMovedForFillet(arcCenter,
				arcIntersectionWithThisElement, arcIntersectionWithThatElement);
		
		Collection<UndoableCommand> ret = new ArrayList<UndoableCommand>();
		
		if (isInvalidFillet(arcCenter, arcIntersectionWithThisElement, arcIntersectionWithThatElement, pointToBeMoved)) {
			UndoableCommand eraseThis = new PutOrRemoveElementCommand(this, true);
			ret.add(eraseThis);
		} else {
			Collection<Point> points = new ArrayList<Point>(); points.add(pointToBeMoved); 
			HashMap<Element, Collection<Point>> hash = new HashMap<Element, Collection<Point>>();
			hash.put(this, points);
			try {
				if (force != null)			
					ret.add(new MoveCommand(hash, new Vector(pointToBeMoved, force)));
				else
					ret.add(new MoveCommand(hash, new Vector(pointToBeMoved, arcIntersectionWithThisElement)));
			} catch (NullArgumentException e) {
				// Should never reach this block
				e.printStackTrace();
			}
		}
		return ret;

	}

	public Point getPointToBeMovedForFillet(Point arcCenter,
			Point arcIntersectionWithThisElement,
			Point arcIntersectionWithThatElement) throws NullArgumentException {
		Point pointToBeMoved;
		boolean signAreaElement1 = Geometrics.calculateSignedTriangleArea(arcIntersectionWithThisElement, arcIntersectionWithThatElement, initialPoint) > 0;
		boolean signAreaElement2 = Geometrics.calculateSignedTriangleArea(arcIntersectionWithThisElement, arcIntersectionWithThatElement, endingPoint) > 0;

		if (signAreaElement1 == signAreaElement2) {
			if (Geometrics.calculateDistance(initialPoint, arcIntersectionWithThisElement) < Geometrics.calculateDistance(endingPoint, arcIntersectionWithThisElement))
				pointToBeMoved = initialPoint;
			else
				pointToBeMoved = endingPoint;
		} else {
			boolean signArea = Geometrics.calculateSignedTriangleArea(arcIntersectionWithThisElement, arcIntersectionWithThatElement, arcCenter) > 0;
			pointToBeMoved = (signArea == signAreaElement2)? initialPoint : endingPoint;  
		}		
		return pointToBeMoved;
	}

	public boolean isInvalidFillet(Point arcCenter, Point arcIntersectionWithThisElement, Point arcIntersectionWithThatElement, Point pointToBeMoved) {
		Point otherPoint = (pointToBeMoved == initialPoint)? endingPoint : initialPoint;
		boolean expectedSignArea = Geometrics.calculateSignedTriangleArea(arcIntersectionWithThisElement, arcIntersectionWithThatElement, arcCenter) > 0;
		boolean filletSignArea = Geometrics.calculateSignedTriangleArea(arcIntersectionWithThisElement, arcIntersectionWithThatElement, otherPoint) > 0;
		if (expectedSignArea != filletSignArea) {
			return true;
		}
		return false;
	}

	public Point getTangencyLinePoint(Point intersection, Point click) {
		if (click.equals(intersection)) {			
			return initialPoint.equals(intersection)? endingPoint : initialPoint;
		}
		return click;
	}

	






}
