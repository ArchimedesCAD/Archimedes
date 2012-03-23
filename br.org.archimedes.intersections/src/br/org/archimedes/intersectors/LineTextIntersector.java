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

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

import java.util.ArrayList;
import java.util.Collection;

public class LineTextIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Line line;
		Text text;
		LineLineIntersector intersector = new LineLineIntersector();

		if (element.getClass() == Line.class) {
			line = (Line) element;
			text = (Text) otherElement;
		} else {
			line = (Line) otherElement;
			text = (Text) element;
		}
		Collection<Point> boundaryPoints = text.getBoundaryRectangle().getPoints();
		Point lastPoint = null;
		Collection<Line> boundaryLines = new ArrayList<Line>();
		for (Point boundaryPoint : boundaryPoints) {
			if (lastPoint != null) {
				try {
					boundaryLines.add(new Line(lastPoint, boundaryPoint));
				} catch (InvalidArgumentException e) {
					e.printStackTrace();
				}
			}
			lastPoint = boundaryPoint;
		}
		Collection<Point> intersections = new ArrayList<Point>();
		for (Line l : boundaryLines)
			intersections.addAll(intersector.getIntersections(l, line));
		return intersections;
	}
}
