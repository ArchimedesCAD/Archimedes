package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Point;

public class EllipseInfiniteLineIntersectorTest extends Tester {

	private Ellipse ellipse1x1, ellipse2x2, ellipse2x1;
	private EllipseInfiniteLineIntersector intersector;

	@Before
	public void SetUp() throws NullArgumentException, InvalidArgumentException {
		ellipse1x1 = new Ellipse(new Point(0, 0), new Point(1, 0), new Point(0,
				1));
		ellipse2x2 = new Ellipse(new Point(0, 0), new Point(2, 0), new Point(0,
				2));
		intersector = new EllipseInfiniteLineIntersector();
	}

	@Test
	public void shouldNotReturnIntersectionPoints()
			throws InvalidArgumentException, NullArgumentException {
		InfiniteLine verticalLine = new InfiniteLine(3.0, 0.0, 3.0, 1.0);
		assertCollectionTheSame(new ArrayList<Point>(),
				intersector.getIntersections(ellipse1x1, verticalLine));
		assertCollectionTheSame(new ArrayList<Point>(),
				intersector.getIntersections(ellipse2x2, verticalLine));
	}

	@Test
	public void shouldReturnOneTangencyPoint1x1()
			throws InvalidArgumentException, NullArgumentException {
		InfiniteLine verticalLine = new InfiniteLine(1.0, 0.0, 1.0, 1.0);

		ArrayList<Point> interPoints = new ArrayList<Point>();
		interPoints.add(new Point(1.0, 0.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse1x1, verticalLine));

		InfiniteLine horizontalLine = new InfiniteLine(0.0, 1.0, 1.0, 1.0);

		interPoints = new ArrayList<Point>();
		interPoints.add(new Point(0.0, 1.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse1x1, horizontalLine));
	}

	@Test
	public void shouldReturnOneTangencyPoint2x2()
			throws InvalidArgumentException, NullArgumentException {
		InfiniteLine verticalLine = new InfiniteLine(2.0, 0.0, 2.0, 1.0);

		ArrayList<Point> interPoints = new ArrayList<Point>();
		interPoints.add(new Point(2.0, 0.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse2x2, verticalLine));

		InfiniteLine horizontalLine = new InfiniteLine(0.0, 2.0, 1.0, 2.0);

		interPoints = new ArrayList<Point>();
		interPoints.add(new Point(0.0, 2.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse2x2, horizontalLine));
	}

	@Test
	public void shouldReturnOneDiagonalTangencyPoint1x1()
			throws InvalidArgumentException, NullArgumentException {
		double sqrt2 = Math.sqrt(2);
		InfiniteLine diagonalLine = new InfiniteLine(sqrt2, 0.0, 0.0, sqrt2);

		ArrayList<Point> interPoints = new ArrayList<Point>();
		interPoints = new ArrayList<Point>();
		interPoints.add(new Point(sqrt2 / 2, sqrt2 / 2));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse1x1, diagonalLine));
	}

	@Test
	public void shouldReturnOneDiagonalTangencyPoint2x2()
			throws InvalidArgumentException, NullArgumentException {
		double sqrt2 = Math.sqrt(2);
		InfiniteLine diagonalLine = new InfiniteLine(2 * sqrt2, 0.0, 0.0,
				2 * sqrt2);

		ArrayList<Point> interPoints = new ArrayList<Point>();
		interPoints = new ArrayList<Point>();
		interPoints.add(new Point(sqrt2, sqrt2));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse2x2, diagonalLine));
	}

	@Test
	public void shouldReturnTwoTangecyPoint() throws InvalidArgumentException,
			NullArgumentException {
		InfiniteLine verticalLine = new InfiniteLine(0.0, 0.0, 0.0, 1.0);

		ArrayList<Point> interPoints = new ArrayList<Point>();
		interPoints.add(new Point(0.0, -1.0));
		interPoints.add(new Point(0.0, 1.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse1x1, verticalLine));

		InfiniteLine horizontalLine = new InfiniteLine(0.0, 0.0, 1.0, 0.0);

		interPoints = new ArrayList<Point>();
		interPoints.add(new Point(-1.0, 0.0));
		interPoints.add(new Point(1.0, 0.0));

		assertCollectionTheSame(interPoints,
				intersector.getIntersections(ellipse1x1, horizontalLine));
	}

}
