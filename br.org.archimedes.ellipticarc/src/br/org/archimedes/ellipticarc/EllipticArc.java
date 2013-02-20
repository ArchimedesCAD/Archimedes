/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Roberto L. M. Rodrigues, Eduardo Morais and Neuton Jr.<br>
 * <br>
 * This file was created on 2013/01/31, 13:06:39, by Roberto L. M. Rodrigues and Neuton Jr.<br>
 * It is part of package br.org.archimedes.ellipticArc on the br.org.archimedes.ellipticArc project.<br>
 */

package br.org.archimedes.ellipticarc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Geometrics;
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

public class EllipticArc extends Element implements Offsetable {

	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private Point initialPoint;
	private double initialAngle;
	private double endAngle;
	private Point endPoint;
	private double phi;
	private double a; // semi-major axis
	private double b; // semi-minor axis
	private ArrayList<Point> focus;
	ArrayList<Point> points = null;

	/**
	 * Constructor.
	 * 
	 * @param center
	 *            The elliptic arc's center
	 * @param widthPoint
	 *            The elliptic arc's width point
	 * @param heightPoint
	 *            The elliptic arc's height point
	 * @param initialPoint
	 *            The elliptic arc's initial point
	 * @param endPoint
	 *            The elliptic arc's end point
	 * @throws NullArgumentException
	 *             In case the point is null
	 * @throws InvalidArgumentException
	 *             In case the distance between focus and center
	 */
	
	public EllipticArc(Point center, Point widthPoint, Point heightPoint,
			Point initialPoint, Point endPoint) throws InvalidArgumentException, NullArgumentException {
		if(center == null || widthPoint == null || heightPoint == null ||
				initialPoint == null || endPoint == null) 
			throw new InvalidArgumentException();
		if(center.equals(widthPoint) || center.equals(heightPoint) || widthPoint.equals(heightPoint))
			throw new InvalidArgumentException();
		if(Geometrics.calculateDistance(center, widthPoint) < Geometrics.calculateDistance(center, heightPoint)) {
			Point temp = widthPoint;
			widthPoint = heightPoint;
			heightPoint = temp;
		}
		
		this.center = center;
		this.widthPoint = widthPoint;
		this.heightPoint = heightPoint;
		this.focus = calculateFocusPoints();
		this.endAngle = Geometrics.calculateAngle(center, endPoint);
		this.initialAngle = Geometrics.calculateAngle(center, initialPoint);

		
		if(!contains(initialPoint) || !contains(endPoint))
			throw new InvalidArgumentException();
		
		this.initialPoint = initialPoint;
		this.endPoint = endPoint;
		this.phi = Geometrics.calculatePhi(center, widthPoint);
		
		Vector haxis = new Vector(center, widthPoint);
		Vector vaxis = new Vector(center, heightPoint);
		a = haxis.getNorm();
		b = vaxis.getNorm();
	}

	public boolean equals(EllipticArc ellipticArc) {

		boolean result = true;

		if (ellipticArc == null) {
			result = false;
		} else if (!this.center.equals(center)
				|| !this.widthPoint.equals(widthPoint)
				|| !this.heightPoint.equals(heightPoint)
				|| !this.endPoint.equals(endPoint)
				|| !this.initialPoint.equals(initialPoint)) {
			result = false;
		}
		
		return result;
	}
	@Override
	public boolean equals(Object object) {
		if(object.getClass().equals(this.getClass())) return equals((EllipticArc)object);
		return false;
	}
	
	public ArrayList<Point> calculateFocusPoints() {
		ArrayList<Point> focusPoints = new ArrayList<Point>();

		// Formulae reference: http://www.mathopenref.com/ellipsefoci.html
		// http://www.mathopenref.com/ellipsesemiaxes.html

		double widthDist = center.calculateDistance(widthPoint);
		double heightDist = center.calculateDistance(heightPoint);

		double F = 0.0;
		Vector e1 = null;
		if (widthDist > heightDist) {
			// The semi-major axis is the horizontal one.
			F = Math.sqrt(widthDist * widthDist - heightDist * heightDist);
			e1 = new Vector(center, widthPoint).normalized();
		} else {
			F = Math.sqrt(heightDist * heightDist - widthDist * widthDist);
			e1 = new Vector(center, heightPoint).normalized();
		}

		focusPoints.add(center.addVector(e1.multiply(F)));
		focusPoints.add(center.addVector(e1.multiply(-F)));

		return focusPoints;
	}

	
	@Override
	public boolean isPositiveDirection(Point point)
			throws NullArgumentException {
		return !contains(point);
	}

