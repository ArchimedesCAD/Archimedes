package br.org.archimedes.intersector.linepolyline.test;

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
import br.org.archimedes.intersector.linePolyline.LinePolylineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class LinePolylineIntersectorTest extends Tester {

	@Test
	public void testLineIntersectsPolylineReturnsOneIntersectionPoint()
			throws InvalidArgumentException, NullArgumentException {
		Line line = new Line(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		Polyline polyline = new Polyline(list);

		Intersector intersector = new LinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(line,
				polyline);
		Point point = new Point(0.0, 0.0);

		assertCollectionTheSame(Collections.singleton(point), intersections);

	}
	
	@Test
	public void testLineIntersectsPolylineReturnsManyIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {
		
		Line line = new Line(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		list.add(new Point(-1.0, 1.0));
		
		Polyline polyline = new Polyline(list);

		Intersector intersector = new LinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(line,
				polyline);
		
		List<Point> intersectionPoints = new ArrayList<Point>();
		intersectionPoints.add(new Point(0.0, 0.0));
		intersectionPoints.add(new Point(0.0, 1.0));

		assertCollectionTheSame(intersectionPoints, intersections);

	}
	
	@Test
	public void testLineIntersectsPolylineReturnsNoIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {
		
		Line line = new Line(0.0, 1.0, 0.0, -1.0);
		List<Point> list = new ArrayList<Point>();
		
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		
		
		Polyline polyline = new Polyline(list);

		Intersector intersector = new LinePolylineIntersector();

		Collection<Point> intersections = intersector.getIntersections(line,
				polyline);
		
		assertTrue(intersections.isEmpty());

	}
	
	@Test
	public void testLinePolylineIntersectorNullArgument() throws NullArgumentException, InvalidArgumentException{
		
		Line line = new Line(0.0, 1.0, 0.0, -1.0);
		
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(-1.0, 0.0));
		list.add(new Point(1.0, 0.0));
		list.add(new Point(1.0, 1.0));
		list.add(new Point(-1.0, 1.0));
		
		Polyline polyline = new Polyline(list);
		
		Intersector intersector = new LinePolylineIntersector();
		
		try{
			intersector.getIntersections(line,null);
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
