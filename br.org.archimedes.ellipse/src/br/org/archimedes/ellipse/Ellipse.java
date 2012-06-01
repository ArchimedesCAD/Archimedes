package br.org.archimedes.ellipse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
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

public class Ellipse extends Element implements Offsetable {

	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private double fi;

	public Ellipse(Point center, Point widthPoint, Point heightPoint) throws NullArgumentException, InvalidArgumentException {

		validateArguments(center, widthPoint, heightPoint);

		this.center = center;
		this.widthPoint = widthPoint;
		this.heightPoint = calculateHeightPoint(center, widthPoint, heightPoint);
		this.fi = calculateFi(center, widthPoint);
	}

	public Ellipse(Point focus1, Point focus2, Double radius) throws NullArgumentException, InvalidArgumentException {
		
		validateArguments(focus1, focus2, radius);

		this.center = new Point((focus1.getX() + focus2.getX()) / 2, (focus1.getY() + focus2.getY()) / 2);
		this.widthPoint = center.addVector(new Vector(focus1, focus2));
		this.fi = calculateFi(center, widthPoint);

		double angle = this.fi + Math.PI / 2; //rotaciona 90 graus.
		this.heightPoint = new Point(center.getX() + radius * Math.cos(angle), center.getY() + radius * Math.sin(angle));

	}

	public Point getCenter() {
		return center;
	}

	public Point getWidthPoint() {
		return widthPoint;
	}

	public Point getHeightPoint() {
		return heightPoint;
	}
	
	public double getFi() {
		return fi;
	}
	
	public Vector getSemiMajorAxis() {
		Vector widthVector = new Vector(center, widthPoint);
		Vector heightVector = new Vector(center, heightPoint);
		return (widthVector.getNorm() > heightVector.getNorm()) ? widthVector : heightVector;
	}

	public Vector getSemiMinorAxis() {
		Vector widthVector = new Vector(center, widthPoint);
		Vector heightVector = new Vector(center, heightPoint);
		return (widthVector.getNorm() > heightVector.getNorm()) ? heightVector : widthVector;
	}

	
	@Override
	public Element clone() {
		Ellipse ellipse = null;

		try {
			ellipse = new Ellipse(center.clone(), widthPoint.clone(), heightPoint.clone());
			ellipse.setLayer(getLayer());
		} catch (NullArgumentException e) {
			// Should never reach this block
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			// Should never reach this block
			e.printStackTrace();
		}
		return ellipse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + ((heightPoint == null) ? 0 : heightPoint.hashCode());
		result = prime * result + ((widthPoint == null) ? 0 : widthPoint.hashCode());
		return result;
	}

	public boolean equals(Ellipse ellipse) {

		boolean result = true;

		if (ellipse == null) {
			result = false;
		} else if (!this.center.equals(ellipse.getCenter()) 
				|| !this.widthPoint.equals(ellipse.getWidthPoint()) 
				|| !this.heightPoint.equals(ellipse.getHeightPoint())) {
			result = false;
		}
		return result;
	}

	@Override
	public boolean equals(Object object) {

		boolean result = false;

		if (object != null) {
			try {
				Ellipse ellipse = (Ellipse) object;
				result = this.equals(ellipse);
			} catch (ClassCastException e) {
				return false;
			}
		}
		return result;
	}

	@Override
	public Rectangle getBoundaryRectangle() {
		Rectangle boundary;
		Point xmin, xmax, ymin, ymax;

		double a = (new Vector(center, widthPoint)).getNorm();
		double b = (new Vector(center, heightPoint)).getNorm();
		double f = a * Math.cos(this.fi);
		double g = b * Math.sin(this.fi);

		double t1 = Math.acos(f / Math.sqrt(f * f + g * g));
		double t2 = Math.acos(-f / Math.sqrt(f * f + g * g));
		double t3 = -Math.acos(f / Math.sqrt(f * f + g * g));
		double t4 = -Math.acos(-f / Math.sqrt(f * f + g * g));

		if (fi < 0) {
			Point x1 = calculatePointFromAngle(t1, fi);
			Point x4 = calculatePointFromAngle(t4, fi);
			if (x1.getX() < x4.getX()) {
				xmin = x1;
				xmax = x4;
			} else {
				xmin = x4;
				xmax = x1;
			}
		} else {
			Point x2 = calculatePointFromAngle(t2, fi);
			Point x3 = calculatePointFromAngle(t3, fi);
			if (x2.getX() < x3.getX()) {
				xmin = x2;
				xmax = x3;
			} else {
				xmin = x3;
				xmax = x2;
			}
		}

		f = a * Math.sin(this.fi);
		g = b * Math.cos(this.fi);

		t1 = Math.acos(f / Math.sqrt(f * f + g * g));
		t2 = Math.acos(-f / Math.sqrt(f * f + g * g));
		t3 = -Math.acos(f / Math.sqrt(f * f + g * g));
		t4 = -Math.acos(-f / Math.sqrt(f * f + g * g));

		if ((fi > 0 && fi < Math.PI / 2) || (fi > -Math.PI / 2 && fi < 0)) {
			Point y1 = calculatePointFromAngle(t1, fi);
			Point y4 = calculatePointFromAngle(t4, fi);
			if (y1.getY() < y4.getY()) {
				ymin = y1;
				ymax = y4;
			} else {
				ymin = y4;
				ymax = y1;
			}
		} else {
			Point y2 = calculatePointFromAngle(t2, fi);
			Point y3 = calculatePointFromAngle(t3, fi);
			if (y2.getY() < y3.getY()) {
				ymin = y2;
				ymax = y3;
			} else {
				ymin = y3;
				ymax = y2;
			}
		}

		boundary = new Rectangle(xmin.getX(), ymin.getY(), xmax.getX(), ymax.getY());
		return boundary;
	}

