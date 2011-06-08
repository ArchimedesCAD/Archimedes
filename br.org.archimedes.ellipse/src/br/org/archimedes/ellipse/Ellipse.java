package br.org.archimedes.ellipse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Constant;
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

	public Point getwidthPoint() {
		return widthPoint;
	}

	public Point getheightPoint() {
		return heightPoint;
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
				|| !this.widthPoint.equals(ellipse.getwidthPoint()) 
				|| !this.heightPoint.equals(ellipse.getheightPoint())) {
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
			Point x1 = calculatePointFromAngle(t1);
			Point x4 = calculatePointFromAngle(t4);
			if (x1.getX() < x4.getX()) {
				xmin = x1;
				xmax = x4;
			} else {
				xmin = x4;
				xmax = x1;
			}
		} else {
			Point x2 = calculatePointFromAngle(t2);
			Point x3 = calculatePointFromAngle(t3);
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
			Point y1 = calculatePointFromAngle(t1);
			Point y4 = calculatePointFromAngle(t4);
			if (y1.getY() < y4.getY()) {
				ymin = y1;
				ymax = y4;
			} else {
				ymin = y4;
				ymax = y1;
			}
		} else {
			Point y2 = calculatePointFromAngle(t2);
			Point y3 = calculatePointFromAngle(t3);
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
				Point point = calculatePointFromAngle(angle);
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
	public Point getProjectionOf(Point point) throws NullArgumentException {
		// TODO Auto-generated method stub
		return null;
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
		if (Double.compare((dx * dx) / (a * a) + (dy * dy) / (b * b), 1.0) <= 0)
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
		// TODO
		return "Ellipse centered at " + this.getCenter().toString();
	}

	public Element cloneWithDistance(double distance) throws InvalidParameterException {

		// Vector v = new Vector(this.center, this.widthPoint);
		// double radius = v.getNorm();
		// v = v.multiply(0.5);
		// Point focus1 = this.center.clone().addVector(v);
		// v = v.multiply(-1);
		// Point focus2 = this.center.clone().addVector(v);
		if (distance <= 0.0)
			return this.clone();
		// ajustando o eixo principal
		Vector v1 = new Vector(this.center, this.widthPoint);
		Vector distanceVector1 = v1.clone().normalized().multiply(distance);
		v1.add(distanceVector1);
		Point newWidthPoint = this.widthPoint.clone().addVector(v1);

		// ajustando o eixo secundario
		Vector v2 = new Vector(this.center, this.heightPoint);
		Vector distanceVector2 = v2.clone().normalized().multiply(distance);
		v2.add(distanceVector2);
		Point newHeightPoint = this.heightPoint.clone().addVector(v2);

		Ellipse newEllipse = null;
		try {
			newEllipse = new Ellipse(center.clone(), newWidthPoint, newHeightPoint);
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newEllipse;
	}

	@Override
	public void draw(OpenGLWrapper wrapper) {

		double initialAngle = 0.0;
		double endingAngle = 2 * Math.PI;
		double increment = Math.PI / 360;
		ArrayList<Point> points = new ArrayList<Point>();

		for (double angle = initialAngle; angle <= endingAngle; angle += increment) {
			points.add(calculatePointFromAngle(angle));
		}
		points.add(calculatePointFromAngle(endingAngle));

		wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
		try {
			wrapper.drawFromModel(points);
		} catch (NullArgumentException e) {
			// Should never reach this block.
			e.printStackTrace();
		}
	}

	private Point calculatePointFromAngle(double angle) {
		
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

		if (center.getX() < widthPoint.getX())
			return center.addVector(ortho.multiply(height));
		else
			return center.addVector(ortho.multiply(-height));
	}

}