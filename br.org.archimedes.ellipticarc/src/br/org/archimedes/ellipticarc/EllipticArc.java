package br.org.archimedes.ellipticarc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import br.org.archimedes.Geometrics;

public class EllipticArc extends Element implements Offsetable {

	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private Point initialPoint;
	private double initialAngle;
	private double endAngle;
	private Point endPoint;
	private double phi;
	private ArrayList<Point> focus;

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
		
		
		// TODO Auto-generated constructor stub
	}

	public boolean equals(EllipticArc ellipticArc) {

		boolean result = true;

		if (ellipticArc == null) {
			result = false;
		} else if (!this.center.equals(ellipticArc.getCenter())
				|| !this.widthPoint.equals(ellipticArc.getWidthPoint())
				|| !this.heightPoint.equals(ellipticArc.getHeightPoint())
				|| !this.endPoint.equals(ellipticArc.getEndPoint())
				|| !this.initialPoint.equals(ellipticArc.getInitialPoint())) {
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

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getWidthPoint() {
		return widthPoint;
	}

	public void setWidthPoint(Point widthPoint) {
		this.widthPoint = widthPoint;
	}

	public Point getHeightPoint() {
		return heightPoint;
	}

	public void setHeightPoint(Point heightPoint) {
		this.heightPoint = heightPoint;
	}

	public Point getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(Point initialPoint) {
		this.initialPoint = initialPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public boolean isPositiveDirection(Point point)
			throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends ReferencePoint> getReferencePoints(
			Rectangle area) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getProjectionOf(Point point) throws NullArgumentException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(OpenGLWrapper wrapper) {
		// TODO Auto-generated method stub
		
	}
}
