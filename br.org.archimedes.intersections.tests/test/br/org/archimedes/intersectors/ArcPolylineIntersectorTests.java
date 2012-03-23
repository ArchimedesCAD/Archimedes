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
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import static org.junit.Assert.fail;

public class ArcPolylineIntersectorTests extends Tester {
	Arc testArc;
	Intersector intersector;
	
	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testArc = new Arc(new Point(0.0, 0.0), new Point(2.0, 2.0), new Point(4.0, 0.0));
		intersector = new ArcPolylineIntersector();
	}
	
	@Test
	public void nullArgumentsShouldRaiseException() throws NullArgumentException,
			InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 3.0));
		polyPoints.add(new Point(3.0, 3.0));
		polyPoints.add(new Point(5.0, 0.0));
		Polyline testPoly = new Polyline(polyPoints);
		try {
			intersector.getIntersections(testPoly, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
		
		try {
			intersector.getIntersections(null, testPoly);
			fail("The element is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(testArc, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(null, testArc);
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
	public void polylineNotIntersectingArcReturnsNoIntersections() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 3.0));
		polyPoints.add(new Point(3.0, 3.0));
		polyPoints.add(new Point(5.0, 0.0));
		Polyline testPoly = new Polyline(polyPoints);
		
		assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(testPoly, testArc));
	}
	
	@Test
	public void polylineIntersectsArcOnceReturnsOneIntersectionPoint() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(-1.0, 1.0));
		polyPoints.add(new Point(1.0, -1.0));
		polyPoints.add(new Point(-2.0, -1.0));
		Polyline testPoly = new Polyline(polyPoints);
		
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(0.0, 0.0));
		
		Collection<Point> real = intersector.getIntersections(testPoly, testArc);
		assertCollectionTheSame(expected, real);
	}
	
	@Test
	public void polylineIntersectsArcThreeTimesReturnsThreeIntersectionPoints() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, 0.0));
		polyPoints.add(new Point(2.0, 2.0));
		polyPoints.add(new Point(4.0, 0.0));
		Polyline testPoly = new Polyline(polyPoints);
		
		Collection<Point> expected = polyPoints;
		
		Collection<Point> real = intersector.getIntersections(testPoly, testArc);
		assertCollectionTheSame(expected, real);
	}

}
