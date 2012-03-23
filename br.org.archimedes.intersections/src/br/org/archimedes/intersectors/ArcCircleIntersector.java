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

import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcCircleIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		Arc baseArc;
		Circle circle;

		if (element == null || otherElement == null)
			throw new NullArgumentException();

		if (element.getClass() == Arc.class) {
			baseArc = (Arc) element;
			circle = (Circle) otherElement;
		} else {
			baseArc = (Arc) otherElement;
			circle = (Circle) element;
		}
		
		Circle fakeCircle = null;
		try {
			fakeCircle = new Circle(baseArc.getCenter(), baseArc.getRadius());
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		CircleCircleIntersector cci = new CircleCircleIntersector();
		Collection<Point> intersectionPoints = cci.getIntersections(circle, fakeCircle);
		Collection<Point> arcIntersections = new ArrayList<Point>();
		
		for(Point p : intersectionPoints){
			if(baseArc.contains(p)){
				arcIntersections.add(p);
			}
		}
		
		return arcIntersections;
	}
}
