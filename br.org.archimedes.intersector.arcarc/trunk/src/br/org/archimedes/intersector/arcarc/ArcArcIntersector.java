package br.org.archimedes.intersector.arcarc;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.arccircle.ArcCircleIntersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcArcIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		ArcCircleIntersector intersector = new ArcCircleIntersector();
		Arc arc = null;
		Arc arc2 = null;
		Circle circle = null;
		
		arc = (Arc) element;
		arc2 = (Arc) otherElement;
					
		try {
			circle = new Circle(arc.getCenter(), arc.getRadius());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		Collection<Point> intersections = intersector.getIntersections(circle, arc2);
		
		Collection<Point> arcIntersections = new ArrayList<Point>();
		
		for(Point p : intersections){
			if(arc.contains(p)){
				arcIntersections.add(p);
			}
		}
		return arcIntersections;
	}
}
