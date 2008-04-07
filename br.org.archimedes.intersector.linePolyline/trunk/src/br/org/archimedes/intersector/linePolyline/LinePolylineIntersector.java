package br.org.archimedes.intersector.linePolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class LinePolylineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) {

		Line baseLine = (Line) element;
		Polyline polyline = (Polyline) otherElement;

		List<Line> lines = polyline.getLines();
		Collection<Point> intersectionPoints = new ArrayList<Point>();

		Collection<Point> intersection;
		for (Line line : lines) {
			try {
				intersection = line.getIntersection(baseLine);
				intersectionPoints.addAll(intersection);
			} catch (NullArgumentException e) {
				// TODO: We actually should discuss it and decide if it throws exceptions
				// shouldn't happen, anyway.
				e.printStackTrace();
			}
		}
		return intersectionPoints;
	}

}
