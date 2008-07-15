package br.org.archimedes.intersector.semilinecircle;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.semiline.SemiLine;

public class SemilineCircleIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {

		if (element == null || otherElement == null)
			throw new NullArgumentException();

		Collection<Point> intersections = new ArrayList<Point>();

		SemiLine semiline;
		Circle circle;

		if (element.getClass() == SemiLine.class) {
			semiline = (SemiLine) element;
			circle = (Circle) otherElement;
		} else {
			semiline = (SemiLine) otherElement;
			circle = (Circle) element;
		}
		
		Point projection = null;
		double distance = 0.0;
		
		try {
			projection = semiline.getProjectionOf(circle.getCenter());
			distance = Geometrics.calculateDistance(circle.getCenter(),
					projection);
		} catch (NullArgumentException e) {
			e.printStackTrace();
		}

		if ((distance - circle.getRadius()) <= Constant.EPSILON) {

			Vector lineVector = new Vector(semiline.getInitialPoint(), semiline
					.getDirectionPoint());
			lineVector = Geometrics.normalize(lineVector);

			double semiCord = Math.sqrt(circle.getRadius() * circle.getRadius()
					- distance * distance);

			lineVector = lineVector.multiply(semiCord);
			Point intersection1 = projection.addVector(lineVector);
			lineVector = lineVector.multiply(-1);
			Point intersection2 = projection.addVector(lineVector);

			if (semiline.contains(intersection1))
				intersections.add(intersection1);
			if (!intersection2.equals(intersection1) && semiline.contains(intersection2)) {
				intersections.add(intersection2);
			}
		}
		return intersections;
	}

}
