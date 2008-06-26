package br.org.archimedes.trim.polyline.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.trim.polyline.PolylineTrimmer;
import br.org.archimedes.trims.interfaces.Trimmer;

public class PolylineTrimTest extends Tester {
	Polyline poly1;
	Trimmer trimmer;

	@Before
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		List<Point> polyPoints = new ArrayList<Point>();
		polyPoints.add(new Point(0.0, -1.0));
		polyPoints.add(new Point(0.0, 1.0));
		polyPoints.add(new Point(1.0, 1.0));
		polyPoints.add(new Point(1.0, 0.0));
		polyPoints.add(new Point(-1.0, 0.0));
		polyPoints.add(new Point(-1.0, -1.0));
		poly1 = new Polyline(polyPoints);
		trimmer = new PolylineTrimmer();
	}

	@Test
	public void lineIntersectsOnceReturnsPolyline()
			throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-0.5, -0.5), new Point(0.5, -0.5));

		Collection<Element> smallerSide = trimmer.trim(poly1, Collections
				.singleton(line), new Point(0.0, 0.0));
		List<Point> expectedPolyPoints = new ArrayList<Point>();
		expectedPolyPoints.add(new Point(0.0, -1.0));
		expectedPolyPoints.add(new Point(0.0, -0.5));
		Polyline expected = new Polyline(expectedPolyPoints);
		assertCollectionTheSame(Collections.singleton(expected), smallerSide);

		Collection<Element> biggerSide = trimmer.trim(poly1, Collections
				.singleton(line), new Point(0.0, -1.0));
		expectedPolyPoints = new ArrayList<Point>();
		expectedPolyPoints.add(new Point(0.0, -0.5));
		expectedPolyPoints.add(new Point(0.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, -1.0));
		expected = new Polyline(expectedPolyPoints);
		assertCollectionTheSame(Collections.singleton(expected), biggerSide);
	}

	@Test
	public void lineIntersectsTwiceReturnsTwoPolylines()
			throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.5, -0.5), new Point(1.5, -0.5));

		Collection<Element> smallerSide = trimmer.trim(poly1, Collections
				.singleton(line), new Point(0.0, 0.0));
		List<Point> expectedPoly1Points = new ArrayList<Point>();
		expectedPoly1Points.add(new Point(0.0, -1.0));
		expectedPoly1Points.add(new Point(0.0, -0.5));
		Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

		List<Point> expectedPoly2Points = new ArrayList<Point>();
		expectedPoly2Points.add(new Point(-1.0, -0.5));
		expectedPoly2Points.add(new Point(-1.0, -1.0));
		Polyline expectedPoly2 = new Polyline(expectedPoly2Points);

		Collection<Element> expected = new ArrayList<Element>();
		expected.add(expectedPoly1);
		expected.add(expectedPoly2);

		assertCollectionTheSame(expected, smallerSide);
	}

	@Test
	public void lineIntersectsTwiceReturnsPolyline()
			throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.5, -0.5), new Point(0.5, -0.5));

		Collection<Element> biggerSide = trimmer.trim(poly1, Collections
				.singleton(line), new Point(0.0, -1.0));
		List<Point> expectedPolyPoints = new ArrayList<Point>();
		expectedPolyPoints.add(new Point(0.0, -0.5));
		expectedPolyPoints.add(new Point(0.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, -1.0));

		Polyline expected = new Polyline(expectedPolyPoints);
		assertCollectionTheSame(Collections.singleton(expected), biggerSide);
	}

	@Test
	public void lineCrossesMiddleOfPolylineReturnsTwoPiecesOfPolylineClickingOver()
			throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.0, 1.0), new Point(1.0, -1.0));

		Collection<Element> sideBelow = trimmer.trim(poly1, Collections
				.singleton(line), new Point(1.0, 1.0));

		List<Point> expectedPoly1Points = new ArrayList<Point>();
		expectedPoly1Points.add(new Point(0.0, -1.0));
		expectedPoly1Points.add(new Point(0.0, -0.5));
		Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

		List<Point> expectedPoly2Points = new ArrayList<Point>();
		expectedPoly2Points.add(new Point(0.0, 0.0));
		expectedPoly2Points.add(new Point(-1.0, 0.0));
		expectedPoly2Points.add(new Point(-1.0, -1.0));
		Polyline expectedPoly2 = new Polyline(expectedPoly2Points);

		Collection<Element> expected = new ArrayList<Element>();
		expected.add(expectedPoly1);
		expected.add(expectedPoly2);

		assertCollectionTheSame(expected, sideBelow);
	}

	@Test
	public void lineCrossesMiddleOfPolylineReturnsHalfOfPolylineClickingUnderTwice()
			throws NullArgumentException, InvalidArgumentException {
		Element line = new Line(new Point(-1.0, 1.0), new Point(1.0, -1.0));

		Collection<Element> sideOver = trimmer.trim(poly1, Collections
				.singleton(line), new Point(0.0, -1.0));
		List<Point> expectedPolyPoints = new ArrayList<Point>();
		expectedPolyPoints.add(new Point(0.0, 0.0));
		expectedPolyPoints.add(new Point(0.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, 0.0));
		expectedPolyPoints.add(new Point(-1.0, -1.0));

		Polyline expected = new Polyline(expectedPolyPoints);
		assertCollectionTheSame(Collections.singleton(expected), sideOver);

		sideOver = trimmer.trim(expected, Collections.singleton(line),
				new Point(-1.0, -1.0));
		expectedPolyPoints = new ArrayList<Point>();
		expectedPolyPoints.add(new Point(0.0, 0.0));
		expectedPolyPoints.add(new Point(0.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 1.0));
		expectedPolyPoints.add(new Point(1.0, 0.0));
		expectedPolyPoints.add(new Point(0.0, 0.0));
		expected = new Polyline(expectedPolyPoints);
		assertCollectionTheSame(Collections.singleton(expected), sideOver);
	}
	
	@Test
	public void twoLinesCuttingFourTimesReturnsTwoPolylinesClickingPolylineBeforeSecondPiece()
			throws NullArgumentException, InvalidArgumentException {
		Element line1 = new Line(new Point(-1.5, -0.5), new Point(0.5, -0.5));
		Element line2 = new Line(new Point(0.5, 1.5), new Point(0.5, -0.5));
		
		Collection<Element> references = new ArrayList<Element>();
		references.add(line1);
		references.add(line2);
		
		Collection<Element> twoPieces = trimmer.trim(poly1, references, new Point(-1.0, 0.0));

		List<Point> expectedPoly1Points = new ArrayList<Point>();
		expectedPoly1Points.add(new Point(0.0, -1.0));
		expectedPoly1Points.add(new Point(0.0, 1.0));
		expectedPoly1Points.add(new Point(1.0, 1.0));
		expectedPoly1Points.add(new Point(1.0, 0.0));
		expectedPoly1Points.add(new Point(0.5, 0.0));
		Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

		List<Point> expectedPoly2Points = new ArrayList<Point>();
		expectedPoly2Points.add(new Point(-1.0, -0.5));
		expectedPoly2Points.add(new Point(-1.0, -1.0));
		Polyline expectedPoly2 = new Polyline(expectedPoly2Points);

		Collection<Element> expected = new ArrayList<Element>();
		expected.add(expectedPoly1);
		expected.add(expectedPoly2);

		assertCollectionTheSame(expected, twoPieces);
	}
}
