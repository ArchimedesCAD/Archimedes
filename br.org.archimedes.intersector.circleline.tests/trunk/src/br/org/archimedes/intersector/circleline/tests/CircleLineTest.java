package br.org.archimedes.intersector.circleline.tests;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersector.circleline.CircleLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;

public class CircleLineTest extends Tester {

	Intersector intersector;
	Circle testCircle;
	
	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException
	{
		intersector = new CircleLineIntersector();
		testCircle = new Circle(new Point(0.0, 0.0), 5.0);
	}
		
	@Test
	public void testNoIntersections() throws NullArgumentException, InvalidArgumentException
	{
		Line testLine = new Line(new Point(-10.0, 10.0), new Point(10.0, 10.0));
		Collection<Point> intersections = intersector.getIntersections(testCircle, testLine);
		assertCollectionTheSame(new ArrayList<Point>(), intersections);
	}
	@Test
	public void testOneIntersection() throws NullArgumentException, InvalidArgumentException
	{
		Line testLine = new Line(new Point(5.0, -5.0), new Point(5.0, 5.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(testCircle, testLine));
	}
	
	@Test
	public void testTwoIntersections() throws NullArgumentException, InvalidArgumentException
	{
		Line testLine = new Line(new Point(-10.0, 0.0), new Point(10.0, 0.0));
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(-5.0, 0.0));
		expected.add(new Point(5.0, 0.0));
		assertCollectionTheSame(expected, intersector.getIntersections(testCircle, testLine));
	}
}
