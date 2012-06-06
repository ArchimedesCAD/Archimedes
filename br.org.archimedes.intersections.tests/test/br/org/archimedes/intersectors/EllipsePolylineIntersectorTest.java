package br.org.archimedes.intersectors;

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

}
