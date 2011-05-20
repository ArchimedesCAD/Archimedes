package br.org.archimedes.ellipse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

public class Ellipse extends Element {

	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private double fi;

	public Ellipse(Point center, Point widthPoint, Point heightPoint)
			throws NullArgumentException, InvalidArgumentException {

		if (center == null) {
			throw new NullArgumentException();
		}

		if (Math.abs(center.calculateDistance(widthPoint)) <= Constant.EPSILON) {
			throw new InvalidArgumentException();
		}

		if (Math.abs(center.calculateDistance(heightPoint)) <= Constant.EPSILON) {
			throw new InvalidArgumentException();
		}
		this.center = center;
		this.widthPoint = widthPoint;
		Vector ortho = (new Vector(center, widthPoint)).getOrthogonalVector()
				.normalized();
		Vector axis = new Vector(center, heightPoint);
		double height = ortho.dotProduct(axis);
		if (center.getX() < widthPoint.getX())
			this.heightPoint = center.addVector(ortho.multiply(height));
		else
			this.heightPoint = center.addVector(ortho.multiply(-height));
		Vector xaxis = new Vector(new Point(1, 0));
		Vector haxis = new Vector(center, widthPoint);
		if (center.getY() < widthPoint.getY())
			this.fi = Math.acos((xaxis.dotProduct(haxis))
					/ (xaxis.getNorm() * haxis.getNorm()));
		else
			this.fi = -Math.acos((xaxis.dotProduct(haxis))
					/ (xaxis.getNorm() * haxis.getNorm()));
	}

	public Ellipse(Point center, Vector hSemiAxis, Vector vSemiAxis)
			throws NullArgumentException, InvalidArgumentException {

		if (center == null) {
			throw new NullArgumentException();
		}

		if (hSemiAxis.getNorm() <= Constant.EPSILON) {
			throw new InvalidArgumentException();
		}

		if (vSemiAxis.getNorm() <= Constant.EPSILON) {
			throw new InvalidArgumentException();
		}
		this.center = center;
		this.widthPoint = center.addVector(hSemiAxis);
		this.heightPoint = center.addVector(vSemiAxis);
		Vector xaxis = new Vector(new Point(1, 0));
		this.fi = Math.acos((xaxis.dotProduct(hSemiAxis))
				/ (xaxis.getNorm() * hSemiAxis.getNorm()));
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
			ellipse = new Ellipse(center.clone(), widthPoint.clone(),
					heightPoint.clone());
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
		result = prime * result
				+ ((heightPoint == null) ? 0 : heightPoint.hashCode());
		result = prime * result
				+ ((widthPoint == null) ? 0 : widthPoint.hashCode());
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

		Point myCenter = this.getCenter();

		double left = myCenter.getX() - 40;
		double right = myCenter.getX() + 40;

		double bottom = myCenter.getY() - 40;
		double top = myCenter.getY() + 40;

		return new Rectangle(left, bottom, right, top);
	}

