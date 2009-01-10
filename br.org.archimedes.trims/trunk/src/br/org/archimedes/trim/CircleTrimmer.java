package br.org.archimedes.trim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.trims.interfaces.Trimmer;

public class CircleTrimmer implements Trimmer {

	private IntersectionManager intersectionManager;

	public CircleTrimmer() {
		intersectionManager = new IntersectionManagerEPLoader()
		.getIntersectionManager();
	}
	
	public Collection<Element> trim(Element element,
			Collection<Element> references, Point click)
			throws NullArgumentException {
		
		if (element == null || references == null || click == null) {
			throw new NullArgumentException();
		}
		Circle circle = (Circle) element;
				
		Collection<Element> trimResult = new ArrayList<Element>();
        Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(element, references);
        
        if (intersectionPoints.size() <= 1)
        	return Collections.singleton(element);
        
        Point initialPoint = circle.getCenter().clone();
        initialPoint.move(circle.getRadius(), 0);
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(circle,
                initialPoint, intersectionPoints);

        ComparablePoint clickPoint = null;

        try {
            double key = Geometrics.calculateRelativeAngle(circle.getCenter(),
                    initialPoint, click);
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        SortedSet<ComparablePoint> negativeIntersections = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> positiveIntersections = sortedPointSet.tailSet(clickPoint);

        try {
            if (negativeIntersections.size() == 0
                    && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastPositive = positiveIntersections.last().getPoint();
                Element arc = new Arc(firstPositive, lastPositive, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
            else if (positiveIntersections.size() == 0
                    && negativeIntersections.size() > 0) {
                Point lastNegative = negativeIntersections.last().getPoint();
                Point firstNegative = negativeIntersections.first().getPoint();
                Element arc = new Arc(firstNegative, lastNegative, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
            else if (negativeIntersections.size() > 0
                    && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastNegative = negativeIntersections.last().getPoint();
                Element arc = new Arc(firstPositive, lastNegative, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
        }
        catch (NullArgumentException e) {
            // Should not catch this exception
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not catch this exception
            e.printStackTrace();
        }

        return trimResult;

	}

	private SortedSet<ComparablePoint> getSortedPointSet (Circle circle, Point referencePoint,
            Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        for (Point point : intersectionPoints) {
            try {
                double key = Geometrics.calculateRelativeAngle(circle.getCenter(),
                        referencePoint, point);
                ComparablePoint orderedPoint = new ComparablePoint(point,
                        new DoubleKey(key));
                sortedSet.add(orderedPoint);
            }
            catch (NullArgumentException e) {
                // Should not catch this exception
                e.printStackTrace();
            }

        }

        return sortedSet;
    }

}
