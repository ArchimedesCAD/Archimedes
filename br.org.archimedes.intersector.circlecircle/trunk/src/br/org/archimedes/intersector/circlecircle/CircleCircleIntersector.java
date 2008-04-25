package br.org.archimedes.intersector.circlecircle;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersector.circleline.CircleLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class CircleCircleIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {

		if (element == null || otherElement == null)
			throw new NullArgumentException();

		Circle circle1 = (Circle) element;
		Circle circle2 = (Circle) otherElement;

		Collection<Point> intersections = new ArrayList<Point>();

		if (circle1.getRadius() != circle2.getRadius()
				|| !circle1.getCenter().equals(circle2.getCenter())) {
			double radius = circle1.getRadius();
			double otherRadius = circle2.getRadius();
			double distance = Geometrics.calculateDistance(circle1.getCenter(),
					circle2.getCenter());

			if ((distance <= radius + otherRadius)
					&& (distance >= Math.abs(radius - otherRadius))) {
				Vector direction = new Vector(circle1.getCenter(), circle2
						.getCenter());
				direction = Geometrics.normalize(direction);

				double otherDistance = (radius * radius)
						- (otherRadius * otherRadius) + (distance * distance);
				otherDistance /= 2 * distance;

				direction = direction.multiply(otherDistance);
				Point inTheMiddle = circle1.getCenter().addVector(direction);

				Vector orthogonalVector = direction.getOrthogonalVector();
				
				Line line = null;
				try {
					line = new Line(inTheMiddle.addVector(orthogonalVector
							.multiply(radius)), inTheMiddle
							.addVector(orthogonalVector.multiply(-radius)));
				} catch (InvalidArgumentException e) {
					e.printStackTrace();
				}
				
				Intersector intersector = new CircleLineIntersector();
				
				intersections.addAll(intersector.getIntersections(circle1, line));
			}
		}

		return intersections;

	}

}
