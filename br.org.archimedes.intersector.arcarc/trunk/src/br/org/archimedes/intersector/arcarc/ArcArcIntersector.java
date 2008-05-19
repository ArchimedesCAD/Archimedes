package br.org.archimedes.intersector.arcarc;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Constant;
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
		
		Arc arc = (Arc) element;
		Arc arc2 = (Arc) otherElement;
		Collection<Point> arcIntersections = new ArrayList<Point>();
		
		if(Math.abs(arc.getRadius() - arc2.getRadius()) < Constant.EPSILON && arc.getCenter().equals(arc2.getCenter())){
			if(!arc.contains(arc2) && !arc2.contains(arc)){
				if(arc.getEndingPoint().equals(arc2.getInitialPoint()) || arc.getEndingPoint().equals(arc2.getEndingPoint())){
					if(isExtremePointOrIsOutside(arc.getInitialPoint(), arc2)){
						arcIntersections.add(arc.getEndingPoint());
					}
					else{
						return arcIntersections;
					}
				}
				
				if(arc.getInitialPoint().equals(arc2.getInitialPoint()) || arc.getInitialPoint().equals(arc2.getEndingPoint())){
					if(isExtremePointOrIsOutside(arc.getEndingPoint(), arc2)){
						arcIntersections.add(arc.getInitialPoint());
					}
					else{
						return arcIntersections;
					}
				}
			}
			
			return arcIntersections;
		}
		
		ArcCircleIntersector intersector = new ArcCircleIntersector();
		Circle circle = null;
		
		try {
			circle = new Circle(arc.getCenter(), arc.getRadius());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		Collection<Point> intersections = intersector.getIntersections(circle, arc2);
		
		for(Point p : intersections){
			if(arc.contains(p)){
				arcIntersections.add(p);
			}
		}
		return arcIntersections;
	}

	private boolean isExtremePointOrIsOutside(Point point, Arc arc)
			throws NullArgumentException {
		return (!arc.contains(point) || arc.getInitialPoint().equals(point) || arc
				.getEndingPoint().equals(point));
	}
}
