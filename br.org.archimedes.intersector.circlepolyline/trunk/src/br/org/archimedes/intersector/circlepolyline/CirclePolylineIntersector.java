package br.org.archimedes.intersector.circlepolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersector.circleline.CircleLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class CirclePolylineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Circle circle;
		Polyline polyline;
		
		if (element.getClass() == Circle.class)
		{
			circle = (Circle) element;
			polyline = (Polyline) otherElement;
		}
		else
		{
			circle = (Circle) otherElement;
			polyline = (Polyline) element;
		}
		
		List<Line> lines = polyline.getLines();
		
		Collection<Point> points = new ArrayList<Point>();
		
		CircleLineIntersector intersector = new CircleLineIntersector();
		
		for (Line line : lines)
			points.addAll(intersector.getIntersections(circle, line));
		
		return points;
	}

}
