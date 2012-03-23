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
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.fail;

public class ArcInfiniteLineIntersectorTest extends Tester{

	Intersector intersector;
	Arc testArc;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		intersector = new ArcInfiniteLineIntersector();
		testArc = new Arc(new Point(-5.0, 0.0), new Point(0.0, 5.0), new Point(5.0, 0.0));
	}

	@Test
	public void testNullElements() throws NullArgumentException,
			InvalidArgumentException {
		InfiniteLine testInfiniteLine = new InfiniteLine(new Point(0.0, 0.0),
				new Point(1.0, 1.0));
		try {
			intersector.getIntersections(testInfiniteLine, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}

		try {
			intersector.getIntersections(null, testInfiniteLine);
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
		Assert.assertTrue("Detected all null arguments tryed", true);
	}
	
	@Test
	public void infinitelineNotIntersectingArcReturnsNoIntersections() throws NullArgumentException, InvalidArgumentException{
		InfiniteLine infiniteline = new InfiniteLine(new Point(-5.0, 10.0), new Point(5.0, 10.0));
		
		Collection<Point> intersections = intersector.getIntersections(testArc, infiniteline);
		assertCollectionTheSame(Collections.emptyList(), intersections);
	}
	
	@Test
	public void infinitelineIntersectsArcTwiceReturnsTwoIntersectionPoints() throws NullArgumentException, InvalidArgumentException {
		InfiniteLine infiniteline = new InfiniteLine(new Point(-4.0, 0.0), new Point(4.0, 0.0));
		
		Collection<Point> intersections = intersector.getIntersections(testArc, infiniteline);
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(-5.0, 0.0));
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersections);
	}
	
	@Test
	public void infinitelineIntersectsArcOnceReturnsOneIntersectionPoint() throws NullArgumentException, InvalidArgumentException {
		InfiniteLine infiniteline = new InfiniteLine(new Point(0.0, 0.0), new Point(0.0, -5.0));
		
		Collection<Point> intersections = intersector.getIntersections(testArc, infiniteline);
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(0.0, 5.0));
		assertCollectionTheSame(expected, intersections);
	}
}
