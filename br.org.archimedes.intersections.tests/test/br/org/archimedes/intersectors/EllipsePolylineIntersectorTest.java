package br.org.archimedes.intersectors;

import org.junit.Before;

import br.org.archimedes.Tester;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class EllipsePolylineIntersectorTest extends Tester{
	
	Ellipse ellipse;
	EllipsePolylineIntersector intersector;
	
	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		ellipse = new Ellipse(new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(0.0, 1.0));
		intersector = new EllipsePolylineIntersector();
	}
	
	
	
}
