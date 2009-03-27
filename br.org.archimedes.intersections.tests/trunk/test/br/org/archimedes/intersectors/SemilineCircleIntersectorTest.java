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

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

public class SemilineCircleIntersectorTest extends Tester {

	Intersector intersector;
	Circle testCircle;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		intersector = new SemilineCircleIntersector();
		testCircle = new Circle(new Point(0.0, 0.0), 5.0);
	}

	@Test
	public void testNullElements() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(0.0, 0.0), new Point(1.0, 1.0));
		try {
			intersector.getIntersections(testLine, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
		
		try {
			intersector.getIntersections(null, testLine);
			fail("The element is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(testCircle, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(null, testCircle);
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
	}

	@Test
	public void testNoIntersections() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(-10.0, 10.0), new Point(10.0, 10.0));
		Collection<Point> intersections = intersector.getIntersections(
				testCircle, testLine);
		assertCollectionTheSame(new ArrayList<Point>(), intersections);
	}

	@Test
	public void testTangentLine() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(5.0, -5.0), new Point(5.0, 5.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, testLine));
	}

	@Test
	public void testTwoIntersections() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(-10.0, 0.0), new Point(0.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(-5.0, 0.0));
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, testLine));
	}
	
	@Test
	public void testPartiallyInside() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(0.0, 0.0), new Point(10.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, testLine));
	}
	
	@Test
	public void testOnePointAtCircle() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(5.0, 0.0), new Point(10.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, testLine));
	}
	
	@Test
	public void testWouldntIntersectCircleIfLine() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(-10.0, 0.0), new Point(-7.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(-5.0, 0.0));
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(
				testCircle, testLine));
	}
	
	@Test
	public void testNoIntersectionWouldIfInfiniteLine() throws NullArgumentException,
			InvalidArgumentException {
		Semiline testLine = new Semiline(new Point(-7.0, 0.0), new Point(-10.0, 0.0));
		Collection<Point> intersections = intersector.getIntersections(
				testCircle, testLine);
		assertCollectionTheSame(new ArrayList<Point>(), intersections);
	}
}
