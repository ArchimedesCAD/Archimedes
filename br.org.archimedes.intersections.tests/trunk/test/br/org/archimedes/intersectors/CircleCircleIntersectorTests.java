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
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class CircleCircleIntersectorTests extends Tester {
	Circle testCircle;
	CircleCircleIntersector intersector;
	
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testCircle = new Circle(new Point(0.0, 0.0), 1.0);
		intersector = new CircleCircleIntersector();
	}
	
	@Test
	public void testNullArguments() throws NullArgumentException, InvalidArgumentException {
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
	public void noIntersections() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(3.0, 0.0), 1.0);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void tangentOutside() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(2.0, 0.0), 1.0);
		assertCollectionTheSame(Collections.singleton(new Point(1.0, 0.0)), intersector.getIntersections(testCircle, anotherCircle));
	}
	
	@Test
	public void tangentInside() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.5, 0.0), 0.5);
		assertCollectionTheSame(Collections.singleton(new Point(1.0, 0.0)), intersector.getIntersections(testCircle, anotherCircle));
	}
	
	@Test
	public void oneInsideOtherWithoutIntersection() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.0, 0.0), 0.5);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void theSameCircle() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.0, 0.0), 1.0);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void twoIntersections() throws NullArgumentException, InvalidArgumentException {
		Circle circle = new Circle(new Point(0.5, Math.sqrt(Math.pow(1, 2)-Math.pow(0.5, 2))), 1.0);
		Circle anotherCircle = new Circle(new Point(0.5, -(Math.sqrt(Math.pow(1, 2)-Math.pow(0.5, 2)))), 1.0);
		
		Collection<Point> intersectionPoints = new ArrayList<Point>();
		intersectionPoints.add(new Point(0.0, 0.0));
		intersectionPoints.add(new Point(1.0, 0.0));
		assertCollectionTheSame(intersectionPoints, intersector.getIntersections(circle, anotherCircle));
	}
}
