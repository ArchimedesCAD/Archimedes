package br.org.archimedes.trimmers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

public class EllipseTrimmer implements Trimmer {

	public EllipseTrimmer() {

	}

	public Collection<Element> trim(Element element,
			Collection<Point> cutPoints, Point click)
			throws NullArgumentException {

		if (element == null || cutPoints == null || click == null) {
			throw new NullArgumentException();
		}
		Ellipse ellipse = (Ellipse) element;

		Collection<Element> trimResult = new ArrayList<Element>();

		if (cutPoints.size() <= 1)
			return Collections.singleton(element);
		// TODO: Create a method to ordenate a list of points relatively to the ellipse.
		SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(ellipse,
				ellipse.getHeightPoint(), cutPoints);

		return trimResult;

	}

	private SortedSet<ComparablePoint> getSortedPointSet(Ellipse circle,
			Point referencePoint, Collection<Point> cutPoints) {

		SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

		for (Point point : cutPoints) {
			try {
				double key = Geometrics.calculateRelativeAngle(
						circle.getCenter(), referencePoint, point);
				ComparablePoint orderedPoint = new ComparablePoint(point,
						new DoubleKey(key));
				sortedSet.add(orderedPoint);
			} catch (NullArgumentException e) {
				// Should not catch this exception
				e.printStackTrace();
			}

		}

		return sortedSet;
	}

}
