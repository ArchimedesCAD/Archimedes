package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.Semiline;

public class SemilinePolylineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		Semiline baseLine;
		Polyline polyline;
		
		if(element == null || otherElement == null)
			throw new NullArgumentException();
		
		if (element.getClass() == Semiline.class) {
			baseLine = (Semiline) element;
			polyline = (Polyline) otherElement;
		} else {
			baseLine = (Semiline) otherElement;
			polyline = (Polyline) element;
		}
		

		List<Line> lines = polyline.getLines();
		Collection<Point> intersectionPoints = new ArrayList<Point>();

		Collection<Point> intersection;

		SemilineLineIntersector semilineLineIntersector = new SemilineLineIntersector();
		for (Line line : lines) {
			intersection = semilineLineIntersector.getIntersections(line, baseLine);
			intersectionPoints.addAll(intersection);
		}
		return intersectionPoints;
	}
	

	
}