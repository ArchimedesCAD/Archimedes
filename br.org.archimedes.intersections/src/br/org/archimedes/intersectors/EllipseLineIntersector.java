package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class EllipseLineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Collection<Point> intersections = new ArrayList<Point>();

		Line line;
		Ellipse ellipse;

		if (element.getClass() == Line.class) {
			line = (Line) element;
			ellipse = (Ellipse) otherElement;
		} else {
			line = (Line) otherElement;
			ellipse = (Ellipse) element;
		}
		
		//TODO: use EllipseInfiniteLineIntersector
		
		return intersections;
	}
}
