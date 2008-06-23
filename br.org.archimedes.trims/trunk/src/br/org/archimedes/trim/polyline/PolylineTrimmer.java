package br.org.archimedes.trim.polyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.PolyLinePointKey;
import br.org.archimedes.model.Vector;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.trims.interfaces.Trimmer;

public class PolylineTrimmer implements Trimmer {
	private IntersectionManager intersectionManager;

	public PolylineTrimmer() {
		intersectionManager = new IntersectionManagerEPLoader()
				.getIntersectionManager();
	}

	public Collection<Element> trim(Element element,
			Collection<Element> references, Point click)
			throws NullArgumentException {
		if (element == null || references == null) {
			throw new NullArgumentException();
		}

		Polyline polyline = (Polyline) element;

		Collection<Element> trimResult = new ArrayList<Element>();
		Collection<Point> intersectionPoints = intersectionManager
				.getIntersectionsBetween(polyline, references);
		Point point = polyline.getPoints().get(0);
		SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(polyline, point,
				intersectionPoints);

		int clickSegment = polyline.getNearestSegment(click);
		Line line = polyline.getLines().get(clickSegment);
		Vector direction = new Vector(line.getInitialPoint(), line
				.getEndingPoint());
		Vector clickVector = new Vector(line.getInitialPoint(), click);
		PolyLinePointKey key = new PolyLinePointKey(clickSegment, direction
				.dotProduct(clickVector));

		ComparablePoint zero = null;
		ComparablePoint clickPoint = null;
		try {
			clickPoint = new ComparablePoint(click, key);
			zero = new ComparablePoint(point, new DoubleKey(0));
		} catch (NullArgumentException e) {
			// Should never reach
			e.printStackTrace();
		}

		sortedPointSet = sortedPointSet.tailSet(zero);
		SortedSet<ComparablePoint> negativeIntersections = sortedPointSet
				.headSet(clickPoint);
		SortedSet<ComparablePoint> positiveIntersections = sortedPointSet
				.tailSet(clickPoint);

		Point firstCut = null;
		Point secondCut = null;
		if (negativeIntersections.size() == 0
				&& positiveIntersections.size() > 0) {
			firstCut = positiveIntersections.first().getPoint();
			secondCut = positiveIntersections.last().getPoint();
		} else if (positiveIntersections.size() == 0
				&& negativeIntersections.size() > 0) {
			firstCut = negativeIntersections.first().getPoint();
			secondCut = negativeIntersections.last().getPoint();
		} else if (negativeIntersections.size() > 0
				&& positiveIntersections.size() > 0) {
			firstCut = positiveIntersections.first().getPoint();
			secondCut = negativeIntersections.last().getPoint();
		}

		Collection<Polyline> polyLines = polyline.split(firstCut, secondCut);
		for (Polyline polyLine : polyLines) {
			boolean clicked = false;
			try {
				clicked = polyLine.contains(click);
			} catch (NullArgumentException e) {
				// Should not happen
				e.printStackTrace();
			}
			if (!clicked) {
				polyLine.setLayer(polyline.getLayer());
				trimResult.add(polyLine);
			}
		}

		if (trimResult.size() == 2) {
			Iterator<Element> iterator = trimResult.iterator();
			Polyline poly1 = (Polyline) iterator.next();
			Polyline poly2 = (Polyline) iterator.next();
			List<Point> poly1Points = poly1.getPoints();
			List<Point> poly2Points = poly2.getPoints();
			Point firstPoly2 = poly2Points.get(0);
			Point lastPoly1 = poly1Points.get(poly1Points.size() - 1);
			if (lastPoly1.equals(firstPoly2)) {
				trimResult.clear();
				List<Point> points = poly1Points;
				points.remove(points.size() - 1);
				Point last = points.get(points.size() - 1);
				try {
					double determinant = Geometrics.calculateDeterminant(last,
							firstPoly2, poly2Points.get(1));
					if (Math.abs(determinant) < Constant.EPSILON) {
						poly2Points.remove(0);
					}
					points.addAll(poly2Points);
					trimResult.add(new Polyline(points));
				} catch (NullArgumentException e) {
					// Should not happen
					e.printStackTrace();
				} catch (InvalidArgumentException e) {
					// Ignores it
				}
			}
		}

		return trimResult;
	}

	public SortedSet<ComparablePoint> getSortedPointSet(Polyline polyline,
			Point referencePoint, Collection<Point> intersectionPoints) {

		SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();
		List<Line> lines = polyline.getLines();

		Point firstPoint = polyline.getPoints().get(0);
		boolean invertOrder = !firstPoint.equals(referencePoint);

		for (Point intersection : intersectionPoints) {
			try {
				int i = getIntersectionIndex(polyline, invertOrder, intersection);

				if (i < lines.size()) {
					ComparablePoint point = generateComparablePoint(polyline,
							intersection, invertOrder, i);
					sortedPointSet.add(point);
				}
			} catch (NullArgumentException e) {
				// Should never happen
				e.printStackTrace();
			}
		}

		return sortedPointSet;
	}

	private int getIntersectionIndex(Polyline polyline, boolean invertOrder, Point intersection)
			throws NullArgumentException {

		List<Line> lines = polyline.getLines();
		int i;
		for (i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			if (line.contains(intersection)) {
				break;
			}
		}
		/*TODO: add this piece of code when Semiline exists*/
		/*if (i >= lines.size()) {
			SemiLine semiLine;
			int extendableSegment = 0;
			if (!invertOrder) {
				semiLine = new SemiLine(points.get(1), points.get(0));
			} else {
				Point beforeLast = points.get(points.size() - 2);
				Point last = points.get(points.size() - 1);
				semiLine = new SemiLine(beforeLast, last);
				extendableSegment = lines.size() - 1;
			}

			if (semiLine.contains(intersection)) {
				i = extendableSegment;
			}
		}*/
		return i;
	}
	
	private ComparablePoint generateComparablePoint (Polyline polyline, Point point,
            boolean invertOrder, int i) throws NullArgumentException {

        List<Line> lines = polyline.getLines();
        Line line = lines.get(i);
        Point initialPoint = line.getInitialPoint();
        Point endingPoint = line.getEndingPoint();
        int segmentNumber = i;
        if (invertOrder) {
            Point temp = initialPoint;
            initialPoint = endingPoint;
            endingPoint = temp;
            segmentNumber = (lines.size() - 1) - segmentNumber;
        }
        Vector direction = new Vector(initialPoint, endingPoint);

        ComparablePoint element = null;
        try {
            Vector pointVector = new Vector(initialPoint, point);
            PolyLinePointKey key = new PolyLinePointKey(segmentNumber,
                    direction.dotProduct(pointVector));
            element = new ComparablePoint(point, key);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return element;
    }
}
