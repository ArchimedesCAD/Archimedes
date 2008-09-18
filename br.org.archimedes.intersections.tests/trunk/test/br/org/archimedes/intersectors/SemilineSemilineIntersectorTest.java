package br.org.archimedes.intersectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.SemiLine;

public class SemilineSemilineIntersectorTest extends Tester {

	@Test
	public void simpleSemiLineIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(1, 1, 10, 10);
		SemiLine line2 = new SemiLine(-1, 3, 10, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}

	@Test
	public void paralelsSemiLinesIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(1, 1, 10, 10);
		SemiLine line2 = new SemiLine(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	public void sameSemiLineIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 12, 12);
		SemiLine line2 = new SemiLine(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	public void subSemiLineIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 12, 12);
		SemiLine line2 = new SemiLine(10, 10, 3, 3);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	/*Would intersect if one line was extended*/
	public void noSemiLineIntersectionWouldIfOneExtended() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 12, 12);
		SemiLine line2 = new SemiLine(4, 3, 3, -10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	/*Would intersect if the two lines were extended*/
	public void noSemiLineIntersectionWouldIfTwoExtended() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 12, 12);
		SemiLine line2 = new SemiLine(0,0 , 3, -10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}
	
	@Test
	public void onePointParallelLineIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 3, 3);
		SemiLine line2 = new SemiLine(2, 2, 0, 0);
		Point p0 = new Point(2, 2);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}
	
	@Test
	/* End of one line intersects middle of the other */
	public void onePointOrthogonalLineIntersection() throws InvalidArgumentException, NullArgumentException {
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 10, 10);
		SemiLine line2 = new SemiLine(-4, 10, 3, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}
	
	@Test
	public void nullSemiLineIntersection() throws InvalidArgumentException{
		SemilineSemilineIntersector lli = new SemilineSemilineIntersector();
		SemiLine line1 = new SemiLine(2, 2, 10, 10);
		SemiLine line2 = null;

		try {
			lli.getIntersections(line1, line2);
			fail("otherElement is null and then method SemilineSemilineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
		
		try {
			lli.getIntersections(line2, line1);
			fail("element is null and then method SemilineSemilineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			//OK!!!
		}
	}
}
