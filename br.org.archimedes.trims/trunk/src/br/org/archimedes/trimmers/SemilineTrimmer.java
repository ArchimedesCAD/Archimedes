/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Ricardo Sider, Kenzo Yamada - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */

package br.org.archimedes.trimmers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

public class SemilineTrimmer implements Trimmer {

	public SemilineTrimmer() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.org.archimedes.trims.interfaces.Trimmer#trim(br.org.archimedes.model
	 * .Element, java.util.Collection, br.org.archimedes.model.Point)
	 */
	public Collection<Element> trim(Element element,
			Collection<Point> cutPoints, Point click)
			throws NullArgumentException {

		if (element == null || cutPoints == null) {
			throw new NullArgumentException();
		}

		Semiline line = (Semiline) element;
		Collection<Element> trimResult = new ArrayList<Element>();

		SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(line,
				line.getInitialPoint(), cutPoints);

		Vector direction = new Vector(line.getInitialPoint(), line
				.getDirectionPoint());

		Vector clickVector = new Vector(line.getInitialPoint(), click);
		double key = direction.dotProduct(clickVector);
		ComparablePoint clickPoint = null;
		try {
			clickPoint = new ComparablePoint(click, new DoubleKey(key));
		} catch (NullArgumentException e) {
			// Should never reach
			e.printStackTrace();
		}

		SortedSet<ComparablePoint> headSet = sortedPointSet.headSet(clickPoint);
		SortedSet<ComparablePoint> tailSet = sortedPointSet.tailSet(clickPoint);

		try {

			Vector dir = new Vector(new Point(line.getDirectionPoint().getX()
					- line.getInitialPoint().getX(), line.getDirectionPoint()
					.getY()
					- line.getInitialPoint().getY()));

			if (tailSet.size() == 0 && headSet.size() > 0) {
				Point initialPoint = headSet.last().getPoint();
				Element trimmedLine = new Line(line.getInitialPoint(),
						initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

			} else if (headSet.size() == 0 && tailSet.size() > 0) {
				Point initialPoint = tailSet.first().getPoint();
				Point directionPoint = new Point(initialPoint.getX()
						+ dir.getX(), initialPoint.getY() + dir.getY());
				Element trimmedLine = new Semiline(initialPoint, directionPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

			} else if (headSet.size() > 0 && tailSet.size() > 0) {

				Point initialPoint = tailSet.first().getPoint();
				Point directionPoint = new Point(initialPoint.getX()
						+ dir.getX(), initialPoint.getY() + dir.getY());

				Element trimmedLine = new Semiline(initialPoint, directionPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

				initialPoint = headSet.last().getPoint();
				trimmedLine = new Line(line.getInitialPoint(), initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);
			}
		} catch (Exception e) {
			// Should not catch any exception
			e.printStackTrace();
		}

		return trimResult;
	}

	public SortedSet<ComparablePoint> getSortedPointSet(Semiline line,
			Point referencePoint, Collection<Point> intersectionPoints) {

		SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();

		Point otherPoint = line.getInitialPoint();
		if (referencePoint.equals(line.getInitialPoint())) {
			otherPoint = line.getDirectionPoint();
		}

		Vector direction = new Vector(referencePoint, otherPoint);
		for (Point point : intersectionPoints) {
			Vector pointVector = new Vector(referencePoint, point);
			double key = direction.dotProduct(pointVector);
			ComparablePoint element;
			try {
				element = new ComparablePoint(point, new DoubleKey(key));
				sortedPointSet.add(element);
			} catch (NullArgumentException e) {
				// Should never reach
				e.printStackTrace();
			}
		}

		return sortedPointSet;
	}

}
