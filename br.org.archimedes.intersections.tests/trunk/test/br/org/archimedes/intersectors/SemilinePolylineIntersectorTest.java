package br.org.archimedes.intersectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.SemiLine;

public class SemilinePolylineIntersectorTest extends Tester {

	@Test
	public void testSemilineIntersectsPolylineReturnsOneIntersectionPoint()
			throws InvalidArgumentException, NullArgumentException {
		SemiLine semiline = new SemiLine(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		Polyline polyline = new Polyline(list);

		Intersector intersector = new SemilinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(semiline,
				polyline);
		Point point = new Point(0.0, 0.0);

		assertCollectionTheSame(Collections.singleton(point), intersections);

	}
	
	@Test
	public void testSemilineIntersectsPolylineReturnsManyIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {
		
		SemiLine semiline = new SemiLine(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		list.add(new Point(-1.0, 1.0));
		
		Polyline polyline = new Polyline(list);

		Intersector intersector = new SemilinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(semiline,
				polyline);
		
		List<Point> intersectionPoints = new ArrayList<Point>();
		intersectionPoints.add(new Point(0.0, 0.0));
		intersectionPoints.add(new Point(0.0, 1.0));

		assertCollectionTheSame(intersectionPoints, intersections);

	}
	
	@Test
	public void testSemilineIntersectsPolylineReturnsNoIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {
		
		SemiLine semiline = new SemiLine(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		
		
		Polyline polyline = new Polyline(list);

		Intersector intersector = new SemilinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(semiline,
				polyline);
		
		assertTrue(intersections.isEmpty());

	}
	
	@Test
	public void polylineIntersectsSemilineReturnsNoIntersectionPoints() throws InvalidArgumentException, NullArgumentException {
		SemiLine semiline = new SemiLine(-0.5, 0.0,	0.5, 0.0);
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		Polyline polyline = new Polyline(list);

		Intersector intersector = new SemilinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(semiline,
				polyline);

		assertCollectionTheSame(Collections.EMPTY_LIST, intersections);
	}
	
	@Test
	public void testSemilinePolylineIntersectorNullArgument() throws NullArgumentException, InvalidArgumentException{
		
		SemiLine semiline = new SemiLine(0.0, 1.0, 0.0, -1.0);
		
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		list.add(new Point(-1.0, 1.0));
		
		Polyline polyline = new Polyline(list);
		
		Intersector intersector = new SemilinePolylineIntersector();
		
		try{
			intersector.getIntersections(semiline,null);
			fail("Should throw exception because of null polyline argument");			
		} catch (NullArgumentException e){
			// Passed
		}
		
		try{
			intersector.getIntersections(null,polyline);
			fail("Should throw exception because of null line argument");			
		} catch (NullArgumentException e){
			// Passed
		}
		
		try{
			intersector.getIntersections(null,null);
			fail("Should throw exception because of null polyline and line argument");			
		} catch (NullArgumentException e){
			// Passed
		}		
			
	}
}
