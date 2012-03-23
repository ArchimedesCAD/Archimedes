/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/09/18, 01:36:41, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections.tests project.<br>
 */
package br.org.archimedes.intersectors;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CirclePolylineIntersectorTest extends Tester {

	Circle testCircle;
	CirclePolylineIntersector intersector;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testCircle = new Circle(new Point(0.0, 0.0), 5.0);
		intersector = new CirclePolylineIntersector();
	}

	@Test
	public void testJointIntersects() throws NullArgumentException,
			InvalidArgumentException {
		List<Point> linePoints = new ArrayList<Point>();
		linePoints.add(new Point(0.0, 10.0));
		linePoints.add(new Point(0.0, 5.0));
		linePoints.add(new Point(5.0, 5.0));

		Polyline polyline = new Polyline(linePoints);

		assertCollectionTheSame(Collections.singleton(new Point(0.0, 5.0)),
				intersector.getIntersections(testCircle, polyline));
	}

	@Test
	public void testPolylineContainedInCircle() throws NullArgumentException,
			InvalidArgumentException {
		List<Point> linePoints = new ArrayList<Point>();
		linePoints.add(new Point(0.0, 0.0));
		linePoints.add(new Point(0.0, 1.0));
		linePoints.add(new Point(1.0, 1.0));

		Polyline polyline = new Polyline(linePoints);

		assertCollectionTheSame(Collections.emptyList(), intersector
				.getIntersections(testCircle, polyline));
	}

	@Test
	public void testPolylineIntersectsCircle() throws NullArgumentException,
			InvalidArgumentException {
		List<Point> linePoints = new ArrayList<Point>();
		linePoints.add(new Point(0.0, -10.0));
		linePoints.add(new Point(0.0, 10.0));
		linePoints.add(new Point(-20.0, 0.0));
		linePoints.add(new Point(20.0, 0.0));

		Polyline polyline = new Polyline(linePoints);

		List<Point> expected = new ArrayList<Point>();
		expected.add(new Point(0.0, -5.0));
		expected.add(new Point(0.0, 5.0));
		expected.add(new Point(-5.0, 0.0));
		expected.add(new Point(5.0, 0.0));

		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, polyline));
	}

}
