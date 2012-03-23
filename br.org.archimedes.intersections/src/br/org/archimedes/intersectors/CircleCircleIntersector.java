/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/09/18, 01:32:21, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
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
				
				double h = Math.sqrt(radius*radius - otherDistance*otherDistance);
				Point inTheMiddle = circle1.getCenter().addVector(direction.multiply(otherDistance));
				
				Vector orthogonalVector = direction.getOrthogonalVector();

				Point pa = inTheMiddle.addVector(orthogonalVector.multiply(h));
				Point pb = inTheMiddle.addVector(orthogonalVector.multiply(-h));
				
				if(!pa.equals(pb))
					intersections.add(pa);
				
				intersections.add(pb);
			}
		}

		return intersections;

	}

}
