package br.org.archimedes.polyline.polygon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

public class Polygon {

	private Point center;
	private Point initialPoint;
	private int sides;
	
	public Polygon(Point center, Point initialPoint, int sides, boolean insideCircle)
		throws NullArgumentException, InvalidArgumentException {
		
	    if (center.equals(initialPoint)) {
	        throw new InvalidArgumentException();
	    }
    	if (insideCircle) {
    	    double r = center.calculateDistance(initialPoint);
    	    initialPoint.rotate(center, Math.PI/(sides));
    	    Vector normalized = (new Vector(center, initialPoint)).normalized();
    	    initialPoint = center.addVector(normalized.multiply(r/Math.cos(Math.PI/sides)));
    	}
        if ((center == null) || (initialPoint == null)) {
            throw new NullArgumentException();
        } else {
            this.center = center;
            this.initialPoint = initialPoint;
        }
        if (sides < 3) {
            throw new InvalidArgumentException();
        } else {
            this.sides = sides;
        }
	}

	public Point getCenter() {
		return center;
	}

	public Point getInitialPoint() {
		return initialPoint;
	}

	public int getSides() {
		return sides;
	}

	public List<Point> getVertexPoints() {

		List<Point> vertexPoints = new ArrayList<Point>();
		Point auxiliarPoint = getInitialPoint().clone();
		for (int i = 0; i < getSides(); i++) {
			try {
				auxiliarPoint.rotate(getCenter(), 2 * Math.PI / getSides());
				vertexPoints.add(auxiliarPoint.clone());
			} catch (NullArgumentException e) {
				e.printStackTrace();
			}
		}

		return vertexPoints;
	}
}
