package br.org.archimedes.ellipse.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

public class EllipseTest {

	private Point defaultWidthPoint;
	private Point defaultHeightPoint;
	private Point defaultCenter;
	private Ellipse defaultEllipse;
	
	@Before
	public void setUp() throws Exception{
		this.defaultCenter = new Point(0, 0);
		this.defaultWidthPoint = new Point(0, 10);
		this.defaultHeightPoint = new Point(5, 0);
		this.defaultEllipse = new Ellipse(defaultCenter, defaultWidthPoint, defaultHeightPoint);
	}

	@Test(expected=NullArgumentException.class)
	public void shouldNotCreateEllipseIfCenterIsNotPassed() throws Exception {
		new Ellipse(null, defaultWidthPoint, defaultHeightPoint);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void shouldNotCreateEllipseIfPointsAreTooCloseOfCenter() throws Exception {
		new Ellipse(defaultCenter, defaultCenter, defaultHeightPoint);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void shouldNotCreateEllipseIfColinearPointsArePassed() throws Exception {
		new Ellipse(defaultCenter, new Point(-5,0), new Point(5,0));
	}
	
	@Test
	public void shouldEqualsToAnotherEllipseIfAllPointsAreEquals() throws Exception {
		Ellipse ellipse = new Ellipse(defaultCenter, defaultWidthPoint, defaultHeightPoint);
		
		assertTrue(defaultEllipse.equals(ellipse));
	}
	
	@Test
	public void shouldContainsPoint() throws Exception {
		Point pointInside = new Point(2, 2);
		Point frontierPoint = defaultHeightPoint;
		
		assertTrue(defaultEllipse.contains(pointInside));
		assertTrue(defaultEllipse.contains(frontierPoint));
	}
	
	@Test
	public void shouldPositiveDirectionBeTrueWhenPointIsOutside() throws Exception {
		Point pointOutside = new Point(20, 20);
		
		assertTrue(defaultEllipse.isPositiveDirection(pointOutside));
	}
	
	@Test
	public void shouldContainsCenterAndAllFourIntersectionsWithAxis() throws Exception {
		Collection<Point> refPoints = new ArrayList<Point>();
		
		Rectangle container = new Rectangle(-20, -20, 20, 20);

		Point oppositeHeightPoint = new Point(-5,0);
		Point oppositeWidthPoint = new Point(0,-10);
		Collection<Point> points = Arrays.asList(defaultCenter, defaultHeightPoint, defaultWidthPoint, oppositeHeightPoint, oppositeWidthPoint);
		
		Collection<? extends ReferencePoint> referencePoints = defaultEllipse.getReferencePoints(container);
	
		for (ReferencePoint referencePoint : referencePoints) {
			refPoints.add(referencePoint.getPoint());
		}
		
		assertEquals(5,refPoints.size());
		assertTrue(refPoints.containsAll(points));
	}

	@Test
	public void shouldCalculateOffset() throws Exception {
		Element offsetedEllipse = defaultEllipse.cloneWithDistance(5);
		Ellipse expectedEllipse = new Ellipse(defaultCenter, new Point(0, 15), new Point(-10, 0));
		
		assertEquals(expectedEllipse, offsetedEllipse);
	
		Ellipse ellipse = new Ellipse(defaultCenter, defaultWidthPoint, new Point(-5, 0));
		Element offsetedEllipse2 = ellipse.cloneWithDistance(5);
		
		assertEquals(expectedEllipse, offsetedEllipse2);
	}
}
