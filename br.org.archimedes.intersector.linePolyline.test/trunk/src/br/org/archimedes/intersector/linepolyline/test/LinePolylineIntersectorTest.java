package br.org.archimedes.intersector.linepolyline.test;

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
}
