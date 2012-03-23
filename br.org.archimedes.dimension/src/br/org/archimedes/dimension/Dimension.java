/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci, Julien Renaut, Marcos P. Moreti, Luiz C. Real - later contributions<br>
 * <br>
 * This file was created on 2007/04/27, 11:06:57, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.dimension on the br.org.archimedes.dimension project.<br>
 */
package br.org.archimedes.dimension;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.batik.svggen.font.Font;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.QuickMoveFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.text.Text;

/**
 * @author marivb
 */
public class Dimension extends Element {

	public static final double DIST_FROM_ELEMENT = 10.0;

	public static final double DIST_AFTER_LINE = 10.0;

	private Point initialPoint;

	private Point endingPoint;

	private Point distance;

	private Text text;

	private double fontSize;

	/**
	 * @param initialPoint
	 *            The initial point of the dimension
	 * @param endingPoint
	 *            The ending point of the dimension
	 * @param distance
	 *            The point that determines where the dimension will be placed
	 * @param text
	 * 			  The text of the dimension
	 * @throws NullArgumentException
	 *             In case any point is null.
	 * @throws InvalidArgumentException
	 *             In case the initial and ending points are the same.
	 */
	public Dimension (Point initialPoint, Point endingPoint, Point distance,
			Double fontSize, Text text) throws NullArgumentException,
			InvalidArgumentException {

		if (initialPoint == null || endingPoint == null || distance == null
				|| fontSize == null) {
			throw new NullArgumentException();
		}
		if (initialPoint.equals(endingPoint) || initialPoint.equals(distance)
				|| endingPoint.equals(distance)) {
			throw new InvalidArgumentException();
		}

		this.initialPoint = initialPoint;
		this.endingPoint = endingPoint;
		this.distance = distance.clone();
		this.fontSize = fontSize;
		this.text = text;
	}

	/**
	 * @param initialPoint
	 *            The initial point of the dimension
	 * @param endingPoint
	 *            The ending point of the dimension
	 * @param distance
	 *            The point that determines where the dimension will be placed
	 * @throws NullArgumentException
	 *             In case any point is null.
	 * @throws InvalidArgumentException
	 *             In case the initial and ending points are the same.
	 */
	public Dimension (Point initialPoint, Point endingPoint, Point distance,
			Double fontSize) throws NullArgumentException,
			InvalidArgumentException {

		if (initialPoint == null || endingPoint == null || distance == null
				|| fontSize == null) {
			throw new NullArgumentException();
		}
		if (initialPoint.equals(endingPoint) || initialPoint.equals(distance)
				|| endingPoint.equals(distance)) {
			throw new InvalidArgumentException();
		}

		this.initialPoint = initialPoint;
		this.endingPoint = endingPoint;
		this.distance = distance.clone();
		this.fontSize = fontSize;
		remakeDistance();
		text = makeText(Constant.DEFAULT_FONT);
	}

	/**
	 * @param initialPoint
	 *            Initial point
	 * @param endingPoint
	 *            Ending point
	 * @param distance
	 *            distance for displaying the dimension
	 * @param fontSize
	 *            size of text font
	 * @throws NullArgumentException
	 *             In case any argument is null
	 * @throws InvalidArgumentException
	 *             In case the initial and ending point are the same.
	 */
	public Dimension (Point initialPoint, Point endingPoint, Double distance,
			Double fontSize) throws NullArgumentException,
			InvalidArgumentException {

		this(initialPoint, endingPoint, distance, fontSize,
				Constant.DEFAULT_FONT);
	}

