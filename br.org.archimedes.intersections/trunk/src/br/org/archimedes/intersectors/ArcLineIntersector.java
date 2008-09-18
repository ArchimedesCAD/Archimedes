package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcLineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		CircleLineIntersector intersector = new CircleLineIntersector();
		Arc arc = null;
		Line line = null;
		Circle circle = null;
		Collection<Point> intersections = null;
		
		if (element.getClass() == Line.class) {
			line = (Line) element;
			arc = (Arc) otherElement;
		} else {
			line = (Line) otherElement;
			arc = (Arc) element;
		}
		
		try {
			circle = new Circle(arc.getCenter(), arc.getRadius());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		intersections = intersector.getIntersections(circle, line);
		
		Collection<Point> arcIntersections = new ArrayList<Point>();
		
		for(Point p : intersections){
			if(arc.contains(p)){
				arcIntersections.add(p);
			}
		}
		
		return arcIntersections;
	}

}
