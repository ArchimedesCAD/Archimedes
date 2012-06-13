package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class EllipsePolylineIntersectorTest extends Tester {

	Ellipse ellipse;
	EllipsePolylineIntersector intersector;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		ellipse = new Ellipse(new Point(0.0, 0.0), new Point(1.0, 0.0),
				new Point(0.0, 1.0));
		intersector = new EllipsePolylineIntersector();
	}

	@Test
	public void shouldNotReturnIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {

		Polyline polyline = new Polyline(new Point(2.0, 0.0), new Point(2.0,
				2.0), new Point(0.0, 2.0));

		assertCollectionTheSame(Collections.emptyList(),
				intersector.getIntersections(ellipse, polyline));

	}
	
	@Test
	public void shouldReturnTwoIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {

		Polyline polyline = new Polyline(new Point(2.0, 0.0), new Point(0.0,
				0.0), new Point(0.0, 2.0));
		
		Collection<Point> results = new ArrayList<Point>();
		results.add(new Point(1.0, 0.0));
		results.add(new Point(0.0, 1.0));

		assertCollectionTheSame(results,
				intersector.getIntersections(ellipse, polyline));

	}
	
	@Test
	public void shouldReturnManyIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {

		Polyline polyline = new Polyline(
				new Point( 0.0, -1.0), 
				new Point( 0.5,  0.0),
				new Point( 0.0,  0.5),
				new Point(-1.0,  0.0),
				new Point( 0.0,  1.0));
		
		Collection<Point> results = new ArrayList<Point>();
		results.add(new Point( 0.0, -1.0));  
		results.add(new Point(-1.0,  0.0));
		results.add(new Point( 0.0,  1.0));

		assertCollectionTheSame(results,
				intersector.getIntersections(ellipse, polyline));

	}

}
