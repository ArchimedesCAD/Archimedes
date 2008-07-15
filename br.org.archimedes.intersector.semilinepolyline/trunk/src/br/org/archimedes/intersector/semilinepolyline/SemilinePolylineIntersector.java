package br.org.archimedes.intersector.semilinepolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.semilineline.SemilineLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.SemiLine;

public class SemilinePolylineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		SemiLine baseLine;
		Polyline polyline;
		
		if(element == null || otherElement == null)
			throw new NullArgumentException();
		
		if (element.getClass() == SemiLine.class) {
			baseLine = (SemiLine) element;
			polyline = (Polyline) otherElement;
		} else {
			baseLine = (SemiLine) otherElement;
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