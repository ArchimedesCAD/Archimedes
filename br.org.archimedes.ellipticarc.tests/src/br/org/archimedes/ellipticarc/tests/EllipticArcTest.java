package br.org.archimedes.ellipticarc.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.ellipticarc.EllipticArc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.model.Point;

public class EllipticArcTest {
	
	private Point widthPoint1;
	private Point heightPoint1;
	private Point center1;
	private EllipticArc ellipticArc1;
	private Point endPoint;
	private Point initialPoint;

	@Before
	public void setUp() throws Exception {
		this.center1 = new Point(0, 0);
		this.widthPoint1 = new Point(0, 10);
		this.heightPoint1 = new Point(5, 0);
		this.initialPoint = new Point(0, 10);
		this.endPoint = new Point(5, 0);
		this.ellipticArc1 = new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint, endPoint);
	}
	@Test
	public void shouldEqualsToAnotherEllipseIfAllPointsAreEquals()
			throws Exception {
		EllipticArc ellipticArc = new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint, endPoint);

		assertTrue(ellipticArc1.equals(ellipticArc));
	}

	
	@Test
	public void shouldNotAcceptBrokenEllipticArc() {
		Point endPoint2 = new Point(6,0);
		boolean trow = false;
		try{
		EllipticArc ellipticArc = new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint, endPoint2);
		}
		catch( InvalidArgumentException e){
			trow = true;
		}
		assertTrue(trow);
	
	}
	
	@After
	public void tearDown() throws Exception {
	}

	

}
