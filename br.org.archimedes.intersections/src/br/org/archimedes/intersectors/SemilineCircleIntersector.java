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

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.semiline.Semiline;

public class SemilineCircleIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {

		if (element == null || otherElement == null)
			throw new NullArgumentException();

		Collection<Point> intersections = new ArrayList<Point>();

		Semiline semiline;
		Circle circle;

		if (element.getClass() == Semiline.class) {
			semiline = (Semiline) element;
			circle = (Circle) otherElement;
		} else {
			semiline = (Semiline) otherElement;
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
