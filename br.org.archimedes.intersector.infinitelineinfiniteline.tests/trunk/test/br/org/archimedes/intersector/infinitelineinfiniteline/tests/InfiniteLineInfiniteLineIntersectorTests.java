package br.org.archimedes.intersector.infinitelineinfiniteline.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersector.infinitelineinfiniteline.InfiniteLineInfiniteLineIntersector;
import br.org.archimedes.model.Point;

public class InfiniteLineInfiniteLineIntersectorTests extends Tester{
	
	InfiniteLineInfiniteLineIntersector intersector; 
	InfiniteLine infiniteLine;
	
	@Before
	public void setup() throws NullArgumentException, InvalidArgumentException{
		infiniteLine = new InfiniteLine(new Point(0.0,1.0),new Point(1.0,1.0));
		intersector = new InfiniteLineInfiniteLineIntersector();
	}
	
	@Test
	public void concurrentInfinitelineInfiniteLineIntersector() throws Exception {
		InfiniteLine otherInfiniteLine = new InfiniteLine(0.0, 0.0, 1.0, 1.0);
		Point p0 = new Point(1.0, 1.0);
		Collection<Point> intersections = intersector.getIntersections(otherInfiniteLine, infiniteLine);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
		intersections = intersector.getIntersections(infiniteLine, otherInfiniteLine);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}
	
	@Test
	public void paralelsInfiniteLinesIntersection() throws InvalidArgumentException, NullArgumentException {

		InfiniteLine otherInfiniteLine = new InfiniteLine(0.0, 0.0, 1.0, 0.0);
		Collection<Point> intersections = intersector.getIntersections(otherInfiniteLine, infiniteLine);
		assertTrue(intersections.isEmpty());
		intersections = intersector.getIntersections(infiniteLine, otherInfiniteLine);
		assertTrue(intersections.isEmpty());
		
	}
		
	@Test
	public void coincidentLineIntersection() throws InvalidArgumentException, NullArgumentException {
		InfiniteLine otherInfiniteLine = new InfiniteLine(0.0, 1.0, 1.0, 1.0);
		Collection<Point> intersections = intersector.getIntersections(otherInfiniteLine, infiniteLine);
		assertTrue(intersections.isEmpty());
		intersections = intersector.getIntersections(infiniteLine, otherInfiniteLine);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	public void nullLineIntersection() throws InvalidArgumentException{
		InfiniteLine otherInfiniteLine = null;

		try {
			intersector.getIntersections(otherInfiniteLine, infiniteLine);
			fail("First element is null and then method InfiniteLineInfiniteLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
		
		
		try {
			intersector.getIntersections(infiniteLine, otherInfiniteLine);
			fail("Second element is null and then method InfiniteLineInfiniteLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
	}

}