	@Override
	public Collection<? extends ReferencePoint> getReferencePoints(Rectangle area) {

		double initialAngle = 0.0;
		double endingAngle = 2 * Math.PI;
		double increment = Math.PI / 2;
		Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();

		try {
			ReferencePoint reference = new CirclePoint(getCenter());
			if (reference.isInside(area)) {
				references.add(reference);
			}
			for (double angle = initialAngle; angle < endingAngle; angle += increment) {
				Point point = calculatePointFromAngle(angle, fi);
				reference = new RhombusPoint(point);
				if (reference.isInside(area)) {
					references.add(reference);
				}
			}
		} catch (NullArgumentException e) {
			e.printStackTrace();
		}
		return references;
	}

	@Override
	public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Point projection = null;
        
        if (getCenter().equals(point)) {
        	double widthToCenterDistance = this.widthPoint.calculateDistance(this.center);
        	double heightToCenterDistance = this.heightPoint.calculateDistance(this.center);
        	if(widthToCenterDistance < heightToCenterDistance) {
        		return this.widthPoint;
        	} else {
        		return this.heightPoint;
        	}
        }

        Collection<Point> intersectionWithLine = getEllipsePoints(point);

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
	public boolean contains(Point point) {
		double x, y, dx, dy, a, b;
		Point rotPoint = point.clone();
		try {
			rotPoint.rotate(center, -fi);
		} catch (NullArgumentException e) {
			e.printStackTrace();
		}
		x = rotPoint.getX();
		y = rotPoint.getY();
		dx = x - getCenter().getX();
		dy = y - getCenter().getY();
		a = (new Vector(center, widthPoint)).getNorm();
		b = (new Vector(center, heightPoint)).getNorm();
		
		if (Math.abs(((dx * dx) / (a * a) + (dy * dy) / (b * b)) - 1.0) < Constant.EPSILON)
			return true;
		return false;
	}

	public boolean isPositiveDirection(Point point) {
		return !contains(point);
	}

	@Override
	public List<Point> getPoints() {

		List<Point> points = new ArrayList<Point>();
		points.add(center);
		points.add(widthPoint);
		points.add(heightPoint);

		return points;
	}

	public void rotate(Point rotateReference, double angle) throws NullArgumentException {

		verifyNotNull(rotateReference);

		center.rotate(rotateReference, angle);
		widthPoint.rotate(rotateReference, angle);
		heightPoint.rotate(rotateReference, angle);

		this.fi = calculateFi(center, widthPoint);
	}

	public boolean isClosed() {
		return true;
	}

	public String toString() {
		return "Ellipse centered at " + this.getCenter().toString() 
			+ " and width " + this.getWidthPoint().toString()
			+ " with height " + this.getHeightPoint().toString();
	}

