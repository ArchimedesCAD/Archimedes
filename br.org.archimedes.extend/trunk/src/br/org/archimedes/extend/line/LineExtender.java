package br.org.archimedes.extend.line;

import java.util.Collection;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.semiline.Semiline;

public class LineExtender implements Extender {

	public void extend(Element element, Collection<Element> references,
			Point click) throws NullArgumentException {
		IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
				.getIntersectionManager();

		if (element == null || references == null || click == null) {
			throw new NullArgumentException();
		}

		Line line = (Line) element;
		Semiline semiline = null;
		
		Point nearestExtremePoint = getNearestExtremePoint(line, click);
		
		try {
			semiline = new Semiline(click, nearestExtremePoint);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		Collection<Point> intersectionPoints = intersectionManager
				.getIntersectionsBetween(semiline, references);
		
		if(intersectionPoints.size() != 0){
			Point nearestReferencePoint = null;
			double minDistance = Double.MAX_VALUE;
			
			for(Point point : intersectionPoints){
				double distanceToRef = Geometrics.calculateDistance(point,
						nearestExtremePoint);
				if (distanceToRef < minDistance){
					nearestReferencePoint = point;
					minDistance = distanceToRef;
				}
			}
			
			nearestExtremePoint = nearestReferencePoint;
		}
	}

	Point getNearestExtremePoint(Line line, Point point) throws NullArgumentException {

		double distanceToInitial = Geometrics.calculateDistance(point,
				line.getInitialPoint());
		double distanceToEnding = Geometrics.calculateDistance(point,
				line.getEndingPoint());

		Point returnPoint = null;
		if (distanceToEnding < distanceToInitial) {
			returnPoint = line.getEndingPoint();
		} else {
			returnPoint = line.getInitialPoint();
		}
		return returnPoint;
	}
	
	/*private MoveCommand computeExtend(Line line, Point point) {
		MoveCommand stretchCommand = null;
		Point toMove = null;

		if (line != null) {
			try {
				toMove = getNearestExtremePoint(line, point);
			} catch (NullArgumentException e) {
				// Should never happen
				e.printStackTrace();
			}
		}
		if (toMove != null) {
			Point intersectionPoint = getNearestIntersection(line, toMove);
			if (intersectionPoint != null) {
				try {
					
				} catch (NullArgumentException e) {
					// Should never happen
					e.printStackTrace();
				}
			}
		}

		return stretchCommand;
	}

	private Point getNearestIntersection(Element element, Point point) {

		PointSortable sortableElement = null;
		Point nearestIntersection = null;
		try {
			sortableElement = (PointSortable) element;
		} catch (ClassCastException e) {
		}

		if (sortableElement != null) {
			Collection<Point> intersectionPoints = new ArrayList<Point>();
			for (Element reference : references) {
				try {
					Collection<Point> intersection = element
							.getIntersection(reference);
					for (Point intersect : intersection) {
						if (reference.contains(intersect)
								&& !reference.equals(element)) {
							intersectionPoints.add(intersect);
						}
					}
				} catch (NullArgumentException e) {
					// Should not happen
					e.printStackTrace();
				}
			}
			SortedSet<ComparablePoint> sortedPointSet = sortableElement
					.getSortedPointSet(point, intersectionPoints);
			ComparablePoint extendPoint = null;
			try {
				extendPoint = new ComparablePoint(point, new DoubleKey(0.0));
			} catch (NullArgumentException e) {
				// Should never happen
				e.printStackTrace();
			}

			SortedSet<ComparablePoint> negativeIntersections = sortedPointSet
					.headSet(extendPoint);

			if (negativeIntersections.size() > 0) {
				nearestIntersection = negativeIntersections.last().getPoint();
			}
		}
		return nearestIntersection;
	}*/
	 
}