	/**
	 * @param initialPoint
	 *            Initial point
	 * @param endingPoint
	 *            Ending point
	 * @param distance
	 *            distance for displaying the dimension
	 * @param fontSize
	 *            size of text font
	 * @param font
	 *            the font to use
	 * @throws NullArgumentException
	 *             In case any argument is null
	 * @throws InvalidArgumentException
	 *             In case the initial and ending point are the same.
	 */
	public Dimension (Point initialPoint, Point endingPoint, Double distance,
			Double fontSize, Font font) throws NullArgumentException, InvalidArgumentException {

		if (initialPoint == null || endingPoint == null || distance == null
				|| fontSize == null) {
			throw new NullArgumentException();
		}
		if (initialPoint.equals(endingPoint)
				|| Math.abs(distance) < Constant.EPSILON) {
			throw new InvalidArgumentException();
		}

		this.initialPoint = initialPoint;
		this.endingPoint = endingPoint;
		Vector vector = new Vector(initialPoint, endingPoint);
		vector = Geometrics.orthogonalize(vector);
		Line line = new Line(initialPoint, initialPoint.addVector(vector));
		Line offseted = (Line) line.cloneWithDistance(distance);
		this.distance = offseted.getInitialPoint();
		this.fontSize = fontSize;
		remakeDistance();
		text = makeText(font);
	}

	/**
	 * Recalculates the distance point to be the middle of the dimension line,
	 * so that it may be used as a snap point.
	 */
	private void remakeDistance () {

		Line dimLine = getDimensionLine();
		Point newDistance = distance;
		try {
			newDistance = Geometrics.getMeanPoint(dimLine.getInitialPoint(),
					dimLine.getEndingPoint());
		}
		catch (NullArgumentException e) {
			// Should never happen
			e.printStackTrace();
		}
		Vector toMove = new Vector(distance, newDistance);
		distance.move(toMove.getX(), toMove.getY());
	}


	private String getDistanceText() throws NullArgumentException {
		Line lineToMeasure = getDimensionLine();
		Point initial = lineToMeasure.getInitialPoint();
		Point ending = lineToMeasure.getEndingPoint();
		double length = Geometrics.calculateDistance(initial, ending);
		DecimalFormat df = new DecimalFormat();
		String lengthStr = df.format(length);
		return lengthStr;
	}

