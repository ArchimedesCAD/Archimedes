package br.org.archimedes.ellipticarc.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.ellipticarc.EllipticArc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class EllipticArcTest {
	
	private Point widthPoint1;
	private Point heightPoint1;
	private Point center1;
	private EllipticArc ellipticArc1;
	private Point endPoint1;
	private Point initialPoint1;

	@Before
	public void setUp() throws Exception {
		this.center1 = new Point(0, 0);
		this.widthPoint1 = new Point(0, 10);
		this.heightPoint1 = new Point(5, 0);
		this.initialPoint1 = new Point(0, 10);
		this.endPoint1 = new Point(5, 0);
		this.ellipticArc1 = new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint1, endPoint1);
	}
	@Test
	public void shouldEqualsToAnotherEllipseIfAllPointsAreEquals()
			throws Exception {
		EllipticArc ellipticArc = new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint1, endPoint1);

		assertTrue(ellipticArc1.equals(ellipticArc));
	}
	

	
	@Test
	public void shouldNotAcceptBrokenEllipticArc() throws NullArgumentException {
		Point endPoint2 = new Point(6,0);
		boolean trow = false;
		try{
			new EllipticArc(center1, widthPoint1, heightPoint1, initialPoint1, endPoint2);
		}
		catch( InvalidArgumentException e){
			trow = true;
		}
		assertTrue(trow);
	
	}
	
	@Test
	public void shouldNotAcceptBrokenEllipticArc2() throws NullArgumentException {
		boolean trow = false;
		try{
			new EllipticArc(center1, widthPoint1, widthPoint1, initialPoint1, endPoint1);
		}
		catch( InvalidArgumentException e){
			trow = true;
		}
		assertTrue(trow);
	
	}
	@Test
	public void shouldNotAcceptBrokenEllipticArc3() throws NullArgumentException {
		boolean trow = false;
		try{
			new EllipticArc(center1, center1, heightPoint1, initialPoint1, endPoint1);
		}
		catch( InvalidArgumentException e){
			trow = true;
		}
		assertTrue(trow);
	
	}
	@Test
	public void shouldNotAcceptBrokenEllipticArc4() throws NullArgumentException {
		boolean trow = false;
		try{
			new EllipticArc(center1, widthPoint1, center1, initialPoint1, endPoint1);
		}
		catch( InvalidArgumentException e){
			trow = true;
		}
		assertTrue(trow);
	
	}
	
	@Test
	public void shouldCreateEllipseWithGreaterHeightThanWidth()
			throws Exception {
		new EllipticArc(center1, heightPoint1, widthPoint1, initialPoint1, endPoint1);

		assertTrue(true);
	}
	
	@Test
	public void EllipticArcClone() throws InvalidArgumentException, NullArgumentException{
		EllipticArc e = new EllipticArc(center1, heightPoint1, widthPoint1, initialPoint1, endPoint1);
		EllipticArc f = e.clone();
		assertTrue(e.equals((Object)f));
		
	}
	
	@After
	public void tearDown() throws Exception {
	}

	

}
