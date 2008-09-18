package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcCircleIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		Arc baseArc;
		Circle circle;

		if (element == null || otherElement == null)
			throw new NullArgumentException();

		if (element.getClass() == Arc.class) {
			baseArc = (Arc) element;
			circle = (Circle) otherElement;
		} else {
			baseArc = (Arc) otherElement;
			circle = (Circle) element;
		}
		
		Circle fakeCircle = null;
		try {
			fakeCircle = new Circle(baseArc.getCenter(), baseArc.getRadius());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		CircleCircleIntersector cci = new CircleCircleIntersector();
		Collection<Point> intersectionPoints = cci.getIntersections(circle, fakeCircle);
		Collection<Point> arcIntersections = new ArrayList<Point>();
		
		for(Point p : intersectionPoints){
			if(baseArc.contains(p)){
				arcIntersections.add(p);
			}
		}
		
		return arcIntersections;
	}
}
