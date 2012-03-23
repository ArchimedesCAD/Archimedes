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
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.fail;

public class SemilineArcIntersectorTest extends Tester {
	Arc testArc = null;

	SemilineArcIntersector intersector = null;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		intersector = new SemilineArcIntersector();
		testArc = new Arc(new Point(0.0, 0.0), new Point(20.0, 20.0), new Point(
				40.0, 0.0));
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
	}
	
	@Test
	public void testNoIntersectionsArcAsCircle() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(-1.0, 10.0), new Point(-1.0, -10.0));
		
		assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testNoIntersectionsArcWouldIfCircle() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(-1.0, -1.0), new Point(10.0, -1.0));
		
		assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testIntersectsTwoPoints() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(-1.0, 10.0), new Point(40.0, 10.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0 - Math.sqrt(3.0)*10.0, 10.0));
		expected.add(new Point(Math.sqrt(3.0)*10.0 + 20.0, 10.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testIntersectsTwoPointsWouldIntersectOnceIfLine() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(-1.0, 10.0), new Point(20.0, 10.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0 - Math.sqrt(3.0)*10.0, 10.0));
		expected.add(new Point(Math.sqrt(3.0)*10.0 + 20.0, 10.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testTangentLine() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(0.0, 20.0), new Point(40.0, 20.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0, 20.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testExtremePoints() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(0.0, 0.0), new Point(50.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(0.0, 0.0));
		expected.add(new Point(40.0, 0.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testOneIntersectionTwoIfCircle() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(20.0, 30.0), new Point(20.0, -30.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0, 20.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testNoIntersectionWouldIfInfiniteline() throws NullArgumentException, InvalidArgumentException{
		Semiline testLine = new Semiline(new Point(20.0, 10.0), new Point(20.0, -30.0));
		assertCollectionTheSame(Collections.emptyList(), intersector.getIntersections(testArc, testLine));
	}
}
