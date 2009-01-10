package br.org.archimedes.trim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.trims.interfaces.Trimmer;

public class ArcTrimmer implements Trimmer {

	private IntersectionManager intersectionManager;

	public ArcTrimmer() {
		intersectionManager = new IntersectionManagerEPLoader()
		.getIntersectionManager();
	}
	
	public Collection<Element> trim(Element element,
			Collection<Element> references, Point click)
			throws NullArgumentException {
		if (element == null || references == null || click == null) {
			throw new NullArgumentException();
		}
		
		Arc arc = (Arc) element;
		
		Collection<Element> trimResult = new ArrayList<Element>();
        Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(element, references);
        
        if (intersectionPoints.size() == 0)
        	return Collections.singleton(element);
        
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(arc,
                arc.getInitialPoint(), intersectionPoints);

        ComparablePoint clickPoint = null;
        ComparablePoint initial = null;
        try {
            double key = getArcAngle(arc, click);
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
            initial = new ComparablePoint(arc.getInitialPoint(), new DoubleKey(0.0));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        // Consider only point within the arc
        sortedPointSet = sortedPointSet.tailSet(initial);

        SortedSet<ComparablePoint> negativeIntersections = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> positiveIntersections = sortedPointSet.tailSet(clickPoint);

        try {
            if (negativeIntersections.size() == 0
                    && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Collection<Point> points = new ArrayList<Point>();
                points.add(firstPositive);
                points.add(arc.getEndingPoint());
                Element resultArc = new Arc(firstPositive, arc.getEndingPoint(), arc.getCenter(),
                        true);
                resultArc.setLayer(arc.getLayer());

                trimResult.add(resultArc);
            }
            else if (positiveIntersections.size() == 0
                    && negativeIntersections.size() > 0) {
                Point lastNegative = negativeIntersections.last().getPoint();
                Collection<Point> points = new ArrayList<Point>();
                points.add(lastNegative);
                points.add(arc.getInitialPoint());
                Element resultArc = new Arc(arc.getInitialPoint(), lastNegative, arc.getCenter(),
                        true);
                resultArc.setLayer(arc.getLayer());

                trimResult.add(resultArc);
            }
            else if (negativeIntersections.size() > 0
                    && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastNegative = negativeIntersections.last().getPoint();
                Collection<Point> points = new ArrayList<Point>();
                points.add(lastNegative);
                points.add(arc.getInitialPoint());
                Element arc1 = new Arc(arc.getInitialPoint(), lastNegative, arc.getCenter(),
                        true);
                arc1.setLayer(arc.getLayer());

                trimResult.add(arc1);

                points = new ArrayList<Point>();
                points.add(firstPositive);
                points.add(arc.getEndingPoint());
                Element arc2 = new Arc(firstPositive, arc.getEndingPoint(), arc.getCenter(),
                        true);
                arc2.setLayer(arc.getLayer());

                trimResult.add(arc2);
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
	
	public SortedSet<ComparablePoint> getSortedPointSet (Arc arc, Point referencePoint,
            Collection<Point> points) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        boolean invertOrder = referencePoint.equals(arc.getEndingPoint());
        for (Point point : points) {
            try {
                double key = getArcAngle(arc, point);
                if (Math.abs(key) > Constant.EPSILON && invertOrder) {
                    key = 1 / key;
                }
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
	
	private double getArcAngle (Arc arc, Point point) {

        double arcAngle = 0;
        boolean contained = true;
        try {
            contained = arc.contains(point);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        if (contained) {
            arcAngle = Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getInitialPoint(),
                    point);
        }
        else {
            arcAngle = Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getEndingPoint(),
                    point);
            arcAngle -= Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getEndingPoint(),
                    arc.getInitialPoint());
        }
        return arcAngle;
    }

}
