package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class EllipsePolylineIntersector implements Intersector{

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Polyline polyline;
		Ellipse ellipse;

		if (element.getClass() == Line.class) {
			polyline = (Polyline) element;
			ellipse = (Ellipse) otherElement;
		} else {
			polyline = (Polyline) otherElement;
			ellipse = (Ellipse) element;
		}
		
		Collection<Point> results = new ArrayList<Point>();
		
		return results;
	}

}
