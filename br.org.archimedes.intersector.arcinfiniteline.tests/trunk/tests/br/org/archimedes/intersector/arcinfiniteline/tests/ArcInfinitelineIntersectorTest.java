package br.org.archimedes.intersector.arcinfiniteline.tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.arcinfiniteline.ArcInfinitelineIntersector;
import br.org.archimedes.model.Point;

public class ArcInfinitelineIntersectorTest extends Tester{

	Intersector intersector;
	Arc testArc;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		intersector = new ArcInfinitelineIntersector();
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
		assertCollectionTheSame(Collections.EMPTY_LIST, intersections);
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
