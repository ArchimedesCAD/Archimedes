package br.org.archimedes.intersector.linePolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Intersector;
import br.org.archimedes.intersector.lineline.LineLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class LinePolylineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		Line baseLine;
		Polyline polyline;
		
		if(element == null || otherElement == null)
			throw new NullArgumentException();
		
		if (element.getClass() == Line.class) {
			baseLine = (Line) element;
			polyline = (Polyline) otherElement;
		} else {
			baseLine = (Line) otherElement;
			polyline = (Polyline) element;
		}
		

		List<Line> lines = polyline.getLines();
		Collection<Point> intersectionPoints = new ArrayList<Point>();

		Collection<Point> intersection;

		LineLineIntersector lineLineIntersector = new LineLineIntersector();
		for (Line line : lines) {
			intersection = lineLineIntersector.getIntersections(line, baseLine);
			intersectionPoints.addAll(intersection);
		}
		return intersectionPoints;
	}
	

	
}
