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

public class EllipseLineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Line line;
		Ellipse ellipse;

		if (element.getClass() == Line.class) {
			line = (Line) element;
			ellipse = (Ellipse) otherElement;
		} else {
			line = (Line) otherElement;
			ellipse = (Ellipse) element;
		}
		
		InfiniteLine infiniteLine = null;
		try {
			infiniteLine = new InfiniteLine(line.getInitialPoint(), line.getEndingPoint());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} 
		
		EllipseInfiniteLineIntersector intersector = new EllipseInfiniteLineIntersector();
		Collection<Point> intersections = intersector.getIntersections(infiniteLine, ellipse);
		
		Collection<Point> results = new ArrayList<Point>();
		for(Point p : intersections)
			if(line.contains(p))
				results.add(p);
		
		return results;
	}
}