	public Element cloneWithDistance(double distance) throws InvalidParameterException {
		
        if (distance < 0) {
        	double minAxis = Math.min(center.calculateDistance(widthPoint), center.calculateDistance(heightPoint));
            if (Math.abs(minAxis - distance) <= Constant.EPSILON
                    || Math.abs(distance) > minAxis) {
                throw new InvalidParameterException();
            }
        }

		Point newWidthPoint = calculateOffsetPoint(distance, widthPoint);
		Point newHeightPoint = calculateOffsetPoint(distance, heightPoint);

		Ellipse newEllipse = null;
		try {
			newEllipse = new Ellipse(center.clone(), newWidthPoint, newHeightPoint);
		} catch (NullArgumentException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		return newEllipse;
	}

	private Point calculateOffsetPoint(double distance, Point point) {
		Vector axis = new Vector(center, point);
		Vector distanceVector = axis.clone().normalized().multiply(distance);
		Point offsetPoint = point.clone().addVector(distanceVector);
		return offsetPoint;
	}
	
	private Collection<Point> getEllipsePoints(Point point) throws NullArgumentException {
		if(point == null) throw new NullArgumentException();
		Collection<Point> points = new ArrayList<Point>();
		
		Point rotPoint = point.clone();
		rotPoint.rotate(center, -fi);

		Vector ray = new Vector(center, rotPoint);
		Vector e1 = new Vector(new Point(0, 0), new Point(1, 0));
		double angle = e1.dotProduct(ray)/ray.getNorm();
		Point point1;
		Point point2;
		if (rotPoint.getY() < center.getY()) {
			point1 = calculatePointFromAngle(-angle, 0);
			point2 = calculatePointFromAngle(-angle+Math.PI, 0);
		}
		else{
			point1 = calculatePointFromAngle(angle, 0);
			point2 = calculatePointFromAngle(Math.PI+angle, 0);
		}
		point1.rotate(point1, fi);
		point2.rotate(point2, fi);		
		
		points.add(point1);
		points.add(point2);
		
		return points;
	}

	@Override
	public void draw(OpenGLWrapper wrapper) {

		double initialAngle = 0.0;
		double endingAngle = 2 * Math.PI;
		double increment = Math.PI / 360;
		ArrayList<Point> points = new ArrayList<Point>();

		for (double angle = initialAngle; angle <= endingAngle; angle += increment) {
			points.add(calculatePointFromAngle(angle, fi));
		}
		points.add(calculatePointFromAngle(endingAngle, fi));

		wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
		try {
			wrapper.drawFromModel(points);
		} catch (NullArgumentException e) {
			// Should never reach this block.
			e.printStackTrace();
		}
	}
	
	@Override
	public void mirror(Point p1, Point p2) throws NullArgumentException, IllegalActionException {
		super.mirror(p1, p2);
		this.fi = calculateFi(center, widthPoint);
	}


	private Point calculatePointFromAngle(double angle, double fi) {
		
		Vector haxis = new Vector(center, widthPoint);
		Vector vaxis = new Vector(center, heightPoint);
		
		double x = center.getX() + haxis.getNorm() * Math.cos(angle) * Math.cos(fi) - vaxis.getNorm() * Math.sin(angle) * Math.sin(fi);
		double y = center.getY() + haxis.getNorm() * Math.cos(angle) * Math.sin(fi) + vaxis.getNorm() * Math.sin(angle) * Math.cos(fi);
		return new Point(x, y);
	}
	
	private void verifyNotNull(Point point) throws NullArgumentException {
		if (point == null) throw new NullArgumentException();
	}
	
	private void verifyNotInvalid(Double radius) throws InvalidArgumentException {
		if (Math.abs(radius) <= Constant.EPSILON) {
			throw new InvalidArgumentException();
		}
	}
	
	private void validateArguments(Point focus1, Point focus2, Double radius) throws NullArgumentException, InvalidArgumentException {
		verifyNotNull(focus1);
		verifyNotNull(focus2);
		
		verifyNotInvalid(radius);
	}

	private void validateArguments(Point center, Point widthPoint, Point heightPoint) throws NullArgumentException, InvalidArgumentException {
		verifyNotNull(center);
		verifyNotNull(widthPoint);
		verifyNotNull(heightPoint);
		
		verifyNotInvalid(center.calculateDistance(widthPoint));
		verifyNotInvalid(center.calculateDistance(heightPoint));
	}
	
	
	private double calculateFi(Point center, Point widthPoint) {
		Vector xaxis = new Vector(new Point(1, 0));
		Vector haxis = new Vector(center, widthPoint);
		if (center.getY() < widthPoint.getY())
			return Math.acos((xaxis.dotProduct(haxis)) / (xaxis.getNorm() * haxis.getNorm()));
		else
			return -Math.acos((xaxis.dotProduct(haxis)) / (xaxis.getNorm() * haxis.getNorm()));
	}

	private Point calculateHeightPoint(Point center, Point widthPoint, Point heightPoint) throws InvalidArgumentException {
		Vector ortho = (new Vector(center, widthPoint)).getOrthogonalVector().normalized();
		Vector axis = new Vector(center, heightPoint);
		double height = ortho.dotProduct(axis);

		if (Math.abs(height) < Constant.EPSILON) {
			throw new InvalidArgumentException();
		}

		if  (Math.abs(center.getX() - widthPoint.getX()) < Constant.EPSILON) {
			Vector e1 = new Vector(new Point(0, 0), new Point(1, 0));
			if (center.getY() > widthPoint.getY())
				return center.addVector(e1.multiply(axis.getNorm()));
			else 
				return center.addVector(e1.multiply(-axis.getNorm()));
		}
		else if (center.getX() < widthPoint.getX())
			return center.addVector(ortho.multiply(height));
		else// if (center.getX() > widthPoint.getX())
			return center.addVector(ortho.multiply(-height));

	}
	
	public Collection<Point> calculateFocusPoints() {
	    Collection<Point> focusPoints = new ArrayList<Point>();
	    
	    // Formulae reference:	http://www.mathopenref.com/ellipsefoci.html
	    //						http://www.mathopenref.com/ellipsesemiaxes.html
	    
	    double widthDist = center.calculateDistance(widthPoint);
		double heightDist = center.calculateDistance(heightPoint);
		
		double F = 0.0;
		Vector e1 = null;
		if(widthDist > heightDist) {
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
	
	public void translateToPoint(Point destiny) {
		Vector dist = new Vector(destiny, center);
		center.addVector(dist);
		widthPoint.addVector(dist);
		heightPoint.addVector(dist);
	}

}