	/**
	 * Makes the text for this dimension.
	 * @param font The font to be used for the Text
	 * 
	 * @return The created text.
	 */
	private Text makeText (Font font) {
		Line lineToMeasure = getDimensionLine();
		Point initial = lineToMeasure.getInitialPoint();
		Point ending = lineToMeasure.getEndingPoint();

		Text text = null;
		try {
			Point mean = Geometrics.getMeanPoint(initial, ending);
			text = new Text(getDistanceText(), mean, fontSize, font);
			double width = text.getWidth();

			if (isDimLineHorizontal()) {
				text.move( -width / 2.0, DIST_FROM_ELEMENT);
			}
			else {
				text.rotate(mean.clone(), Math.PI / 2.0);
				text.move( -DIST_FROM_ELEMENT, -width / 2.0);
			}
		}
		catch (Exception e) {
			// Should not happen
			e.printStackTrace();
		}

		return text;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#clone()
	 */
	@Override
	public Element clone () {

		Dimension clone = null;
		try {
			clone = new Dimension(initialPoint.clone(), endingPoint.clone(),
					distance.clone(), fontSize, text.clone());
		}
		catch (Exception e) {
			// Should never happen
			e.printStackTrace();
		}
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
	 */
	@Override
	public boolean contains (Point point) throws NullArgumentException {

		// Dimension will not be used for trim, extend, fillet or intersection
		// snap points.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object object) {

		boolean result = (object == this);
		if ( !result && object != null && object.getClass() == getClass()) {
			Dimension other = (Dimension) object;
			Line line = getDimensionLine();
			Line otherLine = other.getDimensionLine();
			result = line.equals(otherLine);
			if (result) {
				Point otherInitial = other.getInitialPoint();
				Point otherEnding = other.getEndingPoint();
				if (otherInitial.equals(getInitialPoint())) {
					result = otherEnding.equals(getEndingPoint());
				}
				else if (otherInitial.equals(getEndingPoint())) {
					result = otherInitial.equals(getEndingPoint());
				}
				else {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * @return The line that should be measured.
	 */
	private Line getDimensionLine () {

		Point first, second;
		if (isDimLineHorizontal()) {
			first = new Point(initialPoint.getX(), distance.getY());
			second = new Point(endingPoint.getX(), distance.getY());
		}
		else {
			first = new Point(distance.getX(), initialPoint.getY());
			second = new Point(distance.getX(), endingPoint.getY());
		}

		Line dimLine = null;
		try {
			dimLine = new Line(first, second);
		}
		catch (Exception e) {
			// Should not happen
			e.printStackTrace();
		}

		return dimLine;
	}

	/**
	 * @return true if the dimension line is horizontal, false if it is vertical
	 */
	private boolean isDimLineHorizontal () {

		Point point1 = new Point(initialPoint.getX(), endingPoint.getY());
		Point point2 = new Point(endingPoint.getX(), initialPoint.getY());

		double originalDeterminant = 0;
		double otherDeterminant = 0;
		try {
			originalDeterminant = Geometrics.calculateDeterminant(initialPoint,
					endingPoint, distance);
			otherDeterminant = Geometrics.calculateDeterminant(point1, point2,
					distance);
		}
		catch (NullArgumentException e) {
			// Should not happen
			e.printStackTrace();
		}
		boolean horizontal = originalDeterminant * otherDeterminant >= 0;
		return horizontal;
	}

	/**
	 * @return Returns the initialPoint.
	 */
	public Point getInitialPoint () {

		return this.initialPoint;
	}

	/**
	 * @return Returns the endingPoint.
	 */
	public Point getEndingPoint () {

		return this.endingPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#getBoundaryRectangle()
	 */
	@Override
	public Rectangle getBoundaryRectangle () {

		Rectangle boundary = null;

		Collection<Line> linesToDraw = getLinesToDraw();
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		for (Line line : linesToDraw) {
			boundary = line.getBoundaryRectangle();
			Point lowerLeft = boundary.getLowerLeft();
			minX = Math.min(minX, lowerLeft.getX());
			minY = Math.min(minY, lowerLeft.getY());
			Point upperRight = boundary.getUpperRight();
			maxX = Math.max(maxX, upperRight.getX());
			maxY = Math.max(maxY, upperRight.getY());
		}

		return new Rectangle(minX, minY, maxX, maxY);
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#getPoints()
	 */
	@Override
	public List<Point> getPoints () {

		List<Point> points = new ArrayList<Point>();
		points.add(initialPoint);
		points.add(endingPoint);
		points.add(distance);
		points.addAll(text.getPoints());
		return points;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model
	 * .Point)
	 */
	@Override
	public Point getProjectionOf (Point point) throws NullArgumentException {

		// Dimension will not be used for perpendicular snap points.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes.
	 * model.Rectangle)
	 */
	@Override
	public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

		ArrayList<ReferencePoint> references = new ArrayList<ReferencePoint>();
		try {
			references.add(new SquarePoint(initialPoint, initialPoint));
			references.add(new SquarePoint(endingPoint, endingPoint));
			references.add(new SquarePoint(distance, distance));
			references.addAll(text.getReferencePoints(area));
		}
		catch (NullArgumentException e) {
			// Should not happen
			e.printStackTrace();
		}
		return references;
	}

	/*
	 * (non-Javadoc)
	 * @see br.org.archimedes.model.Element#move(java.util.Collection,
	 * br.org.archimedes.model.Vector)
	 */
	public void move (Collection<Point> pointsToMove, Vector vector)
	throws NullArgumentException {

		super.move(pointsToMove, vector);
		remakeDistance();
		if ( !pointsToMove.contains(text.getLowerLeft())) {
			try {
				text = new Text(getDistanceText(), text.getLowerLeft(), text.getSize());
			} catch (InvalidArgumentException e) {
				// Should not happen
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return The lines to be drawn for this dimension
	 */
	public Collection<Line> getLinesToDraw () {

		double sign = 0;
		Line dimLine = null, initialLine = null, endingLine = null;
		Collection<Line> lines = new ArrayList<Line>();
		try {
			double initialX = initialPoint.getX();
			double initialY = initialPoint.getY();
			if (isDimLineHorizontal()) {
				sign = Math.signum(initialX - endingPoint.getX());

				double dimInitial = initialX + (sign * DIST_AFTER_LINE);
				double dimEnding = endingPoint.getX()
				- (sign * DIST_AFTER_LINE);

				dimLine = new Line(dimInitial, distance.getY(), dimEnding,
						distance.getY());

				sign = Math.signum(distance.getY() - initialY);

				double initialInitial = (initialY + sign * DIST_FROM_ELEMENT);
				double initialEnding = distance.getY() + sign * DIST_AFTER_LINE;

				if (Math.abs(initialInitial - initialEnding) > Constant.EPSILON) {
					initialLine = new Line(initialX, initialInitial, initialX,
							initialEnding);
				}

				double endingInitial = (endingPoint.getY() + sign
						* DIST_FROM_ELEMENT);
				double endingEnding = distance.getY() + sign * DIST_AFTER_LINE;

				if (Math.abs(endingInitial - endingEnding) > Constant.EPSILON) {
					endingLine = new Line(endingPoint.getX(), endingInitial,
							endingPoint.getX(), endingEnding);
				}
			}
			else {
				sign = Math.signum(initialY - endingPoint.getY());

				double dimInitial = initialY + (sign * DIST_AFTER_LINE);
				double dimEnding = endingPoint.getY()
				- (sign * DIST_AFTER_LINE);

				dimLine = new Line(distance.getX(), dimInitial,
						distance.getX(), dimEnding);

				sign = Math.signum(distance.getX() - initialX);

				double initialInitial = (initialX + sign * DIST_FROM_ELEMENT);
				double initialEnding = distance.getX() + sign * DIST_AFTER_LINE;

				if (Math.abs(initialInitial - initialEnding) > Constant.EPSILON) {
					initialLine = new Line(initialInitial, initialY,
							initialEnding, initialY);
				}

				double endingInitial = (endingPoint.getX() + sign
						* DIST_FROM_ELEMENT);
				double endingEnding = distance.getX() + sign * DIST_AFTER_LINE;

				if (Math.abs(endingInitial - endingEnding) > Constant.EPSILON) {
					endingLine = new Line(endingInitial, endingPoint.getY(),
							endingEnding, endingPoint.getY());
				}
			}

			lines.add(dimLine);
			if (initialLine != null) {
				lines.add(initialLine);
			}
			if (endingLine != null) {
				lines.add(endingLine);
			}
		}
		catch (InvalidArgumentException e) {
			// Should not happen
			e.printStackTrace();
		}

		return lines;
	}

	/**
	 * @return The distance Point (not yet very useful)
	 */
	public Point getDistancePoint () {

		return distance;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {

		return "Dimension: measures " + initialPoint.toString() + " and " //$NON-NLS-1$ //$NON-NLS-2$
		+ endingPoint.toString() + " at distance " //$NON-NLS-1$
		+ distance.toString();
	}

	@Override
	public void draw (OpenGLWrapper wrapper) {
		Collection<Line> linesToDraw = getLinesToDraw();
		try {
			InputController inputController = Utils.getInputController();
			CommandFactory currentFactory = inputController.getCurrentFactory();
			if (currentFactory != null && currentFactory instanceof QuickMoveFactory) {
				QuickMoveFactory quickMoveFactory = (QuickMoveFactory) currentFactory;
				if (!quickMoveFactory.isMoving(this)) {
					text.draw(wrapper);
				}
			} else {
				text.draw(wrapper);
			}
			for (Line line : linesToDraw) {
				wrapper.drawFromModel(new ArrayList<Point>(line
						.getPoints()));
			}
		}
		catch (NullArgumentException e) {
			// Won't happen, but anyway...
			e.printStackTrace();
		}
	}

	/**
	 * @return the text
	 */
	public Text getText () {

		return this.text;
	}

	/**
	 * @return The size of the text contained in this dimension
	 */
	public double getTextSize () {

		return this.text.getSize();
	}

	@Override
	public void drawClone(OpenGLWrapper wrapper) {
		Collection<Line> linesToDraw = getLinesToDraw();
		try {
			text.draw(wrapper);
			for (Line line : linesToDraw) {
				wrapper.drawFromModel(new ArrayList<Point>(line
						.getPoints()));
			}
		}
		catch (NullArgumentException e) {
			// Won't happen, but anyway...
			e.printStackTrace();
		}
	}
}
