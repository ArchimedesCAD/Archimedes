/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Ricardo Sider, Kenzo Yamada, Bruno Klava, Wesley Seidel - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */
package br.org.archimedes.trimmers;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class InfiniteLineTrimmer implements Trimmer {

	public InfiniteLineTrimmer() {

	}

	public Collection<Element> trim(Element element,
			Collection<Point> cutPoints, Point click)
			throws NullArgumentException {

		if (element == null || cutPoints == null) {
			throw new NullArgumentException();
		}

		InfiniteLine line = (InfiniteLine) element;
		Collection<Element> trimResult = new ArrayList<Element>();

		SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(line,
				line.getInitialPoint(), cutPoints);

		Vector direction = new Vector(line.getInitialPoint(), line
				.getEndingPoint());

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

			if (tailSet.size() > 0
					&& tailSet.first().getPoint().equals(clickPoint.getPoint())) {

				Point initialPoint = tailSet.first().getPoint();

				if (line.getInitialPoint().compareTo(line.getEndingPoint()) < 0) {
					direction = new Vector(line.getInitialPoint(), line
							.getEndingPoint());
				} else {
					direction = new Vector(line.getEndingPoint(), line
							.getInitialPoint());
				}

				clickPoint.getPoint().setX(
						clickPoint.getPoint().getX() + direction.getX());
				clickPoint.getPoint().setY(
						clickPoint.getPoint().getY() + direction.getY());

				Element trimmedLine = generateSemiline(clickPoint, initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

			} else if (headSet.size() == 0 && tailSet.size() > 0) {
				Point initialPoint = tailSet.first().getPoint();
				Element trimmedLine = generateSemiline(clickPoint, initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

			} else if (tailSet.size() == 0 && headSet.size() > 0) {
				Point initialPoint = headSet.last().getPoint();
				Element trimmedLine = generateSemiline(clickPoint, initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

			} else if (headSet.size() > 0 && tailSet.size() > 0) {

				Point initialPoint = headSet.last().getPoint();
				Element trimmedLine = generateSemiline(clickPoint, initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);

				initialPoint = tailSet.first().getPoint();
				trimmedLine = generateSemiline(clickPoint, initialPoint);
				trimmedLine.setLayer(line.getLayer());
				trimResult.add(trimmedLine);
			}
		} catch (Exception e) {
			// Should not catch any exception
			e.printStackTrace();
		}

		return trimResult;
	}

	private Element generateSemiline(ComparablePoint clickPoint,
			Point initialPoint) throws NullArgumentException,
			InvalidArgumentException {

		Vector director = new Vector(clickPoint.getPoint(), initialPoint);
		Point endPoint = new Point(initialPoint.getX() + director.getX(),
				initialPoint.getY() + director.getY());

		return new Semiline(initialPoint, endPoint);

	}

	public SortedSet<ComparablePoint> getSortedPointSet(InfiniteLine line,
			Point referencePoint, Collection<Point> intersectionPoints) {

		SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();

		Point otherPoint = line.getInitialPoint();
		if (referencePoint.equals(line.getInitialPoint())) {
			otherPoint = line.getEndingPoint();
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