	@Override
	public Collection<? extends ReferencePoint> getReferencePoints(
			Rectangle area) {

		double initialAngle = 0.0;
		double endingAngle = 2 * Math.PI;
		Vector haxis = new Vector(center, widthPoint);
		Vector vaxis = new Vector(center, heightPoint);

		Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
		double increment = Math.PI / 2;

		ReferencePoint reference;
		try {
			reference = new CirclePoint(getCenter());
			if (reference.isInside(area)) {
				references.add(reference);
			}
			double angle;
			for (angle = initialAngle; angle < endingAngle; angle += increment) {
				double x = center.getX() + haxis.getNorm() * Math.cos(angle)
						* Math.cos(fi) - vaxis.getNorm() * Math.sin(angle)
						* Math.sin(fi);
				double y = center.getY() + haxis.getNorm() * Math.cos(angle)
						* Math.sin(fi) + vaxis.getNorm() * Math.sin(angle)
						* Math.cos(fi);
				Point point = new Point(x, y);
				reference = new RhombusPoint(point);
				if (reference.isInside(area)) {
					references.add(reference);
				}
			}
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
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
		x = point.getX() * Math.cos(-fi) - point.getY() * Math.sin(-fi);
		y = point.getY() * Math.cos(-fi) + point.getX() * Math.sin(-fi);
		dx = x - getCenter().getX();
		dy = y - getCenter().getY();
		a = (new Vector(center, widthPoint)).getNorm();
		b = (new Vector(center, heightPoint)).getNorm();
		if (Double.compare((dx * dx) / (a * a) + (dy * dy) / (b * b), 1.0) <= 0)
			return true;
		return false;
	}

    public boolean isPositiveDirection (Point point) {
    	return !contains(point);
    }
    /*
    public Element cloneWithDistance (double distance) throws InvalidParameterException {
    	Ellipse clone;
		try {
			clone = new Ellipse(center.clone(), widthPoint.addVector(new Vector(0, dingPoint)), heightPoint);
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        clone.setLayer(getLayer());
    }*/
    
	@Override
	public List<Point> getPoints() {

		List<Point> points = new ArrayList<Point>();
		points.add(center);
		points.add(widthPoint);
		points.add(heightPoint);

		return points;
	}

	public void scale(Point reference, double proportion)
			throws NullArgumentException, IllegalActionException {

		center.scale(reference, proportion);
		widthPoint.scale(reference, proportion);
		heightPoint.scale(reference, proportion);
	}
	
	
    public void rotate (Point rotateReference, double angle) throws NullArgumentException {

        if (rotateReference == null) {
            throw new NullArgumentException();
        }
        
		center.rotate(rotateReference, angle);
		widthPoint.rotate(rotateReference, angle);
		heightPoint.rotate(rotateReference, angle);
		
		//corrigindo fi
		Vector xaxis = new Vector(new Point(1, 0));
		Vector haxis = new Vector(center, widthPoint);
		if (center.getY() < widthPoint.getY())
			this.fi = Math.acos((xaxis.dotProduct(haxis))
					/ (xaxis.getNorm() * haxis.getNorm()));
		else
			this.fi = -Math.acos((xaxis.dotProduct(haxis))
					/ (xaxis.getNorm() * haxis.getNorm()));
    }
	

	public boolean isClosed() {

		return true;
	}

	public String toString() {
		// todo
		return "Ellipse centered at " + this.getCenter().toString();
	}

	@Override
	public void draw(OpenGLWrapper wrapper) {

		double initialAngle = 0.0;
		double endingAngle = 2 * Math.PI;
		Vector xaxis = new Vector(new Point(1, 0));
		Vector haxis = new Vector(center, widthPoint);
		Vector vaxis = new Vector(center, heightPoint);
		// double fi =
		// Math.acos((xaxis.dotProduct(haxis))/(xaxis.getNorm()*haxis.getNorm()));

		ArrayList<Point> points = new ArrayList<Point>();
		double increment = Math.PI / 360;

		double angle;
		for (angle = initialAngle; angle <= endingAngle; angle += increment) {
			double x = center.getX() + haxis.getNorm() * Math.cos(angle)
					* Math.cos(fi) - vaxis.getNorm() * Math.sin(angle)
					* Math.sin(fi);
			double y = center.getY() + haxis.getNorm() * Math.cos(angle)
					* Math.sin(fi) + vaxis.getNorm() * Math.sin(angle)
					* Math.cos(fi);
			points.add(new Point(x, y));
		}

		angle = endingAngle;
		double x = center.getX() + haxis.getNorm() * Math.cos(angle)
				* Math.cos(fi) - vaxis.getNorm() * Math.sin(angle)
				* Math.sin(fi);
		double y = center.getY() + haxis.getNorm() * Math.cos(angle)
				* Math.sin(fi) + vaxis.getNorm() * Math.sin(angle)
				* Math.cos(fi);
		points.add(new Point(x, y));

		wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
		try {
			wrapper.drawFromModel(points);
		} catch (NullArgumentException e) {
			// Should never reach this block.
			e.printStackTrace();
		}
	}

}