	@Override
	public Element cloneWithDistance(double distance)
			throws InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EllipticArc clone() {
		EllipticArc e = null;
		try {
		    e =	new EllipticArc(center, widthPoint, heightPoint, initialPoint, endPoint);
		} catch (InvalidArgumentException f) {
			f.printStackTrace();
		} catch (NullArgumentException f) {
			f.printStackTrace();
		}
		return e;
	}

	@Override
	public Rectangle getBoundaryRectangle() {
		
		double[] coordinate = getExtremePoints(a, b);
		double x1 = calculatePointFromAngle(coordinate[0], phi).getX();
		double x2 = calculatePointFromAngle(coordinate[1], phi).getX();
		
		double y1 = calculatePointFromAngle(coordinate[2], phi).getY();
		double y2 = calculatePointFromAngle(coordinate[3], phi).getY();
		
		return new Rectangle(x1, y1, x2, y2);
	}

	private double[] getExtremePoints(double a, double b) {
		double extremeCoordinateX1 = Math.atan((- b / a) * Math.tan(phi));
		double extremeCoordinateX2 = extremeCoordinateX1 + Math.PI;
		
		double extremeCoordinateY1 = Math.atan((b / a) * 1/Math.tan(phi));
		double extremeCoordinateY2 = extremeCoordinateY1 + Math.PI;
		double[] returnValue = {extremeCoordinateX1,extremeCoordinateX2, extremeCoordinateY1, extremeCoordinateY2};
		return returnValue;
	}

	@Override
	public Collection<? extends ReferencePoint> getReferencePoints(
			Rectangle area) {

		//references = new ArrayList<ReferencePoint>();
		//references.add(getCenter());
				
		return null;
	}

	@Override
	public Point getProjectionOf(Point point) throws NullArgumentException {
		if(point == null)
			throw new NullArgumentException();
		Point projection = null;
		
		Collection<Point> intersectionWithLine = getPointsForEllipse();

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

	@Override
	public boolean contains(Point point) throws NullArgumentException {
		
		if(!Geometrics.IsPointInEllipse(center, widthPoint, this.focus, point))
			return false;
		double angle = Geometrics.calculateAngle(center, point);
		if(initialAngle < endAngle)
			if (initialAngle <= angle && angle <= endAngle) return true;
			else return false;
		else
			if (initialAngle <= angle || angle <= endAngle) return true;
			else return false;
	}

	@Override
	public List<Point> getPoints() {
		List<Point> points = new ArrayList<Point>();
		points.add(center);
		points.add(widthPoint);
		points.add(heightPoint);
		points.add(initialPoint);
		points.add(endPoint);

		return points;
	}
	
	private Point calculatePointFromAngle(double angle, double phi) {

		double x = center.getX() + a * Math.cos(angle)
				* Math.cos(phi) - b * Math.sin(angle)
				* Math.sin(phi);
		double y = center.getY() + a * Math.cos(angle)
				* Math.sin(phi) + b * Math.sin(angle)
				* Math.cos(phi);
		return new Point(x, y);
	}

	@Override
	public void draw(OpenGLWrapper wrapper) {
		ArrayList<Point> points = getPointsForEllipse();

		wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
		try {
			wrapper.drawFromModel(points);
		} catch (NullArgumentException e) {
			// Should never reach this block.
			e.printStackTrace();
		}
	}

	private ArrayList<Point> getPointsForEllipse() {
		double initialAngle = 0.0;
		double endingAngle = 0.0;
		double increment = Math.PI / 360;
		if (this.points != null)
			return points;
		
		points = new ArrayList<Point>();

		try {
			initialAngle = Geometrics.calculateAngle(center, initialPoint);
			endingAngle = Geometrics.calculateAngle(center, endPoint);
		} catch (NullArgumentException e1) {
			//This should never happen
			e1.printStackTrace();
		}
		
		if(initialAngle > endAngle)
			initialAngle -= 2*Math.PI;
		
		for (double angle = initialAngle; angle <= endingAngle; angle += increment) {
			points.add(calculatePointFromAngle(angle, phi));
		}
		points.add(calculatePointFromAngle(endingAngle, phi));
		return points;
	}
}
