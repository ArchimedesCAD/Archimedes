package br.org.archimedes.intersectors;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

public class ArcLineIntersectorTest extends Tester {
	Arc testArc = null;

	Intersector intersector = null;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		intersector = new ArcLineIntersector();
		testArc = new Arc(new Point(0.0, 0.0), new Point(20.0, 20.0), new Point(
				40.0, 0.0));
	}

	@Test
	public void testNullElements() throws NullArgumentException,
			InvalidArgumentException {
		Line testLine = new Line(new Point(0.0, 0.0), new Point(1.0, 1.0));
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
		Line testLine = new Line(new Point(-1.0, 10.0), new Point(-1.0, -10.0));
		
		assertCollectionTheSame(Collections.EMPTY_LIST, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testNoIntersectionsArcWouldIfCircle() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(-1.0, -1.0), new Point(10.0, -1.0));
		
		assertCollectionTheSame(Collections.EMPTY_LIST, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testIntersectsTwoPoints() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(-1.0, 10.0), new Point(40.0, 10.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0 - Math.sqrt(3.0)*10.0, 10.0));
		expected.add(new Point(Math.sqrt(3.0)*10.0 + 20.0, 10.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testIntersectsLineInside() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(-1.0, 10.0), new Point(20.0, 10.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0 - Math.sqrt(3.0)*10.0, 10.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testTangentLine() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(0.0, 20.0), new Point(40.0, 20.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0, 20.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testExtremePoints() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(0.0, 0.0), new Point(50.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(0.0, 0.0));
		expected.add(new Point(40.0, 0.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
	
	@Test
	public void testOneIntersectionTwoIfCircle() throws NullArgumentException, InvalidArgumentException{
		Line testLine = new Line(new Point(20.0, 30.0), new Point(20.0, -30.0));
		Collection<Point> expected = new ArrayList<Point>();
		
		expected.add(new Point(20.0, 20.0));
		
		assertCollectionTheSame(expected, intersector.getIntersections(testArc, testLine));
	}
}
