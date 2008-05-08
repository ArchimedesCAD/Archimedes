package br.org.archimedes.intersector.lineinfiniteline;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

public class LineInfiniteLineIntersectorTest extends Tester  {

	@Test
	public void simpleLineInfiniteLineIntersector() throws Exception {
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		Line line = new Line(1, 1, 10, 10);
		InfiniteLine infiniteLine = new InfiniteLine(-1, 3, 10, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line, infiniteLine);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}
	
	@Test
	public void paralelsLinesIntersection() throws InvalidArgumentException, NullArgumentException {
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		Line line = new Line(1, 1, 10, 10);
		InfiniteLine infiniteLine = new InfiniteLine(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line, infiniteLine);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	public void subLineIntersection() throws InvalidArgumentException, NullArgumentException {
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		Line line = new Line(2, 2, 12, 12);
		InfiniteLine infiniteLine = new InfiniteLine(3, 3, 10, 10);
		Collection<Point> intersections = lli.getIntersections(line, infiniteLine);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	/*Would intersect if one line was extended*/
	public void noLineIntersectionWouldIfOneExtended() throws InvalidArgumentException, NullArgumentException {
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		Line line = new Line(4, 3, 3, -10);
		InfiniteLine infiniteline = new InfiniteLine(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line, infiniteline);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	/* End of one line intersects middle of the other */
	public void onePointOrthogonalLineIntersection() throws InvalidArgumentException, NullArgumentException {
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		InfiniteLine infiniteLine = new InfiniteLine(2, 2, 10, 10);
		Line line = new Line(-4, 10, 3, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line, infiniteLine);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}
	
	@Test
	public void nullLineIntersection() throws InvalidArgumentException{
		LineInfiniteLineIntersector lli = new LineInfiniteLineIntersector();
		Line line = new Line(2, 2, 10, 10);
		InfiniteLine infiniteLine = null;

		try {
			lli.getIntersections(line, infiniteLine);
			fail("otherElement is null and then method LineLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
		
		line = null;
		infiniteLine = new InfiniteLine(2, 2, 10, 10);
		
		try {
			lli.getIntersections(line, infiniteLine);
			fail("element is null and then method LineLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
	}
}
