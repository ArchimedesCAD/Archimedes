package br.org.archimedes.ellipse.tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;

public class EllipseTest {

	private Point widthPoint1;
	private Point heightPoint1;
	private Point center1;
	private Ellipse ellipse1;
	
	private Point widthPoint2;
	private Point heightPoint2;
	private Point center2;
	private Ellipse ellipse2;
	
	@Before
	public void setUp() throws Exception{
		this.center1 = new Point(0, 0);
		this.widthPoint1 = new Point(0, 10);
		this.heightPoint1 = new Point(5, 0);
		this.ellipse1 = new Ellipse(center1, widthPoint1, heightPoint1);
		
		this.center2 = new Point(1, 1);
		this.widthPoint2 = new Point(6, 1);
		this.heightPoint2 = new Point(1, 11);
		this.ellipse2 = new Ellipse(center2, widthPoint2, heightPoint2);
	}

	@Test(expected=NullArgumentException.class)
	public void shouldNotCreateEllipseIfCenterIsNotPassed() throws Exception {
		new Ellipse(null, widthPoint1, heightPoint1);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void shouldNotCreateEllipseIfPointsAreTooCloseOfCenter() throws Exception {
		new Ellipse(center1, center1, heightPoint1);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void shouldNotCreateEllipseIfColinearPointsArePassed() throws Exception {
		new Ellipse(center1, new Point(-5,0), new Point(5,0));
	}
	
	@Test
	public void shouldEqualsToAnotherEllipseIfAllPointsAreEquals() throws Exception {
		Ellipse ellipse = new Ellipse(center1, widthPoint1, heightPoint1);
		
		assertTrue(ellipse1.equals(ellipse));
	}
	
	@Test
	public void shouldGetEqualSemiMajorAxis() {
		assertEquals(ellipse1.getSemiMajorAxis(), new Vector(center1, widthPoint1));
		
		Vector expected = new Vector(new Point(1,1), new Point(1,11));
		assertEquals(expected, ellipse2.getSemiMajorAxis());
	}
	@Test
	public void getFiTest(){
		assertEquals(Math.PI/2 ,ellipse1.getFi(), Constant.EPSILON);
		assertEquals(0,ellipse2.getFi(), Constant.EPSILON);
	}
	
	@Test
	public void getSemiMinorAxisTest(){
		Vector expected = new Vector(new Point(0,0), new Point(-5,0));
		assertEquals(expected, ellipse1.getSemiMinorAxis());
		
		expected = new Vector(new Point(1,1), new Point(6,1));
		assertEquals(expected, ellipse2.getSemiMinorAxis());
	}
	
	@Test
	public void cloneTest(){
		Ellipse ellipseClone = (Ellipse)ellipse1.clone();
		assertEquals(ellipseClone, ellipse1);
		assertNotSame(ellipseClone, ellipse1);
		
	}
	
	@Test
	public void testHashCodeEllipse() throws Exception {
		int hash1 = ellipse1.hashCode();
		int hash2 = ellipse2.hashCode();
		assertEquals(hash1, ellipse1.hashCode()); // Test if it returns the same code again
		assertNotSame(hash1, hash2);
		
		Ellipse e1 = new Ellipse(ellipse1.getCenter(), ellipse1.getWidthPoint(),ellipse1.getHeightPoint());
		int hash_e1 = e1.hashCode();
		assertEquals(hash1, hash_e1);
		e1.move(5, 0);
		assertNotSame(hash_e1, e1.hashCode()); // Object changed, hash should change
	}
	
	@Test
	public void boundaryRectangleTest() {
		Rectangle r1 = new Rectangle(-5, 10, 5, -10);
		assertEquals(r1, ellipse1.getBoundaryRectangle());
		
		Rectangle r2 = new Rectangle(-4, 11, 6, -9);
		assertEquals(r2, ellipse2.getBoundaryRectangle());
	}
	
	@Test
	public void rotateTest() throws NullArgumentException{
		Ellipse e1 = (Ellipse)ellipse1.clone();
		e1.rotate(new Point(0,0), Math.PI/2);
		assertEquals(e1.getFi() , Math.PI, Constant.EPSILON);
		
		Ellipse e2 = (Ellipse)ellipse2.clone();
		e2.rotate(new Point(0,0), Math.PI/4);
		assertEquals(e2.getFi() , Math.PI/4, Constant.EPSILON);
		
		
	}
	
	@Test
	public void shouldContainsPoint() throws Exception {
		Point frontierPoint1 = new Point(2, 9.16515138991168);
		Point frontierPoint2 = heightPoint1;
		Point frontierPoint3 = new Point(4, 5.999999999999998);
		
		assertFalse(ellipse1.contains(new Point(2, 2)));
		assertFalse(ellipse1.contains(new Point(0,0)));
		assertFalse(ellipse1.contains(new Point(-4,-2)));
		
		assertTrue(ellipse1.contains(frontierPoint1));
		assertTrue(ellipse1.contains(frontierPoint2));
		assertTrue(ellipse1.contains(frontierPoint3));
	}
	
	@Test
	public void shouldPositiveDirectionBeTrueWhenPointIsOutside() throws Exception {
		Point pointOutside = new Point(20, 20);
		
		assertTrue(ellipse1.isPositiveDirection(pointOutside));
	}
	
	@Test
	public void shouldContainsCenterAndAllFourIntersectionsWithAxis() throws Exception {
		Collection<Point> refPoints = new ArrayList<Point>();
		
		Rectangle container = new Rectangle(-20, -20, 20, 20);

		Point oppositeHeightPoint = new Point(-5,0);
		Point oppositeWidthPoint = new Point(0,-10);
		Collection<Point> points = Arrays.asList(center1, heightPoint1, widthPoint1, oppositeHeightPoint, oppositeWidthPoint);
		
		Collection<? extends ReferencePoint> referencePoints = ellipse1.getReferencePoints(container);
	
		for (ReferencePoint referencePoint : referencePoints) {
			refPoints.add(referencePoint.getPoint());
		}
		assertEquals(5,refPoints.size());
		assertTrue(refPoints.containsAll(points));
	}
	
	@Test(expected=InvalidParameterException.class)
	public void shouldNotCalculateOffsetIfDistanceIsInvalid() throws Exception {
		ellipse1.cloneWithDistance(-1000);
	}

	@Test
	public void shouldCalculateOffset() throws Exception {
		Ellipse expectedEllipse = new Ellipse(center1, new Point(0, 15), new Point(-10, 0));
		
		Element offsetedEllipse = ellipse1.cloneWithDistance(5);
		assertEquals(expectedEllipse, offsetedEllipse);
	
		Ellipse ellipse = new Ellipse(center1, widthPoint1, new Point(-5, 0));
		
		Element offsetedEllipse2 = ellipse.cloneWithDistance(5);
		assertEquals(expectedEllipse, offsetedEllipse2);
	}
	
	@Test 
	public void shouldReturnProjectionOfPointAtEllipse() throws Exception{
		Point expectedPoint = new Point(5*Math.sqrt(2.0)/2.0,10*Math.sqrt(2.0)/2.0);
		Point point = ellipse1.getProjectionOf(new Point(1, 1));
		assertEquals(expectedPoint, point);
	}
	
	@Test
	public void shouldCalculateFocusPointsOfAnEllipse() {
		double expectedF = Math.sqrt(10*10 - 5*5);
		Vector expectedE1 = new Vector(center1, widthPoint1).normalized();
		Point expectedPoint1 = center1.addVector(expectedE1.multiply(expectedF));
		Point expectedPoint2 = center1.addVector(expectedE1.multiply(-expectedF));
		Collection<Point> focus = ellipse1.calculateFocusPoints();
				
		assertTrue(focus.contains(expectedPoint1));
		assertTrue(focus.contains(expectedPoint2));
	}
}
