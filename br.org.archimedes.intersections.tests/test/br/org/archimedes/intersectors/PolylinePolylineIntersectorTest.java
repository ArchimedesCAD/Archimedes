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
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class PolylinePolylineIntersectorTest extends Tester {
	Polyline polyline;
	Intersector intersector;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		ArrayList<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(-1.0, 1.0));
		polyPoints.add(new Point(1.0, 1.0));
		polyPoints.add(new Point(-1.0, -1.0));
		polyPoints.add(new Point(1.0, -1.0));
		polyline = new Polyline(polyPoints);
		intersector = new PolylinePolylineIntersector();
	}

	@Test
	public void nullArgumentsShouldRaiseException()
			throws NullArgumentException, InvalidArgumentException {
		try {
			intersector.getIntersections(polyline, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(null, polyline);
			fail("The element is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(null, null);
			fail("Both elements are null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
		Assert.assertTrue("Raised all exceptions", true);
	}

	@Test
	public void polylineNotIntersectingPolylineReturnsNoIntersections()
			throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.5, 1.5));
		polyPoints.add(new Point(2.5, 1.5));
		polyPoints.add(new Point(0.5, -0.5));
		polyPoints.add(new Point(2.5, -0.5));
		Polyline testPoly = new Polyline(polyPoints);

		assertCollectionTheSame(Collections.emptyList(), intersector
				.getIntersections(testPoly, polyline));
	}

	@Test
	public void polylineIntersectingPolylineOnceReturnsOneIntersectionPoint()
			throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 2.0));
		polyPoints.add(new Point(0.0, 0.5));
		Polyline testPoly = new Polyline(polyPoints);

		assertCollectionTheSame(Collections.singleton(new Point(0.0, 1.0)),
				intersector.getIntersections(testPoly, polyline));
	}
	
	@Test
	public void polylineIntersectingPolylineThreeTimesReturnsThreeIntersectionPoints() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 2.0));
		polyPoints.add(new Point(0.0, 0.0));
		polyPoints.add(new Point(1.0, -1.0));
		Polyline testPoly = new Polyline(polyPoints);

		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(0.0, 1.0));
		expected.add(new Point(0.0, 0.0));
		expected.add(new Point(1.0, -1.0));
		
		assertCollectionTheSame(expected, intersector
				.getIntersections(testPoly, polyline));
	}
	
	@Test
	public void polylineContainsPolylineReturnsNoIntersectionPoints() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 0.0));
		polyPoints.add(new Point(-0.5, -0.5));
		polyPoints.add(new Point(0.5, 0.5));
		Polyline testPoly = new Polyline(polyPoints);

		assertCollectionTheSame(Collections.emptyList(), intersector
				.getIntersections(testPoly, polyline));
	}
}
